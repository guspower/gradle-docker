package com.energizedwork.docker.event.container

import com.energizedwork.docker.Container
import com.energizedwork.docker.event.DockerEvent
import groovyx.net.http.ContentType

import static groovyx.net.http.ContentType.JSON


class CreateContainer extends DockerEvent {

    String path = '/containers/create'

    String hostName      = ''
    String domainName    = ''
    String imageName
    List<String> command = []
    boolean networkDisabled

    HostConfig hostConfig = new HostConfig()

    Map getBody() {
        [
            Hostname:        hostName,
            Image:           imageName,
            Cmd:             command,
            NetworkDisabled: networkDisabled,
            HostConfig: [
                    NetworkMode: hostConfig.networkMode
            ]
        ]
    }

    Map<String, String> getQuery() {
        [name: hostName]
    }

    ContentType getRequestContentType() { JSON }

    boolean noContent = false

    def decodeResponse(def response) {
        new Container(id: response.Id)
    }

    /*
        {
         "Hostname": "",
         "Domainname": "",
         "User": "",
         "Memory": 0,
         "MemorySwap": 0,
         "CpuShares": 512,
         "Cpuset": "0,1",
         "AttachStdin": false,
         "AttachStdout": true,
         "AttachStderr": true,
         "Tty": false,
         "OpenStdin": false,
         "StdinOnce": false,
         "Env": null,
         "Cmd": [
                 "date"
         ],
         "Entrypoint": "",
         "Image": "base",
         "Volumes": {
                 "/tmp": {}
         },
         "WorkingDir": "",
         "NetworkDisabled": false,
         "MacAddress": "12:34:56:78:9a:bc",
         "ExposedPorts": {
                 "22/tcp": {}
         },
         "SecurityOpts": [""],
         "HostConfig": {
           "Binds": ["/tmp:/tmp"],
           "Links": ["redis3:redis"],
           "LxcConf": {"lxc.utsname":"docker"},
           "PortBindings": { "22/tcp": [{ "HostPort": "11022" }] },
           "PublishAllPorts": false,
           "Privileged": false,
           "Dns": ["8.8.8.8"],
           "DnsSearch": [""],
           "ExtraHosts": null,
           "VolumesFrom": ["parent", "other:ro"],
           "CapAdd": ["NET_ADMIN"],
           "CapDrop": ["MKNOD"],
           "RestartPolicy": { "Name": "", "MaximumRetryCount": 0 },
           "NetworkMode": "bridge",
           "Devices": []
        }
    }
     */


    static class HostConfig {

        String networkMode = 'bridge'

    }

}
