// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff;

import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.formats.tiff.write.TiffImageWriterLossy;
import java.io.OutputStream;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreterLogLuv;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreterCieLab;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreterYCbCr;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreterCmyk;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreterRgb;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreterPalette;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreterBiLevel;
import org.apache.commons.imaging.formats.tiff.datareaders.ImageDataReader;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreter;
import org.apache.commons.imaging.common.ImageBuilder;
import java.awt.Rectangle;
import java.nio.ByteOrder;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageInfo;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import java.awt.Dimension;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoBytes;
import org.apache.commons.imaging.formats.tiff.constants.TiffEpTagConstants;
import org.apache.commons.imaging.FormatCompliance;
import java.util.Map;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageParser;

public class TiffImageParser extends ImageParser
{
    private static final String DEFAULT_EXTENSION = ".tif";
    private static final String[] ACCEPTED_EXTENSIONS;
    
    @Override
    public String getName() {
        return "Tiff-Custom";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".tif";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return TiffImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.TIFF };
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final FormatCompliance formatCompliance = FormatCompliance.getDefault();
        final TiffContents contents = new TiffReader(ImageParser.isStrict(params)).readFirstDirectory(byteSource, params, false, formatCompliance);
        final TiffDirectory directory = contents.directories.get(0);
        return directory.getFieldValue(TiffEpTagConstants.EXIF_TAG_INTER_COLOR_PROFILE, false);
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final FormatCompliance formatCompliance = FormatCompliance.getDefault();
        final TiffContents contents = new TiffReader(ImageParser.isStrict(params)).readFirstDirectory(byteSource, params, false, formatCompliance);
        final TiffDirectory directory = contents.directories.get(0);
        final TiffField widthField = directory.findField(TiffTagConstants.TIFF_TAG_IMAGE_WIDTH, true);
        final TiffField heightField = directory.findField(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH, true);
        if (widthField == null || heightField == null) {
            throw new ImageReadException("TIFF image missing size info.");
        }
        final int height = heightField.getIntValue();
        final int width = widthField.getIntValue();
        return new Dimension(width, height);
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final FormatCompliance formatCompliance = FormatCompliance.getDefault();
        final TiffReader tiffReader = new TiffReader(ImageParser.isStrict(params));
        final TiffContents contents = tiffReader.readContents(byteSource, params, formatCompliance);
        final List<TiffDirectory> directories = contents.directories;
        final TiffImageMetadata result = new TiffImageMetadata(contents);
        for (final TiffDirectory dir : directories) {
            final TiffImageMetadata.Directory metadataDirectory = new TiffImageMetadata.Directory(tiffReader.getByteOrder(), dir);
            final List<TiffField> entries = dir.getDirectoryEntries();
            for (final TiffField entry : entries) {
                metadataDirectory.add(entry);
            }
            result.add(metadataDirectory);
        }
        return result;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final FormatCompliance formatCompliance = FormatCompliance.getDefault();
        final TiffContents contents = new TiffReader(ImageParser.isStrict(params)).readDirectories(byteSource, false, formatCompliance);
        final TiffDirectory directory = contents.directories.get(0);
        final TiffField widthField = directory.findField(TiffTagConstants.TIFF_TAG_IMAGE_WIDTH, true);
        final TiffField heightField = directory.findField(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH, true);
        if (widthField == null || heightField == null) {
            throw new ImageReadException("TIFF image missing size info.");
        }
        final int height = heightField.getIntValue();
        final int width = widthField.getIntValue();
        final TiffField resolutionUnitField = directory.findField(TiffTagConstants.TIFF_TAG_RESOLUTION_UNIT);
        int resolutionUnit = 2;
        if (resolutionUnitField != null && resolutionUnitField.getValue() != null) {
            resolutionUnit = resolutionUnitField.getIntValue();
        }
        double unitsPerInch = -1.0;
        switch (resolutionUnit) {
            case 2: {
                unitsPerInch = 1.0;
                break;
            }
            case 3: {
                unitsPerInch = 2.54;
                break;
            }
        }
        int physicalWidthDpi = -1;
        float physicalWidthInch = -1.0f;
        int physicalHeightDpi = -1;
        float physicalHeightInch = -1.0f;
        if (unitsPerInch > 0.0) {
            final TiffField xResolutionField = directory.findField(TiffTagConstants.TIFF_TAG_XRESOLUTION);
            final TiffField yResolutionField = directory.findField(TiffTagConstants.TIFF_TAG_YRESOLUTION);
            if (xResolutionField != null && xResolutionField.getValue() != null) {
                final double xResolutionPixelsPerUnit = xResolutionField.getDoubleValue();
                physicalWidthDpi = (int)Math.round(xResolutionPixelsPerUnit * unitsPerInch);
                physicalWidthInch = (float)(width / (xResolutionPixelsPerUnit * unitsPerInch));
            }
            if (yResolutionField != null && yResolutionField.getValue() != null) {
                final double yResolutionPixelsPerUnit = yResolutionField.getDoubleValue();
                physicalHeightDpi = (int)Math.round(yResolutionPixelsPerUnit * unitsPerInch);
                physicalHeightInch = (float)(height / (yResolutionPixelsPerUnit * unitsPerInch));
            }
        }
        final TiffField bitsPerSampleField = directory.findField(TiffTagConstants.TIFF_TAG_BITS_PER_SAMPLE);
        int bitsPerSample = 1;
        if (bitsPerSampleField != null && bitsPerSampleField.getValue() != null) {
            bitsPerSample = bitsPerSampleField.getIntValueOrArraySum();
        }
        final int bitsPerPixel = bitsPerSample;
        final List<TiffField> entries = directory.entries;
        final List<String> comments = new ArrayList<String>(entries.size());
        for (final TiffField field : entries) {
            final String comment = field.toString();
            comments.add(comment);
        }
        final ImageFormat format = ImageFormats.TIFF;
        final String formatName = "TIFF Tag-based Image File Format";
        final String mimeType = "image/tiff";
        final int numberOfImages = contents.directories.size();
        final boolean progressive = false;
        final String formatDetails = "Tiff v." + contents.header.tiffVersion;
        final boolean transparent = false;
        boolean usesPalette = false;
        final TiffField colorMapField = directory.findField(TiffTagConstants.TIFF_TAG_COLOR_MAP);
        if (colorMapField != null) {
            usesPalette = true;
        }
        final ImageInfo.ColorType colorType = ImageInfo.ColorType.RGB;
        short compressionFieldValue;
        if (directory.findField(TiffTagConstants.TIFF_TAG_COMPRESSION) != null) {
            compressionFieldValue = directory.getFieldValue(TiffTagConstants.TIFF_TAG_COMPRESSION);
        }
        else {
            compressionFieldValue = 1;
        }
        final int compression = 0xFFFF & compressionFieldValue;
        ImageInfo.CompressionAlgorithm compressionAlgorithm = null;
        switch (compression) {
            case 1: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.NONE;
                break;
            }
            case 2: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.CCITT_1D;
                break;
            }
            case 3: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.CCITT_GROUP_3;
                break;
            }
            case 4: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.CCITT_GROUP_4;
                break;
            }
            case 5: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.LZW;
                break;
            }
            case 6: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.JPEG;
                break;
            }
            case 32771: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.NONE;
                break;
            }
            case 32773: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.PACKBITS;
                break;
            }
            default: {
                compressionAlgorithm = ImageInfo.CompressionAlgorithm.UNKNOWN;
                break;
            }
        }
        final ImageInfo result = new ImageInfo(formatDetails, bitsPerPixel, comments, format, "TIFF Tag-based Image File Format", height, "image/tiff", numberOfImages, physicalHeightDpi, physicalHeightInch, physicalWidthDpi, physicalWidthInch, width, false, false, usesPalette, colorType, compressionAlgorithm);
        return result;
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final FormatCompliance formatCompliance = FormatCompliance.getDefault();
        final TiffContents contents = new TiffReader(ImageParser.isStrict(params)).readDirectories(byteSource, false, formatCompliance);
        final TiffDirectory directory = contents.directories.get(0);
        final byte[] bytes = directory.getFieldValue(TiffTagConstants.TIFF_TAG_XMP, false);
        if (bytes == null) {
            return null;
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        try {
            pw.println("tiff.dumpImageFile");
            final ImageInfo imageData = this.getImageInfo(byteSource);
            if (imageData == null) {
                return false;
            }
            imageData.toString(pw, "");
            pw.println("");
            final FormatCompliance formatCompliance = FormatCompliance.getDefault();
            final Map<String, Object> params = null;
            final TiffContents contents = new TiffReader(true).readContents(byteSource, params, formatCompliance);
            final List<TiffDirectory> directories = contents.directories;
            if (directories == null) {
                return false;
            }
            for (int d = 0; d < directories.size(); ++d) {
                final TiffDirectory directory = directories.get(d);
                final List<TiffField> entries = directory.entries;
                if (entries == null) {
                    return false;
                }
                for (final TiffField field : entries) {
                    field.dump(pw, Integer.toString(d));
                }
            }
            pw.println("");
            return true;
        }
        finally {
            pw.println("");
        }
    }
    
    @Override
    public FormatCompliance getFormatCompliance(final ByteSource byteSource) throws ImageReadException, IOException {
        final FormatCompliance formatCompliance = FormatCompliance.getDefault();
        final Map<String, Object> params = null;
        new TiffReader(ImageParser.isStrict(params)).readContents(byteSource, params, formatCompliance);
        return formatCompliance;
    }
    
    public List<byte[]> collectRawImageData(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final FormatCompliance formatCompliance = FormatCompliance.getDefault();
        final TiffContents contents = new TiffReader(ImageParser.isStrict(params)).readDirectories(byteSource, true, formatCompliance);
        final List<byte[]> result = new ArrayList<byte[]>();
        for (int i = 0; i < contents.directories.size(); ++i) {
            final TiffDirectory directory = contents.directories.get(i);
            final List<TiffDirectory.ImageDataElement> dataElements = directory.getTiffRawImageDataElements();
            for (final TiffDirectory.ImageDataElement element : dataElements) {
                final byte[] bytes = byteSource.getBlock(element.offset, element.length);
                result.add(bytes);
            }
        }
        return result;
    }
    
    @Override
    public BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final FormatCompliance formatCompliance = FormatCompliance.getDefault();
        final TiffReader reader = new TiffReader(ImageParser.isStrict(params));
        final TiffContents contents = reader.readFirstDirectory(byteSource, params, true, formatCompliance);
        final ByteOrder byteOrder = reader.getByteOrder();
        final TiffDirectory directory = contents.directories.get(0);
        final BufferedImage result = directory.getTiffImage(byteOrder, params);
        if (null == result) {
            throw new ImageReadException("TIFF does not contain an image.");
        }
        return result;
    }
    
    @Override
    public List<BufferedImage> getAllBufferedImages(final ByteSource byteSource) throws ImageReadException, IOException {
        final FormatCompliance formatCompliance = FormatCompliance.getDefault();
        final TiffReader tiffReader = new TiffReader(true);
        final TiffContents contents = tiffReader.readDirectories(byteSource, true, formatCompliance);
        final List<BufferedImage> results = new ArrayList<BufferedImage>();
        for (int i = 0; i < contents.directories.size(); ++i) {
            final TiffDirectory directory = contents.directories.get(i);
            final BufferedImage result = directory.getTiffImage(tiffReader.getByteOrder(), null);
            if (result != null) {
                results.add(result);
            }
        }
        return results;
    }
    
    private Integer getIntegerParameter(final String key, final Map<String, Object> params) throws ImageReadException {
        if (params == null) {
            return null;
        }
        if (!params.containsKey(key)) {
            return null;
        }
        final Object obj = params.get(key);
        if (obj instanceof Integer) {
            return (Integer)obj;
        }
        throw new ImageReadException("Non-Integer parameter " + key);
    }
    
    private Rectangle checkForSubImage(final Map<String, Object> params) throws ImageReadException {
        final Integer ix0 = this.getIntegerParameter("SUBIMAGE_X", params);
        final Integer iy0 = this.getIntegerParameter("SUBIMAGE_Y", params);
        final Integer iwidth = this.getIntegerParameter("SUBIMAGE_WIDTH", params);
        final Integer iheight = this.getIntegerParameter("SUBIMAGE_HEIGHT", params);
        if (ix0 == null && iy0 == null && iwidth == null && iheight == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder(32);
        if (ix0 == null) {
            sb.append(" x0,");
        }
        if (iy0 == null) {
            sb.append(" y0,");
        }
        if (iwidth == null) {
            sb.append(" width,");
        }
        if (iheight == null) {
            sb.append(" height,");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
            throw new ImageReadException("Incomplete subimage parameters, missing" + sb.toString());
        }
        return new Rectangle(ix0, iy0, iwidth, iheight);
    }
    
    protected BufferedImage getBufferedImage(final TiffDirectory directory, final ByteOrder byteOrder, final Map<String, Object> params) throws ImageReadException, IOException {
        final List<TiffField> entries = directory.entries;
        if (entries == null) {
            throw new ImageReadException("TIFF missing entries");
        }
        final int photometricInterpretation = 0xFFFF & directory.getFieldValue(TiffTagConstants.TIFF_TAG_PHOTOMETRIC_INTERPRETATION);
        short compressionFieldValue;
        if (directory.findField(TiffTagConstants.TIFF_TAG_COMPRESSION) != null) {
            compressionFieldValue = directory.getFieldValue(TiffTagConstants.TIFF_TAG_COMPRESSION);
        }
        else {
            compressionFieldValue = 1;
        }
        final int compression = 0xFFFF & compressionFieldValue;
        final int width = directory.getSingleFieldValue(TiffTagConstants.TIFF_TAG_IMAGE_WIDTH);
        final int height = directory.getSingleFieldValue(TiffTagConstants.TIFF_TAG_IMAGE_LENGTH);
        Rectangle subImage = this.checkForSubImage(params);
        if (subImage != null) {
            if (subImage.width <= 0) {
                throw new ImageReadException("negative or zero subimage width");
            }
            if (subImage.height <= 0) {
                throw new ImageReadException("negative or zero subimage height");
            }
            if (subImage.x < 0 || subImage.x >= width) {
                throw new ImageReadException("subimage x is outside raster");
            }
            if (subImage.x + subImage.width > width) {
                throw new ImageReadException("subimage (x+width) is outside raster");
            }
            if (subImage.y < 0 || subImage.y >= height) {
                throw new ImageReadException("subimage y is outside raster");
            }
            if (subImage.y + subImage.height > height) {
                throw new ImageReadException("subimage (y+height) is outside raster");
            }
            if (subImage.x == 0 && subImage.y == 0 && subImage.width == width && subImage.height == height) {
                subImage = null;
            }
        }
        int samplesPerPixel = 1;
        final TiffField samplesPerPixelField = directory.findField(TiffTagConstants.TIFF_TAG_SAMPLES_PER_PIXEL);
        if (samplesPerPixelField != null) {
            samplesPerPixel = samplesPerPixelField.getIntValue();
        }
        int[] bitsPerSample = { 1 };
        int bitsPerPixel = samplesPerPixel;
        final TiffField bitsPerSampleField = directory.findField(TiffTagConstants.TIFF_TAG_BITS_PER_SAMPLE);
        if (bitsPerSampleField != null) {
            bitsPerSample = bitsPerSampleField.getIntArrayValue();
            bitsPerPixel = bitsPerSampleField.getIntValueOrArraySum();
        }
        int predictor = -1;
        final TiffField predictorField = directory.findField(TiffTagConstants.TIFF_TAG_PREDICTOR);
        if (null != predictorField) {
            predictor = predictorField.getIntValueOrArraySum();
        }
        if (samplesPerPixel != bitsPerSample.length) {
            throw new ImageReadException("Tiff: samplesPerPixel (" + samplesPerPixel + ")!=fBitsPerSample.length (" + bitsPerSample.length + ")");
        }
        final PhotometricInterpreter photometricInterpreter = this.getPhotometricInterpreter(directory, photometricInterpretation, bitsPerPixel, bitsPerSample, predictor, samplesPerPixel, width, height);
        final TiffImageData imageData = directory.getTiffImageData();
        final ImageDataReader dataReader = imageData.getDataReader(directory, photometricInterpreter, bitsPerPixel, bitsPerSample, predictor, samplesPerPixel, width, height, compression, byteOrder);
        BufferedImage result = null;
        if (subImage != null) {
            result = dataReader.readImageData(subImage);
        }
        else {
            final boolean hasAlpha = false;
            final ImageBuilder imageBuilder = new ImageBuilder(width, height, false);
            dataReader.readImageData(imageBuilder);
            result = imageBuilder.getBufferedImage();
        }
        return result;
    }
    
    private PhotometricInterpreter getPhotometricInterpreter(final TiffDirectory directory, final int photometricInterpretation, final int bitsPerPixel, final int[] bitsPerSample, final int predictor, final int samplesPerPixel, final int width, final int height) throws ImageReadException {
        switch (photometricInterpretation) {
            case 0:
            case 1: {
                final boolean invert = photometricInterpretation == 0;
                return new PhotometricInterpreterBiLevel(samplesPerPixel, bitsPerSample, predictor, width, height, invert);
            }
            case 3: {
                final int[] colorMap = directory.findField(TiffTagConstants.TIFF_TAG_COLOR_MAP, true).getIntArrayValue();
                final int expectedColormapSize = 3 * (1 << bitsPerPixel);
                if (colorMap.length != expectedColormapSize) {
                    throw new ImageReadException("Tiff: fColorMap.length (" + colorMap.length + ")!=expectedColormapSize (" + expectedColormapSize + ")");
                }
                return new PhotometricInterpreterPalette(samplesPerPixel, bitsPerSample, predictor, width, height, colorMap);
            }
            case 2: {
                return new PhotometricInterpreterRgb(samplesPerPixel, bitsPerSample, predictor, width, height);
            }
            case 5: {
                return new PhotometricInterpreterCmyk(samplesPerPixel, bitsPerSample, predictor, width, height);
            }
            case 6: {
                return new PhotometricInterpreterYCbCr(samplesPerPixel, bitsPerSample, predictor, width, height);
            }
            case 8: {
                return new PhotometricInterpreterCieLab(samplesPerPixel, bitsPerSample, predictor, width, height);
            }
            case 32844:
            case 32845: {
                return new PhotometricInterpreterLogLuv(samplesPerPixel, bitsPerSample, predictor, width, height);
            }
            default: {
                throw new ImageReadException("TIFF: Unknown fPhotometricInterpretation: " + photometricInterpretation);
            }
        }
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, final Map<String, Object> params) throws ImageWriteException, IOException {
        new TiffImageWriterLossy().writeImage(src, os, params);
    }
    
    static {
        ACCEPTED_EXTENSIONS = new String[] { ".tif", ".tiff" };
    }
}
