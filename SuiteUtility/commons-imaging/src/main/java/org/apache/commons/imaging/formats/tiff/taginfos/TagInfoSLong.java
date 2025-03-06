// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoSLong extends TagInfo
{
    public TagInfoSLong(final String name, final int tag, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.SLONG, 1, directoryType);
    }
    
    public int getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toInt(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final int value) {
        return ByteConversions.toBytes(value, byteOrder);
    }
}
