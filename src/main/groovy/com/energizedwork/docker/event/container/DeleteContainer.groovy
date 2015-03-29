package com.energizedwork.docker.event.container

import com.energizedwork.docker.event.IdEvent
import groovyx.net.http.ContentType
import groovyx.net.http.Method

import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.DELETE


class DeleteContainer extends IdEvent {

    DeleteContainer(String id) {
        this.id = id
    }

    ContentType getContentType() { TEXT }

    Method getMethod() { DELETE }

    String getPath() { "/containers/$id" }

    Map<String, Object> getQuery() { ['v': true] }

}
