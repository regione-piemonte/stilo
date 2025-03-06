// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import java.io.IOException;
import java.awt.image.BufferedImage;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoXpString;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoGpsText;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoDoubles;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoFloats;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSRationals;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSLongs;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSShorts;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoSBytes;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoRationals;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLongs;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoShorts;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoAscii;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoByte;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import java.nio.ByteOrder;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import org.apache.commons.imaging.common.ImageMetadata;
import java.util.List;
import org.apache.commons.imaging.common.GenericImageMetadata;

public class TiffImageMetadata extends GenericImageMetadata
{
    public final TiffContents contents;
    
    public TiffImageMetadata(final TiffContents contents) {
        this.contents = contents;
    }
    
    public List<? extends ImageMetadata.ImageMetadataItem> getDirectories() {
        return super.getItems();
    }
    
    @Override
    public List<? extends ImageMetadata.ImageMetadataItem> getItems() {
        final List<ImageMetadata.ImageMetadataItem> result = new ArrayList<ImageMetadata.ImageMetadataItem>();
        final List<? extends ImageMetadata.ImageMetadataItem> items = super.getItems();
        for (final ImageMetadata.ImageMetadataItem item : items) {
            final Directory dir = (Directory)item;
            result.addAll(dir.getItems());
        }
        return result;
    }
    
    public TiffOutputSet getOutputSet() throws ImageWriteException {
        final ByteOrder byteOrder = this.contents.header.byteOrder;
        final TiffOutputSet result = new TiffOutputSet(byteOrder);
        final List<? extends ImageMetadata.ImageMetadataItem> srcDirs = this.getDirectories();
        for (final ImageMetadata.ImageMetadataItem srcDir1 : srcDirs) {
            final Directory srcDir2 = (Directory)srcDir1;
            if (null != result.findDirectory(srcDir2.type)) {
                continue;
            }
            final TiffOutputDirectory outputDirectory = srcDir2.getOutputDirectory(byteOrder);
            result.addDirectory(outputDirectory);
        }
        return result;
    }
    
    public TiffField findField(final TagInfo tagInfo) throws ImageReadException {
        return this.findField(tagInfo, false);
    }
    
