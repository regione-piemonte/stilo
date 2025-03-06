// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util;

import java.io.IOException;
import java.io.InputStream;

public class Base64DecodeStream extends InputStream
{
    InputStream src;
    private static final byte[] pem_array;
    byte[] decode_buffer;
    byte[] out_buffer;
    int out_offset;
    boolean EOF;
    
    public Base64DecodeStream(final InputStream src) {
        this.decode_buffer = new byte[4];
        this.out_buffer = new byte[3];
        this.out_offset = 3;
        this.EOF = false;
        this.src = src;
    }
    
    public boolean markSupported() {
        return false;
    }
    
    public void close() throws IOException {
        this.EOF = true;
    }
    
    public int available() throws IOException {
        return 3 - this.out_offset;
    }
    
    public int read() throws IOException {
        if (this.out_offset == 3 && (this.EOF || this.getNextAtom())) {
            this.EOF = true;
            return -1;
        }
        return this.out_buffer[this.out_offset++] & 0xFF;
    }
    
    public int read(final byte[] array, final int n, final int n2) throws IOException {
        int i = 0;
        while (i < n2) {
            if (this.out_offset == 3 && (this.EOF || this.getNextAtom())) {
                this.EOF = true;
                if (i == 0) {
                    return -1;
                }
                return i;
            }
            else {
                array[n + i] = this.out_buffer[this.out_offset++];
                ++i;
            }
        }
        return i;
    }
    
    final boolean getNextAtom() throws IOException {
        int n;
        for (int i = 0; i != 4; i = n) {
            final int read = this.src.read(this.decode_buffer, i, 4 - i);
            if (read == -1) {
                return true;
            }
            int j = i;
            n = i;
            while (j < i + read) {
                if (this.decode_buffer[j] != 10 && this.decode_buffer[j] != 13 && this.decode_buffer[j] != 32) {
                    this.decode_buffer[n++] = this.decode_buffer[j];
                }
                ++j;
            }
        }
        final byte b = Base64DecodeStream.pem_array[this.decode_buffer[0] & 0xFF];
        final byte b2 = Base64DecodeStream.pem_array[this.decode_buffer[1] & 0xFF];
        final byte b3 = Base64DecodeStream.pem_array[this.decode_buffer[2] & 0xFF];
        final byte b4 = Base64DecodeStream.pem_array[this.decode_buffer[3] & 0xFF];
        this.out_buffer[0] = (byte)(b << 2 | b2 >>> 4);
        this.out_buffer[1] = (byte)(b2 << 4 | b3 >>> 2);
        this.out_buffer[2] = (byte)(b3 << 6 | b4);
        if (this.decode_buffer[3] != 61) {
            this.out_offset = 0;
        }
        else if (this.decode_buffer[2] == 61) {
            this.out_buffer[2] = this.out_buffer[0];
            this.out_offset = 2;
            this.EOF = true;
        }
        else {
            this.out_buffer[2] = this.out_buffer[1];
            this.out_buffer[1] = this.out_buffer[0];
            this.out_offset = 1;
            this.EOF = true;
        }
        return false;
    }
    
    static {
        pem_array = new byte[256];
        for (int i = 0; i < Base64DecodeStream.pem_array.length; ++i) {
            Base64DecodeStream.pem_array[i] = -1;
        }
        int n = 0;
        for (int j = 65; j <= 90; j = (char)(j + 1)) {
            Base64DecodeStream.pem_array[j] = (byte)(n++);
        }
        for (int k = 97; k <= 122; k = (char)(k + 1)) {
            Base64DecodeStream.pem_array[k] = (byte)(n++);
        }
        for (int l = 48; l <= 57; l = (char)(l + 1)) {
            Base64DecodeStream.pem_array[l] = (byte)(n++);
        }
        Base64DecodeStream.pem_array[43] = (byte)(n++);
        Base64DecodeStream.pem_array[47] = (byte)(n++);
    }
}
