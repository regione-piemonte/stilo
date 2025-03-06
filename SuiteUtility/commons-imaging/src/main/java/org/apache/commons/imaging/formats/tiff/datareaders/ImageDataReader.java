// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.datareaders;

import java.io.InputStream;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.common.PackBits;
import org.apache.commons.imaging.common.mylzw.MyLzwDecompressor;
import java.nio.ByteOrder;
import java.io.ByteArrayInputStream;
import org.apache.commons.imaging.common.itu_t4.T4AndT6Compression;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;
import org.apache.commons.imaging.formats.tiff.photometricinterpreters.PhotometricInterpreter;
import org.apache.commons.imaging.formats.tiff.TiffDirectory;

public abstract class ImageDataReader
{
    protected final TiffDirectory directory;
    protected final PhotometricInterpreter photometricInterpreter;
    private final int[] bitsPerSample;
    protected final int bitsPerSampleLength;
    private final int[] last;
    protected final int predictor;
    protected final int samplesPerPixel;
    protected final int width;
    protected final int height;
    
    public ImageDataReader(final TiffDirectory directory, final PhotometricInterpreter photometricInterpreter, final int[] bitsPerSample, final int predictor, final int samplesPerPixel, final int width, final int height) {
        this.directory = directory;
        this.photometricInterpreter = photometricInterpreter;
        this.bitsPerSample = bitsPerSample;
        this.bitsPerSampleLength = bitsPerSample.length;
        this.samplesPerPixel = samplesPerPixel;
        this.predictor = predictor;
        this.width = width;
        this.height = height;
        this.last = new int[samplesPerPixel];
    }
    
    public abstract void readImageData(final ImageBuilder p0) throws ImageReadException, IOException;
    
    public abstract BufferedImage readImageData(final Rectangle p0) throws ImageReadException, IOException;
    
    protected boolean isHomogenous(final int size) {
        for (final int element : this.bitsPerSample) {
            if (element != size) {
                return false;
            }
        }
        return true;
    }
    
    void getSamplesAsBytes(final BitInputStream bis, final int[] result) throws IOException {
        for (int i = 0; i < this.bitsPerSample.length; ++i) {
            final int bits = this.bitsPerSample[i];
            int sample = bis.readBits(bits);
            if (bits < 8) {
                final int sign = sample & 0x1;
                sample <<= 8 - bits;
                if (sign > 0) {
                    sample |= (1 << 8 - bits) - 1;
                }
            }
            else if (bits > 8) {
                sample >>= bits - 8;
            }
            result[i] = sample;
        }
    }
    
    protected void resetPredictor() {
        Arrays.fill(this.last, 0);
    }
    
    protected int[] applyPredictor(final int[] samples) {
        if (this.predictor == 2) {
            for (int i = 0; i < samples.length; ++i) {
                samples[i] = (0xFF & samples[i] + this.last[i]);
                this.last[i] = samples[i];
            }
        }
        return samples;
    }
    
    protected byte[] decompress(final byte[] compressedInput, final int compression, final int expectedSize, final int tileWidth, final int tileHeight) throws ImageReadException, IOException {
        final TiffField fillOrderField = this.directory.findField(TiffTagConstants.TIFF_TAG_FILL_ORDER);
        int fillOrder = 1;
        if (fillOrderField != null) {
            fillOrder = fillOrderField.getIntValue();
        }
        byte[] compressedOrdered;
        if (fillOrder == 1) {
            compressedOrdered = compressedInput;
        }
        else {
            if (fillOrder != 2) {
                throw new ImageReadException("TIFF FillOrder=" + fillOrder + " is invalid");
            }
            compressedOrdered = new byte[compressedInput.length];
            for (int i = 0; i < compressedInput.length; ++i) {
                compressedOrdered[i] = (byte)(Integer.reverse(0xFF & compressedInput[i]) >>> 24);
            }
        }
        switch (compression) {
            case 1: {
                return compressedOrdered;
            }
            case 2: {
                return T4AndT6Compression.decompressModifiedHuffman(compressedOrdered, tileWidth, tileHeight);
            }
            case 3: {
                int t4Options = 0;
                final TiffField field = this.directory.findField(TiffTagConstants.TIFF_TAG_T4_OPTIONS);
                if (field != null) {
                    t4Options = field.getIntValue();
                }
                final boolean is2D = (t4Options & 0x1) != 0x0;
                final boolean usesUncompressedMode = (t4Options & 0x2) != 0x0;
                if (usesUncompressedMode) {
                    throw new ImageReadException("T.4 compression with the uncompressed mode extension is not yet supported");
                }
                final boolean hasFillBitsBeforeEOL = (t4Options & 0x4) != 0x0;
                if (is2D) {
                    return T4AndT6Compression.decompressT4_2D(compressedOrdered, tileWidth, tileHeight, hasFillBitsBeforeEOL);
                }
                return T4AndT6Compression.decompressT4_1D(compressedOrdered, tileWidth, tileHeight, hasFillBitsBeforeEOL);
            }
            case 4: {
                int t6Options = 0;
                final TiffField field = this.directory.findField(TiffTagConstants.TIFF_TAG_T6_OPTIONS);
                if (field != null) {
                    t6Options = field.getIntValue();
                }
                final boolean usesUncompressedMode2 = (t6Options & 0x2) != 0x0;
                if (usesUncompressedMode2) {
                    throw new ImageReadException("T.6 compression with the uncompressed mode extension is not yet supported");
                }
                return T4AndT6Compression.decompressT6(compressedOrdered, tileWidth, tileHeight);
            }
            case 5: {
                final InputStream is = new ByteArrayInputStream(compressedOrdered);
                final int lzwMinimumCodeSize = 8;
                final MyLzwDecompressor myLzwDecompressor = new MyLzwDecompressor(8, ByteOrder.BIG_ENDIAN);
                myLzwDecompressor.setTiffLZWMode();
                return myLzwDecompressor.decompress(is, expectedSize);
            }
            case 32773: {
                return new PackBits().decompress(compressedOrdered, expectedSize);
            }
            default: {
                throw new ImageReadException("Tiff: unknown/unsupported compression: " + compression);
            }
        }
    }
}
