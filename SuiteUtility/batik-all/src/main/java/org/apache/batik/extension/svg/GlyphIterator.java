// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.font.GVTLineMetrics;
import java.text.CharacterIterator;
import java.util.Map;
import org.apache.batik.gvt.font.AWTGVTFont;
import java.awt.geom.Point2D;
import java.awt.font.FontRenderContext;
import org.apache.batik.gvt.font.GVTGlyphVector;
import org.apache.batik.gvt.font.GVTFont;
import java.text.AttributedCharacterIterator;

public class GlyphIterator
{
    public static final AttributedCharacterIterator.Attribute PREFORMATTED;
    public static final AttributedCharacterIterator.Attribute FLOW_LINE_BREAK;
    public static final AttributedCharacterIterator.Attribute TEXT_COMPOUND_ID;
    public static final AttributedCharacterIterator.Attribute GVT_FONT;
    public static final char SOFT_HYPHEN = '\u00ad';
    public static final char ZERO_WIDTH_SPACE = '\u200b';
    public static final char ZERO_WIDTH_JOINER = '\u200d';
    int idx;
    int chIdx;
    int lineIdx;
    int aciIdx;
    int charCount;
    float adv;
    float adj;
    int runLimit;
    int lineBreakRunLimit;
    int lineBreakCount;
    GVTFont font;
    int fontStart;
    float maxAscent;
    float maxDescent;
    float maxFontSize;
    float width;
    char ch;
    int numGlyphs;
    AttributedCharacterIterator aci;
    GVTGlyphVector gv;
    float[] gp;
    FontRenderContext frc;
    int[] leftShiftIdx;
    float[] leftShiftAmt;
    int leftShift;
    Point2D gvBase;
    
    public GlyphIterator(final AttributedCharacterIterator aci, final GVTGlyphVector gv) {
        this.idx = -1;
        this.chIdx = -1;
        this.lineIdx = -1;
        this.aciIdx = -1;
        this.charCount = -1;
        this.adv = 0.0f;
        this.adj = 0.0f;
        this.runLimit = 0;
        this.lineBreakRunLimit = 0;
        this.lineBreakCount = 0;
        this.font = null;
        this.fontStart = 0;
        this.maxAscent = 0.0f;
        this.maxDescent = 0.0f;
        this.maxFontSize = 0.0f;
        this.width = 0.0f;
        this.ch = '\0';
        this.numGlyphs = 0;
        this.leftShiftIdx = null;
        this.leftShiftAmt = null;
        this.leftShift = 0;
        this.gvBase = null;
        this.aci = aci;
        this.gv = gv;
        this.idx = 0;
        this.chIdx = 0;
        this.lineIdx = 0;
        this.aciIdx = aci.getBeginIndex();
        this.charCount = gv.getCharacterCount(this.idx, this.idx);
        this.ch = aci.first();
        this.frc = gv.getFontRenderContext();
        this.font = (GVTFont)aci.getAttribute(GlyphIterator.GVT_FONT);
        if (this.font == null) {
            this.font = new AWTGVTFont(aci.getAttributes());
        }
        this.fontStart = this.aciIdx;
        this.maxFontSize = -3.4028235E38f;
        this.maxAscent = -3.4028235E38f;
        this.maxDescent = -3.4028235E38f;
        this.runLimit = aci.getRunLimit(GlyphIterator.TEXT_COMPOUND_ID);
        this.lineBreakRunLimit = aci.getRunLimit(GlyphIterator.FLOW_LINE_BREAK);
        this.lineBreakCount = ((aci.getAttribute(GlyphIterator.FLOW_LINE_BREAK) != null) ? 1 : 0);
        this.numGlyphs = gv.getNumGlyphs();
        this.gp = gv.getGlyphPositions(0, this.numGlyphs + 1, null);
        this.gvBase = new Point2D.Float(this.gp[0], this.gp[1]);
        this.adv = this.getCharWidth();
        this.adj = this.getCharAdvance();
    }
    
    public GlyphIterator(final GlyphIterator glyphIterator) {
        this.idx = -1;
        this.chIdx = -1;
        this.lineIdx = -1;
        this.aciIdx = -1;
        this.charCount = -1;
        this.adv = 0.0f;
        this.adj = 0.0f;
        this.runLimit = 0;
        this.lineBreakRunLimit = 0;
        this.lineBreakCount = 0;
        this.font = null;
        this.fontStart = 0;
        this.maxAscent = 0.0f;
        this.maxDescent = 0.0f;
        this.maxFontSize = 0.0f;
        this.width = 0.0f;
        this.ch = '\0';
        this.numGlyphs = 0;
        this.leftShiftIdx = null;
        this.leftShiftAmt = null;
        this.leftShift = 0;
        this.gvBase = null;
        glyphIterator.copy(this);
    }
    
    public GlyphIterator copy() {
        return new GlyphIterator(this);
    }
    
