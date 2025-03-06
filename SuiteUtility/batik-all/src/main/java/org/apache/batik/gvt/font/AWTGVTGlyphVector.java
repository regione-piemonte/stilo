// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import java.awt.RenderingHints;
import java.awt.Color;
import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import java.awt.Graphics2D;
import org.apache.batik.gvt.text.ArabicTextHandler;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.font.GlyphJustificationInfo;
import java.awt.font.FontRenderContext;
import org.apache.batik.gvt.text.TextPaintInfo;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.CharacterIterator;
import java.awt.font.GlyphVector;
import java.text.AttributedCharacterIterator;

public class AWTGVTGlyphVector implements GVTGlyphVector
{
    public static final AttributedCharacterIterator.Attribute PAINT_INFO;
    private GlyphVector awtGlyphVector;
    private AWTGVTFont gvtFont;
    private CharacterIterator ci;
    private Point2D[] defaultGlyphPositions;
    private Point2D.Float[] glyphPositions;
    private AffineTransform[] glyphTransforms;
    private Shape[] glyphOutlines;
    private Shape[] glyphVisualBounds;
    private Shape[] glyphLogicalBounds;
    private boolean[] glyphVisible;
    private GVTGlyphMetrics[] glyphMetrics;
    private GeneralPath outline;
    private Rectangle2D visualBounds;
    private Rectangle2D logicalBounds;
    private Rectangle2D bounds2D;
    private float scaleFactor;
    private float ascent;
    private float descent;
    private TextPaintInfo cacheTPI;
    private static final boolean outlinesPositioned;
    private static final boolean drawGlyphVectorWorks;
    private static final boolean glyphVectorTransformWorks;
    
    public AWTGVTGlyphVector(final GlyphVector awtGlyphVector, final AWTGVTFont gvtFont, final float scaleFactor, final CharacterIterator ci) {
        this.awtGlyphVector = awtGlyphVector;
        this.gvtFont = gvtFont;
        this.scaleFactor = scaleFactor;
        this.ci = ci;
        final GVTLineMetrics lineMetrics = this.gvtFont.getLineMetrics("By", this.awtGlyphVector.getFontRenderContext());
        this.ascent = lineMetrics.getAscent();
        this.descent = lineMetrics.getDescent();
        this.outline = null;
        this.visualBounds = null;
        this.logicalBounds = null;
        this.bounds2D = null;
        final int numGlyphs = awtGlyphVector.getNumGlyphs();
        this.glyphPositions = new Point2D.Float[numGlyphs + 1];
        this.glyphTransforms = new AffineTransform[numGlyphs];
        this.glyphOutlines = new Shape[numGlyphs];
        this.glyphVisualBounds = new Shape[numGlyphs];
        this.glyphLogicalBounds = new Shape[numGlyphs];
        this.glyphVisible = new boolean[numGlyphs];
        this.glyphMetrics = new GVTGlyphMetrics[numGlyphs];
        for (int i = 0; i < numGlyphs; ++i) {
            this.glyphVisible[i] = true;
        }
    }
    
    public GVTFont getFont() {
        return this.gvtFont;
    }
    
    public FontRenderContext getFontRenderContext() {
        return this.awtGlyphVector.getFontRenderContext();
    }
    
    public int getGlyphCode(final int n) {
        return this.awtGlyphVector.getGlyphCode(n);
    }
    
    public int[] getGlyphCodes(final int n, final int n2, final int[] array) {
        return this.awtGlyphVector.getGlyphCodes(n, n2, array);
    }
    
    public GlyphJustificationInfo getGlyphJustificationInfo(final int n) {
        return this.awtGlyphVector.getGlyphJustificationInfo(n);
    }
    
