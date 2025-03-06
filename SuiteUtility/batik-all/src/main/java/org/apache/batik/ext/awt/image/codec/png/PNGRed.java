// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import java.awt.color.ColorSpace;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.DataBufferUShort;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.DataBufferByte;
import java.awt.Point;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import java.awt.image.IndexColorModel;
import java.util.zip.InflaterInputStream;
import java.util.zip.Inflater;
import java.util.Enumeration;
import java.io.SequenceInputStream;
import java.util.Collection;
import java.util.Collections;
import java.io.ByteArrayInputStream;
import org.apache.batik.ext.awt.image.codec.util.PropertyUtil;
import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.awt.image.ComponentColorModel;
import java.util.Map;
import java.awt.Rectangle;
import java.awt.image.WritableRaster;
import java.io.DataInputStream;
import java.util.List;
import org.apache.batik.ext.awt.image.rendered.AbstractRed;

public class PNGRed extends AbstractRed
{
    public static final int PNG_COLOR_GRAY = 0;
    public static final int PNG_COLOR_RGB = 2;
    public static final int PNG_COLOR_PALETTE = 3;
    public static final int PNG_COLOR_GRAY_ALPHA = 4;
    public static final int PNG_COLOR_RGB_ALPHA = 6;
    private static final String[] colorTypeNames;
    public static final int PNG_FILTER_NONE = 0;
    public static final int PNG_FILTER_SUB = 1;
    public static final int PNG_FILTER_UP = 2;
    public static final int PNG_FILTER_AVERAGE = 3;
    public static final int PNG_FILTER_PAETH = 4;
    private int[][] bandOffsets;
    private int bitDepth;
    private int colorType;
    private int compressionMethod;
    private int filterMethod;
    private int interlaceMethod;
    private int paletteEntries;
    private byte[] redPalette;
    private byte[] greenPalette;
    private byte[] bluePalette;
    private byte[] alphaPalette;
    private int bkgdRed;
    private int bkgdGreen;
    private int bkgdBlue;
    private int grayTransparentAlpha;
    private int redTransparentAlpha;
    private int greenTransparentAlpha;
    private int blueTransparentAlpha;
    private int maxOpacity;
    private int[] significantBits;
    private boolean suppressAlpha;
    private boolean expandPalette;
    private boolean output8BitGray;
    private boolean outputHasAlphaPalette;
    private boolean performGammaCorrection;
    private boolean expandGrayAlpha;
    private boolean generateEncodeParam;
    private PNGDecodeParam decodeParam;
    private PNGEncodeParam encodeParam;
    private boolean emitProperties;
    private float fileGamma;
    private float userExponent;
    private float displayExponent;
    private float[] chromaticity;
    private int sRGBRenderingIntent;
    private int postProcess;
    private static final int POST_NONE = 0;
    private static final int POST_GAMMA = 1;
    private static final int POST_GRAY_LUT = 2;
    private static final int POST_GRAY_LUT_ADD_TRANS = 3;
    private static final int POST_PALETTE_TO_RGB = 4;
    private static final int POST_PALETTE_TO_RGBA = 5;
    private static final int POST_ADD_GRAY_TRANS = 6;
    private static final int POST_ADD_RGB_TRANS = 7;
    private static final int POST_REMOVE_GRAY_TRANS = 8;
    private static final int POST_REMOVE_RGB_TRANS = 9;
    private static final int POST_EXP_MASK = 16;
    private static final int POST_GRAY_ALPHA_EXP = 16;
    private static final int POST_GAMMA_EXP = 17;
    private static final int POST_GRAY_LUT_ADD_TRANS_EXP = 19;
    private static final int POST_ADD_GRAY_TRANS_EXP = 22;
    private List streamVec;
    private DataInputStream dataStream;
    private int bytesPerPixel;
    private int inputBands;
    private int outputBands;
    private int chunkIndex;
    private List textKeys;
    private List textStrings;
    private List ztextKeys;
    private List ztextStrings;
    private WritableRaster theTile;
    private Rectangle bounds;
    private Map properties;
    private int[] gammaLut;
    private final byte[][] expandBits;
    private int[] grayLut;
    private static final int[] GrayBits8;
    private static final ComponentColorModel colorModelGray8;
    private static final int[] GrayAlphaBits8;
    private static final ComponentColorModel colorModelGrayAlpha8;
    private static final int[] GrayBits16;
    private static final ComponentColorModel colorModelGray16;
    private static final int[] GrayAlphaBits16;
    private static final ComponentColorModel colorModelGrayAlpha16;
    private static final int[] GrayBits32;
    private static final ComponentColorModel colorModelGray32;
    private static final int[] GrayAlphaBits32;
    private static final ComponentColorModel colorModelGrayAlpha32;
    private static final int[] RGBBits8;
    private static final ComponentColorModel colorModelRGB8;
    private static final int[] RGBABits8;
    private static final ComponentColorModel colorModelRGBA8;
    private static final int[] RGBBits16;
    private static final ComponentColorModel colorModelRGB16;
    private static final int[] RGBABits16;
    private static final ComponentColorModel colorModelRGBA16;
    private static final int[] RGBBits32;
    private static final ComponentColorModel colorModelRGB32;
    private static final int[] RGBABits32;
    private static final ComponentColorModel colorModelRGBA32;
    
    private void initGammaLut(final int n) {
        final double b = this.userExponent / (double)(this.fileGamma * this.displayExponent);
        final int n2 = 1 << n;
        final int n3 = (n == 16) ? 65535 : 255;
        this.gammaLut = new int[n2];
        for (int i = 0; i < n2; ++i) {
            int n4 = (int)(Math.pow(i / (double)(n2 - 1), b) * n3 + 0.5);
            if (n4 > n3) {
                n4 = n3;
            }
            this.gammaLut[i] = n4;
        }
    }
    
    private void initGrayLut(final int n) {
        final int n2 = 1 << n;
        this.grayLut = new int[n2];
        if (this.performGammaCorrection) {
            System.arraycopy(this.gammaLut, 0, this.grayLut, 0, n2);
        }
        else {
            for (int i = 0; i < n2; ++i) {
                this.grayLut[i] = this.expandBits[n][i];
            }
        }
    }
    
    public PNGRed(final InputStream inputStream) throws IOException {
        this(inputStream, null);
    }
    