    public TiffField findField(final TagInfo tagInfo, final boolean exactDirectoryMatch) throws ImageReadException {
        final Integer tagCount = TiffTags.getTagCount(tagInfo.tag);
        final int tagsMatching = (tagCount == null) ? 0 : tagCount;
        final List<? extends ImageMetadata.ImageMetadataItem> directories = this.getDirectories();
        if (exactDirectoryMatch || tagInfo.directoryType != TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN) {
            for (final ImageMetadata.ImageMetadataItem directory1 : directories) {
                final Directory directory2 = (Directory)directory1;
                if (directory2.type == tagInfo.directoryType.directoryType) {
                    final TiffField field = directory2.findField(tagInfo);
                    if (field != null) {
                        return field;
                    }
                    continue;
                }
            }
            if (exactDirectoryMatch || tagsMatching > 1) {
                return null;
            }
            for (final ImageMetadata.ImageMetadataItem directory1 : directories) {
                final Directory directory2 = (Directory)directory1;
                if (tagInfo.directoryType.isImageDirectory() && directory2.type >= 0) {
                    final TiffField field = directory2.findField(tagInfo);
                    if (field != null) {
                        return field;
                    }
                    continue;
                }
                else {
                    if (tagInfo.directoryType.isImageDirectory() || directory2.type >= 0) {
                        continue;
                    }
                    final TiffField field = directory2.findField(tagInfo);
                    if (field != null) {
                        return field;
                    }
                    continue;
                }
            }
        }
        for (final ImageMetadata.ImageMetadataItem directory1 : directories) {
            final Directory directory2 = (Directory)directory1;
            final TiffField field = directory2.findField(tagInfo);
            if (field != null) {
                return field;
            }
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
    
    public byte[] getFieldValue(final TagInfoByte tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        return field.getByteArrayValue();
    }
    
    public String[] getFieldValue(final TagInfoAscii tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public short[] getFieldValue(final TagInfoShorts tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public int[] getFieldValue(final TagInfoLongs tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public RationalNumber[] getFieldValue(final TagInfoRationals tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public byte[] getFieldValue(final TagInfoSBytes tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        return field.getByteArrayValue();
    }
    
    public short[] getFieldValue(final TagInfoSShorts tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public int[] getFieldValue(final TagInfoSLongs tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public RationalNumber[] getFieldValue(final TagInfoSRationals tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public float[] getFieldValue(final TagInfoFloats tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public double[] getFieldValue(final TagInfoDoubles tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        if (!tag.dataTypes.contains(field.getFieldType())) {
            return null;
        }
        final byte[] bytes = field.getByteArrayValue();
        return tag.getValue(field.getByteOrder(), bytes);
    }
    
    public String getFieldValue(final TagInfoGpsText tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        return tag.getValue(field);
    }
    
    public String getFieldValue(final TagInfoXpString tag) throws ImageReadException {
        final TiffField field = this.findField(tag);
        if (field == null) {
            return null;
        }
        return tag.getValue(field);
    }
    
    public TiffDirectory findDirectory(final int directoryType) {
        final List<? extends ImageMetadata.ImageMetadataItem> directories = this.getDirectories();
        for (final ImageMetadata.ImageMetadataItem directory1 : directories) {
            final Directory directory2 = (Directory)directory1;
            if (directory2.type == directoryType) {
                return directory2.directory;
            }
        }
        return null;
    }
    
    public List<TiffField> getAllFields() {
        final List<TiffField> result = new ArrayList<TiffField>();
        final List<? extends ImageMetadata.ImageMetadataItem> directories = this.getDirectories();
        for (final ImageMetadata.ImageMetadataItem directory1 : directories) {
            final Directory directory2 = (Directory)directory1;
            result.addAll(directory2.getAllFields());
        }
        return result;
    }
    
    public GPSInfo getGPS() throws ImageReadException {
        final TiffDirectory gpsDirectory = this.findDirectory(-3);
        if (null == gpsDirectory) {
            return null;
        }
        final TiffField latitudeRefField = gpsDirectory.findField(GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
        final TiffField latitudeField = gpsDirectory.findField(GpsTagConstants.GPS_TAG_GPS_LATITUDE);
        final TiffField longitudeRefField = gpsDirectory.findField(GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
        final TiffField longitudeField = gpsDirectory.findField(GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
        if (latitudeRefField == null || latitudeField == null || longitudeRefField == null || longitudeField == null) {
            return null;
        }
        final String latitudeRef = latitudeRefField.getStringValue();
        final RationalNumber[] latitude = (RationalNumber[])latitudeField.getValue();
        final String longitudeRef = longitudeRefField.getStringValue();
        final RationalNumber[] longitude = (RationalNumber[])longitudeField.getValue();
        if (latitude.length != 3 || longitude.length != 3) {
            throw new ImageReadException("Expected three values for latitude and longitude.");
        }
        final RationalNumber latitudeDegrees = latitude[0];
        final RationalNumber latitudeMinutes = latitude[1];
        final RationalNumber latitudeSeconds = latitude[2];
        final RationalNumber longitudeDegrees = longitude[0];
        final RationalNumber longitudeMinutes = longitude[1];
        final RationalNumber longitudeSeconds = longitude[2];
        return new GPSInfo(latitudeRef, longitudeRef, latitudeDegrees, latitudeMinutes, latitudeSeconds, longitudeDegrees, longitudeMinutes, longitudeSeconds);
    }
    
    public static class Directory extends GenericImageMetadata implements ImageMetadata.ImageMetadataItem
    {
        public final int type;
        private final TiffDirectory directory;
        private final ByteOrder byteOrder;
        
        public Directory(final ByteOrder byteOrder, final TiffDirectory directory) {
            this.type = directory.type;
            this.directory = directory;
            this.byteOrder = byteOrder;
        }
        
        public void add(final TiffField entry) {
            this.add(new TiffMetadataItem(entry));
        }
        
        public BufferedImage getThumbnail() throws ImageReadException, IOException {
            return this.directory.getTiffImage(this.byteOrder);
        }
        
        public TiffImageData getTiffImageData() {
            return this.directory.getTiffImageData();
        }
        
        public TiffField findField(final TagInfo tagInfo) throws ImageReadException {
            return this.directory.findField(tagInfo);
        }
        
        public List<TiffField> getAllFields() {
            return this.directory.getDirectoryEntries();
        }
        
        public JpegImageData getJpegImageData() {
            return this.directory.getJpegImageData();
        }
        
        @Override
        public String toString(final String prefix) {
            return ((prefix != null) ? prefix : "") + this.directory.description() + ": " + ((this.getTiffImageData() != null) ? " (tiffImageData)" : "") + ((this.getJpegImageData() != null) ? " (jpegImageData)" : "") + "\n" + super.toString(prefix) + "\n";
        }
        
        public TiffOutputDirectory getOutputDirectory(final ByteOrder byteOrder) throws ImageWriteException {
            try {
                final TiffOutputDirectory dstDir = new TiffOutputDirectory(this.type, byteOrder);
                final List<? extends ImageMetadata.ImageMetadataItem> entries = this.getItems();
                for (final ImageMetadata.ImageMetadataItem entry : entries) {
                    final TiffMetadataItem item = (TiffMetadataItem)entry;
                    final TiffField srcField = item.getTiffField();
                    if (null != dstDir.findField(srcField.getTag())) {
                        continue;
                    }
                    if (srcField.getTagInfo().isOffset()) {
                        continue;
                    }
                    final TagInfo tagInfo = srcField.getTagInfo();
                    final FieldType fieldType = srcField.getFieldType();
                    final Object value = srcField.getValue();
                    final byte[] bytes = tagInfo.encodeValue(fieldType, value, byteOrder);
                    final int count = bytes.length / fieldType.getSize();
                    final TiffOutputField dstField = new TiffOutputField(srcField.getTag(), tagInfo, fieldType, count, bytes);
                    dstField.setSortHint(srcField.getSortHint());
                    dstDir.add(dstField);
                }
                dstDir.setTiffImageData(this.getTiffImageData());
                dstDir.setJpegImageData(this.getJpegImageData());
                return dstDir;
            }
            catch (ImageReadException e) {
                throw new ImageWriteException(e.getMessage(), e);
            }
        }
    }
    
    public static class TiffMetadataItem extends GenericImageMetadataItem
    {
        private final TiffField entry;
        
        public TiffMetadataItem(final TiffField entry) {
            super(entry.getTagName(), entry.getValueDescription());
            this.entry = entry;
        }
        
        public TiffField getTiffField() {
            return this.entry;
        }
    }
    
    public static class GPSInfo
    {
        public final String latitudeRef;
        public final String longitudeRef;
        public final RationalNumber latitudeDegrees;
        public final RationalNumber latitudeMinutes;
        public final RationalNumber latitudeSeconds;
        public final RationalNumber longitudeDegrees;
        public final RationalNumber longitudeMinutes;
        public final RationalNumber longitudeSeconds;
        
        public GPSInfo(final String latitudeRef, final String longitudeRef, final RationalNumber latitudeDegrees, final RationalNumber latitudeMinutes, final RationalNumber latitudeSeconds, final RationalNumber longitudeDegrees, final RationalNumber longitudeMinutes, final RationalNumber longitudeSeconds) {
            this.latitudeRef = latitudeRef;
            this.longitudeRef = longitudeRef;
            this.latitudeDegrees = latitudeDegrees;
            this.latitudeMinutes = latitudeMinutes;
            this.latitudeSeconds = latitudeSeconds;
            this.longitudeDegrees = longitudeDegrees;
            this.longitudeMinutes = longitudeMinutes;
            this.longitudeSeconds = longitudeSeconds;
        }
        
        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder(88);
            result.append("[GPS. Latitude: " + this.latitudeDegrees.toDisplayString() + " degrees, " + this.latitudeMinutes.toDisplayString() + " minutes, " + this.latitudeSeconds.toDisplayString() + " seconds " + this.latitudeRef);
            result.append(", Longitude: " + this.longitudeDegrees.toDisplayString() + " degrees, " + this.longitudeMinutes.toDisplayString() + " minutes, " + this.longitudeSeconds.toDisplayString() + " seconds " + this.longitudeRef);
            result.append(']');
            return result.toString();
        }
        
        public double getLongitudeAsDegreesEast() throws ImageReadException {
            final double result = this.longitudeDegrees.doubleValue() + this.longitudeMinutes.doubleValue() / 60.0 + this.longitudeSeconds.doubleValue() / 3600.0;
            if (this.longitudeRef.trim().equalsIgnoreCase("e")) {
                return result;
            }
            if (this.longitudeRef.trim().equalsIgnoreCase("w")) {
                return -result;
            }
            throw new ImageReadException("Unknown longitude ref: \"" + this.longitudeRef + "\"");
        }
        
        public double getLatitudeAsDegreesNorth() throws ImageReadException {
            final double result = this.latitudeDegrees.doubleValue() + this.latitudeMinutes.doubleValue() / 60.0 + this.latitudeSeconds.doubleValue() / 3600.0;
            if (this.latitudeRef.trim().equalsIgnoreCase("n")) {
                return result;
            }
            if (this.latitudeRef.trim().equalsIgnoreCase("s")) {
                return -result;
            }
            throw new ImageReadException("Unknown latitude ref: \"" + this.latitudeRef + "\"");
        }
    }
}
