package com.energizedwork.docker.event.container

import com.energizedwork.docker.ContainerDetail
import com.energizedwork.docker.event.IdEvent
import groovy.json.JsonBuilder
import groovyx.net.http.ContentType

import static groovyx.net.http.ContentType.JSON


class InspectContainer extends IdEvent {

    InspectContainer(String id) {
        this.id = id
    }

    ContentType getRequestContentType() { JSON }

    String getPath() { "/containers/$id/json" }

    def decodeResponse(def response) {
        new ContainerDetail(
            json      : new JsonBuilder(response).toString(),

            id        : response.Id,
            path      : response.Path,
            imageName : response.Image,
            args      : response.Args,
            config: new ContainerDetail.Config(
                    hostName  : response.Config.Hostname,
                    user      : response.Config.User,
                    env       : response.Config.Env,
                    command   : response.Config.Cmd,
                    dns       : response.Config.Dns,
                    imageName : response.Config.Image
            ),
            state: new ContainerDetail.State(
                    exitCode : response.State.ExitCode,
                    paused   : response.State.Paused,
                    pid      : response.State.Pid,
                    running  : response.State.Running
            ),
            networkSettings: new ContainerDetail.NetworkSettings(
                    ip      : response.NetworkSettings.IPAddress,
                    gateway : response.NetworkSettings.Gateway,
                    bridge  : response.NetworkSettings.Bridge
            )
        )
    }

}
