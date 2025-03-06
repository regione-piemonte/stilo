// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import java.util.HashSet;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
import java.awt.BasicStroke;
import java.util.Map;
import org.apache.batik.gvt.font.AWTGVTFont;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.GeneralPath;
import java.awt.Shape;
import java.awt.Graphics2D;
import org.apache.batik.gvt.font.GVTGlyphMetrics;
import org.apache.batik.gvt.font.AltGlyphHandler;
import java.text.CharacterIterator;
import java.awt.font.FontRenderContext;
import java.util.Set;
import java.awt.geom.Point2D;
import java.text.AttributedCharacterIterator;
import org.apache.batik.gvt.font.GVTLineMetrics;
import org.apache.batik.gvt.font.GVTFont;
import org.apache.batik.gvt.font.GVTGlyphVector;

public class GlyphLayout implements TextSpanLayout
{
    private GVTGlyphVector gv;
    private GVTFont font;
    private GVTLineMetrics metrics;
    private AttributedCharacterIterator aci;
    private Point2D advance;
    private Point2D offset;
    private float xScale;
    private float yScale;
    private TextPath textPath;
    private Point2D textPathAdvance;
    private int[] charMap;
    private boolean vertical;
    private boolean adjSpacing;
    private float[] glyphAdvances;
    private boolean isAltGlyph;
    private boolean layoutApplied;
    private boolean spacingApplied;
    private boolean pathApplied;
    public static final AttributedCharacterIterator.Attribute FLOW_LINE_BREAK;
    public static final AttributedCharacterIterator.Attribute FLOW_PARAGRAPH;
    public static final AttributedCharacterIterator.Attribute FLOW_EMPTY_PARAGRAPH;
    public static final AttributedCharacterIterator.Attribute LINE_HEIGHT;
    public static final AttributedCharacterIterator.Attribute VERTICAL_ORIENTATION;
    public static final AttributedCharacterIterator.Attribute VERTICAL_ORIENTATION_ANGLE;
    public static final AttributedCharacterIterator.Attribute HORIZONTAL_ORIENTATION_ANGLE;
    private static final AttributedCharacterIterator.Attribute X;
    private static final AttributedCharacterIterator.Attribute Y;
    private static final AttributedCharacterIterator.Attribute DX;
    private static final AttributedCharacterIterator.Attribute DY;
    private static final AttributedCharacterIterator.Attribute ROTATION;
    private static final AttributedCharacterIterator.Attribute BASELINE_SHIFT;
    private static final AttributedCharacterIterator.Attribute WRITING_MODE;
    private static final Integer WRITING_MODE_TTB;
    private static final Integer ORIENTATION_AUTO;
    public static final AttributedCharacterIterator.Attribute GVT_FONT;
    protected static Set runAtts;
    protected static Set szAtts;
    public static final double eps = 1.0E-5;
    
