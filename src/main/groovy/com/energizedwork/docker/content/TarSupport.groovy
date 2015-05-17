package com.energizedwork.docker.content

import groovyx.net.http.HTTPBuilder
import org.apache.http.HttpEntity
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.entity.ContentType
import org.apache.http.entity.FileEntity


class TarSupport {

    final static String CONTENT_TYPE_NAME = 'application/x-tar'
    final static ContentType CONTENT_TYPE = ContentType.create(CONTENT_TYPE_NAME)

    HttpEntity encodeTarFile(Object data) throws UnsupportedEncodingException {
        encode data
    }

    static configure(HTTPBuilder http) {
        http.encoder."$CONTENT_TYPE_NAME" = new TarSupport().&encodeTarFile
    }

    private HttpEntity encode(File file) {
        new FileEntity(file, CONTENT_TYPE)
    }

    private HttpEntity encode(byte[] data) {
        new ByteArrayEntity(data, CONTENT_TYPE)
    }

    private HttpEntity encode(Object unknown) {
        throw new IllegalArgumentException("Cannot encode ${data.class.name} as a $CONTENT_TYPE file")
    }

}
