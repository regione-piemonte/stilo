// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.gvt.font.GVTGlyphVector;
import java.text.AttributedCharacterIterator;
import java.awt.geom.Point2D;

public class LineInfo
{
    Point2D.Float loc;
    AttributedCharacterIterator aci;
    GVTGlyphVector gv;
    int startIdx;
    int endIdx;
    float advance;
    float visualAdvance;
    float lastCharWidth;
    float lineWidth;
    boolean partial;
    Point2D.Float verticalAlignOffset;
    
    public LineInfo(final Point2D.Float loc, final AttributedCharacterIterator aci, final GVTGlyphVector gv, final int startIdx, final int endIdx, final float advance, final float visualAdvance, final float lastCharWidth, final float lineWidth, final boolean partial, final Point2D.Float verticalAlignOffset) {
        this.loc = loc;
        this.aci = aci;
        this.gv = gv;
        this.startIdx = startIdx;
        this.endIdx = endIdx;
        this.advance = advance;
        this.visualAdvance = visualAdvance;
        this.lastCharWidth = lastCharWidth;
        this.lineWidth = lineWidth;
        this.partial = partial;
        this.verticalAlignOffset = verticalAlignOffset;
    }
    
    public Point2D.Float getLocation() {
        return this.loc;
    }
    
    public AttributedCharacterIterator getACI() {
        return this.aci;
    }
    
    public GVTGlyphVector getGlyphVector() {
        return this.gv;
    }
    
    public int getStartIdx() {
        return this.startIdx;
    }
    
    public int getEndIdx() {
        return this.endIdx;
    }
    
    public float getAdvance() {
        return this.advance;
    }
    
    public float getVisualAdvance() {
        return this.visualAdvance;
    }
    
    public float getLastCharWidth() {
        return this.lastCharWidth;
    }
    
    public float getLineWidth() {
        return this.lineWidth;
    }
    
    public boolean isPartialLine() {
        return this.partial;
    }
    
    public Point2D.Float getVerticalAlignOffset() {
        return this.verticalAlignOffset;
    }
    
    public String toString() {
        return "[LineInfo loc: " + this.loc + " [" + this.startIdx + ',' + this.endIdx + "] " + " LWidth: " + this.lineWidth + " Adv: " + this.advance + " VAdv: " + this.visualAdvance + " LCW: " + this.lastCharWidth + " Partial: " + this.partial + " verticalAlignOffset: " + this.verticalAlignOffset;
    }
}
