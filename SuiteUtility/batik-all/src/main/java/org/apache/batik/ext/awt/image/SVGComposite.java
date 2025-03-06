// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image;

import java.awt.image.DataBufferInt;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.awt.CompositeContext;
import java.awt.RenderingHints;
import java.awt.image.PackedColorModel;
import java.awt.image.ColorModel;
import java.awt.AlphaComposite;
import java.awt.Composite;

public class SVGComposite implements Composite
{
    public static final SVGComposite OVER;
    public static final SVGComposite IN;
    public static final SVGComposite OUT;
    public static final SVGComposite ATOP;
    public static final SVGComposite XOR;
    public static final SVGComposite MULTIPLY;
    public static final SVGComposite SCREEN;
    public static final SVGComposite DARKEN;
    public static final SVGComposite LIGHTEN;
    CompositeRule rule;
    
    public CompositeRule getRule() {
        return this.rule;
    }
    
    public SVGComposite(final CompositeRule rule) {
        this.rule = rule;
    }
    
    public boolean equals(final Object o) {
        if (o instanceof SVGComposite) {
            return ((SVGComposite)o).getRule() == this.getRule();
        }
        if (!(o instanceof AlphaComposite)) {
            return false;
        }
        final AlphaComposite alphaComposite = (AlphaComposite)o;
        switch (this.getRule().getRule()) {
            case 1: {
                return alphaComposite == AlphaComposite.SrcOver;
            }
            case 2: {
                return alphaComposite == AlphaComposite.SrcIn;
            }
            case 3: {
                return alphaComposite == AlphaComposite.SrcOut;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean is_INT_PACK(final ColorModel colorModel) {
        if (!(colorModel instanceof PackedColorModel)) {
            return false;
        }
        final int[] masks = ((PackedColorModel)colorModel).getMasks();
        return masks.length == 4 && masks[0] == 16711680 && masks[1] == 65280 && masks[2] == 255 && masks[3] == -16777216;
    }
    
    public CompositeContext createContext(final ColorModel colorModel, final ColorModel colorModel2, final RenderingHints renderingHints) {
        final boolean b = this.is_INT_PACK(colorModel) && this.is_INT_PACK(colorModel2);
        switch (this.rule.getRule()) {
            case 1: {
                if (!colorModel2.hasAlpha()) {
                    if (b) {
                        return new OverCompositeContext_INT_PACK_NA(colorModel, colorModel2);
                    }
                    return new OverCompositeContext_NA(colorModel, colorModel2);
                }
                else {
                    if (!b) {
                        return new OverCompositeContext(colorModel, colorModel2);
                    }
                    if (colorModel.isAlphaPremultiplied()) {
                        return new OverCompositeContext_INT_PACK(colorModel, colorModel2);
                    }
                    return new OverCompositeContext_INT_PACK_UNPRE(colorModel, colorModel2);
                }
                break;
            }
            case 2: {
                if (b) {
                    return new InCompositeContext_INT_PACK(colorModel, colorModel2);
                }
                return new InCompositeContext(colorModel, colorModel2);
            }
            case 3: {
                if (b) {
                    return new OutCompositeContext_INT_PACK(colorModel, colorModel2);
                }
                return new OutCompositeContext(colorModel, colorModel2);
            }
            case 4: {
                if (b) {
                    return new AtopCompositeContext_INT_PACK(colorModel, colorModel2);
                }
                return new AtopCompositeContext(colorModel, colorModel2);
            }
            case 5: {
                if (b) {
                    return new XorCompositeContext_INT_PACK(colorModel, colorModel2);
                }
                return new XorCompositeContext(colorModel, colorModel2);
            }
            case 6: {
                final float[] coefficients = this.rule.getCoefficients();
                if (b) {
                    return new ArithCompositeContext_INT_PACK_LUT(colorModel, colorModel2, coefficients[0], coefficients[1], coefficients[2], coefficients[3]);
                }
                return new ArithCompositeContext(colorModel, colorModel2, coefficients[0], coefficients[1], coefficients[2], coefficients[3]);
            }
            case 7: {
                if (b) {
                    return new MultiplyCompositeContext_INT_PACK(colorModel, colorModel2);
                }
                return new MultiplyCompositeContext(colorModel, colorModel2);
            }
            case 8: {
                if (b) {
                    return new ScreenCompositeContext_INT_PACK(colorModel, colorModel2);
                }
                return new ScreenCompositeContext(colorModel, colorModel2);
            }
            case 9: {
                if (b) {
                    return new DarkenCompositeContext_INT_PACK(colorModel, colorModel2);
                }
                return new DarkenCompositeContext(colorModel, colorModel2);
            }
            case 10: {
                if (b) {
                    return new LightenCompositeContext_INT_PACK(colorModel, colorModel2);
                }
                return new LightenCompositeContext(colorModel, colorModel2);
            }
            default: {
                throw new UnsupportedOperationException("Unknown composite rule requested.");
            }
        }
    }
    
    static {
        OVER = new SVGComposite(CompositeRule.OVER);
        IN = new SVGComposite(CompositeRule.IN);
        OUT = new SVGComposite(CompositeRule.OUT);
        ATOP = new SVGComposite(CompositeRule.ATOP);
        XOR = new SVGComposite(CompositeRule.XOR);
        MULTIPLY = new SVGComposite(CompositeRule.MULTIPLY);
        SCREEN = new SVGComposite(CompositeRule.SCREEN);
        DARKEN = new SVGComposite(CompositeRule.DARKEN);
        LIGHTEN = new SVGComposite(CompositeRule.LIGHTEN);
    }
    
    public static class LightenCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        LightenCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    final int n10 = n8 >>> 24;
                    final int n11 = n9 >>> 24;
                    final int n12 = (255 - n11) * 65793;
                    final int n13 = (255 - n10) * 65793;
                    final int n14 = n10 + n11 - (n10 * n11 * 65793 + 8388608 >>> 24);
                    final int n15 = n8 >> 16 & 0xFF;
                    final int n16 = n9 >> 16 & 0xFF;
                    int n17 = (n12 * n15 + 8388608 >>> 24) + n16;
                    final int n18 = (n13 * n16 + 8388608 >>> 24) + n15;
                    if (n17 < n18) {
                        n17 = n18;
                    }
                    final int n19 = n8 >> 8 & 0xFF;
                    final int n20 = n9 >> 8 & 0xFF;
                    int n21 = (n12 * n19 + 8388608 >>> 24) + n20;
                    final int n22 = (n13 * n20 + 8388608 >>> 24) + n19;
                    if (n21 < n22) {
                        n21 = n22;
                    }
                    final int n23 = n8 & 0xFF;
                    final int n24 = n9 & 0xFF;
                    int n25 = (n12 * n23 + 8388608 >>> 24) + n24;
                    final int n26 = (n13 * n24 + 8388608 >>> 24) + n23;
                    if (n25 < n26) {
                        n25 = n26;
                    }
                    array3[i++] = ((n14 & 0xFF) << 24 | (n17 & 0xFF) << 16 | (n21 & 0xFF) << 8 | (n25 & 0xFF));
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public abstract static class AlphaPreCompositeContext_INT_PACK extends AlphaPreCompositeContext
    {
        AlphaPreCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        protected abstract void precompose_INT_PACK(final int p0, final int p1, final int[] p2, final int p3, final int p4, final int[] p5, final int p6, final int p7, final int[] p8, final int p9, final int p10);
        
        protected void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            final int height = writableRaster.getHeight();
            final SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)raster.getSampleModel();
            final int scanlineStride = singlePixelPackedSampleModel.getScanlineStride();
            final DataBufferInt dataBufferInt = (DataBufferInt)raster.getDataBuffer();
            final int[] array = dataBufferInt.getBankData()[0];
            final int n = dataBufferInt.getOffset() + singlePixelPackedSampleModel.getOffset(minX - raster.getSampleModelTranslateX(), minY - raster.getSampleModelTranslateY());
            final SinglePixelPackedSampleModel singlePixelPackedSampleModel2 = (SinglePixelPackedSampleModel)raster2.getSampleModel();
            final int scanlineStride2 = singlePixelPackedSampleModel2.getScanlineStride();
            final DataBufferInt dataBufferInt2 = (DataBufferInt)raster2.getDataBuffer();
            final int[] array2 = dataBufferInt2.getBankData()[0];
            final int n2 = dataBufferInt2.getOffset() + singlePixelPackedSampleModel2.getOffset(minX - raster2.getSampleModelTranslateX(), minY - raster2.getSampleModelTranslateY());
            final SinglePixelPackedSampleModel singlePixelPackedSampleModel3 = (SinglePixelPackedSampleModel)writableRaster.getSampleModel();
            final int scanlineStride3 = singlePixelPackedSampleModel3.getScanlineStride();
            final DataBufferInt dataBufferInt3 = (DataBufferInt)writableRaster.getDataBuffer();
            this.precompose_INT_PACK(width, height, array, scanlineStride - width, n, array2, scanlineStride2 - width, n2, dataBufferInt3.getBankData()[0], scanlineStride3 - width, dataBufferInt3.getOffset() + singlePixelPackedSampleModel3.getOffset(minX - writableRaster.getSampleModelTranslateX(), minY - writableRaster.getSampleModelTranslateY()));
        }
    }
    
    public abstract static class AlphaPreCompositeContext implements CompositeContext
    {
        ColorModel srcCM;
        ColorModel dstCM;
        
        AlphaPreCompositeContext(final ColorModel srcCM, final ColorModel dstCM) {
            this.srcCM = srcCM;
            this.dstCM = dstCM;
        }
        
        public void dispose() {
            this.srcCM = null;
            this.dstCM = null;
        }
        
        protected abstract void precompose(final Raster p0, final Raster p1, final WritableRaster p2);
        
        public void compose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            ColorModel colorModel = this.srcCM;
            if (!this.srcCM.isAlphaPremultiplied()) {
                colorModel = GraphicsUtil.coerceData((WritableRaster)raster, this.srcCM, true);
            }
            ColorModel colorModel2 = this.dstCM;
            if (!this.dstCM.isAlphaPremultiplied()) {
                colorModel2 = GraphicsUtil.coerceData((WritableRaster)raster2, this.dstCM, true);
            }
            this.precompose(raster, raster2, writableRaster);
            if (!this.srcCM.isAlphaPremultiplied()) {
                GraphicsUtil.coerceData((WritableRaster)raster, colorModel, false);
            }
            if (!this.dstCM.isAlphaPremultiplied()) {
                GraphicsUtil.coerceData(writableRaster, colorModel2, false);
                if (raster2 != writableRaster) {
                    GraphicsUtil.coerceData((WritableRaster)raster2, colorModel2, false);
                }
            }
        }
    }
    
