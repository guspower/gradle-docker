package com.energizedwork.docker

import com.energizedwork.docker.event.container.CreateContainer
import com.energizedwork.docker.event.container.DeleteContainer
import com.energizedwork.docker.event.container.DownloadFiles
import com.energizedwork.docker.event.container.GetContainerLogs
import com.energizedwork.docker.event.container.ListContainers
import com.energizedwork.docker.event.container.ListFilesystemChanges
import com.energizedwork.docker.event.container.ListProcesses
import com.energizedwork.docker.event.container.PauseContainer
import com.energizedwork.docker.event.container.RestartContainer
import com.energizedwork.docker.event.container.UnpauseContainer
import com.energizedwork.docker.event.container.WaitOnContainer
import com.energizedwork.docker.event.image.BuildImage
import com.energizedwork.docker.event.image.DeleteImage
import com.energizedwork.docker.event.image.DownloadImage
import com.energizedwork.docker.event.container.InspectContainer
import com.energizedwork.docker.event.container.KillContainer
import com.energizedwork.docker.event.container.StartContainer
import com.energizedwork.docker.event.container.StopContainer
import com.energizedwork.docker.event.image.InspectImage
import com.energizedwork.docker.event.image.ListImages
import com.energizedwork.docker.event.image.TagImage
import com.energizedwork.docker.event.image.UploadImage
import com.energizedwork.docker.event.server.Info
import com.energizedwork.docker.event.server.Ping
import com.energizedwork.docker.event.server.Version
import com.energizedwork.docker.http.Server
import groovy.transform.ToString


@ToString(includePackage = false, includeFields = true)
class Client {

    private Server _server

    String host = 'localhost'

    Map<String, ?> info()    { server.get new Info() }
    boolean ping()           { server.get new Ping() }
    Map<String, ?> version() { server.get new Version() }

    Container create(CreateContainer create)  { server.post create }

    ContainerDetail inspect(String id)        { server.get new InspectContainer(id) }
    List<FileSystemChange> changes(String id) { server.get new ListFilesystemChanges(id) }
    List<Process> ps(String id)               { server.get new ListProcesses(id) }
    List<String> stdOut(String id)            { server.get new GetContainerLogs(id, true, false) }
    List<String> stdErr(String id)            { server.get new GetContainerLogs(id, false, true) }

    def download(String id, String path) { server.post new DownloadFiles(id, path) }

    void kill(String id)    { server.request new KillContainer(id) }
    void start(String id)   { server.request new StartContainer(id) }
    void restart(String id) { server.request new RestartContainer(id) }
    void stop(String id)    { server.request new StopContainer(id) }
    void waitOn(String id)  { server.request new WaitOnContainer(id) }
    void pause(String id)   { server.request new PauseContainer(id) }
    void unpause(String id) { server.request new UnpauseContainer(id) }
    void delete(String id)  { server.request new DeleteContainer(id) }

    Hub getHub() { new Hub(server: server) }

    List<Container> containers() { server.get new ListContainers() }
    List<Image> images() { server.get new ListImages() }

    void buildImage(String name, String tag, String dockerFile) {
        server.request(new BuildImage(name: name, tag: tag, dockerFile: dockerFile))
    }
    void deleteImage(String nameOrId)    { server.request new DeleteImage(nameOrId) }
    File downloadImage(String nameOrId)  { server.request new DownloadImage(nameOrId) }
    def inspectImage(String nameOrId)    { server.request new InspectImage(nameOrId) }
    void tagImage(String id, String name, String tag, boolean force = false) {
        server.request new TagImage(id, name, tag, force)
    }
    void uploadImage(UploadImage upload) { server.request upload }

    void configureSSL(Map options) {
        def ssl = new Server.SSLClientCertificate(host: host)
        options.each { String key, value ->
            if(ssl.hasProperty(key)) {
                options."$key" = value
            }
        }

        _server = new Server(ssl)
    }

    private Server getServer() {
        if(!_server) {
            _server = new Server(host)
        }
        _server
    }

}
