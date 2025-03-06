// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.write;

import org.apache.commons.imaging.internal.Debug;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.ImageWriteException;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;
import java.util.List;
import java.nio.ByteOrder;

public final class TiffOutputSet
{
    public final ByteOrder byteOrder;
    private final List<TiffOutputDirectory> directories;
    private static final String NEWLINE;
    
    public TiffOutputSet() {
        this(TiffConstants.DEFAULT_TIFF_BYTE_ORDER);
    }
    
    public TiffOutputSet(final ByteOrder byteOrder) {
        this.directories = new ArrayList<TiffOutputDirectory>();
        this.byteOrder = byteOrder;
    }
    
    protected List<TiffOutputItem> getOutputItems(final TiffOutputSummary outputSummary) throws ImageWriteException {
        final List<TiffOutputItem> result = new ArrayList<TiffOutputItem>();
        for (final TiffOutputDirectory directory : this.directories) {
            result.addAll(directory.getOutputItems(outputSummary));
        }
        return result;
    }
    
    public void addDirectory(final TiffOutputDirectory directory) throws ImageWriteException {
        if (null != this.findDirectory(directory.type)) {
            throw new ImageWriteException("Output set already contains a directory of that type.");
        }
        this.directories.add(directory);
    }
    
    public List<TiffOutputDirectory> getDirectories() {
        return new ArrayList<TiffOutputDirectory>(this.directories);
    }
    
    public TiffOutputDirectory getRootDirectory() {
        return this.findDirectory(0);
    }
    
    public TiffOutputDirectory getExifDirectory() {
        return this.findDirectory(-2);
    }
    
    public TiffOutputDirectory getOrCreateRootDirectory() throws ImageWriteException {
        final TiffOutputDirectory result = this.findDirectory(0);
        if (null != result) {
            return result;
        }
        return this.addRootDirectory();
    }
    
    public TiffOutputDirectory getOrCreateExifDirectory() throws ImageWriteException {
        this.getOrCreateRootDirectory();
        final TiffOutputDirectory result = this.findDirectory(-2);
        if (null != result) {
            return result;
        }
        return this.addExifDirectory();
    }
    
    public TiffOutputDirectory getOrCreateGPSDirectory() throws ImageWriteException {
        this.getOrCreateExifDirectory();
        final TiffOutputDirectory result = this.findDirectory(-3);
        if (null != result) {
            return result;
        }
        return this.addGPSDirectory();
    }
    
    public TiffOutputDirectory getGPSDirectory() {
        return this.findDirectory(-3);
    }
    
    public TiffOutputDirectory getInteroperabilityDirectory() {
        return this.findDirectory(-4);
    }
    
    public TiffOutputDirectory findDirectory(final int directoryType) {
        for (final TiffOutputDirectory directory : this.directories) {
            if (directory.type == directoryType) {
                return directory;
            }
        }
        return null;
    }
    
    public void setGPSInDegrees(double longitude, double latitude) throws ImageWriteException {
        final TiffOutputDirectory gpsDirectory = this.getOrCreateGPSDirectory();
        gpsDirectory.removeField(GpsTagConstants.GPS_TAG_GPS_VERSION_ID);
        gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_VERSION_ID, GpsTagConstants.gpsVersion());
        final String longitudeRef = (longitude < 0.0) ? "W" : "E";
        longitude = Math.abs(longitude);
        final String latitudeRef = (latitude < 0.0) ? "S" : "N";
        latitude = Math.abs(latitude);
        gpsDirectory.removeField(GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
        gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF, longitudeRef);
        gpsDirectory.removeField(GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
        gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF, latitudeRef);
        double value = longitude;
        final double longitudeDegrees = (double)(long)value;
        value %= 1.0;
        value *= 60.0;
        final double longitudeMinutes = (double)(long)value;
        value %= 1.0;
        final double longitudeSeconds;
        value = (longitudeSeconds = value * 60.0);
        gpsDirectory.removeField(GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
        gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LONGITUDE, RationalNumber.valueOf(longitudeDegrees), RationalNumber.valueOf(longitudeMinutes), RationalNumber.valueOf(longitudeSeconds));
        value = latitude;
        final double latitudeDegrees = (double)(long)value;
        value %= 1.0;
        value *= 60.0;
        final double latitudeMinutes = (double)(long)value;
        value %= 1.0;
        final double latitudeSeconds;
        value = (latitudeSeconds = value * 60.0);
        gpsDirectory.removeField(GpsTagConstants.GPS_TAG_GPS_LATITUDE);
        gpsDirectory.add(GpsTagConstants.GPS_TAG_GPS_LATITUDE, RationalNumber.valueOf(latitudeDegrees), RationalNumber.valueOf(latitudeMinutes), RationalNumber.valueOf(latitudeSeconds));
    }
    
    public void removeField(final TagInfo tagInfo) {
        this.removeField(tagInfo.tag);
    }
    
    public void removeField(final int tag) {
        for (final TiffOutputDirectory directory : this.directories) {
            directory.removeField(tag);
        }
    }
    
    public TiffOutputField findField(final TagInfo tagInfo) {
        return this.findField(tagInfo.tag);
    }
    
    public TiffOutputField findField(final int tag) {
        for (final TiffOutputDirectory directory : this.directories) {
            final TiffOutputField field = directory.findField(tag);
            if (null != field) {
                return field;
            }
        }
        return null;
    }
    
    public TiffOutputDirectory addRootDirectory() throws ImageWriteException {
        final TiffOutputDirectory result = new TiffOutputDirectory(0, this.byteOrder);
        this.addDirectory(result);
        return result;
    }
    
    public TiffOutputDirectory addExifDirectory() throws ImageWriteException {
        final TiffOutputDirectory result = new TiffOutputDirectory(-2, this.byteOrder);
        this.addDirectory(result);
        return result;
    }
    
    public TiffOutputDirectory addGPSDirectory() throws ImageWriteException {
        final TiffOutputDirectory result = new TiffOutputDirectory(-3, this.byteOrder);
        this.addDirectory(result);
        return result;
    }
    
    public TiffOutputDirectory addInteroperabilityDirectory() throws ImageWriteException {
        this.getOrCreateExifDirectory();
        final TiffOutputDirectory result = new TiffOutputDirectory(-4, this.byteOrder);
        this.addDirectory(result);
        return result;
    }
    
    @Override
    public String toString() {
        return this.toString(null);
    }
    
    public String toString(String prefix) {
        if (prefix == null) {
            prefix = "";
        }
        final StringBuilder result = new StringBuilder(39);
        result.append(prefix);
        result.append("TiffOutputSet {");
        result.append(TiffOutputSet.NEWLINE);
        result.append(prefix);
        result.append("byteOrder: ");
        result.append(this.byteOrder);
        result.append(TiffOutputSet.NEWLINE);
        for (int i = 0; i < this.directories.size(); ++i) {
            final TiffOutputDirectory directory = this.directories.get(i);
            result.append(String.format("%s\tdirectory %d: %s (%d)%n", prefix, i, directory.description(), directory.type));
            final List<TiffOutputField> fields = directory.getFields();
            for (final TiffOutputField field : fields) {
                result.append(prefix);
                result.append("\t\tfield " + i + ": " + field.tagInfo);
                result.append(TiffOutputSet.NEWLINE);
            }
        }
        result.append(prefix);
        result.append('}');
        result.append(TiffOutputSet.NEWLINE);
        return result.toString();
    }
    
    public void dump() {
        Debug.debug(this.toString());
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
}
