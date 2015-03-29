package com.energizedwork.docker.event

import groovyx.net.http.ContentType
import groovyx.net.http.Method

abstract class DockerEvent {

    abstract String getPath()

    Method getMethod() { Method.GET }

    Map<String, Object> getQuery()      { null }
    def getContentType()                { null }
    ContentType getRequestContentType() { null }
    Object getBody()                    { null }
    boolean isNoContent()               { method != Method.GET }

    Map getRequestProperties() {
        Map result = [
                body: body,
                contentType: contentType,
                path: path,
                query: query,
                requestContentType: requestContentType
        ]

        result.findAll { it.value != null }
    }

    def decodeResponse(def response, def inputStream = null) { response }

}