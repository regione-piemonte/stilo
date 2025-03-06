// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.util.Hashtable;
import java.util.Properties;
import java.awt.Point;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.DirectColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

public class ImageBuilder
{
    private final int[] data;
    private final int width;
    private final int height;
    private final boolean hasAlpha;
    
    public ImageBuilder(final int width, final int height, final boolean hasAlpha) {
        if (width <= 0) {
            throw new RasterFormatException("zero or negative width value");
        }
        if (height <= 0) {
            throw new RasterFormatException("zero or negative height value");
        }
        this.data = new int[width * height];
        this.width = width;
        this.height = height;
        this.hasAlpha = hasAlpha;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getRGB(final int x, final int y) {
        final int rowOffset = y * this.width;
        return this.data[rowOffset + x];
    }
    
    public void setRGB(final int x, final int y, final int argb) {
        final int rowOffset = y * this.width;
        this.data[rowOffset + x] = argb;
    }
    
    public BufferedImage getBufferedImage() {
        return this.makeBufferedImage(this.data, this.width, this.height, this.hasAlpha);
    }
    
    public BufferedImage getSubimage(final int x, final int y, final int w, final int h) {
        if (w <= 0) {
            throw new RasterFormatException("negative or zero subimage width");
        }
        if (h <= 0) {
            throw new RasterFormatException("negative or zero subimage height");
        }
        if (x < 0 || x >= this.width) {
            throw new RasterFormatException("subimage x is outside raster");
        }
        if (x + w > this.width) {
            throw new RasterFormatException("subimage (x+width) is outside raster");
        }
        if (y < 0 || y >= this.height) {
            throw new RasterFormatException("subimage y is outside raster");
        }
        if (y + h > this.height) {
            throw new RasterFormatException("subimage (y+height) is outside raster");
        }
        final int[] argb = new int[w * h];
        int k = 0;
        for (int iRow = 0; iRow < h; ++iRow) {
            final int dIndex = (iRow + y) * this.width + x;
            System.arraycopy(this.data, dIndex, argb, k, w);
            k += w;
        }
        return this.makeBufferedImage(argb, w, h, this.hasAlpha);
    }
    
    private BufferedImage makeBufferedImage(final int[] argb, final int w, final int h, final boolean useAlpha) {
        final DataBufferInt buffer = new DataBufferInt(argb, w * h);
        ColorModel colorModel;
        WritableRaster raster;
        if (useAlpha) {
            colorModel = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
            raster = Raster.createPackedRaster(buffer, w, h, w, new int[] { 16711680, 65280, 255, -16777216 }, null);
        }
        else {
            colorModel = new DirectColorModel(24, 16711680, 65280, 255);
            raster = Raster.createPackedRaster(buffer, w, h, w, new int[] { 16711680, 65280, 255 }, null);
        }
        return new BufferedImage(colorModel, raster, colorModel.isAlphaPremultiplied(), new Properties());
    }
}
