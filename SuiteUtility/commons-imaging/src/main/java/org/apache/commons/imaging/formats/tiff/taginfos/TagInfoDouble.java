// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoDouble extends TagInfo
{
    public TagInfoDouble(final String name, final int tag, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.DOUBLE, 1, directoryType);
    }
    
    public double getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toDouble(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final double value) {
        return ByteConversions.toBytes(value, byteOrder);
    }
}
