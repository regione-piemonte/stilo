// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoSByte extends TagInfo
{
    public TagInfoSByte(final String name, final int tag, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.SBYTE, 1, directoryType);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final byte value) {
        return new byte[] { value };
    }
}
