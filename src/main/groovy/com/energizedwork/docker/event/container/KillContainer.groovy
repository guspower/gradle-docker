package com.energizedwork.docker.event.container

import com.energizedwork.docker.event.IdEvent
import groovyx.net.http.ContentType
import groovyx.net.http.Method

import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.POST

class KillContainer extends IdEvent {

    KillContainer(String id) {
        this.id = id
    }

    ContentType getContentType() { TEXT }

    Method getMethod() { POST }

    String getPath() { "/containers/$id/kill" }

}
