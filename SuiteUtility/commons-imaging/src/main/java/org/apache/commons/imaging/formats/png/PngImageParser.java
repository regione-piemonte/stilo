// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png;

import org.apache.commons.imaging.ImageWriteException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.awt.color.ColorSpace;
import org.apache.commons.imaging.ColorTools;
import java.awt.image.ColorModel;
import java.awt.color.ICC_ColorSpace;
import org.apache.commons.imaging.icc.IccProfileParser;
import java.util.zip.InflaterInputStream;
import java.io.ByteArrayInputStream;
import java.awt.color.ICC_Profile;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.formats.png.transparencyfilters.TransparencyFilterIndexedColor;
import org.apache.commons.imaging.formats.png.transparencyfilters.TransparencyFilterTrueColor;
import org.apache.commons.imaging.formats.png.transparencyfilters.TransparencyFilterGrayscale;
import org.apache.commons.imaging.formats.png.transparencyfilters.TransparencyFilter;
import org.apache.commons.imaging.formats.png.chunks.PngTextChunk;
import org.apache.commons.imaging.common.GenericImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata;
import java.awt.Dimension;
import java.util.Map;
import org.apache.commons.imaging.formats.png.chunks.PngChunkItxt;
import org.apache.commons.imaging.formats.png.chunks.PngChunkGama;
import org.apache.commons.imaging.formats.png.chunks.PngChunkIdat;
import org.apache.commons.imaging.formats.png.chunks.PngChunkScal;
import org.apache.commons.imaging.formats.png.chunks.PngChunkPhys;
import org.apache.commons.imaging.formats.png.chunks.PngChunkPlte;
import org.apache.commons.imaging.formats.png.chunks.PngChunkIhdr;
import org.apache.commons.imaging.formats.png.chunks.PngChunkZtxt;
import org.apache.commons.imaging.formats.png.chunks.PngChunkText;
import org.apache.commons.imaging.formats.png.chunks.PngChunkIccp;
import java.util.logging.Level;
import org.apache.commons.imaging.common.BinaryFunctions;
import org.apache.commons.imaging.common.bytesource.ByteSource;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import java.util.Iterator;
import org.apache.commons.imaging.formats.png.chunks.PngChunk;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageFormat;
import java.util.logging.Logger;
import org.apache.commons.imaging.ImageParser;

public class PngImageParser extends ImageParser
{
    private static final Logger LOGGER;
    private static final String DEFAULT_EXTENSION = ".png";
    private static final String[] ACCEPTED_EXTENSIONS;
    
    @Override
    public String getName() {
        return "Png-Custom";
    }
    
    @Override
    public String getDefaultExtension() {
        return ".png";
    }
    
    @Override
    protected String[] getAcceptedExtensions() {
        return PngImageParser.ACCEPTED_EXTENSIONS.clone();
    }
    
    @Override
    protected ImageFormat[] getAcceptedTypes() {
        return new ImageFormat[] { ImageFormats.PNG };
    }
    
    public static String getChunkTypeName(final int chunkType) {
        final StringBuilder result = new StringBuilder();
        result.append((char)(0xFF & chunkType >> 24));
        result.append((char)(0xFF & chunkType >> 16));
        result.append((char)(0xFF & chunkType >> 8));
        result.append((char)(0xFF & chunkType >> 0));
        return result.toString();
    }
    
    public List<String> getChunkTypes(final InputStream is) throws ImageReadException, IOException {
        final List<PngChunk> chunks = this.readChunks(is, null, false);
        final List<String> chunkTypes = new ArrayList<String>(chunks.size());
        for (final PngChunk chunk : chunks) {
            chunkTypes.add(getChunkTypeName(chunk.chunkType));
        }
        return chunkTypes;
    }
    
