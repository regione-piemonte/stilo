// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.fieldtypes;

import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.formats.tiff.TiffField;

public class FieldTypeAscii extends FieldType
{
    public FieldTypeAscii(final int type, final String name) {
        super(type, name, 1);
    }
    
    @Override
    public Object getValue(final TiffField entry) {
        final byte[] bytes = entry.getByteArrayValue();
        int nullCount = 1;
        for (int i = 0; i < bytes.length - 1; ++i) {
            if (bytes[i] == 0) {
                ++nullCount;
            }
        }
        final String[] strings = new String[nullCount];
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
        if (strings.length == 1) {
            return strings[0];
        }
        return strings;
    }
    
    @Override
    public byte[] writeData(final Object o, final ByteOrder byteOrder) throws ImageWriteException {
        if (o instanceof byte[]) {
            final byte[] bytes = (byte[])o;
            final byte[] result = new byte[bytes.length + 1];
            System.arraycopy(bytes, 0, result, 0, bytes.length);
            result[result.length - 1] = 0;
            return result;
        }
        if (o instanceof String) {
            final byte[] bytes = ((String)o).getBytes(StandardCharsets.UTF_8);
            final byte[] result = new byte[bytes.length + 1];
            System.arraycopy(bytes, 0, result, 0, bytes.length);
            result[result.length - 1] = 0;
            return result;
        }
        if (o instanceof String[]) {
            final String[] strings = (String[])o;
            int totalLength = 0;
            for (final String string : strings) {
                final byte[] bytes2 = string.getBytes(StandardCharsets.UTF_8);
                totalLength += bytes2.length + 1;
            }
            final byte[] result2 = new byte[totalLength];
            int position = 0;
            for (final String string2 : strings) {
                final byte[] bytes3 = string2.getBytes(StandardCharsets.UTF_8);
                System.arraycopy(bytes3, 0, result2, position, bytes3.length);
                position += bytes3.length + 1;
            }
            return result2;
        }
        throw new ImageWriteException("Unknown data type: " + o);
    }
}
