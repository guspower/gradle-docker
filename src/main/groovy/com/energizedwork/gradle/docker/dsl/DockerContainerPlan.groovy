package com.energizedwork.gradle.docker.dsl

import com.energizedwork.docker.ContainerDetail
import com.energizedwork.gradle.docker.task.CreateContainerTask
import com.energizedwork.gradle.docker.task.DeleteContainerTask
import com.energizedwork.gradle.docker.task.InspectContainerTask
import com.energizedwork.gradle.docker.task.StartContainerTask
import com.energizedwork.gradle.docker.task.StopContainerTask
import groovy.transform.ToString
import org.gradle.api.Project
import org.gradle.api.Task

@ToString(includePackage = false, includeNames = true)
class DockerContainerPlan {

    private Project _project

    String name
    String before
    String after
    boolean create = true
    boolean delete = true
    String image = 'busybox:latest'
    List<String> command = ['/bin/sh']

    ContainerDetail info

    DockerContainerPlan(Project project) {
        this._project = project
    }

    void before(Task task) {
        String when = 'before'

        def create = create(when, task)
        def start = start(when, task)
        start.dependsOn create

        def inspect = inspect(when, task)
        inspect.dependsOn start

        task.dependsOn inspect
    }

    void after(Task task) {
        String when = 'after'

        def stop = stop(when, task)
        def delete = delete(when, task)
        delete.dependsOn stop

        task.finalizedBy delete
    }

    private CreateContainerTask create(String when, Task task) {
        def create = createTask('create', when, task, CreateContainerTask)
        create.command = command
        create.image = image

        create
    }

    private StartContainerTask start(String when, Task task) {
        createTask('start', when, task, StartContainerTask)
    }

    private InspectContainerTask inspect(String when, Task task) {
        def inspect = createTask('inspect', when, task, InspectContainerTask)
        inspect.action = { DockerContainerPlan plan, ContainerDetail detail ->
            plan.info = detail
        }.curry(this)

        inspect
    }

    private StopContainerTask stop(String when, Task task) {
        createTask("stop", when, task, StopContainerTask)
    }

    private DeleteContainerTask delete(String when, Task task) {
        createTask("delete", when, task, DeleteContainerTask)
    }

    private <T extends Task> T createTask(String shortName, String when, Task task, Class<T> clazz) {
        def t = _project.tasks.create(taskName(shortName, when, task), clazz)
        t.hostname = name

        t
    }

    private String taskName(String action, String when, Task task) {
        "${action}-docker-container-${name}-${when}-${task.name}"
    }

}