    public boolean hasChunkType(final ByteSource byteSource, final ChunkType chunkType) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            this.readSignature(is);
            final List<PngChunk> chunks = this.readChunks(is, new ChunkType[] { chunkType }, true);
            return !chunks.isEmpty();
        }
    }
    
    private boolean keepChunk(final int chunkType, final ChunkType[] chunkTypes) {
        if (chunkTypes == null) {
            return true;
        }
        for (final ChunkType chunkType2 : chunkTypes) {
            if (chunkType2.value == chunkType) {
                return true;
            }
        }
        return false;
    }
    
    private List<PngChunk> readChunks(final InputStream is, final ChunkType[] chunkTypes, final boolean returnAfterFirst) throws ImageReadException, IOException {
        final List<PngChunk> result = new ArrayList<PngChunk>();
        while (true) {
            final int length = BinaryFunctions.read4Bytes("Length", is, "Not a Valid PNG File", this.getByteOrder());
            final int chunkType = BinaryFunctions.read4Bytes("ChunkType", is, "Not a Valid PNG File", this.getByteOrder());
            if (PngImageParser.LOGGER.isLoggable(Level.FINEST)) {
                BinaryFunctions.printCharQuad("ChunkType", chunkType);
                this.debugNumber("Length", length, 4);
            }
            final boolean keep = this.keepChunk(chunkType, chunkTypes);
            byte[] bytes = null;
            if (keep) {
                bytes = BinaryFunctions.readBytes("Chunk Data", is, length, "Not a Valid PNG File: Couldn't read Chunk Data.");
            }
            else {
                BinaryFunctions.skipBytes(is, length, "Not a Valid PNG File");
            }
            if (PngImageParser.LOGGER.isLoggable(Level.FINEST) && bytes != null) {
                this.debugNumber("bytes", bytes.length, 4);
            }
            final int crc = BinaryFunctions.read4Bytes("CRC", is, "Not a Valid PNG File", this.getByteOrder());
            if (keep) {
                if (chunkType == ChunkType.iCCP.value) {
                    result.add(new PngChunkIccp(length, chunkType, crc, bytes));
                }
                else if (chunkType == ChunkType.tEXt.value) {
                    result.add(new PngChunkText(length, chunkType, crc, bytes));
                }
                else if (chunkType == ChunkType.zTXt.value) {
                    result.add(new PngChunkZtxt(length, chunkType, crc, bytes));
                }
                else if (chunkType == ChunkType.IHDR.value) {
                    result.add(new PngChunkIhdr(length, chunkType, crc, bytes));
                }
                else if (chunkType == ChunkType.PLTE.value) {
                    result.add(new PngChunkPlte(length, chunkType, crc, bytes));
                }
                else if (chunkType == ChunkType.pHYs.value) {
                    result.add(new PngChunkPhys(length, chunkType, crc, bytes));
                }
                else if (chunkType == ChunkType.sCAL.value) {
                    result.add(new PngChunkScal(length, chunkType, crc, bytes));
                }
                else if (chunkType == ChunkType.IDAT.value) {
                    result.add(new PngChunkIdat(length, chunkType, crc, bytes));
                }
                else if (chunkType == ChunkType.gAMA.value) {
                    result.add(new PngChunkGama(length, chunkType, crc, bytes));
                }
                else if (chunkType == ChunkType.iTXt.value) {
                    result.add(new PngChunkItxt(length, chunkType, crc, bytes));
                }
                else {
                    result.add(new PngChunk(length, chunkType, crc, bytes));
                }
                if (returnAfterFirst) {
                    return result;
                }
            }
            if (chunkType == ChunkType.IEND.value) {
                return result;
            }
        }
    }
    
    public void readSignature(final InputStream is) throws ImageReadException, IOException {
        BinaryFunctions.readAndVerifyBytes(is, PngConstants.PNG_SIGNATURE, "Not a Valid PNG Segment: Incorrect Signature");
    }
    
    private List<PngChunk> readChunks(final ByteSource byteSource, final ChunkType[] chunkTypes, final boolean returnAfterFirst) throws ImageReadException, IOException {
        try (final InputStream is = byteSource.getInputStream()) {
            this.readSignature(is);
            return this.readChunks(is, chunkTypes, returnAfterFirst);
        }
    }
    
    @Override
    public byte[] getICCProfileBytes(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final List<PngChunk> chunks = this.readChunks(byteSource, new ChunkType[] { ChunkType.iCCP }, true);
        if (chunks == null || chunks.isEmpty()) {
            return null;
        }
        if (chunks.size() > 1) {
            throw new ImageReadException("PNG contains more than one ICC Profile ");
        }
        final PngChunkIccp pngChunkiCCP = chunks.get(0);
        final byte[] bytes = pngChunkiCCP.getUncompressedProfile();
        return bytes;
    }
    
    @Override
    public Dimension getImageSize(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final List<PngChunk> chunks = this.readChunks(byteSource, new ChunkType[] { ChunkType.IHDR }, true);
        if (chunks == null || chunks.isEmpty()) {
            throw new ImageReadException("Png: No chunks");
        }
        if (chunks.size() > 1) {
            throw new ImageReadException("PNG contains more than one Header");
        }
        final PngChunkIhdr pngChunkIHDR = chunks.get(0);
        return new Dimension(pngChunkIHDR.width, pngChunkIHDR.height);
    }
    
    @Override
    public ImageMetadata getMetadata(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final List<PngChunk> chunks = this.readChunks(byteSource, new ChunkType[] { ChunkType.tEXt, ChunkType.zTXt }, true);
        if (chunks == null || chunks.isEmpty()) {
            return null;
        }
        final GenericImageMetadata result = new GenericImageMetadata();
        for (final PngChunk chunk : chunks) {
            final PngTextChunk textChunk = (PngTextChunk)chunk;
            result.add(textChunk.getKeyword(), textChunk.getText());
        }
        return result;
    }
    
    private List<PngChunk> filterChunks(final List<PngChunk> chunks, final ChunkType type) {
        final List<PngChunk> result = new ArrayList<PngChunk>();
        for (final PngChunk chunk : chunks) {
            if (chunk.chunkType == type.value) {
                result.add(chunk);
            }
        }
        return result;
    }
    
    private TransparencyFilter getTransparencyFilter(final PngColorType pngColorType, final PngChunk pngChunktRNS) throws ImageReadException, IOException {
        switch (pngColorType) {
            case GREYSCALE: {
                return new TransparencyFilterGrayscale(pngChunktRNS.getBytes());
            }
            case TRUE_COLOR: {
                return new TransparencyFilterTrueColor(pngChunktRNS.getBytes());
            }
            case INDEXED_COLOR: {
                return new TransparencyFilterIndexedColor(pngChunktRNS.getBytes());
            }
            default: {
                throw new ImageReadException("Simple Transparency not compatible with ColorType: " + pngColorType);
            }
        }
    }
    
    @Override
    public ImageInfo getImageInfo(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final List<PngChunk> chunks = this.readChunks(byteSource, new ChunkType[] { ChunkType.IHDR, ChunkType.pHYs, ChunkType.sCAL, ChunkType.tEXt, ChunkType.zTXt, ChunkType.tRNS, ChunkType.PLTE, ChunkType.iTXt }, false);
        if (chunks == null || chunks.isEmpty()) {
            throw new ImageReadException("PNG: no chunks");
        }
        final List<PngChunk> IHDRs = this.filterChunks(chunks, ChunkType.IHDR);
        if (IHDRs.size() != 1) {
            throw new ImageReadException("PNG contains more than one Header");
        }
        final PngChunkIhdr pngChunkIHDR = IHDRs.get(0);
        boolean transparent = false;
        final List<PngChunk> tRNSs = this.filterChunks(chunks, ChunkType.tRNS);
        transparent = (!tRNSs.isEmpty() || pngChunkIHDR.pngColorType.hasAlpha());
        PngChunkPhys pngChunkpHYs = null;
        final List<PngChunk> pHYss = this.filterChunks(chunks, ChunkType.pHYs);
        if (pHYss.size() > 1) {
            throw new ImageReadException("PNG contains more than one pHYs: " + pHYss.size());
        }
        if (pHYss.size() == 1) {
            pngChunkpHYs = pHYss.get(0);
        }
        PhysicalScale physicalScale = PhysicalScale.UNDEFINED;
        final List<PngChunk> sCALs = this.filterChunks(chunks, ChunkType.sCAL);
        if (sCALs.size() > 1) {
            throw new ImageReadException("PNG contains more than one sCAL:" + sCALs.size());
        }
        if (sCALs.size() == 1) {
            final PngChunkScal pngChunkScal = sCALs.get(0);
            if (pngChunkScal.unitSpecifier == 1) {
                physicalScale = PhysicalScale.createFromMeters(pngChunkScal.unitsPerPixelXAxis, pngChunkScal.unitsPerPixelYAxis);
            }
            else {
                physicalScale = PhysicalScale.createFromRadians(pngChunkScal.unitsPerPixelXAxis, pngChunkScal.unitsPerPixelYAxis);
            }
        }
        final List<PngChunk> tEXts = this.filterChunks(chunks, ChunkType.tEXt);
        final List<PngChunk> zTXts = this.filterChunks(chunks, ChunkType.zTXt);
        final List<PngChunk> iTXts = this.filterChunks(chunks, ChunkType.iTXt);
        final int chunkCount = tEXts.size() + zTXts.size() + iTXts.size();
        final List<String> comments = new ArrayList<String>(chunkCount);
        final List<PngText> textChunks = new ArrayList<PngText>(chunkCount);
        for (final PngChunk tEXt : tEXts) {
            final PngChunkText pngChunktEXt = (PngChunkText)tEXt;
            comments.add(pngChunktEXt.keyword + ": " + pngChunktEXt.text);
            textChunks.add(pngChunktEXt.getContents());
        }
        for (final PngChunk zTXt : zTXts) {
            final PngChunkZtxt pngChunkzTXt = (PngChunkZtxt)zTXt;
            comments.add(pngChunkzTXt.keyword + ": " + pngChunkzTXt.text);
            textChunks.add(pngChunkzTXt.getContents());
        }
        for (final PngChunk iTXt : iTXts) {
            final PngChunkItxt pngChunkiTXt = (PngChunkItxt)iTXt;
            comments.add(pngChunkiTXt.keyword + ": " + pngChunkiTXt.text);
            textChunks.add(pngChunkiTXt.getContents());
        }
        final int bitsPerPixel = pngChunkIHDR.bitDepth * pngChunkIHDR.pngColorType.getSamplesPerPixel();
        final ImageFormat format = ImageFormats.PNG;
        final String formatName = "PNG Portable Network Graphics";
        final int height = pngChunkIHDR.height;
        final String mimeType = "image/png";
        final int numberOfImages = 1;
        final int width = pngChunkIHDR.width;
        final boolean progressive = pngChunkIHDR.interlaceMethod.isProgressive();
        int physicalHeightDpi = -1;
        float physicalHeightInch = -1.0f;
        int physicalWidthDpi = -1;
        float physicalWidthInch = -1.0f;
        if (pngChunkpHYs != null && pngChunkpHYs.unitSpecifier == 1) {
            final double metersPerInch = 0.0254;
            physicalWidthDpi = (int)Math.round(pngChunkpHYs.pixelsPerUnitXAxis * 0.0254);
            physicalWidthInch = (float)(width / (pngChunkpHYs.pixelsPerUnitXAxis * 0.0254));
            physicalHeightDpi = (int)Math.round(pngChunkpHYs.pixelsPerUnitYAxis * 0.0254);
            physicalHeightInch = (float)(height / (pngChunkpHYs.pixelsPerUnitYAxis * 0.0254));
        }
        boolean usesPalette = false;
        final List<PngChunk> PLTEs = this.filterChunks(chunks, ChunkType.PLTE);
        if (PLTEs.size() > 1) {
            usesPalette = true;
        }
        ImageInfo.ColorType colorType = null;
        switch (pngChunkIHDR.pngColorType) {
            case GREYSCALE:
            case GREYSCALE_WITH_ALPHA: {
                colorType = ImageInfo.ColorType.GRAYSCALE;
                break;
            }
            case TRUE_COLOR:
            case INDEXED_COLOR:
            case TRUE_COLOR_WITH_ALPHA: {
                colorType = ImageInfo.ColorType.RGB;
                break;
            }
            default: {
                throw new ImageReadException("Png: Unknown ColorType: " + pngChunkIHDR.pngColorType);
            }
        }
        final String formatDetails = "Png";
        final ImageInfo.CompressionAlgorithm compressionAlgorithm = ImageInfo.CompressionAlgorithm.PNG_FILTER;
        return new PngImageInfo("Png", bitsPerPixel, comments, format, "PNG Portable Network Graphics", height, "image/png", 1, physicalHeightDpi, physicalHeightInch, physicalWidthDpi, physicalWidthInch, width, progressive, transparent, usesPalette, colorType, compressionAlgorithm, textChunks, physicalScale);
    }
    
    @Override
    public BufferedImage getBufferedImage(final ByteSource byteSource, Map<String, Object> params) throws ImageReadException, IOException {
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        final List<PngChunk> chunks = this.readChunks(byteSource, new ChunkType[] { ChunkType.IHDR, ChunkType.PLTE, ChunkType.IDAT, ChunkType.tRNS, ChunkType.iCCP, ChunkType.gAMA, ChunkType.sRGB }, false);
        if (chunks == null || chunks.isEmpty()) {
            throw new ImageReadException("PNG: no chunks");
        }
        final List<PngChunk> IHDRs = this.filterChunks(chunks, ChunkType.IHDR);
        if (IHDRs.size() != 1) {
            throw new ImageReadException("PNG contains more than one Header");
        }
        final PngChunkIhdr pngChunkIHDR = IHDRs.get(0);
        final List<PngChunk> PLTEs = this.filterChunks(chunks, ChunkType.PLTE);
        if (PLTEs.size() > 1) {
            throw new ImageReadException("PNG contains more than one Palette");
        }
        PngChunkPlte pngChunkPLTE = null;
        if (PLTEs.size() == 1) {
            pngChunkPLTE = PLTEs.get(0);
        }
        final List<PngChunk> IDATs = this.filterChunks(chunks, ChunkType.IDAT);
        if (IDATs.isEmpty()) {
            throw new ImageReadException("PNG missing image data");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (final PngChunk IDAT : IDATs) {
            final PngChunkIdat pngChunkIDAT = (PngChunkIdat)IDAT;
            final byte[] bytes = pngChunkIDAT.getBytes();
            baos.write(bytes);
        }
        final byte[] compressed = baos.toByteArray();
        baos = null;
        TransparencyFilter transparencyFilter = null;
        final List<PngChunk> tRNSs = this.filterChunks(chunks, ChunkType.tRNS);
        if (!tRNSs.isEmpty()) {
            final PngChunk pngChunktRNS = tRNSs.get(0);
            transparencyFilter = this.getTransparencyFilter(pngChunkIHDR.pngColorType, pngChunktRNS);
        }
        ICC_Profile iccProfile = null;
        GammaCorrection gammaCorrection = null;
        final List<PngChunk> sRGBs = this.filterChunks(chunks, ChunkType.sRGB);
        final List<PngChunk> gAMAs = this.filterChunks(chunks, ChunkType.gAMA);
        final List<PngChunk> iCCPs = this.filterChunks(chunks, ChunkType.iCCP);
        if (sRGBs.size() > 1) {
            throw new ImageReadException("PNG: unexpected sRGB chunk");
        }
        if (gAMAs.size() > 1) {
            throw new ImageReadException("PNG: unexpected gAMA chunk");
        }
        if (iCCPs.size() > 1) {
            throw new ImageReadException("PNG: unexpected iCCP chunk");
        }
        if (sRGBs.size() == 1) {
            if (PngImageParser.LOGGER.isLoggable(Level.FINEST)) {
                PngImageParser.LOGGER.finest("sRGB, no color management neccesary.");
            }
        }
        else if (iCCPs.size() == 1) {
            if (PngImageParser.LOGGER.isLoggable(Level.FINEST)) {
                PngImageParser.LOGGER.finest("iCCP.");
            }
            final PngChunkIccp pngChunkiCCP = iCCPs.get(0);
            final byte[] bytes2 = pngChunkiCCP.getUncompressedProfile();
            iccProfile = ICC_Profile.getInstance(bytes2);
        }
        else if (gAMAs.size() == 1) {
            final PngChunkGama pngChunkgAMA = gAMAs.get(0);
            final double gamma = pngChunkgAMA.getGamma();
            final double targetGamma = 1.0;
            final double diff = Math.abs(1.0 - gamma);
            if (diff >= 0.5) {
                gammaCorrection = new GammaCorrection(gamma, 1.0);
            }
            if (gammaCorrection != null && pngChunkPLTE != null) {
                pngChunkPLTE.correct(gammaCorrection);
            }
        }
        final int width = pngChunkIHDR.width;
        final int height = pngChunkIHDR.height;
        final PngColorType pngColorType = pngChunkIHDR.pngColorType;
        final int bitDepth = pngChunkIHDR.bitDepth;
        if (pngChunkIHDR.filterMethod != 0) {
            throw new ImageReadException("PNG: unknown FilterMethod: " + pngChunkIHDR.filterMethod);
        }
        final int bitsPerPixel = bitDepth * pngColorType.getSamplesPerPixel();
        final boolean hasAlpha = pngColorType.hasAlpha() || transparencyFilter != null;
        BufferedImage result;
        if (pngColorType.isGreyscale()) {
            result = this.getBufferedImageFactory(params).getGrayscaleBufferedImage(width, height, hasAlpha);
        }
        else {
            result = this.getBufferedImageFactory(params).getColorBufferedImage(width, height, hasAlpha);
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(compressed);
        final InflaterInputStream iis = new InflaterInputStream(bais);
        ScanExpediter scanExpediter = null;
        switch (pngChunkIHDR.interlaceMethod) {
            case NONE: {
                scanExpediter = new ScanExpediterSimple(width, height, iis, result, pngColorType, bitDepth, bitsPerPixel, pngChunkPLTE, gammaCorrection, transparencyFilter);
                break;
            }
            case ADAM7: {
                scanExpediter = new ScanExpediterInterlaced(width, height, iis, result, pngColorType, bitDepth, bitsPerPixel, pngChunkPLTE, gammaCorrection, transparencyFilter);
                break;
            }
            default: {
                throw new ImageReadException("Unknown InterlaceMethod: " + pngChunkIHDR.interlaceMethod);
            }
        }
        scanExpediter.drive();
        if (iccProfile != null) {
            final Boolean is_srgb = new IccProfileParser().issRGB(iccProfile);
            if (is_srgb == null || !is_srgb) {
                final ICC_ColorSpace cs = new ICC_ColorSpace(iccProfile);
                final ColorModel srgbCM = ColorModel.getRGBdefault();
                final ColorSpace cs_sRGB = srgbCM.getColorSpace();
                result = new ColorTools().convertBetweenColorSpaces(result, cs, cs_sRGB);
            }
        }
        return result;
    }
    
    @Override
    public boolean dumpImageFile(final PrintWriter pw, final ByteSource byteSource) throws ImageReadException, IOException {
        final ImageInfo imageInfo = this.getImageInfo(byteSource);
        if (imageInfo == null) {
            return false;
        }
        imageInfo.toString(pw, "");
        final List<PngChunk> chunks = this.readChunks(byteSource, null, false);
        final List<PngChunk> IHDRs = this.filterChunks(chunks, ChunkType.IHDR);
        if (IHDRs.size() != 1) {
            if (PngImageParser.LOGGER.isLoggable(Level.FINEST)) {
                PngImageParser.LOGGER.finest("PNG contains more than one Header");
            }
            return false;
        }
        final PngChunkIhdr pngChunkIHDR = IHDRs.get(0);
        pw.println("Color: " + pngChunkIHDR.pngColorType.name());
        pw.println("chunks: " + chunks.size());
        if (chunks.isEmpty()) {
            return false;
        }
        for (int i = 0; i < chunks.size(); ++i) {
            final PngChunk chunk = chunks.get(i);
            BinaryFunctions.printCharQuad(pw, "\t" + i + ": ", chunk.chunkType);
        }
        pw.println("");
        pw.flush();
        return true;
    }
    
    @Override
    public void writeImage(final BufferedImage src, final OutputStream os, final Map<String, Object> params) throws ImageWriteException, IOException {
        new PngWriter().writeImage(src, os, params);
    }
    
    @Override
    public String getXmpXml(final ByteSource byteSource, final Map<String, Object> params) throws ImageReadException, IOException {
        final List<PngChunk> chunks = this.readChunks(byteSource, new ChunkType[] { ChunkType.iTXt }, false);
        if (chunks == null || chunks.isEmpty()) {
            return null;
        }
        final List<PngChunkItxt> xmpChunks = new ArrayList<PngChunkItxt>();
        for (final PngChunk chunk : chunks) {
            final PngChunkItxt itxtChunk = (PngChunkItxt)chunk;
            if (!itxtChunk.getKeyword().equals("XML:com.adobe.xmp")) {
                continue;
            }
            xmpChunks.add(itxtChunk);
        }
        if (xmpChunks.isEmpty()) {
            return null;
        }
        if (xmpChunks.size() > 1) {
            throw new ImageReadException("PNG contains more than one XMP chunk.");
        }
        final PngChunkItxt chunk2 = xmpChunks.get(0);
        return chunk2.getText();
    }
    
    static {
        LOGGER = Logger.getLogger(PngImageParser.class.getName());
        ACCEPTED_EXTENSIONS = new String[] { ".png" };
    }
}
