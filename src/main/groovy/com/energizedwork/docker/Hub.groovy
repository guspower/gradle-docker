package com.energizedwork.docker

import com.energizedwork.docker.event.hub.Search
import com.energizedwork.docker.http.Server


class Hub {

    Server server

    List<Image> search(String term) {
        server.get new Search(term)
    }

}
