// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.awt.image.IndexColorModel;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.awt.Rectangle;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Deflater;
import java.awt.image.Raster;
import java.io.IOException;
import org.apache.batik.ext.awt.image.codec.util.ImageEncodeParam;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.codec.util.ImageEncoderImpl;

public class PNGImageEncoder extends ImageEncoderImpl
{
    private static final int PNG_COLOR_GRAY = 0;
    private static final int PNG_COLOR_RGB = 2;
    private static final int PNG_COLOR_PALETTE = 3;
    private static final int PNG_COLOR_GRAY_ALPHA = 4;
    private static final int PNG_COLOR_RGB_ALPHA = 6;
    private static final byte[] magic;
    private PNGEncodeParam param;
    private RenderedImage image;
    private int width;
    private int height;
    private int bitDepth;
    private int bitShift;
    private int numBands;
    private int colorType;
    private int bpp;
    private boolean skipAlpha;
    private boolean compressGray;
    private boolean interlace;
    private byte[] redPalette;
    private byte[] greenPalette;
    private byte[] bluePalette;
    private byte[] alphaPalette;
    private DataOutputStream dataOutput;
    private byte[] prevRow;
    private byte[] currRow;
    private byte[][] filteredRows;
    private static final float[] srgbChroma;
    
    public PNGImageEncoder(final OutputStream out, final PNGEncodeParam param) {
        super(out, param);
        this.skipAlpha = false;
        this.compressGray = false;
        this.redPalette = null;
        this.greenPalette = null;
        this.bluePalette = null;
        this.alphaPalette = null;
        this.prevRow = null;
        this.currRow = null;
        this.filteredRows = null;
        if (param != null) {
            this.param = param;
        }
        this.dataOutput = new DataOutputStream(out);
    }
    
    private void writeMagic() throws IOException {
        this.dataOutput.write(PNGImageEncoder.magic);
    }
    
    private void writeIHDR() throws IOException {
        final ChunkStream chunkStream = new ChunkStream("IHDR");
        chunkStream.writeInt(this.width);
        chunkStream.writeInt(this.height);
        chunkStream.writeByte((byte)this.bitDepth);
        chunkStream.writeByte((byte)this.colorType);
        chunkStream.writeByte(0);
        chunkStream.writeByte(0);
        chunkStream.writeByte(this.interlace ? 1 : 0);
        chunkStream.writeToStream(this.dataOutput);
        chunkStream.close();
    }
    
    private static int clamp(final int n, final int n2) {
        return (n > n2) ? n2 : n;
    }
    
    private void encodePass(final OutputStream outputStream, final Raster raster, int n, final int n2, int n3, final int n4) throws IOException {
        final int minX = raster.getMinX();
        final int minY = raster.getMinY();
        final int width = raster.getWidth();
        final int height = raster.getHeight();
        n *= this.numBands;
        n3 *= this.numBands;
        final int n5 = 8 / this.bitDepth;
        final int n6 = width * this.numBands;
        final int[] iArray = new int[n6];
        int len = (n6 - n + n3 - 1) / n3 * this.numBands;
        if (this.bitDepth < 8) {
            len = (len + n5 - 1) / n5;
        }
        else if (this.bitDepth == 16) {
            len *= 2;
        }
        if (len == 0) {
            return;
        }
        this.currRow = new byte[len + this.bpp];
        this.prevRow = new byte[len + this.bpp];
        this.filteredRows = new byte[5][len + this.bpp];
        final int n7 = (1 << this.bitDepth) - 1;
        for (int i = minY + n2; i < minY + height; i += n4) {
            raster.getPixels(minX, i, width, 1, iArray);
            if (this.compressGray) {
                final int n8 = 8 - this.bitDepth;
                for (int j = 0; j < width; ++j) {
                    final int[] array = iArray;
                    final int n9 = j;
                    array[n9] >>= n8;
                }
            }
            int bpp = this.bpp;
            int n10 = 0;
            int n11 = 0;
            switch (this.bitDepth) {
                case 1:
                case 2:
                case 4: {
                    final int n12 = n5 - 1;
                    for (int k = n; k < n6; k += n3) {
                        n11 = (n11 << this.bitDepth | clamp(iArray[k] >> this.bitShift, n7));
                        if (n10++ == n12) {
                            this.currRow[bpp++] = (byte)n11;
                            n11 = 0;
                            n10 = 0;
                        }
                    }
                    if (n10 != 0) {
                        this.currRow[bpp++] = (byte)(n11 << (n5 - n10) * this.bitDepth);
                        break;
                    }
                    break;
                }
                case 8: {
                    for (int l = n; l < n6; l += n3) {
                        for (int n13 = 0; n13 < this.numBands; ++n13) {
                            this.currRow[bpp++] = (byte)clamp(iArray[l + n13] >> this.bitShift, n7);
                        }
                    }
                    break;
                }
                case 16: {
                    for (int n14 = n; n14 < n6; n14 += n3) {
                        for (int n15 = 0; n15 < this.numBands; ++n15) {
                            final int clamp = clamp(iArray[n14 + n15] >> this.bitShift, n7);
                            this.currRow[bpp++] = (byte)(clamp >> 8);
                            this.currRow[bpp++] = (byte)(clamp & 0xFF);
                        }
                    }
                    break;
                }
            }
            final int filterRow = this.param.filterRow(this.currRow, this.prevRow, this.filteredRows, len, this.bpp);
            outputStream.write(filterRow);
            outputStream.write(this.filteredRows[filterRow], this.bpp, len);
            final byte[] currRow = this.currRow;
            this.currRow = this.prevRow;
            this.prevRow = currRow;
        }
    }
    
