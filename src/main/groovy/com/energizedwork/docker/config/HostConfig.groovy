package com.energizedwork.docker.config

class HostConfig extends Config {

    // A list of volume bindings for this container. Each volume binding is a string of the form container_path (to create a new volume for the container), host_path:container_path (to bind-mount a host path into the container), or host_path:container_path:ro (to make the bind-mount read-only inside the container).
    List<String> Binds

    // A list of links for the container. Each link entry should be of of the form "container_name:alias".
    List<String> Links

    // LXC specific configurations. These configurations will only work when using the lxc execution driver.
    String LxcConf

    // A map of exposed container ports and the host port they should map to. It should be specified in the form { <port>/<protocol>: [{ "HostPort": "<port>" }] } Take note that port is specified as a string and not an integer value.
    Map<String, Object> PortBindings

    // Allocates a random host port for all of a container's exposed ports. Specified as a boolean value.
    Boolean PublishAllPorts

    // Gives the container full access to the host. Specified as a boolean value.
    Boolean Privileged

    // Mount the container's root filesystem as read only. Specified as a boolean value.
    Boolean ReadonlyRootfs

    // A list of dns servers for the container to use.
    List<String> Dns

    // A list of DNS search domains
    List<String> DnsSearch

    // A list of hostnames/IP mappings to be added to the container's /etc/hosts file. Specified in the form ["hostname:IP"].
    List<String> ExtraHosts

    // A list of volumes to inherit from another container. Specified in the form <container name>[:<ro|rw>]
    List<String> VolumesFrom

    // A list of kernel capabilties to add to the container.
    List<String> CapAdd

    // A list of kernel capabilties to drop from the container.
    List<String> Capdrop

    RestartPolicy RestartPolicy

    // Sets the networking mode for the container. Supported values are: bridge, host, and container:<name|id>
    String NetworkMode

    // A list of devices to add to the container specified in the form { "PathOnHost": "/dev/deviceName", "PathInContainer": "/dev/deviceName", "CgroupPermissions": "mrw"}
    List<Device> Devices

    // A list of ulimits to be set in the container, specified as { "Name": <name>, "Soft": <soft limit>, "Hard": <hard limit> }, for example: Ulimits: { "Name": "nofile", "Soft": 1024, "Hard", 2048 }}
    List<Ulimit> Ulimits

    LogConfig LogConfig

    // Path to cgroups under which the cgroup for the container will be created. If the path is not absolute, the path is considered to be relative to the cgroups path of the init process. Cgroups will be created if they do not already exist.
    String CgroupParent

}

