package com.energizedwork.docker.event.image

import com.energizedwork.docker.event.IdEvent
import groovyx.net.http.ContentType
import groovyx.net.http.Method

import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.POST


class TagImage extends IdEvent {

    String tag
    String name
    boolean force

    TagImage(String id, String name, String tag, boolean force = false) {
        this.id    = id
        this.name  = name
        this.tag   = tag
        this.force = force
    }

    ContentType getContentType() { TEXT }

    Method getMethod() { POST }

    String getPath() { "/images/$id/tag" }

    Map getQuery() {
        [repo: name, tag: tag, force: force]
    }

}
