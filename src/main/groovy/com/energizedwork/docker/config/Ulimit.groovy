package com.energizedwork.docker.config

/**
 *  - A list of ulimits to be set in the container, specified as { "Name": <name>, "Soft": <soft limit>, "Hard": <hard limit> }, for example: Ulimits: { "Name": "nofile", "Soft": 1024, "Hard", 2048 }}
 */
class Ulimit {

    String Name
    Integer Soft
    Integer Hard

}