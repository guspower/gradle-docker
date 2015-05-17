package com.energizedwork.gradle.docker.config

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includePackage = false)
class Store {

    private String _host
    private String _extension

    private File _file
    String password = 'password'

    Store(String host, String extension) {
        _host = host
        _extension = extension
    }

    File getFile() { _file ?: new File(defaultStorePath, "client.$_extension")}
    void setFile(File file) { _file = file }

    private File getDefaultStorePath() {
        new File("${System.getenv('HOME')}/.docker/$_host")
    }

}
