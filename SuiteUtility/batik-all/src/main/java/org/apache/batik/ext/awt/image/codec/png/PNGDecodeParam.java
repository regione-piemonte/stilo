// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.codec.png;

import org.apache.batik.ext.awt.image.codec.util.PropertyUtil;
import org.apache.batik.ext.awt.image.codec.util.ImageDecodeParam;

public class PNGDecodeParam implements ImageDecodeParam
{
    private boolean suppressAlpha;
    private boolean expandPalette;
    private boolean output8BitGray;
    private boolean performGammaCorrection;
    private float userExponent;
    private float displayExponent;
    private boolean expandGrayAlpha;
    private boolean generateEncodeParam;
    private PNGEncodeParam encodeParam;
    
    public PNGDecodeParam() {
        this.suppressAlpha = false;
        this.expandPalette = false;
        this.output8BitGray = false;
        this.performGammaCorrection = true;
        this.userExponent = 1.0f;
        this.displayExponent = 2.2f;
        this.expandGrayAlpha = false;
        this.generateEncodeParam = false;
        this.encodeParam = null;
    }
    
    public boolean getSuppressAlpha() {
        return this.suppressAlpha;
    }
    
    public void setSuppressAlpha(final boolean suppressAlpha) {
        this.suppressAlpha = suppressAlpha;
    }
    
    public boolean getExpandPalette() {
        return this.expandPalette;
    }
    
    public void setExpandPalette(final boolean expandPalette) {
        this.expandPalette = expandPalette;
    }
    
    public boolean getOutput8BitGray() {
        return this.output8BitGray;
    }
    
    public void setOutput8BitGray(final boolean output8BitGray) {
        this.output8BitGray = output8BitGray;
    }
    
    public boolean getPerformGammaCorrection() {
        return this.performGammaCorrection;
    }
    
    public void setPerformGammaCorrection(final boolean performGammaCorrection) {
        this.performGammaCorrection = performGammaCorrection;
    }
    
    public float getUserExponent() {
        return this.userExponent;
    }
    
    public void setUserExponent(final float userExponent) {
        if (userExponent <= 0.0f) {
            throw new IllegalArgumentException(PropertyUtil.getString("PNGDecodeParam0"));
        }
        this.userExponent = userExponent;
    }
    
    public float getDisplayExponent() {
        return this.displayExponent;
    }
    
    public void setDisplayExponent(final float displayExponent) {
        if (displayExponent <= 0.0f) {
            throw new IllegalArgumentException(PropertyUtil.getString("PNGDecodeParam1"));
        }
        this.displayExponent = displayExponent;
    }
    
    public boolean getExpandGrayAlpha() {
        return this.expandGrayAlpha;
    }
    
    public void setExpandGrayAlpha(final boolean expandGrayAlpha) {
        this.expandGrayAlpha = expandGrayAlpha;
    }
    
    public boolean getGenerateEncodeParam() {
        return this.generateEncodeParam;
    }
    
    public void setGenerateEncodeParam(final boolean generateEncodeParam) {
        this.generateEncodeParam = generateEncodeParam;
    }
    
    public PNGEncodeParam getEncodeParam() {
        return this.encodeParam;
    }
    
    public void setEncodeParam(final PNGEncodeParam encodeParam) {
        this.encodeParam = encodeParam;
    }
}
