// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.DirectColorModel;
import java.awt.image.DataBufferInt;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.Raster;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.WritableRaster;
import java.awt.image.Kernel;
import java.awt.image.SampleModel;
import java.awt.image.ColorModel;
import java.awt.Rectangle;
import java.util.Map;
import java.awt.image.ConvolveOp;
import java.awt.RenderingHints;

public class GaussianBlurRed8Bit extends AbstractRed
{
    int xinset;
    int yinset;
    double stdDevX;
    double stdDevY;
    RenderingHints hints;
    ConvolveOp[] convOp;
    int dX;
    int dY;
    static final float SQRT2PI;
    static final float DSQRT2PI;
    static final float precision = 0.499f;
    
    public GaussianBlurRed8Bit(final CachableRed cachableRed, final double n, final RenderingHints renderingHints) {
        this(cachableRed, n, n, renderingHints);
    }
    
    public GaussianBlurRed8Bit(final CachableRed cachableRed, final double stdDevX, final double stdDevY, final RenderingHints hints) {
        this.convOp = new ConvolveOp[2];
        this.stdDevX = stdDevX;
        this.stdDevY = stdDevY;
        this.hints = hints;
        this.xinset = surroundPixels(stdDevX, hints);
        this.yinset = surroundPixels(stdDevY, hints);
        final Rectangle bounds;
        final Rectangle rectangle = bounds = cachableRed.getBounds();
        bounds.x += this.xinset;
        final Rectangle rectangle2 = rectangle;
        rectangle2.y += this.yinset;
        final Rectangle rectangle3 = rectangle;
        rectangle3.width -= 2 * this.xinset;
        final Rectangle rectangle4 = rectangle;
        rectangle4.height -= 2 * this.yinset;
        if (rectangle.width <= 0 || rectangle.height <= 0) {
            rectangle.width = 0;
            rectangle.height = 0;
        }
        final ColorModel fixColorModel = fixColorModel(cachableRed);
        final SampleModel sampleModel = cachableRed.getSampleModel();
        int w = sampleModel.getWidth();
        int h = sampleModel.getHeight();
        if (w > rectangle.width) {
            w = rectangle.width;
        }
        if (h > rectangle.height) {
            h = rectangle.height;
        }
        this.init(cachableRed, rectangle, fixColorModel, fixColorModel.createCompatibleSampleModel(w, h), cachableRed.getTileGridXOffset() + this.xinset, cachableRed.getTileGridYOffset() + this.yinset, null);
        final boolean b = this.hints != null && RenderingHints.VALUE_RENDER_QUALITY.equals(this.hints.get(RenderingHints.KEY_RENDERING));
        if (this.xinset != 0 && (stdDevX < 2.0 || b)) {
            this.convOp[0] = new ConvolveOp(this.makeQualityKernelX(this.xinset * 2 + 1));
        }
        else {
            this.dX = (int)Math.floor(GaussianBlurRed8Bit.DSQRT2PI * stdDevX + 0.5);
        }
        if (this.yinset != 0 && (stdDevY < 2.0 || b)) {
            this.convOp[1] = new ConvolveOp(this.makeQualityKernelY(this.yinset * 2 + 1));
        }
        else {
            this.dY = (int)Math.floor(GaussianBlurRed8Bit.DSQRT2PI * stdDevY + 0.5);
        }
    }
    
    public static int surroundPixels(final double n) {
        return surroundPixels(n, null);
    }
    
    public static int surroundPixels(final double n, final RenderingHints renderingHints) {
        final boolean b = renderingHints != null && RenderingHints.VALUE_RENDER_QUALITY.equals(renderingHints.get(RenderingHints.KEY_RENDERING));
        if (n < 2.0 || b) {
            float n2;
            int n3;
            for (n2 = (float)(0.5 / (n * GaussianBlurRed8Bit.SQRT2PI)), n3 = 0; n2 < 0.499f; n2 += (float)(Math.pow(2.718281828459045, -n3 * n3 / (2.0 * n * n)) / (n * GaussianBlurRed8Bit.SQRT2PI)), ++n3) {}
            return n3;
        }
        final int n4 = (int)Math.floor(GaussianBlurRed8Bit.DSQRT2PI * n + 0.5);
        if (n4 % 2 == 0) {
            return n4 - 1 + n4 / 2;
        }
        return n4 - 2 + n4 / 2;
    }
    
