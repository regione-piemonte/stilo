// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.fieldtypes;

import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.TiffField;

public class FieldTypeByte extends FieldType
{
    public FieldTypeByte(final int type, final String name) {
        super(type, name, 1);
    }
    
    @Override
    public Object getValue(final TiffField entry) {
        final byte[] bytes = entry.getByteArrayValue();
        if (entry.getCount() == 1L) {
            return bytes[0];
        }
        return bytes;
    }
    
    @Override
    public byte[] writeData(final Object o, final ByteOrder byteOrder) throws ImageWriteException {
        if (o instanceof Byte) {
            return new byte[] { (byte)o };
        }
        if (o instanceof byte[]) {
            return (byte[])o;
        }
        throw new ImageWriteException("Invalid data", o);
    }
}
