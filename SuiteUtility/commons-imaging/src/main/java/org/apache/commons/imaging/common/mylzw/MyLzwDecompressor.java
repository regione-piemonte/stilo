// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.mylzw;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.ByteOrder;

public final class MyLzwDecompressor
{
    private static final int MAX_TABLE_SIZE = 4096;
    private final byte[][] table;
    private int codeSize;
    private final int initialCodeSize;
    private int codes;
    private final ByteOrder byteOrder;
    private final Listener listener;
    private final int clearCode;
    private final int eoiCode;
    private int written;
    private boolean tiffLZWMode;
    
    public MyLzwDecompressor(final int initialCodeSize, final ByteOrder byteOrder) {
        this(initialCodeSize, byteOrder, null);
    }
    
    public MyLzwDecompressor(final int initialCodeSize, final ByteOrder byteOrder, final Listener listener) {
        this.codes = -1;
        this.listener = listener;
        this.byteOrder = byteOrder;
        this.initialCodeSize = initialCodeSize;
        this.table = new byte[4096][];
        this.clearCode = 1 << initialCodeSize;
        this.eoiCode = this.clearCode + 1;
        if (null != listener) {
            listener.init(this.clearCode, this.eoiCode);
        }
        this.initializeTable();
    }
    
    private void initializeTable() {
        this.codeSize = this.initialCodeSize;
        for (int intialEntriesCount = 1 << this.codeSize + 2, i = 0; i < intialEntriesCount; ++i) {
            this.table[i] = new byte[] { (byte)i };
        }
    }
    
    private void clearTable() {
        this.codes = (1 << this.initialCodeSize) + 2;
        this.codeSize = this.initialCodeSize;
        this.incrementCodeSize();
    }
    
    private int getNextCode(final MyBitInputStream is) throws IOException {
        final int code = is.readBits(this.codeSize);
        if (null != this.listener) {
            this.listener.code(code);
        }
        return code;
    }
    
    private byte[] stringFromCode(final int code) throws IOException {
        if (code >= this.codes || code < 0) {
            throw new IOException("Bad Code: " + code + " codes: " + this.codes + " code_size: " + this.codeSize + ", table: " + this.table.length);
        }
        return this.table[code];
    }
    
    private boolean isInTable(final int code) {
        return code < this.codes;
    }
    
    private byte firstChar(final byte[] bytes) {
        return bytes[0];
    }
    
    private void addStringToTable(final byte[] bytes) throws IOException {
        if (this.codes < 1 << this.codeSize) {
            this.table[this.codes] = bytes;
            ++this.codes;
            this.checkCodeSize();
            return;
        }
        throw new IOException("AddStringToTable: codes: " + this.codes + " code_size: " + this.codeSize);
    }
    
    private byte[] appendBytes(final byte[] bytes, final byte b) {
        final byte[] result = new byte[bytes.length + 1];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        result[result.length - 1] = b;
        return result;
    }
    
    private void writeToResult(final OutputStream os, final byte[] bytes) throws IOException {
        os.write(bytes);
        this.written += bytes.length;
    }
    
    public void setTiffLZWMode() {
        this.tiffLZWMode = true;
    }
    
    public byte[] decompress(final InputStream is, final int expectedLength) throws IOException {
        int oldCode = -1;
        final MyBitInputStream mbis = new MyBitInputStream(is, this.byteOrder);
        if (this.tiffLZWMode) {
            mbis.setTiffLZWMode();
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(expectedLength);
        this.clearTable();
        int code;
        while ((code = this.getNextCode(mbis)) != this.eoiCode) {
            if (code == this.clearCode) {
                this.clearTable();
                if (this.written >= expectedLength) {
                    break;
                }
                code = this.getNextCode(mbis);
                if (code == this.eoiCode) {
                    break;
                }
                this.writeToResult(baos, this.stringFromCode(code));
                oldCode = code;
            }
            else if (this.isInTable(code)) {
                this.writeToResult(baos, this.stringFromCode(code));
                this.addStringToTable(this.appendBytes(this.stringFromCode(oldCode), this.firstChar(this.stringFromCode(code))));
                oldCode = code;
            }
            else {
                final byte[] outString = this.appendBytes(this.stringFromCode(oldCode), this.firstChar(this.stringFromCode(oldCode)));
                this.writeToResult(baos, outString);
                this.addStringToTable(outString);
                oldCode = code;
            }
            if (this.written >= expectedLength) {
                break;
            }
        }
        return baos.toByteArray();
    }
    
    private void checkCodeSize() {
        int limit = 1 << this.codeSize;
        if (this.tiffLZWMode) {
            --limit;
        }
        if (this.codes == limit) {
            this.incrementCodeSize();
        }
    }
    
    private void incrementCodeSize() {
        if (this.codeSize != 12) {
            ++this.codeSize;
        }
    }
    
    public interface Listener
    {
        void code(final int p0);
        
        void init(final int p0, final int p1);
    }
}
