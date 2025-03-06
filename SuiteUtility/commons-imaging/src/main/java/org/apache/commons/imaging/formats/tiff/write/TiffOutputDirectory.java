// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.write;

import org.apache.commons.imaging.formats.tiff.TiffElement;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;
import java.io.IOException;
import org.apache.commons.imaging.common.BinaryOutputStream;
import org.apache.commons.imaging.formats.tiff.TiffDirectory;
import java.util.Collections;
import java.util.Iterator;
import java.util.Collection;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAsciiOrRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAsciiOrByte;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoXpString;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoGpsText;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShortOrRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShortOrLongOrRational;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShortOrLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoByteOrShort;
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
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLongs;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShorts;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShort;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoBytes;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoByte;
import java.util.ArrayList;
import org.apache.commons.imaging.formats.tiff.TiffImageData;
import org.apache.commons.imaging.formats.tiff.JpegImageData;
import java.util.Comparator;
import java.nio.ByteOrder;
import java.util.List;

public final class TiffOutputDirectory extends TiffOutputItem
{
    public final int type;
    private final List<TiffOutputField> fields;
    private final ByteOrder byteOrder;
    private TiffOutputDirectory nextDirectory;
    public static final Comparator<TiffOutputDirectory> COMPARATOR;
    private JpegImageData jpegImageData;
    private TiffImageData tiffImageData;
    
    public void setNextDirectory(final TiffOutputDirectory nextDirectory) {
        this.nextDirectory = nextDirectory;
    }
    
    public TiffOutputDirectory(final int type, final ByteOrder byteOrder) {
        this.fields = new ArrayList<TiffOutputField>();
        this.type = type;
        this.byteOrder = byteOrder;
    }
    
