package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.docker.Container
import com.energizedwork.docker.ContainerDetail
import com.energizedwork.gradle.DockerPluginExtension


class InspectContainerTask extends AbstractDockerTask {

    String hostname
    Closure action

    @Override
    void run(Client client, DockerPluginExtension extension) {
        Container container = client.findContainerByName(hostname)
        if(container) {
            action.call client.inspect(container.id)
        } else {
            println "WARN: No container found for $hostname"
        }
    }

}
