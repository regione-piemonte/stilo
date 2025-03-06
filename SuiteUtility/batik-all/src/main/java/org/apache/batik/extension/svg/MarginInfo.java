// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

public class MarginInfo
{
    public static final int JUSTIFY_START = 0;
    public static final int JUSTIFY_MIDDLE = 1;
    public static final int JUSTIFY_END = 2;
    public static final int JUSTIFY_FULL = 3;
    protected float top;
    protected float right;
    protected float bottom;
    protected float left;
    protected float indent;
    protected int justification;
    protected boolean flowRegionBreak;
    
    public MarginInfo(final float top, final float right, final float bottom, final float left, final float indent, final int justification, final boolean flowRegionBreak) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.indent = indent;
        this.justification = justification;
        this.flowRegionBreak = flowRegionBreak;
    }
    
    public MarginInfo(final float margin, final int justification) {
        this.setMargin(margin);
        this.indent = 0.0f;
        this.justification = justification;
        this.flowRegionBreak = false;
    }
    
    public void setMargin(final float n) {
        this.top = n;
        this.right = n;
        this.bottom = n;
        this.left = n;
    }
    
    public float getTopMargin() {
        return this.top;
    }
    
    public float getRightMargin() {
        return this.right;
    }
    
    public float getBottomMargin() {
        return this.bottom;
    }
    
    public float getLeftMargin() {
        return this.left;
    }
    
    public float getIndent() {
        return this.indent;
    }
    
    public int getJustification() {
        return this.justification;
    }
    
    public boolean isFlowRegionBreak() {
        return this.flowRegionBreak;
    }
}