    public Rectangle2D getBounds2D(final AttributedCharacterIterator attributedCharacterIterator) {
        attributedCharacterIterator.first();
        final TextPaintInfo textPaintInfo = (TextPaintInfo)attributedCharacterIterator.getAttribute(AWTGVTGlyphVector.PAINT_INFO);
        if (this.bounds2D != null && TextPaintInfo.equivilent(textPaintInfo, this.cacheTPI)) {
            return this.bounds2D;
        }
        if (textPaintInfo == null) {
            return null;
        }
        if (!textPaintInfo.visible) {
            return null;
        }
        this.cacheTPI = new TextPaintInfo(textPaintInfo);
        Shape shape = null;
        if (textPaintInfo.fillPaint != null) {
            shape = this.getOutline();
            this.bounds2D = shape.getBounds2D();
        }
        final Stroke strokeStroke = textPaintInfo.strokeStroke;
        final Paint strokePaint = textPaintInfo.strokePaint;
        if (strokeStroke != null && strokePaint != null) {
            if (shape == null) {
                shape = this.getOutline();
            }
            final Rectangle2D bounds2D = strokeStroke.createStrokedShape(shape).getBounds2D();
            if (this.bounds2D == null) {
                this.bounds2D = bounds2D;
            }
            else {
                this.bounds2D.add(bounds2D);
            }
        }
        if (this.bounds2D == null) {
            return null;
        }
        if (this.bounds2D.getWidth() == 0.0 || this.bounds2D.getHeight() == 0.0) {
            this.bounds2D = null;
        }
        return this.bounds2D;
    }
    
    public Rectangle2D getLogicalBounds() {
        if (this.logicalBounds == null) {
            this.computeGlyphLogicalBounds();
        }
        return this.logicalBounds;
    }
    
    public Shape getGlyphLogicalBounds(final int n) {
        if (this.glyphLogicalBounds[n] == null && this.glyphVisible[n]) {
            this.computeGlyphLogicalBounds();
        }
        return this.glyphLogicalBounds[n];
    }
    
