// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import java.awt.Graphics2D;
import org.apache.batik.gvt.text.ArabicTextHandler;
import java.awt.geom.AffineTransform;
import java.awt.font.GlyphJustificationInfo;
import org.apache.batik.gvt.text.TextPaintInfo;
import java.awt.geom.Point2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;
import java.awt.font.FontRenderContext;
import java.text.AttributedCharacterIterator;

public final class SVGGVTGlyphVector implements GVTGlyphVector
{
    public static final AttributedCharacterIterator.Attribute PAINT_INFO;
    private GVTFont font;
    private Glyph[] glyphs;
    private FontRenderContext frc;
    private GeneralPath outline;
    private Rectangle2D logicalBounds;
    private Rectangle2D bounds2D;
    private Shape[] glyphLogicalBounds;
    private boolean[] glyphVisible;
    private Point2D endPos;
    private TextPaintInfo cacheTPI;
    
    public SVGGVTGlyphVector(final GVTFont font, final Glyph[] glyphs, final FontRenderContext frc) {
        this.font = font;
        this.glyphs = glyphs;
        this.frc = frc;
        this.outline = null;
        this.bounds2D = null;
        this.logicalBounds = null;
        this.glyphLogicalBounds = new Shape[glyphs.length];
        this.glyphVisible = new boolean[glyphs.length];
        for (int i = 0; i < glyphs.length; ++i) {
            this.glyphVisible[i] = true;
        }
        this.endPos = glyphs[glyphs.length - 1].getPosition();
        this.endPos = new Point2D.Float((float)(this.endPos.getX() + glyphs[glyphs.length - 1].getHorizAdvX()), (float)this.endPos.getY());
    }
    
    public GVTFont getFont() {
        return this.font;
    }
    
    public FontRenderContext getFontRenderContext() {
        return this.frc;
    }
    
    public int getGlyphCode(final int i) throws IndexOutOfBoundsException {
        if (i < 0 || i > this.glyphs.length - 1) {
            throw new IndexOutOfBoundsException("glyphIndex " + i + " is out of bounds, should be between 0 and " + (this.glyphs.length - 1));
        }
        return this.glyphs[i].getGlyphCode();
    }
    
