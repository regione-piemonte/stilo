// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.pcx;

import java.util.Arrays;
import java.io.IOException;
import org.apache.commons.imaging.palette.SimplePalette;
import org.apache.commons.imaging.common.BinaryOutputStream;
import java.nio.ByteOrder;
import org.apache.commons.imaging.palette.PaletteFactory;
import java.io.OutputStream;
import java.awt.image.BufferedImage;
import org.apache.commons.imaging.ImageWriteException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.imaging.PixelDensity;

class PcxWriter
{
    private int encoding;
    private int bitDepthWanted;
    private int planesWanted;
    private PixelDensity pixelDensity;
    private final RleWriter rleWriter;
    
    PcxWriter(Map<String, Object> params) throws ImageWriteException {
        this.bitDepthWanted = -1;
        this.planesWanted = -1;
        params = ((params == null) ? new HashMap<String, Object>() : new HashMap<String, Object>(params));
        if (params.containsKey("FORMAT")) {
            params.remove("FORMAT");
        }
        this.encoding = 1;
        if (params.containsKey("PCX_COMPRESSION")) {
            final Object value = params.remove("PCX_COMPRESSION");
            if (value != null) {
                if (!(value instanceof Number)) {
                    throw new ImageWriteException("Invalid compression parameter: " + value);
                }
                final int compression = ((Number)value).intValue();
                if (compression == 0) {
                    this.encoding = 0;
                }
            }
        }
        if (this.encoding == 0) {
            this.rleWriter = new RleWriter(false);
        }
        else {
            this.rleWriter = new RleWriter(true);
        }
        if (params.containsKey("PCX_BIT_DEPTH")) {
            final Object value = params.remove("PCX_BIT_DEPTH");
            if (value != null) {
                if (!(value instanceof Number)) {
                    throw new ImageWriteException("Invalid bit depth parameter: " + value);
                }
                this.bitDepthWanted = ((Number)value).intValue();
            }
        }
        if (params.containsKey("PCX_PLANES")) {
            final Object value = params.remove("PCX_PLANES");
            if (value != null) {
                if (!(value instanceof Number)) {
                    throw new ImageWriteException("Invalid planes parameter: " + value);
                }
                this.planesWanted = ((Number)value).intValue();
            }
        }
        if (params.containsKey("PIXEL_DENSITY")) {
            final Object value = params.remove("PIXEL_DENSITY");
            if (value != null) {
                if (!(value instanceof PixelDensity)) {
                    throw new ImageWriteException("Invalid pixel density parameter");
                }
                this.pixelDensity = (PixelDensity)value;
            }
        }
        if (this.pixelDensity == null) {
            this.pixelDensity = PixelDensity.createFromPixelsPerInch(72.0, 72.0);
        }
        if (!params.isEmpty()) {
            final Object firstKey = params.keySet().iterator().next();
            throw new ImageWriteException("Unknown parameter: " + firstKey);
        }
    }
    
