package com.energizedwork.docker.event.container

import com.energizedwork.docker.Container
import com.energizedwork.docker.config.ContainerConfigBuilder
import com.energizedwork.docker.event.DockerEvent
import groovyx.net.http.ContentType

import static groovyx.net.http.ContentType.JSON

/**
 * See https://docs.docker.com/reference/api/docker_remote_api_v1.18/#create-a-container
 */
class CreateContainer extends DockerEvent {

    String path = '/containers/create'

    String hostName      = ''
    String domainName    = ''
    String imageName
    List<String> command = []
    boolean networkDisabled

    Map getBody() {
        ContainerConfigBuilder.newConfig()
            .host(hostName)
            .image(imageName)
            .command(command).config()
    }

    Map<String, String> getQuery() {
        [name: hostName]
    }

    ContentType getRequestContentType() { JSON }

    boolean noContent = false

    def decodeResponse(def response) {
        new Container(id: response.Id)
    }

    static class HostConfig {

        String networkMode = 'bridge'

    }

}