    private float[] computeQualityKernelData(final int n, final double n2) {
        final float[] array = new float[n];
        final int n3 = n / 2;
        float n4 = 0.0f;
        for (int i = 0; i < n; ++i) {
            array[i] = (float)(Math.pow(2.718281828459045, -(i - n3) * (i - n3) / (2.0 * n2 * n2)) / (GaussianBlurRed8Bit.SQRT2PI * n2));
            n4 += array[i];
        }
        for (int j = 0; j < n; ++j) {
            final float[] array2 = array;
            final int n5 = j;
            array2[n5] /= n4;
        }
        return array;
    }
    
    private Kernel makeQualityKernelX(final int width) {
        return new Kernel(width, 1, this.computeQualityKernelData(width, this.stdDevX));
    }
    
    private Kernel makeQualityKernelY(final int height) {
        return new Kernel(1, height, this.computeQualityKernelData(height, this.stdDevY));
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        final CachableRed cachableRed = this.getSources().get(0);
        final Rectangle bounds;
        final Rectangle rectangle = bounds = writableRaster.getBounds();
        bounds.x -= this.xinset;
        final Rectangle rectangle2 = rectangle;
        rectangle2.y -= this.yinset;
        final Rectangle rectangle3 = rectangle;
        rectangle3.width += 2 * this.xinset;
        final Rectangle rectangle4 = rectangle;
        rectangle4.height += 2 * this.yinset;
        final ColorModel colorModel = cachableRed.getColorModel();
        WritableRaster compatibleWritableRaster = null;
        WritableRaster writableRaster2 = colorModel.createCompatibleWritableRaster(rectangle.width, rectangle.height);
        cachableRed.copyData(writableRaster2.createWritableTranslatedChild(rectangle.x, rectangle.y));
        if (colorModel.hasAlpha() && !colorModel.isAlphaPremultiplied()) {
            GraphicsUtil.coerceData(writableRaster2, colorModel, true);
        }
        int xOrigin;
        if (this.xinset == 0) {
            xOrigin = 0;
        }
        else if (this.convOp[0] != null) {
            final WritableRaster filter = this.convOp[0].filter(writableRaster2, this.getColorModel().createCompatibleWritableRaster(rectangle.width, rectangle.height));
            xOrigin = this.convOp[0].getKernel().getXOrigin();
            final WritableRaster writableRaster3 = writableRaster2;
            writableRaster2 = filter;
            compatibleWritableRaster = writableRaster3;
        }
        else if ((this.dX & 0x1) == 0x0) {
            final WritableRaster boxFilterH = this.boxFilterH(writableRaster2, writableRaster2, 0, 0, this.dX, this.dX / 2);
            final WritableRaster boxFilterH2 = this.boxFilterH(boxFilterH, boxFilterH, this.dX / 2, 0, this.dX, this.dX / 2 - 1);
            writableRaster2 = this.boxFilterH(boxFilterH2, boxFilterH2, this.dX - 1, 0, this.dX + 1, this.dX / 2);
            xOrigin = this.dX - 1 + this.dX / 2;
        }
        else {
            final WritableRaster boxFilterH3 = this.boxFilterH(writableRaster2, writableRaster2, 0, 0, this.dX, this.dX / 2);
            final WritableRaster boxFilterH4 = this.boxFilterH(boxFilterH3, boxFilterH3, this.dX / 2, 0, this.dX, this.dX / 2);
            writableRaster2 = this.boxFilterH(boxFilterH4, boxFilterH4, this.dX - 2, 0, this.dX, this.dX / 2);
            xOrigin = this.dX - 2 + this.dX / 2;
        }
        WritableRaster filter2;
        if (this.yinset == 0) {
            filter2 = writableRaster2;
        }
        else if (this.convOp[1] != null) {
            if (compatibleWritableRaster == null) {
                compatibleWritableRaster = this.getColorModel().createCompatibleWritableRaster(rectangle.width, rectangle.height);
            }
            filter2 = this.convOp[1].filter(writableRaster2, compatibleWritableRaster);
        }
        else {
            WritableRaster writableRaster4;
            if ((this.dY & 0x1) == 0x0) {
                final WritableRaster boxFilterV = this.boxFilterV(writableRaster2, writableRaster2, xOrigin, 0, this.dY, this.dY / 2);
                final WritableRaster boxFilterV2 = this.boxFilterV(boxFilterV, boxFilterV, xOrigin, this.dY / 2, this.dY, this.dY / 2 - 1);
                writableRaster4 = this.boxFilterV(boxFilterV2, boxFilterV2, xOrigin, this.dY - 1, this.dY + 1, this.dY / 2);
            }
            else {
                final WritableRaster boxFilterV3 = this.boxFilterV(writableRaster2, writableRaster2, xOrigin, 0, this.dY, this.dY / 2);
                final WritableRaster boxFilterV4 = this.boxFilterV(boxFilterV3, boxFilterV3, xOrigin, this.dY / 2, this.dY, this.dY / 2);
                writableRaster4 = this.boxFilterV(boxFilterV4, boxFilterV4, xOrigin, this.dY - 2, this.dY, this.dY / 2);
            }
            filter2 = writableRaster4;
        }
        GraphicsUtil.copyData(filter2.createWritableTranslatedChild(rectangle.x, rectangle.y), writableRaster);
        return writableRaster;
    }
    
