// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png;

import java.util.Iterator;
import java.util.List;
import org.apache.commons.imaging.PixelDensity;
import org.apache.commons.imaging.palette.SimplePalette;
import org.apache.commons.imaging.internal.Debug;
import org.apache.commons.imaging.palette.PaletteFactory;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.util.Map;
import org.apache.commons.imaging.palette.Palette;
import java.util.zip.DeflaterOutputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.imaging.ImageWriteException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class PngWriter
{
    private void writeInt(final OutputStream os, final int value) throws IOException {
        os.write(0xFF & value >> 24);
        os.write(0xFF & value >> 16);
        os.write(0xFF & value >> 8);
        os.write(0xFF & value >> 0);
    }
    
    private void writeChunk(final OutputStream os, final ChunkType chunkType, final byte[] data) throws IOException {
        final int dataLength = (data == null) ? 0 : data.length;
        this.writeInt(os, dataLength);
        os.write(chunkType.array);
        if (data != null) {
            os.write(data);
        }
        final PngCrc png_crc = new PngCrc();
        final long crc1 = png_crc.start_partial_crc(chunkType.array, chunkType.array.length);
        final long crc2 = (data == null) ? crc1 : png_crc.continue_partial_crc(crc1, data, data.length);
        final int crc3 = (int)png_crc.finish_partial_crc(crc2);
        this.writeInt(os, crc3);
    }
    
    private void writeChunkIHDR(final OutputStream os, final ImageHeader value) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.writeInt(baos, value.width);
        this.writeInt(baos, value.height);
        baos.write(0xFF & value.bitDepth);
        baos.write(0xFF & value.pngColorType.getValue());
        baos.write(0xFF & value.compressionMethod);
        baos.write(0xFF & value.filterMethod);
        baos.write(0xFF & value.interlaceMethod.ordinal());
        this.writeChunk(os, ChunkType.IHDR, baos.toByteArray());
    }
    
    private void writeChunkiTXt(final OutputStream os, final PngText.Itxt text) throws IOException, ImageWriteException {
        if (!this.isValidISO_8859_1(text.keyword)) {
            throw new ImageWriteException("Png tEXt chunk keyword is not ISO-8859-1: " + text.keyword);
        }
        if (!this.isValidISO_8859_1(text.languageTag)) {
            throw new ImageWriteException("Png tEXt chunk language tag is not ISO-8859-1: " + text.languageTag);
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(text.keyword.getBytes(StandardCharsets.ISO_8859_1));
        baos.write(0);
        baos.write(1);
        baos.write(0);
        baos.write(text.languageTag.getBytes(StandardCharsets.ISO_8859_1));
        baos.write(0);
        baos.write(text.translatedKeyword.getBytes(StandardCharsets.UTF_8));
        baos.write(0);
        baos.write(this.deflate(text.text.getBytes(StandardCharsets.UTF_8)));
        this.writeChunk(os, ChunkType.iTXt, baos.toByteArray());
    }
    
    private void writeChunkzTXt(final OutputStream os, final PngText.Ztxt text) throws IOException, ImageWriteException {
        if (!this.isValidISO_8859_1(text.keyword)) {
            throw new ImageWriteException("Png zTXt chunk keyword is not ISO-8859-1: " + text.keyword);
        }
        if (!this.isValidISO_8859_1(text.text)) {
            throw new ImageWriteException("Png zTXt chunk text is not ISO-8859-1: " + text.text);
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(text.keyword.getBytes(StandardCharsets.ISO_8859_1));
        baos.write(0);
        baos.write(0);
        baos.write(this.deflate(text.text.getBytes(StandardCharsets.ISO_8859_1)));
        this.writeChunk(os, ChunkType.zTXt, baos.toByteArray());
    }
    
    private void writeChunktEXt(final OutputStream os, final PngText.Text text) throws IOException, ImageWriteException {
        if (!this.isValidISO_8859_1(text.keyword)) {
            throw new ImageWriteException("Png tEXt chunk keyword is not ISO-8859-1: " + text.keyword);
        }
        if (!this.isValidISO_8859_1(text.text)) {
            throw new ImageWriteException("Png tEXt chunk text is not ISO-8859-1: " + text.text);
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(text.keyword.getBytes(StandardCharsets.ISO_8859_1));
        baos.write(0);
        baos.write(text.text.getBytes(StandardCharsets.ISO_8859_1));
        this.writeChunk(os, ChunkType.tEXt, baos.toByteArray());
    }
    
    private byte[] deflate(final byte[] bytes) throws IOException {
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (final DeflaterOutputStream dos = new DeflaterOutputStream(baos)) {
                dos.write(bytes);
            }
            return baos.toByteArray();
        }
    }
    
    private boolean isValidISO_8859_1(final String s) {
        final String roundtrip = new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.ISO_8859_1);
        return s.equals(roundtrip);
    }
    
    private void writeChunkXmpiTXt(final OutputStream os, final String xmpXml) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("XML:com.adobe.xmp".getBytes(StandardCharsets.ISO_8859_1));
        baos.write(0);
        baos.write(1);
        baos.write(0);
        baos.write(0);
        baos.write("XML:com.adobe.xmp".getBytes(StandardCharsets.UTF_8));
        baos.write(0);
        baos.write(this.deflate(xmpXml.getBytes(StandardCharsets.UTF_8)));
        this.writeChunk(os, ChunkType.iTXt, baos.toByteArray());
    }
    
    private void writeChunkPLTE(final OutputStream os, final Palette palette) throws IOException {
        final int length = palette.length();
        final byte[] bytes = new byte[length * 3];
        for (int i = 0; i < length; ++i) {
            final int rgb = palette.getEntry(i);
            final int index = i * 3;
            bytes[index + 0] = (byte)(0xFF & rgb >> 16);
            bytes[index + 1] = (byte)(0xFF & rgb >> 8);
            bytes[index + 2] = (byte)(0xFF & rgb >> 0);
        }
        this.writeChunk(os, ChunkType.PLTE, bytes);
    }
    
    private void writeChunkTRNS(final OutputStream os, final Palette palette) throws IOException {
        final byte[] bytes = new byte[palette.length()];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte)(0xFF & palette.getEntry(i) >> 24);
        }
        this.writeChunk(os, ChunkType.tRNS, bytes);
    }
    
    private void writeChunkIEND(final OutputStream os) throws IOException {
        this.writeChunk(os, ChunkType.IEND, null);
    }
    
    private void writeChunkIDAT(final OutputStream os, final byte[] bytes) throws IOException {
        this.writeChunk(os, ChunkType.IDAT, bytes);
    }
    
    private void writeChunkPHYS(final OutputStream os, final int xPPU, final int yPPU, final byte units) throws IOException {
        final byte[] bytes = { (byte)(0xFF & xPPU >> 24), (byte)(0xFF & xPPU >> 16), (byte)(0xFF & xPPU >> 8), (byte)(0xFF & xPPU >> 0), (byte)(0xFF & yPPU >> 24), (byte)(0xFF & yPPU >> 16), (byte)(0xFF & yPPU >> 8), (byte)(0xFF & yPPU >> 0), units };
        this.writeChunk(os, ChunkType.pHYs, bytes);
    }
    
    private void writeChunkSCAL(final OutputStream os, final double xUPP, final double yUPP, final byte units) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(units);
        baos.write(String.valueOf(xUPP).getBytes(StandardCharsets.ISO_8859_1));
        baos.write(0);
        baos.write(String.valueOf(yUPP).getBytes(StandardCharsets.ISO_8859_1));
        this.writeChunk(os, ChunkType.sCAL, baos.toByteArray());
    }
    
    private byte getBitDepth(final PngColorType pngColorType, final Map<String, Object> params) {
        byte depth = 8;
        final Object o = params.get("PNG_BIT_DEPTH");
        if (o instanceof Number) {
            depth = ((Number)o).byteValue();
        }
        return (byte)(pngColorType.isBitDepthAllowed(depth) ? depth : 8);
    }
    
    public void writeImage(final BufferedImage src, final OutputStream os, Map<String, Object> params) throws ImageWriteException, IOException {
        params = new HashMap<String, Object>(params);
        if (params.containsKey("FORMAT")) {
            params.remove("FORMAT");
        }
        final Map<String, Object> rawParams = new HashMap<String, Object>(params);
        if (params.containsKey("PNG_FORCE_TRUE_COLOR")) {
            params.remove("PNG_FORCE_TRUE_COLOR");
        }
        if (params.containsKey("PNG_FORCE_INDEXED_COLOR")) {
            params.remove("PNG_FORCE_INDEXED_COLOR");
        }
        if (params.containsKey("PNG_BIT_DEPTH")) {
            params.remove("PNG_BIT_DEPTH");
        }
        if (params.containsKey("XMP_XML")) {
            params.remove("XMP_XML");
        }
        if (params.containsKey("PNG_TEXT_CHUNKS")) {
            params.remove("PNG_TEXT_CHUNKS");
        }
        params.remove("PIXEL_DENSITY");
        params.remove("PHYSICAL_SCALE_CHUNK");
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageWriteException("Unknown parameter: " + firstKey);
        }
        params = rawParams;
        final int width = src.getWidth();
        final int height = src.getHeight();
        final boolean hasAlpha = new PaletteFactory().hasTransparency(src);
        Debug.debug("hasAlpha: " + hasAlpha);
        boolean isGrayscale = new PaletteFactory().isGrayscale(src);
        Debug.debug("isGrayscale: " + isGrayscale);
        final boolean forceIndexedColor = Boolean.TRUE.equals(params.get("PNG_FORCE_INDEXED_COLOR"));
        final boolean forceTrueColor = Boolean.TRUE.equals(params.get("PNG_FORCE_TRUE_COLOR"));
        if (forceIndexedColor && forceTrueColor) {
            throw new ImageWriteException("Params: Cannot force both indexed and true color modes");
        }
        PngColorType pngColorType;
        if (forceIndexedColor) {
            pngColorType = PngColorType.INDEXED_COLOR;
        }
        else if (forceTrueColor) {
            pngColorType = (hasAlpha ? PngColorType.TRUE_COLOR_WITH_ALPHA : PngColorType.TRUE_COLOR);
            isGrayscale = false;
        }
        else {
            pngColorType = PngColorType.getColorType(hasAlpha, isGrayscale);
        }
        Debug.debug("colorType: " + pngColorType);
        final byte bitDepth = this.getBitDepth(pngColorType, params);
        Debug.debug("bitDepth: " + bitDepth);
        int sampleDepth;
        if (pngColorType == PngColorType.INDEXED_COLOR) {
            sampleDepth = 8;
        }
        else {
            sampleDepth = bitDepth;
        }
        Debug.debug("sampleDepth: " + sampleDepth);
        PngConstants.PNG_SIGNATURE.writeTo(os);
        final byte compressionMethod = 0;
        final byte filterMethod = 0;
        final InterlaceMethod interlaceMethod = InterlaceMethod.NONE;
        final ImageHeader imageHeader = new ImageHeader(width, height, bitDepth, pngColorType, (byte)0, (byte)0, interlaceMethod);
        this.writeChunkIHDR(os, imageHeader);
        Palette palette = null;
        if (pngColorType == PngColorType.INDEXED_COLOR) {
            final int maxColors = hasAlpha ? 255 : 256;
            final PaletteFactory paletteFactory = new PaletteFactory();
            palette = paletteFactory.makeQuantizedRgbPalette(src, maxColors);
            if (hasAlpha) {
                palette = new TransparentPalette(palette);
                this.writeChunkPLTE(os, palette);
                this.writeChunkTRNS(os, new SimplePalette(new int[] { 0 }));
            }
            else {
                this.writeChunkPLTE(os, palette);
            }
        }
        final Object pixelDensityObj = params.get("PIXEL_DENSITY");
        if (pixelDensityObj instanceof PixelDensity) {
            final PixelDensity pixelDensity = (PixelDensity)pixelDensityObj;
            if (pixelDensity.isUnitless()) {
                this.writeChunkPHYS(os, (int)Math.round(pixelDensity.getRawHorizontalDensity()), (int)Math.round(pixelDensity.getRawVerticalDensity()), (byte)0);
            }
            else {
                this.writeChunkPHYS(os, (int)Math.round(pixelDensity.horizontalDensityMetres()), (int)Math.round(pixelDensity.verticalDensityMetres()), (byte)1);
            }
        }
        final Object physcialScaleObj = params.get("PHYSICAL_SCALE_CHUNK");
        if (physcialScaleObj instanceof PhysicalScale) {
            final PhysicalScale physicalScale = (PhysicalScale)physcialScaleObj;
            this.writeChunkSCAL(os, physicalScale.getHorizontalUnitsPerPixel(), physicalScale.getVerticalUnitsPerPixel(), (byte)(physicalScale.isInMeters() ? 1 : 2));
        }
        if (params.containsKey("XMP_XML")) {
            final String xmpXml = params.get("XMP_XML");
            this.writeChunkXmpiTXt(os, xmpXml);
        }
        if (params.containsKey("PNG_TEXT_CHUNKS")) {
            final List<?> outputTexts = params.get("PNG_TEXT_CHUNKS");
            for (final Object outputText : outputTexts) {
                final PngText text = (PngText)outputText;
                if (text instanceof PngText.Text) {
                    this.writeChunktEXt(os, (PngText.Text)text);
                }
                else if (text instanceof PngText.Ztxt) {
                    this.writeChunkzTXt(os, (PngText.Ztxt)text);
                }
                else {
                    if (!(text instanceof PngText.Itxt)) {
                        throw new ImageWriteException("Unknown text to embed in PNG: " + text);
                    }
                    this.writeChunkiTXt(os, (PngText.Itxt)text);
                }
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final boolean useAlpha = pngColorType == PngColorType.GREYSCALE_WITH_ALPHA || pngColorType == PngColorType.TRUE_COLOR_WITH_ALPHA;
        final int[] row = new int[width];
        for (int y = 0; y < height; ++y) {
            src.getRGB(0, y, width, 1, row, 0, width);
            baos.write(FilterType.NONE.ordinal());
            for (final int argb : row) {
                if (palette != null) {
                    if (hasAlpha && argb >>> 24 == 0) {
                        baos.write(0);
                    }
                    else {
                        final int index = palette.getPaletteIndex(argb);
                        baos.write(0xFF & index);
                    }
                }
                else {
                    final int alpha = 0xFF & argb >> 24;
                    final int red = 0xFF & argb >> 16;
                    final int green = 0xFF & argb >> 8;
                    final int blue = 0xFF & argb >> 0;
                    if (isGrayscale) {
                        final int gray = (red + green + blue) / 3;
                        baos.write(gray);
                    }
                    else {
                        baos.write(red);
                        baos.write(green);
                        baos.write(blue);
                    }
                    if (useAlpha) {
                        baos.write(alpha);
                    }
                }
            }
        }
        final byte[] uncompressed = baos.toByteArray();
        baos = new ByteArrayOutputStream();
        final DeflaterOutputStream dos = new DeflaterOutputStream(baos);
        final int chunkSize = 262144;
        for (int index2 = 0; index2 < uncompressed.length; index2 += 262144) {
            final int end = Math.min(uncompressed.length, index2 + 262144);
            final int length = end - index2;
            dos.write(uncompressed, index2, length);
            dos.flush();
            baos.flush();
            final byte[] compressed = baos.toByteArray();
            baos.reset();
            if (compressed.length > 0) {
                this.writeChunkIDAT(os, compressed);
            }
        }
        dos.finish();
        final byte[] compressed2 = baos.toByteArray();
        if (compressed2.length > 0) {
            this.writeChunkIDAT(os, compressed2);
        }
        this.writeChunkIEND(os);
        os.close();
    }
    
    private static class ImageHeader
    {
        public final int width;
        public final int height;
        public final byte bitDepth;
        public final PngColorType pngColorType;
        public final byte compressionMethod;
        public final byte filterMethod;
        public final InterlaceMethod interlaceMethod;
        
        ImageHeader(final int width, final int height, final byte bitDepth, final PngColorType pngColorType, final byte compressionMethod, final byte filterMethod, final InterlaceMethod interlaceMethod) {
            this.width = width;
            this.height = height;
            this.bitDepth = bitDepth;
            this.pngColorType = pngColorType;
            this.compressionMethod = compressionMethod;
            this.filterMethod = filterMethod;
            this.interlaceMethod = interlaceMethod;
        }
    }
    
    private static class TransparentPalette implements Palette
    {
        private final Palette palette;
        
        TransparentPalette(final Palette palette) {
            this.palette = palette;
        }
        
        @Override
        public int getEntry(final int index) {
            if (index == 0) {
                return 0;
            }
            return this.palette.getEntry(index - 1);
        }
        
        @Override
        public int length() {
            return 1 + this.palette.length();
        }
        
        @Override
        public int getPaletteIndex(final int rgb) throws ImageWriteException {
            if (rgb == 0) {
                return 0;
            }
            final int index = this.palette.getPaletteIndex(rgb);
            if (index >= 0) {
                return 1 + index;
            }
            return index;
        }
    }
}
