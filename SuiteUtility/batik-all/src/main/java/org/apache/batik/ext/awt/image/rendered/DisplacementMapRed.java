// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.DataBufferInt;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.Rectangle;
import java.util.Map;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.ARGBChannel;

public class DisplacementMapRed extends AbstractRed
{
    private static final boolean TIME = false;
    private static final boolean USE_NN = false;
    private float scaleX;
    private float scaleY;
    private ARGBChannel xChannel;
    private ARGBChannel yChannel;
    CachableRed image;
    CachableRed offsets;
    int maxOffX;
    int maxOffY;
    RenderingHints hints;
    TileOffsets[] xOffsets;
    TileOffsets[] yOffsets;
    
    public DisplacementMapRed(final CachableRed cachableRed, final CachableRed offsets, final ARGBChannel xChannel, final ARGBChannel yChannel, final float scaleX, final float scaleY, final RenderingHints hints) {
        if (xChannel == null) {
            throw new IllegalArgumentException("Must provide xChannel");
        }
        if (yChannel == null) {
            throw new IllegalArgumentException("Must provide yChannel");
        }
        this.offsets = offsets;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.xChannel = xChannel;
        this.yChannel = yChannel;
        this.hints = hints;
        this.maxOffX = (int)Math.ceil(scaleX / 2.0f);
        this.maxOffY = (int)Math.ceil(scaleY / 2.0f);
        final Rectangle bounds = cachableRed.getBounds();
        final Rectangle bounds2;
        final Rectangle rectangle = bounds2 = cachableRed.getBounds();
        bounds2.x -= this.maxOffX;
        final Rectangle rectangle2 = rectangle;
        rectangle2.width += 2 * this.maxOffX;
        final Rectangle rectangle3 = rectangle;
        rectangle3.y -= this.maxOffY;
        final Rectangle rectangle4 = rectangle;
        rectangle4.height += 2 * this.maxOffY;
        final TileCacheRed image = new TileCacheRed(new PadRed(cachableRed, rectangle, PadMode.ZERO_PAD, null));
        this.init(this.image = image, bounds, GraphicsUtil.coerceColorModel(image.getColorModel(), true), image.getSampleModel(), bounds.x, bounds.y, null);
        this.xOffsets = new TileOffsets[this.getNumXTiles()];
        this.yOffsets = new TileOffsets[this.getNumYTiles()];
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        this.copyToRaster(writableRaster);
        return writableRaster;
    }
    
    public Raster getTile(final int n, final int n2) {
        final WritableRaster tile = this.makeTile(n, n2);
        final Raster data = this.offsets.getData(tile.getBounds());
        GraphicsUtil.coerceData((WritableRaster)data, this.offsets.getColorModel(), false);
        final TileOffsets xOffsets = this.getXOffsets(n);
        final TileOffsets yOffsets = this.getYOffsets(n2);
        if (this.image.getColorModel().isAlphaPremultiplied()) {
            this.filterBL(data, tile, xOffsets.tile, xOffsets.off, yOffsets.tile, yOffsets.off);
        }
        else {
            this.filterBLPre(data, tile, xOffsets.tile, xOffsets.off, yOffsets.tile, yOffsets.off);
        }
        return tile;
    }
    
