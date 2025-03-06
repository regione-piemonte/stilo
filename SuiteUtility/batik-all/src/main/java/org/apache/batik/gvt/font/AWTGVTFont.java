// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import java.util.HashMap;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.font.GlyphVector;
import org.apache.batik.gvt.text.ArabicTextHandler;
import java.text.StringCharacterIterator;
import java.awt.font.FontRenderContext;
import java.text.CharacterIterator;
import java.text.AttributedCharacterIterator;
import java.awt.font.TextAttribute;
import java.util.Map;
import java.awt.Font;

public class AWTGVTFont implements GVTFont
{
    protected Font awtFont;
    protected float size;
    protected float scale;
    public static final float FONT_SIZE = 48.0f;
    static Map fontCache;
    
    public AWTGVTFont(final Font font) {
        this.size = font.getSize2D();
        this.awtFont = font.deriveFont(48.0f);
        this.scale = this.size / this.awtFont.getSize2D();
        initializeFontCache(this.awtFont);
    }
    
    public AWTGVTFont(final Font font, final float n) {
        this.size = font.getSize2D() * n;
        this.awtFont = font.deriveFont(48.0f);
        this.scale = this.size / this.awtFont.getSize2D();
        initializeFontCache(this.awtFont);
    }
    
    public AWTGVTFont(final Map map) {
        final Float n = map.get(TextAttribute.SIZE);
        if (n != null) {
            this.size = n;
            map.put(TextAttribute.SIZE, new Float(48.0f));
            this.awtFont = new Font(map);
        }
        else {
            this.awtFont = new Font(map);
            this.size = this.awtFont.getSize2D();
        }
        this.scale = this.size / this.awtFont.getSize2D();
        initializeFontCache(this.awtFont);
    }
    
    public AWTGVTFont(final String name, final int style, final int n) {
        this.awtFont = new Font(name, style, 48);
        this.size = (float)n;
        this.scale = n / this.awtFont.getSize2D();
        initializeFontCache(this.awtFont);
    }
    
    public boolean canDisplay(final char c) {
        return this.awtFont.canDisplay(c);
    }
    
    public int canDisplayUpTo(final char[] text, final int start, final int limit) {
        return this.awtFont.canDisplayUpTo(text, start, limit);
    }
    
    public int canDisplayUpTo(final CharacterIterator iter, final int start, final int limit) {
        return this.awtFont.canDisplayUpTo(iter, start, limit);
    }
    
    public int canDisplayUpTo(final String str) {
        return this.awtFont.canDisplayUpTo(str);
    }
    
    public GVTGlyphVector createGlyphVector(final FontRenderContext frc, final char[] array) {
        return new AWTGVTGlyphVector(this.awtFont.createGlyphVector(frc, array), this, this.scale, new StringCharacterIterator(new String(array)));
    }
    
    public GVTGlyphVector createGlyphVector(final FontRenderContext frc, final CharacterIterator ci) {
        if (ci instanceof AttributedCharacterIterator) {
            final AttributedCharacterIterator attributedCharacterIterator = (AttributedCharacterIterator)ci;
            if (ArabicTextHandler.containsArabic(attributedCharacterIterator)) {
                return this.createGlyphVector(frc, ArabicTextHandler.createSubstituteString(attributedCharacterIterator));
            }
        }
        return new AWTGVTGlyphVector(this.awtFont.createGlyphVector(frc, ci), this, this.scale, ci);
    }
    
    public GVTGlyphVector createGlyphVector(final FontRenderContext frc, final int[] glyphCodes, final CharacterIterator characterIterator) {
        return new AWTGVTGlyphVector(this.awtFont.createGlyphVector(frc, glyphCodes), this, this.scale, characterIterator);
    }
    
    public GVTGlyphVector createGlyphVector(final FontRenderContext frc, final String s) {
        return new AWTGVTGlyphVector(this.awtFont.createGlyphVector(frc, s), this, this.scale, new StringCharacterIterator(s));
    }
    
    public GVTFont deriveFont(final float n) {
        return new AWTGVTFont(this.awtFont, n / this.size);
    }
    
    public String getFamilyName() {
        return this.awtFont.getFamily();
    }
    
    public GVTLineMetrics getLineMetrics(final char[] chars, final int beginIndex, final int limit, final FontRenderContext frc) {
        return new GVTLineMetrics(this.awtFont.getLineMetrics(chars, beginIndex, limit, frc), this.scale);
    }
    
    public GVTLineMetrics getLineMetrics(final CharacterIterator ci, final int beginIndex, final int limit, final FontRenderContext frc) {
        return new GVTLineMetrics(this.awtFont.getLineMetrics(ci, beginIndex, limit, frc), this.scale);
    }
    
    public GVTLineMetrics getLineMetrics(final String str, final FontRenderContext frc) {
        return new GVTLineMetrics(this.awtFont.getLineMetrics(str, frc), this.scale);
    }
    
    public GVTLineMetrics getLineMetrics(final String str, final int beginIndex, final int limit, final FontRenderContext frc) {
        return new GVTLineMetrics(this.awtFont.getLineMetrics(str, beginIndex, limit, frc), this.scale);
    }
    
    public float getSize() {
        return this.size;
    }
    
    public float getHKern(final int n, final int n2) {
        return 0.0f;
    }
    
    public float getVKern(final int n, final int n2) {
        return 0.0f;
    }
    
    public static AWTGlyphGeometryCache.Value getGlyphGeometry(final AWTGVTFont awtgvtFont, final char c, final GlyphVector glyphVector, final int n, final Point2D point2D) {
        final AWTGlyphGeometryCache awtGlyphGeometryCache = AWTGVTFont.fontCache.get(awtgvtFont.awtFont);
        AWTGlyphGeometryCache.Value value = awtGlyphGeometryCache.get(c);
        if (value == null) {
            Shape pSrc = glyphVector.getGlyphOutline(n);
            final Rectangle2D bounds2D = glyphVector.getGlyphMetrics(n).getBounds2D();
            if (AWTGVTGlyphVector.outlinesPositioned()) {
                pSrc = AffineTransform.getTranslateInstance(-point2D.getX(), -point2D.getY()).createTransformedShape(pSrc);
            }
            value = new AWTGlyphGeometryCache.Value(pSrc, bounds2D);
            awtGlyphGeometryCache.put(c, value);
        }
        return value;
    }
    
    static void initializeFontCache(final Font font) {
        if (!AWTGVTFont.fontCache.containsKey(font)) {
            AWTGVTFont.fontCache.put(font, new AWTGlyphGeometryCache());
        }
    }
    
    static void putAWTGVTFont(final AWTGVTFont awtgvtFont) {
        AWTGVTFont.fontCache.put(awtgvtFont.awtFont, awtgvtFont);
    }
    
    static AWTGVTFont getAWTGVTFont(final Font font) {
        return AWTGVTFont.fontCache.get(font);
    }
    
    static {
        AWTGVTFont.fontCache = new HashMap(11);
    }
}
