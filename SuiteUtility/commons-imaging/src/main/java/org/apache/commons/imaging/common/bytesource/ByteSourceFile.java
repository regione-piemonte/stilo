// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.bytesource;

import java.io.ByteArrayOutputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;

public class ByteSourceFile extends ByteSource
{
    private final File file;
    
    public ByteSourceFile(final File file) {
        super(file.getName());
        this.file = file;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return new BufferedInputStream(new FileInputStream(this.file));
    }
    
    @Override
    public byte[] getBlock(final long start, final int length) throws IOException {
        try (final RandomAccessFile raf = new RandomAccessFile(this.file, "r")) {
            if (start < 0L || length < 0 || start + length < 0L || start + length > raf.length()) {
                throw new IOException("Could not read block (block start: " + start + ", block length: " + length + ", data length: " + raf.length() + ").");
            }
            return BinaryFunctions.getRAFBytes(raf, start, length, "Could not read value from file");
        }
    }
    
    @Override
    public long getLength() {
        return this.file.length();
    }
    
    @Override
    public byte[] getAll() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (final InputStream is = this.getInputStream()) {
            final byte[] buffer = new byte[1024];
            int read;
            while ((read = is.read(buffer)) > 0) {
                baos.write(buffer, 0, read);
            }
            return baos.toByteArray();
        }
    }
    
    @Override
    public String getDescription() {
        return "File: '" + this.file.getAbsolutePath() + "'";
    }
}
