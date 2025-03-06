// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

class ColorGroupCut
{
    public final ColorGroup less;
    public final ColorGroup more;
    public final ColorComponent mode;
    public final int limit;
    
    ColorGroupCut(final ColorGroup less, final ColorGroup more, final ColorComponent mode, final int limit) {
        this.less = less;
        this.more = more;
        this.mode = mode;
        this.limit = limit;
    }
    
    public ColorGroup getColorGroup(final int argb) {
        final int value = this.mode.argbComponent(argb);
        if (value <= this.limit) {
            return this.less;
        }
        return this.more;
    }
}
