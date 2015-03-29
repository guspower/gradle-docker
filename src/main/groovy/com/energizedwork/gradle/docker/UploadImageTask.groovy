package com.energizedwork.gradle.docker

import com.energizedwork.docker.Client
import com.energizedwork.docker.event.image.UploadImage
import com.energizedwork.gradle.DockerPluginExtension

class UploadImageTask extends AbstractDockerTask {

    File file
    String imageName
    String tag

    void run(Client client, DockerPluginExtension extension) {
        if(!file.exists()) {
            throw new FileNotFoundException("No docker image found at ${file}")
        }

        def upload = new UploadImage(file:file, name:imageName, tag:tag)
        client.uploadImage upload
    }

}
