// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.TiffField;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoXpString extends TagInfo
{
    public TagInfoXpString(final String name, final int tag, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.BYTE, -1, directoryType);
    }
    
    @Override
    public byte[] encodeValue(final FieldType fieldType, final Object value, final ByteOrder byteOrder) throws ImageWriteException {
        if (!(value instanceof String)) {
            throw new ImageWriteException("Text value not String", value);
        }
        final String s = (String)value;
        final byte[] bytes = s.getBytes(StandardCharsets.UTF_16LE);
        final byte[] paddedBytes = new byte[bytes.length + 2];
        System.arraycopy(bytes, 0, paddedBytes, 0, bytes.length);
        return paddedBytes;
    }
    
    @Override
    public String getValue(final TiffField entry) throws ImageReadException {
        if (entry.getFieldType() != FieldType.BYTE) {
            throw new ImageReadException("Text field not encoded as bytes.");
        }
        final byte[] bytes = entry.getByteArrayValue();
        int length;
        if (bytes.length >= 2 && bytes[bytes.length - 1] == 0 && bytes[bytes.length - 2] == 0) {
            length = bytes.length - 2;
        }
        else {
            length = bytes.length;
        }
        return new String(bytes, 0, length, StandardCharsets.UTF_16LE);
    }
}
