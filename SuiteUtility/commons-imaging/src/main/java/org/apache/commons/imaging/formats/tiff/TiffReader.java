// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import java.util.Map;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoLong;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoDirectory;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.common.ByteConversions;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.imaging.FormatCompliance;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.nio.ByteOrder;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.io.InputStream;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.common.BinaryFileParser;

public class TiffReader extends BinaryFileParser
{
    private final boolean strict;
    
    public TiffReader(final boolean strict) {
        this.strict = strict;
    }
    
    private TiffHeader readTiffHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            return this.readTiffHeader(is);
        }
    }
    
    private ByteOrder getTiffByteOrder(final int byteOrderByte) throws ImageReadException {
        if (byteOrderByte == 73) {
            return ByteOrder.LITTLE_ENDIAN;
        }
        if (byteOrderByte == 77) {
            return ByteOrder.BIG_ENDIAN;
        }
        throw new ImageReadException("Invalid TIFF byte order " + (0xFF & byteOrderByte));
    }
    
    private TiffHeader readTiffHeader(final InputStream is) throws ImageReadException, IOException {
        final int byteOrder1 = BinaryFunctions.readByte("BYTE_ORDER_1", is, "Not a Valid TIFF File");
        final int byteOrder2 = BinaryFunctions.readByte("BYTE_ORDER_2", is, "Not a Valid TIFF File");
        if (byteOrder1 != byteOrder2) {
            throw new ImageReadException("Byte Order bytes don't match (" + byteOrder1 + ", " + byteOrder2 + ").");
        }
        final ByteOrder byteOrder3 = this.getTiffByteOrder(byteOrder1);
        this.setByteOrder(byteOrder3);
        final int tiffVersion = BinaryFunctions.read2Bytes("tiffVersion", is, "Not a Valid TIFF File", this.getByteOrder());
        if (tiffVersion != 42) {
            throw new ImageReadException("Unknown Tiff Version: " + tiffVersion);
        }
        final long offsetToFirstIFD = 0xFFFFFFFFL & (long)BinaryFunctions.read4Bytes("offsetToFirstIFD", is, "Not a Valid TIFF File", this.getByteOrder());
        BinaryFunctions.skipBytes(is, offsetToFirstIFD - 8L, "Not a Valid TIFF File: couldn't find IFDs");
        return new TiffHeader(byteOrder3, tiffVersion, offsetToFirstIFD);
    }
    
    private void readDirectories(final ByteSource byteSource, final FormatCompliance formatCompliance, final Listener listener) throws ImageReadException, IOException {
        final TiffHeader tiffHeader = this.readTiffHeader(byteSource);
        if (!listener.setTiffHeader(tiffHeader)) {
            return;
        }
        final long offset = tiffHeader.offsetToFirstIFD;
        final int dirType = 0;
        final List<Number> visited = new ArrayList<Number>();
        this.readDirectory(byteSource, offset, 0, formatCompliance, listener, visited);
    }
    
    private boolean readDirectory(final ByteSource byteSource, final long offset, final int dirType, final FormatCompliance formatCompliance, final Listener listener, final List<Number> visited) throws ImageReadException, IOException {
        final boolean ignoreNextDirectory = false;
        return this.readDirectory(byteSource, offset, dirType, formatCompliance, listener, false, visited);
    }
    
    private boolean readDirectory(final ByteSource byteSource, final long directoryOffset, final int dirType, final FormatCompliance formatCompliance, final Listener listener, final boolean ignoreNextDirectory, final List<Number> visited) throws ImageReadException, IOException {
        if (visited.contains(directoryOffset)) {
            return false;
        }
        visited.add(directoryOffset);
        try (final InputStream is = byteSource.getInputStream()) {
            if (directoryOffset >= byteSource.getLength()) {
                return true;
            }
            BinaryFunctions.skipBytes(is, directoryOffset);
            final List<TiffField> fields = new ArrayList<TiffField>();
            int entryCount;
            try {
                entryCount = BinaryFunctions.read2Bytes("DirectoryEntryCount", is, "Not a Valid TIFF File", this.getByteOrder());
            }
            catch (IOException e) {
                if (this.strict) {
                    throw e;
                }
                return true;
            }
            for (int i = 0; i < entryCount; ++i) {
                final int tag = BinaryFunctions.read2Bytes("Tag", is, "Not a Valid TIFF File", this.getByteOrder());
                final int type = BinaryFunctions.read2Bytes("Type", is, "Not a Valid TIFF File", this.getByteOrder());
                final long count = 0xFFFFFFFFL & (long)BinaryFunctions.read4Bytes("Count", is, "Not a Valid TIFF File", this.getByteOrder());
                final byte[] offsetBytes = BinaryFunctions.readBytes("Offset", is, 4, "Not a Valid TIFF File");
                final long offset = 0xFFFFFFFFL & (long)ByteConversions.toInt(offsetBytes, this.getByteOrder());
                if (tag != 0) {
                    FieldType fieldType;
                    try {
                        fieldType = FieldType.getFieldType(type);
                    }
                    catch (ImageReadException imageReadEx) {
                        continue;
                    }
                    final long valueLength = count * fieldType.getSize();
                    byte[] value;
                    if (valueLength > 4L) {
                        if (offset < 0L || offset + valueLength > byteSource.getLength()) {
                            if (this.strict) {
                                throw new IOException("Attempt to read byte range starting from " + offset + " of length " + valueLength + " which is outside the file's size of " + byteSource.getLength());
                            }
                            continue;
                        }
                        else {
                            value = byteSource.getBlock(offset, (int)valueLength);
                        }
                    }
                    else {
                        value = offsetBytes;
                    }
                    final TiffField field = new TiffField(tag, dirType, fieldType, count, offset, value, this.getByteOrder(), i);
                    fields.add(field);
                    if (!listener.addField(field)) {
                        return true;
                    }
                }
            }
            final long nextDirectoryOffset = 0xFFFFFFFFL & (long)BinaryFunctions.read4Bytes("nextDirectoryOffset", is, "Not a Valid TIFF File", this.getByteOrder());
            final TiffDirectory directory = new TiffDirectory(dirType, fields, directoryOffset, nextDirectoryOffset);
            if (listener.readImageData()) {
                if (directory.hasTiffImageData()) {
                    final TiffImageData rawImageData = this.getTiffRawImageData(byteSource, directory);
                    directory.setTiffImageData(rawImageData);
                }
                if (directory.hasJpegImageData()) {
                    final JpegImageData rawJpegImageData = this.getJpegRawImageData(byteSource, directory);
                    directory.setJpegImageData(rawJpegImageData);
                }
            }
            if (!listener.addDirectory(directory)) {
                return true;
            }
            if (listener.readOffsetDirectories()) {
                final TagInfoDirectory[] offsetFields = { ExifTagConstants.EXIF_TAG_EXIF_OFFSET, ExifTagConstants.EXIF_TAG_GPSINFO, ExifTagConstants.EXIF_TAG_INTEROP_OFFSET };
                final int[] directoryTypes = { -2, -3, -4 };
                for (int j = 0; j < offsetFields.length; ++j) {
                    final TagInfoDirectory offsetField = offsetFields[j];
                    final TiffField field2 = directory.findField(offsetField);
                    if (field2 != null) {
                        boolean subDirectoryRead = false;
                        try {
                            final long subDirectoryOffset = directory.getFieldValue(offsetField);
                            final int subDirectoryType = directoryTypes[j];
                            subDirectoryRead = this.readDirectory(byteSource, subDirectoryOffset, subDirectoryType, formatCompliance, listener, true, visited);
                        }
                        catch (ImageReadException imageReadException) {
                            if (this.strict) {
                                throw imageReadException;
                            }
                        }
                        if (!subDirectoryRead) {
                            fields.remove(field2);
                        }
                    }
                }
            }
            if (!ignoreNextDirectory && directory.nextDirectoryOffset > 0L) {
                this.readDirectory(byteSource, directory.nextDirectoryOffset, dirType + 1, formatCompliance, listener, visited);
            }
            return true;
        }
    }
    
    public TiffContents readFirstDirectory(final ByteSource byteSource, final Map<String, Object> params, final boolean readImageData, final FormatCompliance formatCompliance) throws ImageReadException, IOException {
        final Collector collector = new FirstDirectoryCollector(readImageData);
        this.read(byteSource, params, formatCompliance, collector);
        final TiffContents contents = collector.getContents();
        if (contents.directories.size() < 1) {
            throw new ImageReadException("Image did not contain any directories.");
        }
        return contents;
    }
    
    public TiffContents readDirectories(final ByteSource byteSource, final boolean readImageData, final FormatCompliance formatCompliance) throws ImageReadException, IOException {
        final Collector collector = new Collector(null);
        this.readDirectories(byteSource, formatCompliance, collector);
        final TiffContents contents = collector.getContents();
        if (contents.directories.size() < 1) {
            throw new ImageReadException("Image did not contain any directories.");
        }
        return contents;
    }
    
    public TiffContents readContents(final ByteSource byteSource, final Map<String, Object> params, final FormatCompliance formatCompliance) throws ImageReadException, IOException {
        final Collector collector = new Collector(params);
        this.read(byteSource, params, formatCompliance, collector);
        return collector.getContents();
    }
    
    public void read(final ByteSource byteSource, final Map<String, Object> params, final FormatCompliance formatCompliance, final Listener listener) throws ImageReadException, IOException {
        this.readDirectories(byteSource, formatCompliance, listener);
    }
    
    private TiffImageData getTiffRawImageData(final ByteSource byteSource, final TiffDirectory directory) throws ImageReadException, IOException {
        final List<TiffDirectory.ImageDataElement> elements = directory.getTiffRawImageDataElements();
        final TiffImageData.Data[] data = new TiffImageData.Data[elements.size()];
        if (byteSource instanceof ByteSourceFile) {
            final ByteSourceFile bsf = (ByteSourceFile)byteSource;
            for (int i = 0; i < elements.size(); ++i) {
                final TiffDirectory.ImageDataElement element = elements.get(i);
                data[i] = new TiffImageData.ByteSourceData(element.offset, element.length, bsf);
            }
        }
        else {
            for (int j = 0; j < elements.size(); ++j) {
                final TiffDirectory.ImageDataElement element2 = elements.get(j);
                final byte[] bytes = byteSource.getBlock(element2.offset, element2.length);
                data[j] = new TiffImageData.Data(element2.offset, element2.length, bytes);
            }
        }
        if (directory.imageDataInStrips()) {
            final TiffField rowsPerStripField = directory.findField(TiffTagConstants.TIFF_TAG_ROWS_PER_STRIP);
            int rowsPerStrip = Integer.MAX_VALUE;
            if (null != rowsPerStripField) {
                rowsPerStrip = rowsPerStripField.getIntValue();
            }
            else {
                final TiffField imageHeight = directory.findField(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH);
                if (imageHeight != null) {
                    rowsPerStrip = imageHeight.getIntValue();
                }
            }
            return new TiffImageData.Strips(data, rowsPerStrip);
        }
        final TiffField tileWidthField = directory.findField(TiffTagConstants.TIFF_TAG_TILE_WIDTH);
        if (null == tileWidthField) {
            throw new ImageReadException("Can't find tile width field.");
        }
        final int tileWidth = tileWidthField.getIntValue();
        final TiffField tileLengthField = directory.findField(TiffTagConstants.TIFF_TAG_TILE_LENGTH);
        if (null == tileLengthField) {
            throw new ImageReadException("Can't find tile length field.");
        }
        final int tileLength = tileLengthField.getIntValue();
        return new TiffImageData.Tiles(data, tileWidth, tileLength);
    }
    
    private JpegImageData getJpegRawImageData(final ByteSource byteSource, final TiffDirectory directory) throws ImageReadException, IOException {
        final TiffDirectory.ImageDataElement element = directory.getJpegRawImageDataElement();
        final long offset = element.offset;
        int length = element.length;
        if (offset + length > byteSource.getLength()) {
            length = (int)(byteSource.getLength() - offset);
        }
        final byte[] data = byteSource.getBlock(offset, length);
        if (this.strict && (length < 2 || ((data[data.length - 2] & 0xFF) << 8 | (data[data.length - 1] & 0xFF)) != 0xFFD9)) {
            throw new ImageReadException("JPEG EOI marker could not be found at expected location");
        }
        return new JpegImageData(offset, length, data);
    }
    
    private static class Collector implements Listener
    {
        private TiffHeader tiffHeader;
        private final List<TiffDirectory> directories;
        private final List<TiffField> fields;
        private final boolean readThumbnails;
        
        Collector() {
            this(null);
        }
        
        Collector(final Map<String, Object> params) {
            this.directories = new ArrayList<TiffDirectory>();
            this.fields = new ArrayList<TiffField>();
            boolean tmpReadThumbnails = true;
            if (params != null && params.containsKey("READ_THUMBNAILS")) {
                tmpReadThumbnails = Boolean.TRUE.equals(params.get("READ_THUMBNAILS"));
            }
            this.readThumbnails = tmpReadThumbnails;
        }
        
        @Override
        public boolean setTiffHeader(final TiffHeader tiffHeader) {
            this.tiffHeader = tiffHeader;
            return true;
        }
        
        @Override
        public boolean addDirectory(final TiffDirectory directory) {
            this.directories.add(directory);
            return true;
        }
        
        @Override
        public boolean addField(final TiffField field) {
            this.fields.add(field);
            return true;
        }
        
        @Override
        public boolean readImageData() {
            return this.readThumbnails;
        }
        
        @Override
        public boolean readOffsetDirectories() {
            return true;
        }
        
        public TiffContents getContents() {
            return new TiffContents(this.tiffHeader, this.directories);
        }
    }
    
    private static class FirstDirectoryCollector extends Collector
    {
        private final boolean readImageData;
        
        FirstDirectoryCollector(final boolean readImageData) {
            this.readImageData = readImageData;
        }
        
        @Override
        public boolean addDirectory(final TiffDirectory directory) {
            super.addDirectory(directory);
            return false;
        }
        
        @Override
        public boolean readImageData() {
            return this.readImageData;
        }
    }
    
    public interface Listener
    {
        boolean setTiffHeader(final TiffHeader p0);
        
        boolean addDirectory(final TiffDirectory p0);
        
        boolean addField(final TiffField p0);
        
        boolean readImageData();
        
        boolean readOffsetDirectories();
    }
}
