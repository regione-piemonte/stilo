// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoLong extends TagInfo
{
    public TagInfoLong(final String name, final int tag, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.LONG, 1, directoryType);
    }
    
    public TagInfoLong(final String name, final int tag, final TiffDirectoryType directoryType, final boolean isOffset) {
        super(name, tag, FieldType.LONG, 1, directoryType, isOffset);
    }
    
    public int getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toInt(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final int value) {
        return ByteConversions.toBytes(value, byteOrder);
    }
}
