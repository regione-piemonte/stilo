// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.fieldtypes;

import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.common.RationalNumber;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.formats.tiff.TiffField;

public class FieldTypeRational extends FieldType
{
    public FieldTypeRational(final int type, final String name) {
        super(type, name, 8);
    }
    
    @Override
    public Object getValue(final TiffField entry) {
        final byte[] bytes = entry.getByteArrayValue();
        if (entry.getCount() == 1L) {
            return ByteConversions.toRational(bytes, entry.getByteOrder());
        }
        return ByteConversions.toRationals(bytes, entry.getByteOrder());
    }
    
    @Override
    public byte[] writeData(final Object o, final ByteOrder byteOrder) throws ImageWriteException {
        if (o instanceof RationalNumber) {
            return ByteConversions.toBytes((RationalNumber)o, byteOrder);
        }
        if (o instanceof RationalNumber[]) {
            return ByteConversions.toBytes((RationalNumber[])o, byteOrder);
        }
        if (o instanceof Number) {
            final Number number = (Number)o;
            final RationalNumber rationalNumber = RationalNumber.valueOf(number.doubleValue());
            return ByteConversions.toBytes(rationalNumber, byteOrder);
        }
        if (o instanceof Number[]) {
            final Number[] numbers = (Number[])o;
            final RationalNumber[] rationalNumbers = new RationalNumber[numbers.length];
            for (int i = 0; i < numbers.length; ++i) {
                final Number number2 = numbers[i];
                rationalNumbers[i] = RationalNumber.valueOf(number2.doubleValue());
            }
            return ByteConversions.toBytes(rationalNumbers, byteOrder);
        }
        if (o instanceof double[]) {
            final double[] numbers2 = (double[])o;
            final RationalNumber[] rationalNumbers = new RationalNumber[numbers2.length];
            for (int i = 0; i < numbers2.length; ++i) {
                final double number3 = numbers2[i];
                rationalNumbers[i] = RationalNumber.valueOf(number3);
            }
            return ByteConversions.toBytes(rationalNumbers, byteOrder);
        }
        throw new ImageWriteException("Invalid data", o);
    }
}
