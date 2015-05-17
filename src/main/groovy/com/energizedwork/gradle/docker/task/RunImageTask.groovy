package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.docker.event.container.CreateContainer
import com.energizedwork.gradle.DockerPluginExtension

class RunImageTask extends AbstractDockerTask {

    String imageName
    String tag
    List<String> command

    @Override
    void run(Client client, DockerPluginExtension extension) {
        def create = new CreateContainer(imageName: "$imageName:$tag", command: command)
        extension.container = client.create(create)

        client.start extension.container.id
    }

}