    public void writeImage(final BufferedImage src, final OutputStream os) throws ImageWriteException, IOException {
        final PaletteFactory paletteFactory = new PaletteFactory();
        final SimplePalette palette = paletteFactory.makeExactRgbPaletteSimple(src, 256);
        final BinaryOutputStream bos = new BinaryOutputStream(os, ByteOrder.LITTLE_ENDIAN);
        int bitDepth;
        int planes;
        if (palette == null || this.bitDepthWanted == 24 || this.bitDepthWanted == 32) {
            if (this.bitDepthWanted == 32) {
                bitDepth = 32;
                planes = 1;
            }
            else {
                bitDepth = 8;
                planes = 3;
            }
        }
        else if (palette.length() > 16 || this.bitDepthWanted == 8) {
            bitDepth = 8;
            planes = 1;
        }
        else if (palette.length() > 8 || this.bitDepthWanted == 4) {
            if (this.planesWanted == 1) {
                bitDepth = 4;
                planes = 1;
            }
            else {
                bitDepth = 1;
                planes = 4;
            }
        }
        else if (palette.length() > 4 || this.bitDepthWanted == 3) {
            bitDepth = 1;
            planes = 3;
        }
        else if (palette.length() > 2 || this.bitDepthWanted == 2) {
            if (this.planesWanted == 2) {
                bitDepth = 1;
                planes = 2;
            }
            else {
                bitDepth = 2;
                planes = 1;
            }
        }
        else {
            boolean onlyBlackAndWhite = true;
            if (palette.length() >= 1) {
                final int rgb = palette.getEntry(0);
                if (rgb != 0 && rgb != 16777215) {
                    onlyBlackAndWhite = false;
                }
            }
            if (palette.length() == 2) {
                final int rgb = palette.getEntry(1);
                if (rgb != 0 && rgb != 16777215) {
                    onlyBlackAndWhite = false;
                }
            }
            if (onlyBlackAndWhite) {
                bitDepth = 1;
                planes = 1;
            }
            else {
                bitDepth = 1;
                planes = 2;
            }
        }
        int bytesPerLine = (bitDepth * src.getWidth() + 7) / 8;
        if (bytesPerLine % 2 != 0) {
            ++bytesPerLine;
        }
        final byte[] palette2 = new byte[48];
        for (int i = 0; i < 16; ++i) {
            int rgb2;
            if (i < palette.length()) {
                rgb2 = palette.getEntry(i);
            }
            else {
                rgb2 = 0;
            }
            palette2[3 * i + 0] = (byte)(0xFF & rgb2 >> 16);
            palette2[3 * i + 1] = (byte)(0xFF & rgb2 >> 8);
            palette2[3 * i + 2] = (byte)(0xFF & rgb2);
        }
        bos.write(10);
        bos.write((bitDepth == 1 && planes == 1) ? 3 : 5);
        bos.write(this.encoding);
        bos.write(bitDepth);
        bos.write2Bytes(0);
        bos.write2Bytes(0);
        bos.write2Bytes(src.getWidth() - 1);
        bos.write2Bytes(src.getHeight() - 1);
        bos.write2Bytes((short)Math.round(this.pixelDensity.horizontalDensityInches()));
        bos.write2Bytes((short)Math.round(this.pixelDensity.verticalDensityInches()));
        bos.write(palette2);
        bos.write(0);
        bos.write(planes);
        bos.write2Bytes(bytesPerLine);
        bos.write2Bytes(1);
        bos.write2Bytes(0);
        bos.write2Bytes(0);
        bos.write(new byte[54]);
        if (bitDepth == 32) {
            this.writePixels32(src, bytesPerLine, bos);
        }
        else {
            this.writePixels(src, bitDepth, planes, bytesPerLine, palette, bos);
        }
        if (bitDepth == 8 && planes == 1) {
            bos.write(12);
            for (int i = 0; i < 256; ++i) {
                int rgb2;
                if (i < palette.length()) {
                    rgb2 = palette.getEntry(i);
                }
                else {
                    rgb2 = 0;
                }
                bos.write(rgb2 >> 16 & 0xFF);
                bos.write(rgb2 >> 8 & 0xFF);
                bos.write(rgb2 & 0xFF);
            }
        }
    }
    
