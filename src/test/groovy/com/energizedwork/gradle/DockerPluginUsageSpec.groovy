package com.energizedwork.gradle

import com.energizedwork.docker.Container
import com.energizedwork.docker.ContainerDetail
import com.energizedwork.docker.DockerTestTrait
import org.gradle.tooling.BuildLauncher
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection

import spock.lang.Specification
import spock.lang.Unroll

class DockerPluginUsageSpec extends Specification implements DockerTestTrait {

    def setup() {
        setupClient()
    }

    def cleanup() {
        cleanupTestContainersAndImages()
    }

    @Unroll('can run #task')
    def 'can run gradle project'() {
        given:
            def build = gradle(project)

        and:
            def stdout = new ByteArrayOutputStream()

        when:
            build.standardOutput = stdout
            build.forTasks(task).run()

        then:
            stdout.toString().contains 'BUILD SUCCESSFUL'
            println stdout

        where:
            task << ['containers', 'images']
            project = 'basic'
    }

    def 'can start container through gradle'() {
        given:
            def build = gradle(project)

        and:
            def stdout = new ByteArrayOutputStream()

        when:
            build.standardOutput = stdout
            build.forTasks(task).run()

        then:
            stdout.toString().contains 'BUILD SUCCESSFUL'

        when:
            Container container = client.findContainerByName(TEST_CONTAINER_NAME)
            ContainerDetail detail = client.inspect(container.id)

        then:
            container
            detail.state.running

        where:
            task    = 'startNewContainer'
            project = 'basic'
    }

    def 'can stop container through gradle'() {
        given:
            def build = gradle(project)

        and:
            def stdout = new ByteArrayOutputStream()

        when:
            build.standardOutput = stdout
            build.forTasks(task).run()

        then:
            stdout.toString().contains 'BUILD SUCCESSFUL'
            println stdout

        and:
            waitFor { ! client.findContainerByName(TEST_CONTAINER_NAME).running }

        where:
            task    = 'startAndStopContainer'
            project = 'basic'
    }

    def 'can delete container through gradle'() {
        given:
            def build = gradle(project)

        and:
            def stdout = new ByteArrayOutputStream()

        when:
            build.standardOutput = stdout
            build.forTasks(task).run()

        then:
            stdout.toString().contains 'BUILD SUCCESSFUL'
            println stdout

        and:
            waitFor { ! client.findContainerByName(TEST_CONTAINER_NAME) }

        where:
            task    = 'deleteContainer'
            project = 'basic'
    }

    def 'can control containers using DSL'() {
        given:
            def build = gradle(project)

        and:
            def stdout = new ByteArrayOutputStream()

        when:
            build.standardOutput = stdout
            build.forTasks(task).run()

        then:
            stdout.toString().contains 'BUILD SUCCESSFUL'
            println stdout

        where:
            task    = 'useDSLTask'
            project = 'basic'
    }

    private BuildLauncher gradle(String projectName) {
        File projectDir = new File("src/test/resources/project/$projectName")
        ProjectConnection connection = GradleConnector.newConnector()
            .forProjectDirectory(projectDir).connect()

        connection.newBuild()
    }

}
