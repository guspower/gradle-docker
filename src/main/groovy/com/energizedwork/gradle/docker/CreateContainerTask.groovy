package com.energizedwork.gradle.docker

import com.energizedwork.docker.Client
import com.energizedwork.docker.Container
import com.energizedwork.docker.event.container.CreateContainer
import com.energizedwork.gradle.DockerPluginExtension


class CreateContainerTask extends AbstractDockerTask {

    List<String> command
    String image

    Container container

    void run(Client client, DockerPluginExtension extension) {
        def create = new CreateContainer(imageName: image, command: command)

        container = client.create(create)
    }

}
