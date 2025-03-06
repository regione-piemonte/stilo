// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.write;

import org.apache.commons.imaging.common.BinaryOutputStream;
import java.util.Comparator;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.TiffImageData;
import org.apache.commons.imaging.formats.tiff.TiffElement;
import org.apache.commons.imaging.common.mylzw.MyLzwCompressor;
import org.apache.commons.imaging.common.PackBits;
import org.apache.commons.imaging.common.itu_t4.T4AndT6Compression;
import org.apache.commons.imaging.PixelDensity;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import java.util.Collections;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageWriteException;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.imaging.formats.tiff.constants.TiffConstants;
import java.nio.ByteOrder;

public abstract class TiffImageWriterBase
{
    protected final ByteOrder byteOrder;
    
    public TiffImageWriterBase() {
        this.byteOrder = TiffConstants.DEFAULT_TIFF_BYTE_ORDER;
    }
    
    public TiffImageWriterBase(final ByteOrder byteOrder) {
        this.byteOrder = byteOrder;
    }
    
    protected static int imageDataPaddingLength(final int dataLength) {
        return (4 - dataLength % 4) % 4;
    }
    
    public abstract void write(final OutputStream p0, final TiffOutputSet p1) throws IOException, ImageWriteException;
    
    protected TiffOutputSummary validateDirectories(final TiffOutputSet outputSet) throws ImageWriteException {
        final List<TiffOutputDirectory> directories = outputSet.getDirectories();
        if (directories.isEmpty()) {
            throw new ImageWriteException("No directories.");
        }
        TiffOutputDirectory exifDirectory = null;
        TiffOutputDirectory gpsDirectory = null;
        TiffOutputDirectory interoperabilityDirectory = null;
        TiffOutputField exifDirectoryOffsetField = null;
        TiffOutputField gpsDirectoryOffsetField = null;
        TiffOutputField interoperabilityDirectoryOffsetField = null;
        final List<Integer> directoryIndices = new ArrayList<Integer>();
        final Map<Integer, TiffOutputDirectory> directoryTypeMap = new HashMap<Integer, TiffOutputDirectory>();
        for (final TiffOutputDirectory directory : directories) {
            final int dirType = directory.type;
            directoryTypeMap.put(dirType, directory);
            if (dirType < 0) {
                switch (dirType) {
                    case -2: {
                        if (exifDirectory != null) {
                            throw new ImageWriteException("More than one EXIF directory.");
                        }
                        exifDirectory = directory;
                        break;
                    }
                    case -3: {
                        if (gpsDirectory != null) {
                            throw new ImageWriteException("More than one GPS directory.");
                        }
                        gpsDirectory = directory;
                        break;
                    }
                    case -4: {
                        if (interoperabilityDirectory != null) {
                            throw new ImageWriteException("More than one Interoperability directory.");
                        }
                        interoperabilityDirectory = directory;
                        break;
                    }
                    default: {
                        throw new ImageWriteException("Unknown directory: " + dirType);
                    }
                }
            }
            else {
                if (directoryIndices.contains(dirType)) {
                    throw new ImageWriteException("More than one directory with index: " + dirType + ".");
                }
                directoryIndices.add(dirType);
            }
            final HashSet<Integer> fieldTags = new HashSet<Integer>();
            final List<TiffOutputField> fields = directory.getFields();
            for (final TiffOutputField field : fields) {
                if (fieldTags.contains(field.tag)) {
                    throw new ImageWriteException("Tag (" + field.tagInfo.getDescription() + ") appears twice in directory.");
                }
                fieldTags.add(field.tag);
                if (field.tag == ExifTagConstants.EXIF_TAG_EXIF_OFFSET.tag) {
                    if (exifDirectoryOffsetField != null) {
                        throw new ImageWriteException("More than one Exif directory offset field.");
                    }
                    exifDirectoryOffsetField = field;
                }
                else if (field.tag == ExifTagConstants.EXIF_TAG_INTEROP_OFFSET.tag) {
                    if (interoperabilityDirectoryOffsetField != null) {
                        throw new ImageWriteException("More than one Interoperability directory offset field.");
                    }
                    interoperabilityDirectoryOffsetField = field;
                }
                else {
                    if (field.tag != ExifTagConstants.EXIF_TAG_GPSINFO.tag) {
                        continue;
                    }
                    if (gpsDirectoryOffsetField != null) {
                        throw new ImageWriteException("More than one GPS directory offset field.");
                    }
                    gpsDirectoryOffsetField = field;
                }
            }
        }
        if (directoryIndices.isEmpty()) {
            throw new ImageWriteException("Missing root directory.");
        }
        Collections.sort(directoryIndices);
        TiffOutputDirectory previousDirectory = null;
        for (int i = 0; i < directoryIndices.size(); ++i) {
            final Integer index = directoryIndices.get(i);
            if (index != i) {
                throw new ImageWriteException("Missing directory: " + i + ".");
            }
            final TiffOutputDirectory directory2 = directoryTypeMap.get(index);
            if (null != previousDirectory) {
                previousDirectory.setNextDirectory(directory2);
            }
            previousDirectory = directory2;
        }
        final TiffOutputDirectory rootDirectory = directoryTypeMap.get(0);
        final TiffOutputSummary result = new TiffOutputSummary(this.byteOrder, rootDirectory, directoryTypeMap);
        if (interoperabilityDirectory == null && interoperabilityDirectoryOffsetField != null) {
            throw new ImageWriteException("Output set has Interoperability Directory Offset field, but no Interoperability Directory");
        }
        if (interoperabilityDirectory != null) {
            if (exifDirectory == null) {
                exifDirectory = outputSet.addExifDirectory();
            }
            if (interoperabilityDirectoryOffsetField == null) {
                interoperabilityDirectoryOffsetField = TiffOutputField.createOffsetField(ExifTagConstants.EXIF_TAG_INTEROP_OFFSET, this.byteOrder);
                exifDirectory.add(interoperabilityDirectoryOffsetField);
            }
            result.add(interoperabilityDirectory, interoperabilityDirectoryOffsetField);
        }
        if (exifDirectory == null && exifDirectoryOffsetField != null) {
            throw new ImageWriteException("Output set has Exif Directory Offset field, but no Exif Directory");
        }
        if (exifDirectory != null) {
            if (exifDirectoryOffsetField == null) {
                exifDirectoryOffsetField = TiffOutputField.createOffsetField(ExifTagConstants.EXIF_TAG_EXIF_OFFSET, this.byteOrder);
                rootDirectory.add(exifDirectoryOffsetField);
            }
            result.add(exifDirectory, exifDirectoryOffsetField);
        }
        if (gpsDirectory == null && gpsDirectoryOffsetField != null) {
            throw new ImageWriteException("Output set has GPS Directory Offset field, but no GPS Directory");
        }
        if (gpsDirectory != null) {
            if (gpsDirectoryOffsetField == null) {
                gpsDirectoryOffsetField = TiffOutputField.createOffsetField(ExifTagConstants.EXIF_TAG_GPSINFO, this.byteOrder);
                rootDirectory.add(gpsDirectoryOffsetField);
            }
            result.add(gpsDirectory, gpsDirectoryOffsetField);
        }
        return result;
    }
    
