// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.fieldtypes;

import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.formats.tiff.TiffField;

public class FieldTypeFloat extends FieldType
{
    public FieldTypeFloat(final int type, final String name) {
        super(type, name, 4);
    }
    
    @Override
    public Object getValue(final TiffField entry) {
        final byte[] bytes = entry.getByteArrayValue();
        if (entry.getCount() == 1L) {
            return ByteConversions.toFloat(bytes, entry.getByteOrder());
        }
        return ByteConversions.toFloats(bytes, entry.getByteOrder());
    }
    
    @Override
    public byte[] writeData(final Object o, final ByteOrder byteOrder) throws ImageWriteException {
        if (o instanceof Float) {
            return ByteConversions.toBytes((float)o, byteOrder);
        }
        if (o instanceof float[]) {
            final float[] numbers = (float[])o;
            return ByteConversions.toBytes(numbers, byteOrder);
        }
        if (o instanceof Float[]) {
            final Float[] numbers2 = (Float[])o;
            final float[] values = new float[numbers2.length];
            for (int i = 0; i < values.length; ++i) {
                values[i] = numbers2[i];
            }
            return ByteConversions.toBytes(values, byteOrder);
        }
        throw new ImageWriteException("Invalid data", o);
    }
}
