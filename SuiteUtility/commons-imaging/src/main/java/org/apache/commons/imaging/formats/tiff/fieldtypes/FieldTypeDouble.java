// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.fieldtypes;

import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.formats.tiff.TiffField;

public class FieldTypeDouble extends FieldType
{
    public FieldTypeDouble(final int type, final String name) {
        super(type, name, 8);
    }
    
    @Override
    public Object getValue(final TiffField entry) {
        final byte[] bytes = entry.getByteArrayValue();
        if (entry.getCount() == 1L) {
            return ByteConversions.toDouble(bytes, entry.getByteOrder());
        }
        return ByteConversions.toDoubles(bytes, entry.getByteOrder());
    }
    
    @Override
    public byte[] writeData(final Object o, final ByteOrder byteOrder) throws ImageWriteException {
        if (o instanceof Double) {
            return ByteConversions.toBytes((double)o, byteOrder);
        }
        if (o instanceof double[]) {
            final double[] numbers = (double[])o;
            return ByteConversions.toBytes(numbers, byteOrder);
        }
        if (o instanceof Double[]) {
            final Double[] numbers2 = (Double[])o;
            final double[] values = new double[numbers2.length];
            for (int i = 0; i < values.length; ++i) {
                values[i] = numbers2[i];
            }
            return ByteConversions.toBytes(values, byteOrder);
        }
        throw new ImageWriteException("Invalid data", o);
    }
}
