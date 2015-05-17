package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.gradle.DockerPluginExtension

class StopContainerTask extends AbstractDockerTask {

    String id

    @Override
    void run(Client client, DockerPluginExtension extension) {
        client.stop id
    }

}