    public void add(final TagInfoByte tagInfo, final byte value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.BYTE, bytes.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoBytes tagInfo, final byte... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.BYTE, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoAscii tagInfo, final String... values) throws ImageWriteException {
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        if (tagInfo.length > 0 && tagInfo.length != bytes.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " byte(s), not " + values.length);
        }
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.ASCII, bytes.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoShort tagInfo, final short value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SHORT, 1, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoShorts tagInfo, final short... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SHORT, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoLong tagInfo, final int value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.LONG, 1, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoLongs tagInfo, final int... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.LONG, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoRational tagInfo, final RationalNumber value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.RATIONAL, 1, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoRationals tagInfo, final RationalNumber... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.RATIONAL, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoSByte tagInfo, final byte value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SBYTE, 1, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoSBytes tagInfo, final byte... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SBYTE, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoSShort tagInfo, final short value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SSHORT, 1, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoSShorts tagInfo, final short... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SSHORT, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoSLong tagInfo, final int value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SLONG, 1, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoSLongs tagInfo, final int... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SLONG, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoSRational tagInfo, final RationalNumber value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SRATIONAL, 1, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoSRationals tagInfo, final RationalNumber... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SRATIONAL, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoFloat tagInfo, final float value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.FLOAT, 1, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoFloats tagInfo, final float... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.FLOAT, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoDouble tagInfo, final double value) throws ImageWriteException {
        if (tagInfo.length != 1) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not 1");
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, value);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.DOUBLE, 1, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoDoubles tagInfo, final double... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.DOUBLE, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoByteOrShort tagInfo, final byte... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.BYTE, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoByteOrShort tagInfo, final short... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SHORT, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoShortOrLong tagInfo, final short... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SHORT, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoShortOrLong tagInfo, final int... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.LONG, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoShortOrLongOrRational tagInfo, final short... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SHORT, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoShortOrLongOrRational tagInfo, final int... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.LONG, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoShortOrLongOrRational tagInfo, final RationalNumber... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.RATIONAL, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoShortOrRational tagInfo, final short... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.SHORT, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoShortOrRational tagInfo, final RationalNumber... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(this.byteOrder, values);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.RATIONAL, values.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoGpsText tagInfo, final String value) throws ImageWriteException {
        final byte[] bytes = tagInfo.encodeValue(FieldType.UNDEFINED, value, this.byteOrder);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, tagInfo.dataTypes.get(0), bytes.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoXpString tagInfo, final String value) throws ImageWriteException {
        final byte[] bytes = tagInfo.encodeValue(FieldType.BYTE, value, this.byteOrder);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.BYTE, bytes.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoAsciiOrByte tagInfo, final String... values) throws ImageWriteException {
        final byte[] bytes = tagInfo.encodeValue(FieldType.ASCII, values, this.byteOrder);
        if (tagInfo.length > 0 && tagInfo.length != bytes.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " byte(s), not " + values.length);
        }
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.ASCII, bytes.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoAsciiOrRational tagInfo, final String... values) throws ImageWriteException {
        final byte[] bytes = tagInfo.encodeValue(FieldType.ASCII, values, this.byteOrder);
        if (tagInfo.length > 0 && tagInfo.length != bytes.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " byte(s), not " + values.length);
        }
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.ASCII, bytes.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TagInfoAsciiOrRational tagInfo, final RationalNumber... values) throws ImageWriteException {
        if (tagInfo.length > 0 && tagInfo.length != values.length) {
            throw new ImageWriteException("Tag expects " + tagInfo.length + " value(s), not " + values.length);
        }
        final byte[] bytes = tagInfo.encodeValue(FieldType.RATIONAL, values, this.byteOrder);
        final TiffOutputField tiffOutputField = new TiffOutputField(tagInfo.tag, tagInfo, FieldType.RATIONAL, bytes.length, bytes);
        this.add(tiffOutputField);
    }
    
    public void add(final TiffOutputField field) {
        this.fields.add(field);
    }
    
    public List<TiffOutputField> getFields() {
        return new ArrayList<TiffOutputField>(this.fields);
    }
    
    public void removeField(final TagInfo tagInfo) {
        this.removeField(tagInfo.tag);
    }
    
    public void removeField(final int tag) {
        final List<TiffOutputField> matches = new ArrayList<TiffOutputField>();
        for (final TiffOutputField field : this.fields) {
            if (field.tag == tag) {
                matches.add(field);
            }
        }
        this.fields.removeAll(matches);
    }
    
    public TiffOutputField findField(final TagInfo tagInfo) {
        return this.findField(tagInfo.tag);
    }
    
    public TiffOutputField findField(final int tag) {
        for (final TiffOutputField field : this.fields) {
            if (field.tag == tag) {
                return field;
            }
        }
        return null;
    }
    
    public void sortFields() {
        final Comparator<TiffOutputField> comparator = new Comparator<TiffOutputField>() {
            @Override
            public int compare(final TiffOutputField e1, final TiffOutputField e2) {
                if (e1.tag != e2.tag) {
                    return e1.tag - e2.tag;
                }
                return e1.getSortHint() - e2.getSortHint();
            }
        };
        Collections.sort(this.fields, comparator);
    }
    
    public String description() {
        return TiffDirectory.description(this.type);
    }
    
    @Override
    public void writeItem(final BinaryOutputStream bos) throws IOException, ImageWriteException {
        bos.write2Bytes(this.fields.size());
        for (final TiffOutputField field : this.fields) {
            field.writeField(bos);
        }
        long nextDirectoryOffset = 0L;
        if (this.nextDirectory != null) {
            nextDirectoryOffset = this.nextDirectory.getOffset();
        }
        if (nextDirectoryOffset == -1L) {
            bos.write4Bytes(0);
        }
        else {
            bos.write4Bytes((int)nextDirectoryOffset);
        }
    }
    
    public void setJpegImageData(final JpegImageData rawJpegImageData) {
        this.jpegImageData = rawJpegImageData;
    }
    
    public JpegImageData getRawJpegImageData() {
        return this.jpegImageData;
    }
    
    public void setTiffImageData(final TiffImageData rawTiffImageData) {
        this.tiffImageData = rawTiffImageData;
    }
    
    public TiffImageData getRawTiffImageData() {
        return this.tiffImageData;
    }
    
    @Override
    public int getItemLength() {
        return 12 * this.fields.size() + 2 + 4;
    }
    
    @Override
    public String getItemDescription() {
        final TiffDirectoryType dirType = TiffDirectoryType.getExifDirectoryType(this.type);
        return "Directory: " + dirType.name + " (" + this.type + ")";
    }
    
    private void removeFieldIfPresent(final TagInfo tagInfo) {
        final TiffOutputField field = this.findField(tagInfo);
        if (null != field) {
            this.fields.remove(field);
        }
    }
    
    protected List<TiffOutputItem> getOutputItems(final TiffOutputSummary outputSummary) throws ImageWriteException {
        this.removeFieldIfPresent(TiffTagConstants.TIFF_TAG_JPEG_INTERCHANGE_FORMAT);
        this.removeFieldIfPresent(TiffTagConstants.TIFF_TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        TiffOutputField jpegOffsetField = null;
        if (null != this.jpegImageData) {
            jpegOffsetField = new TiffOutputField(TiffTagConstants.TIFF_TAG_JPEG_INTERCHANGE_FORMAT, FieldType.LONG, 1, new byte[4]);
            this.add(jpegOffsetField);
            final byte[] lengthValue = FieldType.LONG.writeData(this.jpegImageData.length, outputSummary.byteOrder);
            final TiffOutputField jpegLengthField = new TiffOutputField(TiffTagConstants.TIFF_TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, FieldType.LONG, 1, lengthValue);
            this.add(jpegLengthField);
        }
        this.removeFieldIfPresent(TiffTagConstants.TIFF_TAG_STRIP_OFFSETS);
        this.removeFieldIfPresent(TiffTagConstants.TIFF_TAG_STRIP_BYTE_COUNTS);
        this.removeFieldIfPresent(TiffTagConstants.TIFF_TAG_TILE_OFFSETS);
        this.removeFieldIfPresent(TiffTagConstants.TIFF_TAG_TILE_BYTE_COUNTS);
        ImageDataOffsets imageDataInfo = null;
        if (null != this.tiffImageData) {
            final boolean stripsNotTiles = this.tiffImageData.stripsNotTiles();
            TagInfo offsetTag;
            TagInfo byteCountsTag;
            if (stripsNotTiles) {
                offsetTag = TiffTagConstants.TIFF_TAG_STRIP_OFFSETS;
                byteCountsTag = TiffTagConstants.TIFF_TAG_STRIP_BYTE_COUNTS;
            }
            else {
                offsetTag = TiffTagConstants.TIFF_TAG_TILE_OFFSETS;
                byteCountsTag = TiffTagConstants.TIFF_TAG_TILE_BYTE_COUNTS;
            }
            final TiffElement.DataElement[] imageData = this.tiffImageData.getImageData();
            final int[] imageDataOffsets = new int[imageData.length];
            final int[] imageDataByteCounts = new int[imageData.length];
            for (int i = 0; i < imageData.length; ++i) {
                imageDataByteCounts[i] = imageData[i].length;
            }
            final TiffOutputField imageDataOffsetField = new TiffOutputField(offsetTag, FieldType.LONG, imageDataOffsets.length, FieldType.LONG.writeData(imageDataOffsets, outputSummary.byteOrder));
            this.add(imageDataOffsetField);
            final byte[] data = FieldType.LONG.writeData(imageDataByteCounts, outputSummary.byteOrder);
            final TiffOutputField byteCountsField = new TiffOutputField(byteCountsTag, FieldType.LONG, imageDataByteCounts.length, data);
            this.add(byteCountsField);
            imageDataInfo = new ImageDataOffsets(imageData, imageDataOffsets, imageDataOffsetField);
        }
        final List<TiffOutputItem> result = new ArrayList<TiffOutputItem>();
        result.add(this);
        this.sortFields();
        for (final TiffOutputField field : this.fields) {
            if (field.isLocalValue()) {
                continue;
            }
            final TiffOutputItem item = field.getSeperateValue();
            result.add(item);
        }
        if (null != imageDataInfo) {
            Collections.addAll(result, imageDataInfo.outputItems);
            outputSummary.addTiffImageData(imageDataInfo);
        }
        if (null != this.jpegImageData) {
            final TiffOutputItem item2 = new Value("JPEG image data", this.jpegImageData.getData());
            result.add(item2);
            outputSummary.add(item2, jpegOffsetField);
        }
        return result;
    }
    
    static {
        COMPARATOR = new Comparator<TiffOutputDirectory>() {
            @Override
            public int compare(final TiffOutputDirectory o1, final TiffOutputDirectory o2) {
                if (o1.type < o2.type) {
                    return -1;
                }
                if (o1.type > o2.type) {
                    return 1;
                }
                return 0;
            }
        };
    }
}
