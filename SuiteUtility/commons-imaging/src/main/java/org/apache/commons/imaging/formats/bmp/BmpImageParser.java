// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.bmp;

import org.apache.commons.imaging.palette.SimplePalette;
import org.apache.commons.imaging.common.BinaryOutputStream;
import org.apache.commons.imaging.palette.PaletteFactory;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.PixelDensity;
import java.io.OutputStream;
import org.apache.commons.imaging.common.ImageBuilder;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.common.ImageMetadata;
import java.util.HashMap;
import java.awt.Dimension;
import java.util.Map;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.util.logging.Level;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.FormatCompliance;
import java.io.InputStream;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import java.nio.ByteOrder;
import java.util.logging.Logger;
import org.apache.commons.imaging.ImageParser;

public class BmpImageParser extends ImageParser
{
    private static final Logger LOGGER;
    private static final String DEFAULT_EXTENSION = ".bmp";
    private static final String[] ACCEPTED_EXTENSIONS;
    private static final byte[] BMP_HEADER_SIGNATURE;
    private static final int BI_RGB = 0;
    private static final int BI_RLE4 = 2;
    private static final int BI_RLE8 = 1;
    private static final int BI_BITFIELDS = 3;
    private static final int BITMAP_FILE_HEADER_SIZE = 14;
    private static final int BITMAP_INFO_HEADER_SIZE = 40;
    
    public BmpImageParser() {
        super.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    }
    
