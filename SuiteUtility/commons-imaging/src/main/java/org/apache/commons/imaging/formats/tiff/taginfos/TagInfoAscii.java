// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.ImageWriteException;
import java.nio.charset.StandardCharsets;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public class TagInfoAscii extends TagInfo
{
    public TagInfoAscii(final String name, final int tag, final int length, final TiffDirectoryType directoryType) {
        super(name, tag, FieldType.ASCII, length, directoryType);
    }
    
    public String[] getValue(final ByteOrder byteOrder, final byte[] bytes) {
        int nullCount = 0;
        for (int i = 0; i < bytes.length - 1; ++i) {
            if (bytes[i] == 0) {
                ++nullCount;
            }
        }
        final String[] strings = new String[nullCount + 1];
        int stringsAdded = 0;
        strings[0] = "";
        int nextStringPos = 0;
        for (int j = 0; j < bytes.length; ++j) {
            if (bytes[j] == 0) {
                final String string = new String(bytes, nextStringPos, j - nextStringPos, StandardCharsets.UTF_8);
                strings[stringsAdded++] = string;
                nextStringPos = j + 1;
            }
        }
        if (nextStringPos < bytes.length) {
            final String string2 = new String(bytes, nextStringPos, bytes.length - nextStringPos, StandardCharsets.UTF_8);
            strings[stringsAdded++] = string2;
        }
        return strings;
    }
    
    public byte[] encodeValue(final ByteOrder byteOrder, final String... values) throws ImageWriteException {
        return FieldType.ASCII.writeData(values, byteOrder);
    }
}
