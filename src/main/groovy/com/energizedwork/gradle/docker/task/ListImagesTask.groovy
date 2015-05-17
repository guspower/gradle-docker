package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.gradle.DockerPluginExtension

class ListImagesTask extends AbstractDockerTask {

    @Override
    void run(Client client, DockerPluginExtension extension) {
        client.images().each { println it }
    }

}
