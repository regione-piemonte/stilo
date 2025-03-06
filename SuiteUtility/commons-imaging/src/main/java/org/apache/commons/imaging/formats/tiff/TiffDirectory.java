// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoXpString;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoGpsText;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoDoubles;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoDouble;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoFloats;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoFloat;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSRationals;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSLongs;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSShorts;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSShort;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSBytes;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSByte;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoRationals;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoRational;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLongs;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShorts;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShort;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoBytes;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoByte;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShortOrLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;
import java.io.IOException;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.nio.ByteOrder;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.util.List;

public class TiffDirectory extends TiffElement
{
    public final int type;
    public final List<TiffField> entries;
    public final long nextDirectoryOffset;
    private TiffImageData tiffImageData;
    private JpegImageData jpegImageData;
    
    public TiffDirectory(final int type, final List<TiffField> entries, final long offset, final long nextDirectoryOffset) {
        super(offset, 2 + entries.size() * 12 + 4);
        this.type = type;
        this.entries = Collections.unmodifiableList((List<? extends TiffField>)entries);
        this.nextDirectoryOffset = nextDirectoryOffset;
    }
    
    public String description() {
        return description(this.type);
    }
    
    @Override
    public String getElementDescription() {
        long entryOffset = this.offset + 2L;
        final StringBuilder result = new StringBuilder();
        for (final TiffField entry : this.entries) {
            result.append(String.format("\t[%d]: %s (%d, 0x%x), %s, %d: %s%n", entryOffset, entry.getTagInfo().name, entry.getTag(), entry.getTag(), entry.getFieldType().getName(), entry.getBytesLength(), entry.getValueDescription()));
            entryOffset += 12L;
        }
        return result.toString();
    }
    
    public static String description(final int type) {
        switch (type) {
            case -1: {
                return "Unknown";
            }
            case 0: {
                return "Root";
            }
            case 1: {
                return "Sub";
            }
            case 2: {
                return "Thumbnail";
            }
            case -2: {
                return "Exif";
            }
            case -3: {
                return "Gps";
            }
            case -4: {
                return "Interoperability";
            }
            default: {
                return "Bad Type";
            }
        }
    }
    
    public List<TiffField> getDirectoryEntries() {
        return new ArrayList<TiffField>(this.entries);
    }
    
    public void dump() {
        for (final TiffField entry : this.entries) {
            entry.dump();
        }
    }
    
    public boolean hasJpegImageData() throws ImageReadException {
        return null != this.findField(TiffTagConstants.TIFF_TAG_JPEG_INTERCHANGE_FORMAT);
    }
    
    public boolean hasTiffImageData() throws ImageReadException {
        return null != this.findField(TiffTagConstants.TIFF_TAG_TILE_OFFSETS) || null != this.findField(TiffTagConstants.TIFF_TAG_STRIP_OFFSETS);
    }
    
    public BufferedImage getTiffImage(final ByteOrder byteOrder) throws ImageReadException, IOException {
        final Map<String, Object> params = null;
        return this.getTiffImage(byteOrder, params);
    }
    
    public BufferedImage getTiffImage(final ByteOrder byteOrder, final Map<String, Object> params) throws ImageReadException, IOException {
        if (null == this.tiffImageData) {
            return null;
        }
        return new TiffImageParser().getBufferedImage(this, byteOrder, params);
    }
    
    public TiffField findField(final TagInfo tag) throws ImageReadException {
        final boolean failIfMissing = false;
        return this.findField(tag, false);
    }
    
    public TiffField findField(final TagInfo tag, final boolean failIfMissing) throws ImageReadException {
        if (this.entries == null) {
            return null;
        }
        for (final TiffField field : this.entries) {
            if (field.getTag() == tag.tag) {
                return field;
            }
        }
        if (failIfMissing) {
            throw new ImageReadException("Missing expected field: " + tag.getDescription());
        }
        return null;
    }
    
    public Object getFieldValue(final TagInfo tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        return field.getValue();
    }
    
