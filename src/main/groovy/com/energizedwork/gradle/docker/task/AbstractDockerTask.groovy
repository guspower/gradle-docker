package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.gradle.DockerPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class AbstractDockerTask extends DefaultTask {

    final String group = 'Docker'

    @TaskAction
    void run() {
        DockerPluginExtension extension = project.docker
        Client client = extension.client

        run client, extension
    }

    abstract void run(Client client, DockerPluginExtension extension)

}
