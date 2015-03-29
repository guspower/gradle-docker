package com.energizedwork.docker

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


@EqualsAndHashCode
@ToString(includePackage = false, includeNames = true)
class Image {

    String description
    String name

    Date created
    String id
    String parentId
    List<String> tags
    Integer size
    Integer virtualSize

    void setCreated(String utcDate) {
        created = new Date(utcDate.toInteger() * 1000)
    }

}
