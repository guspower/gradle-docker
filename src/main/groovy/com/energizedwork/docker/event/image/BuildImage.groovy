package com.energizedwork.docker.event.image

import com.energizedwork.docker.content.TarSupport
import com.energizedwork.docker.event.DockerEvent
import com.energizedwork.file.TarFile
import groovyx.net.http.Method

import static groovyx.net.http.Method.POST

class BuildImage extends DockerEvent {

    final String path = '/build'
    final String DOCKERFILE_NAME = 'Dockerfile'

    String name
    String tag
    String dockerFile

    Object getBody() {
        def tar = new TarFile()
        tar.add DOCKERFILE_NAME, dockerFile
        tar.writeOut()
        tar.bytes
    }

    String getContentType() { TarSupport.CONTENT_TYPE_NAME }

    Method getMethod() { POST }

    Map getQuery() { [t: "$name:$tag"] }

}
