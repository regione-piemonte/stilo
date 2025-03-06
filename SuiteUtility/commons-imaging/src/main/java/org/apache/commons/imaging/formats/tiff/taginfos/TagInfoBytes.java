// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import java.nio.ByteOrder;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoBytes extends TagInfo
{
    public TagInfoBytes(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.BYTE, length, directoryType);
    }
    
    public TagInfoBytes(final String name, final int tag, final List<FieldType> fieldTypes, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, fieldTypes, length, directoryType);
    }
    
    public TagInfoBytes(final String name, final int tag, final FieldType fieldType, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, fieldType, length, directoryType);
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final byte... values) {
        return values;
    }
}