    private void writeIDAT() throws IOException {
        final IDATOutputStream out = new IDATOutputStream(this.dataOutput, 8192);
        final DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(out, new Deflater(9));
        Raster raster = this.image.getData(new Rectangle(this.image.getMinX(), this.image.getMinY(), this.image.getWidth(), this.image.getHeight()));
        if (this.skipAlpha) {
            final int n = raster.getNumBands() - 1;
            final int[] bandList = new int[n];
            for (int i = 0; i < n; ++i) {
                bandList[i] = i;
            }
            raster = raster.createChild(0, 0, raster.getWidth(), raster.getHeight(), 0, 0, bandList);
        }
        if (this.interlace) {
            this.encodePass(deflaterOutputStream, raster, 0, 0, 8, 8);
            this.encodePass(deflaterOutputStream, raster, 4, 0, 8, 8);
            this.encodePass(deflaterOutputStream, raster, 0, 4, 4, 8);
            this.encodePass(deflaterOutputStream, raster, 2, 0, 4, 4);
            this.encodePass(deflaterOutputStream, raster, 0, 2, 2, 4);
            this.encodePass(deflaterOutputStream, raster, 1, 0, 2, 2);
            this.encodePass(deflaterOutputStream, raster, 0, 1, 1, 2);
        }
        else {
            this.encodePass(deflaterOutputStream, raster, 0, 0, 1, 1);
        }
        deflaterOutputStream.finish();
        out.flush();
    }
    
    private void writeIEND() throws IOException {
        final ChunkStream chunkStream = new ChunkStream("IEND");
        chunkStream.writeToStream(this.dataOutput);
        chunkStream.close();
    }
    
