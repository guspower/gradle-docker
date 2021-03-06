package com.energizedwork.gradle.docker.config

import com.energizedwork.docker.http.DockerServerConfig
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import org.gradle.api.Project

@EqualsAndHashCode
@ToString(includePackage = false)
class Host {

    final static String DEFAULT_HOST = 'localhost'

    final static int DEFAULT_PORT = 2375
    final static int DEFAULT_TLS_PORT = 2376

    private Project _project

    private String _alias
    private String _name
    private String _protocol
    private URL _url
    private Integer _port

    Tls tls

    Host(Project project) {
        _project = project
    }

    void tls(Closure closure) {
        tls = new Tls(_project, name)
        _project.configure tls, closure
    }

    String getAlias() { _alias ?: name }
    void setAlias(String alias) { _alias = alias }

    String getName() { _name ?: DEFAULT_HOST }
    void setName(String name) { _name = name }

    Integer getPort() { _port ?: isSSL() ? DEFAULT_TLS_PORT: DEFAULT_PORT }
    void setPort(Integer port) { _port = port }

    String getProtocol() { _protocol ?: tls ? 'https': 'http' }
    void setProtocol(String protocol) { _protocol = protocol }

    URL getUrl() throws MalformedURLException { _url ?: buildUrl(name, port) }
    void setUrl(String urlString) throws MalformedURLException {
        _url = urlString.toURL()
        name     = _url.host
        port     = _url.port
        protocol = _url.protocol
    }

    boolean isSSL() { protocol == 'https' }

    def asType(Class clazz) {
        if(clazz == DockerServerConfig) {
            def config = new DockerServerConfig(url: url)
            if(tls) {
                if(tls.keystore) {
                    config.keystoreFile = tls.keystore.file
                    config.keystorePassword = tls.keystore.password
                }
                if(tls.truststore) {
                    config.truststoreFile = tls.truststore.file
                    config.truststorePassword = tls.truststore.password
                }
            }
            config
        }
    }

    private URL buildUrl(String name, Integer port) throws MalformedURLException {
        def result = new StringBuilder()

        result << protocol
        result << '://'
        result << name
        result << ':'
        result << port

        result.toString().toURL()
    }

}
