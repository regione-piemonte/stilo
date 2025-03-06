// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.gif;

class GraphicControlExtension extends GifBlock
{
    public final int packed;
    public final int dispose;
    public final boolean transparency;
    public final int delay;
    public final int transparentColorIndex;
    
    GraphicControlExtension(final int blockCode, final int packed, final int dispose, final boolean transparency, final int delay, final int transparentColorIndex) {
        super(blockCode);
        this.packed = packed;
        this.dispose = dispose;
        this.transparency = transparency;
        this.delay = delay;
        this.transparentColorIndex = transparentColorIndex;
    }
}
