package com.energizedwork.docker.event.server

import com.energizedwork.docker.event.DockerEvent

class Ping extends DockerEvent {

    final String path = '/_ping'

    def decodeResponse(def response) { true }

}
