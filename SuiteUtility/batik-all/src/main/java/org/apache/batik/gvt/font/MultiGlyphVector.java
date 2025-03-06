// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import java.awt.Graphics2D;
import org.apache.batik.gvt.text.AttributedCharacterSpanIterator;
import java.text.AttributedCharacterIterator;
import java.awt.geom.Path2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.awt.font.GlyphJustificationInfo;
import java.awt.font.FontRenderContext;
import java.util.Iterator;
import java.util.List;

public class MultiGlyphVector implements GVTGlyphVector
{
    GVTGlyphVector[] gvs;
    int[] nGlyphs;
    int[] off;
    int nGlyph;
    
    public MultiGlyphVector(final List list) {
        final int size = list.size();
        this.gvs = new GVTGlyphVector[size];
        this.nGlyphs = new int[size];
        this.off = new int[size];
        final Iterator<GVTGlyphVector> iterator = list.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            this.off[n] = this.nGlyph;
            final GVTGlyphVector gvtGlyphVector = iterator.next();
            this.gvs[n] = gvtGlyphVector;
            this.nGlyphs[n] = gvtGlyphVector.getNumGlyphs();
            this.nGlyph += this.nGlyphs[n];
            ++n;
        }
        final int[] nGlyphs = this.nGlyphs;
        final int n2 = n - 1;
        ++nGlyphs[n2];
    }
    
    public int getNumGlyphs() {
        return this.nGlyph;
    }
    
    int getGVIdx(final int n) {
        if (n > this.nGlyph) {
            return -1;
        }
        if (n == this.nGlyph) {
            return this.gvs.length - 1;
        }
        for (int i = 0; i < this.nGlyphs.length; ++i) {
            if (n - this.off[i] < this.nGlyphs[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public GVTFont getFont() {
        throw new IllegalArgumentException("Can't be correctly Implemented");
    }
    
    public FontRenderContext getFontRenderContext() {
        return this.gvs[0].getFontRenderContext();
    }
    
    public int getGlyphCode(final int n) {
        final int gvIdx = this.getGVIdx(n);
        return this.gvs[gvIdx].getGlyphCode(n - this.off[gvIdx]);
    }
    
    public GlyphJustificationInfo getGlyphJustificationInfo(final int n) {
        final int gvIdx = this.getGVIdx(n);
        return this.gvs[gvIdx].getGlyphJustificationInfo(n - this.off[gvIdx]);
    }
    
    public Shape getGlyphLogicalBounds(final int n) {
        final int gvIdx = this.getGVIdx(n);
        return this.gvs[gvIdx].getGlyphLogicalBounds(n - this.off[gvIdx]);
    }
    
    public GVTGlyphMetrics getGlyphMetrics(final int n) {
        final int gvIdx = this.getGVIdx(n);
        return this.gvs[gvIdx].getGlyphMetrics(n - this.off[gvIdx]);
    }
    
    public Shape getGlyphOutline(final int n) {
        final int gvIdx = this.getGVIdx(n);
        return this.gvs[gvIdx].getGlyphOutline(n - this.off[gvIdx]);
    }
    
    public Rectangle2D getGlyphCellBounds(final int n) {
        return this.getGlyphLogicalBounds(n).getBounds2D();
    }
    
    public Point2D getGlyphPosition(final int n) {
        final int gvIdx = this.getGVIdx(n);
        return this.gvs[gvIdx].getGlyphPosition(n - this.off[gvIdx]);
    }
    
    public AffineTransform getGlyphTransform(final int n) {
        final int gvIdx = this.getGVIdx(n);
        return this.gvs[gvIdx].getGlyphTransform(n - this.off[gvIdx]);
    }
    
    public Shape getGlyphVisualBounds(final int n) {
        final int gvIdx = this.getGVIdx(n);
        return this.gvs[gvIdx].getGlyphVisualBounds(n - this.off[gvIdx]);
    }
    
    public void setGlyphPosition(final int n, final Point2D point2D) {
        final int gvIdx = this.getGVIdx(n);
        this.gvs[gvIdx].setGlyphPosition(n - this.off[gvIdx], point2D);
    }
    
    public void setGlyphTransform(final int n, final AffineTransform affineTransform) {
        final int gvIdx = this.getGVIdx(n);
        this.gvs[gvIdx].setGlyphTransform(n - this.off[gvIdx], affineTransform);
    }
    
    public void setGlyphVisible(final int n, final boolean b) {
        final int gvIdx = this.getGVIdx(n);
        this.gvs[gvIdx].setGlyphVisible(n - this.off[gvIdx], b);
    }
    
    public boolean isGlyphVisible(final int n) {
        final int gvIdx = this.getGVIdx(n);
        return this.gvs[gvIdx].isGlyphVisible(n - this.off[gvIdx]);
    }
    
    public int[] getGlyphCodes(final int n, int i, final int[] array) {
        int[] array2 = array;
        if (array2 == null) {
            array2 = new int[i];
        }
        int[] array3 = null;
        int gvIdx = this.getGVIdx(n);
        int n2 = n - this.off[gvIdx];
        int n4;
        for (int n3 = 0; i != 0; i -= n4, n3 += n4) {
            n4 = i;
            if (n2 + n4 > this.nGlyphs[gvIdx]) {
                n4 = this.nGlyphs[gvIdx] - n2;
            }
            final GVTGlyphVector gvtGlyphVector = this.gvs[gvIdx];
            if (n3 == 0) {
                gvtGlyphVector.getGlyphCodes(n2, n4, array2);
            }
            else {
                if (array3 == null || array3.length < n4) {
                    array3 = new int[n4];
                }
                gvtGlyphVector.getGlyphCodes(n2, n4, array3);
                System.arraycopy(array3, 0, array2, n3, n4);
            }
            n2 = 0;
            ++gvIdx;
        }
        return array2;
    }
    
    public float[] getGlyphPositions(final int n, int i, final float[] array) {
        float[] array2 = array;
        if (array2 == null) {
            array2 = new float[i * 2];
        }
        float[] array3 = null;
        int gvIdx = this.getGVIdx(n);
        int n2 = n - this.off[gvIdx];
        int n4;
        for (int n3 = 0; i != 0; i -= n4, n3 += n4 * 2) {
            n4 = i;
            if (n2 + n4 > this.nGlyphs[gvIdx]) {
                n4 = this.nGlyphs[gvIdx] - n2;
            }
            final GVTGlyphVector gvtGlyphVector = this.gvs[gvIdx];
            if (n3 == 0) {
                gvtGlyphVector.getGlyphPositions(n2, n4, array2);
            }
            else {
                if (array3 == null || array3.length < n4 * 2) {
                    array3 = new float[n4 * 2];
                }
                gvtGlyphVector.getGlyphPositions(n2, n4, array3);
                System.arraycopy(array3, 0, array2, n3, n4 * 2);
            }
            n2 = 0;
            ++gvIdx;
        }
        return array2;
    }
    
    public Rectangle2D getLogicalBounds() {
        Rectangle2D rectangle2D = null;
        for (int i = 0; i < this.gvs.length; ++i) {
            final Rectangle2D logicalBounds = this.gvs[i].getLogicalBounds();
            if (rectangle2D == null) {
                rectangle2D = logicalBounds;
            }
            else {
                rectangle2D.add(logicalBounds);
            }
        }
        return rectangle2D;
    }
    
    public Shape getOutline() {
        Path2D path2D = null;
        for (int i = 0; i < this.gvs.length; ++i) {
            final Shape outline = this.gvs[i].getOutline();
            if (path2D == null) {
                path2D = new GeneralPath(outline);
            }
            else {
                path2D.append(outline, false);
            }
        }
        return path2D;
    }
    
    public Shape getOutline(final float n, final float n2) {
        return AffineTransform.getTranslateInstance(n, n2).createTransformedShape(this.getOutline());
    }
    
    public Rectangle2D getBounds2D(final AttributedCharacterIterator attributedCharacterIterator) {
        Rectangle2D rectangle2D = null;
        int beginIndex = attributedCharacterIterator.getBeginIndex();
        for (int i = 0; i < this.gvs.length; ++i) {
            final GVTGlyphVector gvtGlyphVector = this.gvs[i];
            final int n = gvtGlyphVector.getCharacterCount(0, gvtGlyphVector.getNumGlyphs()) + 1;
            final Rectangle2D bounds2D = this.gvs[i].getBounds2D(new AttributedCharacterSpanIterator(attributedCharacterIterator, beginIndex, n));
            if (rectangle2D == null) {
                rectangle2D = bounds2D;
            }
            else {
                rectangle2D.add(bounds2D);
            }
            beginIndex = n;
        }
        return rectangle2D;
    }
    
    public Rectangle2D getGeometricBounds() {
        Rectangle2D rectangle2D = null;
        for (int i = 0; i < this.gvs.length; ++i) {
            final Rectangle2D geometricBounds = this.gvs[i].getGeometricBounds();
            if (rectangle2D == null) {
                rectangle2D = geometricBounds;
            }
            else {
                rectangle2D.add(geometricBounds);
            }
        }
        return rectangle2D;
    }
    
    public void performDefaultLayout() {
        for (int i = 0; i < this.gvs.length; ++i) {
            this.gvs[i].performDefaultLayout();
        }
    }
    
    public int getCharacterCount(int n, final int n2) {
        final int gvIdx = this.getGVIdx(n);
        final int gvIdx2 = this.getGVIdx(n2);
        int n3 = 0;
        for (int i = gvIdx; i <= gvIdx2; ++i) {
            final int n4 = n - this.off[i];
            int n5 = n2 - this.off[i];
            if (n5 >= this.nGlyphs[i]) {
                n5 = this.nGlyphs[i] - 1;
            }
            n3 += this.gvs[i].getCharacterCount(n4, n5);
            n += n5 - n4 + 1;
        }
        return n3;
    }
    
    public void draw(final Graphics2D graphics2D, final AttributedCharacterIterator attributedCharacterIterator) {
        int beginIndex = attributedCharacterIterator.getBeginIndex();
        for (int i = 0; i < this.gvs.length; ++i) {
            final GVTGlyphVector gvtGlyphVector = this.gvs[i];
            final int n = gvtGlyphVector.getCharacterCount(0, gvtGlyphVector.getNumGlyphs()) + 1;
            gvtGlyphVector.draw(graphics2D, new AttributedCharacterSpanIterator(attributedCharacterIterator, beginIndex, n));
            beginIndex = n;
        }
    }
}
