package com.energizedwork.gradle.docker

import com.energizedwork.docker.Client
import com.energizedwork.docker.ContainerDetail
import com.energizedwork.gradle.DockerPluginExtension


class InspectContainerTask extends AbstractDockerTask {

    File file
    String id

    @Override
    void run(Client client, DockerPluginExtension extension) {
        if(!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }

        ContainerDetail detail = client.inspect(id)
        file.text = detail.json
    }

}
