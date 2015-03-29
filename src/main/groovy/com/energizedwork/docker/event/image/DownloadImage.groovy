package com.energizedwork.docker.event.image

import com.energizedwork.docker.event.DockerEvent
import groovyx.net.http.ContentType


class DownloadImage extends DockerEvent {

    String nameOrId

    DownloadImage(String nameOrId) {
        this.nameOrId = nameOrId
    }

    String getPath() { "/images/$nameOrId/get" }

    ContentType contentType = ContentType.BINARY

    def decodeResponse(def response, def inputStream) {
        File target = File.createTempFile("docker-$nameOrId", '.tar')
        inputStream.withStream { stream ->
            target << stream
        }
        target
    }

}
