// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoLongOrIFD extends TagInfo
{
    public TagInfoLongOrIFD(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.LONG_OR_IFD, length, directoryType);
    }
    
    public TagInfoLongOrIFD(final String name, final int tag, final int length, final TiffDirectoryType directoryType, final boolean isOffset) {
        super(name, tag, FieldType.LONG_OR_IFD, length, directoryType, isOffset);
    }
    
    public int[] getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toInts(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final int... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
}
