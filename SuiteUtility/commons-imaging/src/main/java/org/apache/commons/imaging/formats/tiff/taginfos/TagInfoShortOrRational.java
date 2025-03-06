// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoShortOrRational extends TagInfo
{
    public TagInfoShortOrRational(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.SHORT_OR_RATIONAL, length, directoryType, false);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final short... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final RationalNumber... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
}
