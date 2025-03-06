// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoByteOrShort extends TagInfo
{
    public TagInfoByteOrShort(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.BYTE_OR_SHORT, length, directoryType);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final byte... values) {
        return values;
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final short... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
}
