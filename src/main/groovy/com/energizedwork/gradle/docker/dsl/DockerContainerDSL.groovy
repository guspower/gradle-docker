package com.energizedwork.gradle.docker.dsl

import org.gradle.api.Project
import org.gradle.api.Task

class DockerContainerDSL {

    private Project _project

    DockerContainerDSL(Project project) {
        this._project = project
    }

    List<DockerContainerPlan> plans = []

    void container(Closure closure) {
        def plan = new DockerContainerPlan(_project)
        _project.configure plan, closure
        plans << plan
    }

    void notifyPlansOfTask(Task task) {
        plans.each { DockerContainerPlan plan ->
            if(task.name == plan.before) {
                plan.before task
            }
            if(task.name == plan.after) {
                plan.after task
            }
        }
    }

    Object getAt(String name) {
        DockerContainerPlan plan = plans.find { it.name == name }
        plan?.info
    }

}