    public GlyphIterator copy(final GlyphIterator glyphIterator) {
        if (glyphIterator == null) {
            return new GlyphIterator(this);
        }
        glyphIterator.idx = this.idx;
        glyphIterator.chIdx = this.chIdx;
        glyphIterator.aciIdx = this.aciIdx;
        glyphIterator.charCount = this.charCount;
        glyphIterator.adv = this.adv;
        glyphIterator.adj = this.adj;
        glyphIterator.runLimit = this.runLimit;
        glyphIterator.ch = this.ch;
        glyphIterator.numGlyphs = this.numGlyphs;
        glyphIterator.gp = this.gp;
        glyphIterator.gvBase = this.gvBase;
        glyphIterator.lineBreakRunLimit = this.lineBreakRunLimit;
        glyphIterator.lineBreakCount = this.lineBreakCount;
        glyphIterator.frc = this.frc;
        glyphIterator.font = this.font;
        glyphIterator.fontStart = this.fontStart;
        glyphIterator.maxAscent = this.maxAscent;
        glyphIterator.maxDescent = this.maxDescent;
        glyphIterator.maxFontSize = this.maxFontSize;
        glyphIterator.leftShift = this.leftShift;
        glyphIterator.leftShiftIdx = this.leftShiftIdx;
        glyphIterator.leftShiftAmt = this.leftShiftAmt;
        return glyphIterator;
    }
    
    public int getGlyphIndex() {
        return this.idx;
    }
    
    public char getChar() {
        return this.ch;
    }
    
    public int getACIIndex() {
        return this.aciIdx;
    }
    
    public float getAdv() {
        return this.adv;
    }
    
    public Point2D getOrigin() {
        return this.gvBase;
    }
    
    public float getAdj() {
        return this.adj;
    }
    
    public float getMaxFontSize() {
        if (this.aciIdx >= this.fontStart) {
            final int fontStart = this.aciIdx + this.charCount;
            this.updateLineMetrics(fontStart);
            this.fontStart = fontStart;
        }
        return this.maxFontSize;
    }
    
    public float getMaxAscent() {
        if (this.aciIdx >= this.fontStart) {
            final int fontStart = this.aciIdx + this.charCount;
            this.updateLineMetrics(fontStart);
            this.fontStart = fontStart;
        }
        return this.maxAscent;
    }
    
    public float getMaxDescent() {
        if (this.aciIdx >= this.fontStart) {
            final int fontStart = this.aciIdx + this.charCount;
            this.updateLineMetrics(fontStart);
            this.fontStart = fontStart;
        }
        return this.maxDescent;
    }
    
    public boolean isLastChar() {
        return this.idx == this.numGlyphs - 1;
    }
    
    public boolean done() {
        return this.idx >= this.numGlyphs;
    }
    
