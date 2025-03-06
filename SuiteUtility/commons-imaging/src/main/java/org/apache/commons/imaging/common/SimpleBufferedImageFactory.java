// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.awt.image.BufferedImage;

public class SimpleBufferedImageFactory implements BufferedImageFactory
{
    @Override
    public BufferedImage getColorBufferedImage(final int width, final int height, final boolean hasAlpha) {
        if (hasAlpha) {
            return new BufferedImage(width, height, 2);
        }
        return new BufferedImage(width, height, 1);
    }
    
    @Override
    public BufferedImage getGrayscaleBufferedImage(final int width, final int height, final boolean hasAlpha) {
        if (hasAlpha) {
            return new BufferedImage(width, height, 2);
        }
        return new BufferedImage(width, height, 10);
    }
}
