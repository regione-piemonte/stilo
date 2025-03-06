// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.io.IOException;
import java.io.PrintStream;
import java.io.OutputStream;

public class Base64EncoderStream extends OutputStream
{
    private static final byte[] pem_array;
    byte[] atom;
    int atomLen;
    byte[] encodeBuf;
    int lineLen;
    PrintStream out;
    boolean closeOutOnClose;
    
    public Base64EncoderStream(final OutputStream out) {
        this.atom = new byte[3];
        this.atomLen = 0;
        this.encodeBuf = new byte[4];
        this.lineLen = 0;
        this.out = new PrintStream(out);
        this.closeOutOnClose = true;
    }
    
    public Base64EncoderStream(final OutputStream out, final boolean closeOutOnClose) {
        this.atom = new byte[3];
        this.atomLen = 0;
        this.encodeBuf = new byte[4];
        this.lineLen = 0;
        this.out = new PrintStream(out);
        this.closeOutOnClose = closeOutOnClose;
    }
    
    public void close() throws IOException {
        if (this.out != null) {
            this.encodeAtom();
            this.out.flush();
            if (this.closeOutOnClose) {
                this.out.close();
            }
            this.out = null;
        }
    }
    
    public void flush() throws IOException {
        this.out.flush();
    }
    
    public void write(final int n) throws IOException {
        this.atom[this.atomLen++] = (byte)n;
        if (this.atomLen == 3) {
            this.encodeAtom();
        }
    }
    
    public void write(final byte[] array) throws IOException {
        this.encodeFromArray(array, 0, array.length);
    }
    
    public void write(final byte[] array, final int n, final int n2) throws IOException {
        this.encodeFromArray(array, n, n2);
    }
    
    void encodeAtom() throws IOException {
        switch (this.atomLen) {
            case 0: {
                return;
            }
            case 1: {
                final byte b = this.atom[0];
                this.encodeBuf[0] = Base64EncoderStream.pem_array[b >>> 2 & 0x3F];
                this.encodeBuf[1] = Base64EncoderStream.pem_array[b << 4 & 0x30];
                this.encodeBuf[2] = (this.encodeBuf[3] = 61);
                break;
            }
            case 2: {
                final byte b2 = this.atom[0];
                final byte b3 = this.atom[1];
                this.encodeBuf[0] = Base64EncoderStream.pem_array[b2 >>> 2 & 0x3F];
                this.encodeBuf[1] = Base64EncoderStream.pem_array[(b2 << 4 & 0x30) | (b3 >>> 4 & 0xF)];
                this.encodeBuf[2] = Base64EncoderStream.pem_array[b3 << 2 & 0x3C];
                this.encodeBuf[3] = 61;
                break;
            }
            default: {
                final byte b4 = this.atom[0];
                final byte b5 = this.atom[1];
                final byte b6 = this.atom[2];
                this.encodeBuf[0] = Base64EncoderStream.pem_array[b4 >>> 2 & 0x3F];
                this.encodeBuf[1] = Base64EncoderStream.pem_array[(b4 << 4 & 0x30) | (b5 >>> 4 & 0xF)];
                this.encodeBuf[2] = Base64EncoderStream.pem_array[(b5 << 2 & 0x3C) | (b6 >>> 6 & 0x3)];
                this.encodeBuf[3] = Base64EncoderStream.pem_array[b6 & 0x3F];
                break;
            }
        }
        if (this.lineLen == 64) {
            this.out.println();
            this.lineLen = 0;
        }
        this.out.write(this.encodeBuf);
        this.lineLen += 4;
        this.atomLen = 0;
    }
    
    void encodeFromArray(final byte[] array, int n, int i) throws IOException {
        if (i == 0) {
            return;
        }
        if (this.atomLen != 0) {
            switch (this.atomLen) {
                case 1: {
                    this.atom[1] = array[n++];
                    --i;
                    ++this.atomLen;
                    if (i == 0) {
                        return;
                    }
                    this.atom[2] = array[n++];
                    --i;
                    ++this.atomLen;
                    break;
                }
                case 2: {
                    this.atom[2] = array[n++];
                    --i;
                    ++this.atomLen;
                    break;
                }
            }
            this.encodeAtom();
        }
        while (i >= 3) {
            final byte b = array[n++];
            final byte b2 = array[n++];
            final byte b3 = array[n++];
            this.encodeBuf[0] = Base64EncoderStream.pem_array[b >>> 2 & 0x3F];
            this.encodeBuf[1] = Base64EncoderStream.pem_array[(b << 4 & 0x30) | (b2 >>> 4 & 0xF)];
            this.encodeBuf[2] = Base64EncoderStream.pem_array[(b2 << 2 & 0x3C) | (b3 >>> 6 & 0x3)];
            this.encodeBuf[3] = Base64EncoderStream.pem_array[b3 & 0x3F];
            this.out.write(this.encodeBuf);
            this.lineLen += 4;
            if (this.lineLen == 64) {
                this.out.println();
                this.lineLen = 0;
            }
            i -= 3;
        }
        switch (i) {
            case 1: {
                this.atom[0] = array[n];
                break;
            }
            case 2: {
                this.atom[0] = array[n];
                this.atom[1] = array[n + 1];
                break;
            }
        }
        this.atomLen = i;
    }
    
    static {
        pem_array = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
    }
}