    public PNGRed(InputStream inputStream, PNGDecodeParam decodeParam) throws IOException {
        this.bandOffsets = new int[][] { null, { 0 }, { 0, 1 }, { 0, 1, 2 }, { 0, 1, 2, 3 } };
        this.significantBits = null;
        this.suppressAlpha = false;
        this.expandPalette = false;
        this.output8BitGray = false;
        this.outputHasAlphaPalette = false;
        this.performGammaCorrection = false;
        this.expandGrayAlpha = false;
        this.generateEncodeParam = false;
        this.decodeParam = null;
        this.encodeParam = null;
        this.emitProperties = true;
        this.fileGamma = 0.45455f;
        this.userExponent = 1.0f;
        this.displayExponent = 2.2f;
        this.chromaticity = null;
        this.sRGBRenderingIntent = -1;
        this.postProcess = 0;
        this.streamVec = new ArrayList();
        this.chunkIndex = 0;
        this.textKeys = new ArrayList();
        this.textStrings = new ArrayList();
        this.ztextKeys = new ArrayList();
        this.ztextStrings = new ArrayList();
        this.properties = new HashMap();
        this.gammaLut = null;
        this.expandBits = new byte[][] { null, { 0, -1 }, { 0, 85, -86, -1 }, null, { 0, 17, 34, 51, 68, 85, 102, 119, -120, -103, -86, -69, -52, -35, -18, -1 } };
        this.grayLut = null;
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream);
        }
        final DataInputStream dataInputStream = new DataInputStream(inputStream);
        if (decodeParam == null) {
            decodeParam = new PNGDecodeParam();
        }
        this.decodeParam = decodeParam;
        this.suppressAlpha = decodeParam.getSuppressAlpha();
        this.expandPalette = decodeParam.getExpandPalette();
        this.output8BitGray = decodeParam.getOutput8BitGray();
        this.expandGrayAlpha = decodeParam.getExpandGrayAlpha();
        if (decodeParam.getPerformGammaCorrection()) {
            this.userExponent = decodeParam.getUserExponent();
            this.displayExponent = decodeParam.getDisplayExponent();
            this.performGammaCorrection = true;
            this.output8BitGray = true;
        }
        this.generateEncodeParam = decodeParam.getGenerateEncodeParam();
        if (this.emitProperties) {
            this.properties.put("file_type", "PNG v. 1.0");
        }
        Label_0592: {
            try {
                if (dataInputStream.readLong() != -8552249625308161526L) {
                    throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder0"));
                }
                break Label_0592;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder1"));
            }
            try {
                while (true) {
                    final String chunkType = getChunkType(dataInputStream);
                    if (chunkType.equals("IHDR")) {
                        this.parse_IHDR_chunk(readChunk(dataInputStream));
                    }
                    else if (chunkType.equals("PLTE")) {
                        this.parse_PLTE_chunk(readChunk(dataInputStream));
                    }
                    else if (chunkType.equals("IDAT")) {
                        this.streamVec.add(new ByteArrayInputStream(readChunk(dataInputStream).getData()));
                    }
                    else {
                        if (chunkType.equals("IEND")) {
                            break;
                        }
                        if (chunkType.equals("bKGD")) {
                            this.parse_bKGD_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("cHRM")) {
                            this.parse_cHRM_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("gAMA")) {
                            this.parse_gAMA_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("hIST")) {
                            this.parse_hIST_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("iCCP")) {
                            this.parse_iCCP_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("pHYs")) {
                            this.parse_pHYs_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("sBIT")) {
                            this.parse_sBIT_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("sRGB")) {
                            this.parse_sRGB_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("tEXt")) {
                            this.parse_tEXt_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("tIME")) {
                            this.parse_tIME_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("tRNS")) {
                            this.parse_tRNS_chunk(readChunk(dataInputStream));
                        }
                        else if (chunkType.equals("zTXt")) {
                            this.parse_zTXt_chunk(readChunk(dataInputStream));
                        }
                        else {
                            final PNGChunk chunk = readChunk(dataInputStream);
                            final String typeString = chunk.getTypeString();
                            final byte[] data = chunk.getData();
                            if (this.encodeParam != null) {
                                this.encodeParam.addPrivateChunk(typeString, data);
                            }
                            if (!this.emitProperties) {
                                continue;
                            }
                            this.properties.put(("chunk_" + this.chunkIndex++ + ':' + typeString).toLowerCase(), data);
                        }
                    }
                }
                this.parse_IEND_chunk(readChunk(dataInputStream));
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
                throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder2"));
            }
        }
        if (this.significantBits == null) {
            this.significantBits = new int[this.inputBands];
            for (int i = 0; i < this.inputBands; ++i) {
                this.significantBits[i] = this.bitDepth;
            }
            if (this.emitProperties) {
                this.properties.put("significant_bits", this.significantBits);
            }
        }
    }
    
    private static String getChunkType(final DataInputStream dataInputStream) {
        try {
            dataInputStream.mark(8);
            dataInputStream.readInt();
            final int int1 = dataInputStream.readInt();
            dataInputStream.reset();
            return "" + (char)(int1 >> 24 & 0xFF) + (char)(int1 >> 16 & 0xFF) + (char)(int1 >> 8 & 0xFF) + (char)(int1 & 0xFF);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static PNGChunk readChunk(final DataInputStream dataInputStream) {
        try {
            final int int1 = dataInputStream.readInt();
            final int int2 = dataInputStream.readInt();
            final byte[] b = new byte[int1];
            dataInputStream.readFully(b);
            return new PNGChunk(int1, int2, b, dataInputStream.readInt());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private void parse_IHDR_chunk(final PNGChunk pngChunk) {
        this.bounds = new Rectangle(0, 0, pngChunk.getInt4(0), pngChunk.getInt4(4));
        this.bitDepth = pngChunk.getInt1(8);
        if ((1 << this.bitDepth & 0x10116) == 0x0) {
            throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder3"));
        }
        this.maxOpacity = (1 << this.bitDepth) - 1;
        this.colorType = pngChunk.getInt1(9);
        if (this.colorType != 0 && this.colorType != 2 && this.colorType != 3 && this.colorType != 4 && this.colorType != 6) {
            System.out.println(PropertyUtil.getString("PNGImageDecoder4"));
        }
        if (this.colorType == 2 && this.bitDepth < 8) {
            throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder5"));
        }
        if (this.colorType == 3 && this.bitDepth == 16) {
            throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder6"));
        }
        if (this.colorType == 4 && this.bitDepth < 8) {
            throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder7"));
        }
        if (this.colorType == 6 && this.bitDepth < 8) {
            throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder8"));
        }
        if (this.emitProperties) {
            this.properties.put("color_type", PNGRed.colorTypeNames[this.colorType]);
        }
        if (this.generateEncodeParam) {
            if (this.colorType == 3) {
                this.encodeParam = new PNGEncodeParam.Palette();
            }
            else if (this.colorType == 0 || this.colorType == 4) {
                this.encodeParam = new PNGEncodeParam.Gray();
            }
            else {
                this.encodeParam = new PNGEncodeParam.RGB();
            }
            this.decodeParam.setEncodeParam(this.encodeParam);
        }
        if (this.encodeParam != null) {
            this.encodeParam.setBitDepth(this.bitDepth);
        }
        if (this.emitProperties) {
            this.properties.put("bit_depth", new Integer(this.bitDepth));
        }
        if (this.performGammaCorrection) {
            final float n = 0.45454544f * (this.displayExponent / this.userExponent);
            if (this.encodeParam != null) {
                this.encodeParam.setGamma(n);
            }
            if (this.emitProperties) {
                this.properties.put("gamma", new Float(n));
            }
        }
        this.compressionMethod = pngChunk.getInt1(10);
        if (this.compressionMethod != 0) {
            throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder9"));
        }
        this.filterMethod = pngChunk.getInt1(11);
        if (this.filterMethod != 0) {
            throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder10"));
        }
        this.interlaceMethod = pngChunk.getInt1(12);
        if (this.interlaceMethod == 0) {
            if (this.encodeParam != null) {
                this.encodeParam.setInterlacing(false);
            }
            if (this.emitProperties) {
                this.properties.put("interlace_method", "None");
            }
        }
        else {
            if (this.interlaceMethod != 1) {
                throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder11"));
            }
            if (this.encodeParam != null) {
                this.encodeParam.setInterlacing(true);
            }
            if (this.emitProperties) {
                this.properties.put("interlace_method", "Adam7");
            }
        }
        this.bytesPerPixel = ((this.bitDepth == 16) ? 2 : 1);
        switch (this.colorType) {
            case 0: {
                this.inputBands = 1;
                this.outputBands = 1;
                if (this.output8BitGray && this.bitDepth < 8) {
                    this.postProcess = 2;
                    break;
                }
                if (this.performGammaCorrection) {
                    this.postProcess = 1;
                    break;
                }
                this.postProcess = 0;
                break;
            }
            case 2: {
                this.inputBands = 3;
                this.bytesPerPixel *= 3;
                this.outputBands = 3;
                if (this.performGammaCorrection) {
                    this.postProcess = 1;
                    break;
                }
                this.postProcess = 0;
                break;
            }
            case 3: {
                this.inputBands = 1;
                this.bytesPerPixel = 1;
                this.outputBands = (this.expandPalette ? 3 : 1);
                if (this.expandPalette) {
                    this.postProcess = 4;
                    break;
                }
                this.postProcess = 0;
                break;
            }
            case 4: {
                this.inputBands = 2;
                this.bytesPerPixel *= 2;
                if (this.suppressAlpha) {
                    this.outputBands = 1;
                    this.postProcess = 8;
                    break;
                }
                if (this.performGammaCorrection) {
                    this.postProcess = 1;
                }
                else {
                    this.postProcess = 0;
                }
                if (this.expandGrayAlpha) {
                    this.postProcess |= 0x10;
                    this.outputBands = 4;
                    break;
                }
                this.outputBands = 2;
                break;
            }
            case 6: {
                this.inputBands = 4;
                this.bytesPerPixel *= 4;
                this.outputBands = (this.suppressAlpha ? 3 : 4);
                if (this.suppressAlpha) {
                    this.postProcess = 9;
                    break;
                }
                if (this.performGammaCorrection) {
                    this.postProcess = 1;
                    break;
                }
                this.postProcess = 0;
                break;
            }
        }
    }
    
    private void parse_IEND_chunk(final PNGChunk pngChunk) throws Exception {
        final int size = this.textKeys.size();
        final String[] text = new String[2 * size];
        for (int i = 0; i < size; ++i) {
            final String str = this.textKeys.get(i);
            final String s = this.textStrings.get(i);
            text[2 * i] = str;
            text[2 * i + 1] = s;
            if (this.emitProperties) {
                this.properties.put(("text_" + i + ':' + str).toLowerCase(), s);
            }
        }
        if (this.encodeParam != null) {
            this.encodeParam.setText(text);
        }
        final int size2 = this.ztextKeys.size();
        final String[] compressedText = new String[2 * size2];
        for (int j = 0; j < size2; ++j) {
            final String str2 = this.ztextKeys.get(j);
            final String s2 = this.ztextStrings.get(j);
            compressedText[2 * j] = str2;
            compressedText[2 * j + 1] = s2;
            if (this.emitProperties) {
                this.properties.put(("ztext_" + j + ':' + str2).toLowerCase(), s2);
            }
        }
        if (this.encodeParam != null) {
            this.encodeParam.setCompressedText(compressedText);
        }
        this.dataStream = new DataInputStream(new InflaterInputStream(new SequenceInputStream((Enumeration<? extends InputStream>)Collections.enumeration((Collection<Object>)this.streamVec)), new Inflater()));
        int bitDepth = this.bitDepth;
        if (this.colorType == 0 && this.bitDepth < 8 && this.output8BitGray) {
            bitDepth = 8;
        }
        if (this.colorType == 3 && this.expandPalette) {
            bitDepth = 8;
        }
        final int width = this.bounds.width;
        final int height = this.bounds.height;
        final int n = (this.outputBands * width * bitDepth + 7) / 8;
        this.theTile = this.createRaster(width, height, this.outputBands, (bitDepth == 16) ? (n / 2) : n, bitDepth);
        if (this.performGammaCorrection && this.gammaLut == null) {
            this.initGammaLut(this.bitDepth);
        }
        if (this.postProcess == 2 || this.postProcess == 3 || this.postProcess == 19) {
            this.initGrayLut(this.bitDepth);
        }
        this.decodeImage(this.interlaceMethod == 1);
        final SampleModel sampleModel = this.theTile.getSampleModel();
        ColorModel componentColorModel;
        if (this.colorType == 3 && !this.expandPalette) {
            if (this.outputHasAlphaPalette) {
                componentColorModel = new IndexColorModel(this.bitDepth, this.paletteEntries, this.redPalette, this.greenPalette, this.bluePalette, this.alphaPalette);
            }
            else {
                componentColorModel = new IndexColorModel(this.bitDepth, this.paletteEntries, this.redPalette, this.greenPalette, this.bluePalette);
            }
        }
        else if (this.colorType == 0 && this.bitDepth < 8 && !this.output8BitGray) {
            final byte[] array = this.expandBits[this.bitDepth];
            componentColorModel = new IndexColorModel(this.bitDepth, array.length, array, array, array);
        }
        else {
            componentColorModel = createComponentColorModel(sampleModel);
        }
        this.init((CachableRed)null, this.bounds, componentColorModel, sampleModel, 0, 0, this.properties);
    }
    
    public static ColorModel createComponentColorModel(final SampleModel sampleModel) {
        final int dataType = sampleModel.getDataType();
        final int numBands = sampleModel.getNumBands();
        ColorModel colorModel = null;
        if (dataType == 0) {
            switch (numBands) {
                case 1: {
                    colorModel = PNGRed.colorModelGray8;
                    break;
                }
                case 2: {
                    colorModel = PNGRed.colorModelGrayAlpha8;
                    break;
                }
                case 3: {
                    colorModel = PNGRed.colorModelRGB8;
                    break;
                }
                case 4: {
                    colorModel = PNGRed.colorModelRGBA8;
                    break;
                }
            }
        }
        else if (dataType == 1) {
            switch (numBands) {
                case 1: {
                    colorModel = PNGRed.colorModelGray16;
                    break;
                }
                case 2: {
                    colorModel = PNGRed.colorModelGrayAlpha16;
                    break;
                }
                case 3: {
                    colorModel = PNGRed.colorModelRGB16;
                    break;
                }
                case 4: {
                    colorModel = PNGRed.colorModelRGBA16;
                    break;
                }
            }
        }
        else if (dataType == 3) {
            switch (numBands) {
                case 1: {
                    colorModel = PNGRed.colorModelGray32;
                    break;
                }
                case 2: {
                    colorModel = PNGRed.colorModelGrayAlpha32;
                    break;
                }
                case 3: {
                    colorModel = PNGRed.colorModelRGB32;
                    break;
                }
                case 4: {
                    colorModel = PNGRed.colorModelRGBA32;
                    break;
                }
            }
        }
        return colorModel;
    }
    
    private void parse_PLTE_chunk(final PNGChunk pngChunk) {
        this.paletteEntries = pngChunk.getLength() / 3;
        this.redPalette = new byte[this.paletteEntries];
        this.greenPalette = new byte[this.paletteEntries];
        this.bluePalette = new byte[this.paletteEntries];
        int n = 0;
        if (this.performGammaCorrection) {
            if (this.gammaLut == null) {
                this.initGammaLut((this.bitDepth == 16) ? 16 : 8);
            }
            for (int i = 0; i < this.paletteEntries; ++i) {
                final byte byte1 = pngChunk.getByte(n++);
                final byte byte2 = pngChunk.getByte(n++);
                final byte byte3 = pngChunk.getByte(n++);
                this.redPalette[i] = (byte)this.gammaLut[byte1 & 0xFF];
                this.greenPalette[i] = (byte)this.gammaLut[byte2 & 0xFF];
                this.bluePalette[i] = (byte)this.gammaLut[byte3 & 0xFF];
            }
        }
        else {
            for (int j = 0; j < this.paletteEntries; ++j) {
                this.redPalette[j] = pngChunk.getByte(n++);
                this.greenPalette[j] = pngChunk.getByte(n++);
                this.bluePalette[j] = pngChunk.getByte(n++);
            }
        }
    }
    
    private void parse_bKGD_chunk(final PNGChunk pngChunk) {
        switch (this.colorType) {
            case 3: {
                final int backgroundPaletteIndex = pngChunk.getByte(0) & 0xFF;
                this.bkgdRed = (this.redPalette[backgroundPaletteIndex] & 0xFF);
                this.bkgdGreen = (this.greenPalette[backgroundPaletteIndex] & 0xFF);
                this.bkgdBlue = (this.bluePalette[backgroundPaletteIndex] & 0xFF);
                if (this.encodeParam != null) {
                    ((PNGEncodeParam.Palette)this.encodeParam).setBackgroundPaletteIndex(backgroundPaletteIndex);
                    break;
                }
                break;
            }
            case 0:
            case 4: {
                final int int2;
                final int backgroundGray = int2 = pngChunk.getInt2(0);
                this.bkgdBlue = int2;
                this.bkgdGreen = int2;
                this.bkgdRed = int2;
                if (this.encodeParam != null) {
                    ((PNGEncodeParam.Gray)this.encodeParam).setBackgroundGray(backgroundGray);
                    break;
                }
                break;
            }
            case 2:
            case 6: {
                this.bkgdRed = pngChunk.getInt2(0);
                this.bkgdGreen = pngChunk.getInt2(2);
                this.bkgdBlue = pngChunk.getInt2(4);
                final int[] backgroundRGB = { this.bkgdRed, this.bkgdGreen, this.bkgdBlue };
                if (this.encodeParam != null) {
                    ((PNGEncodeParam.RGB)this.encodeParam).setBackgroundRGB(backgroundRGB);
                    break;
                }
                break;
            }
        }
        if (this.emitProperties) {
            int bkgdRed = 0;
            int bkgdGreen = 0;
            int bkgdBlue = 0;
            if (this.colorType == 3 || this.bitDepth == 8) {
                bkgdRed = this.bkgdRed;
                bkgdGreen = this.bkgdGreen;
                bkgdBlue = this.bkgdBlue;
            }
            else if (this.bitDepth < 8) {
                bkgdRed = this.expandBits[this.bitDepth][this.bkgdRed];
                bkgdGreen = this.expandBits[this.bitDepth][this.bkgdGreen];
                bkgdBlue = this.expandBits[this.bitDepth][this.bkgdBlue];
            }
            else if (this.bitDepth == 16) {
                bkgdRed = this.bkgdRed >> 8;
                bkgdGreen = this.bkgdGreen >> 8;
                bkgdBlue = this.bkgdBlue >> 8;
            }
            this.properties.put("background_color", new Color(bkgdRed, bkgdGreen, bkgdBlue));
        }
    }
    
    private void parse_cHRM_chunk(final PNGChunk pngChunk) {
        if (this.sRGBRenderingIntent != -1) {
            return;
        }
        (this.chromaticity = new float[8])[0] = pngChunk.getInt4(0) / 100000.0f;
        this.chromaticity[1] = pngChunk.getInt4(4) / 100000.0f;
        this.chromaticity[2] = pngChunk.getInt4(8) / 100000.0f;
        this.chromaticity[3] = pngChunk.getInt4(12) / 100000.0f;
        this.chromaticity[4] = pngChunk.getInt4(16) / 100000.0f;
        this.chromaticity[5] = pngChunk.getInt4(20) / 100000.0f;
        this.chromaticity[6] = pngChunk.getInt4(24) / 100000.0f;
        this.chromaticity[7] = pngChunk.getInt4(28) / 100000.0f;
        if (this.encodeParam != null) {
            this.encodeParam.setChromaticity(this.chromaticity);
        }
        if (this.emitProperties) {
            this.properties.put("white_point_x", new Float(this.chromaticity[0]));
            this.properties.put("white_point_y", new Float(this.chromaticity[1]));
            this.properties.put("red_x", new Float(this.chromaticity[2]));
            this.properties.put("red_y", new Float(this.chromaticity[3]));
            this.properties.put("green_x", new Float(this.chromaticity[4]));
            this.properties.put("green_y", new Float(this.chromaticity[5]));
            this.properties.put("blue_x", new Float(this.chromaticity[6]));
            this.properties.put("blue_y", new Float(this.chromaticity[7]));
        }
    }
    
    private void parse_gAMA_chunk(final PNGChunk pngChunk) {
        if (this.sRGBRenderingIntent != -1) {
            return;
        }
        this.fileGamma = pngChunk.getInt4(0) / 100000.0f;
        final float n = this.performGammaCorrection ? (this.displayExponent / this.userExponent) : 1.0f;
        if (this.encodeParam != null) {
            this.encodeParam.setGamma(this.fileGamma * n);
        }
        if (this.emitProperties) {
            this.properties.put("gamma", new Float(this.fileGamma * n));
        }
    }
    
    private void parse_hIST_chunk(final PNGChunk pngChunk) {
        if (this.redPalette == null) {
            throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder18"));
        }
        final int length = this.redPalette.length;
        final int[] paletteHistogram = new int[length];
        for (int i = 0; i < length; ++i) {
            paletteHistogram[i] = pngChunk.getInt2(2 * i);
        }
        if (this.encodeParam != null) {
            this.encodeParam.setPaletteHistogram(paletteHistogram);
        }
    }
    
    private void parse_iCCP_chunk(final PNGChunk pngChunk) {
        String string = "";
        int n = 0;
        byte byte1;
        while ((byte1 = pngChunk.getByte(n++)) != 0) {
            string += (char)byte1;
        }
    }
    
    private void parse_pHYs_chunk(final PNGChunk pngChunk) {
        final int int4 = pngChunk.getInt4(0);
        final int int5 = pngChunk.getInt4(4);
        final int int6 = pngChunk.getInt1(8);
        if (this.encodeParam != null) {
            this.encodeParam.setPhysicalDimension(int4, int5, int6);
        }
        if (this.emitProperties) {
            this.properties.put("x_pixels_per_unit", new Integer(int4));
            this.properties.put("y_pixels_per_unit", new Integer(int5));
            this.properties.put("pixel_aspect_ratio", new Float(int4 / (float)int5));
            if (int6 == 1) {
                this.properties.put("pixel_units", "Meters");
            }
            else if (int6 != 0) {
                throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder12"));
            }
        }
    }
    
    private void parse_sBIT_chunk(final PNGChunk pngChunk) {
        if (this.colorType == 3) {
            this.significantBits = new int[3];
        }
        else {
            this.significantBits = new int[this.inputBands];
        }
        for (int i = 0; i < this.significantBits.length; ++i) {
            final byte byte1 = pngChunk.getByte(i);
            final int n = (this.colorType == 3) ? 8 : this.bitDepth;
            if (byte1 <= 0 || byte1 > n) {
                throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder13"));
            }
            this.significantBits[i] = byte1;
        }
        if (this.encodeParam != null) {
            this.encodeParam.setSignificantBits(this.significantBits);
        }
        if (this.emitProperties) {
            this.properties.put("significant_bits", this.significantBits);
        }
    }
    
    private void parse_sRGB_chunk(final PNGChunk pngChunk) {
        this.sRGBRenderingIntent = pngChunk.getByte(0);
        this.fileGamma = 0.45455f;
        (this.chromaticity = new float[8])[0] = 3.127f;
        this.chromaticity[1] = 3.29f;
        this.chromaticity[2] = 6.4f;
        this.chromaticity[3] = 3.3f;
        this.chromaticity[4] = 3.0f;
        this.chromaticity[5] = 6.0f;
        this.chromaticity[6] = 1.5f;
        this.chromaticity[7] = 0.6f;
        if (this.performGammaCorrection) {
            final float n = this.fileGamma * (this.displayExponent / this.userExponent);
            if (this.encodeParam != null) {
                this.encodeParam.setGamma(n);
                this.encodeParam.setChromaticity(this.chromaticity);
            }
            if (this.emitProperties) {
                this.properties.put("gamma", new Float(n));
                this.properties.put("white_point_x", new Float(this.chromaticity[0]));
                this.properties.put("white_point_y", new Float(this.chromaticity[1]));
                this.properties.put("red_x", new Float(this.chromaticity[2]));
                this.properties.put("red_y", new Float(this.chromaticity[3]));
                this.properties.put("green_x", new Float(this.chromaticity[4]));
                this.properties.put("green_y", new Float(this.chromaticity[5]));
                this.properties.put("blue_x", new Float(this.chromaticity[6]));
                this.properties.put("blue_y", new Float(this.chromaticity[7]));
            }
        }
    }
    
    private void parse_tEXt_chunk(final PNGChunk pngChunk) {
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer();
        int n = 0;
        byte byte1;
        while ((byte1 = pngChunk.getByte(n++)) != 0) {
            sb.append((char)byte1);
        }
        for (int i = n; i < pngChunk.getLength(); ++i) {
            sb2.append((char)pngChunk.getByte(i));
        }
        this.textKeys.add(sb.toString());
        this.textStrings.add(sb2.toString());
    }
    
    private void parse_tIME_chunk(final PNGChunk pngChunk) {
        final int int2 = pngChunk.getInt2(0);
        final int month = pngChunk.getInt1(2) - 1;
        final int int3 = pngChunk.getInt1(3);
        final int int4 = pngChunk.getInt1(4);
        final int int5 = pngChunk.getInt1(5);
        final int int6 = pngChunk.getInt1(6);
        final GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        gregorianCalendar.set(int2, month, int3, int4, int5, int6);
        final Date time = gregorianCalendar.getTime();
        if (this.encodeParam != null) {
            this.encodeParam.setModificationTime(time);
        }
        if (this.emitProperties) {
            this.properties.put("timestamp", time);
        }
    }
    
    private void parse_tRNS_chunk(final PNGChunk pngChunk) {
        if (this.colorType == 3) {
            final int length = pngChunk.getLength();
            if (length > this.paletteEntries) {
                throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder14"));
            }
            this.alphaPalette = new byte[this.paletteEntries];
            for (int i = 0; i < length; ++i) {
                this.alphaPalette[i] = pngChunk.getByte(i);
            }
            for (int j = length; j < this.paletteEntries; ++j) {
                this.alphaPalette[j] = -1;
            }
            if (!this.suppressAlpha) {
                if (this.expandPalette) {
                    this.postProcess = 5;
                    this.outputBands = 4;
                }
                else {
                    this.outputHasAlphaPalette = true;
                }
            }
        }
        else if (this.colorType == 0) {
            this.grayTransparentAlpha = pngChunk.getInt2(0);
            if (!this.suppressAlpha) {
                if (this.bitDepth < 8) {
                    this.output8BitGray = true;
                    this.maxOpacity = 255;
                    this.postProcess = 3;
                }
                else {
                    this.postProcess = 6;
                }
                if (this.expandGrayAlpha) {
                    this.outputBands = 4;
                    this.postProcess |= 0x10;
                }
                else {
                    this.outputBands = 2;
                }
                if (this.encodeParam != null) {
                    ((PNGEncodeParam.Gray)this.encodeParam).setTransparentGray(this.grayTransparentAlpha);
                }
            }
        }
        else if (this.colorType == 2) {
            this.redTransparentAlpha = pngChunk.getInt2(0);
            this.greenTransparentAlpha = pngChunk.getInt2(2);
            this.blueTransparentAlpha = pngChunk.getInt2(4);
            if (!this.suppressAlpha) {
                this.outputBands = 4;
                this.postProcess = 7;
                if (this.encodeParam != null) {
                    ((PNGEncodeParam.RGB)this.encodeParam).setTransparentRGB(new int[] { this.redTransparentAlpha, this.greenTransparentAlpha, this.blueTransparentAlpha });
                }
            }
        }
        else if (this.colorType == 4 || this.colorType == 6) {
            throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder15"));
        }
    }
    
    private void parse_zTXt_chunk(final PNGChunk pngChunk) {
        final StringBuffer sb = new StringBuffer();
        final StringBuffer sb2 = new StringBuffer();
        int offset = 0;
        byte byte1;
        while ((byte1 = pngChunk.getByte(offset++)) != 0) {
            sb.append((char)byte1);
        }
        pngChunk.getByte(offset++);
        try {
            int read;
            while ((read = new InflaterInputStream(new ByteArrayInputStream(pngChunk.getData(), offset, pngChunk.getLength() - offset)).read()) != -1) {
                sb2.append((char)read);
            }
            this.ztextKeys.add(sb.toString());
            this.ztextStrings.add(sb2.toString());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private WritableRaster createRaster(final int w, final int h, final int n, final int n2, final int bitsPerPixel) {
        final Point location = new Point(0, 0);
        WritableRaster writableRaster;
        if (bitsPerPixel < 8 && n == 1) {
            writableRaster = Raster.createPackedRaster(new DataBufferByte(h * n2), w, h, bitsPerPixel, location);
        }
        else if (bitsPerPixel <= 8) {
            writableRaster = Raster.createInterleavedRaster(new DataBufferByte(h * n2), w, h, n2, n, this.bandOffsets[n], location);
        }
        else {
            writableRaster = Raster.createInterleavedRaster(new DataBufferUShort(h * n2), w, h, n2, n, this.bandOffsets[n], location);
        }
        return writableRaster;
    }
    
    private static void decodeSubFilter(final byte[] array, final int n, final int n2) {
        for (int i = n2; i < n; ++i) {
            array[i] = (byte)((array[i] & 0xFF) + (array[i - n2] & 0xFF));
        }
    }
    
    private static void decodeUpFilter(final byte[] array, final byte[] array2, final int n) {
        for (int i = 0; i < n; ++i) {
            array[i] = (byte)((array[i] & 0xFF) + (array2[i] & 0xFF));
        }
    }
    
    private static void decodeAverageFilter(final byte[] array, final byte[] array2, final int n, final int n2) {
        for (int i = 0; i < n2; ++i) {
            array[i] = (byte)((array[i] & 0xFF) + (array2[i] & 0xFF) / 2);
        }
        for (int j = n2; j < n; ++j) {
            array[j] = (byte)((array[j] & 0xFF) + ((array[j - n2] & 0xFF) + (array2[j] & 0xFF)) / 2);
        }
    }
    
    private static int paethPredictor(final int n, final int n2, final int n3) {
        final int n4 = n + n2 - n3;
        final int abs = Math.abs(n4 - n);
        final int abs2 = Math.abs(n4 - n2);
        final int abs3 = Math.abs(n4 - n3);
        if (abs <= abs2 && abs <= abs3) {
            return n;
        }
        if (abs2 <= abs3) {
            return n2;
        }
        return n3;
    }
    
    private static void decodePaethFilter(final byte[] array, final byte[] array2, final int n, final int n2) {
        for (int i = 0; i < n2; ++i) {
            array[i] = (byte)((array[i] & 0xFF) + (array2[i] & 0xFF));
        }
        for (int j = n2; j < n; ++j) {
            array[j] = (byte)((array[j] & 0xFF) + paethPredictor(array[j - n2] & 0xFF, array2[j] & 0xFF, array2[j - n2] & 0xFF));
        }
    }
    
    private void processPixels(final int n, final Raster raster, final WritableRaster writableRaster, final int n2, final int n3, final int n4, final int n5) {
        final int[] pixel = raster.getPixel(0, 0, (int[])null);
        final int[] pixel2 = writableRaster.getPixel(0, 0, (int[])null);
        int n6 = n2;
        switch (n) {
            case 0: {
                for (int i = 0; i < n5; ++i) {
                    raster.getPixel(i, 0, pixel);
                    writableRaster.setPixel(n6, n4, pixel);
                    n6 += n3;
                }
                break;
            }
            case 1: {
                for (int j = 0; j < n5; ++j) {
                    raster.getPixel(j, 0, pixel);
                    for (int k = 0; k < this.inputBands; ++k) {
                        pixel[k] = this.gammaLut[pixel[k]];
                    }
                    writableRaster.setPixel(n6, n4, pixel);
                    n6 += n3;
                }
                break;
            }
            case 2: {
                for (int l = 0; l < n5; ++l) {
                    raster.getPixel(l, 0, pixel);
                    pixel2[0] = this.grayLut[pixel[0]];
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 3: {
                for (int x = 0; x < n5; ++x) {
                    raster.getPixel(x, 0, pixel);
                    final int n7 = pixel[0];
                    pixel2[0] = this.grayLut[n7];
                    if (n7 == this.grayTransparentAlpha) {
                        pixel2[1] = 0;
                    }
                    else {
                        pixel2[1] = this.maxOpacity;
                    }
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 4: {
                for (int x2 = 0; x2 < n5; ++x2) {
                    raster.getPixel(x2, 0, pixel);
                    final int n8 = pixel[0];
                    pixel2[0] = this.redPalette[n8];
                    pixel2[1] = this.greenPalette[n8];
                    pixel2[2] = this.bluePalette[n8];
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 5: {
                for (int x3 = 0; x3 < n5; ++x3) {
                    raster.getPixel(x3, 0, pixel);
                    final int n9 = pixel[0];
                    pixel2[0] = this.redPalette[n9];
                    pixel2[1] = this.greenPalette[n9];
                    pixel2[2] = this.bluePalette[n9];
                    pixel2[3] = this.alphaPalette[n9];
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 6: {
                for (int x4 = 0; x4 < n5; ++x4) {
                    raster.getPixel(x4, 0, pixel);
                    int n10 = pixel[0];
                    if (this.performGammaCorrection) {
                        n10 = this.gammaLut[n10];
                    }
                    if ((pixel2[0] = n10) == this.grayTransparentAlpha) {
                        pixel2[1] = 0;
                    }
                    else {
                        pixel2[1] = this.maxOpacity;
                    }
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 7: {
                final boolean performGammaCorrection = this.performGammaCorrection;
                final int[] gammaLut = this.gammaLut;
                for (int x5 = 0; x5 < n5; ++x5) {
                    raster.getPixel(x5, 0, pixel);
                    final int n11 = pixel[0];
                    final int n12 = pixel[1];
                    final int n13 = pixel[2];
                    if (performGammaCorrection) {
                        pixel2[0] = gammaLut[n11];
                        pixel2[1] = gammaLut[n12];
                        pixel2[2] = gammaLut[n13];
                    }
                    else {
                        pixel2[0] = n11;
                        pixel2[1] = n12;
                        pixel2[2] = n13;
                    }
                    if (n11 == this.redTransparentAlpha && n12 == this.greenTransparentAlpha && n13 == this.blueTransparentAlpha) {
                        pixel2[3] = 0;
                    }
                    else {
                        pixel2[3] = this.maxOpacity;
                    }
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 8: {
                for (int x6 = 0; x6 < n5; ++x6) {
                    raster.getPixel(x6, 0, pixel);
                    final int n14 = pixel[0];
                    if (this.performGammaCorrection) {
                        pixel2[0] = this.gammaLut[n14];
                    }
                    else {
                        pixel2[0] = n14;
                    }
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 9: {
                for (int x7 = 0; x7 < n5; ++x7) {
                    raster.getPixel(x7, 0, pixel);
                    final int n15 = pixel[0];
                    final int n16 = pixel[1];
                    final int n17 = pixel[2];
                    if (this.performGammaCorrection) {
                        pixel2[0] = this.gammaLut[n15];
                        pixel2[1] = this.gammaLut[n16];
                        pixel2[2] = this.gammaLut[n17];
                    }
                    else {
                        pixel2[0] = n15;
                        pixel2[1] = n16;
                        pixel2[2] = n17;
                    }
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 17: {
                for (int x8 = 0; x8 < n5; ++x8) {
                    raster.getPixel(x8, 0, pixel);
                    final int n18 = pixel[0];
                    final int n19 = pixel[1];
                    final int n20 = this.gammaLut[n18];
                    pixel2[0] = n20;
                    pixel2[2] = (pixel2[1] = n20);
                    pixel2[3] = n19;
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 16: {
                for (int x9 = 0; x9 < n5; ++x9) {
                    raster.getPixel(x9, 0, pixel);
                    final int n21 = pixel[0];
                    final int n22 = pixel[1];
                    pixel2[0] = n21;
                    pixel2[2] = (pixel2[1] = n21);
                    pixel2[3] = n22;
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 22: {
                for (int x10 = 0; x10 < n5; ++x10) {
                    raster.getPixel(x10, 0, pixel);
                    int n23 = pixel[0];
                    if (this.performGammaCorrection) {
                        n23 = this.gammaLut[n23];
                    }
                    pixel2[1] = (pixel2[0] = n23);
                    if ((pixel2[2] = n23) == this.grayTransparentAlpha) {
                        pixel2[3] = 0;
                    }
                    else {
                        pixel2[3] = this.maxOpacity;
                    }
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
            case 19: {
                for (int x11 = 0; x11 < n5; ++x11) {
                    raster.getPixel(x11, 0, pixel);
                    final int n24 = pixel[0];
                    final int n25 = this.grayLut[n24];
                    pixel2[0] = n25;
                    pixel2[2] = (pixel2[1] = n25);
                    if (n24 == this.grayTransparentAlpha) {
                        pixel2[3] = 0;
                    }
                    else {
                        pixel2[3] = this.maxOpacity;
                    }
                    writableRaster.setPixel(n6, n4, pixel2);
                    n6 += n3;
                }
                break;
            }
        }
    }
    
    private void decodePass(final WritableRaster writableRaster, final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        if (n5 == 0 || n6 == 0) {
            return;
        }
        final int len = (this.inputBands * n5 * this.bitDepth + 7) / 8;
        final int n7 = (this.bitDepth == 16) ? (len / 2) : len;
        byte[] b = new byte[len];
        byte[] array = new byte[len];
        final WritableRaster raster = this.createRaster(n5, 1, this.inputBands, n7, this.bitDepth);
        final DataBuffer dataBuffer = raster.getDataBuffer();
        final int dataType = dataBuffer.getDataType();
        Object data = null;
        short[] data2 = null;
        if (dataType == 0) {
            data = ((DataBufferByte)dataBuffer).getData();
        }
        else {
            data2 = ((DataBufferUShort)dataBuffer).getData();
        }
        for (int i = 0, n8 = n2; i < n6; ++i, n8 += n4) {
            int read = 0;
            try {
                read = this.dataStream.read();
                this.dataStream.readFully(b, 0, len);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            switch (read) {
                case 0: {
                    break;
                }
                case 1: {
                    decodeSubFilter(b, len, this.bytesPerPixel);
                    break;
                }
                case 2: {
                    decodeUpFilter(b, array, len);
                    break;
                }
                case 3: {
                    decodeAverageFilter(b, array, len, this.bytesPerPixel);
                    break;
                }
                case 4: {
                    decodePaethFilter(b, array, len, this.bytesPerPixel);
                    break;
                }
                default: {
                    throw new RuntimeException(PropertyUtil.getString("PNGImageDecoder16"));
                }
            }
            if (this.bitDepth < 16) {
                System.arraycopy(b, 0, data, 0, len);
            }
            else {
                int n9 = 0;
                for (int j = 0; j < n7; ++j) {
                    data2[j] = (short)(b[n9] << 8 | (b[n9 + 1] & 0xFF));
                    n9 += 2;
                }
            }
            this.processPixels(this.postProcess, raster, writableRaster, n, n3, n8, n5);
            final byte[] array2 = array;
            array = b;
            b = array2;
        }
    }
    
    private void decodeImage(final boolean b) {
        final int width = this.bounds.width;
        final int height = this.bounds.height;
        if (!b) {
            this.decodePass(this.theTile, 0, 0, 1, 1, width, height);
        }
        else {
            this.decodePass(this.theTile, 0, 0, 8, 8, (width + 7) / 8, (height + 7) / 8);
            this.decodePass(this.theTile, 4, 0, 8, 8, (width + 3) / 8, (height + 7) / 8);
            this.decodePass(this.theTile, 0, 4, 4, 8, (width + 3) / 4, (height + 3) / 8);
            this.decodePass(this.theTile, 2, 0, 4, 4, (width + 1) / 4, (height + 3) / 4);
            this.decodePass(this.theTile, 0, 2, 2, 4, (width + 1) / 2, (height + 1) / 4);
            this.decodePass(this.theTile, 1, 0, 2, 2, width / 2, (height + 1) / 2);
            this.decodePass(this.theTile, 0, 1, 1, 2, width, height / 2);
        }
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        GraphicsUtil.copyData(this.theTile, writableRaster);
        return writableRaster;
    }
    
    public Raster getTile(final int n, final int n2) {
        if (n != 0 || n2 != 0) {
            throw new IllegalArgumentException(PropertyUtil.getString("PNGImageDecoder17"));
        }
        return this.theTile;
    }
    
    static {
        colorTypeNames = new String[] { "Grayscale", "Error", "Truecolor", "Index", "Grayscale with alpha", "Error", "Truecolor with alpha" };
        GrayBits8 = new int[] { 8 };
        colorModelGray8 = new ComponentColorModel(ColorSpace.getInstance(1003), PNGRed.GrayBits8, false, false, 1, 0);
        GrayAlphaBits8 = new int[] { 8, 8 };
        colorModelGrayAlpha8 = new ComponentColorModel(ColorSpace.getInstance(1003), PNGRed.GrayAlphaBits8, true, false, 3, 0);
        GrayBits16 = new int[] { 16 };
        colorModelGray16 = new ComponentColorModel(ColorSpace.getInstance(1003), PNGRed.GrayBits16, false, false, 1, 1);
        GrayAlphaBits16 = new int[] { 16, 16 };
        colorModelGrayAlpha16 = new ComponentColorModel(ColorSpace.getInstance(1003), PNGRed.GrayAlphaBits16, true, false, 3, 1);
        GrayBits32 = new int[] { 32 };
        colorModelGray32 = new ComponentColorModel(ColorSpace.getInstance(1003), PNGRed.GrayBits32, false, false, 1, 3);
        GrayAlphaBits32 = new int[] { 32, 32 };
        colorModelGrayAlpha32 = new ComponentColorModel(ColorSpace.getInstance(1003), PNGRed.GrayAlphaBits32, true, false, 3, 3);
        RGBBits8 = new int[] { 8, 8, 8 };
        colorModelRGB8 = new ComponentColorModel(ColorSpace.getInstance(1000), PNGRed.RGBBits8, false, false, 1, 0);
        RGBABits8 = new int[] { 8, 8, 8, 8 };
        colorModelRGBA8 = new ComponentColorModel(ColorSpace.getInstance(1000), PNGRed.RGBABits8, true, false, 3, 0);
        RGBBits16 = new int[] { 16, 16, 16 };
        colorModelRGB16 = new ComponentColorModel(ColorSpace.getInstance(1000), PNGRed.RGBBits16, false, false, 1, 1);
        RGBABits16 = new int[] { 16, 16, 16, 16 };
        colorModelRGBA16 = new ComponentColorModel(ColorSpace.getInstance(1000), PNGRed.RGBABits16, true, false, 3, 1);
        RGBBits32 = new int[] { 32, 32, 32 };
        colorModelRGB32 = new ComponentColorModel(ColorSpace.getInstance(1000), PNGRed.RGBBits32, false, false, 1, 3);
        RGBABits32 = new int[] { 32, 32, 32, 32 };
        colorModelRGBA32 = new ComponentColorModel(ColorSpace.getInstance(1000), PNGRed.RGBABits32, true, false, 3, 3);
    }
    
    static class PNGChunk
    {
        int length;
        int type;
        byte[] data;
        int crc;
        String typeString;
        
        public PNGChunk(final int length, final int type, final byte[] data, final int crc) {
            this.length = length;
            this.type = type;
            this.data = data;
            this.crc = crc;
            this.typeString = "";
            this.typeString += (char)(type >> 24);
            this.typeString += (char)(type >> 16 & 0xFF);
            this.typeString += (char)(type >> 8 & 0xFF);
            this.typeString += (char)(type & 0xFF);
        }
        
        public int getLength() {
            return this.length;
        }
        
        public int getType() {
            return this.type;
        }
        
        public String getTypeString() {
            return this.typeString;
        }
        
        public byte[] getData() {
            return this.data;
        }
        
        public byte getByte(final int n) {
            return this.data[n];
        }
        
        public int getInt1(final int n) {
            return this.data[n] & 0xFF;
        }
        
        public int getInt2(final int n) {
            return (this.data[n] & 0xFF) << 8 | (this.data[n + 1] & 0xFF);
        }
        
        public int getInt4(final int n) {
            return (this.data[n] & 0xFF) << 24 | (this.data[n + 1] & 0xFF) << 16 | (this.data[n + 2] & 0xFF) << 8 | (this.data[n + 3] & 0xFF);
        }
        
        public String getString4(final int n) {
            return new String() + (char)this.data[n] + (char)this.data[n + 1] + (char)this.data[n + 2] + (char)this.data[n + 3];
        }
        
        public boolean isType(final String anObject) {
            return this.typeString.equals(anObject);
        }
    }
}
