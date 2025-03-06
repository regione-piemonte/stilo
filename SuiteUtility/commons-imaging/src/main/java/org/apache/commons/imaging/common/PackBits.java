// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.ByteArrayOutputStream;

public class PackBits
{
    public byte[] decompress(final byte[] bytes, final int expected) throws ImageReadException {
        int total = 0;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = 0;
        while (total < expected) {
            if (i >= bytes.length) {
                throw new ImageReadException("Tiff: Unpack bits source exhausted: " + i + ", done + " + total + ", expected + " + expected);
            }
            final int n = bytes[i++];
            if (n >= 0 && n <= 127) {
                final int count = n + 1;
                total += count;
                for (int j = 0; j < count; ++j) {
                    baos.write(bytes[i++]);
                }
            }
            else if (n >= -127 && n <= -1) {
                final int b = bytes[i++];
                final int count2 = -n + 1;
                total += count2;
                for (int k = 0; k < count2; ++k) {
                    baos.write(b);
                }
            }
            else {
                if (n == -128) {
                    throw new ImageReadException("Packbits: " + n);
                }
                continue;
            }
        }
        return baos.toByteArray();
    }
    
    private int findNextDuplicate(final byte[] bytes, final int start) {
        if (start >= bytes.length) {
            return -1;
        }
        byte prev = bytes[start];
        for (int i = start + 1; i < bytes.length; ++i) {
            final byte b = bytes[i];
            if (b == prev) {
                return i - 1;
            }
            prev = b;
        }
        return -1;
    }
    
    private int findRunLength(final byte[] bytes, final int start) {
        byte b;
        int i;
        for (b = bytes[start], i = start + 1; i < bytes.length && bytes[i] == b; ++i) {}
        return i - start;
    }
    
    public byte[] compress(final byte[] bytes) throws IOException {
        try (final FastByteArrayOutputStream baos = new FastByteArrayOutputStream(bytes.length * 2)) {
            int actualLen;
            for (int ptr = 0; ptr < bytes.length; ptr += actualLen) {
                int dup = this.findNextDuplicate(bytes, ptr);
                if (dup == ptr) {
                    final int len = this.findRunLength(bytes, dup);
                    actualLen = Math.min(len, 128);
                    baos.write(-(actualLen - 1));
                    baos.write(bytes[ptr]);
                }
                else {
                    int len = dup - ptr;
                    if (dup > 0) {
                        final int runlen = this.findRunLength(bytes, dup);
                        if (runlen < 3) {
                            final int nextptr = ptr + len + runlen;
                            final int nextdup = this.findNextDuplicate(bytes, nextptr);
                            if (nextdup != nextptr) {
                                dup = nextdup;
                                len = dup - ptr;
                            }
                        }
                    }
                    if (dup < 0) {
                        len = bytes.length - ptr;
                    }
                    actualLen = Math.min(len, 128);
                    baos.write(actualLen - 1);
                    for (int i = 0; i < actualLen; ++i) {
                        baos.write(bytes[ptr]);
                        ++ptr;
                    }
                }
            }
            final byte[] result = baos.toByteArray();
            return result;
        }
    }
}
