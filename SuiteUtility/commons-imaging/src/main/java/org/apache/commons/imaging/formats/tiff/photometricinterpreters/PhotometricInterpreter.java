// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.tiff.photometricinterpreters;

import java.io.IOException;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.common.ImageBuilder;

public abstract class PhotometricInterpreter
{
    protected final int samplesPerPixel;
    private final int[] bitsPerSample;
    protected final int predictor;
    protected final int width;
    protected final int height;
    
    public PhotometricInterpreter(final int samplesPerPixel, final int[] bitsPerSample, final int predictor, final int width, final int height) {
        this.samplesPerPixel = samplesPerPixel;
        this.bitsPerSample = bitsPerSample;
        this.predictor = predictor;
        this.width = width;
        this.height = height;
    }
    
    public abstract void interpretPixel(final ImageBuilder p0, final int[] p1, final int p2, final int p3) throws ImageReadException, IOException;
    
    protected int getBitsPerSample(final int offset) {
        return this.bitsPerSample[offset];
    }
}
