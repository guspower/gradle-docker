package com.energizedwork.docker.event.container

import com.energizedwork.docker.content.TarSupport
import com.energizedwork.docker.event.IdEvent
import groovyx.net.http.ContentType
import groovyx.net.http.Method

import static groovyx.net.http.Method.POST

class DownloadFiles extends IdEvent {

    String path

    DownloadFiles(String id, String path) {
        this.id = id
        this.path = path
    }

    String getContentType() { TarSupport.CONTENT_TYPE }

    ContentType getRequestContentType() { ContentType.JSON }

    Method getMethod() { POST }

    String getPath() { "/containers/$id/copy" }

    Map getBody() { [Resource: path] }

    def decodeResponse(def response) {
        File target
        if(response) {
            target = File.createTempFile("docker-id-path", '.tar')
            response.withStream {
                target << response
            }
        }
        target
    }

}
