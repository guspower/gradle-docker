package com.energizedwork.docker.event.container

import com.energizedwork.docker.event.IdEvent

class GetContainerLogs extends IdEvent {

    boolean stdOut
    boolean stdErr

    GetContainerLogs(String id, boolean stdOut, boolean stdErr) {
        this.id = id
        this.stdOut = stdOut
        this.stdErr = stdErr
    }

    String getPath() { "/containers/$id/logs" }

    Map<String, ?> getQuery() {
        [stdout: stdOut, stderr: stdErr]
    }

    def decodeResponse(def response) {
        response?.readLines()
    }

}
