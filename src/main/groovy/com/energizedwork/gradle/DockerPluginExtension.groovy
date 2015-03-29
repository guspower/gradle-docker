package com.energizedwork.gradle

import com.energizedwork.docker.Client
import com.energizedwork.docker.Container

class DockerPluginExtension {

    private Client _client

    Container container

    List<String> command
    String host = InetAddress.localHost.hostName
    String image
    String tag = new Date().format('ddMMyyHHmm')

    Map ssl = [:]

    Client getClient() {
        if(! _client) {
            _client = new Client(host: host)
            _client.configureSSL ssl
        }

        _client
    }

}
