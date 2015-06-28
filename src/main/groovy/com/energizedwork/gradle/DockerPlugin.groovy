package com.energizedwork.gradle

import com.energizedwork.docker.ContainerDetail
import com.energizedwork.gradle.docker.task.CreateContainerTask
import com.energizedwork.gradle.docker.task.DeleteContainerTask
import com.energizedwork.gradle.docker.task.InspectContainerTask
import com.energizedwork.gradle.docker.task.ListContainersTask
import com.energizedwork.gradle.docker.task.ListImagesTask
import com.energizedwork.gradle.docker.task.StartContainerTask
import com.energizedwork.gradle.docker.task.StopContainerTask
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
            extension.containers.containers.findAll { DockerContainerPlan plan -> task.name == plan.before }.each { DockerContainerPlan plan ->
                def create = project.tasks.create(taskName("create", "before", plan), CreateContainerTask)
                create.hostname = plan.name
                create.command = plan.command
                create.image = plan.image

                def start  = project.tasks.create(taskName("start", "before", plan), StartContainerTask)
                start.hostname = plan.name
                start.dependsOn create

                def inspect = project.tasks.create(taskName("inspect", "before", plan), InspectContainerTask)
                inspect.hostname = plan.name
                inspect.action = { DockerContainerPlan p, ContainerDetail detail ->
                    p.info = detail
                }.curry(plan)
                inspect.dependsOn start
                task.dependsOn inspect
            }

            extension.containers.containers.findAll { DockerContainerPlan plan -> task.name == plan.after }.each { DockerContainerPlan plan ->
                def stop = project.tasks.create(taskName("stop", "after", plan), StopContainerTask)
                stop.hostname = plan.name

                def delete = project.tasks.create(taskName("delete", "after", plan), DeleteContainerTask)
                delete.hostname = plan.name
                delete.dependsOn stop
                task.finalizedBy delete
            }
        }

        project.tasks.create 'containers', ListContainersTask
        project.tasks.create 'images', ListImagesTask
    }

    private String taskName(String action, String when, DockerContainerPlan plan) {
        "${action}-docker-container-${plan.name}-${when}-${plan.before}"
    }

}
