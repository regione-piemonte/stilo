// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.ico;

import org.apache.commons.imaging.palette.SimplePalette;
import org.apache.commons.imaging.palette.PaletteFactory;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.PixelDensity;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import org.apache.commons.imaging.Imaging;
import java.awt.image.BufferedImage;
import org.apache.commons.imaging.formats.bmp.BmpImageParser;
import java.io.OutputStream;
import org.apache.commons.imaging.common.BinaryOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import org.apache.commons.imaging.common.BinaryFunctions;
import java.io.InputStream;
import java.awt.Dimension;
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

public class IcoImageParser extends ImageParser
{
    private static final String DEFAULT_EXTENSION = ".ico";
    private static final String[] ACCEPTED_EXTENSIONS;
    
    public IcoImageParser() {
        super.setByteOrder(ByteOrder.LITTLE_ENDIAN);
    }
    
    @Override
    public String getName() {
        return "ico-Custom";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".ico";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return IcoImageParser.ACCEPTED_EXTENSIONS;
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.ICO };
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    private FileHeader readFileHeader(final InputStream is) throws ImageReadException, IOException {
        final int reserved = BinaryFunctions.read2Bytes("Reserved", is, "Not a Valid ICO File", this.getByteOrder());
        final int iconType = BinaryFunctions.read2Bytes("IconType", is, "Not a Valid ICO File", this.getByteOrder());
        final int iconCount = BinaryFunctions.read2Bytes("IconCount", is, "Not a Valid ICO File", this.getByteOrder());
        if (reserved != 0) {
            throw new ImageReadException("Not a Valid ICO File: reserved is " + reserved);
        }
        if (iconType != 1 && iconType != 2) {
            throw new ImageReadException("Not a Valid ICO File: icon type is " + iconType);
        }
        return new FileHeader(reserved, iconType, iconCount);
    }
    
    private IconInfo readIconInfo(final InputStream is) throws IOException {
        final byte width = BinaryFunctions.readByte("Width", is, "Not a Valid ICO File");
        final byte height = BinaryFunctions.readByte("Height", is, "Not a Valid ICO File");
        final byte colorCount = BinaryFunctions.readByte("ColorCount", is, "Not a Valid ICO File");
        final byte reserved = BinaryFunctions.readByte("Reserved", is, "Not a Valid ICO File");
        final int planes = BinaryFunctions.read2Bytes("Planes", is, "Not a Valid ICO File", this.getByteOrder());
        final int bitCount = BinaryFunctions.read2Bytes("BitCount", is, "Not a Valid ICO File", this.getByteOrder());
        final int imageSize = BinaryFunctions.read4Bytes("ImageSize", is, "Not a Valid ICO File", this.getByteOrder());
        final int imageOffset = BinaryFunctions.read4Bytes("ImageOffset", is, "Not a Valid ICO File", this.getByteOrder());
        return new IconInfo(width, height, colorCount, reserved, planes, bitCount, imageSize, imageOffset);
    }
    
