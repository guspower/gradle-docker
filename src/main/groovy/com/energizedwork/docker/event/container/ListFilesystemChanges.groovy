package com.energizedwork.docker.event.container

import com.energizedwork.docker.FileSystemChange
import com.energizedwork.docker.Process
import com.energizedwork.docker.event.IdEvent

class ListFilesystemChanges extends IdEvent {

    ListFilesystemChanges(String id) {
        this.id = id
    }

    String getPath() { "/containers/$id/changes" }

    List<FileSystemChange> decodeResponse(def response) {
        response.collect { data ->
            new FileSystemChange(
                    type: data.Kind,
                    path: data.Path
            )
        }
    }

}
