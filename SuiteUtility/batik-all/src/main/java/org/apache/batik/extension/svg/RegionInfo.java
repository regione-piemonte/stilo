// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import java.awt.geom.Rectangle2D;

public class RegionInfo extends Float
{
    private float verticalAlignment;
    
    public RegionInfo(final float x, final float y, final float w, final float h, final float verticalAlignment) {
        super(x, y, w, h);
        this.verticalAlignment = 0.0f;
        this.verticalAlignment = verticalAlignment;
    }
    
    public float getVerticalAlignment() {
        return this.verticalAlignment;
    }
    
    public void setVerticalAlignment(final float verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }
}
