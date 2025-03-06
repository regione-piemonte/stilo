// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.tiff;

import java.io.Serializable;

public class TIFFField implements Comparable, Serializable
{
    public static final int TIFF_BYTE = 1;
    public static final int TIFF_ASCII = 2;
    public static final int TIFF_SHORT = 3;
    public static final int TIFF_LONG = 4;
    public static final int TIFF_RATIONAL = 5;
    public static final int TIFF_SBYTE = 6;
    public static final int TIFF_UNDEFINED = 7;
    public static final int TIFF_SSHORT = 8;
    public static final int TIFF_SLONG = 9;
    public static final int TIFF_SRATIONAL = 10;
    public static final int TIFF_FLOAT = 11;
    public static final int TIFF_DOUBLE = 12;
    int tag;
    int type;
    int count;
    Object data;
    
    TIFFField() {
    }
    
    public TIFFField(final int tag, final int type, final int count, final Object data) {
        this.tag = tag;
        this.type = type;
        this.count = count;
        this.data = data;
    }
    
    public int getTag() {
        return this.tag;
    }
    
    public int getType() {
        return this.type;
    }
    
    public int getCount() {
        return this.count;
    }
    
    public byte[] getAsBytes() {
        return (byte[])this.data;
    }
    
    public char[] getAsChars() {
        return (char[])this.data;
    }
    
    public short[] getAsShorts() {
        return (short[])this.data;
    }
    
    public int[] getAsInts() {
        return (int[])this.data;
    }
    
    public long[] getAsLongs() {
        return (long[])this.data;
    }
    
    public float[] getAsFloats() {
        return (float[])this.data;
    }
    
    public double[] getAsDoubles() {
        return (double[])this.data;
    }
    
    public int[][] getAsSRationals() {
        return (int[][])this.data;
    }
    
    public long[][] getAsRationals() {
        return (long[][])this.data;
    }
    
    public int getAsInt(final int n) {
        switch (this.type) {
            case 1:
            case 7: {
                return ((byte[])this.data)[n] & 0xFF;
            }
            case 6: {
                return ((byte[])this.data)[n];
            }
            case 3: {
                return ((char[])this.data)[n] & '\uffff';
            }
            case 8: {
                return ((short[])this.data)[n];
            }
            case 9: {
                return ((int[])this.data)[n];
            }
            default: {
                throw new ClassCastException();
            }
        }
    }
    
    public long getAsLong(final int n) {
        switch (this.type) {
            case 1:
            case 7: {
                return ((byte[])this.data)[n] & 0xFF;
            }
            case 6: {
                return ((byte[])this.data)[n];
            }
            case 3: {
                return ((char[])this.data)[n] & '\uffff';
            }
            case 8: {
                return ((short[])this.data)[n];
            }
            case 9: {
                return ((int[])this.data)[n];
            }
            case 4: {
                return ((long[])this.data)[n];
            }
            default: {
                throw new ClassCastException();
            }
        }
    }
    
    public float getAsFloat(final int n) {
        switch (this.type) {
            case 1: {
                return (float)(((byte[])this.data)[n] & 0xFF);
            }
            case 6: {
                return ((byte[])this.data)[n];
            }
            case 3: {
                return (float)(((char[])this.data)[n] & '\uffff');
            }
            case 8: {
                return ((short[])this.data)[n];
            }
            case 9: {
                return (float)((int[])this.data)[n];
            }
            case 4: {
                return (float)((long[])this.data)[n];
            }
            case 11: {
                return ((float[])this.data)[n];
            }
            case 12: {
                return (float)((double[])this.data)[n];
            }
            case 10: {
                final int[] asSRational = this.getAsSRational(n);
                return (float)(asSRational[0] / (double)asSRational[1]);
            }
            case 5: {
                final long[] asRational = this.getAsRational(n);
                return (float)(asRational[0] / (double)asRational[1]);
            }
            default: {
                throw new ClassCastException();
            }
        }
    }
    
    public double getAsDouble(final int n) {
        switch (this.type) {
            case 1: {
                return ((byte[])this.data)[n] & 0xFF;
            }
            case 6: {
                return ((byte[])this.data)[n];
            }
            case 3: {
                return ((char[])this.data)[n] & '\uffff';
            }
            case 8: {
                return ((short[])this.data)[n];
            }
            case 9: {
                return ((int[])this.data)[n];
            }
            case 4: {
                return (double)((long[])this.data)[n];
            }
            case 11: {
                return ((float[])this.data)[n];
            }
            case 12: {
                return ((double[])this.data)[n];
            }
            case 10: {
                final int[] asSRational = this.getAsSRational(n);
                return asSRational[0] / (double)asSRational[1];
            }
            case 5: {
                final long[] asRational = this.getAsRational(n);
                return asRational[0] / (double)asRational[1];
            }
            default: {
                throw new ClassCastException();
            }
        }
    }
    
    public String getAsString(final int n) {
        return ((String[])this.data)[n];
    }
    
    public int[] getAsSRational(final int n) {
        return ((int[][])this.data)[n];
    }
    
    public long[] getAsRational(final int n) {
        return ((long[][])this.data)[n];
    }
    
    public int compareTo(final Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
        final int tag = ((TIFFField)o).getTag();
        if (this.tag < tag) {
            return -1;
        }
        if (this.tag > tag) {
            return 1;
        }
        return 0;
    }
}