    private WritableRaster boxFilterH(final Raster raster, final WritableRaster writableRaster, final int n, final int n2, final int n3, final int n4) {
        final int width = raster.getWidth();
        final int height = raster.getHeight();
        if (width < 2 * n + n3) {
            return writableRaster;
        }
        if (height < 2 * n2) {
            return writableRaster;
        }
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)raster.getSampleModel();
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel2 = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final int scanlineStride2 = singlePixelPackedSampleModel2.getScanlineStride();
        final DataBufferInt dataBufferInt = (DataBufferInt)raster.getDataBuffer();
        final DataBufferInt dataBufferInt2 = (DataBufferInt)writableRaster.getDataBuffer();
        final int n5 = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(raster.getMinX() - raster.getSampleModelTranslateX(), raster.getMinY() - raster.getSampleModelTranslateY());
        final int n6 = dataBufferInt2.getOffset() + singlePixelPackedSampleModel2.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final int[] array = dataBufferInt.getBankData()[0];
        final int[] array2 = dataBufferInt2.getBankData()[0];
        final int[] array3 = new int[n3];
        final int n7 = 16777216 / n3;
        for (int i = n2; i < height - n2; ++i) {
            final int n8 = n5 + i * scanlineStride;
            final int n9 = n6 + i * scanlineStride2;
            final int n10 = n8 + (width - n);
            int n11 = 0;
            int n12 = 0;
            int n13 = 0;
            int n14 = 0;
            int n15 = 0;
            int j;
            for (j = n8 + n; j < j + n3; ++j) {
                final int[] array4 = array3;
                final int n16 = n11;
                final int n17 = array[j];
                array4[n16] = n17;
                final int n18 = n17;
                n12 += n18 >>> 24;
                n13 += (n18 >> 16 & 0xFF);
                n14 += (n18 >> 8 & 0xFF);
                n15 += (n18 & 0xFF);
                ++n11;
            }
            int n19 = n9 + (n + n4);
            final int[] array5 = array2;
            final int n20 = n19;
            final int n21 = (n12 * n7 & 0xFF000000) | (n13 * n7 & 0xFF000000) >>> 8 | (n14 * n7 & 0xFF000000) >>> 16 | (n15 * n7 & 0xFF000000) >>> 24;
            array5[n20] = n21;
            int n22 = n21;
            ++n19;
            int n23 = 0;
            while (j < n10) {
                final int n24 = array3[n23];
                if (n24 == array[j]) {
                    array2[n19] = n22;
                }
                else {
                    final int n25 = n12 - (n24 >>> 24);
                    final int n26 = n13 - (n24 >> 16 & 0xFF);
                    final int n27 = n14 - (n24 >> 8 & 0xFF);
                    final int n28 = n15 - (n24 & 0xFF);
                    final int[] array6 = array3;
                    final int n29 = n23;
                    final int n30 = array[j];
                    array6[n29] = n30;
                    final int n31 = n30;
                    n12 = n25 + (n31 >>> 24);
                    n13 = n26 + (n31 >> 16 & 0xFF);
                    n14 = n27 + (n31 >> 8 & 0xFF);
                    n15 = n28 + (n31 & 0xFF);
                    final int[] array7 = array2;
                    final int n32 = n19;
                    final int n33 = (n12 * n7 & 0xFF000000) | (n13 * n7 & 0xFF000000) >>> 8 | (n14 * n7 & 0xFF000000) >>> 16 | (n15 * n7 & 0xFF000000) >>> 24;
                    array7[n32] = n33;
                    n22 = n33;
                }
                n23 = (n23 + 1) % n3;
                ++j;
                ++n19;
            }
        }
        return writableRaster;
    }
    
    private WritableRaster boxFilterV(final Raster raster, final WritableRaster writableRaster, final int n, final int n2, final int n3, final int n4) {
        final int width = raster.getWidth();
        final int height = raster.getHeight();
        if (width < 2 * n) {
            return writableRaster;
        }
        if (height < 2 * n2 + n3) {
            return writableRaster;
        }
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)raster.getSampleModel();
        final SinglePixelPackedSampleModel singlePixelPackedSampleModel2 = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
        final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
        final int scanlineStride2 = singlePixelPackedSampleModel2.getScanlineStride();
        final DataBufferInt dataBufferInt = (DataBufferInt)raster.getDataBuffer();
        final DataBufferInt dataBufferInt2 = (DataBufferInt)writableRaster.getDataBuffer();
        final int n5 = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(raster.getMinX() - raster.getSampleModelTranslateX(), raster.getMinY() - raster.getSampleModelTranslateY());
        final int n6 = dataBufferInt2.getOffset() + singlePixelPackedSampleModel2.getOffset(writableRaster.getMinX() - writableRaster.getSampleModelTranslateX(), writableRaster.getMinY() - writableRaster.getSampleModelTranslateY());
        final int[] array = dataBufferInt.getBankData()[0];
        final int[] array2 = dataBufferInt2.getBankData()[0];
        final int[] array3 = new int[n3];
        final int n7 = 16777216 / n3;
        for (int i = n; i < width - n; ++i) {
            final int n8 = n5 + i;
            final int n9 = n6 + i;
            final int n10 = n8 + (height - n2) * scanlineStride;
            int n11 = 0;
            int n12 = 0;
            int n13 = 0;
            int n14 = 0;
            int n15 = 0;
            int j;
            for (j = n8 + n2 * scanlineStride; j < j + n3 * scanlineStride; j += scanlineStride) {
                final int[] array4 = array3;
                final int n16 = n11;
                final int n17 = array[j];
                array4[n16] = n17;
                final int n18 = n17;
                n12 += n18 >>> 24;
                n13 += (n18 >> 16 & 0xFF);
                n14 += (n18 >> 8 & 0xFF);
                n15 += (n18 & 0xFF);
                ++n11;
            }
            final int n19 = n9 + (n2 + n4) * scanlineStride2;
            final int[] array5 = array2;
            final int n20 = n19;
            final int n21 = (n12 * n7 & 0xFF000000) | (n13 * n7 & 0xFF000000) >>> 8 | (n14 * n7 & 0xFF000000) >>> 16 | (n15 * n7 & 0xFF000000) >>> 24;
            array5[n20] = n21;
            int n22 = n21;
            int n23 = n19 + scanlineStride2;
            int n24 = 0;
            while (j < n10) {
                final int n25 = array3[n24];
                if (n25 == array[j]) {
                    array2[n23] = n22;
                }
                else {
                    final int n26 = n12 - (n25 >>> 24);
                    final int n27 = n13 - (n25 >> 16 & 0xFF);
                    final int n28 = n14 - (n25 >> 8 & 0xFF);
                    final int n29 = n15 - (n25 & 0xFF);
                    final int[] array6 = array3;
                    final int n30 = n24;
                    final int n31 = array[j];
                    array6[n30] = n31;
                    final int n32 = n31;
                    n12 = n26 + (n32 >>> 24);
                    n13 = n27 + (n32 >> 16 & 0xFF);
                    n14 = n28 + (n32 >> 8 & 0xFF);
                    n15 = n29 + (n32 & 0xFF);
                    final int[] array7 = array2;
                    final int n33 = n23;
                    final int n34 = (n12 * n7 & 0xFF000000) | (n13 * n7 & 0xFF000000) >>> 8 | (n14 * n7 & 0xFF000000) >>> 16 | (n15 * n7 & 0xFF000000) >>> 24;
                    array7[n33] = n34;
                    n22 = n34;
                }
                n24 = (n24 + 1) % n3;
                j += scanlineStride;
                n23 += scanlineStride2;
            }
        }
        return writableRaster;
    }
    
    protected static ColorModel fixColorModel(final CachableRed cachableRed) {
        final ColorModel colorModel = cachableRed.getColorModel();
        final int numBands = cachableRed.getSampleModel().getNumBands();
        final int[] array = new int[4];
        switch (numBands) {
            case 1: {
                array[0] = 255;
                break;
            }
            case 2: {
                array[0] = 255;
                array[3] = 65280;
                break;
            }
            case 3: {
                array[0] = 16711680;
                array[1] = 65280;
                array[2] = 255;
                break;
            }
            case 4: {
                array[0] = 16711680;
                array[1] = 65280;
                array[2] = 255;
                array[3] = -16777216;
                break;
            }
            default: {
                throw new IllegalArgumentException("GaussianBlurRed8Bit only supports one to four band images");
            }
        }
        return new DirectColorModel(colorModel.getColorSpace(), 8 * numBands, array[0], array[1], array[2], array[3], true, 3);
    }
    
    static {
        SQRT2PI = (float)Math.sqrt(6.283185307179586);
        DSQRT2PI = GaussianBlurRed8Bit.SQRT2PI * 3.0f / 4.0f;
    }
}
