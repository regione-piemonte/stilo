// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.ByteConversions;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoFloats extends TagInfo
{
    public TagInfoFloats(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.FLOAT, length, directoryType);
    }
    
    public float[] getValue(final ByteOrder byteOrder, final byte[] bytes) {
        return ByteConversions.toFloats(bytes, byteOrder);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final float... values) {
        return ByteConversions.toBytes(values, byteOrder);
    }
}
