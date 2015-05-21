package com.energizedwork.docker.config

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
class ContainerConfig extends Config {

    String Hostname
    String Domainname
    String User

    // Memory limit in bytes.
    Integer Memory

    // Total memory limit (memory + swap); set -1 to disable swap, always use this with memory, and make the value larger than memory.
    Integer MemorySwap

    // An integer value containing the CPU Shares for container (ie. the relative weight vs othercontainers).
    Integer CpuShares

    // String value containg the cgroups CpusetCpus to use.
    String CpusetCpus

    // Boolean value, attaches to stdin.
    Boolean AttachStdin

    // Boolean value, attaches to stdout.
    Boolean AttachStdout

    // Boolean value, attaches to stderr.
    Boolean AttachStderr

    // Boolean value, Attach standard streams to a tty, including stdin if it is not closed.
    Boolean Tty

    // Boolean value, opens stdin,
    Boolean OpenStdin

    // Boolean value, close stdin after the 1 attached client disconnects.
    Boolean StdinOnce

    // A list of environment variables in the form of VAR=value
    Map<String, String> Env

    // Adds a map of labels that to a container. To specify a map: {"key":"value"[,"key2":"value2"]}
    Map<String, String> Labels

    // Command to run specified as a string or an array of strings.
    List<String> Cmd

    // Set the entrypoint for the container a a string or an array of strings
    List<String> Entrypoint

    // String value containing the image name to use for the container
    String Image

    // An object mapping mountpoint paths (strings) inside the container to empty objects.
    Map <String, Object> Volumes

    // A string value containing the working dir for commands to run in.
    String WorkingDir

    // Boolean value, when true disables neworking for the container
    Boolean NetworkDisabled

    // An object mapping ports to an empty object in the form of: "ExposedPorts": { "<port>/<tcp|udp>: {}" }
    Map <String, Object> ExposedPorts

    // A list of string values to customize labels for MLS systems, such as SELinux.
    List<String> SecurityOpts

    HostConfig HostConfig

}
