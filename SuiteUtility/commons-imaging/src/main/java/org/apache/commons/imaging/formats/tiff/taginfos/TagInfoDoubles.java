// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoDoubles extends TagInfo
{
    public TagInfoDoubles(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.DOUBLE, length, directoryType);
    }
    
    public double[] getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toDoubles(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final double... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
}