    public GlyphLayout(final AttributedCharacterIterator aci, final int[] charMap, final Point2D offset, final FontRenderContext fontRenderContext) {
        this.xScale = 1.0f;
        this.yScale = 1.0f;
        this.adjSpacing = true;
        this.layoutApplied = false;
        this.spacingApplied = false;
        this.pathApplied = false;
        this.aci = aci;
        this.offset = offset;
        this.font = this.getFont();
        this.charMap = charMap;
        this.metrics = this.font.getLineMetrics(aci, aci.getBeginIndex(), aci.getEndIndex(), fontRenderContext);
        this.gv = null;
        this.aci.first();
        this.vertical = (aci.getAttribute(GlyphLayout.WRITING_MODE) == GlyphLayout.WRITING_MODE_TTB);
        this.textPath = (TextPath)aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.TEXTPATH);
        final AltGlyphHandler altGlyphHandler = (AltGlyphHandler)this.aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.ALT_GLYPH_HANDLER);
        if (altGlyphHandler != null) {
            this.gv = altGlyphHandler.createGlyphVector(fontRenderContext, this.font.getSize(), this.aci);
            if (this.gv != null) {
                this.isAltGlyph = true;
            }
        }
        if (this.gv == null) {
            this.gv = this.font.createGlyphVector(fontRenderContext, this.aci);
        }
    }
    
    public GVTGlyphVector getGlyphVector() {
        return this.gv;
    }
    
    public Point2D getOffset() {
        return this.offset;
    }
    
    public void setScale(float xScale, float yScale, final boolean adjSpacing) {
        if (this.vertical) {
            xScale = 1.0f;
        }
        else {
            yScale = 1.0f;
        }
        if (xScale != this.xScale || yScale != this.yScale || adjSpacing != this.adjSpacing) {
            this.xScale = xScale;
            this.yScale = yScale;
            this.adjSpacing = adjSpacing;
            this.spacingApplied = false;
            this.glyphAdvances = null;
            this.pathApplied = false;
        }
    }
    
    public void setOffset(final Point2D offset) {
        if (offset.getX() != this.offset.getX() || offset.getY() != this.offset.getY()) {
            if (this.layoutApplied || this.spacingApplied) {
                final float n = (float)(offset.getX() - this.offset.getX());
                final float n2 = (float)(offset.getY() - this.offset.getY());
                final int numGlyphs = this.gv.getNumGlyphs();
                final float[] glyphPositions = this.gv.getGlyphPositions(0, numGlyphs + 1, null);
                final Point2D.Float float1 = new Point2D.Float();
                for (int i = 0; i <= numGlyphs; ++i) {
                    float1.x = glyphPositions[2 * i] + n;
                    float1.y = glyphPositions[2 * i + 1] + n2;
                    this.gv.setGlyphPosition(i, float1);
                }
            }
            this.offset = offset;
            this.pathApplied = false;
        }
    }
    
    public GVTGlyphMetrics getGlyphMetrics(final int n) {
        return this.gv.getGlyphMetrics(n);
    }
    
    public GVTLineMetrics getLineMetrics() {
        return this.metrics;
    }
    
    public boolean isVertical() {
        return this.vertical;
    }
    
    public boolean isOnATextPath() {
        return this.textPath != null;
    }
    
    public int getGlyphCount() {
        return this.gv.getNumGlyphs();
    }
    
    public int getCharacterCount(final int n, final int n2) {
        return this.gv.getCharacterCount(n, n2);
    }
    
    public boolean isLeftToRight() {
        this.aci.first();
        return ((int)this.aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.BIDI_LEVEL) & 0x1) == 0x0;
    }
    
    private final void syncLayout() {
        if (!this.pathApplied) {
            this.doPathLayout();
        }
    }
    
    public void draw(final Graphics2D graphics2D) {
        this.syncLayout();
        this.gv.draw(graphics2D, this.aci);
    }
    
    public Point2D getAdvance2D() {
        this.adjustTextSpacing();
        return this.advance;
    }
    
    public Shape getOutline() {
        this.syncLayout();
        return this.gv.getOutline();
    }
    
    public float[] getGlyphAdvances() {
        if (this.glyphAdvances != null) {
            return this.glyphAdvances;
        }
        if (!this.spacingApplied) {
            this.adjustTextSpacing();
        }
        final int numGlyphs = this.gv.getNumGlyphs();
        final float[] glyphPositions = this.gv.getGlyphPositions(0, numGlyphs + 1, null);
        this.glyphAdvances = new float[numGlyphs + 1];
        int n = 0;
        if (this.isVertical()) {
            n = 1;
        }
        final float n2 = glyphPositions[n];
        for (int i = 0; i < numGlyphs + 1; ++i) {
            this.glyphAdvances[i] = glyphPositions[2 * i + n] - n2;
        }
        return this.glyphAdvances;
    }
    
    public Shape getDecorationOutline(final int n) {
        this.syncLayout();
        final GeneralPath generalPath = new GeneralPath();
        if ((n & 0x1) != 0x0) {
            generalPath.append(this.getUnderlineShape(), false);
        }
        if ((n & 0x2) != 0x0) {
            generalPath.append(this.getStrikethroughShape(), false);
        }
        if ((n & 0x4) != 0x0) {
            generalPath.append(this.getOverlineShape(), false);
        }
        return generalPath;
    }
    
    public Rectangle2D getBounds2D() {
        this.syncLayout();
        return this.gv.getBounds2D(this.aci);
    }
    
    public Rectangle2D getGeometricBounds() {
        this.syncLayout();
        return this.gv.getGeometricBounds().createUnion(this.getDecorationOutline(7).getBounds2D());
    }
    
    public Point2D getTextPathAdvance() {
        this.syncLayout();
        if (this.textPath != null) {
            return this.textPathAdvance;
        }
        return this.getAdvance2D();
    }
    
    public int getGlyphIndex(final int n) {
        final int glyphCount = this.getGlyphCount();
        int n2 = 0;
        for (int i = 0; i < glyphCount; ++i) {
            for (int characterCount = this.getCharacterCount(i, i), j = 0; j < characterCount; ++j) {
                if (n == this.charMap[n2++]) {
                    return i;
                }
                if (n2 >= this.charMap.length) {
                    return -1;
                }
            }
        }
        return -1;
    }
    
    public int getLastGlyphIndex(final int n) {
        final int glyphCount = this.getGlyphCount();
        int n2 = this.charMap.length - 1;
        for (int i = glyphCount - 1; i >= 0; --i) {
            for (int characterCount = this.getCharacterCount(i, i), j = 0; j < characterCount; ++j) {
                if (n == this.charMap[n2--]) {
                    return i;
                }
                if (n2 < 0) {
                    return -1;
                }
            }
        }
        return -1;
    }
    
    public double getComputedOrientationAngle(final int index) {
        if (!this.isGlyphOrientationAuto()) {
            return this.getGlyphOrientationAngle();
        }
        if (!this.isVertical()) {
            return 0.0;
        }
        if (this.isLatinChar(this.aci.setIndex(index))) {
            return 90.0;
        }
        return 0.0;
    }
    
    public Shape getHighlightShape(int n, int n2) {
        this.syncLayout();
        if (n > n2) {
            final int n3 = n;
            n = n2;
            n2 = n3;
        }
        GeneralPath generalPath = null;
        final int glyphCount = this.getGlyphCount();
        final Point2D.Float[] array = new Point2D.Float[2 * glyphCount];
        final Point2D.Float[] array2 = new Point2D.Float[2 * glyphCount];
        int n4 = 0;
        int n5 = 0;
        for (int i = 0; i < glyphCount; ++i) {
            final int n6 = this.charMap[n5];
            if (n6 >= n && n6 <= n2 && this.gv.isGlyphVisible(i)) {
                final Shape glyphLogicalBounds = this.gv.getGlyphLogicalBounds(i);
                if (glyphLogicalBounds != null) {
                    if (generalPath == null) {
                        generalPath = new GeneralPath();
                    }
                    final float[] array3 = new float[6];
                    int n7 = 0;
                    final PathIterator pathIterator = glyphLogicalBounds.getPathIterator(null);
                    Point2D.Float float1 = null;
                    while (!pathIterator.isDone()) {
                        final int currentSegment = pathIterator.currentSegment(array3);
                        if (currentSegment == 0 || currentSegment == 1) {
                            if (n7 > 4) {
                                break;
                            }
                            if (n7 == 4) {
                                if (float1 == null || float1.x != array3[0]) {
                                    break;
                                }
                                if (float1.y != array3[1]) {
                                    break;
                                }
                            }
                            else {
                                final Point2D.Float float2 = new Point2D.Float(array3[0], array3[1]);
                                if (n7 == 0) {
                                    float1 = float2;
                                }
                                switch (n7) {
                                    case 0: {
                                        array2[n4] = float2;
                                        break;
                                    }
                                    case 1: {
                                        array[n4] = float2;
                                        break;
                                    }
                                    case 2: {
                                        array[n4 + 1] = float2;
                                        break;
                                    }
                                    case 3: {
                                        array2[n4 + 1] = float2;
                                        break;
                                    }
                                }
                            }
                        }
                        else {
                            if (currentSegment != 4 || n7 < 4) {
                                break;
                            }
                            if (n7 > 5) {
                                break;
                            }
                        }
                        ++n7;
                        pathIterator.next();
                    }
                    if (pathIterator.isDone()) {
                        if (array2[n4] != null && (array[n4].x != array[n4 + 1].x || array[n4].y != array[n4 + 1].y)) {
                            n4 += 2;
                        }
                    }
                    else {
                        addPtsToPath(generalPath, array, array2, n4);
                        n4 = 0;
                        generalPath.append(glyphLogicalBounds, false);
                    }
                }
            }
            n5 += this.getCharacterCount(i, i);
            if (n5 >= this.charMap.length) {
                n5 = this.charMap.length - 1;
            }
        }
        addPtsToPath(generalPath, array, array2, n4);
        return generalPath;
    }
    
    public static boolean epsEQ(final double n, final double n2) {
        return n + 1.0E-5 > n2 && n - 1.0E-5 < n2;
    }
    
    public static int makeConvexHull(final Point2D.Float[] array, final int n) {
        for (int i = 1; i < n; ++i) {
            if (array[i].x < array[i - 1].x || (array[i].x == array[i - 1].x && array[i].y < array[i - 1].y)) {
                final Point2D.Float float1 = array[i];
                array[i] = array[i - 1];
                array[i - 1] = float1;
                i = 0;
            }
        }
        final Point2D.Float float2 = array[0];
        final Point2D.Float float3 = array[n - 1];
        final Point2D.Float float4 = new Point2D.Float(float3.x - float2.x, float3.y - float2.y);
        final float n2 = float4.y * float2.x - float4.x * float2.y;
        final Point2D.Float[] array2 = new Point2D.Float[n];
        final Point2D.Float[] array3 = new Point2D.Float[n];
        array3[0] = (array2[0] = array[0]);
        int j = 1;
        int k = 1;
        for (int l = 1; l < n - 1; ++l) {
            Point2D.Float float5 = array[l];
            if (float4.x * float5.y - float4.y * float5.x + n2 < 0.0f) {
                while (k >= 2) {
                    final Point2D.Float float6 = array3[k - 2];
                    final Point2D.Float float7 = array3[k - 1];
                    final float n3 = float7.x - float6.x;
                    final float n4 = float7.y - float6.y;
                    final float n5 = n3 * float5.y - n4 * float5.x + (n4 * float6.x - n3 * float6.y);
                    if (n5 > 1.0E-5) {
                        break;
                    }
                    if (n5 > -1.0E-5) {
                        if (float7.y < float5.y) {
                            float5 = float7;
                        }
                        --k;
                        break;
                    }
                    --k;
                }
                array3[k++] = float5;
            }
            else {
                while (j >= 2) {
                    final Point2D.Float float8 = array2[j - 2];
                    final Point2D.Float float9 = array2[j - 1];
                    final float n6 = float9.x - float8.x;
                    final float n7 = float9.y - float8.y;
                    final float n8 = n6 * float5.y - n7 * float5.x + (n7 * float8.x - n6 * float8.y);
                    if (n8 < -1.0E-5) {
                        break;
                    }
                    if (n8 < 1.0E-5) {
                        if (float9.y > float5.y) {
                            float5 = float9;
                        }
                        --j;
                        break;
                    }
                    --j;
                }
                array2[j++] = float5;
            }
        }
        final Point2D.Float float10 = array[n - 1];
        while (k >= 2) {
            final Point2D.Float float11 = array3[k - 2];
            final Point2D.Float float12 = array3[k - 1];
            final float n9 = float12.x - float11.x;
            final float n10 = float12.y - float11.y;
            final float n11 = n9 * float10.y - n10 * float10.x + (n10 * float11.x - n9 * float11.y);
            if (n11 > 1.0E-5) {
                break;
            }
            if (n11 > -1.0E-5) {
                if (float12.y >= float10.y) {
                    --k;
                    break;
                }
                break;
            }
            else {
                --k;
            }
        }
        while (j >= 2) {
            final Point2D.Float float13 = array2[j - 2];
            final Point2D.Float float14 = array2[j - 1];
            final float n12 = float14.x - float13.x;
            final float n13 = float14.y - float13.y;
            final float n14 = n12 * float10.y - n13 * float10.x + (n13 * float13.x - n12 * float13.y);
            if (n14 < -1.0E-5) {
                break;
            }
            if (n14 < 1.0E-5) {
                if (float14.y <= float10.y) {
                    --j;
                    break;
                }
                break;
            }
            else {
                --j;
            }
        }
        System.arraycopy(array2, 0, array, 0, j);
        int n15 = j;
        array[n15++] = array[n - 1];
        for (int n16 = k - 1; n16 > 0; --n16, ++n15) {
            array[n15] = array3[n16];
        }
        return n15;
    }
    
    public static void addPtsToPath(final GeneralPath generalPath, final Point2D.Float[] array, final Point2D.Float[] array2, final int n) {
        if (n < 2) {
            return;
        }
        if (n == 2) {
            generalPath.moveTo(array[0].x, array[0].y);
            generalPath.lineTo(array[1].x, array[1].y);
            generalPath.lineTo(array2[1].x, array2[1].y);
            generalPath.lineTo(array2[0].x, array2[0].y);
            generalPath.lineTo(array[0].x, array[0].y);
            return;
        }
        final Point2D.Float[] array3 = new Point2D.Float[8];
        final Point2D.Float[] array4 = new Point2D.Float[8];
        array3[4] = array[0];
        array3[5] = array[1];
        array3[6] = array2[1];
        array3[7] = array2[0];
        final Area[] array5 = new Area[n / 2];
        int n2 = 0;
        for (int i = 2; i < n; i += 2) {
            array3[0] = array3[4];
            array3[1] = array3[5];
            array3[2] = array3[6];
            array3[3] = array3[7];
            array3[4] = array[i];
            array3[5] = array[i + 1];
            array3[6] = array2[i + 1];
            array3[7] = array2[i];
            final float n3 = array3[2].x - array3[0].x;
            final float n4 = n3 * n3;
            final float n5 = array3[2].y - array3[0].y;
            final float n6 = (float)Math.sqrt(n4 + n5 * n5);
            final float n7 = array3[6].x - array3[4].x;
            final float n8 = n7 * n7;
            final float n9 = array3[6].y - array3[4].y;
            final float n10 = n6 + (float)Math.sqrt(n8 + n9 * n9);
            final float n11 = (array3[0].x + array3[1].x + array3[2].x + array3[3].x - (array3[4].x + array3[5].x + array3[6].x + array3[7].x)) / 4.0f;
            final float n12 = n11 * n11;
            final float n13 = (array3[0].y + array3[1].y + array3[2].y + array3[3].y - (array3[4].y + array3[5].y + array3[6].y + array3[7].y)) / 4.0f;
            final float n14 = (float)Math.sqrt(n12 + n13 * n13);
            final GeneralPath generalPath2 = new GeneralPath();
            if (n14 < n10) {
                System.arraycopy(array3, 0, array4, 0, 8);
                final int convexHull = makeConvexHull(array4, 8);
                generalPath2.moveTo(array4[0].x, array4[0].y);
                for (int j = 1; j < convexHull; ++j) {
                    generalPath2.lineTo(array4[j].x, array4[j].y);
                }
                generalPath2.closePath();
            }
            else {
                mergeAreas(generalPath, array5, n2);
                n2 = 0;
                if (i == 2) {
                    generalPath2.moveTo(array3[0].x, array3[0].y);
                    generalPath2.lineTo(array3[1].x, array3[1].y);
                    generalPath2.lineTo(array3[2].x, array3[2].y);
                    generalPath2.lineTo(array3[3].x, array3[3].y);
                    generalPath2.closePath();
                    generalPath.append(generalPath2, false);
                    generalPath2.reset();
                }
                generalPath2.moveTo(array3[4].x, array3[4].y);
                generalPath2.lineTo(array3[5].x, array3[5].y);
                generalPath2.lineTo(array3[6].x, array3[6].y);
                generalPath2.lineTo(array3[7].x, array3[7].y);
                generalPath2.closePath();
            }
            array5[n2++] = new Area(generalPath2);
        }
        mergeAreas(generalPath, array5, n2);
    }
    
    public static void mergeAreas(final GeneralPath generalPath, final Area[] array, int i) {
        while (i > 1) {
            int n = 0;
            for (int j = 1; j < i; j += 2) {
                array[j - 1].add(array[j]);
                array[n++] = array[j - 1];
                array[j] = null;
            }
            if ((i & 0x1) == 0x1) {
                array[n - 1].add(array[i - 1]);
            }
            i /= 2;
        }
        if (i == 1) {
            generalPath.append(array[0], false);
        }
    }
    
    public TextHit hitTestChar(final float n, final float n2) {
        this.syncLayout();
        final TextHit textHit = null;
        int n3 = 0;
        for (int i = 0; i < this.gv.getNumGlyphs(); ++i) {
            final Shape glyphLogicalBounds = this.gv.getGlyphLogicalBounds(i);
            if (glyphLogicalBounds != null) {
                final Rectangle2D bounds2D = glyphLogicalBounds.getBounds2D();
                if (glyphLogicalBounds.contains(n, n2)) {
                    return new TextHit(this.charMap[n3], n <= bounds2D.getX() + bounds2D.getWidth() / 2.0);
                }
            }
            n3 += this.getCharacterCount(i, i);
            if (n3 >= this.charMap.length) {
                n3 = this.charMap.length - 1;
            }
        }
        return textHit;
    }
    
    protected GVTFont getFont() {
        this.aci.first();
        final GVTFont gvtFont = (GVTFont)this.aci.getAttribute(GlyphLayout.GVT_FONT);
        if (gvtFont != null) {
            return gvtFont;
        }
        return new AWTGVTFont(this.aci.getAttributes());
    }
    
    protected Shape getOverlineShape() {
        final double n = this.metrics.getOverlineOffset();
        final float overlineThickness = this.metrics.getOverlineThickness();
        double n2 = n + overlineThickness;
        this.aci.first();
        final Float n3 = (Float)this.aci.getAttribute(GlyphLayout.DY);
        if (n3 != null) {
            n2 += n3;
        }
        final BasicStroke basicStroke = new BasicStroke(overlineThickness);
        final Rectangle2D logicalBounds = this.gv.getLogicalBounds();
        return basicStroke.createStrokedShape(new Line2D.Double(logicalBounds.getMinX() + overlineThickness / 2.0, this.offset.getY() + n2, logicalBounds.getMaxX() - overlineThickness / 2.0, this.offset.getY() + n2));
    }
    
    protected Shape getUnderlineShape() {
        final double n = this.metrics.getUnderlineOffset();
        final float underlineThickness = this.metrics.getUnderlineThickness();
        double n2 = n + underlineThickness * 1.5;
        final BasicStroke basicStroke = new BasicStroke(underlineThickness);
        this.aci.first();
        final Float n3 = (Float)this.aci.getAttribute(GlyphLayout.DY);
        if (n3 != null) {
            n2 += n3;
        }
        final Rectangle2D logicalBounds = this.gv.getLogicalBounds();
        return basicStroke.createStrokedShape(new Line2D.Double(logicalBounds.getMinX() + underlineThickness / 2.0, this.offset.getY() + n2, logicalBounds.getMaxX() - underlineThickness / 2.0, this.offset.getY() + n2));
    }
    
    protected Shape getStrikethroughShape() {
        double n = this.metrics.getStrikethroughOffset();
        final float strikethroughThickness = this.metrics.getStrikethroughThickness();
        final BasicStroke basicStroke = new BasicStroke(strikethroughThickness);
        this.aci.first();
        final Float n2 = (Float)this.aci.getAttribute(GlyphLayout.DY);
        if (n2 != null) {
            n += n2;
        }
        final Rectangle2D logicalBounds = this.gv.getLogicalBounds();
        return basicStroke.createStrokedShape(new Line2D.Double(logicalBounds.getMinX() + strikethroughThickness / 2.0, this.offset.getY() + n, logicalBounds.getMaxX() - strikethroughThickness / 2.0, this.offset.getY() + n));
    }
    
    protected void doExplicitGlyphLayout() {
        this.gv.performDefaultLayout();
        final float n = this.vertical ? ((float)this.gv.getLogicalBounds().getWidth()) : (this.metrics.getAscent() + Math.abs(this.metrics.getDescent()));
        final int numGlyphs = this.gv.getNumGlyphs();
        final float[] glyphPositions = this.gv.getGlyphPositions(0, numGlyphs + 1, null);
        float n2 = 0.0f;
        float n3 = 0.0f;
        final boolean glyphOrientationAuto = this.isGlyphOrientationAuto();
        int glyphOrientationAngle = 0;
        if (!glyphOrientationAuto) {
            glyphOrientationAngle = this.getGlyphOrientationAngle();
        }
        int i = 0;
        final int beginIndex = this.aci.getBeginIndex();
        int n4 = 0;
        char c = this.aci.first();
        int runLimit = n4 + beginIndex;
        Float n5 = null;
        Float n6 = null;
        Float n7 = null;
        Float n8 = null;
        Float n9 = null;
        Object attribute = null;
        float n10 = 0.0f;
        float n11 = 0.0f;
        float x = (float)this.offset.getX();
        float y = (float)this.offset.getY();
        final Point2D.Float float1 = new Point2D.Float();
        boolean b = false;
        while (i < numGlyphs) {
            if (n4 + beginIndex >= runLimit) {
                runLimit = this.aci.getRunLimit(GlyphLayout.runAtts);
                n5 = (Float)this.aci.getAttribute(GlyphLayout.X);
                n6 = (Float)this.aci.getAttribute(GlyphLayout.Y);
                n7 = (Float)this.aci.getAttribute(GlyphLayout.DX);
                n8 = (Float)this.aci.getAttribute(GlyphLayout.DY);
                n9 = (Float)this.aci.getAttribute(GlyphLayout.ROTATION);
                attribute = this.aci.getAttribute(GlyphLayout.BASELINE_SHIFT);
            }
            final GVTGlyphMetrics glyphMetrics = this.gv.getGlyphMetrics(i);
            if (i == 0) {
                if (this.isVertical()) {
                    if (glyphOrientationAuto) {
                        if (this.isLatinChar(c)) {
                            n2 = 0.0f;
                        }
                        else {
                            final float verticalAdvance = glyphMetrics.getVerticalAdvance();
                            final float ascent = this.metrics.getAscent();
                            n2 = ascent + (verticalAdvance - (ascent + this.metrics.getDescent())) / 2.0f;
                        }
                    }
                    else if (glyphOrientationAngle == 0) {
                        final float verticalAdvance2 = glyphMetrics.getVerticalAdvance();
                        final float ascent2 = this.metrics.getAscent();
                        n2 = ascent2 + (verticalAdvance2 - (ascent2 + this.metrics.getDescent())) / 2.0f;
                    }
                    else {
                        n2 = 0.0f;
                    }
                }
                else if (glyphOrientationAngle == 270) {
                    n3 = (float)glyphMetrics.getBounds2D().getHeight();
                }
                else {
                    n3 = 0.0f;
                }
            }
            else if (glyphOrientationAuto && n2 == 0.0f && !this.isLatinChar(c)) {
                final float verticalAdvance3 = glyphMetrics.getVerticalAdvance();
                final float ascent3 = this.metrics.getAscent();
                n2 = ascent3 + (verticalAdvance3 - (ascent3 + this.metrics.getDescent())) / 2.0f;
            }
            float n12 = 0.0f;
            float n13 = 0.0f;
            float n14 = 0.0f;
            if (c != '\uffff') {
                float n15;
                if (this.vertical) {
                    if (glyphOrientationAuto) {
                        if (this.isLatinChar(c)) {
                            n15 = 1.5707964f;
                        }
                        else {
                            n15 = 0.0f;
                        }
                    }
                    else {
                        n15 = (float)Math.toRadians(glyphOrientationAngle);
                    }
                    if (this.textPath != null) {
                        n5 = null;
                    }
                }
                else {
                    n15 = (float)Math.toRadians(glyphOrientationAngle);
                    if (this.textPath != null) {
                        n6 = null;
                    }
                }
                if (n9 == null || n9.isNaN()) {
                    n14 = n15;
                }
                else {
                    n14 = n9 + n15;
                }
                if (n5 != null && !n5.isNaN()) {
                    if (i == 0) {
                        n10 = (float)(n5 - this.offset.getX());
                    }
                    x = n5 - n10;
                }
                if (n7 != null && !n7.isNaN()) {
                    x += n7;
                }
                if (n6 != null && !n6.isNaN()) {
                    if (i == 0) {
                        n11 = (float)(n6 - this.offset.getY());
                    }
                    y = n6 - n11;
                }
                if (n8 != null && !n8.isNaN()) {
                    y += n8;
                }
                else if (i > 0) {
                    y += glyphPositions[i * 2 + 1] - glyphPositions[i * 2 - 1];
                }
                float floatValue = 0.0f;
                if (attribute != null) {
                    if (attribute instanceof Integer) {
                        if (attribute == TextAttribute.SUPERSCRIPT_SUPER) {
                            floatValue = n * 0.5f;
                        }
                        else if (attribute == TextAttribute.SUPERSCRIPT_SUB) {
                            floatValue = -n * 0.5f;
                        }
                    }
                    else if (attribute instanceof Float) {
                        floatValue = (float)attribute;
                    }
                    if (this.vertical) {
                        n12 = floatValue;
                    }
                    else {
                        n13 = -floatValue;
                    }
                }
                if (this.vertical) {
                    n13 += n2;
                    if (glyphOrientationAuto) {
                        if (this.isLatinChar(c)) {
                            n12 += this.metrics.getStrikethroughOffset();
                        }
                        else {
                            final Rectangle2D bounds2D = this.gv.getGlyphVisualBounds(i).getBounds2D();
                            n12 -= (float)(bounds2D.getMaxX() - glyphPositions[2 * i] - bounds2D.getWidth() / 2.0);
                        }
                    }
                    else {
                        final Rectangle2D bounds2D2 = this.gv.getGlyphVisualBounds(i).getBounds2D();
                        if (glyphOrientationAngle == 0) {
                            n12 -= (float)(bounds2D2.getMaxX() - glyphPositions[2 * i] - bounds2D2.getWidth() / 2.0);
                        }
                        else if (glyphOrientationAngle == 180) {
                            n12 += (float)(bounds2D2.getMaxX() - glyphPositions[2 * i] - bounds2D2.getWidth() / 2.0);
                        }
                        else if (glyphOrientationAngle == 90) {
                            n12 += this.metrics.getStrikethroughOffset();
                        }
                        else {
                            n12 -= this.metrics.getStrikethroughOffset();
                        }
                    }
                }
                else {
                    n12 += n3;
                    if (glyphOrientationAngle == 90) {
                        n13 -= glyphMetrics.getHorizontalAdvance();
                    }
                    else if (glyphOrientationAngle == 180) {
                        n13 -= this.metrics.getAscent();
                    }
                }
            }
            float1.x = x + n12;
            float1.y = y + n13;
            this.gv.setGlyphPosition(i, float1);
            if (ArabicTextHandler.arabicCharTransparent(c)) {
                b = true;
            }
            else if (this.vertical) {
                float n16;
                if (glyphOrientationAuto) {
                    if (this.isLatinChar(c)) {
                        n16 = glyphMetrics.getHorizontalAdvance();
                    }
                    else {
                        n16 = glyphMetrics.getVerticalAdvance();
                    }
                }
                else if (glyphOrientationAngle == 0 || glyphOrientationAngle == 180) {
                    n16 = glyphMetrics.getVerticalAdvance();
                }
                else if (glyphOrientationAngle == 90) {
                    n16 = glyphMetrics.getHorizontalAdvance();
                }
                else {
                    n16 = glyphMetrics.getHorizontalAdvance();
                    this.gv.setGlyphTransform(i, AffineTransform.getTranslateInstance(0.0, n16));
                }
                y += n16;
            }
            else {
                float n17;
                if (glyphOrientationAngle == 0) {
                    n17 = glyphMetrics.getHorizontalAdvance();
                }
                else if (glyphOrientationAngle == 180) {
                    n17 = glyphMetrics.getHorizontalAdvance();
                    this.gv.setGlyphTransform(i, AffineTransform.getTranslateInstance(n17, 0.0));
                }
                else {
                    n17 = glyphMetrics.getVerticalAdvance();
                }
                x += n17;
            }
            if (!epsEQ(n14, 0.0)) {
                AffineTransform glyphTransform = this.gv.getGlyphTransform(i);
                if (glyphTransform == null) {
                    glyphTransform = new AffineTransform();
                }
                AffineTransform rotateInstance;
                if (epsEQ(n14, 1.5707963267948966)) {
                    rotateInstance = new AffineTransform(0.0f, 1.0f, -1.0f, 0.0f, 0.0f, 0.0f);
                }
                else if (epsEQ(n14, 3.141592653589793)) {
                    rotateInstance = new AffineTransform(-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f);
                }
                else if (epsEQ(n14, 4.71238898038469)) {
                    rotateInstance = new AffineTransform(0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
                }
                else {
                    rotateInstance = AffineTransform.getRotateInstance(n14);
                }
                glyphTransform.concatenate(rotateInstance);
                this.gv.setGlyphTransform(i, glyphTransform);
            }
            n4 += this.gv.getCharacterCount(i, i);
            if (n4 >= this.charMap.length) {
                n4 = this.charMap.length - 1;
            }
            c = this.aci.setIndex(n4 + beginIndex);
            ++i;
        }
        float1.x = x;
        float1.y = y;
        this.gv.setGlyphPosition(i, float1);
        this.advance = new Point2D.Float((float)(x - this.offset.getX()), (float)(y - this.offset.getY()));
        if (b) {
            char c2 = this.aci.first();
            int n18 = 0;
            int j = 0;
            int n19 = -1;
            while (j < numGlyphs) {
                if (ArabicTextHandler.arabicCharTransparent(c2)) {
                    if (n19 == -1) {
                        n19 = j;
                    }
                }
                else if (n19 != -1) {
                    final Point2D glyphPosition = this.gv.getGlyphPosition(j);
                    final GVTGlyphMetrics glyphMetrics2 = this.gv.getGlyphMetrics(j);
                    final boolean b2 = false;
                    final boolean b3 = false;
                    float n20 = 0.0f;
                    float n21 = 0.0f;
                    if (this.vertical) {
                        if (glyphOrientationAuto || glyphOrientationAngle == 90) {
                            n21 = glyphMetrics2.getHorizontalAdvance();
                        }
                        else if (glyphOrientationAngle == 270) {
                            n21 = 0.0f;
                        }
                        else if (glyphOrientationAngle == 0) {
                            n20 = glyphMetrics2.getHorizontalAdvance();
                        }
                        else {
                            n20 = -glyphMetrics2.getHorizontalAdvance();
                        }
                    }
                    else if (glyphOrientationAngle == 0) {
                        n20 = glyphMetrics2.getHorizontalAdvance();
                    }
                    else if (glyphOrientationAngle == 90) {
                        n21 = glyphMetrics2.getHorizontalAdvance();
                    }
                    else if (glyphOrientationAngle == 180) {
                        n20 = 0.0f;
                    }
                    else {
                        n21 = -glyphMetrics2.getHorizontalAdvance();
                    }
                    final float n22 = (float)(glyphPosition.getX() + n20);
                    final float n23 = (float)(glyphPosition.getY() + n21);
                    for (int k = n19; k < j; ++k) {
                        final Point2D glyphPosition2 = this.gv.getGlyphPosition(k);
                        final GVTGlyphMetrics glyphMetrics3 = this.gv.getGlyphMetrics(k);
                        float n24 = (float)glyphPosition2.getX();
                        float n25 = (float)glyphPosition2.getY();
                        final float n26 = 0.0f;
                        final float n27 = 0.0f;
                        final float horizontalAdvance = glyphMetrics3.getHorizontalAdvance();
                        if (this.vertical) {
                            if (glyphOrientationAuto || glyphOrientationAngle == 90) {
                                n25 = n23 - horizontalAdvance;
                            }
                            else if (glyphOrientationAngle == 270) {
                                n25 = n23 + horizontalAdvance;
                            }
                            else if (glyphOrientationAngle == 0) {
                                n24 = n22 - horizontalAdvance;
                            }
                            else {
                                n24 = n22 + horizontalAdvance;
                            }
                        }
                        else if (glyphOrientationAngle == 0) {
                            n24 = n22 - horizontalAdvance;
                        }
                        else if (glyphOrientationAngle == 90) {
                            n25 = n23 - horizontalAdvance;
                        }
                        else if (glyphOrientationAngle == 180) {
                            n24 = n22 + horizontalAdvance;
                        }
                        else {
                            n25 = n23 + horizontalAdvance;
                        }
                        this.gv.setGlyphPosition(k, new Point2D.Double(n24, n25));
                        if (b3 || b2) {
                            final AffineTransform translateInstance = AffineTransform.getTranslateInstance(n26, n27);
                            translateInstance.concatenate(this.gv.getGlyphTransform(j));
                            this.gv.setGlyphTransform(j, translateInstance);
                        }
                    }
                    n19 = -1;
                }
                n18 += this.gv.getCharacterCount(j, j);
                if (n18 >= this.charMap.length) {
                    n18 = this.charMap.length - 1;
                }
                c2 = this.aci.setIndex(n18 + beginIndex);
                ++j;
            }
        }
        this.layoutApplied = true;
        this.spacingApplied = false;
        this.glyphAdvances = null;
        this.pathApplied = false;
    }
    
    protected void adjustTextSpacing() {
        if (this.spacingApplied) {
            return;
        }
        if (!this.layoutApplied) {
            this.doExplicitGlyphLayout();
        }
        this.aci.first();
        final Boolean b = (Boolean)this.aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.CUSTOM_SPACING);
        if (b != null && b) {
            this.advance = this.doSpacing((Float)this.aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.KERNING), (Float)this.aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.LETTER_SPACING), (Float)this.aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.WORD_SPACING));
            this.layoutApplied = false;
        }
        this.applyStretchTransform(!this.adjSpacing);
        this.spacingApplied = true;
        this.pathApplied = false;
    }
    
    protected Point2D doSpacing(final Float n, final Float n2, final Float n3) {
        boolean b = true;
        boolean b2 = false;
        boolean b3 = false;
        float floatValue = 0.0f;
        float floatValue2 = 0.0f;
        if (n != null && !n.isNaN()) {
            floatValue = n;
            b = false;
        }
        if (n2 != null && !n2.isNaN()) {
            floatValue2 = n2;
            b3 = true;
        }
        if (n3 != null && !n3.isNaN()) {
            b2 = true;
        }
        final int numGlyphs = this.gv.getNumGlyphs();
        final Point2D[] array = new Point2D[numGlyphs + 1];
        Point2D glyphPosition = this.gv.getGlyphPosition(0);
        int glyphCode = this.gv.getGlyphCode(0);
        float x = (float)glyphPosition.getX();
        float y = (float)glyphPosition.getY();
        final Point2D.Double double1 = new Point2D.Double(this.advance.getX() - (this.gv.getGlyphPosition(numGlyphs - 1).getX() - x), this.advance.getY() - (this.gv.getGlyphPosition(numGlyphs - 1).getY() - y));
        try {
            final GVTFont font = this.gv.getFont();
            if (numGlyphs > 1 && (b3 || !b)) {
                for (int i = 1; i <= numGlyphs; ++i) {
                    final Point2D glyphPosition2 = this.gv.getGlyphPosition(i);
                    final int n4 = (i == numGlyphs) ? -1 : this.gv.getGlyphCode(i);
                    float n5 = (float)glyphPosition2.getX() - (float)glyphPosition.getX();
                    float n6 = (float)glyphPosition2.getY() - (float)glyphPosition.getY();
                    if (b) {
                        if (this.vertical) {
                            n6 += floatValue2;
                        }
                        else {
                            n5 += floatValue2;
                        }
                    }
                    else if (this.vertical) {
                        float vKern = 0.0f;
                        if (n4 != -1) {
                            vKern = font.getVKern(glyphCode, n4);
                        }
                        n6 += floatValue - vKern + floatValue2;
                    }
                    else {
                        float hKern = 0.0f;
                        if (n4 != -1) {
                            hKern = font.getHKern(glyphCode, n4);
                        }
                        n5 += floatValue - hKern + floatValue2;
                    }
                    x += n5;
                    y += n6;
                    array[i] = new Point2D.Float(x, y);
                    glyphPosition = glyphPosition2;
                    glyphCode = n4;
                }
                for (int j = 1; j <= numGlyphs; ++j) {
                    if (array[j] != null) {
                        this.gv.setGlyphPosition(j, array[j]);
                    }
                }
            }
            if (this.vertical) {
                double1.setLocation(double1.getX(), double1.getY() + floatValue + floatValue2);
            }
            else {
                double1.setLocation(double1.getX() + floatValue + floatValue2, double1.getY());
            }
            Point2D glyphPosition3 = this.gv.getGlyphPosition(0);
            float n7 = (float)glyphPosition3.getX();
            float n8 = (float)glyphPosition3.getY();
            if (numGlyphs > 1 && b2) {
                for (int k = 1; k < numGlyphs; ++k) {
                    Point2D point2D = this.gv.getGlyphPosition(k);
                    final float n9 = (float)point2D.getX() - (float)glyphPosition3.getX();
                    final float n10 = (float)point2D.getY() - (float)glyphPosition3.getY();
                    int n11 = 0;
                    final int n12 = k;
                    int n13 = k;
                    for (GVTGlyphMetrics gvtGlyphMetrics = this.gv.getGlyphMetrics(k); gvtGlyphMetrics.getBounds2D().getWidth() < 0.01 || gvtGlyphMetrics.isWhitespace(); gvtGlyphMetrics = this.gv.getGlyphMetrics(k)) {
                        if (n11 == 0) {
                            n11 = 1;
                        }
                        if (k == numGlyphs - 1) {
                            break;
                        }
                        ++k;
                        ++n13;
                        point2D = this.gv.getGlyphPosition(k);
                    }
                    if (n11 != 0) {
                        final int n14 = n13 - n12;
                        final float n15 = (float)glyphPosition3.getX();
                        final float n16 = (float)glyphPosition3.getY();
                        float n17 = (float)(point2D.getX() - n15) / (n14 + 1);
                        float n18 = (float)(point2D.getY() - n16) / (n14 + 1);
                        if (this.vertical) {
                            n18 += n3 / (n14 + 1);
                        }
                        else {
                            n17 += n3 / (n14 + 1);
                        }
                        for (int l = n12; l <= n13; ++l) {
                            n7 += n17;
                            n8 += n18;
                            array[l] = new Point2D.Float(n7, n8);
                        }
                    }
                    else {
                        final float n19 = (float)(point2D.getX() - glyphPosition3.getX());
                        final float n20 = (float)(point2D.getY() - glyphPosition3.getY());
                        n7 += n19;
                        n8 += n20;
                        array[k] = new Point2D.Float(n7, n8);
                    }
                    glyphPosition3 = point2D;
                }
                final Point2D glyphPosition4 = this.gv.getGlyphPosition(numGlyphs);
                array[numGlyphs] = new Point2D.Float(n7 + (float)(glyphPosition4.getX() - glyphPosition3.getX()), n8 + (float)(glyphPosition4.getY() - glyphPosition3.getY()));
                for (int n21 = 1; n21 <= numGlyphs; ++n21) {
                    if (array[n21] != null) {
                        this.gv.setGlyphPosition(n21, array[n21]);
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return new Point2D.Double(this.gv.getGlyphPosition(numGlyphs - 1).getX() - this.gv.getGlyphPosition(0).getX() + double1.getX(), this.gv.getGlyphPosition(numGlyphs - 1).getY() - this.gv.getGlyphPosition(0).getY() + double1.getY());
    }
    
    protected void applyStretchTransform(final boolean b) {
        if (this.xScale == 1.0f && this.yScale == 1.0f) {
            return;
        }
        final AffineTransform scaleInstance = AffineTransform.getScaleInstance(this.xScale, this.yScale);
        final int numGlyphs = this.gv.getNumGlyphs();
        final float[] glyphPositions = this.gv.getGlyphPositions(0, numGlyphs + 1, null);
        final float n = glyphPositions[0];
        final float n2 = glyphPositions[1];
        final Point2D.Float float1 = new Point2D.Float();
        for (int i = 0; i <= numGlyphs; ++i) {
            final float n3 = glyphPositions[2 * i] - n;
            final float n4 = glyphPositions[2 * i + 1] - n2;
            float1.x = n + n3 * this.xScale;
            float1.y = n2 + n4 * this.yScale;
            this.gv.setGlyphPosition(i, float1);
            if (b && i != numGlyphs) {
                final AffineTransform glyphTransform = this.gv.getGlyphTransform(i);
                if (glyphTransform != null) {
                    glyphTransform.preConcatenate(scaleInstance);
                    this.gv.setGlyphTransform(i, glyphTransform);
                }
                else {
                    this.gv.setGlyphTransform(i, scaleInstance);
                }
            }
        }
        this.advance = new Point2D.Float((float)(this.advance.getX() * this.xScale), (float)(this.advance.getY() * this.yScale));
        this.layoutApplied = false;
    }
    
    protected void doPathLayout() {
        if (this.pathApplied) {
            return;
        }
        if (!this.spacingApplied) {
            this.adjustTextSpacing();
        }
        this.getGlyphAdvances();
        if (this.textPath == null) {
            this.pathApplied = true;
            return;
        }
        final boolean b = !this.isVertical();
        if (!this.isGlyphOrientationAuto()) {
            this.getGlyphOrientationAngle();
        }
        final float lengthOfPath = this.textPath.lengthOfPath();
        final float startOffset = this.textPath.getStartOffset();
        final int numGlyphs = this.gv.getNumGlyphs();
        for (int i = 0; i < numGlyphs; ++i) {
            this.gv.setGlyphVisible(i, true);
        }
        float n;
        if (b) {
            n = (float)this.gv.getLogicalBounds().getWidth();
        }
        else {
            n = (float)this.gv.getLogicalBounds().getHeight();
        }
        if (lengthOfPath == 0.0f || n == 0.0f) {
            this.pathApplied = true;
            this.textPathAdvance = this.advance;
            return;
        }
        final Point2D glyphPosition = this.gv.getGlyphPosition(0);
        float n2;
        float n3;
        if (b) {
            n2 = (float)glyphPosition.getY();
            n3 = (float)(glyphPosition.getX() + startOffset);
        }
        else {
            n2 = (float)glyphPosition.getX();
            n3 = (float)(glyphPosition.getY() + startOffset);
        }
        this.aci.first();
        final int beginIndex = this.aci.getBeginIndex();
        int n4 = 0;
        int n5 = -1;
        float n6 = 0.0f;
        for (int j = 0; j < numGlyphs; ++j) {
            final Point2D glyphPosition2 = this.gv.getGlyphPosition(j);
            final Point2D glyphPosition3 = this.gv.getGlyphPosition(j + 1);
            float n7;
            float n8;
            if (b) {
                n7 = (float)(glyphPosition3.getX() - glyphPosition2.getX());
                n8 = (float)(glyphPosition3.getY() - glyphPosition2.getY());
            }
            else {
                n7 = (float)(glyphPosition3.getY() - glyphPosition2.getY());
                n8 = (float)(glyphPosition3.getX() - glyphPosition2.getX());
            }
            final Rectangle2D bounds2D = this.gv.getGlyphOutline(j).getBounds2D();
            final float n9 = (float)bounds2D.getWidth();
            final float n10 = (float)bounds2D.getHeight();
            float n11 = 0.0f;
            if (n9 > 0.0f) {
                n11 = (float)(bounds2D.getX() + n9 / 2.0f) - (float)glyphPosition2.getX();
            }
            float n12 = 0.0f;
            if (n10 > 0.0f) {
                n12 = (float)(bounds2D.getY() + n10 / 2.0f) - (float)glyphPosition2.getY();
            }
            float n13;
            if (b) {
                n13 = n3 + n11;
            }
            else {
                n13 = n3 + n12;
            }
            final Point2D pointAtLength = this.textPath.pointAtLength(n13);
            if (pointAtLength != null) {
                final float angleAtLength = this.textPath.angleAtLength(n13);
                final AffineTransform affineTransform = new AffineTransform();
                if (b) {
                    affineTransform.rotate(angleAtLength);
                }
                else {
                    affineTransform.rotate(angleAtLength - 1.5707963267948966);
                }
                if (b) {
                    affineTransform.translate(0.0, n2);
                }
                else {
                    affineTransform.translate(n2, 0.0);
                }
                if (b) {
                    affineTransform.translate(-n11, 0.0);
                }
                else {
                    affineTransform.translate(0.0, -n12);
                }
                final AffineTransform glyphTransform = this.gv.getGlyphTransform(j);
                if (glyphTransform != null) {
                    affineTransform.concatenate(glyphTransform);
                }
                this.gv.setGlyphTransform(j, affineTransform);
                this.gv.setGlyphPosition(j, pointAtLength);
                n5 = j;
                n6 = n7;
            }
            else {
                this.gv.setGlyphVisible(j, false);
            }
            n3 += n7;
            n2 += n8;
            n4 += this.gv.getCharacterCount(j, j);
            if (n4 >= this.charMap.length) {
                n4 = this.charMap.length - 1;
            }
            this.aci.setIndex(n4 + beginIndex);
        }
        if (n5 > -1) {
            final Point2D glyphPosition4 = this.gv.getGlyphPosition(n5);
            if (b) {
                this.textPathAdvance = new Point2D.Double(glyphPosition4.getX() + n6, glyphPosition4.getY());
            }
            else {
                this.textPathAdvance = new Point2D.Double(glyphPosition4.getX(), glyphPosition4.getY() + n6);
            }
        }
        else {
            this.textPathAdvance = new Point2D.Double(0.0, 0.0);
        }
        this.layoutApplied = false;
        this.spacingApplied = false;
        this.pathApplied = true;
    }
    
    protected boolean isLatinChar(final char c) {
        if (c < '\u00ff' && Character.isLetterOrDigit(c)) {
            return true;
        }
        final Character.UnicodeBlock of = Character.UnicodeBlock.of(c);
        return of == Character.UnicodeBlock.BASIC_LATIN || of == Character.UnicodeBlock.LATIN_1_SUPPLEMENT || of == Character.UnicodeBlock.LATIN_EXTENDED_ADDITIONAL || of == Character.UnicodeBlock.LATIN_EXTENDED_A || of == Character.UnicodeBlock.LATIN_EXTENDED_B || of == Character.UnicodeBlock.ARABIC || of == Character.UnicodeBlock.ARABIC_PRESENTATION_FORMS_A || of == Character.UnicodeBlock.ARABIC_PRESENTATION_FORMS_B;
    }
    
    protected boolean isGlyphOrientationAuto() {
        if (!this.isVertical()) {
            return false;
        }
        this.aci.first();
        final Integer n = (Integer)this.aci.getAttribute(GlyphLayout.VERTICAL_ORIENTATION);
        return n == null || n == GlyphLayout.ORIENTATION_AUTO;
    }
    
    protected int getGlyphOrientationAngle() {
        int i = 0;
        this.aci.first();
        Float n;
        if (this.isVertical()) {
            n = (Float)this.aci.getAttribute(GlyphLayout.VERTICAL_ORIENTATION_ANGLE);
        }
        else {
            n = (Float)this.aci.getAttribute(GlyphLayout.HORIZONTAL_ORIENTATION_ANGLE);
        }
        if (n != null) {
            i = (int)(float)n;
        }
        if (i != 0 || i != 90 || i != 180 || i != 270) {
            while (i < 0) {
                i += 360;
            }
            while (i >= 360) {
                i -= 360;
            }
            if (i <= 45 || i > 315) {
                i = 0;
            }
            else if (i > 45 && i <= 135) {
                i = 90;
            }
            else if (i > 135 && i <= 225) {
                i = 180;
            }
            else {
                i = 270;
            }
        }
        return i;
    }
    
    public boolean hasCharacterIndex(final int n) {
        for (int i = 0; i < this.charMap.length; ++i) {
            if (n == this.charMap[i]) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isAltGlyph() {
        return this.isAltGlyph;
    }
    
    static {
        FLOW_LINE_BREAK = GVTAttributedCharacterIterator.TextAttribute.FLOW_LINE_BREAK;
        FLOW_PARAGRAPH = GVTAttributedCharacterIterator.TextAttribute.FLOW_PARAGRAPH;
        FLOW_EMPTY_PARAGRAPH = GVTAttributedCharacterIterator.TextAttribute.FLOW_EMPTY_PARAGRAPH;
        LINE_HEIGHT = GVTAttributedCharacterIterator.TextAttribute.LINE_HEIGHT;
        VERTICAL_ORIENTATION = GVTAttributedCharacterIterator.TextAttribute.VERTICAL_ORIENTATION;
        VERTICAL_ORIENTATION_ANGLE = GVTAttributedCharacterIterator.TextAttribute.VERTICAL_ORIENTATION_ANGLE;
        HORIZONTAL_ORIENTATION_ANGLE = GVTAttributedCharacterIterator.TextAttribute.HORIZONTAL_ORIENTATION_ANGLE;
        X = GVTAttributedCharacterIterator.TextAttribute.X;
        Y = GVTAttributedCharacterIterator.TextAttribute.Y;
        DX = GVTAttributedCharacterIterator.TextAttribute.DX;
        DY = GVTAttributedCharacterIterator.TextAttribute.DY;
        ROTATION = GVTAttributedCharacterIterator.TextAttribute.ROTATION;
        BASELINE_SHIFT = GVTAttributedCharacterIterator.TextAttribute.BASELINE_SHIFT;
        WRITING_MODE = GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE;
        WRITING_MODE_TTB = GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE_TTB;
        ORIENTATION_AUTO = GVTAttributedCharacterIterator.TextAttribute.ORIENTATION_AUTO;
        GVT_FONT = GVTAttributedCharacterIterator.TextAttribute.GVT_FONT;
        (GlyphLayout.runAtts = new HashSet()).add(GlyphLayout.X);
        GlyphLayout.runAtts.add(GlyphLayout.Y);
        GlyphLayout.runAtts.add(GlyphLayout.DX);
        GlyphLayout.runAtts.add(GlyphLayout.DY);
        GlyphLayout.runAtts.add(GlyphLayout.ROTATION);
        GlyphLayout.runAtts.add(GlyphLayout.BASELINE_SHIFT);
        (GlyphLayout.szAtts = new HashSet()).add(TextAttribute.SIZE);
        GlyphLayout.szAtts.add(GlyphLayout.GVT_FONT);
        GlyphLayout.szAtts.add(GlyphLayout.LINE_HEIGHT);
    }
}
