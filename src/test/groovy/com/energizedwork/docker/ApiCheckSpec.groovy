package com.energizedwork.docker

import com.energizedwork.docker.event.container.CreateContainer
import com.energizedwork.docker.event.image.UploadImage
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import spock.lang.Specification
import spock.lang.Unroll

class ApiCheckSpec extends Specification implements DockerTestTrait {

    CreateContainer busybox

    def setup() {
        setupClient()

        busybox = new CreateContainer(
                imageName: 'busybox',
                command: ['/bin/sh', '-c', '"echo START; echo START 1>&2; while true; do echo STDOUT; echo STDERR 1>&2; touch foo; sleep 1; done"']
        )
    }

    def cleanup() {
        cleanupTestContainersAndImages()
    }

    @Unroll('can call #action on docker API')
    def 'can connect to docker API'() {
        when:
            def data = client."$action"()

        then:
            data

        where:
            action   << ['info', 'version', 'images', 'ping']
    }

    def 'can find created container by name'() {
        given:
            busybox.hostName = hostName

        when:
            client.create(busybox)

        and:
            def container = client.findContainerByName(hostName)

        then:
            container

        where:
            hostName = generateUniqueName()
    }

    def 'can search for an image on Docker Hub'() {
        when:
            def images = client.hub.search(term)

        then:
            images

        where:
            term = 'busybox'
    }

    def 'can create, start, stop, kill and destroy a busybox container'() {
        given:
            busybox.hostName = hostName

        when:
            Container container = client.create(busybox)

        then:
            container
            container.id

        when:
            initConditions container.id

        then:
            waitFor created

        and:
            [start: started, stop: stopped, start: started, kill: stopped, delete: deleted].each { action, condition ->
                client."$action" container.id
                waitFor condition
            }

        where:
            hostName = generateUniqueName()
    }

    def 'can create and ping container'() {
        given:
            busybox.hostName = hostName

        when:
            ContainerDetail container = createAndStart(busybox)

        then:
            waitFor 'container has ip address', {
                def details = client.inspect(container.id)
                details.networkSettings.ip
            }

        and:
            InetAddress.getByName(container.networkSettings.ip).isReachable(timeoutInSeconds as Integer)

        where:
            hostName = generateUniqueName()
    }

    def 'can see container processes'() {
        given:
            busybox.hostName = hostName

        when:
            ContainerDetail container = createAndStart(busybox)

        then:
            client.ps(container.id).find { Process process -> process.cmd.contains('/bin/sh') }

        where:
            hostName = generateUniqueName()
    }

    def 'can see container logs'() {
        given:
            busybox.hostName = hostName

        when:
            ContainerDetail container = createAndStart(busybox)

        and:
            Thread.sleep 1000

        then:
            client.stdOut(container.id)[0].contains 'START'
            client.stdOut(container.id)[1].contains 'STDOUT'
            client.stdErr(container.id)[0].contains 'START'
            client.stdErr(container.id)[1].contains 'STDERR'

        where:
            hostName = generateUniqueName()
    }

    def 'can see container filesystem changes'() {
        given:
            busybox.hostName = hostName

        when:
            ContainerDetail container = createAndStart(busybox)

        and:
            Thread.sleep 1000

        then:
            '/foo' == client.changes(container.id)[0].path

        where:
            hostName = generateUniqueName()
    }

    def 'can restart container'() {
        given:
            busybox.hostName = hostName

        when:
            ContainerDetail container = createAndStart(busybox)

        and:
            client.restart container.id

        then:
            waitFor started

        and:
            2 == client.stdOut(container.id).findAll { it.contains('START') }.size()

        where:
            hostName = generateUniqueName()
    }

    def 'can pause and unpause a container'() {
        given:
            busybox.hostName = hostName

        when:
            ContainerDetail container = createAndStart(busybox)

        and:
            client.pause container.id

        then:
            waitFor paused

        and:
            client.unpause container.id

        then:
            waitFor started

        where:
            hostName = generateUniqueName()
    }

    def 'can wait for a container to exit'() {
        given:
            busybox.command  = command
            busybox.hostName = hostName

        when:
            ContainerDetail container = createAndStart(busybox)

        and:
            client.waitOn container.id

        then:
            waitFor stopped

        where:
            command  = ['/bin/sh', '-c', '"sleep 2"']
            hostName = generateUniqueName()
    }

    def 'can download files from a container'() {
        given:
            busybox.command  = command
            busybox.hostName = hostName

        when:
            ContainerDetail container = createAndStart(busybox)

        and:
            File file = client.download(container.id, '/dir')

        then:
            file

        and:
            ['dir/', 'dir/bar', 'dir/foo'] == getArchiveEntries(file)

        where:
            command  = ['/bin/sh', '-c', '"mkdir dir; cd dir; echo fish > foo; echo cow > bar; while true; do sleep 1; done"']
            hostName = generateUniqueName()
    }

    def 'create download, upload, tag and delete images'() {
        when:
            File imageFile = client.downloadImage(imageName)

        then:
            imageFile.exists()
            imageFile.size()

        when:
            String nameAndTag = "$name:$tag"
            client.uploadImage(new UploadImage(file: imageFile, name: name, tag: tag))

        and:
            Image image

        then:
            waitFor 'image created', { image = client.images().find { Image i -> nameAndTag in i.tags }; image }

        when:
            String newTag = "${tag}-NEW"
            String nameAndNewTag = "$name:$newTag"
            client.tagImage image.id, name, newTag

        then:
            waitFor 'image tagged', { image = client.images().find { Image i -> nameAndNewTag in i.tags }; image }

        when:
            client.deleteImage nameAndTag
            client.deleteImage nameAndNewTag

        then:
            waitFor 'image deleted', { !client.images().find { nameAndTag in it.tags } }
            waitFor 'image tag deleted', { !client.images().find { nameAndNewTag in it.tags } }

        where:
            imageName = 'busybox:latest'
            name      = 'busybox'
            tag       = generateUniqueName()
    }

    def 'can inspect an image'() {
        when:
            def details = client.inspectImage(imageName)

        then:
            details

        where:
            imageName = 'busybox'
    }

    def 'can build an image from a Dockerfile'() {
        when:
            client.buildImage name, tag, dockerFile

        then:
            waitFor 'image created', { client.images().find { Image i -> "$name:$tag".toString() in i.tags } }

        when:
            def details = client.inspectImage("$name:$tag")

        then:
            details.ContainerConfig.Cmd.find { it.contains 'CMD [/bin/true]' }

        where:
            command    = ['/bin/true']
            dockerFile = '''
FROM busybox
MAINTAINER Gus Power, gus@energizedwork.com
CMD ["/bin/true"]
'''
            name      = 'busybox-new'
            tag       = generateUniqueName()
    }

    def 'can create an image from a container'() {
        given:
            busybox.hostName = hostName

        when:
            ContainerDetail container = createAndStart(busybox)

        then:
            waitFor created

        and:
            client.stop container.id
            waitFor stopped

        when:
            client.commit container.id, imageName, tag

        and:
            def details = client.inspectImage("$imageName:$tag")

        then:
            details

        where:
            imageName = 'busybox-new'
            tag       = generateUniqueName()
            hostName  = generateUniqueName()
    }

    private List<String> getArchiveEntries(File tar) {
        List<String> result = []

        tar.withInputStream { inputStream ->
            def archive = new TarArchiveInputStream(inputStream)
            def entry = archive.nextTarEntry
            while(entry) {
                result << entry.name
                entry = archive.nextTarEntry
            }
        }

        result
    }

}
