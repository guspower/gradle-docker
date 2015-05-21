package com.energizedwork.docker.config

/**
 * / - A list of devices to add to the container specified in the form { "PathOnHost": "/dev/deviceName", "PathInContainer": "/dev/deviceName", "CgroupPermissions": "mrw"}
 */
class Device {

    String PathOnHost
    String PathInContainer
    String CgroupPermissions

}
