// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.tiff;

public class TIFFLZWDecoder
{
    byte[][] stringTable;
    byte[] data;
    byte[] uncompData;
    int tableIndex;
    int bitsToGet;
    int bytePointer;
    int bitPointer;
    int dstIndex;
    int w;
    int h;
    int predictor;
    int samplesPerPixel;
    int nextData;
    int nextBits;
    int[] andTable;
    
    public TIFFLZWDecoder(final int w, final int predictor, final int samplesPerPixel) {
        this.data = null;
        this.bitsToGet = 9;
        this.nextData = 0;
        this.nextBits = 0;
        this.andTable = new int[] { 511, 1023, 2047, 4095 };
        this.w = w;
        this.predictor = predictor;
        this.samplesPerPixel = samplesPerPixel;
    }
    
    public byte[] decode(final byte[] data, final byte[] uncompData, final int h) {
        if (data[0] == 0 && data[1] == 1) {
            throw new UnsupportedOperationException("TIFFLZWDecoder0");
        }
        this.initializeStringTable();
        this.data = data;
        this.h = h;
        this.uncompData = uncompData;
        this.bytePointer = 0;
        this.bitPointer = 0;
        this.dstIndex = 0;
        this.nextData = 0;
        this.nextBits = 0;
        int n = 0;
        int nextCode;
        while ((nextCode = this.getNextCode()) != 257 && this.dstIndex != uncompData.length) {
            if (nextCode == 256) {
                this.initializeStringTable();
                final int nextCode2 = this.getNextCode();
                if (nextCode2 == 257) {
                    break;
                }
                this.writeString(this.stringTable[nextCode2]);
                n = nextCode2;
            }
            else if (nextCode < this.tableIndex) {
                final byte[] array = this.stringTable[nextCode];
                this.writeString(array);
                this.addStringToTable(this.stringTable[n], array[0]);
                n = nextCode;
            }
            else {
                final byte[] array2 = this.stringTable[n];
                final byte[] composeString = this.composeString(array2, array2[0]);
                this.writeString(composeString);
                this.addStringToTable(composeString);
                n = nextCode;
            }
        }
        if (this.predictor == 2) {
            for (int i = 0; i < h; ++i) {
                int n2 = this.samplesPerPixel * (i * this.w + 1);
                for (int j = this.samplesPerPixel; j < this.w * this.samplesPerPixel; ++j) {
                    final int n3 = n2;
                    uncompData[n3] += uncompData[n2 - this.samplesPerPixel];
                    ++n2;
                }
            }
        }
        return uncompData;
    }
    
    public void initializeStringTable() {
        this.stringTable = new byte[4096][];
        for (int i = 0; i < 256; ++i) {
            (this.stringTable[i] = new byte[1])[0] = (byte)i;
        }
        this.tableIndex = 258;
        this.bitsToGet = 9;
    }
    
    public void writeString(final byte[] array) {
        for (int i = 0; i < array.length; ++i) {
            this.uncompData[this.dstIndex++] = array[i];
        }
    }
    
    public void addStringToTable(final byte[] array, final byte b) {
        final int length = array.length;
        final byte[] array2 = new byte[length + 1];
        System.arraycopy(array, 0, array2, 0, length);
        array2[length] = b;
        this.stringTable[this.tableIndex++] = array2;
        if (this.tableIndex == 511) {
            this.bitsToGet = 10;
        }
        else if (this.tableIndex == 1023) {
            this.bitsToGet = 11;
        }
        else if (this.tableIndex == 2047) {
            this.bitsToGet = 12;
        }
    }
    
    public void addStringToTable(final byte[] array) {
        this.stringTable[this.tableIndex++] = array;
        if (this.tableIndex == 511) {
            this.bitsToGet = 10;
        }
        else if (this.tableIndex == 1023) {
            this.bitsToGet = 11;
        }
        else if (this.tableIndex == 2047) {
            this.bitsToGet = 12;
        }
    }
    
    public byte[] composeString(final byte[] array, final byte b) {
        final int length = array.length;
        final byte[] array2 = new byte[length + 1];
        System.arraycopy(array, 0, array2, 0, length);
        array2[length] = b;
        return array2;
    }
    
    public int getNextCode() {
        try {
            this.nextData = (this.nextData << 8 | (this.data[this.bytePointer++] & 0xFF));
            this.nextBits += 8;
            if (this.nextBits < this.bitsToGet) {
                this.nextData = (this.nextData << 8 | (this.data[this.bytePointer++] & 0xFF));
                this.nextBits += 8;
            }
            final int n = this.nextData >> this.nextBits - this.bitsToGet & this.andTable[this.bitsToGet - 9];
            this.nextBits -= this.bitsToGet;
            return n;
        }
        catch (ArrayIndexOutOfBoundsException ex) {
            return 257;
        }
    }
}
