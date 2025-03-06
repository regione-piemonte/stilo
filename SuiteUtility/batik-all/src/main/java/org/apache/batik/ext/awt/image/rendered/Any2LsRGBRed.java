// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.Rectangle;
import java.awt.image.ColorConvertOp;
import java.awt.image.SampleModel;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.SinglePixelPackedSampleModel;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.image.BandCombineOp;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.awt.color.ColorSpace;
import java.util.Map;

public class Any2LsRGBRed extends AbstractRed
{
    boolean srcIssRGB;
    private static final double GAMMA = 2.4;
    private static final double LFACT = 0.07739938080495357;
    private static final int[] sRGBToLsRGBLut;
    
    public Any2LsRGBRed(final CachableRed cachableRed) {
        super(cachableRed, cachableRed.getBounds(), fixColorModel(cachableRed), fixSampleModel(cachableRed), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
        this.srcIssRGB = false;
        final ColorModel colorModel = cachableRed.getColorModel();
        if (colorModel == null) {
            return;
        }
        if (colorModel.getColorSpace() == ColorSpace.getInstance(1000)) {
            this.srcIssRGB = true;
        }
    }
    
    public static final double sRGBToLsRGB(final double n) {
        if (n <= 0.003928) {
            return n * 0.07739938080495357;
        }
        return Math.pow((n + 0.055) / 1.055, 2.4);
    }
    
    public WritableRaster copyData(final WritableRaster dst) {
        final CachableRed cachableRed = this.getSources().get(0);
        final ColorModel colorModel = cachableRed.getColorModel();
        final SampleModel sampleModel = cachableRed.getSampleModel();
        if (this.srcIssRGB && Any2sRGBRed.is_INT_PACK_COMP(dst.getSampleModel())) {
            cachableRed.copyData(dst);
            if (colorModel.hasAlpha()) {
                GraphicsUtil.coerceData(dst, colorModel, false);
            }
            Any2sRGBRed.applyLut_INT(dst, Any2LsRGBRed.sRGBToLsRGBLut);
            return dst;
        }
        if (colorModel == null) {
            final float[][] array = null;
            float[][] matrix = null;
            switch (sampleModel.getNumBands()) {
                case 1: {
                    matrix = new float[1][3];
                    matrix[0][0] = 1.0f;
                    matrix[0][1] = 1.0f;
                    matrix[0][2] = 1.0f;
                    break;
                }
                case 2: {
                    matrix = new float[2][4];
                    matrix[0][0] = 1.0f;
                    matrix[0][1] = 1.0f;
                    matrix[0][2] = 1.0f;
                    matrix[1][3] = 1.0f;
                    break;
                }
                case 3: {
                    matrix = new float[3][3];
                    matrix[0][0] = 1.0f;
                    matrix[1][1] = 1.0f;
                    matrix[2][2] = 1.0f;
                    break;
                }
                default: {
                    matrix = new float[sampleModel.getNumBands()][4];
                    matrix[0][0] = 1.0f;
                    matrix[1][1] = 1.0f;
                    matrix[2][2] = 1.0f;
                    matrix[3][3] = 1.0f;
                    break;
                }
            }
            new BandCombineOp(matrix, null).filter(cachableRed.getData(dst.getBounds()), dst);
        }
        else {
            final ColorModel colorModel2 = this.getColorModel();
            BufferedImage dest;
            if (!colorModel2.hasAlpha()) {
                dest = new BufferedImage(colorModel2, dst.createWritableTranslatedChild(0, 0), colorModel2.isAlphaPremultiplied(), null);
            }
            else {
                final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)dst.getSampleModel();
                final int[] bitMasks = singlePixelPackedSampleModel.getBitMasks();
                dest = new BufferedImage(GraphicsUtil.Linear_sRGB, Raster.createWritableRaster(new SinglePixelPackedSampleModel(singlePixelPackedSampleModel.getDataType(), singlePixelPackedSampleModel.getWidth(), singlePixelPackedSampleModel.getHeight(), singlePixelPackedSampleModel.getScanlineStride(), new int[] { bitMasks[0], bitMasks[1], bitMasks[2] }), dst.getDataBuffer(), new Point(0, 0)).createWritableChild(dst.getMinX() - dst.getSampleModelTranslateX(), dst.getMinY() - dst.getSampleModelTranslateY(), dst.getWidth(), dst.getHeight(), 0, 0, null), false, null);
            }
            ColorModel coerceData = colorModel;
            WritableRaster writableRaster;
            if (colorModel.hasAlpha() && colorModel.isAlphaPremultiplied()) {
                final Rectangle bounds = dst.getBounds();
                writableRaster = Raster.createWritableRaster(colorModel.createCompatibleSampleModel(bounds.width, bounds.height), new Point(bounds.x, bounds.y));
                cachableRed.copyData(writableRaster);
                coerceData = GraphicsUtil.coerceData(writableRaster, colorModel, false);
            }
            else {
                writableRaster = GraphicsUtil.makeRasterWritable(cachableRed.getData(dst.getBounds()));
            }
            new ColorConvertOp(null).filter(new BufferedImage(coerceData, writableRaster.createWritableTranslatedChild(0, 0), false, null), dest);
            if (colorModel2.hasAlpha()) {
                AbstractRed.copyBand(writableRaster, sampleModel.getNumBands() - 1, dst, this.getSampleModel().getNumBands() - 1);
            }
        }
        return dst;
    }
    
    protected static ColorModel fixColorModel(final CachableRed cachableRed) {
        final ColorModel colorModel = cachableRed.getColorModel();
        if (colorModel != null) {
            if (colorModel.hasAlpha()) {
                return GraphicsUtil.Linear_sRGB_Unpre;
            }
            return GraphicsUtil.Linear_sRGB;
        }
        else {
            switch (cachableRed.getSampleModel().getNumBands()) {
                case 1: {
                    return GraphicsUtil.Linear_sRGB;
                }
                case 2: {
                    return GraphicsUtil.Linear_sRGB_Unpre;
                }
                case 3: {
                    return GraphicsUtil.Linear_sRGB;
                }
                default: {
                    return GraphicsUtil.Linear_sRGB_Unpre;
                }
            }
        }
    }
    
    protected static SampleModel fixSampleModel(final CachableRed cachableRed) {
        final SampleModel sampleModel = cachableRed.getSampleModel();
        final ColorModel colorModel = cachableRed.getColorModel();
        boolean hasAlpha = false;
        if (colorModel != null) {
            hasAlpha = colorModel.hasAlpha();
        }
        else {
            switch (sampleModel.getNumBands()) {
                case 1:
                case 3: {
                    hasAlpha = false;
                    break;
                }
                default: {
                    hasAlpha = true;
                    break;
                }
            }
        }
        if (hasAlpha) {
            return new SinglePixelPackedSampleModel(3, sampleModel.getWidth(), sampleModel.getHeight(), new int[] { 16711680, 65280, 255, -16777216 });
        }
        return new SinglePixelPackedSampleModel(3, sampleModel.getWidth(), sampleModel.getHeight(), new int[] { 16711680, 65280, 255 });
    }
    
    static {
        sRGBToLsRGBLut = new int[256];
        for (int i = 0; i < 256; ++i) {
            Any2LsRGBRed.sRGBToLsRGBLut[i] = (int)Math.round(sRGBToLsRGB(i * 0.00392156862745098) * 255.0);
        }
    }
}
