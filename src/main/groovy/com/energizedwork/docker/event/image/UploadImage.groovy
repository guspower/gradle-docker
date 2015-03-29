package com.energizedwork.docker.event.image

import com.energizedwork.docker.event.DockerEvent
import groovy.transform.ToString
import groovyx.net.http.Method

import static groovyx.net.http.Method.POST


@ToString(includePackage = false, includeFields = true)
class UploadImage extends DockerEvent {

    File file
    String name
    String tag

    Object getBody() { file }

    String getContentType() { 'application/x-tar' }

    Method getMethod() { POST }

    String getPath() { '/images/create' }

    Map<String, Object> getQuery() { [fromSrc: '-', tag: tag, repo: name] }

}
