// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.rendered;

import java.awt.image.DirectColorModel;
import java.awt.Graphics2D;
import java.awt.image.ColorConvertOp;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.Point;
import java.awt.image.BandedSampleModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.WritableRaster;
import java.util.Map;
import org.apache.batik.ext.awt.color.ICCColorSpaceExt;
import java.awt.image.ColorModel;
import java.awt.color.ColorSpace;

public class ProfileRed extends AbstractRed
{
    private static final ColorSpace sRGBCS;
    private static final ColorModel sRGBCM;
    private ICCColorSpaceExt colorSpace;
    
    public ProfileRed(final CachableRed cachableRed, final ICCColorSpaceExt colorSpace) {
        this.colorSpace = colorSpace;
        this.init(cachableRed, cachableRed.getBounds(), ProfileRed.sRGBCM, ProfileRed.sRGBCM.createCompatibleSampleModel(cachableRed.getWidth(), cachableRed.getHeight()), cachableRed.getTileGridXOffset(), cachableRed.getTileGridYOffset(), null);
    }
    
    public CachableRed getSource() {
        return this.getSources().get(0);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        try {
            final CachableRed source = this.getSource();
            ColorModel colorModel = source.getColorModel();
            final ColorSpace colorSpace = colorModel.getColorSpace();
            if (colorSpace.getNumComponents() != this.colorSpace.getNumComponents()) {
                System.err.println("Input image and associated color profile have mismatching number of color components: conversion is not possible");
                return writableRaster;
            }
            final int width = writableRaster.getWidth();
            final int height = writableRaster.getHeight();
            final int minX = writableRaster.getMinX();
            final int minY = writableRaster.getMinY();
            WritableRaster writableRaster2 = colorModel.createCompatibleWritableRaster(width, height).createWritableTranslatedChild(minX, minY);
            source.copyData(writableRaster2);
            if (!(colorModel instanceof ComponentColorModel) || !(source.getSampleModel() instanceof BandedSampleModel) || (colorModel.hasAlpha() && colorModel.isAlphaPremultiplied())) {
                final ComponentColorModel cm = new ComponentColorModel(colorSpace, colorModel.getComponentSize(), colorModel.hasAlpha(), false, colorModel.getTransparency(), 0);
                final WritableRaster bandedRaster = Raster.createBandedRaster(0, writableRaster.getWidth(), writableRaster.getHeight(), cm.getNumComponents(), new Point(0, 0));
                final BufferedImage bufferedImage = new BufferedImage(cm, bandedRaster, cm.isAlphaPremultiplied(), null);
                final BufferedImage bufferedImage2 = new BufferedImage(colorModel, writableRaster2.createWritableTranslatedChild(0, 0), colorModel.isAlphaPremultiplied(), null);
                final Graphics2D graphics = bufferedImage.createGraphics();
                graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                graphics.drawImage(bufferedImage2, 0, 0, null);
                colorModel = cm;
                writableRaster2 = bandedRaster.createWritableTranslatedChild(minX, minY);
            }
            final ComponentColorModel cm2 = new ComponentColorModel(this.colorSpace, colorModel.getComponentSize(), false, false, 1, 0);
            final DataBufferByte dataBuffer = (DataBufferByte)writableRaster2.getDataBuffer();
            final BufferedImage src = new BufferedImage(cm2, Raster.createBandedRaster(dataBuffer, writableRaster.getWidth(), writableRaster.getHeight(), writableRaster.getWidth(), new int[] { 0, 1, 2 }, new int[] { 0, 0, 0 }, new Point(0, 0)), cm2.isAlphaPremultiplied(), null);
            final ComponentColorModel cm3 = new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8 }, false, false, 1, 0);
            final WritableRaster bandedRaster2 = Raster.createBandedRaster(0, writableRaster.getWidth(), writableRaster.getHeight(), cm3.getNumComponents(), new Point(0, 0));
            BufferedImage dest = new BufferedImage(cm3, bandedRaster2, false, null);
            new ColorConvertOp(null).filter(src, dest);
            if (colorModel.hasAlpha()) {
                final DataBufferByte dataBufferByte = (DataBufferByte)bandedRaster2.getDataBuffer();
                final byte[][] bankData = dataBuffer.getBankData();
                final byte[][] bankData2 = dataBufferByte.getBankData();
                dest = new BufferedImage(new ComponentColorModel(ColorSpace.getInstance(1000), new int[] { 8, 8, 8, 8 }, true, false, 3, 0), Raster.createBandedRaster(new DataBufferByte(new byte[][] { bankData2[0], bankData2[1], bankData2[2], bankData[3] }, bankData[0].length), writableRaster.getWidth(), writableRaster.getHeight(), writableRaster.getWidth(), new int[] { 0, 1, 2, 3 }, new int[] { 0, 0, 0, 0 }, new Point(0, 0)), false, null);
            }
            final Graphics2D graphics2 = new BufferedImage(ProfileRed.sRGBCM, writableRaster.createWritableTranslatedChild(0, 0), false, null).createGraphics();
            graphics2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            graphics2.drawImage(dest, 0, 0, null);
            graphics2.dispose();
            return writableRaster;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new Error(ex.getMessage());
        }
    }
    
    static {
        sRGBCS = ColorSpace.getInstance(1000);
        sRGBCM = new DirectColorModel(ProfileRed.sRGBCS, 32, 16711680, 65280, 255, -16777216, false, 3);
    }
}
