package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.docker.Container
import com.energizedwork.gradle.DockerPluginExtension

class StopContainerTask extends AbstractDockerTask {

    String description = 'Stops a docker container'

    String hostname

    @Override
    void run(Client client, DockerPluginExtension extension) {
        Container container = client.findContainerByName(hostname)
        if(container) {
            client.stop container.id
        } else {
            println "WARN: No container found for $hostname"
        }
    }

}