    @Override
    public String getName() {
        return "Bmp-Custom";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".bmp";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return BmpImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.BMP };
    }
    
    private BmpHeaderInfo readBmpHeaderInfo(final InputStream is, final FormatCompliance formatCompliance) throws ImageReadException, IOException {
        final byte identifier1 = BinaryFunctions.readByte("Identifier1", is, "Not a Valid BMP File");
        final byte identifier2 = BinaryFunctions.readByte("Identifier2", is, "Not a Valid BMP File");
        if (formatCompliance != null) {
            formatCompliance.compareBytes("Signature", BmpImageParser.BMP_HEADER_SIGNATURE, new byte[] { identifier1, identifier2 });
        }
        final int fileSize = BinaryFunctions.read4Bytes("File Size", is, "Not a Valid BMP File", this.getByteOrder());
        final int reserved = BinaryFunctions.read4Bytes("Reserved", is, "Not a Valid BMP File", this.getByteOrder());
        final int bitmapDataOffset = BinaryFunctions.read4Bytes("Bitmap Data Offset", is, "Not a Valid BMP File", this.getByteOrder());
        final int bitmapHeaderSize = BinaryFunctions.read4Bytes("Bitmap Header Size", is, "Not a Valid BMP File", this.getByteOrder());
        int width = 0;
        int height = 0;
        int planes = 0;
        int bitsPerPixel = 0;
        int compression = 0;
        int bitmapDataSize = 0;
        int hResolution = 0;
        int vResolution = 0;
        int colorsUsed = 0;
        int colorsImportant = 0;
        int redMask = 0;
        int greenMask = 0;
        int blueMask = 0;
        int alphaMask = 0;
        int colorSpaceType = 0;
        final BmpHeaderInfo.ColorSpace colorSpace = new BmpHeaderInfo.ColorSpace();
        colorSpace.red = new BmpHeaderInfo.ColorSpaceCoordinate();
        colorSpace.green = new BmpHeaderInfo.ColorSpaceCoordinate();
        colorSpace.blue = new BmpHeaderInfo.ColorSpaceCoordinate();
        int gammaRed = 0;
        int gammaGreen = 0;
        int gammaBlue = 0;
        int intent = 0;
        int profileData = 0;
        int profileSize = 0;
        int reservedV5 = 0;
        if (bitmapHeaderSize >= 40) {
            width = BinaryFunctions.read4Bytes("Width", is, "Not a Valid BMP File", this.getByteOrder());
            height = BinaryFunctions.read4Bytes("Height", is, "Not a Valid BMP File", this.getByteOrder());
            planes = BinaryFunctions.read2Bytes("Planes", is, "Not a Valid BMP File", this.getByteOrder());
            bitsPerPixel = BinaryFunctions.read2Bytes("Bits Per Pixel", is, "Not a Valid BMP File", this.getByteOrder());
            compression = BinaryFunctions.read4Bytes("Compression", is, "Not a Valid BMP File", this.getByteOrder());
            bitmapDataSize = BinaryFunctions.read4Bytes("Bitmap Data Size", is, "Not a Valid BMP File", this.getByteOrder());
            hResolution = BinaryFunctions.read4Bytes("HResolution", is, "Not a Valid BMP File", this.getByteOrder());
            vResolution = BinaryFunctions.read4Bytes("VResolution", is, "Not a Valid BMP File", this.getByteOrder());
            colorsUsed = BinaryFunctions.read4Bytes("ColorsUsed", is, "Not a Valid BMP File", this.getByteOrder());
            colorsImportant = BinaryFunctions.read4Bytes("ColorsImportant", is, "Not a Valid BMP File", this.getByteOrder());
            if (bitmapHeaderSize >= 52 || compression == 3) {
                redMask = BinaryFunctions.read4Bytes("RedMask", is, "Not a Valid BMP File", this.getByteOrder());
                greenMask = BinaryFunctions.read4Bytes("GreenMask", is, "Not a Valid BMP File", this.getByteOrder());
                blueMask = BinaryFunctions.read4Bytes("BlueMask", is, "Not a Valid BMP File", this.getByteOrder());
            }
            if (bitmapHeaderSize >= 56) {
                alphaMask = BinaryFunctions.read4Bytes("AlphaMask", is, "Not a Valid BMP File", this.getByteOrder());
            }
            if (bitmapHeaderSize >= 108) {
                colorSpaceType = BinaryFunctions.read4Bytes("ColorSpaceType", is, "Not a Valid BMP File", this.getByteOrder());
                colorSpace.red.x = BinaryFunctions.read4Bytes("ColorSpaceRedX", is, "Not a Valid BMP File", this.getByteOrder());
                colorSpace.red.y = BinaryFunctions.read4Bytes("ColorSpaceRedY", is, "Not a Valid BMP File", this.getByteOrder());
                colorSpace.red.z = BinaryFunctions.read4Bytes("ColorSpaceRedZ", is, "Not a Valid BMP File", this.getByteOrder());
                colorSpace.green.x = BinaryFunctions.read4Bytes("ColorSpaceGreenX", is, "Not a Valid BMP File", this.getByteOrder());
                colorSpace.green.y = BinaryFunctions.read4Bytes("ColorSpaceGreenY", is, "Not a Valid BMP File", this.getByteOrder());
                colorSpace.green.z = BinaryFunctions.read4Bytes("ColorSpaceGreenZ", is, "Not a Valid BMP File", this.getByteOrder());
                colorSpace.blue.x = BinaryFunctions.read4Bytes("ColorSpaceBlueX", is, "Not a Valid BMP File", this.getByteOrder());
                colorSpace.blue.y = BinaryFunctions.read4Bytes("ColorSpaceBlueY", is, "Not a Valid BMP File", this.getByteOrder());
                colorSpace.blue.z = BinaryFunctions.read4Bytes("ColorSpaceBlueZ", is, "Not a Valid BMP File", this.getByteOrder());
                gammaRed = BinaryFunctions.read4Bytes("GammaRed", is, "Not a Valid BMP File", this.getByteOrder());
                gammaGreen = BinaryFunctions.read4Bytes("GammaGreen", is, "Not a Valid BMP File", this.getByteOrder());
                gammaBlue = BinaryFunctions.read4Bytes("GammaBlue", is, "Not a Valid BMP File", this.getByteOrder());
            }
            if (bitmapHeaderSize >= 124) {
                intent = BinaryFunctions.read4Bytes("Intent", is, "Not a Valid BMP File", this.getByteOrder());
                profileData = BinaryFunctions.read4Bytes("ProfileData", is, "Not a Valid BMP File", this.getByteOrder());
                profileSize = BinaryFunctions.read4Bytes("ProfileSize", is, "Not a Valid BMP File", this.getByteOrder());
                reservedV5 = BinaryFunctions.read4Bytes("Reserved", is, "Not a Valid BMP File", this.getByteOrder());
            }
            if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
                this.debugNumber("identifier1", identifier1, 1);
                this.debugNumber("identifier2", identifier2, 1);
                this.debugNumber("fileSize", fileSize, 4);
                this.debugNumber("reserved", reserved, 4);
                this.debugNumber("bitmapDataOffset", bitmapDataOffset, 4);
                this.debugNumber("bitmapHeaderSize", bitmapHeaderSize, 4);
                this.debugNumber("width", width, 4);
                this.debugNumber("height", height, 4);
                this.debugNumber("planes", planes, 2);
                this.debugNumber("bitsPerPixel", bitsPerPixel, 2);
                this.debugNumber("compression", compression, 4);
                this.debugNumber("bitmapDataSize", bitmapDataSize, 4);
                this.debugNumber("hResolution", hResolution, 4);
                this.debugNumber("vResolution", vResolution, 4);
                this.debugNumber("colorsUsed", colorsUsed, 4);
                this.debugNumber("colorsImportant", colorsImportant, 4);
                if (bitmapHeaderSize >= 52 || compression == 3) {
                    this.debugNumber("redMask", redMask, 4);
                    this.debugNumber("greenMask", greenMask, 4);
                    this.debugNumber("blueMask", blueMask, 4);
                }
                if (bitmapHeaderSize >= 56) {
                    this.debugNumber("alphaMask", alphaMask, 4);
                }
                if (bitmapHeaderSize >= 108) {
                    this.debugNumber("colorSpaceType", colorSpaceType, 4);
                    this.debugNumber("colorSpace.red.x", colorSpace.red.x, 1);
                    this.debugNumber("colorSpace.red.y", colorSpace.red.y, 1);
                    this.debugNumber("colorSpace.red.z", colorSpace.red.z, 1);
                    this.debugNumber("colorSpace.green.x", colorSpace.green.x, 1);
                    this.debugNumber("colorSpace.green.y", colorSpace.green.y, 1);
                    this.debugNumber("colorSpace.green.z", colorSpace.green.z, 1);
                    this.debugNumber("colorSpace.blue.x", colorSpace.blue.x, 1);
                    this.debugNumber("colorSpace.blue.y", colorSpace.blue.y, 1);
                    this.debugNumber("colorSpace.blue.z", colorSpace.blue.z, 1);
                    this.debugNumber("gammaRed", gammaRed, 4);
                    this.debugNumber("gammaGreen", gammaGreen, 4);
                    this.debugNumber("gammaBlue", gammaBlue, 4);
                }
                if (bitmapHeaderSize >= 124) {
                    this.debugNumber("intent", intent, 4);
                    this.debugNumber("profileData", profileData, 4);
                    this.debugNumber("profileSize", profileSize, 4);
                    this.debugNumber("reservedV5", reservedV5, 4);
                }
            }
            return new BmpHeaderInfo(identifier1, identifier2, fileSize, reserved, bitmapDataOffset, bitmapHeaderSize, width, height, planes, bitsPerPixel, compression, bitmapDataSize, hResolution, vResolution, colorsUsed, colorsImportant, redMask, greenMask, blueMask, alphaMask, colorSpaceType, colorSpace, gammaRed, gammaGreen, gammaBlue, intent, profileData, profileSize, reservedV5);
        }
        throw new ImageReadException("Invalid/unsupported BMP file");
    }
    
    private byte[] getRLEBytes(final InputStream is, final int rleSamplesPerByte) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean done = false;
        while (!done) {
            final int a = 0xFF & BinaryFunctions.readByte("RLE a", is, "BMP: Bad RLE");
            baos.write(a);
            final int b = 0xFF & BinaryFunctions.readByte("RLE b", is, "BMP: Bad RLE");
            baos.write(b);
            if (a == 0) {
                switch (b) {
                    case 0: {
                        continue;
                    }
                    case 1: {
                        done = true;
                        continue;
                    }
                    case 2: {
                        final int c = 0xFF & BinaryFunctions.readByte("RLE c", is, "BMP: Bad RLE");
                        baos.write(c);
                        final int d = 0xFF & BinaryFunctions.readByte("RLE d", is, "BMP: Bad RLE");
                        baos.write(d);
                        continue;
                    }
                    default: {
                        int size = b / rleSamplesPerByte;
                        if (b % rleSamplesPerByte > 0) {
                            ++size;
                        }
                        if (size % 2 != 0) {
                            ++size;
                        }
                        final byte[] bytes = BinaryFunctions.readBytes("bytes", is, size, "RLE: Absolute Mode");
                        baos.write(bytes);
                        continue;
                    }
                }
            }
        }
        return baos.toByteArray();
    }
    
    private BmpImageContents readImageContents(final InputStream is, final FormatCompliance formatCompliance) throws ImageReadException, IOException {
        final BmpHeaderInfo bhi = this.readBmpHeaderInfo(is, formatCompliance);
        int colorTableSize = bhi.colorsUsed;
        if (colorTableSize == 0) {
            colorTableSize = 1 << bhi.bitsPerPixel;
        }
        if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
            this.debugNumber("ColorsUsed", bhi.colorsUsed, 4);
            this.debugNumber("BitsPerPixel", bhi.bitsPerPixel, 4);
            this.debugNumber("ColorTableSize", colorTableSize, 4);
            this.debugNumber("bhi.colorsUsed", bhi.colorsUsed, 4);
            this.debugNumber("Compression", bhi.compression, 4);
        }
        int rleSamplesPerByte = 0;
        boolean rle = false;
        int paletteLength = 0;
        switch (bhi.compression) {
            case 0: {
                if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
                    BmpImageParser.LOGGER.fine("Compression: BI_RGB");
                }
                if (bhi.bitsPerPixel <= 8) {
                    paletteLength = 4 * colorTableSize;
                    break;
                }
                paletteLength = 0;
                break;
            }
            case 2: {
                if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
                    BmpImageParser.LOGGER.fine("Compression: BI_RLE4");
                }
                paletteLength = 4 * colorTableSize;
                rleSamplesPerByte = 2;
                rle = true;
                break;
            }
            case 1: {
                if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
                    BmpImageParser.LOGGER.fine("Compression: BI_RLE8");
                }
                paletteLength = 4 * colorTableSize;
                rleSamplesPerByte = 1;
                rle = true;
                break;
            }
            case 3: {
                if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
                    BmpImageParser.LOGGER.fine("Compression: BI_BITFIELDS");
                }
                if (bhi.bitsPerPixel <= 8) {
                    paletteLength = 4 * colorTableSize;
                    break;
                }
                paletteLength = 0;
                break;
            }
            default: {
                throw new ImageReadException("BMP: Unknown Compression: " + bhi.compression);
            }
        }
        byte[] colorTable = null;
        if (paletteLength > 0) {
            colorTable = BinaryFunctions.readBytes("ColorTable", is, paletteLength, "Not a Valid BMP File");
        }
        if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
            this.debugNumber("paletteLength", paletteLength, 4);
            BmpImageParser.LOGGER.fine("ColorTable: " + ((colorTable == null) ? "null" : Integer.toString(colorTable.length)));
        }
        int imageLineLength = (bhi.bitsPerPixel * bhi.width + 7) / 8;
        if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
            final int pixelCount = bhi.width * bhi.height;
            this.debugNumber("bhi.Width", bhi.width, 4);
            this.debugNumber("bhi.Height", bhi.height, 4);
            this.debugNumber("ImageLineLength", imageLineLength, 4);
            this.debugNumber("PixelCount", pixelCount, 4);
        }
        while (imageLineLength % 4 != 0) {
            ++imageLineLength;
        }
        final int headerSize = 14 + bhi.bitmapHeaderSize + ((bhi.bitmapHeaderSize == 40 && bhi.compression == 3) ? 12 : 0);
        final int expectedDataOffset = headerSize + paletteLength;
        if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
            this.debugNumber("bhi.BitmapDataOffset", bhi.bitmapDataOffset, 4);
            this.debugNumber("expectedDataOffset", expectedDataOffset, 4);
        }
        final int extraBytes = bhi.bitmapDataOffset - expectedDataOffset;
        if (extraBytes < 0) {
            throw new ImageReadException("BMP has invalid image data offset: " + bhi.bitmapDataOffset + " (expected: " + expectedDataOffset + ", paletteLength: " + paletteLength + ", headerSize: " + headerSize + ")");
        }
        if (extraBytes > 0) {
            BinaryFunctions.readBytes("BitmapDataOffset", is, extraBytes, "Not a Valid BMP File");
        }
        final int imageDataSize = bhi.height * imageLineLength;
        if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
            this.debugNumber("imageDataSize", imageDataSize, 4);
        }
        byte[] imageData;
        if (rle) {
            imageData = this.getRLEBytes(is, rleSamplesPerByte);
        }
        else {
            imageData = BinaryFunctions.readBytes("ImageData", is, imageDataSize, "Not a Valid BMP File");
        }
        if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
            this.debugNumber("ImageData.length", imageData.length, 4);
        }
        PixelParser pixelParser = null;
        switch (bhi.compression) {
            case 1:
            case 2: {
                pixelParser = new PixelParserRle(bhi, colorTable, imageData);
                break;
            }
            case 0: {
                pixelParser = new PixelParserRgb(bhi, colorTable, imageData);
                break;
            }
            case 3: {
                pixelParser = new PixelParserBitFields(bhi, colorTable, imageData);
                break;
            }
            default: {
                throw new ImageReadException("BMP: Unknown Compression: " + bhi.compression);
            }
        }
        return new BmpImageContents(bhi, colorTable, imageData, pixelParser);
    }
    
    private BmpHeaderInfo readBmpHeaderInfo(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final BmpHeaderInfo ret = this.readBmpHeaderInfo(is, null);
            return ret;
        }
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, Map<String, Object> params) throws ImageReadException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageReadException("Unknown parameter: " + firstKey);
        }
        final BmpHeaderInfo bhi = this.readBmpHeaderInfo(byteSource);
        if (bhi == null) {
            throw new ImageReadException("BMP: couldn't read header");
        }
        return new Dimension(bhi.width, bhi.height);
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private String getBmpTypeDescription(final int identifier1, final int identifier2) {
        if (identifier1 == 66 && identifier2 == 77) {
            return "Windows 3.1x, 95, NT,";
        }
        if (identifier1 == 66 && identifier2 == 65) {
            return "OS/2 Bitmap Array";
        }
        if (identifier1 == 67 && identifier2 == 73) {
            return "OS/2 Color Icon";
        }
        if (identifier1 == 67 && identifier2 == 80) {
            return "OS/2 Color Pointer";
        }
        if (identifier1 == 73 && identifier2 == 67) {
            return "OS/2 Icon";
        }
        if (identifier1 == 80 && identifier2 == 84) {
            return "OS/2 Pointer";
        }
        return "Unknown";
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, Map<String, Object> params) throws ImageReadException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageReadException("Unknown parameter: " + firstKey);
        }
        BmpImageContents ic = null;
        try (final InputStream is = byteSource.getInputStream()) {
            ic = this.readImageContents(is, FormatCompliance.getDefault());
        }
        if (ic == null) {
            throw new ImageReadException("Couldn't read BMP Data");
        }
        final BmpHeaderInfo bhi = ic.bhi;
        final byte[] colorTable = ic.colorTable;
        if (bhi == null) {
            throw new ImageReadException("BMP: couldn't read header");
        }
        final int height = bhi.height;
        final int width = bhi.width;
        final List<String> comments = new ArrayList<String>();
        final int bitsPerPixel = bhi.bitsPerPixel;
        final ImageFormat format = ImageFormats.BMP;
        final String name = "BMP Windows Bitmap";
        final String mimeType = "image/x-ms-bmp";
        final int numberOfImages = -1;
        final boolean progressive = false;
        final int physicalWidthDpi = (int)(bhi.hResolution * 0.0254);
        final float physicalWidthInch = (float)(width / (double)physicalWidthDpi);
        final int physicalHeightDpi = (int)(bhi.vResolution * 0.0254);
        final float physicalHeightInch = (float)(height / (double)physicalHeightDpi);
        final String formatDetails = "Bmp (" + (char)bhi.identifier1 + (char)bhi.identifier2 + ": " + this.getBmpTypeDescription(bhi.identifier1, bhi.identifier2) + ")";
        final boolean transparent = false;
        final boolean usesPalette = colorTable != null;
        final ImageInfo.ColorType colorType = ImageInfo.ColorType.RGB;
        final ImageInfo.CompressionAlgorithm compressionAlgorithm = ImageInfo.CompressionAlgorithm.RLE;
        return new ImageInfo(formatDetails, bitsPerPixel, comments, format, "BMP Windows Bitmap", height, "image/x-ms-bmp", -1, physicalHeightDpi, physicalHeightInch, physicalWidthDpi, physicalWidthInch, width, false, false, usesPalette, colorType, compressionAlgorithm);
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        pw.println("bmp.dumpImageFile");
        final ImageInfo imageData = this.getImageInfo(byteSource, null);
        imageData.toString(pw, "");
        pw.println("");
        return true;
    }
    
    @Override
    public FormatCompliance getFormatCompliance(final ByteSource byteSource) throws ImageReadException, IOException {
        final FormatCompliance result = new FormatCompliance(byteSource.getDescription());
        try (final InputStream is = byteSource.getInputStream()) {
            this.readImageContents(is, result);
        }
        return result;
    }
    
    @Override
    public BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final BufferedImage ret = this.getBufferedImage(is, params);
            return ret;
        }
    }
    
    public BufferedImage getBufferedImage(final InputStream inputStream, Map<String, Object> params) throws ImageReadException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        if (params.containsKey("BUFFERED_IMAGE_FACTORY")) {
            params.remove("BUFFERED_IMAGE_FACTORY");
        }
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageReadException("Unknown parameter: " + firstKey);
        }
        final BmpImageContents ic = this.readImageContents(inputStream, FormatCompliance.getDefault());
        if (ic == null) {
            throw new ImageReadException("Couldn't read BMP Data");
        }
        final BmpHeaderInfo bhi = ic.bhi;
        final int width = bhi.width;
        final int height = bhi.height;
        if (BmpImageParser.LOGGER.isLoggable(Level.FINE)) {
            BmpImageParser.LOGGER.fine("width: " + width);
            BmpImageParser.LOGGER.fine("height: " + height);
            BmpImageParser.LOGGER.fine("width*height: " + width * height);
            BmpImageParser.LOGGER.fine("width*height*4: " + width * height * 4);
        }
        final PixelParser pixelParser = ic.pixelParser;
        final ImageBuilder imageBuilder = new ImageBuilder(width, height, true);
        pixelParser.processImage(imageBuilder);
        return imageBuilder.getBufferedImage();
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, Map<String, Object> params) throws ImageWriteException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        PixelDensity pixelDensity = null;
        if (params.containsKey("FORMAT")) {
            params.remove("FORMAT");
        }
        if (params.containsKey("PIXEL_DENSITY")) {
            pixelDensity = params.remove("PIXEL_DENSITY");
        }
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageWriteException("Unknown parameter: " + firstKey);
        }
        final SimplePalette palette = new PaletteFactory().makeExactRgbPaletteSimple(src, 256);
        BmpWriter writer;
        if (palette == null) {
            writer = new BmpWriterRgb();
        }
        else {
            writer = new BmpWriterPalette(palette);
        }
        final byte[] imagedata = writer.getImageData(src);
        final BinaryOutputStream bos = new BinaryOutputStream(os, ByteOrder.LITTLE_ENDIAN);
        os.write(66);
        os.write(77);
        final int filesize = 54 + 4 * writer.getPaletteSize() + imagedata.length;
        bos.write4Bytes(filesize);
        bos.write4Bytes(0);
        bos.write4Bytes(54 + 4 * writer.getPaletteSize());
        final int width = src.getWidth();
        final int height = src.getHeight();
        bos.write4Bytes(40);
        bos.write4Bytes(width);
        bos.write4Bytes(height);
        bos.write2Bytes(1);
        bos.write2Bytes(writer.getBitsPerPixel());
        bos.write4Bytes(0);
        bos.write4Bytes(imagedata.length);
        bos.write4Bytes((pixelDensity != null) ? ((int)Math.round(pixelDensity.horizontalDensityMetres())) : 0);
        bos.write4Bytes((pixelDensity != null) ? ((int)Math.round(pixelDensity.verticalDensityMetres())) : 0);
        if (palette == null) {
            bos.write4Bytes(0);
        }
        else {
            bos.write4Bytes(palette.length());
        }
        bos.write4Bytes(0);
        writer.writePalette(bos);
        bos.write(imagedata);
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    static {
        LOGGER = Logger.getLogger(BmpImageParser.class.getName());
        ACCEPTED_EXTENSIONS = new String[] { ".bmp" };
        BMP_HEADER_SIGNATURE = new byte[] { 66, 77 };
    }
}
