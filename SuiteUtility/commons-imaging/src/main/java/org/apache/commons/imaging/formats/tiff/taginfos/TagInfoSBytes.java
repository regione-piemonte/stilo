// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoSBytes extends TagInfo
{
    public TagInfoSBytes(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.SBYTE, length, directoryType);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final byte... values) {
        return values;
    }
}