    public void writeImage(final BufferedImage src, final OutputStream os, Map<String, Object> params) throws ImageWriteException, IOException {
        params = new HashMap<String, Object>(params);
        if (params.containsKey("FORMAT")) {
            params.remove("FORMAT");
        }
        TiffOutputSet userExif = null;
        if (params.containsKey("EXIF")) {
            userExif = params.remove("EXIF");
        }
        String xmpXml = null;
        if (params.containsKey("XMP_XML")) {
            xmpXml = params.get("XMP_XML");
            params.remove("XMP_XML");
        }
        PixelDensity pixelDensity = params.remove("PIXEL_DENSITY");
        if (pixelDensity == null) {
            pixelDensity = PixelDensity.createFromPixelsPerInch(72.0, 72.0);
        }
        final int width = src.getWidth();
        final int height = src.getHeight();
        int compression = 5;
        int stripSizeInBits = 64000;
        if (params.containsKey("COMPRESSION")) {
            final Object value = params.get("COMPRESSION");
            if (value != null) {
                if (!(value instanceof Number)) {
                    throw new ImageWriteException("Invalid compression parameter, must be numeric: " + value);
                }
                compression = ((Number)value).intValue();
            }
            params.remove("COMPRESSION");
            if (params.containsKey("PARAM_KEY_LZW_COMPRESSION_BLOCK_SIZE")) {
                final Object bValue = params.get("PARAM_KEY_LZW_COMPRESSION_BLOCK_SIZE");
                if (!(bValue instanceof Number)) {
                    throw new ImageWriteException("Invalid compression block-size parameter: " + value);
                }
                final int stripSizeInBytes = ((Number)bValue).intValue();
                if (stripSizeInBytes < 8000) {
                    throw new ImageWriteException("Block size parameter " + stripSizeInBytes + " is less than 8000 minimum");
                }
                stripSizeInBits = stripSizeInBytes * 8;
                params.remove("PARAM_KEY_LZW_COMPRESSION_BLOCK_SIZE");
            }
        }
        final HashMap<String, Object> rawParams = new HashMap<String, Object>(params);
        params.remove("T4_OPTIONS");
        params.remove("T6_OPTIONS");
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageWriteException("Unknown parameter: " + firstKey);
        }
        int samplesPerPixel;
        int bitsPerSample;
        int photometricInterpretation;
        if (compression == 2 || compression == 3 || compression == 4) {
            samplesPerPixel = 1;
            bitsPerSample = 1;
            photometricInterpretation = 0;
        }
        else {
            samplesPerPixel = 3;
            bitsPerSample = 8;
            photometricInterpretation = 2;
        }
        int rowsPerStrip = stripSizeInBits / (width * bitsPerSample * samplesPerPixel);
        rowsPerStrip = Math.max(1, rowsPerStrip);
        final byte[][] strips = this.getStrips(src, samplesPerPixel, bitsPerSample, rowsPerStrip);
        int t4Options = 0;
        int t6Options = 0;
        if (compression == 2) {
            for (int i = 0; i < strips.length; ++i) {
                strips[i] = T4AndT6Compression.compressModifiedHuffman(strips[i], width, strips[i].length / ((width + 7) / 8));
            }
        }
        else if (compression == 3) {
            final Integer t4Parameter = rawParams.get("T4_OPTIONS");
            if (t4Parameter != null) {
                t4Options = t4Parameter;
            }
            t4Options &= 0x7;
            final boolean is2D = (t4Options & 0x1) != 0x0;
            final boolean usesUncompressedMode = (t4Options & 0x2) != 0x0;
            if (usesUncompressedMode) {
                throw new ImageWriteException("T.4 compression with the uncompressed mode extension is not yet supported");
            }
            final boolean hasFillBitsBeforeEOL = (t4Options & 0x4) != 0x0;
            for (int j = 0; j < strips.length; ++j) {
                if (is2D) {
                    strips[j] = T4AndT6Compression.compressT4_2D(strips[j], width, strips[j].length / ((width + 7) / 8), hasFillBitsBeforeEOL, rowsPerStrip);
                }
                else {
                    strips[j] = T4AndT6Compression.compressT4_1D(strips[j], width, strips[j].length / ((width + 7) / 8), hasFillBitsBeforeEOL);
                }
            }
        }
        else if (compression == 4) {
            final Integer t6Parameter = rawParams.get("T6_OPTIONS");
            if (t6Parameter != null) {
                t6Options = t6Parameter;
            }
            t6Options &= 0x4;
            final boolean usesUncompressedMode2 = (t6Options & 0x2) != 0x0;
            if (usesUncompressedMode2) {
                throw new ImageWriteException("T.6 compression with the uncompressed mode extension is not yet supported");
            }
            for (int k = 0; k < strips.length; ++k) {
                strips[k] = T4AndT6Compression.compressT6(strips[k], width, strips[k].length / ((width + 7) / 8));
            }
        }
        else if (compression == 32773) {
            for (int i = 0; i < strips.length; ++i) {
                strips[i] = new PackBits().compress(strips[i]);
            }
        }
        else if (compression == 5) {
            for (int i = 0; i < strips.length; ++i) {
                final byte[] uncompressed = strips[i];
                final int LZW_MINIMUM_CODE_SIZE = 8;
                final MyLzwCompressor compressor = new MyLzwCompressor(8, ByteOrder.BIG_ENDIAN, true);
                final byte[] compressed = compressor.compress(uncompressed);
                strips[i] = compressed;
            }
        }
        else if (compression != 1) {
            throw new ImageWriteException("Invalid compression parameter (Only CCITT 1D/Group 3/Group 4, LZW, Packbits and uncompressed supported).");
        }
        final TiffElement.DataElement[] imageData = new TiffElement.DataElement[strips.length];
        for (int l = 0; l < strips.length; ++l) {
            imageData[l] = new TiffImageData.Data(0L, strips[l].length, strips[l]);
        }
        final TiffOutputSet outputSet = new TiffOutputSet(this.byteOrder);
        final TiffOutputDirectory directory = outputSet.addRootDirectory();
        directory.add(TiffTagConstants.TIFF_TAG_IMAGE_WIDTH, width);
        directory.add(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH, height);
        directory.add(TiffTagConstants.TIFF_TAG_PHOTOMETRIC_INTERPRETATION, (short)photometricInterpretation);
        directory.add(TiffTagConstants.TIFF_TAG_COMPRESSION, (short)compression);
        directory.add(TiffTagConstants.TIFF_TAG_SAMPLES_PER_PIXEL, (short)samplesPerPixel);
        if (samplesPerPixel == 3) {
            directory.add(TiffTagConstants.TIFF_TAG_BITS_PER_SAMPLE, (short)bitsPerSample, (short)bitsPerSample, (short)bitsPerSample);
        }
        else if (samplesPerPixel == 1) {
            directory.add(TiffTagConstants.TIFF_TAG_BITS_PER_SAMPLE, (short)bitsPerSample);
        }
        directory.add(TiffTagConstants.TIFF_TAG_ROWS_PER_STRIP, rowsPerStrip);
        if (pixelDensity.isUnitless()) {
            directory.add(TiffTagConstants.TIFF_TAG_RESOLUTION_UNIT, (short)0);
            directory.add(TiffTagConstants.TIFF_TAG_XRESOLUTION, RationalNumber.valueOf(pixelDensity.getRawHorizontalDensity()));
            directory.add(TiffTagConstants.TIFF_TAG_YRESOLUTION, RationalNumber.valueOf(pixelDensity.getRawVerticalDensity()));
        }
        else if (pixelDensity.isInInches()) {
            directory.add(TiffTagConstants.TIFF_TAG_RESOLUTION_UNIT, (short)2);
            directory.add(TiffTagConstants.TIFF_TAG_XRESOLUTION, RationalNumber.valueOf(pixelDensity.horizontalDensityInches()));
            directory.add(TiffTagConstants.TIFF_TAG_YRESOLUTION, RationalNumber.valueOf(pixelDensity.verticalDensityInches()));
        }
        else {
            directory.add(TiffTagConstants.TIFF_TAG_RESOLUTION_UNIT, (short)1);
            directory.add(TiffTagConstants.TIFF_TAG_XRESOLUTION, RationalNumber.valueOf(pixelDensity.horizontalDensityCentimetres()));
            directory.add(TiffTagConstants.TIFF_TAG_YRESOLUTION, RationalNumber.valueOf(pixelDensity.verticalDensityCentimetres()));
        }
        if (t4Options != 0) {
            directory.add(TiffTagConstants.TIFF_TAG_T4_OPTIONS, t4Options);
        }
        if (t6Options != 0) {
            directory.add(TiffTagConstants.TIFF_TAG_T6_OPTIONS, t6Options);
        }
        if (null != xmpXml) {
            final byte[] xmpXmlBytes = xmpXml.getBytes(StandardCharsets.UTF_8);
            directory.add(TiffTagConstants.TIFF_TAG_XMP, xmpXmlBytes);
        }
        final TiffImageData tiffImageData = new TiffImageData.Strips(imageData, rowsPerStrip);
        directory.setTiffImageData(tiffImageData);
        if (userExif != null) {
            this.combineUserExifIntoFinalExif(userExif, outputSet);
        }
        this.write(os, outputSet);
    }
    
    private void combineUserExifIntoFinalExif(final TiffOutputSet userExif, final TiffOutputSet outputSet) throws ImageWriteException {
        final List<TiffOutputDirectory> outputDirectories = outputSet.getDirectories();
        Collections.sort(outputDirectories, TiffOutputDirectory.COMPARATOR);
        for (final TiffOutputDirectory userDirectory : userExif.getDirectories()) {
            final int location = Collections.binarySearch(outputDirectories, userDirectory, TiffOutputDirectory.COMPARATOR);
            if (location < 0) {
                outputSet.addDirectory(userDirectory);
            }
            else {
                final TiffOutputDirectory outputDirectory = outputDirectories.get(location);
                for (final TiffOutputField userField : userDirectory.getFields()) {
                    if (outputDirectory.findField(userField.tagInfo) == null) {
                        outputDirectory.add(userField);
                    }
                }
            }
        }
    }
    
    private byte[][] getStrips(final BufferedImage src, final int samplesPerPixel, final int bitsPerSample, final int rowsPerStrip) {
        final int width = src.getWidth();
        final int height = src.getHeight();
        final int stripCount = (height + rowsPerStrip - 1) / rowsPerStrip;
        final byte[][] result = new byte[stripCount][];
        int remainingRows = height;
        for (int i = 0; i < stripCount; ++i) {
            final int rowsInStrip = Math.min(rowsPerStrip, remainingRows);
            remainingRows -= rowsInStrip;
            final int bitsInRow = bitsPerSample * samplesPerPixel * width;
            final int bytesPerRow = (bitsInRow + 7) / 8;
            final int bytesInStrip = rowsInStrip * bytesPerRow;
            final byte[] uncompressed = new byte[bytesInStrip];
            int counter = 0;
            for (int y = i * rowsPerStrip, stop = i * rowsPerStrip + rowsPerStrip; y < height && y < stop; ++y) {
                int bitCache = 0;
                int bitsInCache = 0;
                for (int x = 0; x < width; ++x) {
                    final int rgb = src.getRGB(x, y);
                    final int red = 0xFF & rgb >> 16;
                    final int green = 0xFF & rgb >> 8;
                    final int blue = 0xFF & rgb >> 0;
                    if (bitsPerSample == 1) {
                        int sample = (red + green + blue) / 3;
                        if (sample > 127) {
                            sample = 0;
                        }
                        else {
                            sample = 1;
                        }
                        bitCache <<= 1;
                        bitCache |= sample;
                        if (++bitsInCache == 8) {
                            uncompressed[counter++] = (byte)bitCache;
                            bitCache = 0;
                            bitsInCache = 0;
                        }
                    }
                    else {
                        uncompressed[counter++] = (byte)red;
                        uncompressed[counter++] = (byte)green;
                        uncompressed[counter++] = (byte)blue;
                    }
                }
                if (bitsInCache > 0) {
                    bitCache <<= 8 - bitsInCache;
                    uncompressed[counter++] = (byte)bitCache;
                }
            }
            result[i] = uncompressed;
        }
        return result;
    }
    
    protected void writeImageFileHeader(final BinaryOutputStream bos) throws IOException {
        final int offsetToFirstIFD = 8;
        this.writeImageFileHeader(bos, 8L);
    }
    
    protected void writeImageFileHeader(final BinaryOutputStream bos, final long offsetToFirstIFD) throws IOException {
        if (this.byteOrder == ByteOrder.LITTLE_ENDIAN) {
            bos.write(73);
            bos.write(73);
        }
        else {
            bos.write(77);
            bos.write(77);
        }
        bos.write2Bytes(42);
        bos.write4Bytes((int)offsetToFirstIFD);
    }
}
