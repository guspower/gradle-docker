package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.gradle.DockerPluginExtension

class DeleteContainerTask extends AbstractDockerTask {

    String id

    void run(Client client, DockerPluginExtension extension) {
        client.delete id
    }

}
