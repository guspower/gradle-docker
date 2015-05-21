package com.energizedwork.docker.config

import spock.lang.Specification

class ContainerConfigBuilderSpec extends Specification {

    def 'can create basic configuration'() {
        given:
            def builder = ContainerConfigBuilder.newConfig()

        when:
            builder.host(hostName).image(imageName).command(command)

        then:
            builder.config() == [
                Hostname:        hostName,
                Image:           imageName,
                Cmd:             command,
                NetworkDisabled: false,
                HostConfig: [
                    NetworkMode: 'bridge'
                ]
            ]

        where:
            hostName  = 'somehost'
            imageName = 'someimage:tag'
            command   = ['/bin/bash', '-c', 'echo ohai']
    }


}
