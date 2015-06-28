package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.gradle.DockerPluginExtension

class ListImagesTask extends AbstractDockerTask {

    final static String TASK_NAME = 'images'

    String description = 'Lists all docker images on docker host'

    @Override
    void run(Client client, DockerPluginExtension extension) {
        client.images().each { println it }
    }

}
