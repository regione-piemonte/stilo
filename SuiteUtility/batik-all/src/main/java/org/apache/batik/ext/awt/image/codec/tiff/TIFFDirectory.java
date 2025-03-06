// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.tiff;

import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.batik.ext.awt.image.codec.util.SeekableStream;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

public class TIFFDirectory implements Serializable
{
    boolean isBigEndian;
    int numEntries;
    TIFFField[] fields;
    Map fieldIndex;
    long IFDOffset;
    long nextIFDOffset;
    private static final int[] sizeOfType;
    
    TIFFDirectory() {
        this.fieldIndex = new HashMap();
        this.IFDOffset = 8L;
        this.nextIFDOffset = 0L;
    }
    
    private static boolean isValidEndianTag(final int n) {
        return n == 18761 || n == 19789;
    }
    
    public TIFFDirectory(final SeekableStream seekableStream, final int n) throws IOException {
        this.fieldIndex = new HashMap();
        this.IFDOffset = 8L;
        this.nextIFDOffset = 0L;
        final long filePointer = seekableStream.getFilePointer();
        seekableStream.seek(0L);
        final int unsignedShort = seekableStream.readUnsignedShort();
        if (!isValidEndianTag(unsignedShort)) {
            throw new IllegalArgumentException("TIFFDirectory1");
        }
        this.isBigEndian = (unsignedShort == 19789);
        if (this.readUnsignedShort(seekableStream) != 42) {
            throw new IllegalArgumentException("TIFFDirectory2");
        }
        long n2 = this.readUnsignedInt(seekableStream);
        for (int i = 0; i < n; ++i) {
            if (n2 == 0L) {
                throw new IllegalArgumentException("TIFFDirectory3");
            }
            seekableStream.seek(n2);
            seekableStream.skip(12L * this.readUnsignedShort(seekableStream));
            n2 = this.readUnsignedInt(seekableStream);
        }
        seekableStream.seek(n2);
        this.initialize(seekableStream);
        seekableStream.seek(filePointer);
    }
    
    public TIFFDirectory(final SeekableStream seekableStream, long unsignedInt, final int n) throws IOException {
        this.fieldIndex = new HashMap();
        this.IFDOffset = 8L;
        this.nextIFDOffset = 0L;
        final long filePointer = seekableStream.getFilePointer();
        seekableStream.seek(0L);
        final int unsignedShort = seekableStream.readUnsignedShort();
        if (!isValidEndianTag(unsignedShort)) {
            throw new IllegalArgumentException("TIFFDirectory1");
        }
        this.isBigEndian = (unsignedShort == 19789);
        seekableStream.seek(unsignedInt);
        for (int i = 0; i < n; ++i) {
            seekableStream.seek(unsignedInt + 12L * this.readUnsignedShort(seekableStream));
            unsignedInt = this.readUnsignedInt(seekableStream);
            seekableStream.seek(unsignedInt);
        }
        this.initialize(seekableStream);
        seekableStream.seek(filePointer);
    }
    
