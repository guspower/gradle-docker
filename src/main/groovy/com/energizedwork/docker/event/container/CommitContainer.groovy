package com.energizedwork.docker.event.container

import com.energizedwork.docker.event.IdEvent
import groovyx.net.http.ContentType
import groovyx.net.http.Method

import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.POST


class CommitContainer extends IdEvent {

    private String imageName
    private String tagName

    CommitContainer(String id, String imageName, String tagName) {
        this.id = id
        this.imageName = imageName
        this.tagName = tagName
    }

    ContentType getContentType() { JSON }

    Method getMethod() { POST }

    String getPath() { '/commit' }

    /**
     * container
     * repo
     * tag
     * comment
     * author
     *
     * https://docs.docker.com/reference/api/docker_remote_api_v1.18/#create-a-new-image-from-a-containers-changes
     *
     */
    Map<String, Object> getQuery() { [container: id, repo: imageName, tag: tagName] }

}
