// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.bytesource;

import java.io.ByteArrayOutputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class ByteSourceInputStream extends ByteSource
{
    private static final int BLOCK_SIZE = 1024;
    private final InputStream is;
    private CacheBlock cacheHead;
    private byte[] readBuffer;
    private long streamLength;
    
    public ByteSourceInputStream(final InputStream is, final String filename) {
        super(filename);
        this.streamLength = -1L;
        this.is = new BufferedInputStream(is);
    }
    
    private CacheBlock readBlock() throws IOException {
        if (null == this.readBuffer) {
            this.readBuffer = new byte[1024];
        }
        final int read = this.is.read(this.readBuffer);
        if (read < 1) {
            return null;
        }
        if (read < 1024) {
            final byte[] result = new byte[read];
            System.arraycopy(this.readBuffer, 0, result, 0, read);
            return new CacheBlock(result);
        }
        final byte[] result = this.readBuffer;
        this.readBuffer = null;
        return new CacheBlock(result);
    }
    
    private CacheBlock getFirstBlock() throws IOException {
        if (null == this.cacheHead) {
            this.cacheHead = this.readBlock();
        }
        return this.cacheHead;
    }
    
    @Override
    public InputStream getInputStream() throws IOException {
        return new CacheReadingInputStream();
    }
    
    @Override
    public byte[] getBlock(final long blockStart, final int blockLength) throws IOException {
        if (blockStart < 0L || blockLength < 0 || blockStart + blockLength < 0L || blockStart + blockLength > this.streamLength) {
            throw new IOException("Could not read block (block start: " + blockStart + ", block length: " + blockLength + ", data length: " + this.streamLength + ").");
        }
        final InputStream cis = this.getInputStream();
        BinaryFunctions.skipBytes(cis, blockStart);
        final byte[] bytes = new byte[blockLength];
        int total = 0;
        while (true) {
            final int read = cis.read(bytes, total, bytes.length - total);
            if (read < 1) {
                throw new IOException("Could not read block.");
            }
            total += read;
            if (total >= blockLength) {
                return bytes;
            }
        }
    }
    
    @Override
    public long getLength() throws IOException {
        if (this.streamLength >= 0L) {
            return this.streamLength;
        }
        final InputStream cis = this.getInputStream();
        long result = 0L;
        long skipped;
        while ((skipped = cis.skip(1024L)) > 0L) {
            result += skipped;
        }
        return this.streamLength = result;
    }
    
    @Override
    public byte[] getAll() throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (CacheBlock block = this.getFirstBlock(); block != null; block = block.getNext()) {
            baos.write(block.bytes);
        }
        return baos.toByteArray();
    }
    
    @Override
    public String getDescription() {
        return "Inputstream: '" + this.getFilename() + "'";
    }
    
    private class CacheBlock
    {
        public final byte[] bytes;
        private CacheBlock next;
        private boolean triedNext;
        
        CacheBlock(final byte[] bytes) {
            this.bytes = bytes;
        }
        
        public CacheBlock getNext() throws IOException {
            if (null != this.next) {
                return this.next;
            }
            if (this.triedNext) {
                return null;
            }
            this.triedNext = true;
            return this.next = ByteSourceInputStream.this.readBlock();
        }
    }
    
    private class CacheReadingInputStream extends InputStream
    {
        private CacheBlock block;
        private boolean readFirst;
        private int blockIndex;
        
        @Override
        public int read() throws IOException {
            if (null == this.block) {
                if (this.readFirst) {
                    return -1;
                }
                this.block = ByteSourceInputStream.this.getFirstBlock();
                this.readFirst = true;
            }
            if (this.block != null && this.blockIndex >= this.block.bytes.length) {
                this.block = this.block.getNext();
                this.blockIndex = 0;
            }
            if (null == this.block) {
                return -1;
            }
            if (this.blockIndex >= this.block.bytes.length) {
                return -1;
            }
            return 0xFF & this.block.bytes[this.blockIndex++];
        }
        
        @Override
        public int read(final byte[] b, final int off, final int len) throws IOException {
            if (b == null) {
                throw new NullPointerException();
            }
            if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (len == 0) {
                return 0;
            }
            if (null == this.block) {
                if (this.readFirst) {
                    return -1;
                }
                this.block = ByteSourceInputStream.this.getFirstBlock();
                this.readFirst = true;
            }
            if (this.block != null && this.blockIndex >= this.block.bytes.length) {
                this.block = this.block.getNext();
                this.blockIndex = 0;
            }
            if (null == this.block) {
                return -1;
            }
            if (this.blockIndex >= this.block.bytes.length) {
                return -1;
            }
            final int readSize = Math.min(len, this.block.bytes.length - this.blockIndex);
            System.arraycopy(this.block.bytes, this.blockIndex, b, off, readSize);
            this.blockIndex += readSize;
            return readSize;
        }
        
        @Override
        public long skip(final long n) throws IOException {
            long remaining = n;
            if (n <= 0L) {
                return 0L;
            }
            while (remaining > 0L) {
                if (null == this.block) {
                    if (this.readFirst) {
                        return -1L;
                    }
                    this.block = ByteSourceInputStream.this.getFirstBlock();
                    this.readFirst = true;
                }
                if (this.block != null && this.blockIndex >= this.block.bytes.length) {
                    this.block = this.block.getNext();
                    this.blockIndex = 0;
                }
                if (null == this.block) {
                    break;
                }
                if (this.blockIndex >= this.block.bytes.length) {
                    break;
                }
                final int readSize = Math.min((int)Math.min(1024L, remaining), this.block.bytes.length - this.blockIndex);
                this.blockIndex += readSize;
                remaining -= readSize;
            }
            return n - remaining;
        }
    }
}
