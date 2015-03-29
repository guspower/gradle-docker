package com.energizedwork.docker.event.image

import com.energizedwork.docker.event.IdEvent
import groovyx.net.http.ContentType

import static groovyx.net.http.ContentType.JSON

class InspectImage extends IdEvent {

    String getPath() { "/images/$id/json" }

    ContentType getContentType() { JSON }

    InspectImage(String nameOrId) {
        this.id = nameOrId
    }

    def decodeResponse(def response, def json = null) {
        json
    }

}
