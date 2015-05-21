package com.energizedwork.docker.config

abstract class Config {

    Map asMap() {
        def result = [:]

        this.properties.findAll { it.value != null && it.key != 'class' }.each { String key, value ->
            if(value instanceof Config) {
                result.put(key.capitalize(), value.asMap())
            } else {
                result.put(key.capitalize(), value)
            }
        }

        result
    }

}