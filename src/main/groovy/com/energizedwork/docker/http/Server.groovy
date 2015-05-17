package com.energizedwork.docker.http

import com.energizedwork.docker.content.TarSupport
import com.energizedwork.docker.event.DockerEvent
import groovyx.net.http.RESTClient


class Server {

    @Delegate
    private RESTClient _server
    private Closure requestFailure = { resp -> println resp.statusLine }

    Server() {
        this(new TLSConfig())
    }

    Server(String host) {
        this(new TLSConfig(host: host))
    }

    Server(DockerServerConfig config) {
        _server = new RESTClient(config.baseUrl)
        config.configure _server

        TarSupport.configure _server
    }

    def get(DockerEvent event) {
        event.decodeResponse get(event.requestProperties).data
    }

    def post(DockerEvent event) {
        event.decodeResponse post(event.requestProperties).data
    }

    def request(DockerEvent event) {
        def result

        request(event.method, event.contentType) { request ->
            uri.path = event.path

            if(event.query) { uri.query = event.query }
            if(event.body) { body = event.body }
            if(event.contentType) { contentType = event.contentType }
            if(event.requestContentType) { requestContentType = event.requestContentType }

            if(event.noContent) {
                response.'204' = { response, reader -> }
            } else {
                response.success = { response, stream ->
                    result = event.decodeResponse(response, stream)
                }
            }
            response.failure = requestFailure
        }

        result
    }

}
