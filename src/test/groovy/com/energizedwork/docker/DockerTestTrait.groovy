package com.energizedwork.docker

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import com.energizedwork.docker.event.container.CreateContainer
import com.energizedwork.docker.http.DockerServerConfig
import org.slf4j.LoggerFactory

trait DockerTestTrait implements AsyncTestUtils {

    final String TEST_CONTAINER_NAME = 'docker-gradle-test-container'

    Client client
    TestCondition created, started, stopped, deleted, paused

    void setupClient() {
        LoggerContext context = (LoggerContext) LoggerFactory.ILoggerFactory
        Logger logger = context.getLogger(Logger.ROOT_LOGGER_NAME)
        logger.level = Level.WARN

        def config = new DockerServerConfig(
            url: new URL('https://spinship:664'),
            keystoreFile: new File("${System.getenv('HOME')}/.docker/spinship/client.jks"),
            keystorePassword: 'password',
            truststoreFile: new File("${System.getenv('HOME')}/.docker/spinship/client.jts"),
            truststorePassword: 'password'
        )

        client = new Client(config)
    }

    void cleanupTestContainersAndImages() {
        String hostnamePattern = generateUniqueName()[0..-7]

        client.containers().findAll { Container container ->
            container.names.find { it.contains(hostnamePattern) || it.contains(TEST_CONTAINER_NAME) }
        }.each { removeContainer it }

        client.images().findAll { Image image ->
            image.tags.find { it.contains(hostnamePattern) }
        }.each { removeImage it }
    }

    void initConditions(String id) {
        created = condition('container created', { client.containers().find { it.id == id } })
        started = condition('container started', { client.inspect(id).state.running })
        stopped = condition('container stopped', { !client.inspect(id).state.running })
        deleted = condition('container deleted', { !client.containers().find { it.id == id } })
        paused  = condition('container paused',  { client.inspect(id).state.paused })
    }

    String generateUniqueName() {
        "${this.class.simpleName}-${System.currentTimeMillis()}"
    }

    ContainerDetail createAndStart(CreateContainer create) {
        Container container = client.create(create)
        String id = container.id
        initConditions id
        waitFor created
        client.start id
        waitFor started
        client.inspect id
    }

    void removeContainer(Container container) {
        println "Cleaning up test container [$container]..."

        initConditions container.id
        client.kill container.id
        waitFor stopped
        client.delete container.id
        waitFor deleted
    }

    void removeImage(Image image) {
        println "Cleaning up test image [$image]..."

        client.deleteImage image.id
    }

}