    private void computeGlyphLogicalBounds() {
        final Shape[] array = new Shape[this.getNumGlyphs()];
        final boolean[] array2 = new boolean[this.getNumGlyphs()];
        double width = -1.0;
        double height = -1.0;
        for (int i = 0; i < this.getNumGlyphs(); ++i) {
            if (!this.glyphVisible[i]) {
                array[i] = null;
            }
            else {
                final AffineTransform glyphTransform = this.getGlyphTransform(i);
                final GVTGlyphMetrics glyphMetrics = this.getGlyphMetrics(i);
                final Rectangle2D.Double pSrc = new Rectangle2D.Double(0.0f, -this.ascent / this.scaleFactor, glyphMetrics.getHorizontalAdvance() / this.scaleFactor, glyphMetrics.getVerticalAdvance() / this.scaleFactor);
                if (pSrc.isEmpty()) {
                    if (i > 0) {
                        array2[i] = array2[i - 1];
                    }
                    else {
                        array2[i] = true;
                    }
                }
                else {
                    final Point2D.Double ptSrc = new Point2D.Double(pSrc.getMinX(), pSrc.getMinY());
                    final Point2D.Double ptSrc2 = new Point2D.Double(pSrc.getMaxX(), pSrc.getMinY());
                    final Point2D.Double ptSrc3 = new Point2D.Double(pSrc.getMinX(), pSrc.getMaxY());
                    final Point2D glyphPosition = this.getGlyphPosition(i);
                    final AffineTransform translateInstance = AffineTransform.getTranslateInstance(glyphPosition.getX(), glyphPosition.getY());
                    if (glyphTransform != null) {
                        translateInstance.concatenate(glyphTransform);
                    }
                    translateInstance.scale(this.scaleFactor, this.scaleFactor);
                    array[i] = translateInstance.createTransformedShape(pSrc);
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
                    if ((Math.abs(a) < 0.001 && Math.abs(a4) < 0.001) || (Math.abs(a2) < 0.001 && Math.abs(a3) < 0.001)) {
                        array2[i] = false;
                    }
                    else {
                        array2[i] = true;
                    }
                    final Rectangle2D bounds2D = array[i].getBounds2D();
                    if (bounds2D.getWidth() > width) {
                        width = bounds2D.getWidth();
                    }
                    if (bounds2D.getHeight() > height) {
                        height = bounds2D.getHeight();
                    }
                }
            }
        }
        final GeneralPath generalPath = new GeneralPath();
        for (int j = 0; j < this.getNumGlyphs(); ++j) {
            if (array[j] != null) {
                generalPath.append(array[j], false);
            }
        }
        this.logicalBounds = generalPath.getBounds2D();
        if (this.logicalBounds.getHeight() < height * 1.5) {
            for (int k = 0; k < this.getNumGlyphs(); ++k) {
                if (!array2[k]) {
                    if (array[k] != null) {
                        final Rectangle2D bounds2D2 = array[k].getBounds2D();
                        final double minX = bounds2D2.getMinX();
                        double width2 = bounds2D2.getWidth();
                        if (k < this.getNumGlyphs() - 1 && array[k + 1] != null) {
                            final Rectangle2D bounds2D3 = array[k + 1].getBounds2D();
                            if (bounds2D3.getX() > minX) {
                                final double n = bounds2D3.getX() - minX;
                                if (n < width2 * 1.15 && n > width2 * 0.85) {
                                    final double n2 = (n - width2) * 0.5;
                                    width2 += n2;
                                    bounds2D3.setRect(bounds2D3.getX() - n2, bounds2D3.getY(), bounds2D3.getWidth() + n2, bounds2D3.getHeight());
                                }
                            }
                        }
                        array[k] = new Rectangle2D.Double(minX, this.logicalBounds.getMinY(), width2, this.logicalBounds.getHeight());
                    }
                }
            }
        }
        else if (this.logicalBounds.getWidth() < width * 1.5) {
            for (int l = 0; l < this.getNumGlyphs(); ++l) {
                if (!array2[l]) {
                    if (array[l] != null) {
                        final Rectangle2D bounds2D4 = array[l].getBounds2D();
                        final double minY = bounds2D4.getMinY();
                        double height2 = bounds2D4.getHeight();
                        if (l < this.getNumGlyphs() - 1 && array[l + 1] != null) {
                            final Rectangle2D bounds2D5 = array[l + 1].getBounds2D();
                            if (bounds2D5.getY() > minY) {
                                final double n3 = bounds2D5.getY() - minY;
                                if (n3 < height2 * 1.15 && n3 > height2 * 0.85) {
                                    final double n4 = (n3 - height2) * 0.5;
                                    height2 += n4;
                                    bounds2D5.setRect(bounds2D5.getX(), bounds2D5.getY() - n4, bounds2D5.getWidth(), bounds2D5.getHeight() + n4);
                                }
                            }
                        }
                        array[l] = new Rectangle2D.Double(this.logicalBounds.getMinX(), minY, this.logicalBounds.getWidth(), height2);
                    }
                }
            }
        }
        System.arraycopy(array, 0, this.glyphLogicalBounds, 0, this.getNumGlyphs());
    }
    
    public GVTGlyphMetrics getGlyphMetrics(final int n) {
        if (this.glyphMetrics[n] != null) {
            return this.glyphMetrics[n];
        }
        final Point2D point2D = this.defaultGlyphPositions[n];
        final char setIndex = this.ci.setIndex(this.ci.getBeginIndex() + n);
        this.ci.setIndex(this.ci.getBeginIndex());
        final Rectangle2D bounds2D = AWTGVTFont.getGlyphGeometry(this.gvtFont, setIndex, this.awtGlyphVector, n, point2D).getBounds2D();
        return this.glyphMetrics[n] = new GVTGlyphMetrics((float)(this.defaultGlyphPositions[n + 1].getX() - this.defaultGlyphPositions[n].getX()) * this.scaleFactor, this.ascent + this.descent, new Rectangle2D.Double(bounds2D.getX() * this.scaleFactor, bounds2D.getY() * this.scaleFactor, bounds2D.getWidth() * this.scaleFactor, bounds2D.getHeight() * this.scaleFactor), (byte)0);
    }
    
