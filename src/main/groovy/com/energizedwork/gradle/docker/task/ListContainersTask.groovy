package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.gradle.DockerPluginExtension

class ListContainersTask extends AbstractDockerTask {

    @Override
    void run(Client client, DockerPluginExtension extension) {
        client.containers().each { println it }
    }

}
