// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.taginfos;

import org.apache.commons.imaging.ImageWriteException;
import java.nio.ByteOrder;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.TiffField;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.imaging.formats.tiff.constants.TiffDirectoryType;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import java.util.List;

public class TagInfo
{
    public static final int LENGTH_UNKNOWN = -1;
    public final String name;
    public final int tag;
    public final List<FieldType> dataTypes;
    public final int length;
    public final TiffDirectoryType directoryType;
    private final boolean isOffset;
    
    public TagInfo(final String name, final int tag, final FieldType dataType, final int length, final TiffDirectoryType exifDirectory) {
        this(name, tag, Arrays.asList(dataType), length, exifDirectory);
    }
    
    public TagInfo(final String name, final int tag, final FieldType dataType, final int length, final TiffDirectoryType exifDirectory, final boolean isOffset) {
        this(name, tag, Arrays.asList(dataType), length, exifDirectory, isOffset);
    }
    
    public TagInfo(final String name, final int tag, final FieldType dataType, final int length) {
        this(name, tag, Arrays.asList(dataType), length, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
    }
    
    public TagInfo(final String name, final int tag, final FieldType dataType) {
        this(name, tag, dataType, -1, TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
    }
    
    public TagInfo(final String name, final int tag, final List<FieldType> dataTypes, final int length, final TiffDirectoryType exifDirectory) {
        this(name, tag, dataTypes, length, exifDirectory, false);
    }
    
    public TagInfo(final String name, final int tag, final List<FieldType> dataTypes, final int length, final TiffDirectoryType exifDirectory, final boolean isOffset) {
        this.name = name;
        this.tag = tag;
        this.dataTypes = Collections.unmodifiableList((List<? extends FieldType>)new ArrayList<FieldType>(dataTypes));
        this.length = length;
        this.directoryType = exifDirectory;
        this.isOffset = isOffset;
    }
    
    public Object getValue(final TiffField entry) throws ImageReadException {
        return entry.getFieldType().getValue(entry);
    }
    
    public byte[] encodeValue(final FieldType fieldType, final Object value, final ByteOrder byteOrder) throws ImageWriteException {
        return fieldType.writeData(value, byteOrder);
    }
    
    public String getDescription() {
        return this.tag + " (0x" + Integer.toHexString(this.tag) + ": " + this.name + "): ";
    }
    
    @Override
    public String toString() {
        return "[TagInfo. tag: " + this.tag + " (0x" + Integer.toHexString(this.tag) + ", name: " + this.name + "]";
    }
    
    public boolean isOffset() {
        return this.isOffset;
    }
    
    public boolean isText() {
        return false;
    }
}
