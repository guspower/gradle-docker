package com.energizedwork.file

import org.apache.commons.compress.archivers.tar.TarArchiveEntry
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream
import org.apache.commons.compress.utils.IOUtils

class TarFile {

    private TarArchiveOutputStream _tar
    private ByteArrayOutputStream _bytes
    private File _file

    TarFile() {
        _bytes = new ByteArrayOutputStream()
        _tar = new TarArchiveOutputStream(_bytes)
    }

    TarFile(File file) {
        _file = file
        _tar = new TarArchiveOutputStream(_file.newOutputStream())
    }

    byte[] getBytes() {
        _file ? _file.bytes : _bytes.toByteArray()
    }

    void add(String entryName, String content) {
        def entry = new TarArchiveEntry(entryName)
        entry.size = content.size()

        _tar.putArchiveEntry entry
        IOUtils.copy(new ByteArrayInputStream(content.bytes), _tar)

        _tar.closeArchiveEntry()
    }

    void add(File file, String path) {
        def entry = new TarArchiveEntry(file, path)
        entry.size = file.size()

        _tar.putArchiveEntry entry
        IOUtils.copy(file.newInputStream(), _tar)

        _tar.closeArchiveEntry()
    }

    void writeOut() {
        _tar.finish()
        _tar.close()
    }

}
