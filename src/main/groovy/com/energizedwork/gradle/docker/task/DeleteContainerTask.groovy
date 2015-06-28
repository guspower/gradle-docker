package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.gradle.DockerPluginExtension

class DeleteContainerTask extends AbstractDockerTask {

    String description = 'Deletes a docker container'

    String hostname

    void run(Client client, DockerPluginExtension extension) {
        client.delete hostname
    }

}
