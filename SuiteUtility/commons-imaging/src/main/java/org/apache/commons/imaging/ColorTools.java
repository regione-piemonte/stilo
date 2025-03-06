// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging;

import java.awt.image.ColorConvertOp;
import java.awt.RenderingHints;
import java.awt.image.DirectColorModel;
import java.awt.image.ComponentColorModel;
import java.util.Hashtable;
import java.awt.image.ColorModel;
import java.awt.image.ImagingOpException;
import java.io.IOException;
import java.awt.color.ICC_Profile;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.io.File;
import java.awt.image.BufferedImage;

public class ColorTools
{
    public BufferedImage correctImage(final BufferedImage src, final File file) throws ImageReadException, IOException {
        final ICC_Profile icc = Imaging.getICCProfile(file);
        if (icc == null) {
            return src;
        }
        final ICC_ColorSpace cs = new ICC_ColorSpace(icc);
        return this.convertFromColorSpace(src, cs);
    }
    
    public BufferedImage relabelColorSpace(final BufferedImage bi, final ICC_Profile profile) throws ImagingOpException {
        final ICC_ColorSpace cs = new ICC_ColorSpace(profile);
        return this.relabelColorSpace(bi, cs);
    }
    
    public BufferedImage relabelColorSpace(final BufferedImage bi, final ColorSpace cs) throws ImagingOpException {
        final ColorModel cm = this.deriveColorModel(bi, cs);
        return this.relabelColorSpace(bi, cm);
    }
    
    public BufferedImage relabelColorSpace(final BufferedImage bi, final ColorModel cm) throws ImagingOpException {
        return new BufferedImage(cm, bi.getRaster(), false, null);
    }
    
    public ColorModel deriveColorModel(final BufferedImage bi, final ColorSpace cs) throws ImagingOpException {
        return this.deriveColorModel(bi, cs, false);
    }
    
    public ColorModel deriveColorModel(final BufferedImage bi, final ColorSpace cs, final boolean forceNoAlpha) throws ImagingOpException {
        return this.deriveColorModel(bi.getColorModel(), cs, forceNoAlpha);
    }
    
    public ColorModel deriveColorModel(final ColorModel colorModel, final ColorSpace cs, final boolean forceNoAlpha) throws ImagingOpException {
        if (colorModel instanceof ComponentColorModel) {
            final ComponentColorModel ccm = (ComponentColorModel)colorModel;
            if (forceNoAlpha) {
                return new ComponentColorModel(cs, false, false, 1, ccm.getTransferType());
            }
            return new ComponentColorModel(cs, ccm.hasAlpha(), ccm.isAlphaPremultiplied(), ccm.getTransparency(), ccm.getTransferType());
        }
        else {
            if (colorModel instanceof DirectColorModel) {
                final DirectColorModel dcm = (DirectColorModel)colorModel;
                final int oldMask = dcm.getRedMask() | dcm.getGreenMask() | dcm.getBlueMask() | dcm.getAlphaMask();
                final int oldBits = this.countBitsInMask(oldMask);
                return new DirectColorModel(cs, oldBits, dcm.getRedMask(), dcm.getGreenMask(), dcm.getBlueMask(), dcm.getAlphaMask(), dcm.isAlphaPremultiplied(), dcm.getTransferType());
            }
            throw new ImagingOpException("Could not clone unknown ColorModel Type.");
        }
    }
    
    private int countBitsInMask(int i) {
        int count = 0;
        while (i != 0) {
            count += (i & 0x1);
            i >>>= 1;
        }
        return count;
    }
    
    public BufferedImage convertToColorSpace(final BufferedImage bi, final ColorSpace to) {
        final ColorSpace from = bi.getColorModel().getColorSpace();
        final RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        final ColorConvertOp op = new ColorConvertOp(from, to, hints);
        BufferedImage result = op.filter(bi, null);
        result = this.relabelColorSpace(result, to);
        return result;
    }
    
    public BufferedImage convertTosRGB(final BufferedImage bi) {
        final ColorModel srgbCM = ColorModel.getRGBdefault();
        return this.convertToColorSpace(bi, srgbCM.getColorSpace());
    }
    
    protected BufferedImage convertFromColorSpace(final BufferedImage bi, final ColorSpace from) {
        final ColorModel srgbCM = ColorModel.getRGBdefault();
        return this.convertBetweenColorSpaces(bi, from, srgbCM.getColorSpace());
    }
    
    public BufferedImage convertBetweenICCProfiles(final BufferedImage bi, final ICC_Profile from, final ICC_Profile to) {
        final ICC_ColorSpace csFrom = new ICC_ColorSpace(from);
        final ICC_ColorSpace csTo = new ICC_ColorSpace(to);
        return this.convertBetweenColorSpaces(bi, csFrom, csTo);
    }
    
    public BufferedImage convertToICCProfile(final BufferedImage bi, final ICC_Profile to) {
        final ICC_ColorSpace csTo = new ICC_ColorSpace(to);
        return this.convertToColorSpace(bi, csTo);
    }
    
    public BufferedImage convertBetweenColorSpacesX2(BufferedImage bi, final ColorSpace from, final ColorSpace to) {
        final RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        bi = this.relabelColorSpace(bi, from);
        final ColorConvertOp op = new ColorConvertOp(from, to, hints);
        bi = op.filter(bi, null);
        bi = this.relabelColorSpace(bi, from);
        bi = op.filter(bi, null);
        bi = this.relabelColorSpace(bi, to);
        return bi;
    }
    
    public BufferedImage convertBetweenColorSpaces(BufferedImage bi, final ColorSpace from, final ColorSpace to) {
        final RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        final ColorConvertOp op = new ColorConvertOp(from, to, hints);
        bi = this.relabelColorSpace(bi, from);
        BufferedImage result = op.filter(bi, null);
        result = this.relabelColorSpace(result, to);
        return result;
    }
}
