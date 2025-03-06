// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.common.RationalNumber;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoRational extends TagInfo
{
    public TagInfoRational(final String name, final int tag, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.RATIONAL, 1, directoryType);
    }
    
    public RationalNumber getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toRational(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final RationalNumber value) {
        return ByteConversions.toBytes(value, byteOrder);
    }
}
