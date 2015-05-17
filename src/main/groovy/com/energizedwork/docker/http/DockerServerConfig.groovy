package com.energizedwork.docker.http

import groovyx.net.http.HTTPBuilder

interface DockerServerConfig {

    String getBaseUrl()
    void configure(HTTPBuilder server)

}