    public Shape getGlyphOutline(final int n) {
        if (this.glyphOutlines[n] == null) {
            final Point2D point2D = this.defaultGlyphPositions[n];
            final char setIndex = this.ci.setIndex(this.ci.getBeginIndex() + n);
            this.ci.setIndex(this.ci.getBeginIndex());
            final Shape outline = AWTGVTFont.getGlyphGeometry(this.gvtFont, setIndex, this.awtGlyphVector, n, point2D).getOutline();
            final AffineTransform translateInstance = AffineTransform.getTranslateInstance(this.getGlyphPosition(n).getX(), this.getGlyphPosition(n).getY());
            final AffineTransform glyphTransform = this.getGlyphTransform(n);
            if (glyphTransform != null) {
                translateInstance.concatenate(glyphTransform);
            }
            translateInstance.scale(this.scaleFactor, this.scaleFactor);
            this.glyphOutlines[n] = translateInstance.createTransformedShape(outline);
        }
        return this.glyphOutlines[n];
    }
    
    static boolean outlinesPositioned() {
        return AWTGVTGlyphVector.outlinesPositioned;
    }
    
    public Rectangle2D getGlyphCellBounds(final int n) {
        return this.getGlyphLogicalBounds(n).getBounds2D();
    }
    
    public Point2D getGlyphPosition(final int n) {
        return this.glyphPositions[n];
    }
    
    public float[] getGlyphPositions(final int n, final int n2, float[] array) {
        if (array == null) {
            array = new float[n2 * 2];
        }
        for (int i = n; i < n + n2; ++i) {
            final Point2D glyphPosition = this.getGlyphPosition(i);
            array[(i - n) * 2] = (float)glyphPosition.getX();
            array[(i - n) * 2 + 1] = (float)glyphPosition.getY();
        }
        return array;
    }
    
    public AffineTransform getGlyphTransform(final int n) {
        return this.glyphTransforms[n];
    }
    
    public Shape getGlyphVisualBounds(final int n) {
        if (this.glyphVisualBounds[n] == null) {
            final Point2D point2D = this.defaultGlyphPositions[n];
            final char setIndex = this.ci.setIndex(this.ci.getBeginIndex() + n);
            this.ci.setIndex(this.ci.getBeginIndex());
            final Rectangle2D outlineBounds2D = AWTGVTFont.getGlyphGeometry(this.gvtFont, setIndex, this.awtGlyphVector, n, point2D).getOutlineBounds2D();
            final AffineTransform translateInstance = AffineTransform.getTranslateInstance(this.getGlyphPosition(n).getX(), this.getGlyphPosition(n).getY());
            final AffineTransform glyphTransform = this.getGlyphTransform(n);
            if (glyphTransform != null) {
                translateInstance.concatenate(glyphTransform);
            }
            translateInstance.scale(this.scaleFactor, this.scaleFactor);
            this.glyphVisualBounds[n] = translateInstance.createTransformedShape(outlineBounds2D);
        }
        return this.glyphVisualBounds[n];
    }
    
    public int getNumGlyphs() {
        return this.awtGlyphVector.getNumGlyphs();
    }
    
    public Shape getOutline() {
        if (this.outline != null) {
            return this.outline;
        }
        this.outline = new GeneralPath();
        for (int i = 0; i < this.getNumGlyphs(); ++i) {
            if (this.glyphVisible[i]) {
                this.outline.append(this.getGlyphOutline(i), false);
            }
        }
        return this.outline;
    }
    
    public Shape getOutline(final float n, final float n2) {
        return AffineTransform.getTranslateInstance(n, n2).createTransformedShape(this.getOutline());
    }
    
    public Rectangle2D getGeometricBounds() {
        if (this.visualBounds == null) {
            this.visualBounds = this.getOutline().getBounds2D();
        }
        return this.visualBounds;
    }
    
