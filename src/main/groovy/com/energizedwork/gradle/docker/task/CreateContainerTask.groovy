package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.docker.Container
import com.energizedwork.docker.event.container.CreateContainer
import com.energizedwork.gradle.DockerPluginExtension


class CreateContainerTask extends AbstractDockerTask {

    List<String> command
    String hostname
    String image

    Container container

    void run(Client client, DockerPluginExtension extension) {
        def create = new CreateContainer(hostName: hostname, imageName: image, command: command)

        container = client.create(create)
    }

}
