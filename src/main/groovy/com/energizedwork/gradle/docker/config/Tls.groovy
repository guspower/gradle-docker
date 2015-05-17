package com.energizedwork.gradle.docker.config

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.gradle.api.Project

@EqualsAndHashCode
@ToString(includePackage = false)
class Tls {

    private Project _project
    private String _host

    Store keystore
    Store truststore

    Tls(Project project, String host) {
        _project = project
        _host = host
    }

    void keystore(Closure closure) {
        keystore = new Store(_host, 'jks')
        _project.configure keystore, closure
    }

    void truststore(Closure closure) {
        truststore = new Store(_host, 'jts')
        _project.configure truststore, closure
    }

}