    public void performDefaultLayout() {
        if (this.defaultGlyphPositions == null) {
            this.awtGlyphVector.performDefaultLayout();
            this.defaultGlyphPositions = new Point2D.Float[this.getNumGlyphs() + 1];
            for (int i = 0; i <= this.getNumGlyphs(); ++i) {
                this.defaultGlyphPositions[i] = this.awtGlyphVector.getGlyphPosition(i);
            }
        }
        this.outline = null;
        this.visualBounds = null;
        this.logicalBounds = null;
        this.bounds2D = null;
        final float n = 0.0f;
        int j;
        for (j = 0; j < this.getNumGlyphs(); ++j) {
            this.glyphTransforms[j] = null;
            this.glyphVisualBounds[j] = null;
            this.glyphLogicalBounds[j] = null;
            this.glyphOutlines[j] = null;
            this.glyphMetrics[j] = null;
            final Point2D point2D = this.defaultGlyphPositions[j];
            final float n2 = (float)(point2D.getX() * this.scaleFactor - n);
            final float n3 = (float)(point2D.getY() * this.scaleFactor);
            this.ci.setIndex(j + this.ci.getBeginIndex());
            if (this.glyphPositions[j] == null) {
                this.glyphPositions[j] = new Point2D.Float(n2, n3);
            }
            else {
                this.glyphPositions[j].x = n2;
                this.glyphPositions[j].y = n3;
            }
        }
        final Point2D point2D2 = this.defaultGlyphPositions[j];
        this.glyphPositions[j] = new Point2D.Float((float)(point2D2.getX() * this.scaleFactor - n), (float)(point2D2.getY() * this.scaleFactor));
    }
    
    public void setGlyphPosition(final int n, final Point2D point2D) {
        this.glyphPositions[n].x = (float)point2D.getX();
        this.glyphPositions[n].y = (float)point2D.getY();
        this.outline = null;
        this.visualBounds = null;
        this.logicalBounds = null;
        this.bounds2D = null;
        if (n != this.getNumGlyphs()) {
            this.glyphVisualBounds[n] = null;
            this.glyphLogicalBounds[n] = null;
            this.glyphOutlines[n] = null;
            this.glyphMetrics[n] = null;
        }
    }
    
    public void setGlyphTransform(final int n, final AffineTransform affineTransform) {
        this.glyphTransforms[n] = affineTransform;
        this.outline = null;
        this.visualBounds = null;
        this.logicalBounds = null;
        this.bounds2D = null;
        this.glyphVisualBounds[n] = null;
        this.glyphLogicalBounds[n] = null;
        this.glyphOutlines[n] = null;
        this.glyphMetrics[n] = null;
    }
    
    public void setGlyphVisible(final int n, final boolean b) {
        if (b == this.glyphVisible[n]) {
            return;
        }
        this.glyphVisible[n] = b;
        this.outline = null;
        this.visualBounds = null;
        this.logicalBounds = null;
        this.bounds2D = null;
        this.glyphVisualBounds[n] = null;
        this.glyphLogicalBounds[n] = null;
        this.glyphOutlines[n] = null;
        this.glyphMetrics[n] = null;
    }
    
    public boolean isGlyphVisible(final int n) {
        return this.glyphVisible[n];
    }
    
    public int getCharacterCount(int n, int n2) {
        if (n < 0) {
            n = 0;
        }
        if (n2 >= this.getNumGlyphs()) {
            n2 = this.getNumGlyphs() - 1;
        }
        int n3 = 0;
        final int index = n + this.ci.getBeginIndex();
        final int n4 = n2 + this.ci.getBeginIndex();
        char c = this.ci.setIndex(index);
        while (this.ci.getIndex() <= n4) {
            n3 += ArabicTextHandler.getNumChars(c);
            c = this.ci.next();
        }
        return n3;
    }
    
