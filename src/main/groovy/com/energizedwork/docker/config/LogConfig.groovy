package com.energizedwork.docker.config

/**
 *  - Logging configuration to container, format { "Type": "<driver_name>", "Config": {"key1": "val1"}} Available types:json-file,syslog,none.json-file` logging driver.
 */
class LogConfig {

    String Type
    Map<String, String> Config

}