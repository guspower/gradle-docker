package com.energizedwork.docker

import groovy.transform.ToString


@ToString(includePackage = false, includeFields = true)
class ContainerDetail {

    String json

    String id
    Date created
    String path
    String imageName

    List<String> args

    Config config
    State state
    NetworkSettings networkSettings


    /**
     *
     "SysInitPath": "/home/kitty/go/src/github.com/docker/docker/bin/docker",
     "ResolvConfPath": "/etc/resolv.conf",
     "Volumes": {},
     "HostConfig": {
     "Binds": null,
     "ContainerIDFile": "",
     "LxcConf": [],
     "Privileged": false,
     "PortBindings": {
     "80/tcp": [
     {
     "HostIp": "0.0.0.0",
     "HostPort": "49153"
     }
     ]
     },
     "Links": ["/name:alias"],
     "PublishAllPorts": false,
     "CapAdd": ["NET_ADMIN"],
     "CapDrop": ["MKNOD"]
     }
     */

    @ToString(includePackage = false, includeFields = true)
    static class Config {
        String hostName
        String user
        String env
        List<String> command
        String dns
        String imageName

//        "Hostname": "4fa6e0f0c678",
//        "User": "",
//        "Memory": 0,
//        "MemorySwap": 0,
//        "AttachStdin": false,
//        "AttachStdout": true,
//        "AttachStderr": true,
//        "PortSpecs": null,
//        "Tty": false,
//        "OpenStdin": false,
//        "StdinOnce": false,
//        "Env": null,
//        "Cmd": [
//        "date"
//        ],
//        "Dns": null,
//        "Image": "base",
//        "Volumes": {},
//        "VolumesFrom": "",
//        "WorkingDir": ""
    }

    @ToString(includePackage = false, includeFields = true)
    static class State {
        boolean running
        Integer pid
        Integer exitCode
        boolean paused

//        "Running": false,
//        "Pid": 0,
//        "ExitCode": 0,
//        "StartedAt": "2013-05-07T14:51:42.087658+02:01360",
//        "Ghost": false
    }

    @ToString(includePackage = false, includeFields = true)
    static class NetworkSettings {
        String ip
        String gateway
        String bridge

//        "IpAddress": "",
//        "IpPrefixLen": 0,
//        "Gateway": "",
//        "Bridge": "",
//        "PortMapping": null
    }

}