    public void draw(final Graphics2D graphics2D, final AttributedCharacterIterator attributedCharacterIterator) {
        final int numGlyphs = this.getNumGlyphs();
        attributedCharacterIterator.first();
        final TextPaintInfo textPaintInfo = (TextPaintInfo)attributedCharacterIterator.getAttribute(GVTAttributedCharacterIterator.TextAttribute.PAINT_INFO);
        if (textPaintInfo == null) {
            return;
        }
        if (!textPaintInfo.visible) {
            return;
        }
        final Paint fillPaint = textPaintInfo.fillPaint;
        final Stroke strokeStroke = textPaintInfo.strokeStroke;
        final Paint strokePaint = textPaintInfo.strokePaint;
        if (fillPaint == null && (strokePaint == null || strokeStroke == null)) {
            return;
        }
        int drawGlyphVectorWorks = AWTGVTGlyphVector.drawGlyphVectorWorks ? 1 : 0;
        if (drawGlyphVectorWorks != 0 && strokeStroke != null && strokePaint != null) {
            drawGlyphVectorWorks = 0;
        }
        if (drawGlyphVectorWorks != 0 && fillPaint != null && !(fillPaint instanceof Color)) {
            drawGlyphVectorWorks = 0;
        }
        if (drawGlyphVectorWorks != 0) {
            final Object renderingHint = graphics2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
            final Object renderingHint2 = graphics2D.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
            if (renderingHint == RenderingHints.VALUE_TEXT_ANTIALIAS_ON && renderingHint2 == RenderingHints.VALUE_STROKE_PURE) {
                drawGlyphVectorWorks = 0;
            }
        }
        if (drawGlyphVectorWorks != 0) {
            final int type = graphics2D.getTransform().getType();
            if ((type & 0x20) != 0x0 || (type & 0x10) != 0x0) {
                drawGlyphVectorWorks = 0;
            }
        }
        if (drawGlyphVectorWorks != 0) {
            for (int i = 0; i < numGlyphs; ++i) {
                if (!this.glyphVisible[i]) {
                    drawGlyphVectorWorks = 0;
                    break;
                }
                final AffineTransform affineTransform = this.glyphTransforms[i];
                if (affineTransform != null) {
                    final int type2 = affineTransform.getType();
                    if ((type2 & 0xFFFFFFFE) != 0x0) {
                        if (!AWTGVTGlyphVector.glyphVectorTransformWorks || (type2 & 0x20) != 0x0 || (type2 & 0x10) != 0x0) {
                            drawGlyphVectorWorks = 0;
                            break;
                        }
                    }
                }
            }
        }
        if (drawGlyphVectorWorks != 0) {
            final double n = this.scaleFactor;
            final double[] array = new double[6];
            for (int j = 0; j < numGlyphs; ++j) {
                final Point2D.Float float1 = this.glyphPositions[j];
                double x = float1.getX();
                double y = float1.getY();
                AffineTransform affineTransform2 = this.glyphTransforms[j];
                if (affineTransform2 != null) {
                    affineTransform2.getMatrix(array);
                    x += array[4];
                    y += array[5];
                    if (array[0] != 1.0 || array[1] != 0.0 || array[2] != 0.0 || array[3] != 1.0) {
                        array[5] = (array[4] = 0.0);
                        affineTransform2 = new AffineTransform(array);
                    }
                    else {
                        affineTransform2 = null;
                    }
                }
                this.awtGlyphVector.setGlyphPosition(j, new Point2D.Double(x / n, y / n));
                this.awtGlyphVector.setGlyphTransform(j, affineTransform2);
            }
            graphics2D.scale(n, n);
            graphics2D.setPaint(fillPaint);
            graphics2D.drawGlyphVector(this.awtGlyphVector, 0.0f, 0.0f);
            graphics2D.scale(1.0 / n, 1.0 / n);
            for (int k = 0; k < numGlyphs; ++k) {
                this.awtGlyphVector.setGlyphPosition(k, this.defaultGlyphPositions[k]);
                this.awtGlyphVector.setGlyphTransform(k, null);
            }
        }
        else {
            final Shape outline = this.getOutline();
            if (fillPaint != null) {
                graphics2D.setPaint(fillPaint);
                graphics2D.fill(outline);
            }
            if (strokeStroke != null && strokePaint != null) {
                graphics2D.setStroke(strokeStroke);
                graphics2D.setPaint(strokePaint);
                graphics2D.draw(outline);
            }
        }
    }
    
    static {
        PAINT_INFO = GVTAttributedCharacterIterator.TextAttribute.PAINT_INFO;
        if ("1.4".compareTo(System.getProperty("java.specification.version")) <= 0) {
            outlinesPositioned = true;
            drawGlyphVectorWorks = true;
            glyphVectorTransformWorks = true;
        }
        else if ("Mac OS X".equals(System.getProperty("os.name"))) {
            outlinesPositioned = true;
            drawGlyphVectorWorks = false;
            glyphVectorTransformWorks = false;
        }
        else {
            outlinesPositioned = false;
            drawGlyphVectorWorks = true;
            glyphVectorTransformWorks = false;
        }
    }
}
