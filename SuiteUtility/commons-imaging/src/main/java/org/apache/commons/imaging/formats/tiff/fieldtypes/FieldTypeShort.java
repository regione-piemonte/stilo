// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.fieldtypes;

import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.formats.tiff.TiffField;

public class FieldTypeShort extends FieldType
{
    public FieldTypeShort(final int type, final String name) {
        super(type, name, 2);
    }
    
    @Override
    public Object getValue(final TiffField entry) {
        final byte[] bytes = entry.getByteArrayValue();
        if (entry.getCount() == 1L) {
            return ByteConversions.toShort(bytes, entry.getByteOrder());
        }
        return ByteConversions.toShorts(bytes, entry.getByteOrder());
    }
    
    @Override
    public byte[] writeData(final Object o, final ByteOrder byteOrder) throws ImageWriteException {
        if (o instanceof Short) {
            return ByteConversions.toBytes((short)o, byteOrder);
        }
        if (o instanceof short[]) {
            final short[] numbers = (short[])o;
            return ByteConversions.toBytes(numbers, byteOrder);
        }
        if (o instanceof Short[]) {
            final Short[] numbers2 = (Short[])o;
            final short[] values = new short[numbers2.length];
            for (int i = 0; i < values.length; ++i) {
                values[i] = numbers2[i];
            }
            return ByteConversions.toBytes(values, byteOrder);
        }
        throw new ImageWriteException("Invalid data", o);
    }
}
