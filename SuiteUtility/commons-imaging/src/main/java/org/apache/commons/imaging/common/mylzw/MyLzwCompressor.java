// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common.mylzw;

import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.nio.ByteOrder;

public class MyLzwCompressor
{
    private int codeSize;
    private final int initialCodeSize;
    private int codes;
    private final ByteOrder byteOrder;
    private final boolean earlyLimit;
    private final int clearCode;
    private final int eoiCode;
    private final Listener listener;
    private final Map<ByteArray, Integer> map;
    
    public MyLzwCompressor(final int initialCodeSize, final ByteOrder byteOrder, final boolean earlyLimit) {
        this(initialCodeSize, byteOrder, earlyLimit, null);
    }
    
    public MyLzwCompressor(final int initialCodeSize, final ByteOrder byteOrder, final boolean earlyLimit, final Listener listener) {
        this.codes = -1;
        this.map = new HashMap<ByteArray, Integer>();
        this.listener = listener;
        this.byteOrder = byteOrder;
        this.earlyLimit = earlyLimit;
        this.initialCodeSize = initialCodeSize;
        this.clearCode = 1 << initialCodeSize;
        this.eoiCode = this.clearCode + 1;
        if (null != listener) {
            listener.init(this.clearCode, this.eoiCode);
        }
        this.initializeStringTable();
    }
    
    private void initializeStringTable() {
        this.codeSize = this.initialCodeSize;
        final int intialEntriesCount = (1 << this.codeSize) + 2;
        this.map.clear();
        this.codes = 0;
        while (this.codes < intialEntriesCount) {
            if (this.codes != this.clearCode && this.codes != this.eoiCode) {
                final ByteArray key = this.arrayToKey((byte)this.codes);
                this.map.put(key, this.codes);
            }
            ++this.codes;
        }
    }
    
    private void clearTable() {
        this.initializeStringTable();
        this.incrementCodeSize();
    }
    
    private void incrementCodeSize() {
        if (this.codeSize != 12) {
            ++this.codeSize;
        }
    }
    
    private ByteArray arrayToKey(final byte b) {
        return this.arrayToKey(new byte[] { b }, 0, 1);
    }
    
    private ByteArray arrayToKey(final byte[] bytes, final int start, final int length) {
        return new ByteArray(bytes, start, length);
    }
    
    private void writeDataCode(final MyBitOutputStream bos, final int code) throws IOException {
        if (null != this.listener) {
            this.listener.dataCode(code);
        }
        this.writeCode(bos, code);
    }
    
    private void writeClearCode(final MyBitOutputStream bos) throws IOException {
        if (null != this.listener) {
            this.listener.dataCode(this.clearCode);
        }
        this.writeCode(bos, this.clearCode);
    }
    
    private void writeEoiCode(final MyBitOutputStream bos) throws IOException {
        if (null != this.listener) {
            this.listener.eoiCode(this.eoiCode);
        }
        this.writeCode(bos, this.eoiCode);
    }
    
    private void writeCode(final MyBitOutputStream bos, final int code) throws IOException {
        bos.writeBits(code, this.codeSize);
    }
    
    private boolean isInTable(final byte[] bytes, final int start, final int length) {
        final ByteArray key = this.arrayToKey(bytes, start, length);
        return this.map.containsKey(key);
    }
    
    private int codeFromString(final byte[] bytes, final int start, final int length) throws IOException {
        final ByteArray key = this.arrayToKey(bytes, start, length);
        final Integer code = this.map.get(key);
        if (code == null) {
            throw new IOException("CodeFromString");
        }
        return code;
    }
    
    private boolean addTableEntry(final MyBitOutputStream bos, final byte[] bytes, final int start, final int length) throws IOException {
        final ByteArray key = this.arrayToKey(bytes, start, length);
        return this.addTableEntry(bos, key);
    }
    
    private boolean addTableEntry(final MyBitOutputStream bos, final ByteArray key) throws IOException {
        boolean cleared = false;
        int limit = 1 << this.codeSize;
        if (this.earlyLimit) {
            --limit;
        }
        if (this.codes == limit) {
            if (this.codeSize < 12) {
                this.incrementCodeSize();
            }
            else {
                this.writeClearCode(bos);
                this.clearTable();
                cleared = true;
            }
        }
        if (!cleared) {
            this.map.put(key, this.codes);
            ++this.codes;
        }
        return cleared;
    }
    
    public byte[] compress(final byte[] bytes) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length);
        final MyBitOutputStream bos = new MyBitOutputStream(baos, this.byteOrder);
        this.initializeStringTable();
        this.clearTable();
        this.writeClearCode(bos);
        int wStart = 0;
        int wLength = 0;
        for (int i = 0; i < bytes.length; ++i) {
            if (this.isInTable(bytes, wStart, wLength + 1)) {
                ++wLength;
            }
            else {
                final int code = this.codeFromString(bytes, wStart, wLength);
                this.writeDataCode(bos, code);
                this.addTableEntry(bos, bytes, wStart, wLength + 1);
                wStart = i;
                wLength = 1;
            }
        }
        final int code2 = this.codeFromString(bytes, wStart, wLength);
        this.writeDataCode(bos, code2);
        this.writeEoiCode(bos);
        bos.flushCache();
        return baos.toByteArray();
    }
    
    private static final class ByteArray
    {
        private final byte[] bytes;
        private final int start;
        private final int length;
        private final int hash;
        
        ByteArray(final byte[] bytes, final int start, final int length) {
            this.bytes = bytes;
            this.start = start;
            this.length = length;
            int tempHash = length;
            for (int i = 0; i < length; ++i) {
                final int b = 0xFF & bytes[i + start];
                tempHash = (tempHash + (tempHash << 8) ^ b ^ i);
            }
            this.hash = tempHash;
        }
        
        @Override
        public int hashCode() {
            return this.hash;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof ByteArray)) {
                return false;
            }
            final ByteArray other = (ByteArray)o;
            if (other.hash != this.hash) {
                return false;
            }
            if (other.length != this.length) {
                return false;
            }
            for (int i = 0; i < this.length; ++i) {
                if (other.bytes[i + other.start] != this.bytes[i + this.start]) {
                    return false;
                }
            }
            return true;
        }
    }
    
    public interface Listener
    {
        void dataCode(final int p0);
        
        void eoiCode(final int p0);
        
        void clearCode(final int p0);
        
        void init(final int p0, final int p1);
    }
}
