package com.energizedwork.gradle.docker.task

import com.energizedwork.docker.Client
import com.energizedwork.docker.Image
import com.energizedwork.gradle.DockerPluginExtension

class TagImageTask extends AbstractDockerTask {

    String description = 'Tags a docker image with a given tag'

    String imageName
    String tag
    String newTag
    boolean force

    @Override
    void run(Client client, DockerPluginExtension extension) {
        String imageNameWithTag = "$imageName:$tag"

        Image image = client.images().find { imageNameWithTag in it.tags }
        client.tagImage(image.id, imageName, newTag, force)
    }

}
