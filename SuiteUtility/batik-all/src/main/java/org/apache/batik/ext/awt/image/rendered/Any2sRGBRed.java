// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.Raster;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.RenderingHints;
import java.awt.image.BandCombineOp;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.SampleModel;
import java.awt.image.ColorModel;
import java.awt.color.ColorSpace;
import java.util.Map;

public class Any2sRGBRed extends AbstractRed
{
    boolean srcIsLsRGB;
    private static final double GAMMA = 2.4;
    private static final int[] linearToSRGBLut;
    
    public Any2sRGBRed(final CachableRed cachableRed) {
        super(cachableRed, cachableRed.getBounds(), fixColorModel(cachableRed), fixSampleModel(cachableRed), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
        this.srcIsLsRGB = false;
        final ColorModel colorModel = cachableRed.getColorModel();
        if (colorModel == null) {
            return;
        }
        if (colorModel.getColorSpace() == ColorSpace.getInstance(1004)) {
            this.srcIsLsRGB = true;
        }
    }
    
    public static boolean is_INT_PACK_COMP(final SampleModel sampleModel) {
        if (!(sampleModel instanceof SinglePixelPackedSampleModel)) {
            return false;
        }
        if (sampleModel.getDataType() != 3) {
            return false;
        }
        final int[] bitMasks = ((SinglePixelPackedSampleModel)sampleModel).getBitMasks();
        return (bitMasks.length == 3 || bitMasks.length == 4) && bitMasks[0] == 16711680 && bitMasks[1] == 65280 && bitMasks[2] == 255 && (bitMasks.length != 4 || bitMasks[3] == -16777216);
    }
    
    public static WritableRaster applyLut_INT(final WritableRaster writableRaster, final int[] array) {
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final DataBufferInt dataBufferInt = (DataBufferInt)writableRaster.getDataBuffer();
        final int n = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final int[] array2 = dataBufferInt.getBankData()[0];
        final int width = writableRaster.getWidth();
        final int height = writableRaster.getHeight();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        for (int i = 0; i < height; ++i) {
            for (int j = n + i * scanlineStride; j < j + width; ++j) {
                final int n2 = array2[j];
                array2[j] = ((n2 & 0xFF000000) | array[n2 >>> 16 & 0xFF] << 16 | array[n2 >>> 8 & 0xFF] << 8 | array[n2 & 0xFF]);
            }
        }
        return writableRaster;
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        final CachableRed cachableRed = this.getSources().get(0);
        final ColorModel colorModel = cachableRed.getColorModel();
        final SampleModel sampleModel = cachableRed.getSampleModel();
        if (this.srcIsLsRGB && is_INT_PACK_COMP(writableRaster.getSampleModel())) {
            cachableRed.copyData(writableRaster);
            if (colorModel.hasAlpha()) {
                GraphicsUtil.coerceData(writableRaster, colorModel, false);
            }
            applyLut_INT(writableRaster, Any2sRGBRed.linearToSRGBLut);
            return writableRaster;
        }
        if (colorModel == null) {
            final float[][] array = null;
            float[][] matrix = null;
            switch (sampleModel.getNumBands()) {
                case 1: {
                    matrix = new float[3][1];
                    matrix[0][0] = 1.0f;
                    matrix[1][0] = 1.0f;
                    matrix[2][0] = 1.0f;
                    break;
                }
                case 2: {
                    matrix = new float[4][2];
                    matrix[0][0] = 1.0f;
                    matrix[1][0] = 1.0f;
                    matrix[2][0] = 1.0f;
                    matrix[3][1] = 1.0f;
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
                    matrix = new float[4][sampleModel.getNumBands()];
                    matrix[0][0] = 1.0f;
                    matrix[1][1] = 1.0f;
                    matrix[2][2] = 1.0f;
                    matrix[3][3] = 1.0f;
                    break;
                }
            }
            new BandCombineOp(matrix, null).filter(cachableRed.getData(writableRaster.getBounds()), writableRaster);
            return writableRaster;
        }
        if (colorModel.getColorSpace() == ColorSpace.getInstance(1003)) {
            try {
                final float[][] array2 = null;
                float[][] matrix2 = null;
                switch (sampleModel.getNumBands()) {
                    case 1: {
                        matrix2 = new float[3][1];
                        matrix2[0][0] = 1.0f;
                        matrix2[1][0] = 1.0f;
                        matrix2[2][0] = 1.0f;
                        break;
                    }
                    default: {
                        matrix2 = new float[4][2];
                        matrix2[0][0] = 1.0f;
                        matrix2[1][0] = 1.0f;
                        matrix2[2][0] = 1.0f;
                        matrix2[3][1] = 1.0f;
                        break;
                    }
                }
                new BandCombineOp(matrix2, null).filter(cachableRed.getData(writableRaster.getBounds()), writableRaster);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            return writableRaster;
        }
        final ColorModel colorModel2 = this.getColorModel();
        if (colorModel.getColorSpace() == colorModel2.getColorSpace()) {
            if (is_INT_PACK_COMP(sampleModel)) {
                cachableRed.copyData(writableRaster);
            }
            else {
                GraphicsUtil.copyData(cachableRed.getData(writableRaster.getBounds()), writableRaster);
            }
            return writableRaster;
        }
        final WritableRaster writableRaster2 = (WritableRaster)cachableRed.getData(writableRaster.getBounds());
        ColorModel coerceData = colorModel;
        if (colorModel.hasAlpha()) {
            coerceData = GraphicsUtil.coerceData(writableRaster2, colorModel, false);
        }
        final BufferedImage filter = new ColorConvertOp(colorModel2.getColorSpace(), null).filter(new BufferedImage(coerceData, writableRaster2.createWritableTranslatedChild(0, 0), false, null), null);
        final WritableRaster writableTranslatedChild = writableRaster.createWritableTranslatedChild(0, 0);
        for (int i = 0; i < colorModel2.getColorSpace().getNumComponents(); ++i) {
            AbstractRed.copyBand(filter.getRaster(), i, writableTranslatedChild, i);
        }
        if (colorModel2.hasAlpha()) {
            AbstractRed.copyBand(writableRaster2, sampleModel.getNumBands() - 1, writableRaster, this.getSampleModel().getNumBands() - 1);
        }
        return writableRaster;
    }
    
    protected static ColorModel fixColorModel(final CachableRed cachableRed) {
        final ColorModel colorModel = cachableRed.getColorModel();
        if (colorModel != null) {
            if (colorModel.hasAlpha()) {
                return GraphicsUtil.sRGB_Unpre;
            }
            return GraphicsUtil.sRGB;
        }
        else {
            switch (cachableRed.getSampleModel().getNumBands()) {
                case 1: {
                    return GraphicsUtil.sRGB;
                }
                case 2: {
                    return GraphicsUtil.sRGB_Unpre;
                }
                case 3: {
                    return GraphicsUtil.sRGB;
                }
                default: {
                    return GraphicsUtil.sRGB_Unpre;
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
        linearToSRGBLut = new int[256];
        for (int i = 0; i < 256; ++i) {
            final double a = i * 0.00392156862745098;
            double n;
            if (a <= 0.0031308) {
                n = a * 12.92;
            }
            else {
                n = 1.055 * Math.pow(a, 0.4166666666666667) - 0.055;
            }
            Any2sRGBRed.linearToSRGBLut[i] = (int)Math.round(n * 255.0);
        }
    }
}
