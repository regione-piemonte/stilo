// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen.font;

import org.apache.batik.svggen.font.table.GlyphDescription;

public class Glyph
{
    protected short leftSideBearing;
    protected int advanceWidth;
    private Point[] points;
    
    public Glyph(final GlyphDescription glyphDescription, final short leftSideBearing, final int advanceWidth) {
        this.leftSideBearing = leftSideBearing;
        this.advanceWidth = advanceWidth;
        this.describe(glyphDescription);
    }
    
    public int getAdvanceWidth() {
        return this.advanceWidth;
    }
    
    public short getLeftSideBearing() {
        return this.leftSideBearing;
    }
    
    public Point getPoint(final int n) {
        return this.points[n];
    }
    
    public int getPointCount() {
        return this.points.length;
    }
    
    public void reset() {
    }
    
    public void scale(final int n) {
        for (int i = 0; i < this.points.length; ++i) {
            this.points[i].x = (this.points[i].x << 10) * n >> 26;
            this.points[i].y = (this.points[i].y << 10) * n >> 26;
        }
        this.leftSideBearing = (short)(this.leftSideBearing * n >> 6);
        this.advanceWidth = this.advanceWidth * n >> 6;
    }
    
    private void describe(final GlyphDescription glyphDescription) {
        int n = 0;
        this.points = new Point[glyphDescription.getPointCount() + 2];
        for (int i = 0; i < glyphDescription.getPointCount(); ++i) {
            final boolean b = glyphDescription.getEndPtOfContours(n) == i;
            if (b) {
                ++n;
            }
            this.points[i] = new Point(glyphDescription.getXCoordinate(i), glyphDescription.getYCoordinate(i), (glyphDescription.getFlags(i) & 0x1) != 0x0, b);
        }
        this.points[glyphDescription.getPointCount()] = new Point(0, 0, true, true);
        this.points[glyphDescription.getPointCount() + 1] = new Point(this.advanceWidth, 0, true, true);
    }
}
