package com.energizedwork.docker.event.container

import com.energizedwork.docker.Process
import com.energizedwork.docker.event.IdEvent

class ListProcesses extends IdEvent {

    ListProcesses(String id) {
        this.id = id
    }

    String getPath() { "/containers/$id/top" }

    List<Process> decodeResponse(def response) {
        def fields = response.Titles.collect { String field -> field.toLowerCase() }
        response.Processes.collect { data ->
            def process = new Process()
            fields.eachWithIndex { String field, int index ->
                process.put field, data[index]
            }
            process
        }
    }

}
