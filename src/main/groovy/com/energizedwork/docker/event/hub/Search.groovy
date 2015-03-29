package com.energizedwork.docker.event.hub

import com.energizedwork.docker.Image
import com.energizedwork.docker.event.DockerEvent

class Search extends DockerEvent {

    String path = '/images/search'

    String term

    Search(String term) {
        this.term = term
    }

    Map<String, Object> getQuery() { [term: term] }

    def decodeRequest(def request) {
        request.collect {
            new Image(name: it.name, description: it.description)
        }
    }

}
