// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoShort extends TagInfo
{
    public TagInfoShort(final String name, final int tag, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.SHORT, 1, directoryType);
    }
    
    public short getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toShort(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final short value) {
        return ByteConversions.toBytes(value, byteOrder);
    }
}
