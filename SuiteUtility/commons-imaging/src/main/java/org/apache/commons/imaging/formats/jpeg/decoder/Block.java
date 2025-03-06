// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.jpeg.decoder;

final class Block
{
    final int[] samples;
    final int width;
    final int height;
    
    Block(final int width, final int height) {
        this.samples = new int[width * height];
        this.width = width;
        this.height = height;
    }
}
