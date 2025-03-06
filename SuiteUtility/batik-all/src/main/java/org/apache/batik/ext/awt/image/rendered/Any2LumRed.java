// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.SampleModel;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.ComponentColorModel;
import java.awt.color.ColorSpace;
import java.awt.image.PixelInterleavedSampleModel;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.RenderingHints;
import java.awt.image.BandCombineOp;
import java.awt.image.WritableRaster;
import org.apache.batik.ext.awt.ColorSpaceHintKey;
import java.util.Map;

public class Any2LumRed extends AbstractRed
{
    public Any2LumRed(final CachableRed cachableRed) {
        super(cachableRed, cachableRed.getBounds(), fixColorModel(cachableRed), fixSampleModel(cachableRed), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
        this.props.put("org.apache.batik.gvt.filter.Colorspace", ColorSpaceHintKey.VALUE_COLORSPACE_GREY);
    }
    
    public WritableRaster copyData(final WritableRaster dst) {
        final CachableRed cachableRed = this.getSources().get(0);
        final SampleModel sampleModel = cachableRed.getSampleModel();
        final ColorModel colorModel = cachableRed.getColorModel();
        final Raster data = cachableRed.getData(dst.getBounds());
        if (colorModel == null) {
            final float[][] array = null;
            float[][] matrix;
            if (sampleModel.getNumBands() == 2) {
                matrix = new float[2][2];
                matrix[0][0] = 1.0f;
                matrix[1][1] = 1.0f;
            }
            else {
                matrix = new float[sampleModel.getNumBands()][1];
                matrix[0][0] = 1.0f;
            }
            new BandCombineOp(matrix, null).filter(data, dst);
        }
        else {
            final WritableRaster writableRaster = (WritableRaster)data;
            if (colorModel.hasAlpha()) {
                GraphicsUtil.coerceData(writableRaster, colorModel, false);
            }
            final BufferedImage src = new BufferedImage(colorModel, writableRaster.createWritableTranslatedChild(0, 0), false, null);
            final ColorModel colorModel2 = this.getColorModel();
            BufferedImage dest;
            if (!colorModel2.hasAlpha()) {
                dest = new BufferedImage(colorModel2, dst.createWritableTranslatedChild(0, 0), colorModel2.isAlphaPremultiplied(), null);
            }
            else {
                final PixelInterleavedSampleModel pixelInterleavedSampleModel = (PixelInterleavedSampleModel)dst.getSampleModel();
                dest = new BufferedImage(new ComponentColorModel(ColorSpace.getInstance(1003), new int[] { 8 }, false, false, 1, 0), Raster.createWritableRaster(new PixelInterleavedSampleModel(pixelInterleavedSampleModel.getDataType(), pixelInterleavedSampleModel.getWidth(), pixelInterleavedSampleModel.getHeight(), pixelInterleavedSampleModel.getPixelStride(), pixelInterleavedSampleModel.getScanlineStride(), new int[] { 0 }), dst.getDataBuffer(), new Point(0, 0)).createWritableChild(dst.getMinX() - dst.getSampleModelTranslateX(), dst.getMinY() - dst.getSampleModelTranslateY(), dst.getWidth(), dst.getHeight(), 0, 0, null), false, null);
            }
            new ColorConvertOp(null).filter(src, dest);
            if (colorModel2.hasAlpha()) {
                AbstractRed.copyBand(writableRaster, sampleModel.getNumBands() - 1, dst, this.getSampleModel().getNumBands() - 1);
                if (colorModel2.isAlphaPremultiplied()) {
                    GraphicsUtil.multiplyAlpha(dst);
                }
            }
        }
        return dst;
    }
    
    protected static ColorModel fixColorModel(final CachableRed cachableRed) {
        final ColorModel colorModel = cachableRed.getColorModel();
        if (colorModel != null) {
            if (colorModel.hasAlpha()) {
                return new ComponentColorModel(ColorSpace.getInstance(1003), new int[] { 8, 8 }, true, colorModel.isAlphaPremultiplied(), 3, 0);
            }
            return new ComponentColorModel(ColorSpace.getInstance(1003), new int[] { 8 }, false, false, 1, 0);
        }
        else {
            if (cachableRed.getSampleModel().getNumBands() == 2) {
                return new ComponentColorModel(ColorSpace.getInstance(1003), new int[] { 8, 8 }, true, true, 3, 0);
            }
            return new ComponentColorModel(ColorSpace.getInstance(1003), new int[] { 8 }, false, false, 1, 0);
        }
    }
    
    protected static SampleModel fixSampleModel(final CachableRed cachableRed) {
        final SampleModel sampleModel = cachableRed.getSampleModel();
        final int width = sampleModel.getWidth();
        final int height = sampleModel.getHeight();
        final ColorModel colorModel = cachableRed.getColorModel();
        if (colorModel != null) {
            if (colorModel.hasAlpha()) {
                return new PixelInterleavedSampleModel(0, width, height, 2, 2 * width, new int[] { 0, 1 });
            }
            return new PixelInterleavedSampleModel(0, width, height, 1, width, new int[] { 0 });
        }
        else {
            if (sampleModel.getNumBands() == 2) {
                return new PixelInterleavedSampleModel(0, width, height, 2, 2 * width, new int[] { 0, 1 });
            }
            return new PixelInterleavedSampleModel(0, width, height, 1, width, new int[] { 0 });
        }
    }
}
