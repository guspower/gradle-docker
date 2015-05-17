package com.energizedwork.docker.http

import groovyx.net.http.HTTPBuilder
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory

import java.security.KeyStore

class DockerServerConfig {

    URL url

    File keystoreFile
    String keystorePassword

    File truststoreFile
    String truststorePassword

    String getBaseUrl() { url.toString() }

    void configure(HTTPBuilder server) {
        if(url.protocol == 'https' && keystoreFile) {
            configureSSLWithClientCertificate server
        }
    }

    private void configureSSLWithClientCertificate(HTTPBuilder server) {
        KeyStore keystore = loadKeyStore(keystoreFile, keystorePassword)
        KeyStore truststore

        if(truststoreFile) {
            truststore = loadKeyStore(truststoreFile, truststorePassword)
        }

        registerSSLFactory server, keystore, keystorePassword, truststore
    }

    private void registerSSLFactory(HTTPBuilder server, KeyStore keyStore, String password, KeyStore trustStore) {
        def sslFactory = new SSLSocketFactory(keyStore, password, trustStore)
        def scheme = new Scheme(url.protocol, sslFactory, url.port)
        server.client.connectionManager.schemeRegistry.register(scheme)
    }

    private KeyStore loadKeyStore(File store, String password) {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.defaultType)
        store.withInputStream {
            keyStore.load(it, password.toCharArray())
        }
        keyStore
    }

}
