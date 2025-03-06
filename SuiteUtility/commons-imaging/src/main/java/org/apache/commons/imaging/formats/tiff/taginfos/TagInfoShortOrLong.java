// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoShortOrLong extends TagInfo
{
    public TagInfoShortOrLong(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.SHORT_OR_LONG, length, directoryType, false);
    }
    
    public TagInfoShortOrLong(final String name, final int tag, final int length, final TiffDirectoryType directoryType, final boolean isOffset) {
        super(name, tag, FieldType.SHORT_OR_LONG, length, directoryType, isOffset);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final short... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final int... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
}
