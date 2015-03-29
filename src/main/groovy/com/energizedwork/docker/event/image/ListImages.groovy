package com.energizedwork.docker.event.image

import com.energizedwork.docker.Image
import com.energizedwork.docker.event.DockerEvent

class ListImages extends DockerEvent {

    final String path = '/images/json'

    def decodeResponse(def response) {
        response.collect { record ->
            def image = new Image(
                    id:          record.Id,
                    parentId:    record.ParentId,
                    tags:        record.RepoTags,
                    size:        record.Size,
                    virtualSize: record.VirtualSize,
            )
            image.created = record.Created
            image
        }
    }

}
