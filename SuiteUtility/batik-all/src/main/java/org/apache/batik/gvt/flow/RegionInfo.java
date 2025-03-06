// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.flow;

import java.awt.Shape;

public class RegionInfo
{
    private Shape shape;
    private float verticalAlignment;
    
    public RegionInfo(final Shape shape, final float verticalAlignment) {
        this.shape = shape;
        this.verticalAlignment = verticalAlignment;
    }
    
    public Shape getShape() {
        return this.shape;
    }
    
    public void setShape(final Shape shape) {
        this.shape = shape;
    }
    
    public float getVerticalAlignment() {
        return this.verticalAlignment;
    }
    
    public void setVerticalAlignment(final float verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }
}
