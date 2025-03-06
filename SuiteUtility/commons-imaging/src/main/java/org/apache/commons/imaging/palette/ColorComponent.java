// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

enum ColorComponent
{
    ALPHA(24), 
    RED(16), 
    GREEN(8), 
    BLUE(0);
    
    private final int shift;
    
    private ColorComponent(final int shift) {
        this.shift = shift;
    }
    
    public int argbComponent(final int argb) {
        return argb >> this.shift & 0xFF;
    }
}
