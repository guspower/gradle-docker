package com.energizedwork.docker.http

import groovyx.net.http.HTTPBuilder
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory

import java.security.KeyStore

class TLSConfig implements DockerServerConfig {

    String host           = 'localhost'
    int    port           = 664
    String protocol       = 'https'

    String password       = 'password'
    String keystorePath
    String truststorePath

    @Override
    String getBaseUrl() {
        "$protocol://$host:$port"
    }

    @Override
    void configure(HTTPBuilder server) {
        if(!keystorePath)   { keystorePath   = "${System.getenv('HOME')}/.docker/${host}/client.jks" }
        if(!truststorePath) { truststorePath = "${System.getenv('HOME')}/.docker/${host}/client.jts" }

        configureSSLWithClientCertificate server
    }

    private void configureSSLWithClientCertificate(HTTPBuilder server) {
        KeyStore keyStore = loadKeyStore(keystorePath, password)
        KeyStore trustStore = loadKeyStore(truststorePath, password)

        registerSSLFactory server, keyStore, trustStore
    }

    private void registerSSLFactory(HTTPBuilder server, KeyStore keyStore, KeyStore trustStore) {
        def sslFactory = new SSLSocketFactory(keyStore, password, trustStore)
        def scheme = new Scheme(protocol, sslFactory, port)
        server.client.connectionManager.schemeRegistry.register(scheme)
    }

    private KeyStore loadKeyStore(String path, String password) {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.defaultType)
        new File(path).withInputStream {
            keyStore.load(it, password.toCharArray())
        }
        keyStore
    }

}
