// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import java.nio.ByteOrder;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoByte extends TagInfo
{
    public TagInfoByte(final String name, final int tag, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.BYTE, 1, directoryType);
    }
    
    public TagInfoByte(final String name, final int tag, final List<FieldType> fieldTypes, final TiffDirectoryType directoryType) {
        super(name, tag, fieldTypes, 1, directoryType);
    }
    
    public TagInfoByte(final String name, final int tag, final FieldType fieldType, final TiffDirectoryType directoryType) {
        super(name, tag, fieldType, 1, directoryType);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final byte value) {
        return new byte[] { value };
    }
}
