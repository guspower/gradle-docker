package com.energizedwork.docker.event.image

import com.energizedwork.docker.event.DockerEvent
import groovyx.net.http.ContentType
import groovyx.net.http.Method

import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.DELETE


class DeleteImage extends DockerEvent {

    String nameOrId

    DeleteImage(String nameOrId) {
        this.nameOrId = nameOrId
    }

    ContentType getContentType() { TEXT }

    Method getMethod() { DELETE }

    String getPath() { "/images/$nameOrId" }

}
