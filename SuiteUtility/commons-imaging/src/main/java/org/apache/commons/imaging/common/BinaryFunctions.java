// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.PrintWriter;
import java.nio.ByteOrder;
import org.apache.commons.imaging.ImageReadException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public final class BinaryFunctions
{
    private static final Logger LOGGER;
    
    private BinaryFunctions() {
    }
    
    public static boolean startsWith(final byte[] haystack, final byte[] needle) {
        if (needle == null) {
            return false;
        }
        if (haystack == null) {
            return false;
        }
        if (needle.length > haystack.length) {
            return false;
        }
        for (int i = 0; i < needle.length; ++i) {
            if (needle[i] != haystack[i]) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean startsWith(final byte[] haystack, final BinaryConstant needle) {
        if (haystack == null || haystack.length < needle.size()) {
            return false;
        }
        for (int i = 0; i < needle.size(); ++i) {
            if (haystack[i] != needle.get(i)) {
                return false;
            }
        }
        return true;
    }
    
    public static byte readByte(final String name, final InputStream is, final String exception) throws IOException {
        final int result = is.read();
        if (result < 0) {
            throw new IOException(exception);
        }
        return (byte)(0xFF & result);
    }
    
    public static byte[] readBytes(final String name, final InputStream is, final int length) throws IOException {
        final String exception = name + " could not be read.";
        return readBytes(name, is, length, exception);
    }
    
    public static byte[] readBytes(final String name, final InputStream is, final int length, final String exception) throws IOException {
        final byte[] result = new byte[length];
        int count;
        for (int read = 0; read < length; read += count) {
            count = is.read(result, read, length - read);
            if (count < 0) {
                throw new IOException(exception + " count: " + count + " read: " + read + " length: " + length);
            }
        }
        return result;
    }
    
    public static byte[] readBytes(final InputStream is, final int count) throws IOException {
        return readBytes("", is, count, "Unexpected EOF");
    }
    
    public static void readAndVerifyBytes(final InputStream is, final byte[] expected, final String exception) throws ImageReadException, IOException {
        for (final byte element : expected) {
            final int data = is.read();
            final byte b = (byte)(0xFF & data);
            if (data < 0) {
                throw new ImageReadException("Unexpected EOF.");
            }
            if (b != element) {
                throw new ImageReadException(exception);
            }
        }
    }
    
    public static void readAndVerifyBytes(final InputStream is, final BinaryConstant expected, final String exception) throws ImageReadException, IOException {
        for (int i = 0; i < expected.size(); ++i) {
            final int data = is.read();
            final byte b = (byte)(0xFF & data);
            if (data < 0) {
                throw new ImageReadException("Unexpected EOF.");
            }
            if (b != expected.get(i)) {
                throw new ImageReadException(exception);
            }
        }
    }
    
    public static void skipBytes(final InputStream is, final long length, final String exception) throws IOException {
        long skipped;
        for (long total = 0L; length != total; total += skipped) {
            skipped = is.skip(length - total);
            if (skipped < 1L) {
                throw new IOException(exception + " (" + skipped + ")");
            }
        }
    }
    
    public static byte[] remainingBytes(final String name, final byte[] bytes, final int count) {
        return slice(bytes, count, bytes.length - count);
    }
    
    public static byte[] slice(final byte[] bytes, final int start, final int count) {
        final byte[] result = new byte[count];
        System.arraycopy(bytes, start, result, 0, count);
        return result;
    }
    
    public static byte[] head(final byte[] bytes, int count) {
        if (count > bytes.length) {
            count = bytes.length;
        }
        return slice(bytes, 0, count);
    }
    
    public static boolean compareBytes(final byte[] a, final int aStart, final byte[] b, final int bStart, final int length) {
        if (a.length < aStart + length) {
            return false;
        }
        if (b.length < bStart + length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (a[aStart + i] != b[bStart + i]) {
                return false;
            }
        }
        return true;
    }
    
    public static int read4Bytes(final String name, final InputStream is, final String exception, final ByteOrder byteOrder) throws IOException {
        final int byte0 = is.read();
        final int byte2 = is.read();
        final int byte3 = is.read();
        final int byte4 = is.read();
        if ((byte0 | byte2 | byte3 | byte4) < 0) {
            throw new IOException(exception);
        }
        int result;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            result = (byte0 << 24 | byte2 << 16 | byte3 << 8 | byte4 << 0);
        }
        else {
            result = (byte4 << 24 | byte3 << 16 | byte2 << 8 | byte0 << 0);
        }
        return result;
    }
    
    public static int read3Bytes(final String name, final InputStream is, final String exception, final ByteOrder byteOrder) throws IOException {
        final int byte0 = is.read();
        final int byte2 = is.read();
        final int byte3 = is.read();
        if ((byte0 | byte2 | byte3) < 0) {
            throw new IOException(exception);
        }
        int result;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            result = (byte0 << 16 | byte2 << 8 | byte3 << 0);
        }
        else {
            result = (byte3 << 16 | byte2 << 8 | byte0 << 0);
        }
        return result;
    }
    
    public static int read2Bytes(final String name, final InputStream is, final String exception, final ByteOrder byteOrder) throws IOException {
        final int byte0 = is.read();
        final int byte2 = is.read();
        if ((byte0 | byte2) < 0) {
            throw new IOException(exception);
        }
        int result;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            result = (byte0 << 8 | byte2);
        }
        else {
            result = (byte2 << 8 | byte0);
        }
        return result;
    }
    
    public static void printCharQuad(final String msg, final int i) {
        BinaryFunctions.LOGGER.finest(msg + ": '" + (char)(0xFF & i >> 24) + (char)(0xFF & i >> 16) + (char)(0xFF & i >> 8) + (char)(0xFF & i >> 0) + "'");
    }
    
    public static void printCharQuad(final PrintWriter pw, final String msg, final int i) {
        pw.println(msg + ": '" + (char)(0xFF & i >> 24) + (char)(0xFF & i >> 16) + (char)(0xFF & i >> 8) + (char)(0xFF & i >> 0) + "'");
    }
    
    public static void printByteBits(final String msg, final byte i) {
        BinaryFunctions.LOGGER.finest(msg + ": '" + Integer.toBinaryString(0xFF & i));
    }
    
    public static int charsToQuad(final char c1, final char c2, final char c3, final char c4) {
        return ('\u00ff' & c1) << 24 | ('\u00ff' & c2) << 16 | ('\u00ff' & c3) << 8 | ('\u00ff' & c4) << 0;
    }
    
    public static int findNull(final byte[] src) {
        return findNull(src, 0);
    }
    
    public static int findNull(final byte[] src, final int start) {
        for (int i = start; i < src.length; ++i) {
            if (src[i] == 0) {
                return i;
            }
        }
        return -1;
    }
    
    public static byte[] getRAFBytes(final RandomAccessFile raf, final long pos, final int length, final String exception) throws IOException {
        final byte[] result = new byte[length];
        raf.seek(pos);
        int count;
        for (int read = 0; read < length; read += count) {
            count = raf.read(result, read, length - read);
            if (count < 0) {
                throw new IOException(exception);
            }
        }
        return result;
    }
    
    public static void skipBytes(final InputStream is, final long length) throws IOException {
        skipBytes(is, length, "Couldn't skip bytes");
    }
    
    public static void copyStreamToStream(final InputStream is, final OutputStream os) throws IOException {
        final byte[] buffer = new byte[1024];
        int read;
        while ((read = is.read(buffer)) > 0) {
            os.write(buffer, 0, read);
        }
    }
    
    public static byte[] getStreamBytes(final InputStream is) throws IOException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        copyStreamToStream(is, os);
        return os.toByteArray();
    }
    
    static {
        LOGGER = Logger.getLogger(BinaryFunctions.class.getName());
    }
}
