package com.energizedwork.gradle

import com.energizedwork.gradle.docker.task.ListContainersTask
import com.energizedwork.gradle.docker.task.ListImagesTask
import org.gradle.api.Plugin
import org.gradle.api.Project


class DockerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def extension = project.extensions.create(DockerPluginExtension.NAME, DockerPluginExtension, project)

        project.afterEvaluate {
            extension.validate()
        }

        project.tasks.create 'containers', ListContainersTask
        project.tasks.create 'images', ListImagesTask
    }

}
