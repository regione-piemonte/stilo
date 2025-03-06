// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.common.RationalNumber;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoRationals extends TagInfo
{
    public TagInfoRationals(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.RATIONAL, length, directoryType);
    }
    
    public RationalNumber[] getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toRationals(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final RationalNumber... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
}