    private void writePixels(final BufferedImage src, final int bitDepth, final int planes, final int bytesPerLine, final SimplePalette palette, final BinaryOutputStream bos) throws IOException, ImageWriteException {
        final byte[] plane0 = new byte[bytesPerLine];
        final byte[] plane2 = new byte[bytesPerLine];
        final byte[] plane3 = new byte[bytesPerLine];
        final byte[] plane4 = new byte[bytesPerLine];
        final byte[][] allPlanes = { plane0, plane2, plane3, plane4 };
        for (int y = 0; y < src.getHeight(); ++y) {
            for (int i = 0; i < planes; ++i) {
                Arrays.fill(allPlanes[i], (byte)0);
            }
            if (bitDepth == 1 && planes == 1) {
                for (int x = 0; x < src.getWidth(); ++x) {
                    final int rgb = 0xFFFFFF & src.getRGB(x, y);
                    int bit;
                    if (rgb == 0) {
                        bit = 0;
                    }
                    else {
                        bit = 1;
                    }
                    final byte[] array = plane0;
                    final int n = x >>> 3;
                    array[n] |= (byte)(bit << 7 - (x & 0x7));
                }
            }
            else if (bitDepth == 1 && planes == 2) {
                for (int x = 0; x < src.getWidth(); ++x) {
                    final int argb = src.getRGB(x, y);
                    final int index = palette.getPaletteIndex(0xFFFFFF & argb);
                    final byte[] array2 = plane0;
                    final int n2 = x >>> 3;
                    array2[n2] |= (byte)((index & 0x1) << 7 - (x & 0x7));
                    final byte[] array3 = plane2;
                    final int n3 = x >>> 3;
                    array3[n3] |= (byte)((index & 0x2) >> 1 << 7 - (x & 0x7));
                }
            }
            else if (bitDepth == 1 && planes == 3) {
                for (int x = 0; x < src.getWidth(); ++x) {
                    final int argb = src.getRGB(x, y);
                    final int index = palette.getPaletteIndex(0xFFFFFF & argb);
                    final byte[] array4 = plane0;
                    final int n4 = x >>> 3;
                    array4[n4] |= (byte)((index & 0x1) << 7 - (x & 0x7));
                    final byte[] array5 = plane2;
                    final int n5 = x >>> 3;
                    array5[n5] |= (byte)((index & 0x2) >> 1 << 7 - (x & 0x7));
                    final byte[] array6 = plane3;
                    final int n6 = x >>> 3;
                    array6[n6] |= (byte)((index & 0x4) >> 2 << 7 - (x & 0x7));
                }
            }
            else if (bitDepth == 1 && planes == 4) {
                for (int x = 0; x < src.getWidth(); ++x) {
                    final int argb = src.getRGB(x, y);
                    final int index = palette.getPaletteIndex(0xFFFFFF & argb);
                    final byte[] array7 = plane0;
                    final int n7 = x >>> 3;
                    array7[n7] |= (byte)((index & 0x1) << 7 - (x & 0x7));
                    final byte[] array8 = plane2;
                    final int n8 = x >>> 3;
                    array8[n8] |= (byte)((index & 0x2) >> 1 << 7 - (x & 0x7));
                    final byte[] array9 = plane3;
                    final int n9 = x >>> 3;
                    array9[n9] |= (byte)((index & 0x4) >> 2 << 7 - (x & 0x7));
                    final byte[] array10 = plane4;
                    final int n10 = x >>> 3;
                    array10[n10] |= (byte)((index & 0x8) >> 3 << 7 - (x & 0x7));
                }
            }
            else if (bitDepth == 2 && planes == 1) {
                for (int x = 0; x < src.getWidth(); ++x) {
                    final int argb = src.getRGB(x, y);
                    final int index = palette.getPaletteIndex(0xFFFFFF & argb);
                    final byte[] array11 = plane0;
                    final int n11 = x >>> 2;
                    array11[n11] |= (byte)(index << 2 * (3 - (x & 0x3)));
                }
            }
            else if (bitDepth == 4 && planes == 1) {
                for (int x = 0; x < src.getWidth(); ++x) {
                    final int argb = src.getRGB(x, y);
                    final int index = palette.getPaletteIndex(0xFFFFFF & argb);
                    final byte[] array12 = plane0;
                    final int n12 = x >>> 1;
                    array12[n12] |= (byte)(index << 4 * (1 - (x & 0x1)));
                }
            }
            else if (bitDepth == 8 && planes == 1) {
                for (int x = 0; x < src.getWidth(); ++x) {
                    final int argb = src.getRGB(x, y);
                    final int index = palette.getPaletteIndex(0xFFFFFF & argb);
                    plane0[x] = (byte)index;
                }
            }
            else if (bitDepth == 8 && planes == 3) {
                for (int x = 0; x < src.getWidth(); ++x) {
                    final int argb = src.getRGB(x, y);
                    plane0[x] = (byte)(argb >>> 16);
                    plane2[x] = (byte)(argb >>> 8);
                    plane3[x] = (byte)argb;
                }
            }
            for (int i = 0; i < planes; ++i) {
                this.rleWriter.write(bos, allPlanes[i]);
            }
        }
        this.rleWriter.flush(bos);
    }
    
    private void writePixels32(final BufferedImage src, final int bytesPerLine, final BinaryOutputStream bos) throws IOException, ImageWriteException {
        final int[] rgbs = new int[src.getWidth()];
        final byte[] plane = new byte[4 * bytesPerLine];
        for (int y = 0; y < src.getHeight(); ++y) {
            src.getRGB(0, y, src.getWidth(), 1, rgbs, 0, src.getWidth());
            for (int x = 0; x < rgbs.length; ++x) {
                plane[4 * x + 0] = (byte)rgbs[x];
                plane[4 * x + 1] = (byte)(rgbs[x] >> 8);
                plane[4 * x + 2] = (byte)(rgbs[x] >> 16);
                plane[4 * x + 3] = 0;
            }
            this.rleWriter.write(bos, plane);
        }
        this.rleWriter.flush(bos);
    }
}
