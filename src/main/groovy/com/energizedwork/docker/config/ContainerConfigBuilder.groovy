package com.energizedwork.docker.config

class ContainerConfigBuilder {

    private ContainerConfig config

    static ContainerConfigBuilder newConfig() {
        new ContainerConfigBuilder()
    }

    private ContainerConfigBuilder() {
        this.config = new ContainerConfig(NetworkDisabled: false)

        config.HostConfig = new HostConfig(NetworkMode: 'bridge')
    }

    Map config() {
        config.asMap()
    }

    ContainerConfigBuilder host(String hostName) {
        config.Hostname = hostName
        this
    }

    ContainerConfigBuilder image(String imageName) {
        config.Image = imageName
        this
    }

    ContainerConfigBuilder command(List<String> commands) {
        config.Cmd = commands
        this
    }

}
