package com.energizedwork.gradle

import org.gradle.api.Project
import org.gradle.api.ProjectEvaluationListener
import org.gradle.api.internal.project.AbstractProject
import org.gradle.api.internal.project.ProjectStateInternal
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification
import spock.lang.Unroll

class DockerPluginExtensionSpec extends Specification {

    @Unroll('extension defaults to #url given host #name on port #port with protocol[#protocol]')
    def 'extension defaults'() {
        given:
            DockerPluginExtension extension = newExtension()

        when:
            configureHost extension, [name: name, port: port, protocol: protocol]

        then:
            extension.host.url == url.toURL()

        where:
            port | name | protocol | url
            null | null | null     | 'http://localhost:2375'
            1234 | null | null     | 'http://localhost:1234'
            null | 'hn' | null     | 'http://hn:2375'
            null | null | 'https'  | 'https://localhost:2376'
            1234 | 'hn' | 'http'   | 'http://hn:1234'
            1234 | 'hn' | 'https'  | 'https://hn:1234'
    }

    def 'extension throws illegal arg given invalid host'() {
        given:
            def extension = newExtension()

        when:
            configureHost extension, [name: ':']
            extension.validate()

        then:
            thrown IllegalArgumentException
    }

    private configureHost(DockerPluginExtension extension, Map values) {
        extension.host {
            values.each { key, value ->
                if(value) { setProperty(key, value) }
            }
        }
    }

    private DockerPluginExtension newExtension() {
        def project = newProject()

        project.extensions.findByName(DockerPluginExtension.NAME)
    }

    private Project newProject() {
        def builder = new ProjectBuilder()

        Project project = builder.build()
        project.pluginManager.apply(DockerPlugin)

        project
    }

    /**
     * GP: HACK: See http://discuss.gradle.org/t/plugin-development-how-to-programmatically-run-a-gradle-project-in-the-unit-tests/4761
     * Reason: no supported public method of setting project evaluation state
     */
    private void evaluate(Project project) {
        def state = new ProjectStateInternal()
        state.executed()
        ProjectEvaluationListener listener = ((AbstractProject)project).projectEvaluationBroadcaster
        listener.afterEvaluate project, state
    }

}