    public static class LightenCompositeContext extends AlphaPreCompositeContext
    {
        LightenCompositeContext(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                for (int j = 0; j < width * 4; ++j, pixels2[j] = pixels[j] + pixels2[j] - (pixels2[j] * pixels[j] * 65793 + 8388608 >>> 24), ++j) {
                    final int n2 = 255 - pixels2[j + 3];
                    final int n3 = 255 - pixels[j + 3];
                    final int n4 = (n2 * pixels[j] * 65793 + 8388608 >>> 24) + pixels2[j];
                    final int n5 = (n3 * pixels2[j] * 65793 + 8388608 >>> 24) + pixels[j];
                    if (n4 > n5) {
                        pixels2[j] = n4;
                    }
                    else {
                        pixels2[j] = n5;
                    }
                    ++j;
                    final int n6 = (n2 * pixels[j] * 65793 + 8388608 >>> 24) + pixels2[j];
                    final int n7 = (n3 * pixels2[j] * 65793 + 8388608 >>> 24) + pixels[j];
                    if (n6 > n7) {
                        pixels2[j] = n6;
                    }
                    else {
                        pixels2[j] = n7;
                    }
                    ++j;
                    final int n8 = (n2 * pixels[j] * 65793 + 8388608 >>> 24) + pixels2[j];
                    final int n9 = (n3 * pixels2[j] * 65793 + 8388608 >>> 24) + pixels[j];
                    if (n8 > n9) {
                        pixels2[j] = n8;
                    }
                    else {
                        pixels2[j] = n9;
                    }
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class DarkenCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        DarkenCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    final int n10 = n8 >>> 24;
                    final int n11 = n9 >>> 24;
                    final int n12 = (255 - n11) * 65793;
                    final int n13 = (255 - n10) * 65793;
                    final int n14 = n10 + n11 - (n10 * n11 * 65793 + 8388608 >>> 24);
                    final int n15 = n8 >> 16 & 0xFF;
                    final int n16 = n9 >> 16 & 0xFF;
                    int n17 = (n12 * n15 + 8388608 >>> 24) + n16;
                    final int n18 = (n13 * n16 + 8388608 >>> 24) + n15;
                    if (n17 > n18) {
                        n17 = n18;
                    }
                    final int n19 = n8 >> 8 & 0xFF;
                    final int n20 = n9 >> 8 & 0xFF;
                    int n21 = (n12 * n19 + 8388608 >>> 24) + n20;
                    final int n22 = (n13 * n20 + 8388608 >>> 24) + n19;
                    if (n21 > n22) {
                        n21 = n22;
                    }
                    final int n23 = n8 & 0xFF;
                    final int n24 = n9 & 0xFF;
                    int n25 = (n12 * n23 + 8388608 >>> 24) + n24;
                    final int n26 = (n13 * n24 + 8388608 >>> 24) + n23;
                    if (n25 > n26) {
                        n25 = n26;
                    }
                    array3[i++] = ((n14 & 0xFF) << 24 | (n17 & 0xFF) << 16 | (n21 & 0xFF) << 8 | (n25 & 0xFF));
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class DarkenCompositeContext extends AlphaPreCompositeContext
    {
        DarkenCompositeContext(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                for (int j = 0; j < width * 4; ++j, pixels2[j] = pixels[j] + pixels2[j] - (pixels2[j] * pixels[j] * 65793 + 8388608 >>> 24), ++j) {
                    final int n2 = 255 - pixels2[j + 3];
                    final int n3 = 255 - pixels[j + 3];
                    final int n4 = (n2 * pixels[j] * 65793 + 8388608 >>> 24) + pixels2[j];
                    final int n5 = (n3 * pixels2[j] * 65793 + 8388608 >>> 24) + pixels[j];
                    if (n4 > n5) {
                        pixels2[j] = n5;
                    }
                    else {
                        pixels2[j] = n4;
                    }
                    ++j;
                    final int n6 = (n2 * pixels[j] * 65793 + 8388608 >>> 24) + pixels2[j];
                    final int n7 = (n3 * pixels2[j] * 65793 + 8388608 >>> 24) + pixels[j];
                    if (n6 > n7) {
                        pixels2[j] = n7;
                    }
                    else {
                        pixels2[j] = n6;
                    }
                    ++j;
                    final int n8 = (n2 * pixels[j] * 65793 + 8388608 >>> 24) + pixels2[j];
                    final int n9 = (n3 * pixels2[j] * 65793 + 8388608 >>> 24) + pixels[j];
                    if (n8 > n9) {
                        pixels2[j] = n9;
                    }
                    else {
                        pixels2[j] = n8;
                    }
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class ScreenCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        ScreenCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    final int n10 = n8 >>> 24;
                    final int n11 = n9 >>> 24;
                    final int n12 = n8 >> 16 & 0xFF;
                    final int n13 = n9 >> 16 & 0xFF;
                    final int n14 = n8 >> 8 & 0xFF;
                    final int n15 = n9 >> 8 & 0xFF;
                    final int n16 = n8 & 0xFF;
                    final int n17 = n9 & 0xFF;
                    array3[i++] = (n12 + n13 - (n12 * n13 * 65793 + 8388608 >>> 24) << 16 | n14 + n15 - (n14 * n15 * 65793 + 8388608 >>> 24) << 8 | n16 + n17 - (n16 * n17 * 65793 + 8388608 >>> 24) | n10 + n11 - (n10 * n11 * 65793 + 8388608 >>> 24) << 24);
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class ScreenCompositeContext extends AlphaPreCompositeContext
    {
        ScreenCompositeContext(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                int n4;
                int n5;
                int n6;
                int n7;
                int n8;
                int n9;
                for (int j = 0; j < width * 4; ++j, n4 = pixels[j], n5 = pixels2[j], pixels2[j] = n4 + n5 - (n5 * n4 * 65793 + 8388608 >>> 24), ++j, n6 = pixels[j], n7 = pixels2[j], pixels2[j] = n6 + n7 - (n7 * n6 * 65793 + 8388608 >>> 24), ++j, n8 = pixels[j], n9 = pixels2[j], pixels2[j] = n8 + n9 - (n9 * n8 * 65793 + 8388608 >>> 24), ++j) {
                    final int n2 = pixels[j];
                    final int n3 = pixels2[j];
                    pixels2[j] = n2 + n3 - (n3 * n2 * 65793 + 8388608 >>> 24);
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class MultiplyCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        MultiplyCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    final int n10 = n8 >>> 24;
                    final int n11 = n9 >>> 24;
                    final int n12 = n8 >> 16 & 0xFF;
                    final int n13 = n9 >> 16 & 0xFF;
                    final int n14 = n8 >> 8 & 0xFF;
                    final int n15 = n9 >> 8 & 0xFF;
                    final int n16 = n8 & 0xFF;
                    final int n17 = n9 & 0xFF;
                    final int n18 = 255 - n11;
                    final int n19 = 255 - n10;
                    array3[i++] = (((n12 * n18 + n13 * n19 + n12 * n13) * 65793 + 8388608 & 0xFF000000) >>> 8 | ((n14 * n18 + n15 * n19 + n14 * n15) * 65793 + 8388608 & 0xFF000000) >>> 16 | (n16 * n18 + n17 * n19 + n16 * n17) * 65793 + 8388608 >>> 24 | n10 + n11 - (n10 * n11 * 65793 + 8388608 >>> 24) << 24);
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class MultiplyCompositeContext extends AlphaPreCompositeContext
    {
        MultiplyCompositeContext(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                int n2;
                int n3;
                for (int j = 0; j < width * 4; ++j, pixels2[j] = (pixels[j] * n2 + pixels2[j] * n3 + pixels[j] * pixels2[j]) * 65793 + 8388608 >>> 24, ++j, pixels2[j] = (pixels[j] * n2 + pixels2[j] * n3 + pixels[j] * pixels2[j]) * 65793 + 8388608 >>> 24, ++j, pixels2[j] = pixels[j] + pixels2[j] - (pixels2[j] * pixels[j] * 65793 + 8388608 >>> 24), ++j) {
                    n2 = 255 - pixels2[j + 3];
                    n3 = 255 - pixels[j + 3];
                    pixels2[j] = (pixels[j] * n2 + pixels2[j] * n3 + pixels[j] * pixels2[j]) * 65793 + 8388608 >>> 24;
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class ArithCompositeContext_INT_PACK_LUT extends AlphaPreCompositeContext_INT_PACK
    {
        byte[] lut;
        
        ArithCompositeContext_INT_PACK_LUT(final ColorModel colorModel, final ColorModel colorModel2, float n, final float n2, final float n3, float n4) {
            super(colorModel, colorModel2);
            n /= 255.0f;
            n4 = n4 * 255.0f + 0.5f;
            final int n5 = 65536;
            this.lut = new byte[n5];
            for (int i = 0; i < n5; ++i) {
                int n6 = (int)((i >> 8) * (i & 0xFF) * n + (i >> 8) * n2 + (i & 0xFF) * n3 + n4);
                if ((n6 & 0xFFFFFF00) != 0x0) {
                    if ((n6 & Integer.MIN_VALUE) != 0x0) {
                        n6 = 0;
                    }
                    else {
                        n6 = 255;
                    }
                }
                this.lut[i] = (byte)n6;
            }
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            final byte[] lut = this.lut;
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    int n10 = 0xFF & lut[(n8 >> 16 & 0xFF00) | n9 >>> 24];
                    final int n11 = 0xFF & lut[(n8 >> 8 & 0xFF00) | (n9 >> 16 & 0xFF)];
                    final int n12 = 0xFF & lut[(n8 & 0xFF00) | (n9 >> 8 & 0xFF)];
                    final int n13 = 0xFF & lut[(n8 << 8 & 0xFF00) | (n9 & 0xFF)];
                    if (n11 > n10) {
                        n10 = n11;
                    }
                    if (n12 > n10) {
                        n10 = n12;
                    }
                    if (n13 > n10) {
                        n10 = n13;
                    }
                    array3[i++] = (n10 << 24 | n11 << 16 | n12 << 8 | n13);
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class ArithCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        float k1;
        float k2;
        float k3;
        float k4;
        
        ArithCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2, final float n, final float k2, final float k3, final float n2) {
            super(colorModel, colorModel2);
            this.k1 = n / 255.0f;
            this.k2 = k2;
            this.k3 = k3;
            this.k4 = n2 * 255.0f + 0.5f;
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    int n10 = (int)((n8 >>> 24) * (n9 >>> 24) * this.k1 + (n8 >>> 24) * this.k2 + (n9 >>> 24) * this.k3 + this.k4);
                    if ((n10 & 0xFFFFFF00) != 0x0) {
                        if ((n10 & Integer.MIN_VALUE) != 0x0) {
                            n10 = 0;
                        }
                        else {
                            n10 = 255;
                        }
                    }
                    int n11 = (int)((n8 >> 16 & 0xFF) * (n9 >> 16 & 0xFF) * this.k1 + (n8 >> 16 & 0xFF) * this.k2 + (n9 >> 16 & 0xFF) * this.k3 + this.k4);
                    if ((n11 & 0xFFFFFF00) != 0x0) {
                        if ((n11 & Integer.MIN_VALUE) != 0x0) {
                            n11 = 0;
                        }
                        else {
                            n11 = 255;
                        }
                    }
                    if (n10 < n11) {
                        n10 = n11;
                    }
                    int n12 = (int)((n8 >> 8 & 0xFF) * (n9 >> 8 & 0xFF) * this.k1 + (n8 >> 8 & 0xFF) * this.k2 + (n9 >> 8 & 0xFF) * this.k3 + this.k4);
                    if ((n12 & 0xFFFFFF00) != 0x0) {
                        if ((n12 & Integer.MIN_VALUE) != 0x0) {
                            n12 = 0;
                        }
                        else {
                            n12 = 255;
                        }
                    }
                    if (n10 < n12) {
                        n10 = n12;
                    }
                    int n13 = (int)((n8 & 0xFF) * (n9 & 0xFF) * this.k1 + (n8 & 0xFF) * this.k2 + (n9 & 0xFF) * this.k3 + this.k4);
                    if ((n13 & 0xFFFFFF00) != 0x0) {
                        if ((n13 & Integer.MIN_VALUE) != 0x0) {
                            n13 = 0;
                        }
                        else {
                            n13 = 255;
                        }
                    }
                    if (n10 < n13) {
                        n10 = n13;
                    }
                    array3[i++] = (n10 << 24 | n11 << 16 | n12 << 8 | n13);
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class ArithCompositeContext extends AlphaPreCompositeContext
    {
        float k1;
        float k2;
        float k3;
        float k4;
        
        ArithCompositeContext(final ColorModel colorModel, final ColorModel colorModel2, final float k1, final float k2, final float k3, final float k4) {
            super(colorModel, colorModel2);
            this.k1 = k1;
            this.k2 = k2;
            this.k3 = k3;
            this.k4 = k4;
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int numBands = writableRaster.getNumBands();
            final int minY = writableRaster.getMinY();
            final int n = minY + writableRaster.getHeight();
            final float n2 = this.k1 / 255.0f;
            final float n3 = this.k4 * 255.0f + 0.5f;
            for (int i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                for (int j = 0; j < pixels.length; ++j) {
                    int n4 = 0;
                    for (int k = 1; k < numBands; ++k, ++j) {
                        int n5 = (int)(n2 * pixels[j] * pixels2[j] + this.k2 * pixels[j] + this.k3 * pixels2[j] + n3);
                        if ((n5 & 0xFFFFFF00) != 0x0) {
                            if ((n5 & Integer.MIN_VALUE) != 0x0) {
                                n5 = 0;
                            }
                            else {
                                n5 = 255;
                            }
                        }
                        if (n5 > n4) {
                            n4 = n5;
                        }
                        pixels2[j] = n5;
                    }
                    int n6 = (int)(n2 * pixels[j] * pixels2[j] + this.k2 * pixels[j] + this.k3 * pixels2[j] + n3);
                    if ((n6 & 0xFFFFFF00) != 0x0) {
                        if ((n6 & Integer.MIN_VALUE) != 0x0) {
                            n6 = 0;
                        }
                        else {
                            n6 = 255;
                        }
                    }
                    if (n6 > n4) {
                        pixels2[j] = n6;
                    }
                    else {
                        pixels2[j] = n4;
                    }
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class XorCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        XorCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    final int n10 = (255 - (n9 >>> 24)) * 65793;
                    final int n11 = (255 - (n8 >>> 24)) * 65793;
                    array3[i++] = (((n8 >>> 24) * n10 + (n9 >>> 24) * n11 + 8388608 & 0xFF000000) | ((n8 >> 16 & 0xFF) * n10 + (n9 >> 16 & 0xFF) * n11 + 8388608 & 0xFF000000) >>> 8 | ((n8 >> 8 & 0xFF) * n10 + (n9 >> 8 & 0xFF) * n11 + 8388608 & 0xFF000000) >>> 16 | (n8 & 0xFF) * n10 + (n9 & 0xFF) * n11 + 8388608 >>> 24);
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class XorCompositeContext extends AlphaPreCompositeContext
    {
        XorCompositeContext(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                int n2;
                int n3;
                for (int j = 0; j < width * 4; ++j, pixels2[j] = pixels[j] * n2 + pixels2[j] * n3 + 8388608 >>> 24, ++j, pixels2[j] = pixels[j] * n2 + pixels2[j] * n3 + 8388608 >>> 24, ++j, pixels2[j] = pixels[j] * n2 + pixels2[j] * n3 + 8388608 >>> 24, ++j) {
                    n2 = (255 - pixels2[j + 3]) * 65793;
                    n3 = (255 - pixels[j + 3]) * 65793;
                    pixels2[j] = pixels[j] * n2 + pixels2[j] * n3 + 8388608 >>> 24;
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class AtopCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        AtopCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    final int n10 = (n9 >>> 24) * 65793;
                    final int n11 = (255 - (n8 >>> 24)) * 65793;
                    array3[i++] = ((n9 & 0xFF000000) | ((n8 >> 16 & 0xFF) * n10 + (n9 >> 16 & 0xFF) * n11 + 8388608 & 0xFF000000) >>> 8 | ((n8 >> 8 & 0xFF) * n10 + (n9 >> 8 & 0xFF) * n11 + 8388608 & 0xFF000000) >>> 16 | (n8 & 0xFF) * n10 + (n9 & 0xFF) * n11 + 8388608 >>> 24);
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class AtopCompositeContext extends AlphaPreCompositeContext
    {
        AtopCompositeContext(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                int n2;
                int n3;
                for (int j = 0; j < width * 4; ++j, pixels2[j] = pixels[j] * n2 + pixels2[j] * n3 + 8388608 >>> 24, ++j, pixels2[j] = pixels[j] * n2 + pixels2[j] * n3 + 8388608 >>> 24, j += 2) {
                    n2 = pixels2[j + 3] * 65793;
                    n3 = (255 - pixels[j + 3]) * 65793;
                    pixels2[j] = pixels[j] * n2 + pixels2[j] * n3 + 8388608 >>> 24;
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class OutCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        OutCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = (255 - (array2[n6++] >>> 24)) * 65793;
                    final int n9 = array[n4++];
                    array3[i++] = (((n9 >>> 24) * n8 + 8388608 & 0xFF000000) | ((n9 >> 16 & 0xFF) * n8 + 8388608 & 0xFF000000) >>> 8 | ((n9 >> 8 & 0xFF) * n8 + 8388608 & 0xFF000000) >>> 16 | (n9 & 0xFF) * n8 + 8388608 >>> 24);
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class OutCompositeContext extends AlphaPreCompositeContext
    {
        OutCompositeContext(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                int n2;
                for (int j = 0; j < width * 4; ++j, pixels2[j] = pixels[j] * n2 + 8388608 >>> 24, ++j, pixels2[j] = pixels[j] * n2 + 8388608 >>> 24, ++j, pixels2[j] = pixels[j] * n2 + 8388608 >>> 24, ++j) {
                    n2 = (255 - pixels2[j + 3]) * 65793;
                    pixels2[j] = pixels[j] * n2 + 8388608 >>> 24;
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class InCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        InCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = (array2[n6++] >>> 24) * 65793;
                    final int n9 = array[n4++];
                    array3[i++] = (((n9 >>> 24) * n8 + 8388608 & 0xFF000000) | ((n9 >> 16 & 0xFF) * n8 + 8388608 & 0xFF000000) >>> 8 | ((n9 >> 8 & 0xFF) * n8 + 8388608 & 0xFF000000) >>> 16 | (n9 & 0xFF) * n8 + 8388608 >>> 24);
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class InCompositeContext extends AlphaPreCompositeContext
    {
        InCompositeContext(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                int n2;
                for (int j = 0; j < width * 4; ++j, pixels2[j] = pixels[j] * n2 + 8388608 >>> 24, ++j, pixels2[j] = pixels[j] * n2 + 8388608 >>> 24, ++j, pixels2[j] = pixels[j] * n2 + 8388608 >>> 24, ++j) {
                    n2 = pixels2[j + 3] * 65793;
                    pixels2[j] = pixels[j] * n2 + 8388608 >>> 24;
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class OverCompositeContext_INT_PACK_UNPRE extends AlphaPreCompositeContext_INT_PACK
    {
        OverCompositeContext_INT_PACK_UNPRE(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
            if (colorModel.isAlphaPremultiplied()) {
                throw new IllegalArgumentException("OverCompositeContext_INT_PACK_UNPRE is only forsources with unpremultiplied alpha");
            }
        }
        
        public void compose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            ColorModel colorModel = this.dstCM;
            if (!this.dstCM.isAlphaPremultiplied()) {
                colorModel = GraphicsUtil.coerceData((WritableRaster)raster2, this.dstCM, true);
            }
            this.precompose(raster, raster2, writableRaster);
            if (!this.dstCM.isAlphaPremultiplied()) {
                GraphicsUtil.coerceData(writableRaster, colorModel, false);
                if (raster2 != writableRaster) {
                    GraphicsUtil.coerceData((WritableRaster)raster2, colorModel, false);
                }
            }
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    final int n10 = (n8 >>> 24) * 65793;
                    final int n11 = (255 - (n8 >>> 24)) * 65793;
                    array3[i++] = (((n8 & 0xFF000000) + (n9 >>> 24) * n11 + 8388608 & 0xFF000000) | ((n8 >> 16 & 0xFF) * n10 + (n9 >> 16 & 0xFF) * n11 + 8388608 & 0xFF000000) >>> 8 | ((n8 >> 8 & 0xFF) * n10 + (n9 >> 8 & 0xFF) * n11 + 8388608 & 0xFF000000) >>> 16 | (n8 & 0xFF) * n10 + (n9 & 0xFF) * n11 + 8388608 >>> 24);
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class OverCompositeContext_INT_PACK_NA extends AlphaPreCompositeContext_INT_PACK
    {
        OverCompositeContext_INT_PACK_NA(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    final int n10 = (255 - (n8 >>> 24)) * 65793;
                    array3[i++] = ((n8 & 0xFF0000) + (((n9 >> 16 & 0xFF) * n10 + 8388608 & 0xFF000000) >>> 8) | (n8 & 0xFF00) + (((n9 >> 8 & 0xFF) * n10 + 8388608 & 0xFF000000) >>> 16) | (n8 & 0xFF) + ((n9 & 0xFF) * n10 + 8388608 >>> 24));
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class OverCompositeContext_INT_PACK extends AlphaPreCompositeContext_INT_PACK
    {
        OverCompositeContext_INT_PACK(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose_INT_PACK(final int n, final int n2, final int[] array, final int n3, int n4, final int[] array2, final int n5, int n6, final int[] array3, final int n7, int i) {
            for (int j = 0; j < n2; ++j) {
                while (i < i + n) {
                    final int n8 = array[n4++];
                    final int n9 = array2[n6++];
                    final int n10 = (255 - (n8 >>> 24)) * 65793;
                    array3[i++] = ((n8 & 0xFF000000) + ((n9 >>> 24) * n10 + 8388608 & 0xFF000000) | (n8 & 0xFF0000) + (((n9 >> 16 & 0xFF) * n10 + 8388608 & 0xFF000000) >>> 8) | (n8 & 0xFF00) + (((n9 >> 8 & 0xFF) * n10 + 8388608 & 0xFF000000) >>> 16) | (n8 & 0xFF) + ((n9 & 0xFF) * n10 + 8388608 >>> 24));
                }
                n4 += n3;
                n6 += n5;
                i += n7;
            }
        }
    }
    
    public static class OverCompositeContext_NA extends AlphaPreCompositeContext
    {
        OverCompositeContext_NA(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                int n3;
                for (int j = 0, n2 = 0; j < width * 4; ++j, ++n2, pixels2[n2] = pixels[j] + (pixels2[n2] * n3 + 8388608 >>> 24), ++j, ++n2, pixels2[n2] = pixels[j] + (pixels2[n2] * n3 + 8388608 >>> 24), j += 2, ++n2) {
                    n3 = (255 - pixels[j + 3]) * 65793;
                    pixels2[n2] = pixels[j] + (pixels2[n2] * n3 + 8388608 >>> 24);
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
    
    public static class OverCompositeContext extends AlphaPreCompositeContext
    {
        OverCompositeContext(final ColorModel colorModel, final ColorModel colorModel2) {
            super(colorModel, colorModel2);
        }
        
        public void precompose(final Raster raster, final Raster raster2, final WritableRaster writableRaster) {
            int[] pixels = null;
            int[] pixels2 = null;
            final int minX = writableRaster.getMinX();
            final int width = writableRaster.getWidth();
            final int minY = writableRaster.getMinY();
            for (int n = minY + writableRaster.getHeight(), i = minY; i < n; ++i) {
                pixels = raster.getPixels(minX, i, width, 1, pixels);
                pixels2 = raster2.getPixels(minX, i, width, 1, pixels2);
                int n2;
                for (int j = 0; j < width * 4; ++j, pixels2[j] = pixels[j] + (pixels2[j] * n2 + 8388608 >>> 24), ++j, pixels2[j] = pixels[j] + (pixels2[j] * n2 + 8388608 >>> 24), ++j, pixels2[j] = pixels[j] + (pixels2[j] * n2 + 8388608 >>> 24), ++j) {
                    n2 = (255 - pixels[j + 3]) * 65793;
                    pixels2[j] = pixels[j] + (pixels2[j] * n2 + 8388608 >>> 24);
                }
                writableRaster.setPixels(minX, i, width, 1, pixels2);
            }
        }
    }
}
