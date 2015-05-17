package com.energizedwork.gradle.config

import com.energizedwork.gradle.docker.config.Host
import org.gradle.api.Project
import spock.lang.Specification

class DockerConfigSpec extends Specification {

    def 'default configuration'() {
        given:
            def project = Mock(Project)

        when:
            def host = new Host(project)

        then:
            'http://localhost:2375' == host.url.toString()
            'localhost'             == host.name
            'localhost'             == host.alias
            Host.DEFAULT_PORT       == host.port
            'http'                  == host.protocol
            ! host.tls
    }

    def 'default tls configuration'() {
        given:
            def project = Mock(Project)

        when:
            def host = new Host(project)

        and:
            host.tls {}

        then:
            'https://localhost:2376' == host.url.toString()
            Host.DEFAULT_TLS_PORT    == host.port
            'https'                  == host.protocol
            host.tls

        and:
            1 * project.configure(_, _)
    }

    def 'default keystore configuration'() {
        given:
            def project = Mock(Project)

        when:
            def host = new Host(project)

        and:
            host.tls {}
            host.tls.keystore {}

        then:
            host.tls.keystore
            ! host.tls.truststore

        and:
            'password'                       == host.tls.keystore.password
            "$home/.docker/$name/client.jks" == host.tls.keystore.file.absolutePath

        and:
            2 * project.configure(_, _)

        where:
            home = System.getenv('HOME')
            name = 'localhost'
    }

    def 'default truststore configuration'() {
        given:
            def project = Mock(Project)

        when:
            def host = new Host(project)

        and:
            host.tls {}
            host.tls.truststore {}

        then:
            host.tls.truststore
            ! host.tls.keystore

        and:
            'password'                       == host.tls.truststore.password
            "$home/.docker/$name/client.jts" == host.tls.truststore.file.absolutePath

        and:
            2 * project.configure(_, _)

        where:
            home = System.getenv('HOME')
            name = 'localhost'
    }

    def 'http url configuration'() {
        given:
            def project = Mock(Project)

        when:
            def host = new Host(project)

        and:
            host.url = 'http://some.host:12345'

        then:
            'http://some.host:12345' == host.url.toString()
            'some.host'              == host.name
            'some.host'              == host.alias
            12345                    == host.port
            'http'                   == host.protocol
            ! host.tls
    }

    def 'https url configuration'() {
        given:
            def project = Mock(Project)

        when:
            def host = new Host(project)

        and:
            host.url = 'https://some.host:12345'

        then:
            'https://some.host:12345' == host.url.toString()
            'some.host'               == host.name
            'some.host'               == host.alias
            12345                     == host.port
            'https'                   == host.protocol
            ! host.tls
    }

    def 'alias configuration'() {
        given:
            def project = Mock(Project)

        when:
            def host = new Host(project)
            host.alias = 'some.alias'
            host.name  = 'some.host'

        then:
            'http://some.host:2375' == host.url.toString()
            'some.host'             == host.name
            'some.alias'            == host.alias
    }
}
