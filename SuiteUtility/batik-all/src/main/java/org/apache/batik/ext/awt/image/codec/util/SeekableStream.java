// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.DataInput;
import java.io.InputStream;

public abstract class SeekableStream extends InputStream implements DataInput
{
    protected long markPos;
    private byte[] ruileBuf;
    
    public SeekableStream() {
        this.markPos = -1L;
        this.ruileBuf = new byte[4];
    }
    
    public static SeekableStream wrapInputStream(final InputStream inputStream, final boolean b) {
        SeekableStream seekableStream;
        if (b) {
            try {
                seekableStream = new FileCacheSeekableStream(inputStream);
            }
            catch (Exception ex) {
                seekableStream = new MemoryCacheSeekableStream(inputStream);
            }
        }
        else {
            seekableStream = new ForwardSeekableStream(inputStream);
        }
        return seekableStream;
    }
    
    public abstract int read() throws IOException;
    
    public abstract int read(final byte[] p0, final int p1, final int p2) throws IOException;
    
    public synchronized void mark(final int n) {
        try {
            this.markPos = this.getFilePointer();
        }
        catch (IOException ex) {
            this.markPos = -1L;
        }
    }
    
    public synchronized void reset() throws IOException {
        if (this.markPos != -1L) {
            this.seek(this.markPos);
        }
    }
    
    public boolean markSupported() {
        return this.canSeekBackwards();
    }
    
    public boolean canSeekBackwards() {
        return false;
    }
    
    public abstract long getFilePointer() throws IOException;
    
    public abstract void seek(final long p0) throws IOException;
    
    public final void readFully(final byte[] array) throws IOException {
        this.readFully(array, 0, array.length);
    }
    
    public final void readFully(final byte[] array, final int n, final int n2) throws IOException {
        int i = 0;
        do {
            final int read = this.read(array, n + i, n2 - i);
            if (read < 0) {
                throw new EOFException();
            }
            i += read;
        } while (i < n2);
    }
    
    public int skipBytes(final int n) throws IOException {
        if (n <= 0) {
            return 0;
        }
        return (int)this.skip(n);
    }
    
    public final boolean readBoolean() throws IOException {
        final int read = this.read();
        if (read < 0) {
            throw new EOFException();
        }
        return read != 0;
    }
    
    public final byte readByte() throws IOException {
        final int read = this.read();
        if (read < 0) {
            throw new EOFException();
        }
        return (byte)read;
    }
    
    public final int readUnsignedByte() throws IOException {
        final int read = this.read();
        if (read < 0) {
            throw new EOFException();
        }
        return read;
    }
    
    public final short readShort() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (short)((read << 8) + (read2 << 0));
    }
    
    public final short readShortLE() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (short)((read2 << 8) + (read << 0));
    }
    
    public final int readUnsignedShort() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (read << 8) + (read2 << 0);
    }
    
    public final int readUnsignedShortLE() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (read2 << 8) + (read << 0);
    }
    
    public final char readChar() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (char)((read << 8) + (read2 << 0));
    }
    
    public final char readCharLE() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        if ((read | read2) < 0) {
            throw new EOFException();
        }
        return (char)((read2 << 8) + (read << 0));
    }
    
    public final int readInt() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        final int read3 = this.read();
        final int read4 = this.read();
        if ((read | read2 | read3 | read4) < 0) {
            throw new EOFException();
        }
        return (read << 24) + (read2 << 16) + (read3 << 8) + (read4 << 0);
    }
    
    public final int readIntLE() throws IOException {
        final int read = this.read();
        final int read2 = this.read();
        final int read3 = this.read();
        final int read4 = this.read();
        if ((read | read2 | read3 | read4) < 0) {
            throw new EOFException();
        }
        return (read4 << 24) + (read3 << 16) + (read2 << 8) + (read << 0);
    }
    
    public final long readUnsignedInt() throws IOException {
        final long n = this.read();
        final long n2 = this.read();
        final long n3 = this.read();
        final long n4 = this.read();
        if ((n | n2 | n3 | n4) < 0L) {
            throw new EOFException();
        }
        return (n << 24) + (n2 << 16) + (n3 << 8) + (n4 << 0);
    }
    
    public final long readUnsignedIntLE() throws IOException {
        this.readFully(this.ruileBuf);
        return ((long)(this.ruileBuf[3] & 0xFF) << 24) + ((long)(this.ruileBuf[2] & 0xFF) << 16) + ((long)(this.ruileBuf[1] & 0xFF) << 8) + ((long)(this.ruileBuf[0] & 0xFF) << 0);
    }
    
    public final long readLong() throws IOException {
        return ((long)this.readInt() << 32) + ((long)this.readInt() & 0xFFFFFFFFL);
    }
    
    public final long readLongLE() throws IOException {
        return ((long)this.readIntLE() << 32) + ((long)this.readIntLE() & 0xFFFFFFFFL);
    }
    
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }
    
    public final float readFloatLE() throws IOException {
        return Float.intBitsToFloat(this.readIntLE());
    }
    
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }
    
    public final double readDoubleLE() throws IOException {
        return Double.longBitsToDouble(this.readLongLE());
    }
    
    public final String readLine() throws IOException {
        final StringBuffer sb = new StringBuffer();
        int read = -1;
        int i = 0;
        while (i == 0) {
            switch (read = this.read()) {
                case -1:
                case 10: {
                    i = 1;
                    continue;
                }
                case 13: {
                    i = 1;
                    final long filePointer = this.getFilePointer();
                    if (this.read() != 10) {
                        this.seek(filePointer);
                        continue;
                    }
                    continue;
                }
                default: {
                    sb.append((char)read);
                    continue;
                }
            }
        }
        if (read == -1 && sb.length() == 0) {
            return null;
        }
        return sb.toString();
    }
    
    public final String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }
    
    protected void finalize() throws Throwable {
        super.finalize();
        this.close();
    }
}
