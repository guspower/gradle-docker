package com.energizedwork.docker

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


@EqualsAndHashCode
@ToString(includePackage = false, includeNames = true)
class Container {

    String        command
    Date          created
    String        id
    String        image
    List<String>  names
    List<Integer> ports
    String        status

    void setCreated(String utcDate) {
        created = new Date(utcDate.toInteger() * 1000)
    }

}