    private IconData readBitmapIconData(final byte[] iconData, final IconInfo fIconInfo) throws ImageReadException, IOException {
        final ByteArrayInputStream is = new ByteArrayInputStream(iconData);
        final int size = BinaryFunctions.read4Bytes("size", is, "Not a Valid ICO File", this.getByteOrder());
        final int width = BinaryFunctions.read4Bytes("width", is, "Not a Valid ICO File", this.getByteOrder());
        final int height = BinaryFunctions.read4Bytes("height", is, "Not a Valid ICO File", this.getByteOrder());
        final int planes = BinaryFunctions.read2Bytes("planes", is, "Not a Valid ICO File", this.getByteOrder());
        final int bitCount = BinaryFunctions.read2Bytes("bitCount", is, "Not a Valid ICO File", this.getByteOrder());
        int compression = BinaryFunctions.read4Bytes("compression", is, "Not a Valid ICO File", this.getByteOrder());
        final int sizeImage = BinaryFunctions.read4Bytes("sizeImage", is, "Not a Valid ICO File", this.getByteOrder());
        final int xPelsPerMeter = BinaryFunctions.read4Bytes("xPelsPerMeter", is, "Not a Valid ICO File", this.getByteOrder());
        final int yPelsPerMeter = BinaryFunctions.read4Bytes("yPelsPerMeter", is, "Not a Valid ICO File", this.getByteOrder());
        final int colorsUsed = BinaryFunctions.read4Bytes("colorsUsed", is, "Not a Valid ICO File", this.getByteOrder());
        final int colorsImportant = BinaryFunctions.read4Bytes("ColorsImportant", is, "Not a Valid ICO File", this.getByteOrder());
        int redMask = 0;
        int greenMask = 0;
        int blueMask = 0;
        int alphaMask = 0;
        if (compression == 3) {
            redMask = BinaryFunctions.read4Bytes("redMask", is, "Not a Valid ICO File", this.getByteOrder());
            greenMask = BinaryFunctions.read4Bytes("greenMask", is, "Not a Valid ICO File", this.getByteOrder());
            blueMask = BinaryFunctions.read4Bytes("blueMask", is, "Not a Valid ICO File", this.getByteOrder());
        }
        final byte[] restOfFile = BinaryFunctions.readBytes("RestOfFile", is, is.available());
        if (size != 40) {
            throw new ImageReadException("Not a Valid ICO File: Wrong bitmap header size " + size);
        }
        if (planes != 1) {
            throw new ImageReadException("Not a Valid ICO File: Planes can't be " + planes);
        }
        if (compression == 0 && bitCount == 32) {
            compression = 3;
            redMask = 16711680;
            greenMask = 65280;
            blueMask = 255;
            alphaMask = -16777216;
        }
        final BitmapHeader header = new BitmapHeader(size, width, height, planes, bitCount, compression, sizeImage, xPelsPerMeter, yPelsPerMeter, colorsUsed, colorsImportant);
        final int bitmapPixelsOffset = 70 + 4 * ((colorsUsed == 0 && bitCount <= 8) ? (1 << bitCount) : colorsUsed);
        final int bitmapSize = 70 + restOfFile.length;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(bitmapSize);
        try (final BinaryOutputStream bos = new BinaryOutputStream(baos, ByteOrder.LITTLE_ENDIAN)) {
            bos.write(66);
            bos.write(77);
            bos.write4Bytes(bitmapSize);
            bos.write4Bytes(0);
            bos.write4Bytes(bitmapPixelsOffset);
            bos.write4Bytes(56);
            bos.write4Bytes(width);
            bos.write4Bytes(height / 2);
            bos.write2Bytes(planes);
            bos.write2Bytes(bitCount);
            bos.write4Bytes(compression);
            bos.write4Bytes(sizeImage);
            bos.write4Bytes(xPelsPerMeter);
            bos.write4Bytes(yPelsPerMeter);
            bos.write4Bytes(colorsUsed);
            bos.write4Bytes(colorsImportant);
            bos.write4Bytes(redMask);
            bos.write4Bytes(greenMask);
            bos.write4Bytes(blueMask);
            bos.write4Bytes(alphaMask);
            bos.write(restOfFile);
            bos.flush();
        }
        final ByteArrayInputStream bmpInputStream = new ByteArrayInputStream(baos.toByteArray());
        final BufferedImage bmpImage = new BmpImageParser().getBufferedImage(bmpInputStream, null);
        int t_scanline_size = (width + 7) / 8;
        if (t_scanline_size % 4 != 0) {
            t_scanline_size += 4 - t_scanline_size % 4;
        }
        final int colorMapSizeBytes = t_scanline_size * (height / 2);
        byte[] transparencyMap = null;
        try {
            transparencyMap = BinaryFunctions.readBytes("transparency_map", bmpInputStream, colorMapSizeBytes, "Not a Valid ICO File");
        }
        catch (IOException ioEx) {
            if (bitCount != 32) {
                throw ioEx;
            }
        }
        boolean allAlphasZero = true;
        if (bitCount == 32) {
            for (int y = 0; allAlphasZero && y < bmpImage.getHeight(); ++y) {
                for (int x = 0; x < bmpImage.getWidth(); ++x) {
                    if ((bmpImage.getRGB(x, y) & 0xFF000000) != 0x0) {
                        allAlphasZero = false;
                        break;
                    }
                }
            }
        }
        BufferedImage resultImage;
        if (allAlphasZero) {
            resultImage = new BufferedImage(bmpImage.getWidth(), bmpImage.getHeight(), 2);
            for (int y2 = 0; y2 < resultImage.getHeight(); ++y2) {
                for (int x2 = 0; x2 < resultImage.getWidth(); ++x2) {
                    int alpha = 255;
                    if (transparencyMap != null) {
                        final int alphaByte = 0xFF & transparencyMap[t_scanline_size * (bmpImage.getHeight() - y2 - 1) + x2 / 8];
                        alpha = (0x1 & alphaByte >> 7 - x2 % 8);
                        alpha = ((alpha == 0) ? 255 : 0);
                    }
                    resultImage.setRGB(x2, y2, alpha << 24 | (0xFFFFFF & bmpImage.getRGB(x2, y2)));
                }
            }
        }
        else {
            resultImage = bmpImage;
        }
        return new BitmapIconData(fIconInfo, header, resultImage);
    }
    
