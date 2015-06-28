package com.energizedwork.gradle

import com.energizedwork.gradle.docker.task.ListContainersTask
import com.energizedwork.gradle.docker.task.ListImagesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

class DockerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def extension = project.extensions.create(DockerPluginExtension.NAME, DockerPluginExtension, project)

        project.afterEvaluate {
            extension.validate()
        }

        project.tasks.whenTaskAdded { Task task ->
            extension.containers.notifyPlansOfTask task
        }

        project.tasks.create ListContainersTask.TASK_NAME, ListContainersTask
        project.tasks.create ListImagesTask.TASK_NAME, ListImagesTask
    }

}