    public TileOffsets getXOffsets(final int n) {
        final TileOffsets tileOffsets = this.xOffsets[n - this.getMinTileX()];
        if (tileOffsets != null) {
            return tileOffsets;
        }
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)this.getSampleModel();
        final int offset = singlePixelPackedSampleModel.getOffset(0, 0);
        final int width = singlePixelPackedSampleModel.getWidth();
        final int n2 = width + 2 * this.maxOffX;
        final int n3 = this.getTileGridXOffset() + n * width - this.maxOffX - this.image.getTileGridXOffset();
        final int n4 = n3 + n2 - 1;
        final int n5 = (int)Math.floor(n3 / (double)width);
        final int n6 = (int)Math.floor(n4 / (double)width);
        return this.xOffsets[n - this.getMinTileX()] = new TileOffsets(n2, offset, 1, n3 - n5 * width, width, (n6 + 1) * width - 1 - n4, n5, n6);
    }
    
    public TileOffsets getYOffsets(final int n) {
        final TileOffsets tileOffsets = this.yOffsets[n - this.getMinTileY()];
        if (tileOffsets != null) {
            return tileOffsets;
        }
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)this.getSampleModel();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final int height = singlePixelPackedSampleModel.getHeight();
        final int n2 = height + 2 * this.maxOffY;
        final int n3 = this.getTileGridYOffset() + n * height - this.maxOffY - this.image.getTileGridYOffset();
        final int n4 = n3 + n2 - 1;
        final int n5 = (int)Math.floor(n3 / (double)height);
        final int n6 = (int)Math.floor(n4 / (double)height);
        return this.yOffsets[n - this.getMinTileY()] = new TileOffsets(n2, 0, scanlineStride, n3 - n5 * height, height, (n6 + 1) * height - 1 - n4, n5, n6);
    }
    
    public void filterBL(final Raster raster, final WritableRaster writableRaster, final int[] array, final int[] array2, final int[] array3, final int[] array4) {
        final int width = writableRaster.getWidth();
        final int height = writableRaster.getHeight();
        final int maxOffX = this.maxOffX;
        final int maxOffY = this.maxOffY;
        final int n = maxOffX + width;
        final int n2 = maxOffY + height;
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final DataBufferInt dataBufferInt2 = (DataBufferInt)raster.getDataBuffer();
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int n3 = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel2 = (SinglePixelPackedSampleModel)raster.getSampleModel();
        final int n4 = dataBufferInt2.getOffset() + singlePixelPackedSampleModel2.getOffset(writableRaster.getMinX() - raster.getSampleModelTranslateX(), writableRaster.getMinY() - raster.getSampleModelTranslateY());
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final int scanlineStride2 = singlePixelPackedSampleModel2.getScanlineStride();
        final int n5 = scanlineStride - width;
        final int n6 = scanlineStride2 - width;
        final int[] array5 = dataBufferInt.getBankData()[0];
        final int[] array6 = dataBufferInt2.getBankData()[0];
        final int n7 = this.xChannel.toInt() * 8;
        final int n8 = this.yChannel.toInt() * 8;
        int n9 = n3;
        int n10 = n4;
        final int n11 = (int)(this.scaleX / 255.0 * 32768.0 + 0.5);
        final int n12 = (int)(-127.5 * n11 - 0.5);
        final int n13 = (int)(this.scaleY / 255.0 * 32768.0 + 0.5);
        final int n14 = (int)(-127.5 * n13 - 0.5);
        System.currentTimeMillis();
        int n15 = array[0] - 1;
        int n16 = array3[0] - 1;
        int[] array7 = null;
        for (int i = maxOffY; i < n2; ++i) {
            for (int j = maxOffX; j < n; ++j, ++n9, ++n10) {
                final int n17 = array6[n10];
                final int n18 = n11 * (n17 >> n7 & 0xFF) + n12;
                final int n19 = n13 * (n17 >> n8 & 0xFF) + n14;
                final int n20 = j + (n18 >> 15);
                final int n21 = i + (n19 >> 15);
                if (n15 != array[n20] || n16 != array3[n21]) {
                    n15 = array[n20];
                    n16 = array3[n21];
                    array7 = ((DataBufferInt)this.image.getTile(n15, n16).getDataBuffer()).getBankData()[0];
                }
                final int n22 = array7[array2[n20] + array4[n21]];
                final int n23 = array[n20 + 1];
                final int n24 = array3[n21 + 1];
                int n25;
                int n26;
                int n27;
                if (n16 == n24) {
                    if (n15 == n23) {
                        n25 = array7[array2[n20 + 1] + array4[n21]];
                        n26 = array7[array2[n20] + array4[n21 + 1]];
                        n27 = array7[array2[n20 + 1] + array4[n21 + 1]];
                    }
                    else {
                        n26 = array7[array2[n20] + array4[n21 + 1]];
                        array7 = ((DataBufferInt)this.image.getTile(n23, n16).getDataBuffer()).getBankData()[0];
                        n25 = array7[array2[n20 + 1] + array4[n21]];
                        n27 = array7[array2[n20 + 1] + array4[n21 + 1]];
                        n15 = n23;
                    }
                }
                else if (n15 == n23) {
                    n25 = array7[array2[n20 + 1] + array4[n21]];
                    array7 = ((DataBufferInt)this.image.getTile(n15, n24).getDataBuffer()).getBankData()[0];
                    n26 = array7[array2[n20] + array4[n21 + 1]];
                    n27 = array7[array2[n20 + 1] + array4[n21 + 1]];
                    n16 = n24;
                }
                else {
                    n26 = ((DataBufferInt)this.image.getTile(n15, n24).getDataBuffer()).getBankData()[0][array2[n20] + array4[n21 + 1]];
                    n27 = ((DataBufferInt)this.image.getTile(n23, n24).getDataBuffer()).getBankData()[0][array2[n20 + 1] + array4[n21 + 1]];
                    array7 = ((DataBufferInt)this.image.getTile(n23, n16).getDataBuffer()).getBankData()[0];
                    n25 = array7[array2[n20 + 1] + array4[n21]];
                    n15 = n23;
                }
                final int n28 = n18 & 0x7FFF;
                final int n29 = n19 & 0x7FFF;
                final int n30 = n22 >>> 16 & 0xFF00;
                final int n31 = n30 + (((n25 >>> 16 & 0xFF00) - n30) * n28 + 16384 >> 15) & 0xFFFF;
                final int n32 = n26 >>> 16 & 0xFF00;
                final int n33 = ((n31 << 15) + ((n32 + (((n27 >>> 16 & 0xFF00) - n32) * n28 + 16384 >> 15) & 0xFFFF) - n31) * n29 + 4194304 & 0x7F800000) << 1;
                final int n34 = n22 >> 8 & 0xFF00;
                final int n35 = n34 + (((n25 >> 8 & 0xFF00) - n34) * n28 + 16384 >> 15) & 0xFFFF;
                final int n36 = n26 >> 8 & 0xFF00;
                final int n37 = n33 | ((n35 << 15) + ((n36 + (((n27 >> 8 & 0xFF00) - n36) * n28 + 16384 >> 15) & 0xFFFF) - n35) * n29 + 4194304 & 0x7F800000) >>> 7;
                final int n38 = n22 & 0xFF00;
                final int n39 = n38 + (((n25 & 0xFF00) - n38) * n28 + 16384 >> 15) & 0xFFFF;
                final int n40 = n26 & 0xFF00;
                final int n41 = n37 | ((n39 << 15) + ((n40 + (((n27 & 0xFF00) - n40) * n28 + 16384 >> 15) & 0xFFFF) - n39) * n29 + 4194304 & 0x7F800000) >>> 15;
                final int n42 = n22 << 8 & 0xFF00;
                final int n43 = n42 + (((n25 << 8 & 0xFF00) - n42) * n28 + 16384 >> 15) & 0xFFFF;
                final int n44 = n26 << 8 & 0xFF00;
                array5[n9] = (n41 | ((n43 << 15) + ((n44 + (((n27 << 8 & 0xFF00) - n44) * n28 + 16384 >> 15) & 0xFFFF) - n43) * n29 + 4194304 & 0x7F800000) >>> 23);
            }
            n9 += n5;
            n10 += n6;
        }
    }
    
    public void filterBLPre(final Raster raster, final WritableRaster writableRaster, final int[] array, final int[] array2, final int[] array3, final int[] array4) {
        final int width = writableRaster.getWidth();
        final int height = writableRaster.getHeight();
        final int maxOffX = this.maxOffX;
        final int maxOffY = this.maxOffY;
        final int n = maxOffX + width;
        final int n2 = maxOffY + height;
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final DataBufferInt dataBufferInt2 = (DataBufferInt)raster.getDataBuffer();
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int n3 = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel2 = (SinglePixelPackedSampleModel)raster.getSampleModel();
        final int n4 = dataBufferInt2.getOffset() + singlePixelPackedSampleModel2.getOffset(writableRaster.getMinX() - raster.getSampleModelTranslateX(), writableRaster.getMinY() - raster.getSampleModelTranslateY());
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final int scanlineStride2 = singlePixelPackedSampleModel2.getScanlineStride();
        final int n5 = scanlineStride - width;
        final int n6 = scanlineStride2 - width;
        final int[] array5 = dataBufferInt.getBankData()[0];
        final int[] array6 = dataBufferInt2.getBankData()[0];
        final int n7 = this.xChannel.toInt() * 8;
        final int n8 = this.yChannel.toInt() * 8;
        int n9 = n3;
        int n10 = n4;
        final int n11 = (int)(this.scaleX / 255.0 * 32768.0 + 0.5);
        final int n12 = (int)(-127.5 * n11 - 0.5);
        final int n13 = (int)(this.scaleY / 255.0 * 32768.0 + 0.5);
        final int n14 = (int)(-127.5 * n13 - 0.5);
        System.currentTimeMillis();
        int n15 = array[0] - 1;
        int n16 = array3[0] - 1;
        int[] array7 = null;
        for (int i = maxOffY; i < n2; ++i) {
            for (int j = maxOffX; j < n; ++j, ++n9, ++n10) {
                final int n17 = array6[n10];
                final int n18 = n11 * (n17 >> n7 & 0xFF) + n12;
                final int n19 = n13 * (n17 >> n8 & 0xFF) + n14;
                final int n20 = j + (n18 >> 15);
                final int n21 = i + (n19 >> 15);
                if (n15 != array[n20] || n16 != array3[n21]) {
                    n15 = array[n20];
                    n16 = array3[n21];
                    array7 = ((DataBufferInt)this.image.getTile(n15, n16).getDataBuffer()).getBankData()[0];
                }
                final int n22 = array7[array2[n20] + array4[n21]];
                final int n23 = array[n20 + 1];
                final int n24 = array3[n21 + 1];
                int n25;
                int n26;
                int n27;
                if (n16 == n24) {
                    if (n15 == n23) {
                        n25 = array7[array2[n20 + 1] + array4[n21]];
                        n26 = array7[array2[n20] + array4[n21 + 1]];
                        n27 = array7[array2[n20 + 1] + array4[n21 + 1]];
                    }
                    else {
                        n26 = array7[array2[n20] + array4[n21 + 1]];
                        array7 = ((DataBufferInt)this.image.getTile(n23, n16).getDataBuffer()).getBankData()[0];
                        n25 = array7[array2[n20 + 1] + array4[n21]];
                        n27 = array7[array2[n20 + 1] + array4[n21 + 1]];
                        n15 = n23;
                    }
                }
                else if (n15 == n23) {
                    n25 = array7[array2[n20 + 1] + array4[n21]];
                    array7 = ((DataBufferInt)this.image.getTile(n15, n24).getDataBuffer()).getBankData()[0];
                    n26 = array7[array2[n20] + array4[n21 + 1]];
                    n27 = array7[array2[n20 + 1] + array4[n21 + 1]];
                    n16 = n24;
                }
                else {
                    n26 = ((DataBufferInt)this.image.getTile(n15, n24).getDataBuffer()).getBankData()[0][array2[n20] + array4[n21 + 1]];
                    n27 = ((DataBufferInt)this.image.getTile(n23, n24).getDataBuffer()).getBankData()[0][array2[n20 + 1] + array4[n21 + 1]];
                    array7 = ((DataBufferInt)this.image.getTile(n23, n16).getDataBuffer()).getBankData()[0];
                    n25 = array7[array2[n20 + 1] + array4[n21]];
                    n15 = n23;
                }
                final int n28 = n18 & 0x7FFF;
                final int n29 = n19 & 0x7FFF;
                final int n30 = n22 >>> 16 & 0xFF00;
                final int n31 = n25 >>> 16 & 0xFF00;
                final int n32 = n30 + ((n31 - n30) * n28 + 16384 >> 15) & 0xFFFF;
                final int n33 = (n30 >> 8) * 65793 + 128 >> 8;
                final int n34 = (n31 >> 8) * 65793 + 128 >> 8;
                final int n35 = n26 >>> 16 & 0xFF00;
                final int n36 = n27 >>> 16 & 0xFF00;
                final int n37 = n35 + ((n36 - n35) * n28 + 16384 >> 15) & 0xFFFF;
                final int n38 = (n35 >> 8) * 65793 + 128 >> 8;
                final int n39 = (n36 >> 8) * 65793 + 128 >> 8;
                final int n40 = ((n32 << 15) + (n37 - n32) * n29 + 4194304 & 0x7F800000) << 1;
                final int n41 = (n22 >> 16 & 0xFF) * n33 + 128 >> 8;
                final int n42 = n41 + ((((n25 >> 16 & 0xFF) * n34 + 128 >> 8) - n41) * n28 + 16384 >> 15) & 0xFFFF;
                final int n43 = (n26 >> 16 & 0xFF) * n38 + 128 >> 8;
                final int n44 = n40 | ((n42 << 15) + ((n43 + ((((n27 >> 16 & 0xFF) * n39 + 128 >> 8) - n43) * n28 + 16384 >> 15) & 0xFFFF) - n42) * n29 + 4194304 & 0x7F800000) >>> 7;
                final int n45 = (n22 >> 8 & 0xFF) * n33 + 128 >> 8;
                final int n46 = n45 + ((((n25 >> 8 & 0xFF) * n34 + 128 >> 8) - n45) * n28 + 16384 >> 15) & 0xFFFF;
                final int n47 = (n26 >> 8 & 0xFF) * n38 + 128 >> 8;
                final int n48 = n44 | ((n46 << 15) + ((n47 + ((((n27 >> 8 & 0xFF) * n39 + 128 >> 8) - n47) * n28 + 16384 >> 15) & 0xFFFF) - n46) * n29 + 4194304 & 0x7F800000) >>> 15;
                final int n49 = (n22 & 0xFF) * n33 + 128 >> 8;
                final int n50 = n49 + ((((n25 & 0xFF) * n34 + 128 >> 8) - n49) * n28 + 16384 >> 15) & 0xFFFF;
                final int n51 = (n26 & 0xFF) * n38 + 128 >> 8;
                array5[n9] = (n48 | ((n50 << 15) + ((n51 + ((((n27 & 0xFF) * n39 + 128 >> 8) - n51) * n28 + 16384 >> 15) & 0xFFFF) - n50) * n29 + 4194304 & 0x7F800000) >>> 23);
            }
            n9 += n5;
            n10 += n6;
        }
    }
    
    public void filterNN(final Raster raster, final WritableRaster writableRaster, final int[] array, final int[] array2, final int[] array3, final int[] array4) {
        final int width = writableRaster.getWidth();
        final int height = writableRaster.getHeight();
        final int maxOffX = this.maxOffX;
        final int maxOffY = this.maxOffY;
        final int n = maxOffX + width;
        final int n2 = maxOffY + height;
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final DataBufferInt dataBufferInt2 = (DataBufferInt)raster.getDataBuffer();
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int n3 = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel2 = (SinglePixelPackedSampleModel)raster.getSampleModel();
        final int n4 = dataBufferInt2.getOffset() + singlePixelPackedSampleModel2.getOffset(raster.getMinX() - raster.getSampleModelTranslateX(), raster.getMinY() - raster.getSampleModelTranslateY());
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final int scanlineStride2 = singlePixelPackedSampleModel2.getScanlineStride();
        final int n5 = scanlineStride - width;
        final int n6 = scanlineStride2 - width;
        final int[] array5 = dataBufferInt.getBankData()[0];
        final int[] array6 = dataBufferInt2.getBankData()[0];
        final int n7 = this.xChannel.toInt() * 8;
        final int n8 = this.yChannel.toInt() * 8;
        final int n9 = (int)(this.scaleX / 255.0 * 32768.0 + 0.5);
        final int n10 = (int)(this.scaleY / 255.0 * 32768.0 + 0.5);
        final int n11 = (int)(-127.5 * n9 - 0.5) + 16384;
        final int n12 = (int)(-127.5 * n10 - 0.5) + 16384;
        int n13 = n3;
        int n14 = n4;
        System.currentTimeMillis();
        int i = maxOffY;
        int n15 = array[0] - 1;
        int n16 = array3[0] - 1;
        int[] array7 = null;
        while (i < n2) {
            for (int j = maxOffX; j < n; ++j) {
                final int n17 = array6[n14];
                final int n18 = n9 * (n17 >> n7 & 0xFF) + n11;
                final int n19 = n10 * (n17 >> n8 & 0xFF) + n12;
                final int n20 = j + (n18 >> 15);
                final int n21 = i + (n19 >> 15);
                if (n15 != array[n20] || n16 != array3[n21]) {
                    n15 = array[n20];
                    n16 = array3[n21];
                    array7 = ((DataBufferInt)this.image.getTile(n15, n16).getDataBuffer()).getBankData()[0];
                }
                array5[n13] = array7[array2[n20] + array4[n21]];
                ++n13;
                ++n14;
            }
            n13 += n5;
            n14 += n6;
            ++i;
        }
    }
    
    static class TileOffsets
    {
        int[] tile;
        int[] off;
        
        TileOffsets(final int n, final int n2, final int n3, int n4, int n5, final int n6, int n7, final int n8) {
            this.tile = new int[n + 1];
            this.off = new int[n + 1];
            if (n7 == n8) {
                n5 -= n6;
            }
            for (int i = 0; i < n; ++i) {
                this.tile[i] = n7;
                this.off[i] = n2 + n4 * n3;
                if (++n4 == n5) {
                    n4 = 0;
                    if (++n7 == n8) {
                        n5 -= n6;
                    }
                }
            }
            this.tile[n] = this.tile[n - 1];
            this.off[n] = this.off[n - 1];
        }
    }
}