    private void initialize(final SeekableStream seekableStream) throws IOException {
        this.IFDOffset = seekableStream.getFilePointer();
        this.numEntries = this.readUnsignedShort(seekableStream);
        this.fields = new TIFFField[this.numEntries];
        for (int i = 0; i < this.numEntries; ++i) {
            final int unsignedShort = this.readUnsignedShort(seekableStream);
            final int unsignedShort2 = this.readUnsignedShort(seekableStream);
            int size = (int)this.readUnsignedInt(seekableStream);
            final long n = seekableStream.getFilePointer() + 4L;
            try {
                if (size * TIFFDirectory.sizeOfType[unsignedShort2] > 4) {
                    seekableStream.seek((int)this.readUnsignedInt(seekableStream));
                }
            }
            catch (ArrayIndexOutOfBoundsException ex) {
                System.err.println(unsignedShort + " " + "TIFFDirectory4");
                seekableStream.seek(n);
                continue;
            }
            this.fieldIndex.put(new Integer(unsignedShort), new Integer(i));
            Object o = null;
            switch (unsignedShort2) {
                case 1:
                case 2:
                case 6:
                case 7: {
                    final byte[] bytes = new byte[size];
                    seekableStream.readFully(bytes, 0, size);
                    if (unsignedShort2 == 2) {
                        int j = 0;
                        int offset = 0;
                        final ArrayList list = new ArrayList<String>();
                        while (j < size) {
                            while (j < size && bytes[j++] != 0) {}
                            list.add(new String(bytes, offset, j - offset));
                            offset = j;
                        }
                        size = list.size();
                        final String[] array = new String[size];
                        list.toArray(array);
                        o = array;
                        break;
                    }
                    o = bytes;
                    break;
                }
                case 3: {
                    final char[] array2 = new char[size];
                    for (int k = 0; k < size; ++k) {
                        array2[k] = (char)this.readUnsignedShort(seekableStream);
                    }
                    o = array2;
                    break;
                }
                case 4: {
                    final long[] array3 = new long[size];
                    for (int l = 0; l < size; ++l) {
                        array3[l] = this.readUnsignedInt(seekableStream);
                    }
                    o = array3;
                    break;
                }
                case 5: {
                    final long[][] array4 = new long[size][2];
                    for (int n2 = 0; n2 < size; ++n2) {
                        array4[n2][0] = this.readUnsignedInt(seekableStream);
                        array4[n2][1] = this.readUnsignedInt(seekableStream);
                    }
                    o = array4;
                    break;
                }
                case 8: {
                    final short[] array5 = new short[size];
                    for (int n3 = 0; n3 < size; ++n3) {
                        array5[n3] = this.readShort(seekableStream);
                    }
                    o = array5;
                    break;
                }
                case 9: {
                    final int[] array6 = new int[size];
                    for (int n4 = 0; n4 < size; ++n4) {
                        array6[n4] = this.readInt(seekableStream);
                    }
                    o = array6;
                    break;
                }
                case 10: {
                    final int[][] array7 = new int[size][2];
                    for (int n5 = 0; n5 < size; ++n5) {
                        array7[n5][0] = this.readInt(seekableStream);
                        array7[n5][1] = this.readInt(seekableStream);
                    }
                    o = array7;
                    break;
                }
                case 11: {
                    final float[] array8 = new float[size];
                    for (int n6 = 0; n6 < size; ++n6) {
                        array8[n6] = this.readFloat(seekableStream);
                    }
                    o = array8;
                    break;
                }
                case 12: {
                    final double[] array9 = new double[size];
                    for (int n7 = 0; n7 < size; ++n7) {
                        array9[n7] = this.readDouble(seekableStream);
                    }
                    o = array9;
                    break;
                }
                default: {
                    System.err.println("TIFFDirectory0");
                    break;
                }
            }
            this.fields[i] = new TIFFField(unsignedShort, unsignedShort2, size, o);
            seekableStream.seek(n);
        }
        this.nextIFDOffset = this.readUnsignedInt(seekableStream);
    }
    
    public int getNumEntries() {
        return this.numEntries;
    }
    
    public TIFFField getField(final int value) {
        final Integer n = this.fieldIndex.get(new Integer(value));
        if (n == null) {
            return null;
        }
        return this.fields[n];
    }
    
    public boolean isTagPresent(final int value) {
        return this.fieldIndex.containsKey(new Integer(value));
    }
    
    public int[] getTags() {
        final int[] array = new int[this.fieldIndex.size()];
        final Iterator<Integer> iterator = this.fieldIndex.keySet().iterator();
        int n = 0;
        while (iterator.hasNext()) {
            array[n++] = iterator.next();
        }
        return array;
    }
    
    public TIFFField[] getFields() {
        return this.fields;
    }
    
    public byte getFieldAsByte(final int value, final int n) {
        return this.fields[this.fieldIndex.get(new Integer(value))].getAsBytes()[n];
    }
    
    public byte getFieldAsByte(final int n) {
        return this.getFieldAsByte(n, 0);
    }
    
