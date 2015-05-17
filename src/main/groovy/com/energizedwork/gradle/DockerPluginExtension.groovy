package com.energizedwork.gradle

import com.energizedwork.docker.Client
import com.energizedwork.docker.Container
import com.energizedwork.docker.http.DockerServerConfig
import com.energizedwork.gradle.docker.config.Host
import org.gradle.api.Project

class DockerPluginExtension {

    static final String NAME = 'docker'

    private Client _client
    private Project _project

    Host host

    DockerPluginExtension(Project project) {
        _project = project
        host = new Host(project)
    }

    void host(Closure closure) {
        _project.configure host, closure
    }

    Container container

    List<String> command
    String image

    Client getClient() {
        if(! _client) {
            _client = new Client(host.asType(DockerServerConfig))
        }

        _client
    }

    void validate() {
        try {
            host.url
        } catch(MalformedURLException e) {
            throw new IllegalArgumentException('Invalid docker host url', e)
        }
    }

}
