package com.energizedwork.docker.http

import com.energizedwork.docker.content.TarSupport
import com.energizedwork.docker.event.DockerEvent
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.RESTClient
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory

import java.security.KeyStore


class Server {

    @Delegate
    private RESTClient _server
    private Closure requestFailure = { resp -> println resp.statusLine }

    Server() {
        this(new SSLClientCertificate())
    }

    Server(String host) {
        this(new SSLClientCertificate(host: host))
    }

    Server(Config config) {
        _server = new RESTClient(config.baseUrl)
        config.configure(_server)

        new TarSupport().configure(_server)
    }

    def get(DockerEvent event) {
        event.decodeResponse get(event.requestProperties).data
    }

    def post(DockerEvent event) {
        event.decodeResponse post(event.requestProperties).data
    }

    def request(DockerEvent event) {
        def result

        request(event.method, event.contentType) { request ->
            uri.path = event.path

            if(event.query) { uri.query = event.query }
            if(event.body) { body = event.body }
            if(event.contentType) { contentType = event.contentType }
            if(event.requestContentType) { requestContentType = event.requestContentType }

            if(event.noContent) {
                response.'204' = { response, reader -> }
            } else {
                response.success = { response, stream ->
                    result = event.decodeResponse(response, stream)
                }
            }
            response.failure = requestFailure
        }

        result
    }

    abstract static class Config {

        abstract String getBaseUrl()
        abstract void configure(HTTPBuilder server)

    }

    final static class SSLClientCertificate extends Config {

        String host           = 'localhost'
        String password       = 'password'
        int port              = 664
        String protocol       = 'https'
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


}
