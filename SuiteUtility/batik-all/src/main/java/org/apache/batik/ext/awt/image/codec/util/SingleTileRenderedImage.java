// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.util;

import java.awt.image.ColorModel;
import java.awt.image.Raster;

public class SingleTileRenderedImage extends SimpleRenderedImage
{
    Raster ras;
    
    public SingleTileRenderedImage(final Raster ras, final ColorModel colorModel) {
        this.ras = ras;
        final int minX = ras.getMinX();
        this.minX = minX;
        this.tileGridXOffset = minX;
        final int minY = ras.getMinY();
        this.minY = minY;
        this.tileGridYOffset = minY;
        final int width = ras.getWidth();
        this.width = width;
        this.tileWidth = width;
        final int height = ras.getHeight();
        this.height = height;
        this.tileHeight = height;
        this.sampleModel = ras.getSampleModel();
        this.colorModel = colorModel;
    }
    
    public Raster getTile(final int n, final int n2) {
        if (n != 0 || n2 != 0) {
            throw new IllegalArgumentException(PropertyUtil.getString("SingleTileRenderedImage0"));
        }
        return this.ras;
    }
}