    public String getSingleFieldValue(final TagInfoAscii tag) throws ImageReadException {
        final String[] result = this.getFieldValue(tag, true);
        if (result.length != 1) {
            throw new ImageReadException("Field \"" + tag.name + "\" has incorrect length " + result.length);
        }
        return result[0];
    }
    
    public int getSingleFieldValue(final TagInfoShortOrLong tag) throws ImageReadException {
        final int[] result = this.getFieldValue(tag, true);
        if (result.length != 1) {
            throw new ImageReadException("Field \"" + tag.name + "\" has incorrect length " + result.length);
        }
        return result[0];
    }
    
    public byte getFieldValue(final TagInfoByte tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        return field.getByteArrayValue()[0];
    }
    
    public byte[] getFieldValue(final TagInfoBytes tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                return field.getByteArrayValue();
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public String[] getFieldValue(final TagInfoAscii tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                final byte[] bytes = field.getByteArrayValue();
                return tag.getValue(field.getByteOrder(), bytes);
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public short getFieldValue(final TagInfoShort tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public short[] getFieldValue(final TagInfoShorts tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                final byte[] bytes = field.getByteArrayValue();
                return tag.getValue(field.getByteOrder(), bytes);
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public int getFieldValue(final TagInfoLong tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public int[] getFieldValue(final TagInfoLongs tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                final byte[] bytes = field.getByteArrayValue();
                return tag.getValue(field.getByteOrder(), bytes);
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public int[] getFieldValue(final TagInfoShortOrLong tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else if (!tag.dataTypes.contains(field.getFieldType())) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
        else {
            final byte[] bytes = field.getByteArrayValue();
            if (field.getFieldType() == FieldType.SHORT) {
                return ByteConversions.toUInt16s(bytes, field.getByteOrder());
            }
            return ByteConversions.toInts(bytes, field.getByteOrder());
        }
    }
    
    public RationalNumber getFieldValue(final TagInfoRational tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public RationalNumber[] getFieldValue(final TagInfoRationals tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                final byte[] bytes = field.getByteArrayValue();
                return tag.getValue(field.getByteOrder(), bytes);
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public byte getFieldValue(final TagInfoSByte tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        return field.getByteArrayValue()[0];
    }
    
    public byte[] getFieldValue(final TagInfoSBytes tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                return field.getByteArrayValue();
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public short getFieldValue(final TagInfoSShort tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public short[] getFieldValue(final TagInfoSShorts tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                final byte[] bytes = field.getByteArrayValue();
                return tag.getValue(field.getByteOrder(), bytes);
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public int getFieldValue(final TagInfoSLong tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public int[] getFieldValue(final TagInfoSLongs tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                final byte[] bytes = field.getByteArrayValue();
                return tag.getValue(field.getByteOrder(), bytes);
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public RationalNumber getFieldValue(final TagInfoSRational tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public RationalNumber[] getFieldValue(final TagInfoSRationals tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                final byte[] bytes = field.getByteArrayValue();
                return tag.getValue(field.getByteOrder(), bytes);
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public float getFieldValue(final TagInfoFloat tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public float[] getFieldValue(final TagInfoFloats tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                final byte[] bytes = field.getByteArrayValue();
                return tag.getValue(field.getByteOrder(), bytes);
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public double getFieldValue(final TagInfoDouble tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
        }
        if (field.getCount() != 1L) {
            throw new ImageReadException("Field \"" + tag.name + "\" has wrong count " + field.getCount());
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public double[] getFieldValue(final TagInfoDoubles tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
            }
            return null;
        }
        else {
            if (tag.dataTypes.contains(field.getFieldType())) {
                final byte[] bytes = field.getByteArrayValue();
                return tag.getValue(field.getByteOrder(), bytes);
            }
            if (mustExist) {
                throw new ImageReadException("Required field \"" + tag.name + "\" has incorrect type " + field.getFieldType().getName());
            }
            return null;
        }
    }
    
    public String getFieldValue(final TagInfoGpsText tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field != null) {
            return tag.getValue(field);
        }
        if (mustExist) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        return null;
    }
    
    public String getFieldValue(final TagInfoXpString tag, final boolean mustExist) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field != null) {
            return tag.getValue(field);
        }
        if (mustExist) {
            throw new ImageReadException("Required field \"" + tag.name + "\" is missing");
        }
        return null;
    }
    
    private List<ImageDataElement> getRawImageDataElements(final TiffField offsetsField, final TiffField byteCountsField) throws ImageReadException {
        final int[] offsets = offsetsField.getIntArrayValue();
        final int[] byteCounts = byteCountsField.getIntArrayValue();
        if (offsets.length != byteCounts.length) {
            throw new ImageReadException("offsets.length(" + offsets.length + ") != byteCounts.length(" + byteCounts.length + ")");
        }
        final List<ImageDataElement> result = new ArrayList<ImageDataElement>(offsets.length);
        for (int i = 0; i < offsets.length; ++i) {
            result.add(new ImageDataElement(offsets[i], byteCounts[i]));
        }
        return result;
    }
    
    public List<ImageDataElement> getTiffRawImageDataElements() throws ImageReadException {
        final TiffField tileOffsets = this.findField(TiffTagConstants.TIFF_TAG_TILE_OFFSETS);
        final TiffField tileByteCounts = this.findField(TiffTagConstants.TIFF_TAG_TILE_BYTE_COUNTS);
        final TiffField stripOffsets = this.findField(TiffTagConstants.TIFF_TAG_STRIP_OFFSETS);
        final TiffField stripByteCounts = this.findField(TiffTagConstants.TIFF_TAG_STRIP_BYTE_COUNTS);
        if (tileOffsets != null && tileByteCounts != null) {
            return this.getRawImageDataElements(tileOffsets, tileByteCounts);
        }
        if (stripOffsets != null && stripByteCounts != null) {
            return this.getRawImageDataElements(stripOffsets, stripByteCounts);
        }
        throw new ImageReadException("Couldn't find image data.");
    }
    
    public boolean imageDataInStrips() throws ImageReadException {
        final TiffField tileOffsets = this.findField(TiffTagConstants.TIFF_TAG_TILE_OFFSETS);
        final TiffField tileByteCounts = this.findField(TiffTagConstants.TIFF_TAG_TILE_BYTE_COUNTS);
        final TiffField stripOffsets = this.findField(TiffTagConstants.TIFF_TAG_STRIP_OFFSETS);
        final TiffField stripByteCounts = this.findField(TiffTagConstants.TIFF_TAG_STRIP_BYTE_COUNTS);
        if (tileOffsets != null && tileByteCounts != null) {
            return false;
        }
        if (stripOffsets != null && stripByteCounts != null) {
            return true;
        }
        throw new ImageReadException("Couldn't find image data.");
    }
    
    public ImageDataElement getJpegRawImageDataElement() throws ImageReadException {
        final TiffField jpegInterchangeFormat = this.findField(TiffTagConstants.TIFF_TAG_JPEG_INTERCHANGE_FORMAT);
        final TiffField jpegInterchangeFormatLength = this.findField(TiffTagConstants.TIFF_TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (jpegInterchangeFormat != null && jpegInterchangeFormatLength != null) {
            final int offSet = jpegInterchangeFormat.getIntArrayValue()[0];
            final int byteCount = jpegInterchangeFormatLength.getIntArrayValue()[0];
            return new ImageDataElement(offSet, byteCount);
        }
        throw new ImageReadException("Couldn't find image data.");
    }
    
    public void setTiffImageData(final TiffImageData rawImageData) {
        this.tiffImageData = rawImageData;
    }
    
    public TiffImageData getTiffImageData() {
        return this.tiffImageData;
    }
    
    public void setJpegImageData(final JpegImageData value) {
        this.jpegImageData = value;
    }
    
    public JpegImageData getJpegImageData() {
        return this.jpegImageData;
    }
    
    public static final class ImageDataElement extends TiffElement
    {
        public ImageDataElement(final long offset, final int length) {
            super(offset, length);
        }
        
        @Override
        public String getElementDescription() {
            return "ImageDataElement";
        }
    }
}
