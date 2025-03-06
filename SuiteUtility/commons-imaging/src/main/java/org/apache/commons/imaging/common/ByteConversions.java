// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.nio.ByteOrder;

public final class ByteConversions
{
    private ByteConversions() {
    }
    
    public static byte[] toBytes(final short value, final ByteOrder byteOrder) {
        final byte[] result = new byte[2];
        toBytes(value, byteOrder, result, 0);
        return result;
    }
    
    public static byte[] toBytes(final short[] values, final ByteOrder byteOrder) {
        return toBytes(values, 0, values.length, byteOrder);
    }
    
    private static byte[] toBytes(final short[] values, final int offset, final int length, final ByteOrder byteOrder) {
        final byte[] result = new byte[length * 2];
        for (int i = 0; i < length; ++i) {
            toBytes(values[offset + i], byteOrder, result, i * 2);
        }
        return result;
    }
    
    private static void toBytes(final short value, final ByteOrder byteOrder, final byte[] result, final int offset) {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            result[offset + 0] = (byte)(value >> 8);
            result[offset + 1] = (byte)(value >> 0);
        }
        else {
            result[offset + 1] = (byte)(value >> 8);
            result[offset + 0] = (byte)(value >> 0);
        }
    }
    
    public static byte[] toBytes(final int value, final ByteOrder byteOrder) {
        final byte[] result = new byte[4];
        toBytes(value, byteOrder, result, 0);
        return result;
    }
    
    public static byte[] toBytes(final int[] values, final ByteOrder byteOrder) {
        return toBytes(values, 0, values.length, byteOrder);
    }
    
    private static byte[] toBytes(final int[] values, final int offset, final int length, final ByteOrder byteOrder) {
        final byte[] result = new byte[length * 4];
        for (int i = 0; i < length; ++i) {
            toBytes(values[offset + i], byteOrder, result, i * 4);
        }
        return result;
    }
    
    private static void toBytes(final int value, final ByteOrder byteOrder, final byte[] result, final int offset) {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            result[offset + 0] = (byte)(value >> 24);
            result[offset + 1] = (byte)(value >> 16);
            result[offset + 2] = (byte)(value >> 8);
            result[offset + 3] = (byte)(value >> 0);
        }
        else {
            result[offset + 3] = (byte)(value >> 24);
            result[offset + 2] = (byte)(value >> 16);
            result[offset + 1] = (byte)(value >> 8);
            result[offset + 0] = (byte)(value >> 0);
        }
    }
    
    public static byte[] toBytes(final float value, final ByteOrder byteOrder) {
        final byte[] result = new byte[4];
        toBytes(value, byteOrder, result, 0);
        return result;
    }
    
    public static byte[] toBytes(final float[] values, final ByteOrder byteOrder) {
        return toBytes(values, 0, values.length, byteOrder);
    }
    
    private static byte[] toBytes(final float[] values, final int offset, final int length, final ByteOrder byteOrder) {
        final byte[] result = new byte[length * 4];
        for (int i = 0; i < length; ++i) {
            toBytes(values[offset + i], byteOrder, result, i * 4);
        }
        return result;
    }
    
    private static void toBytes(final float value, final ByteOrder byteOrder, final byte[] result, final int offset) {
        final int bits = Float.floatToRawIntBits(value);
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            result[offset + 0] = (byte)(0xFF & bits >> 0);
            result[offset + 1] = (byte)(0xFF & bits >> 8);
            result[offset + 2] = (byte)(0xFF & bits >> 16);
            result[offset + 3] = (byte)(0xFF & bits >> 24);
        }
        else {
            result[offset + 3] = (byte)(0xFF & bits >> 0);
            result[offset + 2] = (byte)(0xFF & bits >> 8);
            result[offset + 1] = (byte)(0xFF & bits >> 16);
            result[offset + 0] = (byte)(0xFF & bits >> 24);
        }
    }
    
    public static byte[] toBytes(final double value, final ByteOrder byteOrder) {
        final byte[] result = new byte[8];
        toBytes(value, byteOrder, result, 0);
        return result;
    }
    
    public static byte[] toBytes(final double[] values, final ByteOrder byteOrder) {
        return toBytes(values, 0, values.length, byteOrder);
    }
    
    private static byte[] toBytes(final double[] values, final int offset, final int length, final ByteOrder byteOrder) {
        final byte[] result = new byte[length * 8];
        for (int i = 0; i < length; ++i) {
            toBytes(values[offset + i], byteOrder, result, i * 8);
        }
        return result;
    }
    
    private static void toBytes(final double value, final ByteOrder byteOrder, final byte[] result, final int offset) {
        final long bits = Double.doubleToRawLongBits(value);
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            result[offset + 0] = (byte)(0xFFL & bits >> 0);
            result[offset + 1] = (byte)(0xFFL & bits >> 8);
            result[offset + 2] = (byte)(0xFFL & bits >> 16);
            result[offset + 3] = (byte)(0xFFL & bits >> 24);
            result[offset + 4] = (byte)(0xFFL & bits >> 32);
            result[offset + 5] = (byte)(0xFFL & bits >> 40);
            result[offset + 6] = (byte)(0xFFL & bits >> 48);
            result[offset + 7] = (byte)(0xFFL & bits >> 56);
        }
        else {
            result[offset + 7] = (byte)(0xFFL & bits >> 0);
            result[offset + 6] = (byte)(0xFFL & bits >> 8);
            result[offset + 5] = (byte)(0xFFL & bits >> 16);
            result[offset + 4] = (byte)(0xFFL & bits >> 24);
            result[offset + 3] = (byte)(0xFFL & bits >> 32);
            result[offset + 2] = (byte)(0xFFL & bits >> 40);
            result[offset + 1] = (byte)(0xFFL & bits >> 48);
            result[offset + 0] = (byte)(0xFFL & bits >> 56);
        }
    }
    
    public static byte[] toBytes(final RationalNumber value, final ByteOrder byteOrder) {
        final byte[] result = new byte[8];
        toBytes(value, byteOrder, result, 0);
        return result;
    }
    
    public static byte[] toBytes(final RationalNumber[] values, final ByteOrder byteOrder) {
        return toBytes(values, 0, values.length, byteOrder);
    }
    
    private static byte[] toBytes(final RationalNumber[] values, final int offset, final int length, final ByteOrder byteOrder) {
        final byte[] result = new byte[length * 8];
        for (int i = 0; i < length; ++i) {
            toBytes(values[offset + i], byteOrder, result, i * 8);
        }
        return result;
    }
    
    private static void toBytes(final RationalNumber value, final ByteOrder byteOrder, final byte[] result, final int offset) {
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            result[offset + 0] = (byte)(value.numerator >> 24);
            result[offset + 1] = (byte)(value.numerator >> 16);
            result[offset + 2] = (byte)(value.numerator >> 8);
            result[offset + 3] = (byte)(value.numerator >> 0);
            result[offset + 4] = (byte)(value.divisor >> 24);
            result[offset + 5] = (byte)(value.divisor >> 16);
            result[offset + 6] = (byte)(value.divisor >> 8);
            result[offset + 7] = (byte)(value.divisor >> 0);
        }
        else {
            result[offset + 3] = (byte)(value.numerator >> 24);
            result[offset + 2] = (byte)(value.numerator >> 16);
            result[offset + 1] = (byte)(value.numerator >> 8);
            result[offset + 0] = (byte)(value.numerator >> 0);
            result[offset + 7] = (byte)(value.divisor >> 24);
            result[offset + 6] = (byte)(value.divisor >> 16);
            result[offset + 5] = (byte)(value.divisor >> 8);
            result[offset + 4] = (byte)(value.divisor >> 0);
        }
    }
    
    public static short toShort(final byte[] bytes, final ByteOrder byteOrder) {
        return toShort(bytes, 0, byteOrder);
    }
    
    private static short toShort(final byte[] bytes, final int offset, final ByteOrder byteOrder) {
        return (short)toUInt16(bytes, offset, byteOrder);
    }
    
    public static short[] toShorts(final byte[] bytes, final ByteOrder byteOrder) {
        return toShorts(bytes, 0, bytes.length, byteOrder);
    }
    
    private static short[] toShorts(final byte[] bytes, final int offset, final int length, final ByteOrder byteOrder) {
        final short[] result = new short[length / 2];
        for (int i = 0; i < result.length; ++i) {
            result[i] = toShort(bytes, offset + 2 * i, byteOrder);
        }
        return result;
    }
    
    public static int toUInt16(final byte[] bytes, final ByteOrder byteOrder) {
        return toUInt16(bytes, 0, byteOrder);
    }
    
    public static int toUInt16(final byte[] bytes, final int offset, final ByteOrder byteOrder) {
        final int byte0 = 0xFF & bytes[offset + 0];
        final int byte2 = 0xFF & bytes[offset + 1];
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return byte0 << 8 | byte2;
        }
        return byte2 << 8 | byte0;
    }
    
    public static int[] toUInt16s(final byte[] bytes, final ByteOrder byteOrder) {
        return toUInt16s(bytes, 0, bytes.length, byteOrder);
    }
    
    private static int[] toUInt16s(final byte[] bytes, final int offset, final int length, final ByteOrder byteOrder) {
        final int[] result = new int[length / 2];
        for (int i = 0; i < result.length; ++i) {
            result[i] = toUInt16(bytes, offset + 2 * i, byteOrder);
        }
        return result;
    }
    
    public static int toInt(final byte[] bytes, final ByteOrder byteOrder) {
        return toInt(bytes, 0, byteOrder);
    }
    
    public static int toInt(final byte[] bytes, final int offset, final ByteOrder byteOrder) {
        final int byte0 = 0xFF & bytes[offset + 0];
        final int byte2 = 0xFF & bytes[offset + 1];
        final int byte3 = 0xFF & bytes[offset + 2];
        final int byte4 = 0xFF & bytes[offset + 3];
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return byte0 << 24 | byte2 << 16 | byte3 << 8 | byte4;
        }
        return byte4 << 24 | byte3 << 16 | byte2 << 8 | byte0;
    }
    
    public static int[] toInts(final byte[] bytes, final ByteOrder byteOrder) {
        return toInts(bytes, 0, bytes.length, byteOrder);
    }
    
    private static int[] toInts(final byte[] bytes, final int offset, final int length, final ByteOrder byteOrder) {
        final int[] result = new int[length / 4];
        for (int i = 0; i < result.length; ++i) {
            result[i] = toInt(bytes, offset + 4 * i, byteOrder);
        }
        return result;
    }
    
    public static float toFloat(final byte[] bytes, final ByteOrder byteOrder) {
        return toFloat(bytes, 0, byteOrder);
    }
    
    private static float toFloat(final byte[] bytes, final int offset, final ByteOrder byteOrder) {
        final int byte0 = 0xFF & bytes[offset + 0];
        final int byte2 = 0xFF & bytes[offset + 1];
        final int byte3 = 0xFF & bytes[offset + 2];
        final int byte4 = 0xFF & bytes[offset + 3];
        int bits;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            bits = (byte0 << 24 | byte2 << 16 | byte3 << 8 | byte4 << 0);
        }
        else {
            bits = (byte4 << 24 | byte3 << 16 | byte2 << 8 | byte0 << 0);
        }
        return Float.intBitsToFloat(bits);
    }
    
    public static float[] toFloats(final byte[] bytes, final ByteOrder byteOrder) {
        return toFloats(bytes, 0, bytes.length, byteOrder);
    }
    
    private static float[] toFloats(final byte[] bytes, final int offset, final int length, final ByteOrder byteOrder) {
        final float[] result = new float[length / 4];
        for (int i = 0; i < result.length; ++i) {
            result[i] = toFloat(bytes, offset + 4 * i, byteOrder);
        }
        return result;
    }
    
    public static double toDouble(final byte[] bytes, final ByteOrder byteOrder) {
        return toDouble(bytes, 0, byteOrder);
    }
    
    private static double toDouble(final byte[] bytes, final int offset, final ByteOrder byteOrder) {
        final long byte0 = 0xFFL & (long)bytes[offset + 0];
        final long byte2 = 0xFFL & (long)bytes[offset + 1];
        final long byte3 = 0xFFL & (long)bytes[offset + 2];
        final long byte4 = 0xFFL & (long)bytes[offset + 3];
        final long byte5 = 0xFFL & (long)bytes[offset + 4];
        final long byte6 = 0xFFL & (long)bytes[offset + 5];
        final long byte7 = 0xFFL & (long)bytes[offset + 6];
        final long byte8 = 0xFFL & (long)bytes[offset + 7];
        long bits;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            bits = (byte0 << 56 | byte2 << 48 | byte3 << 40 | byte4 << 32 | byte5 << 24 | byte6 << 16 | byte7 << 8 | byte8 << 0);
        }
        else {
            bits = (byte8 << 56 | byte7 << 48 | byte6 << 40 | byte5 << 32 | byte4 << 24 | byte3 << 16 | byte2 << 8 | byte0 << 0);
        }
        return Double.longBitsToDouble(bits);
    }
    
    public static double[] toDoubles(final byte[] bytes, final ByteOrder byteOrder) {
        return toDoubles(bytes, 0, bytes.length, byteOrder);
    }
    
    private static double[] toDoubles(final byte[] bytes, final int offset, final int length, final ByteOrder byteOrder) {
        final double[] result = new double[length / 8];
        for (int i = 0; i < result.length; ++i) {
            result[i] = toDouble(bytes, offset + 8 * i, byteOrder);
        }
        return result;
    }
    
    public static RationalNumber toRational(final byte[] bytes, final ByteOrder byteOrder) {
        return toRational(bytes, 0, byteOrder);
    }
    
    private static RationalNumber toRational(final byte[] bytes, final int offset, final ByteOrder byteOrder) {
        final int byte0 = 0xFF & bytes[offset + 0];
        final int byte2 = 0xFF & bytes[offset + 1];
        final int byte3 = 0xFF & bytes[offset + 2];
        final int byte4 = 0xFF & bytes[offset + 3];
        final int byte5 = 0xFF & bytes[offset + 4];
        final int byte6 = 0xFF & bytes[offset + 5];
        final int byte7 = 0xFF & bytes[offset + 6];
        final int byte8 = 0xFF & bytes[offset + 7];
        int numerator;
        int divisor;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            numerator = (byte0 << 24 | byte2 << 16 | byte3 << 8 | byte4);
            divisor = (byte5 << 24 | byte6 << 16 | byte7 << 8 | byte8);
        }
        else {
            numerator = (byte4 << 24 | byte3 << 16 | byte2 << 8 | byte0);
            divisor = (byte8 << 24 | byte7 << 16 | byte6 << 8 | byte5);
        }
        return new RationalNumber(numerator, divisor);
    }
    
    public static RationalNumber[] toRationals(final byte[] bytes, final ByteOrder byteOrder) {
        return toRationals(bytes, 0, bytes.length, byteOrder);
    }
    
    private static RationalNumber[] toRationals(final byte[] bytes, final int offset, final int length, final ByteOrder byteOrder) {
        final RationalNumber[] result = new RationalNumber[length / 8];
        for (int i = 0; i < result.length; ++i) {
            result[i] = toRational(bytes, offset + 8 * i, byteOrder);
        }
        return result;
    }
}