    public int[] getGlyphCodes(final int n, final int n2, int[] array) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (n2 < 0) {
            throw new IllegalArgumentException("numEntries argument value, " + n2 + ", is illegal. It must be > 0.");
        }
        if (n < 0) {
            throw new IndexOutOfBoundsException("beginGlyphIndex " + n + " is out of bounds, should be between 0 and " + (this.glyphs.length - 1));
        }
        if (n + n2 > this.glyphs.length) {
            throw new IndexOutOfBoundsException("beginGlyphIndex + numEntries (" + n + "+" + n2 + ") exceeds the number of glpyhs in this GlyphVector");
        }
        if (array == null) {
            array = new int[n2];
        }
        for (int i = n; i < n + n2; ++i) {
            array[i - n] = this.glyphs[i].getGlyphCode();
        }
        return array;
    }
    
    public GlyphJustificationInfo getGlyphJustificationInfo(final int i) {
        if (i < 0 || i > this.glyphs.length - 1) {
            throw new IndexOutOfBoundsException("glyphIndex: " + i + ", is out of bounds. Should be between 0 and " + (this.glyphs.length - 1) + ".");
        }
        return null;
    }
    
    public Shape getGlyphLogicalBounds(final int n) {
        if (this.glyphLogicalBounds[n] == null && this.glyphVisible[n]) {
            this.computeGlyphLogicalBounds();
        }
        return this.glyphLogicalBounds[n];
    }
    
    private void computeGlyphLogicalBounds() {
        float ascent = 0.0f;
        float descent = 0.0f;
        if (this.font != null) {
            final GVTLineMetrics lineMetrics = this.font.getLineMetrics("By", this.frc);
            ascent = lineMetrics.getAscent();
            descent = lineMetrics.getDescent();
            if (descent < 0.0f) {
                descent = -descent;
            }
        }
        if (ascent == 0.0f) {
            float n = 0.0f;
            float n2 = 0.0f;
            for (int i = 0; i < this.getNumGlyphs(); ++i) {
                if (this.glyphVisible[i]) {
                    final Rectangle2D bounds2D = this.getGlyphMetrics(i).getBounds2D();
                    final float n3 = (float)(-bounds2D.getMinY());
                    final float n4 = (float)(bounds2D.getHeight() - n3);
                    if (n3 > n) {
                        n = n3;
                    }
                    if (n4 > n2) {
                        n2 = n4;
                    }
                }
            }
            ascent = n;
            descent = n2;
        }
        final Shape[] array = new Shape[this.getNumGlyphs()];
        final boolean[] array2 = new boolean[this.getNumGlyphs()];
        double width = -1.0;
        double height = -1.0;
        for (int j = 0; j < this.getNumGlyphs(); ++j) {
            if (!this.glyphVisible[j]) {
                array[j] = null;
            }
            else {
                final AffineTransform glyphTransform = this.getGlyphTransform(j);
                final Rectangle2D.Double pSrc = new Rectangle2D.Double(0.0, -ascent, this.getGlyphMetrics(j).getHorizontalAdvance(), ascent + descent);
                if (pSrc.isEmpty()) {
                    if (j > 0) {
                        array2[j] = array2[j - 1];
                    }
                    else {
                        array2[j] = true;
                    }
                }
                else {
                    final Point2D.Double ptSrc = new Point2D.Double(pSrc.getMinX(), pSrc.getMinY());
                    final Point2D.Double ptSrc2 = new Point2D.Double(pSrc.getMaxX(), pSrc.getMinY());
                    final Point2D.Double ptSrc3 = new Point2D.Double(pSrc.getMinX(), pSrc.getMaxY());
                    final Point2D glyphPosition = this.getGlyphPosition(j);
                    final AffineTransform translateInstance = AffineTransform.getTranslateInstance(glyphPosition.getX(), glyphPosition.getY());
                    if (glyphTransform != null) {
                        translateInstance.concatenate(glyphTransform);
                    }
                    array[j] = translateInstance.createTransformedShape(pSrc);
                    final Point2D.Double ptDst = new Point2D.Double();
                    final Point2D.Double ptDst2 = new Point2D.Double();
                    final Point2D.Double ptDst3 = new Point2D.Double();
                    translateInstance.transform(ptSrc, ptDst);
                    translateInstance.transform(ptSrc2, ptDst2);
                    translateInstance.transform(ptSrc3, ptDst3);
                    final double a = ptDst.getX() - ptDst2.getX();
                    final double a2 = ptDst.getX() - ptDst3.getX();
                    final double a3 = ptDst.getY() - ptDst2.getY();
                    final double a4 = ptDst.getY() - ptDst3.getY();
                    if (Math.abs(a) < 0.001 && Math.abs(a4) < 0.001) {
                        array2[j] = false;
                    }
                    else if (Math.abs(a2) < 0.001 && Math.abs(a3) < 0.001) {
                        array2[j] = false;
                    }
                    else {
                        array2[j] = true;
                    }
                    final Rectangle2D bounds2D2 = array[j].getBounds2D();
                    if (bounds2D2.getWidth() > width) {
                        width = bounds2D2.getWidth();
                    }
                    if (bounds2D2.getHeight() > height) {
                        height = bounds2D2.getHeight();
                    }
                }
            }
        }
        final GeneralPath generalPath = new GeneralPath();
        for (int k = 0; k < this.getNumGlyphs(); ++k) {
            if (array[k] != null) {
                generalPath.append(array[k], false);
            }
        }
        final Rectangle2D bounds2D3 = generalPath.getBounds2D();
        if (bounds2D3.getHeight() < height * 1.5) {
            for (int l = 0; l < this.getNumGlyphs(); ++l) {
                if (!array2[l]) {
                    if (array[l] != null) {
                        final Rectangle2D bounds2D4 = array[l].getBounds2D();
                        final double minX = bounds2D4.getMinX();
                        double width2 = bounds2D4.getWidth();
                        if (l < this.getNumGlyphs() - 1 && array[l + 1] != null) {
                            final Rectangle2D bounds2D5 = array[l + 1].getBounds2D();
                            if (bounds2D5.getX() > minX) {
                                final double n5 = bounds2D5.getX() - minX;
                                if (n5 < width2 * 1.15 && n5 > width2 * 0.85) {
                                    final double n6 = (n5 - width2) * 0.5;
                                    width2 += n6;
                                    bounds2D5.setRect(bounds2D5.getX() - n6, bounds2D5.getY(), bounds2D5.getWidth() + n6, bounds2D5.getHeight());
                                }
                            }
                        }
                        array[l] = new Rectangle2D.Double(minX, bounds2D3.getMinY(), width2, bounds2D3.getHeight());
                    }
                }
            }
        }
        else if (bounds2D3.getWidth() < width * 1.5) {
            for (int n7 = 0; n7 < this.getNumGlyphs(); ++n7) {
                if (!array2[n7]) {
                    if (array[n7] != null) {
                        final Rectangle2D bounds2D6 = array[n7].getBounds2D();
                        final double minY = bounds2D6.getMinY();
                        double height2 = bounds2D6.getHeight();
                        if (n7 < this.getNumGlyphs() - 1 && array[n7 + 1] != null) {
                            final Rectangle2D bounds2D7 = array[n7 + 1].getBounds2D();
                            if (bounds2D7.getY() > minY) {
                                final double n8 = bounds2D7.getY() - minY;
                                if (n8 < height2 * 1.15 && n8 > height2 * 0.85) {
                                    final double n9 = (n8 - height2) * 0.5;
                                    height2 += n9;
                                    bounds2D7.setRect(bounds2D7.getX(), bounds2D7.getY() - n9, bounds2D7.getWidth(), bounds2D7.getHeight() + n9);
                                }
                            }
                        }
                        array[n7] = new Rectangle2D.Double(bounds2D3.getMinX(), minY, bounds2D3.getWidth(), height2);
                    }
                }
            }
        }
        System.arraycopy(array, 0, this.glyphLogicalBounds, 0, this.getNumGlyphs());
    }
    
    public GVTGlyphMetrics getGlyphMetrics(final int i) {
        if (i < 0 || i > this.glyphs.length - 1) {
            throw new IndexOutOfBoundsException("idx: " + i + ", is out of bounds. Should be between 0 and " + (this.glyphs.length - 1) + '.');
        }
        if (i < this.glyphs.length - 1 && this.font != null) {
            return this.glyphs[i].getGlyphMetrics(this.font.getHKern(this.glyphs[i].getGlyphCode(), this.glyphs[i + 1].getGlyphCode()), this.font.getVKern(this.glyphs[i].getGlyphCode(), this.glyphs[i + 1].getGlyphCode()));
        }
        return this.glyphs[i].getGlyphMetrics();
    }
    
    public Shape getGlyphOutline(final int i) {
        if (i < 0 || i > this.glyphs.length - 1) {
            throw new IndexOutOfBoundsException("glyphIndex: " + i + ", is out of bounds. Should be between 0 and " + (this.glyphs.length - 1) + ".");
        }
        return this.glyphs[i].getOutline();
    }
    
    public Rectangle2D getGlyphCellBounds(final int n) {
        return this.getGlyphLogicalBounds(n).getBounds2D();
    }
    
    public Point2D getGlyphPosition(final int i) {
        if (i == this.glyphs.length) {
            return this.endPos;
        }
        if (i < 0 || i > this.glyphs.length - 1) {
            throw new IndexOutOfBoundsException("glyphIndex: " + i + ", is out of bounds. Should be between 0 and " + (this.glyphs.length - 1) + '.');
        }
        return this.glyphs[i].getPosition();
    }
    
    public float[] getGlyphPositions(final int n, int n2, float[] array) {
        if (n2 < 0) {
            throw new IllegalArgumentException("numEntries argument value, " + n2 + ", is illegal. It must be > 0.");
        }
        if (n < 0) {
            throw new IndexOutOfBoundsException("beginGlyphIndex " + n + " is out of bounds, should be between 0 and " + (this.glyphs.length - 1));
        }
        if (n + n2 > this.glyphs.length + 1) {
            throw new IndexOutOfBoundsException("beginGlyphIndex + numEntries (" + n + '+' + n2 + ") exceeds the number of glpyhs in this GlyphVector");
        }
        if (array == null) {
            array = new float[n2 * 2];
        }
        if (n + n2 == this.glyphs.length + 1) {
            --n2;
            array[n2 * 2] = (float)this.endPos.getX();
            array[n2 * 2 + 1] = (float)this.endPos.getY();
        }
        for (int i = n; i < n + n2; ++i) {
            final Point2D position = this.glyphs[i].getPosition();
            array[(i - n) * 2] = (float)position.getX();
            array[(i - n) * 2 + 1] = (float)position.getY();
        }
        return array;
    }
    
    public AffineTransform getGlyphTransform(final int i) {
        if (i < 0 || i > this.glyphs.length - 1) {
            throw new IndexOutOfBoundsException("glyphIndex: " + i + ", is out of bounds. Should be between 0 and " + (this.glyphs.length - 1) + '.');
        }
        return this.glyphs[i].getTransform();
    }
    
    public Shape getGlyphVisualBounds(final int i) {
        if (i < 0 || i > this.glyphs.length - 1) {
            throw new IndexOutOfBoundsException("glyphIndex: " + i + ", is out of bounds. Should be between 0 and " + (this.glyphs.length - 1) + '.');
        }
        return this.glyphs[i].getOutline();
    }
    
    public Rectangle2D getBounds2D(final AttributedCharacterIterator attributedCharacterIterator) {
        attributedCharacterIterator.first();
        final TextPaintInfo textPaintInfo = (TextPaintInfo)attributedCharacterIterator.getAttribute(SVGGVTGlyphVector.PAINT_INFO);
        if (this.bounds2D != null && TextPaintInfo.equivilent(textPaintInfo, this.cacheTPI)) {
            return this.bounds2D;
        }
        Rectangle2D bounds2D = null;
        if (textPaintInfo.visible) {
            for (int i = 0; i < this.getNumGlyphs(); ++i) {
                if (this.glyphVisible[i]) {
                    final Rectangle2D bounds2D2 = this.glyphs[i].getBounds2D();
                    if (bounds2D2 != null) {
                        if (bounds2D == null) {
                            bounds2D = bounds2D2;
                        }
                        else {
                            bounds2D.add(bounds2D2);
                        }
                    }
                }
            }
        }
        this.bounds2D = bounds2D;
        if (this.bounds2D == null) {
            this.bounds2D = new Rectangle2D.Float();
        }
        this.cacheTPI = new TextPaintInfo(textPaintInfo);
        return this.bounds2D;
    }
    
    public Rectangle2D getLogicalBounds() {
        if (this.logicalBounds == null) {
            final GeneralPath generalPath = new GeneralPath();
            for (int i = 0; i < this.getNumGlyphs(); ++i) {
                final Shape glyphLogicalBounds = this.getGlyphLogicalBounds(i);
                if (glyphLogicalBounds != null) {
                    generalPath.append(glyphLogicalBounds, false);
                }
            }
            this.logicalBounds = generalPath.getBounds2D();
        }
        return this.logicalBounds;
    }
    
    public int getNumGlyphs() {
        if (this.glyphs != null) {
            return this.glyphs.length;
        }
        return 0;
    }
    
    public Shape getOutline() {
        if (this.outline == null) {
            this.outline = new GeneralPath();
            for (int i = 0; i < this.glyphs.length; ++i) {
                if (this.glyphVisible[i]) {
                    final Shape outline = this.glyphs[i].getOutline();
                    if (outline != null) {
                        this.outline.append(outline, false);
                    }
                }
            }
        }
        return this.outline;
    }
    
    public Shape getOutline(final float n, final float n2) {
        return AffineTransform.getTranslateInstance(n, n2).createTransformedShape(this.getOutline());
    }
    
    public Rectangle2D getGeometricBounds() {
        return this.getOutline().getBounds2D();
    }
    
    public void performDefaultLayout() {
        this.logicalBounds = null;
        this.outline = null;
        this.bounds2D = null;
        float n = 0.0f;
        final float y = 0.0f;
        for (int i = 0; i < this.glyphs.length; ++i) {
            Glyph glyph = this.glyphs[i];
            glyph.setTransform(null);
            this.glyphLogicalBounds[i] = null;
            final String unicode = glyph.getUnicode();
            if (unicode != null && unicode.length() != 0 && ArabicTextHandler.arabicCharTransparent(unicode.charAt(0))) {
                int j;
                for (j = i + 1; j < this.glyphs.length; ++j) {
                    final String unicode2 = this.glyphs[j].getUnicode();
                    if (unicode2 == null) {
                        break;
                    }
                    if (unicode2.length() == 0) {
                        break;
                    }
                    if (!ArabicTextHandler.arabicCharTransparent(unicode2.charAt(0))) {
                        break;
                    }
                }
                if (j != this.glyphs.length) {
                    final Glyph glyph2 = this.glyphs[j];
                    final float n2 = n + glyph2.getHorizAdvX();
                    for (int k = i; k < j; ++k) {
                        final Glyph glyph3 = this.glyphs[k];
                        glyph3.setTransform(null);
                        this.glyphLogicalBounds[i] = null;
                        glyph3.setPosition(new Point2D.Float(n2 - glyph3.getHorizAdvX(), y));
                    }
                    i = j;
                    glyph = glyph2;
                }
            }
            glyph.setPosition(new Point2D.Float(n, y));
            n += glyph.getHorizAdvX();
        }
        this.endPos = new Point2D.Float(n, y);
    }
    
    public void setGlyphPosition(final int i, final Point2D position) throws IndexOutOfBoundsException {
        if (i == this.glyphs.length) {
            this.endPos = (Point2D)position.clone();
            return;
        }
        if (i < 0 || i > this.glyphs.length - 1) {
            throw new IndexOutOfBoundsException("glyphIndex: " + i + ", is out of bounds. Should be between 0 and " + (this.glyphs.length - 1) + '.');
        }
        this.glyphs[i].setPosition(position);
        this.glyphLogicalBounds[i] = null;
        this.outline = null;
        this.bounds2D = null;
        this.logicalBounds = null;
    }
    
    public void setGlyphTransform(final int i, final AffineTransform transform) {
        if (i < 0 || i > this.glyphs.length - 1) {
            throw new IndexOutOfBoundsException("glyphIndex: " + i + ", is out of bounds. Should be between 0 and " + (this.glyphs.length - 1) + '.');
        }
        this.glyphs[i].setTransform(transform);
        this.glyphLogicalBounds[i] = null;
        this.outline = null;
        this.bounds2D = null;
        this.logicalBounds = null;
    }
    
    public void setGlyphVisible(final int n, final boolean b) {
        if (b == this.glyphVisible[n]) {
            return;
        }
        this.glyphVisible[n] = b;
        this.outline = null;
        this.bounds2D = null;
        this.logicalBounds = null;
        this.glyphLogicalBounds[n] = null;
    }
    
    public boolean isGlyphVisible(final int n) {
        return this.glyphVisible[n];
    }
    
    public int getCharacterCount(int n, int n2) {
        int n3 = 0;
        if (n < 0) {
            n = 0;
        }
        if (n2 > this.glyphs.length - 1) {
            n2 = this.glyphs.length - 1;
        }
        for (int i = n; i <= n2; ++i) {
            final Glyph glyph = this.glyphs[i];
            if (glyph.getGlyphCode() == -1) {
                ++n3;
            }
            else {
                n3 += glyph.getUnicode().length();
            }
        }
        return n3;
    }
    
    public void draw(final Graphics2D graphics2D, final AttributedCharacterIterator attributedCharacterIterator) {
        attributedCharacterIterator.first();
        if (!((TextPaintInfo)attributedCharacterIterator.getAttribute(SVGGVTGlyphVector.PAINT_INFO)).visible) {
            return;
        }
        for (int i = 0; i < this.glyphs.length; ++i) {
            if (this.glyphVisible[i]) {
                this.glyphs[i].draw(graphics2D);
            }
        }
    }
    
    static {
        PAINT_INFO = GVTAttributedCharacterIterator.TextAttribute.PAINT_INFO;
    }
}
