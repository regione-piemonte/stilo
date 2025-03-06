// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.common.BinaryFunctions;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.internal.Debug;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.TiffField;
import java.io.UnsupportedEncodingException;
import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;

public final class TagInfoGpsText extends TagInfo
{
    private static final TextEncoding TEXT_ENCODING_ASCII;
    private static final TextEncoding TEXT_ENCODING_JIS;
    private static final TextEncoding TEXT_ENCODING_UNICODE_LE;
    private static final TextEncoding TEXT_ENCODING_UNICODE_BE;
    private static final TextEncoding TEXT_ENCODING_UNDEFINED;
    private static final TextEncoding[] TEXT_ENCODINGS;
    
    public TagInfoGpsText(final String name, final int tag, final TiffDirectoryType exifDirectory) {
        super(name, tag, FieldType.UNDEFINED, -1, exifDirectory);
    }
    
    @Override
    public boolean isText() {
        return true;
    }
    
    @Override
    public byte[] encodeValue(final FieldType fieldType, final Object value, final ByteOrder byteOrder) throws ImageWriteException {
        if (!(value instanceof String)) {
            throw new ImageWriteException("GPS text value not String", value);
        }
        final String s = (String)value;
        try {
            final byte[] asciiBytes = s.getBytes(TagInfoGpsText.TEXT_ENCODING_ASCII.encodingName);
            final String decodedAscii = new String(asciiBytes, TagInfoGpsText.TEXT_ENCODING_ASCII.encodingName);
            if (decodedAscii.equals(s)) {
                final byte[] result = new byte[asciiBytes.length + TagInfoGpsText.TEXT_ENCODING_ASCII.prefix.length];
                System.arraycopy(TagInfoGpsText.TEXT_ENCODING_ASCII.prefix, 0, result, 0, TagInfoGpsText.TEXT_ENCODING_ASCII.prefix.length);
                System.arraycopy(asciiBytes, 0, result, TagInfoGpsText.TEXT_ENCODING_ASCII.prefix.length, asciiBytes.length);
                return result;
            }
            TextEncoding encoding;
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                encoding = TagInfoGpsText.TEXT_ENCODING_UNICODE_BE;
            }
            else {
                encoding = TagInfoGpsText.TEXT_ENCODING_UNICODE_LE;
            }
            final byte[] unicodeBytes = s.getBytes(encoding.encodingName);
            final byte[] result2 = new byte[unicodeBytes.length + encoding.prefix.length];
            System.arraycopy(encoding.prefix, 0, result2, 0, encoding.prefix.length);
            System.arraycopy(unicodeBytes, 0, result2, encoding.prefix.length, unicodeBytes.length);
            return result2;
        }
        catch (UnsupportedEncodingException e) {
            throw new ImageWriteException(e.getMessage(), e);
        }
    }
    
    @Override
    public String getValue(final TiffField entry) throws ImageReadException {
        if (entry.getFieldType() == FieldType.ASCII) {
            final Object object = FieldType.ASCII.getValue(entry);
            if (object instanceof String) {
                return (String)object;
            }
            if (object instanceof String[]) {
                return ((String[])object)[0];
            }
            throw new ImageReadException("Unexpected ASCII type decoded");
        }
        else {
            if (entry.getFieldType() != FieldType.UNDEFINED) {
                if (entry.getFieldType() != FieldType.BYTE) {
                    Debug.debug("entry.type: " + entry.getFieldType());
                    Debug.debug("entry.directoryType: " + entry.getDirectoryType());
                    Debug.debug("entry.type: " + entry.getDescriptionWithoutValue());
                    Debug.debug("entry.type: " + entry.getFieldType());
                    throw new ImageReadException("GPS text field not encoded as bytes.");
                }
            }
            final byte[] bytes = entry.getByteArrayValue();
            if (bytes.length < 8) {
                return new String(bytes, StandardCharsets.US_ASCII);
            }
            for (final TextEncoding encoding : TagInfoGpsText.TEXT_ENCODINGS) {
                if (BinaryFunctions.compareBytes(bytes, 0, encoding.prefix, 0, encoding.prefix.length)) {
                    try {
                        final String decodedString = new String(bytes, encoding.prefix.length, bytes.length - encoding.prefix.length, encoding.encodingName);
                        final byte[] reEncodedBytes = decodedString.getBytes(encoding.encodingName);
                        if (BinaryFunctions.compareBytes(bytes, encoding.prefix.length, reEncodedBytes, 0, reEncodedBytes.length)) {
                            return decodedString;
                        }
                    }
                    catch (UnsupportedEncodingException e) {
                        throw new ImageReadException(e.getMessage(), e);
                    }
                }
            }
            return new String(bytes, StandardCharsets.US_ASCII);
        }
    }
    
    static {
        TEXT_ENCODING_ASCII = new TextEncoding(new byte[] { 65, 83, 67, 73, 73, 0, 0, 0 }, "US-ASCII");
        TEXT_ENCODING_JIS = new TextEncoding(new byte[] { 74, 73, 83, 0, 0, 0, 0, 0 }, "JIS");
        TEXT_ENCODING_UNICODE_LE = new TextEncoding(new byte[] { 85, 78, 73, 67, 79, 68, 69, 0 }, "UTF-16LE");
        TEXT_ENCODING_UNICODE_BE = new TextEncoding(new byte[] { 85, 78, 73, 67, 79, 68, 69, 0 }, "UTF-16BE");
        TEXT_ENCODING_UNDEFINED = new TextEncoding(new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 }, "ISO-8859-1");
        TEXT_ENCODINGS = new TextEncoding[] { TagInfoGpsText.TEXT_ENCODING_ASCII, TagInfoGpsText.TEXT_ENCODING_JIS, TagInfoGpsText.TEXT_ENCODING_UNICODE_LE, TagInfoGpsText.TEXT_ENCODING_UNICODE_BE, TagInfoGpsText.TEXT_ENCODING_UNDEFINED };
    }
    
    private static final class TextEncoding
    {
        final byte[] prefix;
        public final String encodingName;
        
        TextEncoding(final byte[] prefix, final String encodingName) {
            this.prefix = prefix;
            this.encodingName = encodingName;
        }
    }
}