    public long getFieldAsLong(final int value, final int n) {
        return this.fields[this.fieldIndex.get(new Integer(value))].getAsLong(n);
    }
    
    public long getFieldAsLong(final int n) {
        return this.getFieldAsLong(n, 0);
    }
    
    public float getFieldAsFloat(final int value, final int n) {
        return this.fields[this.fieldIndex.get(new Integer(value))].getAsFloat(n);
    }
    
    public float getFieldAsFloat(final int n) {
        return this.getFieldAsFloat(n, 0);
    }
    
    public double getFieldAsDouble(final int value, final int n) {
        return this.fields[this.fieldIndex.get(new Integer(value))].getAsDouble(n);
    }
    
    public double getFieldAsDouble(final int n) {
        return this.getFieldAsDouble(n, 0);
    }
    
    private short readShort(final SeekableStream seekableStream) throws IOException {
        if (this.isBigEndian) {
            return seekableStream.readShort();
        }
        return seekableStream.readShortLE();
    }
    
    private int readUnsignedShort(final SeekableStream seekableStream) throws IOException {
        if (this.isBigEndian) {
            return seekableStream.readUnsignedShort();
        }
        return seekableStream.readUnsignedShortLE();
    }
    
    private int readInt(final SeekableStream seekableStream) throws IOException {
        if (this.isBigEndian) {
            return seekableStream.readInt();
        }
        return seekableStream.readIntLE();
    }
    
    private long readUnsignedInt(final SeekableStream seekableStream) throws IOException {
        if (this.isBigEndian) {
            return seekableStream.readUnsignedInt();
        }
        return seekableStream.readUnsignedIntLE();
    }
    
    private long readLong(final SeekableStream seekableStream) throws IOException {
        if (this.isBigEndian) {
            return seekableStream.readLong();
        }
        return seekableStream.readLongLE();
    }
    
    private float readFloat(final SeekableStream seekableStream) throws IOException {
        if (this.isBigEndian) {
            return seekableStream.readFloat();
        }
        return seekableStream.readFloatLE();
    }
    
    private double readDouble(final SeekableStream seekableStream) throws IOException {
        if (this.isBigEndian) {
            return seekableStream.readDouble();
        }
        return seekableStream.readDoubleLE();
    }
    
    private static int readUnsignedShort(final SeekableStream seekableStream, final boolean b) throws IOException {
        if (b) {
            return seekableStream.readUnsignedShort();
        }
        return seekableStream.readUnsignedShortLE();
    }
    
    private static long readUnsignedInt(final SeekableStream seekableStream, final boolean b) throws IOException {
        if (b) {
            return seekableStream.readUnsignedInt();
        }
        return seekableStream.readUnsignedIntLE();
    }
    
    public static int getNumDirectories(final SeekableStream seekableStream) throws IOException {
        final long filePointer = seekableStream.getFilePointer();
        seekableStream.seek(0L);
        final int unsignedShort = seekableStream.readUnsignedShort();
        if (!isValidEndianTag(unsignedShort)) {
            throw new IllegalArgumentException("TIFFDirectory1");
        }
        final boolean b = unsignedShort == 19789;
        if (readUnsignedShort(seekableStream, b) != 42) {
            throw new IllegalArgumentException("TIFFDirectory2");
        }
        seekableStream.seek(4L);
        long n = readUnsignedInt(seekableStream, b);
        int n2 = 0;
        while (n != 0L) {
            ++n2;
            seekableStream.seek(n);
            seekableStream.skip(12L * readUnsignedShort(seekableStream, b));
            n = readUnsignedInt(seekableStream, b);
        }
        seekableStream.seek(filePointer);
        return n2;
    }
    
    public boolean isBigEndian() {
        return this.isBigEndian;
    }
    
    public long getIFDOffset() {
        return this.IFDOffset;
    }
    
    public long getNextIFDOffset() {
        return this.nextIFDOffset;
    }
    
    static {
        sizeOfType = new int[] { 0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8 };
    }
}
