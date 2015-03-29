package com.energizedwork.gradle

import com.energizedwork.gradle.docker.ListContainersTask
import com.energizedwork.gradle.docker.ListImagesTask
import org.gradle.api.Plugin
import org.gradle.api.Project


class DockerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("docker", DockerPluginExtension)

        project.tasks.create 'containers', ListContainersTask
        project.tasks.create 'images', ListImagesTask
    }

}
