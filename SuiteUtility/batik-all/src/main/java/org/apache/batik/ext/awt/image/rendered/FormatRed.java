// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.color.ColorSpace;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.ComponentSampleModel;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.WritableRaster;
import java.util.Map;
import java.awt.image.SampleModel;
import java.awt.image.DirectColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ColorModel;

public class FormatRed extends AbstractRed
{
    public static CachableRed construct(final CachableRed cachableRed, final ColorModel colorModel) {
        final ColorModel colorModel2 = cachableRed.getColorModel();
        if (colorModel.hasAlpha() != colorModel2.hasAlpha() || colorModel.isAlphaPremultiplied() != colorModel2.isAlphaPremultiplied()) {
            return new FormatRed(cachableRed, colorModel);
        }
        if (colorModel.getNumComponents() != colorModel2.getNumComponents()) {
            throw new IllegalArgumentException("Incompatible ColorModel given");
        }
        if (colorModel2 instanceof ComponentColorModel && colorModel instanceof ComponentColorModel) {
            return cachableRed;
        }
        if (colorModel2 instanceof DirectColorModel && colorModel instanceof DirectColorModel) {
            return cachableRed;
        }
        return new FormatRed(cachableRed, colorModel);
    }
    
    public FormatRed(final CachableRed cachableRed, final SampleModel sampleModel) {
        super(cachableRed, cachableRed.getBounds(), makeColorModel(cachableRed, sampleModel), sampleModel, cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
    }
    
    public FormatRed(final CachableRed cachableRed, final ColorModel colorModel) {
        super(cachableRed, cachableRed.getBounds(), colorModel, makeSampleModel(cachableRed, colorModel), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
    }
    
    public CachableRed getSource() {
        return this.getSources().get(0);
    }
    
    public Object getProperty(final String s) {
        return this.getSource().getProperty(s);
    }
    
    public String[] getPropertyNames() {
        return this.getSource().getPropertyNames();
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        final ColorModel colorModel = this.getColorModel();
        final CachableRed source = this.getSource();
        final ColorModel colorModel2 = source.getColorModel();
        final WritableRaster writableRaster2 = Raster.createWritableRaster(source.getSampleModel().createCompatibleSampleModel(writableRaster.getWidth(), writableRaster.getHeight()), new Point(writableRaster.getMinX(), writableRaster.getMinY()));
        this.getSource().copyData(writableRaster2);
        GraphicsUtil.copyData(new BufferedImage(colorModel2, writableRaster2.createWritableTranslatedChild(0, 0), colorModel2.isAlphaPremultiplied(), null), new BufferedImage(colorModel, writableRaster.createWritableTranslatedChild(0, 0), colorModel.isAlphaPremultiplied(), null));
        return writableRaster;
    }
    
    public static SampleModel makeSampleModel(final CachableRed cachableRed, final ColorModel colorModel) {
        final SampleModel sampleModel = cachableRed.getSampleModel();
        return colorModel.createCompatibleSampleModel(sampleModel.getWidth(), sampleModel.getHeight());
    }
    
    public static ColorModel makeColorModel(final CachableRed cachableRed, final SampleModel sampleModel) {
        final ColorModel colorModel = cachableRed.getColorModel();
        final ColorSpace colorSpace = colorModel.getColorSpace();
        final int numBands = sampleModel.getNumBands();
        final int dataType = sampleModel.getDataType();
        int n = 0;
        switch (dataType) {
            case 0: {
                n = 8;
                break;
            }
            case 2: {
                n = 16;
                break;
            }
            case 1: {
                n = 16;
                break;
            }
            case 3: {
                n = 32;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported DataBuffer type: " + dataType);
            }
        }
        boolean hasAlpha = colorModel.hasAlpha();
        if (hasAlpha) {
            if (numBands == colorModel.getNumComponents() - 1) {
                hasAlpha = false;
            }
            else if (numBands != colorModel.getNumComponents()) {
                throw new IllegalArgumentException("Incompatible number of bands in and out");
            }
        }
        else if (numBands == colorModel.getNumComponents() + 1) {
            hasAlpha = true;
        }
        else if (numBands != colorModel.getNumComponents()) {
            throw new IllegalArgumentException("Incompatible number of bands in and out");
        }
        boolean alphaPremultiplied = colorModel.isAlphaPremultiplied();
        if (!hasAlpha) {
            alphaPremultiplied = false;
        }
        if (sampleModel instanceof ComponentSampleModel) {
            final int[] bits = new int[numBands];
            for (int i = 0; i < numBands; ++i) {
                bits[i] = n;
            }
            return new ComponentColorModel(colorSpace, bits, hasAlpha, alphaPremultiplied, hasAlpha ? 3 : 1, dataType);
        }
        if (!(sampleModel instanceof SinglePixelPackedSampleModel)) {
            throw new IllegalArgumentException("Unsupported SampleModel Type");
        }
        final int[] bitMasks = ((SinglePixelPackedSampleModel)sampleModel).getBitMasks();
        if (numBands == 4) {
            return new DirectColorModel(colorSpace, n, bitMasks[0], bitMasks[1], bitMasks[2], bitMasks[3], alphaPremultiplied, dataType);
        }
        if (numBands == 3) {
            return new DirectColorModel(colorSpace, n, bitMasks[0], bitMasks[1], bitMasks[2], 0, alphaPremultiplied, dataType);
        }
        throw new IllegalArgumentException("Incompatible number of bands out for ColorModel");
    }
}
