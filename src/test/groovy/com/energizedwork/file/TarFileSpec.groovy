package com.energizedwork.file

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import spock.lang.Shared
import spock.lang.Specification

class TarFileSpec extends Specification {

    private @Shared List<File> _files = []

    def cleanup() {
        _files.each { File file ->
            if(file.exists()) { file.delete() }
        }
        _files.clear()
    }

    def 'can create valid tar file given entry name and content'() {
        when:
            def tar = new TarFile(tarFile)
            tar.add entryName, data
            tar.writeOut()

        then:
            def actual = new TarArchiveInputStream(tarFile.newInputStream())
            def entry  = actual.nextTarEntry

            entry.name == entryName
            entry.size == data.size()

        when:
            def bytes   = new byte[data.size()]
            actual.read(bytes)

        then:
            new String(bytes) == data

        where:
            data      = 'some file content'
            entryName = 'some-tar-entry'
            tarFile   = newFile('.tar')
    }

    def 'can create valid tar file given file'() {
        when:
            def tar = new TarFile(tarFile)
            tar.add dataFile, entryName
            tar.writeOut()

        then:
            def actual = new TarArchiveInputStream(tarFile.newInputStream())
            def entry  = actual.nextTarEntry

            entry.name == entryName
            entry.size == data.size()

        when:
            def bytes   = new byte[data.size()]
            actual.read(bytes)

        then:
            new String(bytes) == data

        where:
            data      = 'some file content'
            dataFile  = newFile('.txt', data)
            entryName = 'some-tar-entry'
            tarFile   = newFile('.tar')
    }

    def 'can create valid tar archive in byte array given entry name and content'() {
        when:
            def tar = new TarFile()
            tar.add entryName, data
            tar.writeOut()

        then:
            def actual = new TarArchiveInputStream(new ByteArrayInputStream(tar.bytes))
            def entry  = actual.nextTarEntry

            entry.name == entryName
            entry.size == data.size()

        when:
            def bytes   = new byte[data.size()]
            actual.read(bytes)

        then:
            new String(bytes) == data

        where:
            data      = 'some file content'
            entryName = 'some-tar-entry'
    }

    private File newFile(String type) {
        def result = File.createTempFile(this.class.simpleName, type)
        _files << result
        result
    }

    private File newFile(String type, String content) {
        def result = newFile(type)
        result.text = content
        result
    }

}
