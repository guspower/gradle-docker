package com.energizedwork.docker.event.container

import com.energizedwork.docker.Container
import com.energizedwork.docker.event.DockerEvent

class ListContainers extends DockerEvent {

    final String path = '/containers/json'

    Map<String, Object> getQuery() { [all: 1] }

    def decodeResponse(def response) {
        response.collect { record ->
            def container = new Container(
                    command: record.Command,
                    id:      record.Id,
                    image:   record.Image,
                    names:   record.Names,
                    ports:   record.Ports,
                    status:  record.Status
            )
            container.created = record.Created
            container
        }
    }

}
