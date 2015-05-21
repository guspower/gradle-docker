package com.energizedwork.gradle

import com.energizedwork.docker.AsyncTestUtils
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection

import spock.lang.Specification
import spock.lang.Unroll

class DockerPluginUsageSpec extends Specification implements AsyncTestUtils {

    @Unroll('can run #task')
    def 'can run gradle project'() {
        given:
            ProjectConnection connection = GradleConnector.newConnector()
                .forProjectDirectory(new File('src/test/resources/project/basic'))
                .connect()

        and:
            def stdout = new ByteArrayOutputStream()

        when:
            def build = connection.newBuild()
            build.standardOutput = stdout
            build.forTasks(task).run()

        then:
            stdout.toString().contains 'BUILD SUCCESSFUL'
            println stdout

        where:
            task << ['containers', 'images']
    }


}
