// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

class ColorCount
{
    public final int argb;
    public int count;
    public final int alpha;
    public final int red;
    public final int green;
    public final int blue;
    
    ColorCount(final int argb) {
        this.argb = argb;
        this.alpha = (0xFF & argb >> 24);
        this.red = (0xFF & argb >> 16);
        this.green = (0xFF & argb >> 8);
        this.blue = (0xFF & argb >> 0);
    }
    
    @Override
    public int hashCode() {
        return this.argb;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof ColorCount) {
            final ColorCount other = (ColorCount)o;
            return other.argb == this.argb;
        }
        return false;
    }
}