    private void writeCHRM() throws IOException {
        if (this.param.isChromaticitySet() || this.param.isSRGBIntentSet()) {
            final ChunkStream chunkStream = new ChunkStream("cHRM");
            float[] array;
            if (!this.param.isSRGBIntentSet()) {
                array = this.param.getChromaticity();
            }
            else {
                array = PNGImageEncoder.srgbChroma;
            }
            for (int i = 0; i < 8; ++i) {
                chunkStream.writeInt((int)(array[i] * 100000.0f));
            }
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writeGAMA() throws IOException {
        if (this.param.isGammaSet() || this.param.isSRGBIntentSet()) {
            final ChunkStream chunkStream = new ChunkStream("gAMA");
            float gamma;
            if (!this.param.isSRGBIntentSet()) {
                gamma = this.param.getGamma();
            }
            else {
                gamma = 0.45454544f;
            }
            chunkStream.writeInt((int)(gamma * 100000.0f));
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writeICCP() throws IOException {
        if (this.param.isICCProfileDataSet()) {
            final ChunkStream chunkStream = new ChunkStream("iCCP");
            chunkStream.write(this.param.getICCProfileData());
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writeSBIT() throws IOException {
        if (this.param.isSignificantBitsSet()) {
            final ChunkStream chunkStream = new ChunkStream("sBIT");
            final int[] significantBits = this.param.getSignificantBits();
            for (int length = significantBits.length, i = 0; i < length; ++i) {
                chunkStream.writeByte(significantBits[i]);
            }
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writeSRGB() throws IOException {
        if (this.param.isSRGBIntentSet()) {
            final ChunkStream chunkStream = new ChunkStream("sRGB");
            chunkStream.write(this.param.getSRGBIntent());
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writePLTE() throws IOException {
        if (this.redPalette == null) {
            return;
        }
        final ChunkStream chunkStream = new ChunkStream("PLTE");
        for (int i = 0; i < this.redPalette.length; ++i) {
            chunkStream.writeByte(this.redPalette[i]);
            chunkStream.writeByte(this.greenPalette[i]);
            chunkStream.writeByte(this.bluePalette[i]);
        }
        chunkStream.writeToStream(this.dataOutput);
        chunkStream.close();
    }
    
    private void writeBKGD() throws IOException {
        if (this.param.isBackgroundSet()) {
            final ChunkStream chunkStream = new ChunkStream("bKGD");
            switch (this.colorType) {
                case 0:
                case 4: {
                    chunkStream.writeShort(((PNGEncodeParam.Gray)this.param).getBackgroundGray());
                    break;
                }
                case 3: {
                    chunkStream.writeByte(((PNGEncodeParam.Palette)this.param).getBackgroundPaletteIndex());
                    break;
                }
                case 2:
                case 6: {
                    final int[] backgroundRGB = ((PNGEncodeParam.RGB)this.param).getBackgroundRGB();
                    chunkStream.writeShort(backgroundRGB[0]);
                    chunkStream.writeShort(backgroundRGB[1]);
                    chunkStream.writeShort(backgroundRGB[2]);
                    break;
                }
            }
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writeHIST() throws IOException {
        if (this.param.isPaletteHistogramSet()) {
            final ChunkStream chunkStream = new ChunkStream("hIST");
            final int[] paletteHistogram = this.param.getPaletteHistogram();
            for (int i = 0; i < paletteHistogram.length; ++i) {
                chunkStream.writeShort(paletteHistogram[i]);
            }
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writeTRNS() throws IOException {
        if (this.param.isTransparencySet() && this.colorType != 4 && this.colorType != 6) {
            final ChunkStream chunkStream = new ChunkStream("tRNS");
            if (this.param instanceof PNGEncodeParam.Palette) {
                final byte[] paletteTransparency = ((PNGEncodeParam.Palette)this.param).getPaletteTransparency();
                for (int i = 0; i < paletteTransparency.length; ++i) {
                    chunkStream.writeByte(paletteTransparency[i]);
                }
            }
            else if (this.param instanceof PNGEncodeParam.Gray) {
                chunkStream.writeShort(((PNGEncodeParam.Gray)this.param).getTransparentGray());
            }
            else if (this.param instanceof PNGEncodeParam.RGB) {
                final int[] transparentRGB = ((PNGEncodeParam.RGB)this.param).getTransparentRGB();
                chunkStream.writeShort(transparentRGB[0]);
                chunkStream.writeShort(transparentRGB[1]);
                chunkStream.writeShort(transparentRGB[2]);
            }
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
        else if (this.colorType == 3) {
            int min;
            for (min = Math.min(255, this.alphaPalette.length - 1); min >= 0 && this.alphaPalette[min] == -1; --min) {}
            if (min >= 0) {
                final ChunkStream chunkStream2 = new ChunkStream("tRNS");
                for (int j = 0; j <= min; ++j) {
                    chunkStream2.writeByte(this.alphaPalette[j]);
                }
                chunkStream2.writeToStream(this.dataOutput);
                chunkStream2.close();
            }
        }
    }
    
    private void writePHYS() throws IOException {
        if (this.param.isPhysicalDimensionSet()) {
            final ChunkStream chunkStream = new ChunkStream("pHYs");
            final int[] physicalDimension = this.param.getPhysicalDimension();
            chunkStream.writeInt(physicalDimension[0]);
            chunkStream.writeInt(physicalDimension[1]);
            chunkStream.writeByte((byte)physicalDimension[2]);
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writeSPLT() throws IOException {
        if (this.param.isSuggestedPaletteSet()) {
            final ChunkStream chunkStream = new ChunkStream("sPLT");
            System.out.println("sPLT not supported yet.");
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writeTIME() throws IOException {
        if (this.param.isModificationTimeSet()) {
            final ChunkStream chunkStream = new ChunkStream("tIME");
            final Date modificationTime = this.param.getModificationTime();
            final GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            gregorianCalendar.setTime(modificationTime);
            final int value = gregorianCalendar.get(1);
            final int value2 = gregorianCalendar.get(2);
            final int value3 = gregorianCalendar.get(5);
            final int value4 = gregorianCalendar.get(11);
            final int value5 = gregorianCalendar.get(12);
            final int value6 = gregorianCalendar.get(13);
            chunkStream.writeShort(value);
            chunkStream.writeByte(value2 + 1);
            chunkStream.writeByte(value3);
            chunkStream.writeByte(value4);
            chunkStream.writeByte(value5);
            chunkStream.writeByte(value6);
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private void writeTEXT() throws IOException {
        if (this.param.isTextSet()) {
            final String[] text = this.param.getText();
            for (int i = 0; i < text.length / 2; ++i) {
                final byte[] bytes = text[2 * i].getBytes();
                final byte[] bytes2 = text[2 * i + 1].getBytes();
                final ChunkStream chunkStream = new ChunkStream("tEXt");
                chunkStream.write(bytes, 0, Math.min(bytes.length, 79));
                chunkStream.write(0);
                chunkStream.write(bytes2);
                chunkStream.writeToStream(this.dataOutput);
                chunkStream.close();
            }
        }
    }
    
    private void writeZTXT() throws IOException {
        if (this.param.isCompressedTextSet()) {
            final String[] compressedText = this.param.getCompressedText();
            for (int i = 0; i < compressedText.length / 2; ++i) {
                final byte[] bytes = compressedText[2 * i].getBytes();
                final byte[] bytes2 = compressedText[2 * i + 1].getBytes();
                final ChunkStream out = new ChunkStream("zTXt");
                out.write(bytes, 0, Math.min(bytes.length, 79));
                out.write(0);
                out.write(0);
                final DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(out);
                deflaterOutputStream.write(bytes2);
                deflaterOutputStream.finish();
                out.writeToStream(this.dataOutput);
                out.close();
            }
        }
    }
    
    private void writePrivateChunks() throws IOException {
        for (int numPrivateChunks = this.param.getNumPrivateChunks(), i = 0; i < numPrivateChunks; ++i) {
            final String privateChunkType = this.param.getPrivateChunkType(i);
            final byte[] privateChunkData = this.param.getPrivateChunkData(i);
            final ChunkStream chunkStream = new ChunkStream(privateChunkType);
            chunkStream.write(privateChunkData);
            chunkStream.writeToStream(this.dataOutput);
            chunkStream.close();
        }
    }
    
    private PNGEncodeParam.Gray createGrayParam(final byte[] array, final byte[] array2, final byte[] array3, final byte[] array4) {
        final PNGEncodeParam.Gray gray = new PNGEncodeParam.Gray();
        int n = 0;
        final int n2 = 255 / ((1 << this.bitDepth) - 1);
        for (int n3 = 1 << this.bitDepth, i = 0; i < n3; ++i) {
            final byte b = array[i];
            if (b != i * n2 || b != array2[i] || b != array3[i]) {
                return null;
            }
            final byte b2 = array4[i];
            if (b2 == 0) {
                gray.setTransparentGray(i);
                if (++n > 1) {
                    return null;
                }
            }
            else if (b2 != -1) {
                return null;
            }
        }
        return gray;
    }
    
    public void encode(final RenderedImage image) throws IOException {
        this.image = image;
        this.width = this.image.getWidth();
        this.height = this.image.getHeight();
        final SampleModel sampleModel = this.image.getSampleModel();
        final int[] sampleSize = sampleModel.getSampleSize();
        this.bitDepth = -1;
        this.bitShift = 0;
        if (this.param instanceof PNGEncodeParam.Gray) {
            final PNGEncodeParam.Gray gray = (PNGEncodeParam.Gray)this.param;
            if (gray.isBitDepthSet()) {
                this.bitDepth = gray.getBitDepth();
            }
            if (gray.isBitShiftSet()) {
                this.bitShift = gray.getBitShift();
            }
        }
        if (this.bitDepth == -1) {
            this.bitDepth = sampleSize[0];
            for (int i = 1; i < sampleSize.length; ++i) {
                if (sampleSize[i] != this.bitDepth) {
                    throw new RuntimeException();
                }
            }
            if (this.bitDepth > 2 && this.bitDepth < 4) {
                this.bitDepth = 4;
            }
            else if (this.bitDepth > 4 && this.bitDepth < 8) {
                this.bitDepth = 8;
            }
            else if (this.bitDepth > 8 && this.bitDepth < 16) {
                this.bitDepth = 16;
            }
            else if (this.bitDepth > 16) {
                throw new RuntimeException();
            }
        }
        this.numBands = sampleModel.getNumBands();
        this.bpp = this.numBands * ((this.bitDepth == 16) ? 2 : 1);
        final ColorModel colorModel = this.image.getColorModel();
        if (colorModel instanceof IndexColorModel) {
            if (this.bitDepth < 1 || this.bitDepth > 8) {
                throw new RuntimeException();
            }
            if (sampleModel.getNumBands() != 1) {
                throw new RuntimeException();
            }
            final IndexColorModel indexColorModel = (IndexColorModel)colorModel;
            final int mapSize = indexColorModel.getMapSize();
            this.redPalette = new byte[mapSize];
            this.greenPalette = new byte[mapSize];
            this.bluePalette = new byte[mapSize];
            this.alphaPalette = new byte[mapSize];
            indexColorModel.getReds(this.redPalette);
            indexColorModel.getGreens(this.greenPalette);
            indexColorModel.getBlues(this.bluePalette);
            indexColorModel.getAlphas(this.alphaPalette);
            this.bpp = 1;
            if (this.param == null) {
                this.param = this.createGrayParam(this.redPalette, this.greenPalette, this.bluePalette, this.alphaPalette);
            }
            if (this.param == null) {
                this.param = new PNGEncodeParam.Palette();
            }
            if (this.param instanceof PNGEncodeParam.Palette) {
                final PNGEncodeParam.Palette palette = (PNGEncodeParam.Palette)this.param;
                if (palette.isPaletteSet()) {
                    final int[] palette2 = palette.getPalette();
                    final int n = palette2.length / 3;
                    int n2 = 0;
                    for (int j = 0; j < n; ++j) {
                        this.redPalette[j] = (byte)palette2[n2++];
                        this.greenPalette[j] = (byte)palette2[n2++];
                        this.bluePalette[j] = (byte)palette2[n2++];
                        this.alphaPalette[j] = -1;
                    }
                }
                this.colorType = 3;
            }
            else {
                if (!(this.param instanceof PNGEncodeParam.Gray)) {
                    throw new RuntimeException();
                }
                final byte[] array = null;
                this.alphaPalette = array;
                this.bluePalette = array;
                this.greenPalette = array;
                this.redPalette = array;
                this.colorType = 0;
            }
        }
        else if (this.numBands == 1) {
            if (this.param == null) {
                this.param = new PNGEncodeParam.Gray();
            }
            this.colorType = 0;
        }
        else if (this.numBands == 2) {
            if (this.param == null) {
                this.param = new PNGEncodeParam.Gray();
            }
            if (this.param.isTransparencySet()) {
                this.skipAlpha = true;
                this.numBands = 1;
                if (sampleSize[0] == 8 && this.bitDepth < 8) {
                    this.compressGray = true;
                }
                this.bpp = ((this.bitDepth == 16) ? 2 : 1);
                this.colorType = 0;
            }
            else {
                if (this.bitDepth < 8) {
                    this.bitDepth = 8;
                }
                this.colorType = 4;
            }
        }
        else if (this.numBands == 3) {
            if (this.param == null) {
                this.param = new PNGEncodeParam.RGB();
            }
            this.colorType = 2;
        }
        else if (this.numBands == 4) {
            if (this.param == null) {
                this.param = new PNGEncodeParam.RGB();
            }
            if (this.param.isTransparencySet()) {
                this.skipAlpha = true;
                this.numBands = 3;
                this.bpp = ((this.bitDepth == 16) ? 6 : 3);
                this.colorType = 2;
            }
            else {
                this.colorType = 6;
            }
        }
        this.interlace = this.param.getInterlacing();
        this.writeMagic();
        this.writeIHDR();
        this.writeCHRM();
        this.writeGAMA();
        this.writeICCP();
        this.writeSBIT();
        this.writeSRGB();
        this.writePLTE();
        this.writeHIST();
        this.writeTRNS();
        this.writeBKGD();
        this.writePHYS();
        this.writeSPLT();
        this.writeTIME();
        this.writeTEXT();
        this.writeZTXT();
        this.writePrivateChunks();
        this.writeIDAT();
        this.writeIEND();
        this.dataOutput.flush();
    }
    
    static {
        magic = new byte[] { -119, 80, 78, 71, 13, 10, 26, 10 };
        srgbChroma = new float[] { 0.3127f, 0.329f, 0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f };
    }
}
