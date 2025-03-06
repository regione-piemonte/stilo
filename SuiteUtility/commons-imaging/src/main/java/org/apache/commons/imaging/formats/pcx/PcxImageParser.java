// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pcx;

import org.apache.commons.imaging.ImageWriteException;
import java.io.OutputStream;
import java.util.HashMap;
import java.awt.image.WritableRaster;
import java.awt.image.ComponentColorModel;
import java.awt.color.ColorSpace;
import java.util.Arrays;
import java.util.Hashtable;
import java.awt.image.ColorModel;
import java.util.Properties;
import java.awt.image.IndexColorModel;
import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.DataBufferByte;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import org.apache.commons.imaging.common.ByteConversions;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.InputStream;
import java.awt.Dimension;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageInfo;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageMetadata;
import java.util.Map;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import java.nio.ByteOrder;
import org.apache.commons.imaging.ImageParser;

public class PcxImageParser extends ImageParser
{
    private static final String DEFAULT_EXTENSION = ".pcx";
    private static final String[] ACCEPTED_EXTENSIONS;
    
    public PcxImageParser() {
        super.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    }
    
    @Override
    public String getName() {
        return "Pcx-Custom";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".pcx";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return PcxImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.PCX };
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final PcxHeader pcxHeader = this.readPcxHeader(byteSource);
        final Dimension size = this.getImageSize(byteSource, params);
        return new ImageInfo("PCX", pcxHeader.nPlanes * pcxHeader.bitsPerPixel, new ArrayList<String>(), ImageFormats.PCX, "ZSoft PCX Image", size.height, "image/x-pcx", 1, pcxHeader.vDpi, (float)Math.round(size.getHeight() / pcxHeader.vDpi), pcxHeader.hDpi, (float)Math.round(size.getWidth() / pcxHeader.hDpi), size.width, false, false, pcxHeader.nPlanes != 3 || pcxHeader.bitsPerPixel != 8, ImageInfo.ColorType.RGB, (pcxHeader.encoding == 1) ? ImageInfo.CompressionAlgorithm.RLE : ImageInfo.CompressionAlgorithm.NONE);
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final PcxHeader pcxHeader = this.readPcxHeader(byteSource);
        final int xSize = pcxHeader.xMax - pcxHeader.xMin + 1;
        if (xSize < 0) {
            throw new ImageReadException("Image width is negative");
        }
        final int ySize = pcxHeader.yMax - pcxHeader.yMin + 1;
        if (ySize < 0) {
            throw new ImageReadException("Image height is negative");
        }
        return new Dimension(xSize, ySize);
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private PcxHeader readPcxHeader(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            return this.readPcxHeader(is, false);
        }
    }
    
    private PcxHeader readPcxHeader(final InputStream is, final boolean isStrict) throws ImageReadException, IOException {
        final byte[] pcxHeaderBytes = BinaryFunctions.readBytes("PcxHeader", is, 128, "Not a Valid PCX File");
        final int manufacturer = 0xFF & pcxHeaderBytes[0];
        final int version = 0xFF & pcxHeaderBytes[1];
        final int encoding = 0xFF & pcxHeaderBytes[2];
        final int bitsPerPixel = 0xFF & pcxHeaderBytes[3];
        final int xMin = ByteConversions.toUInt16(pcxHeaderBytes, 4, this.getByteOrder());
        final int yMin = ByteConversions.toUInt16(pcxHeaderBytes, 6, this.getByteOrder());
        final int xMax = ByteConversions.toUInt16(pcxHeaderBytes, 8, this.getByteOrder());
        final int yMax = ByteConversions.toUInt16(pcxHeaderBytes, 10, this.getByteOrder());
        final int hDpi = ByteConversions.toUInt16(pcxHeaderBytes, 12, this.getByteOrder());
        final int vDpi = ByteConversions.toUInt16(pcxHeaderBytes, 14, this.getByteOrder());
        final int[] colormap = new int[16];
        for (int i = 0; i < 16; ++i) {
            colormap[i] = (0xFF000000 | (0xFF & pcxHeaderBytes[16 + 3 * i]) << 16 | (0xFF & pcxHeaderBytes[16 + 3 * i + 1]) << 8 | (0xFF & pcxHeaderBytes[16 + 3 * i + 2]));
        }
        final int reserved = 0xFF & pcxHeaderBytes[64];
        final int nPlanes = 0xFF & pcxHeaderBytes[65];
        final int bytesPerLine = ByteConversions.toUInt16(pcxHeaderBytes, 66, this.getByteOrder());
        final int paletteInfo = ByteConversions.toUInt16(pcxHeaderBytes, 68, this.getByteOrder());
        final int hScreenSize = ByteConversions.toUInt16(pcxHeaderBytes, 70, this.getByteOrder());
        final int vScreenSize = ByteConversions.toUInt16(pcxHeaderBytes, 72, this.getByteOrder());
        if (manufacturer != 10) {
            throw new ImageReadException("Not a Valid PCX File: manufacturer is " + manufacturer);
        }
        if (isStrict && bytesPerLine % 2 != 0) {
            throw new ImageReadException("Not a Valid PCX File: bytesPerLine is odd");
        }
        return new PcxHeader(manufacturer, version, encoding, bitsPerPixel, xMin, yMin, xMax, yMax, hDpi, vDpi, colormap, reserved, nPlanes, bytesPerLine, paletteInfo, hScreenSize, vScreenSize);
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        this.readPcxHeader(byteSource).dump(pw);
        return true;
    }
    
    private int[] read256ColorPalette(final InputStream stream) throws IOException {
        final byte[] paletteBytes = BinaryFunctions.readBytes("Palette", stream, 769, "Error reading palette");
        if (paletteBytes[0] != 12) {
            return null;
        }
        final int[] palette = new int[256];
        for (int i = 0; i < palette.length; ++i) {
            palette[i] = ((0xFF & paletteBytes[1 + 3 * i]) << 16 | (0xFF & paletteBytes[1 + 3 * i + 1]) << 8 | (0xFF & paletteBytes[1 + 3 * i + 2]));
        }
        return palette;
    }
    
    private int[] read256ColorPaletteFromEndOfFile(final ByteSource byteSource) throws IOException {
        try (final InputStream stream = byteSource.getInputStream()) {
            final long toSkip = byteSource.getLength() - 769L;
            BinaryFunctions.skipBytes(stream, (int)toSkip);
            final int[] ret = this.read256ColorPalette(stream);
            return ret;
        }
    }
    
    private BufferedImage readImage(final PcxHeader pcxHeader, final InputStream is, final ByteSource byteSource) throws ImageReadException, IOException {
        final int xSize = pcxHeader.xMax - pcxHeader.xMin + 1;
        if (xSize < 0) {
            throw new ImageReadException("Image width is negative");
        }
        final int ySize = pcxHeader.yMax - pcxHeader.yMin + 1;
        if (ySize < 0) {
            throw new ImageReadException("Image height is negative");
        }
        if (pcxHeader.nPlanes <= 0 || 4 < pcxHeader.nPlanes) {
            throw new ImageReadException("Unsupported/invalid image with " + pcxHeader.nPlanes + " planes");
        }
        RleReader rleReader;
        if (pcxHeader.encoding == 0) {
            rleReader = new RleReader(false);
        }
        else {
            if (pcxHeader.encoding != 1) {
                throw new ImageReadException("Unsupported/invalid image encoding " + pcxHeader.encoding);
            }
            rleReader = new RleReader(true);
        }
        final int scanlineLength = pcxHeader.bytesPerLine * pcxHeader.nPlanes;
        final byte[] scanline = new byte[scanlineLength];
        if ((pcxHeader.bitsPerPixel == 1 || pcxHeader.bitsPerPixel == 2 || pcxHeader.bitsPerPixel == 4 || pcxHeader.bitsPerPixel == 8) && pcxHeader.nPlanes == 1) {
            final int bytesPerImageRow = (xSize * pcxHeader.bitsPerPixel + 7) / 8;
            final byte[] image = new byte[ySize * bytesPerImageRow];
            for (int y = 0; y < ySize; ++y) {
                rleReader.read(is, scanline);
                System.arraycopy(scanline, 0, image, y * bytesPerImageRow, bytesPerImageRow);
            }
            final DataBufferByte dataBuffer = new DataBufferByte(image, image.length);
            int[] palette;
            if (pcxHeader.bitsPerPixel == 1) {
                palette = new int[] { 0, 16777215 };
            }
            else if (pcxHeader.bitsPerPixel == 8) {
                palette = this.read256ColorPalette(is);
                if (palette == null) {
                    palette = this.read256ColorPaletteFromEndOfFile(byteSource);
                }
                if (palette == null) {
                    throw new ImageReadException("No 256 color palette found in image that needs it");
                }
            }
            else {
                palette = pcxHeader.colormap;
            }
            WritableRaster raster;
            if (pcxHeader.bitsPerPixel == 8) {
                raster = Raster.createInterleavedRaster(dataBuffer, xSize, ySize, bytesPerImageRow, 1, new int[] { 0 }, null);
            }
            else {
                raster = Raster.createPackedRaster(dataBuffer, xSize, ySize, pcxHeader.bitsPerPixel, null);
            }
            final IndexColorModel colorModel = new IndexColorModel(pcxHeader.bitsPerPixel, 1 << pcxHeader.bitsPerPixel, palette, 0, false, -1, 0);
            return new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), new Properties());
        }
        if (pcxHeader.bitsPerPixel == 1 && 2 <= pcxHeader.nPlanes && pcxHeader.nPlanes <= 4) {
            final IndexColorModel colorModel2 = new IndexColorModel(pcxHeader.nPlanes, 1 << pcxHeader.nPlanes, pcxHeader.colormap, 0, false, -1, 0);
            final BufferedImage image2 = new BufferedImage(xSize, ySize, 12, colorModel2);
            final byte[] unpacked = new byte[xSize];
            for (int y2 = 0; y2 < ySize; ++y2) {
                rleReader.read(is, scanline);
                int nextByte = 0;
                Arrays.fill(unpacked, (byte)0);
                for (int plane = 0; plane < pcxHeader.nPlanes; ++plane) {
                    for (int i = 0; i < pcxHeader.bytesPerLine; ++i) {
                        final int b = 0xFF & scanline[nextByte++];
                        for (int j = 0; j < 8 && 8 * i + j < unpacked.length; ++j) {
                            final byte[] array = unpacked;
                            final int n = 8 * i + j;
                            array[n] |= (byte)((b >> 7 - j & 0x1) << plane);
                        }
                    }
                }
                image2.getRaster().setDataElements(0, y2, xSize, 1, unpacked);
            }
            return image2;
        }
        if (pcxHeader.bitsPerPixel == 8 && pcxHeader.nPlanes == 3) {
            final byte[][] image3 = { new byte[xSize * ySize], new byte[xSize * ySize], new byte[xSize * ySize] };
            for (int y3 = 0; y3 < ySize; ++y3) {
                rleReader.read(is, scanline);
                System.arraycopy(scanline, 0, image3[0], y3 * xSize, xSize);
                System.arraycopy(scanline, pcxHeader.bytesPerLine, image3[1], y3 * xSize, xSize);
                System.arraycopy(scanline, 2 * pcxHeader.bytesPerLine, image3[2], y3 * xSize, xSize);
            }
            final DataBufferByte dataBuffer2 = new DataBufferByte(image3, image3[0].length);
            final WritableRaster raster2 = Raster.createBandedRaster(dataBuffer2, xSize, ySize, xSize, new int[] { 0, 1, 2 }, new int[] { 0, 0, 0 }, null);
            final ColorModel colorModel3 = new ComponentColorModel(ColorSpace.getInstance(1000), false, false, 1, 0);
            return new BufferedImage(colorModel3, raster2, colorModel3.isAlphaPremultiplied(), new Properties());
        }
        if ((pcxHeader.bitsPerPixel == 24 && pcxHeader.nPlanes == 1) || (pcxHeader.bitsPerPixel == 32 && pcxHeader.nPlanes == 1)) {
            final int rowLength = 3 * xSize;
            final byte[] image = new byte[rowLength * ySize];
            for (int y = 0; y < ySize; ++y) {
                rleReader.read(is, scanline);
                if (pcxHeader.bitsPerPixel == 24) {
                    System.arraycopy(scanline, 0, image, y * rowLength, rowLength);
                }
                else {
                    for (int x = 0; x < xSize; ++x) {
                        image[y * rowLength + 3 * x] = scanline[4 * x];
                        image[y * rowLength + 3 * x + 1] = scanline[4 * x + 1];
                        image[y * rowLength + 3 * x + 2] = scanline[4 * x + 2];
                    }
                }
            }
            final DataBufferByte dataBuffer = new DataBufferByte(image, image.length);
            final WritableRaster raster3 = Raster.createInterleavedRaster(dataBuffer, xSize, ySize, rowLength, 3, new int[] { 2, 1, 0 }, null);
            final ColorModel colorModel4 = new ComponentColorModel(ColorSpace.getInstance(1000), false, false, 1, 0);
            return new BufferedImage(colorModel4, raster3, colorModel4.isAlphaPremultiplied(), new Properties());
        }
        throw new ImageReadException("Invalid/unsupported image with bitsPerPixel " + pcxHeader.bitsPerPixel + " and planes " + pcxHeader.nPlanes);
    }
    
    @Override
    public final BufferedImage getBufferedImage(final ByteSource byteSource, Map<String, Object> params) throws ImageReadException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        boolean isStrict = false;
        final Object strictness = params.get("STRICT");
        if (strictness != null) {
            isStrict = (boolean)strictness;
        }
        try (final InputStream is = byteSource.getInputStream()) {
            final PcxHeader pcxHeader = this.readPcxHeader(is, isStrict);
            final BufferedImage ret = this.readImage(pcxHeader, is, byteSource);
            return ret;
        }
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, final Map<String, Object> params) throws ImageWriteException, IOException {
        new PcxWriter(params).writeImage(src, os);
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    static {
        ACCEPTED_EXTENSIONS = new String[] { ".pcx", ".pcc" };
    }
    
    static class PcxHeader
    {
        public static final int ENCODING_UNCOMPRESSED = 0;
        public static final int ENCODING_RLE = 1;
        public static final int PALETTE_INFO_COLOR = 1;
        public static final int PALETTE_INFO_GRAYSCALE = 2;
        public final int manufacturer;
        public final int version;
        public final int encoding;
        public final int bitsPerPixel;
        public final int xMin;
        public final int yMin;
        public final int xMax;
        public final int yMax;
        public final int hDpi;
        public final int vDpi;
        public final int[] colormap;
        public final int reserved;
        public final int nPlanes;
        public final int bytesPerLine;
        public final int paletteInfo;
        public final int hScreenSize;
        public final int vScreenSize;
        
        PcxHeader(final int manufacturer, final int version, final int encoding, final int bitsPerPixel, final int xMin, final int yMin, final int xMax, final int yMax, final int hDpi, final int vDpi, final int[] colormap, final int reserved, final int nPlanes, final int bytesPerLine, final int paletteInfo, final int hScreenSize, final int vScreenSize) {
            this.manufacturer = manufacturer;
            this.version = version;
            this.encoding = encoding;
            this.bitsPerPixel = bitsPerPixel;
            this.xMin = xMin;
            this.yMin = yMin;
            this.xMax = xMax;
            this.yMax = yMax;
            this.hDpi = hDpi;
            this.vDpi = vDpi;
            this.colormap = colormap;
            this.reserved = reserved;
            this.nPlanes = nPlanes;
            this.bytesPerLine = bytesPerLine;
            this.paletteInfo = paletteInfo;
            this.hScreenSize = hScreenSize;
            this.vScreenSize = vScreenSize;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("PcxHeader");
            pw.println("Manufacturer: " + this.manufacturer);
            pw.println("Version: " + this.version);
            pw.println("Encoding: " + this.encoding);
            pw.println("BitsPerPixel: " + this.bitsPerPixel);
            pw.println("xMin: " + this.xMin);
            pw.println("yMin: " + this.yMin);
            pw.println("xMax: " + this.xMax);
            pw.println("yMax: " + this.yMax);
            pw.println("hDpi: " + this.hDpi);
            pw.println("vDpi: " + this.vDpi);
            pw.print("ColorMap: ");
            for (int i = 0; i < this.colormap.length; ++i) {
                if (i > 0) {
                    pw.print(",");
                }
                pw.print("(" + (0xFF & this.colormap[i] >> 16) + "," + (0xFF & this.colormap[i] >> 8) + "," + (0xFF & this.colormap[i]) + ")");
            }
            pw.println();
            pw.println("Reserved: " + this.reserved);
            pw.println("nPlanes: " + this.nPlanes);
            pw.println("BytesPerLine: " + this.bytesPerLine);
            pw.println("PaletteInfo: " + this.paletteInfo);
            pw.println("hScreenSize: " + this.hScreenSize);
            pw.println("vScreenSize: " + this.vScreenSize);
            pw.println();
        }
    }
}