    public boolean isBreakChar() {
        switch (this.ch) {
            case '\u200b': {
                return true;
            }
            case '\u200d': {
                return false;
            }
            case '\u00ad': {
                return true;
            }
            case '\t':
            case ' ': {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    protected boolean isPrinting(final char c) {
        switch (this.ch) {
            case '\u200b': {
                return false;
            }
            case '\u200d': {
                return false;
            }
            case '\u00ad': {
                return true;
            }
            case '\t':
            case ' ': {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public int getLineBreaks() {
        int lineBreakCount = 0;
        if (this.aciIdx + this.charCount >= this.lineBreakRunLimit) {
            lineBreakCount = this.lineBreakCount;
            this.aci.setIndex(this.aciIdx + this.charCount);
            this.lineBreakRunLimit = this.aci.getRunLimit(GlyphIterator.FLOW_LINE_BREAK);
            this.aci.setIndex(this.aciIdx);
            this.lineBreakCount = ((this.aci.getAttribute(GlyphIterator.FLOW_LINE_BREAK) != null) ? 1 : 0);
        }
        return lineBreakCount;
    }
    
    public void nextChar() {
        if (this.ch == '\u00ad' || this.ch == '\u200b' || this.ch == '\u200d') {
            this.gv.setGlyphVisible(this.idx, false);
            final float charAdvance = this.getCharAdvance();
            this.adj -= charAdvance;
            this.addLeftShift(this.idx, charAdvance);
        }
        this.aciIdx += this.charCount;
        this.ch = this.aci.setIndex(this.aciIdx);
        ++this.idx;
        this.charCount = this.gv.getCharacterCount(this.idx, this.idx);
        if (this.idx == this.numGlyphs) {
            return;
        }
        if (this.aciIdx >= this.runLimit) {
            this.updateLineMetrics(this.aciIdx);
            this.runLimit = this.aci.getRunLimit(GlyphIterator.TEXT_COMPOUND_ID);
            this.font = (GVTFont)this.aci.getAttribute(GlyphIterator.GVT_FONT);
            if (this.font == null) {
                this.font = new AWTGVTFont(this.aci.getAttributes());
            }
            this.fontStart = this.aciIdx;
        }
        final float charAdvance2 = this.getCharAdvance();
        this.adj += charAdvance2;
        if (this.isPrinting()) {
            this.chIdx = this.idx;
            this.adv = this.adj - (charAdvance2 - this.getCharWidth());
        }
    }
    
    protected void addLeftShift(final int n, final float n2) {
        if (this.leftShiftIdx == null) {
            (this.leftShiftIdx = new int[1])[0] = n;
            (this.leftShiftAmt = new float[1])[0] = n2;
        }
        else {
            final int[] leftShiftIdx = new int[this.leftShiftIdx.length + 1];
            System.arraycopy(this.leftShiftIdx, 0, leftShiftIdx, 0, this.leftShiftIdx.length);
            leftShiftIdx[this.leftShiftIdx.length] = n;
            this.leftShiftIdx = leftShiftIdx;
            final float[] leftShiftAmt = new float[this.leftShiftAmt.length + 1];
            System.arraycopy(this.leftShiftAmt, 0, leftShiftAmt, 0, this.leftShiftAmt.length);
            leftShiftAmt[this.leftShiftAmt.length] = n2;
            this.leftShiftAmt = leftShiftAmt;
        }
    }
    
    protected void updateLineMetrics(final int n) {
        final GVTLineMetrics lineMetrics = this.font.getLineMetrics(this.aci, this.fontStart, n, this.frc);
        final float ascent = lineMetrics.getAscent();
        final float descent = lineMetrics.getDescent();
        final float size = this.font.getSize();
        if (ascent > this.maxAscent) {
            this.maxAscent = ascent;
        }
        if (descent > this.maxDescent) {
            this.maxDescent = descent;
        }
        if (size > this.maxFontSize) {
            this.maxFontSize = size;
        }
    }
    
    public LineInfo newLine(final Point2D.Float float1, final float n, final boolean b, final Point2D.Float float2) {
        if (this.ch == '\u00ad') {
            this.gv.setGlyphVisible(this.idx, true);
        }
        int n2 = 0;
        int n3;
        if (this.leftShiftIdx != null) {
            n3 = this.leftShiftIdx[n2];
        }
        else {
            n3 = this.idx + 1;
        }
        for (int i = this.lineIdx; i <= this.idx; ++i) {
            if (i == n3) {
                this.leftShift += (int)this.leftShiftAmt[n2++];
                if (n2 < this.leftShiftIdx.length) {
                    n3 = this.leftShiftIdx[n2];
                }
            }
            this.gv.setGlyphPosition(i, new Point2D.Float(this.gp[2 * i] - this.leftShift, this.gp[2 * i + 1]));
        }
        this.leftShiftIdx = null;
        this.leftShiftAmt = null;
        float charWidth;
        int n4;
        if (this.chIdx != 0 || this.isPrinting()) {
            charWidth = this.getCharWidth(this.chIdx);
            n4 = this.chIdx + 1;
        }
        else {
            charWidth = 0.0f;
            n4 = 0;
        }
        int n5 = this.idx + 1;
        final float adv = this.adv;
        float adj = this.adj;
        while (!this.done()) {
            this.adv = 0.0f;
            this.adj = 0.0f;
            if (this.ch == '\u200b' || this.ch == '\u200d') {
                this.gv.setGlyphVisible(this.idx, false);
            }
            this.ch = '\0';
            this.nextChar();
            if (this.isPrinting()) {
                break;
            }
            n5 = this.idx + 1;
            adj += this.adj;
        }
        for (int j = n4; j < n5; ++j) {
            this.gv.setGlyphVisible(j, false);
        }
        this.maxAscent = -3.4028235E38f;
        this.maxDescent = -3.4028235E38f;
        this.maxFontSize = -3.4028235E38f;
        final LineInfo lineInfo = new LineInfo(float1, this.aci, this.gv, this.lineIdx, n5, adj, adv, charWidth, n, b, float2);
        this.lineIdx = this.idx;
        return lineInfo;
    }
    
    public boolean isPrinting() {
        return this.aci.getAttribute(GlyphIterator.PREFORMATTED) == Boolean.TRUE || this.isPrinting(this.ch);
    }
    
    public float getCharAdvance() {
        return this.getCharAdvance(this.idx);
    }
    
    public float getCharWidth() {
        return this.getCharWidth(this.idx);
    }
    
    protected float getCharAdvance(final int n) {
        return this.gp[2 * n + 2] - this.gp[2 * n];
    }
    
    protected float getCharWidth(final int n) {
        final Rectangle2D bounds2D = this.gv.getGlyphVisualBounds(n).getBounds2D();
        return (float)(bounds2D.getX() + bounds2D.getWidth() - this.gv.getGlyphPosition(n).getX());
    }
    
    static {
        PREFORMATTED = GVTAttributedCharacterIterator.TextAttribute.PREFORMATTED;
        FLOW_LINE_BREAK = GVTAttributedCharacterIterator.TextAttribute.FLOW_LINE_BREAK;
        TEXT_COMPOUND_ID = GVTAttributedCharacterIterator.TextAttribute.TEXT_COMPOUND_ID;
        GVT_FONT = GVTAttributedCharacterIterator.TextAttribute.GVT_FONT;
    }
}
