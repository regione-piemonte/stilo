// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.fieldtypes;

import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.formats.tiff.TiffField;

public class FieldTypeLong extends FieldType
{
    public FieldTypeLong(final int type, final String name) {
        super(type, name, 4);
    }
    
    @Override
    public Object getValue(final TiffField entry) {
        final byte[] bytes = entry.getByteArrayValue();
        if (entry.getCount() == 1L) {
            return ByteConversions.toInt(bytes, entry.getByteOrder());
        }
        return ByteConversions.toInts(bytes, entry.getByteOrder());
    }
    
    @Override
    public byte[] writeData(final Object o, final ByteOrder byteOrder) throws ImageWriteException {
        if (o instanceof Integer) {
            return ByteConversions.toBytes((int)o, byteOrder);
        }
        if (o instanceof int[]) {
            final int[] numbers = (int[])o;
            return ByteConversions.toBytes(numbers, byteOrder);
        }
        if (o instanceof Integer[]) {
            final Integer[] numbers2 = (Integer[])o;
            final int[] values = new int[numbers2.length];
            for (int i = 0; i < values.length; ++i) {
                values[i] = numbers2[i];
            }
            return ByteConversions.toBytes(values, byteOrder);
        }
        throw new ImageWriteException("Invalid data", o);
    }
}