    private IconData readIconData(final byte[] iconData, final IconInfo fIconInfo) throws ImageReadException, IOException {
        final ImageFormat imageFormat = Imaging.guessFormat(iconData);
        if (imageFormat.equals(ImageFormats.PNG)) {
            final BufferedImage bufferedImage = Imaging.getBufferedImage(iconData);
            return new PNGIconData(fIconInfo, bufferedImage);
        }
        return this.readBitmapIconData(iconData, fIconInfo);
    }
    
    private ImageContents readImage(final ByteSource byteSource) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            final FileHeader fileHeader = this.readFileHeader(is);
            final IconInfo[] fIconInfos = new IconInfo[fileHeader.iconCount];
            for (int i = 0; i < fileHeader.iconCount; ++i) {
                fIconInfos[i] = this.readIconInfo(is);
            }
            final IconData[] fIconDatas = new IconData[fileHeader.iconCount];
            for (int j = 0; j < fileHeader.iconCount; ++j) {
                final byte[] iconData = byteSource.getBlock(fIconInfos[j].imageOffset, fIconInfos[j].imageSize);
                fIconDatas[j] = this.readIconData(iconData, fIconInfos[j]);
            }
            final ImageContents ret = new ImageContents(fileHeader, fIconDatas);
            return ret;
        }
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        final ImageContents contents = this.readImage(byteSource);
        contents.fileHeader.dump(pw);
        for (final IconData iconData : contents.iconDatas) {
            iconData.dump(pw);
        }
        return true;
    }
    
    @Override
    public final BufferedImage getBufferedImage(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final ImageContents contents = this.readImage(byteSource);
        final FileHeader fileHeader = contents.fileHeader;
        if (fileHeader.iconCount > 0) {
            return contents.iconDatas[0].readBufferedImage();
        }
        throw new ImageReadException("No icons in ICO file");
    }
    
    @Override
    public List<BufferedImage> getAllBufferedImages(final ByteSource byteSource) throws ImageReadException, IOException {
        final ImageContents contents = this.readImage(byteSource);
        final FileHeader fileHeader = contents.fileHeader;
        final List<BufferedImage> result = new ArrayList<BufferedImage>(fileHeader.iconCount);
        for (int i = 0; i < fileHeader.iconCount; ++i) {
            final IconData iconData = contents.iconDatas[i];
            final BufferedImage image = iconData.readBufferedImage();
            result.add(image);
        }
        return result;
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, Map<String, Object> params) throws ImageWriteException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        if (params.containsKey("FORMAT")) {
            params.remove("FORMAT");
        }
        final PixelDensity pixelDensity = params.remove("PIXEL_DENSITY");
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageWriteException("Unknown parameter: " + firstKey);
        }
        final PaletteFactory paletteFactory = new PaletteFactory();
        final SimplePalette palette = paletteFactory.makeExactRgbPaletteSimple(src, 256);
        int bitCount;
        if (palette == null) {
            final boolean hasTransparency = paletteFactory.hasTransparency(src);
            if (hasTransparency) {
                bitCount = 32;
            }
            else {
                bitCount = 24;
            }
        }
        else if (palette.length() <= 2) {
            bitCount = 1;
        }
        else if (palette.length() <= 16) {
            bitCount = 4;
        }
        else {
            bitCount = 8;
        }
        final BinaryOutputStream bos = new BinaryOutputStream(os, ByteOrder.LITTLE_ENDIAN);
        int scanline_size = (bitCount * src.getWidth() + 7) / 8;
        if (scanline_size % 4 != 0) {
            scanline_size += 4 - scanline_size % 4;
        }
        int t_scanline_size = (src.getWidth() + 7) / 8;
        if (t_scanline_size % 4 != 0) {
            t_scanline_size += 4 - t_scanline_size % 4;
        }
        final int imageSize = 40 + 4 * ((bitCount <= 8) ? (1 << bitCount) : 0) + src.getHeight() * scanline_size + src.getHeight() * t_scanline_size;
        bos.write2Bytes(0);
        bos.write2Bytes(1);
        bos.write2Bytes(1);
        int iconDirEntryWidth = src.getWidth();
        int iconDirEntryHeight = src.getHeight();
        if (iconDirEntryWidth > 255 || iconDirEntryHeight > 255) {
            iconDirEntryWidth = 0;
            iconDirEntryHeight = 0;
        }
        bos.write(iconDirEntryWidth);
        bos.write(iconDirEntryHeight);
        bos.write((bitCount >= 8) ? 0 : (1 << bitCount));
        bos.write(0);
        bos.write2Bytes(1);
        bos.write2Bytes(bitCount);
        bos.write4Bytes(imageSize);
        bos.write4Bytes(22);
        bos.write4Bytes(40);
        bos.write4Bytes(src.getWidth());
        bos.write4Bytes(2 * src.getHeight());
        bos.write2Bytes(1);
        bos.write2Bytes(bitCount);
        bos.write4Bytes(0);
        bos.write4Bytes(0);
        bos.write4Bytes((pixelDensity == null) ? 0 : ((int)Math.round(pixelDensity.horizontalDensityMetres())));
        bos.write4Bytes((pixelDensity == null) ? 0 : ((int)Math.round(pixelDensity.horizontalDensityMetres())));
        bos.write4Bytes(0);
        bos.write4Bytes(0);
        if (palette != null) {
            for (int i = 0; i < 1 << bitCount; ++i) {
                if (i < palette.length()) {
                    final int argb = palette.getEntry(i);
                    bos.write3Bytes(argb);
                    bos.write(0);
                }
                else {
                    bos.write4Bytes(0);
                }
            }
        }
        int bitCache = 0;
        int bitsInCache = 0;
        final int rowPadding = scanline_size - (bitCount * src.getWidth() + 7) / 8;
        for (int y = src.getHeight() - 1; y >= 0; --y) {
            for (int x = 0; x < src.getWidth(); ++x) {
                final int argb2 = src.getRGB(x, y);
                if (bitCount < 8) {
                    final int rgb = 0xFFFFFF & argb2;
                    final int index = palette.getPaletteIndex(rgb);
                    bitCache <<= bitCount;
                    bitCache |= index;
                    bitsInCache += bitCount;
                    if (bitsInCache >= 8) {
                        bos.write(0xFF & bitCache);
                        bitCache = 0;
                        bitsInCache = 0;
                    }
                }
                else if (bitCount == 8) {
                    final int rgb = 0xFFFFFF & argb2;
                    final int index = palette.getPaletteIndex(rgb);
                    bos.write(0xFF & index);
                }
                else if (bitCount == 24) {
                    bos.write3Bytes(argb2);
                }
                else if (bitCount == 32) {
                    bos.write4Bytes(argb2);
                }
            }
            if (bitsInCache > 0) {
                bitCache <<= 8 - bitsInCache;
                bos.write(0xFF & bitCache);
                bitCache = 0;
                bitsInCache = 0;
            }
            for (int x = 0; x < rowPadding; ++x) {
                bos.write(0);
            }
        }
        final int t_row_padding = t_scanline_size - (src.getWidth() + 7) / 8;
        for (int y2 = src.getHeight() - 1; y2 >= 0; --y2) {
            for (int x2 = 0; x2 < src.getWidth(); ++x2) {
                final int argb3 = src.getRGB(x2, y2);
                final int alpha = 0xFF & argb3 >> 24;
                bitCache <<= 1;
                if (alpha == 0) {
                    bitCache |= 0x1;
                }
                if (++bitsInCache >= 8) {
                    bos.write(0xFF & bitCache);
                    bitCache = 0;
                    bitsInCache = 0;
                }
            }
            if (bitsInCache > 0) {
                bitCache <<= 8 - bitsInCache;
                bos.write(0xFF & bitCache);
                bitCache = 0;
                bitsInCache = 0;
            }
            for (int x2 = 0; x2 < t_row_padding; ++x2) {
                bos.write(0);
            }
        }
        bos.close();
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        return null;
    }
    
    static {
        ACCEPTED_EXTENSIONS = new String[] { ".ico", ".cur" };
    }
    
    private static class FileHeader
    {
        public final int reserved;
        public final int iconType;
        public final int iconCount;
        
        FileHeader(final int reserved, final int iconType, final int iconCount) {
            this.reserved = reserved;
            this.iconType = iconType;
            this.iconCount = iconCount;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("FileHeader");
            pw.println("Reserved: " + this.reserved);
            pw.println("IconType: " + this.iconType);
            pw.println("IconCount: " + this.iconCount);
            pw.println();
        }
    }
    
    private static class IconInfo
    {
        public final byte width;
        public final byte height;
        public final byte colorCount;
        public final byte reserved;
        public final int planes;
        public final int bitCount;
        public final int imageSize;
        public final int imageOffset;
        
        IconInfo(final byte width, final byte height, final byte colorCount, final byte reserved, final int planes, final int bitCount, final int imageSize, final int imageOffset) {
            this.width = width;
            this.height = height;
            this.colorCount = colorCount;
            this.reserved = reserved;
            this.planes = planes;
            this.bitCount = bitCount;
            this.imageSize = imageSize;
            this.imageOffset = imageOffset;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("IconInfo");
            pw.println("Width: " + this.width);
            pw.println("Height: " + this.height);
            pw.println("ColorCount: " + this.colorCount);
            pw.println("Reserved: " + this.reserved);
            pw.println("Planes: " + this.planes);
            pw.println("BitCount: " + this.bitCount);
            pw.println("ImageSize: " + this.imageSize);
            pw.println("ImageOffset: " + this.imageOffset);
        }
    }
    
    private static class BitmapHeader
    {
        public final int size;
        public final int width;
        public final int height;
        public final int planes;
        public final int bitCount;
        public final int compression;
        public final int sizeImage;
        public final int xPelsPerMeter;
        public final int yPelsPerMeter;
        public final int colorsUsed;
        public final int colorsImportant;
        
        BitmapHeader(final int size, final int width, final int height, final int planes, final int bitCount, final int compression, final int sizeImage, final int pelsPerMeter, final int pelsPerMeter2, final int colorsUsed, final int colorsImportant) {
            this.size = size;
            this.width = width;
            this.height = height;
            this.planes = planes;
            this.bitCount = bitCount;
            this.compression = compression;
            this.sizeImage = sizeImage;
            this.xPelsPerMeter = pelsPerMeter;
            this.yPelsPerMeter = pelsPerMeter2;
            this.colorsUsed = colorsUsed;
            this.colorsImportant = colorsImportant;
        }
        
        public void dump(final PrintWriter pw) {
            pw.println("BitmapHeader");
            pw.println("Size: " + this.size);
            pw.println("Width: " + this.width);
            pw.println("Height: " + this.height);
            pw.println("Planes: " + this.planes);
            pw.println("BitCount: " + this.bitCount);
            pw.println("Compression: " + this.compression);
            pw.println("SizeImage: " + this.sizeImage);
            pw.println("XPelsPerMeter: " + this.xPelsPerMeter);
            pw.println("YPelsPerMeter: " + this.yPelsPerMeter);
            pw.println("ColorsUsed: " + this.colorsUsed);
            pw.println("ColorsImportant: " + this.colorsImportant);
        }
    }
    
    private abstract static class IconData
    {
        public final IconInfo iconInfo;
        
        IconData(final IconInfo iconInfo) {
            this.iconInfo = iconInfo;
        }
        
        public void dump(final PrintWriter pw) {
            this.iconInfo.dump(pw);
            pw.println();
            this.dumpSubclass(pw);
        }
        
        protected abstract void dumpSubclass(final PrintWriter p0);
        
        public abstract BufferedImage readBufferedImage() throws ImageReadException;
    }
    
    private static class BitmapIconData extends IconData
    {
        public final BitmapHeader header;
        public final BufferedImage bufferedImage;
        
        BitmapIconData(final IconInfo iconInfo, final BitmapHeader header, final BufferedImage bufferedImage) {
            super(iconInfo);
            this.header = header;
            this.bufferedImage = bufferedImage;
        }
        
        @Override
        public BufferedImage readBufferedImage() throws ImageReadException {
            return this.bufferedImage;
        }
        
        @Override
        protected void dumpSubclass(final PrintWriter pw) {
            pw.println("BitmapIconData");
            this.header.dump(pw);
            pw.println();
        }
    }
    
    private static class PNGIconData extends IconData
    {
        public final BufferedImage bufferedImage;
        
        PNGIconData(final IconInfo iconInfo, final BufferedImage bufferedImage) {
            super(iconInfo);
            this.bufferedImage = bufferedImage;
        }
        
        @Override
        public BufferedImage readBufferedImage() {
            return this.bufferedImage;
        }
        
        @Override
        protected void dumpSubclass(final PrintWriter pw) {
            pw.println("PNGIconData");
            pw.println();
        }
    }
    
    private static class ImageContents
    {
        public final FileHeader fileHeader;
        public final IconData[] iconDatas;
        
        ImageContents(final FileHeader fileHeader, final IconData[] iconDatas) {
            this.fileHeader = fileHeader;
            this.iconDatas = iconDatas;
        }
    }
}
