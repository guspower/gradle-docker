package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.gradle.DockerPluginExtension

class ListContainersTask extends AbstractDockerTask {

    final static String TASK_NAME = 'containers'

    String description = 'Lists all docker containers on docker host'

    @Override
    void run(Client client, DockerPluginExtension extension) {
        client.containers().each { println it }
    }

}
