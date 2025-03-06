// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.gvt.font.SVGGVTGlyphVector;
import org.apache.batik.gvt.text.TextPaintInfo;
import org.apache.batik.gvt.font.Glyph;
import org.apache.batik.gvt.font.GVTGlyphVector;
import java.awt.font.FontRenderContext;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import java.util.StringTokenizer;
import java.util.ArrayList;
import org.apache.batik.gvt.font.Kern;
import org.apache.batik.dom.util.XMLSupport;
import org.apache.batik.gvt.font.GVTLineMetrics;
import org.apache.batik.gvt.font.KerningTable;
import org.w3c.dom.Element;
import org.apache.batik.gvt.font.GVTFontFace;
import java.text.AttributedCharacterIterator;
import org.apache.batik.util.SVGConstants;
import org.apache.batik.gvt.font.GVTFont;

public final class SVGGVTFont implements GVTFont, SVGConstants
{
    public static final AttributedCharacterIterator.Attribute PAINT_INFO;
    private float fontSize;
    private GVTFontFace fontFace;
    private String[] glyphUnicodes;
    private String[] glyphNames;
    private String[] glyphLangs;
    private String[] glyphOrientations;
    private String[] glyphForms;
    private Element[] glyphElements;
    private Element[] hkernElements;
    private Element[] vkernElements;
    private BridgeContext ctx;
    private Element textElement;
    private Element missingGlyphElement;
    private KerningTable hKerningTable;
    private KerningTable vKerningTable;
    private String language;
    private String orientation;
    private float scale;
    private GVTLineMetrics lineMetrics;
    
    public SVGGVTFont(final float fontSize, final GVTFontFace fontFace, final String[] glyphUnicodes, final String[] glyphNames, final String[] glyphLangs, final String[] glyphOrientations, final String[] glyphForms, final BridgeContext ctx, final Element[] glyphElements, final Element missingGlyphElement, final Element[] hkernElements, final Element[] vkernElements, final Element textElement) {
        this.lineMetrics = null;
        this.fontFace = fontFace;
        this.fontSize = fontSize;
        this.glyphUnicodes = glyphUnicodes;
        this.glyphNames = glyphNames;
        this.glyphLangs = glyphLangs;
        this.glyphOrientations = glyphOrientations;
        this.glyphForms = glyphForms;
        this.ctx = ctx;
        this.glyphElements = glyphElements;
        this.missingGlyphElement = missingGlyphElement;
        this.hkernElements = hkernElements;
        this.vkernElements = vkernElements;
        this.scale = fontSize / fontFace.getUnitsPerEm();
        this.textElement = textElement;
        this.language = XMLSupport.getXMLLang(textElement);
        if (CSSUtilities.getComputedStyle(textElement, 59).getStringValue().startsWith("tb")) {
            this.orientation = "v";
        }
        else {
            this.orientation = "h";
        }
        this.createKerningTables();
    }
    
    private void createKerningTables() {
        final Kern[] array = new Kern[this.hkernElements.length];
        for (int i = 0; i < this.hkernElements.length; ++i) {
            final Element element = this.hkernElements[i];
            array[i] = ((SVGHKernElementBridge)this.ctx.getBridge(element)).createKern(this.ctx, element, this);
        }
        this.hKerningTable = new KerningTable(array);
        final Kern[] array2 = new Kern[this.vkernElements.length];
        for (int j = 0; j < this.vkernElements.length; ++j) {
            final Element element2 = this.vkernElements[j];
            array2[j] = ((SVGVKernElementBridge)this.ctx.getBridge(element2)).createKern(this.ctx, element2, this);
        }
        this.vKerningTable = new KerningTable(array2);
    }
    
    public float getHKern(final int n, final int n2) {
        if (n < 0 || n >= this.glyphUnicodes.length || n2 < 0 || n2 >= this.glyphUnicodes.length) {
            return 0.0f;
        }
        return this.hKerningTable.getKerningValue(n, n2, this.glyphUnicodes[n], this.glyphUnicodes[n2]) * this.scale;
    }
    
    public float getVKern(final int n, final int n2) {
        if (n < 0 || n >= this.glyphUnicodes.length || n2 < 0 || n2 >= this.glyphUnicodes.length) {
            return 0.0f;
        }
        return this.vKerningTable.getKerningValue(n, n2, this.glyphUnicodes[n], this.glyphUnicodes[n2]) * this.scale;
    }
    
    public int[] getGlyphCodesForName(final String anObject) {
        final ArrayList<Object> list = new ArrayList<Object>();
        for (int i = 0; i < this.glyphNames.length; ++i) {
            if (this.glyphNames[i] != null && this.glyphNames[i].equals(anObject)) {
                list.add(new Integer(i));
            }
        }
        final int[] array = new int[list.size()];
        for (int j = 0; j < list.size(); ++j) {
            array[j] = list.get(j);
        }
        return array;
    }
    
    public int[] getGlyphCodesForUnicode(final String anObject) {
        final ArrayList<Object> list = new ArrayList<Object>();
        for (int i = 0; i < this.glyphUnicodes.length; ++i) {
            if (this.glyphUnicodes[i] != null && this.glyphUnicodes[i].equals(anObject)) {
                list.add(new Integer(i));
            }
        }
        final int[] array = new int[list.size()];
        for (int j = 0; j < list.size(); ++j) {
            array[j] = list.get(j);
        }
        return array;
    }
    
    private boolean languageMatches(final String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        final StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (nextToken.equals(this.language) || (nextToken.startsWith(this.language) && nextToken.length() > this.language.length() && nextToken.charAt(this.language.length()) == '-')) {
                return true;
            }
        }
        return false;
    }
    
    private boolean orientationMatches(final String s) {
        return s == null || s.length() == 0 || s.equals(this.orientation);
    }
    
    private boolean formMatches(final String s, final String s2, final AttributedCharacterIterator attributedCharacterIterator, final int index) {
        if (attributedCharacterIterator == null || s2 == null || s2.length() == 0) {
            return true;
        }
        attributedCharacterIterator.setIndex(index);
        final Integer n = (Integer)attributedCharacterIterator.getAttribute(GVTAttributedCharacterIterator.TextAttribute.ARABIC_FORM);
        if (n == null || n.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_NONE)) {
            return false;
        }
        if (s.length() > 1) {
            boolean b = true;
            for (int i = 1; i < s.length(); ++i) {
                if (s.charAt(i) != attributedCharacterIterator.next()) {
                    b = false;
                    break;
                }
            }
            attributedCharacterIterator.setIndex(index);
            if (b) {
                attributedCharacterIterator.setIndex(index + s.length() - 1);
                final Integer n2 = (Integer)attributedCharacterIterator.getAttribute(GVTAttributedCharacterIterator.TextAttribute.ARABIC_FORM);
                attributedCharacterIterator.setIndex(index);
                if (n != null && n2 != null) {
                    if (n.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_TERMINAL) && n2.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_INITIAL)) {
                        return s2.equals("isolated");
                    }
                    if (n.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_TERMINAL)) {
                        return s2.equals("terminal");
                    }
                    if (n.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_MEDIAL) && n2.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_MEDIAL)) {
                        return s2.equals("medial");
                    }
                }
            }
        }
        if (n.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_ISOLATED)) {
            return s2.equals("isolated");
        }
        if (n.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_TERMINAL)) {
            return s2.equals("terminal");
        }
        if (n.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_INITIAL)) {
            return s2.equals("initial");
        }
        return n.equals(GVTAttributedCharacterIterator.TextAttribute.ARABIC_MEDIAL) && s2.equals("medial");
    }
    
    public boolean canDisplayGivenName(final String anObject) {
        for (int i = 0; i < this.glyphNames.length; ++i) {
            if (this.glyphNames[i] != null && this.glyphNames[i].equals(anObject) && this.languageMatches(this.glyphLangs[i]) && this.orientationMatches(this.glyphOrientations[i])) {
                return true;
            }
        }
        return false;
    }
    
    public boolean canDisplay(final char ch) {
        for (int i = 0; i < this.glyphUnicodes.length; ++i) {
            if (this.glyphUnicodes[i].indexOf(ch) != -1 && this.languageMatches(this.glyphLangs[i]) && this.orientationMatches(this.glyphOrientations[i])) {
                return true;
            }
        }
        return false;
    }
    
    public int canDisplayUpTo(final char[] value, final int n, final int n2) {
        return this.canDisplayUpTo(new StringCharacterIterator(new String(value)), n, n2);
    }
    
    public int canDisplayUpTo(final CharacterIterator characterIterator, final int index, final int n) {
        AttributedCharacterIterator attributedCharacterIterator = null;
        if (characterIterator instanceof AttributedCharacterIterator) {
            attributedCharacterIterator = (AttributedCharacterIterator)characterIterator;
        }
        char ch = characterIterator.setIndex(index);
        for (int index2 = index; ch != '\uffff' && index2 < n; ch = characterIterator.next(), index2 = characterIterator.getIndex()) {
            boolean b = false;
            for (int i = 0; i < this.glyphUnicodes.length; ++i) {
                if (this.glyphUnicodes[i].indexOf(ch) == 0 && this.languageMatches(this.glyphLangs[i]) && this.orientationMatches(this.glyphOrientations[i]) && this.formMatches(this.glyphUnicodes[i], this.glyphForms[i], attributedCharacterIterator, index2)) {
                    if (this.glyphUnicodes[i].length() == 1) {
                        b = true;
                        break;
                    }
                    boolean b2 = true;
                    for (int j = 1; j < this.glyphUnicodes[i].length(); ++j) {
                        if (this.glyphUnicodes[i].charAt(j) != characterIterator.next()) {
                            b2 = false;
                            break;
                        }
                    }
                    if (b2) {
                        b = true;
                        break;
                    }
                    ch = characterIterator.setIndex(index2);
                }
            }
            if (!b) {
                return index2;
            }
        }
        return -1;
    }
    
    public int canDisplayUpTo(final String text) {
        return this.canDisplayUpTo(new StringCharacterIterator(text), 0, text.length());
    }
    
    public GVTGlyphVector createGlyphVector(final FontRenderContext fontRenderContext, final char[] value) {
        return this.createGlyphVector(fontRenderContext, new StringCharacterIterator(new String(value)));
    }
    
    public GVTGlyphVector createGlyphVector(final FontRenderContext fontRenderContext, final CharacterIterator characterIterator) {
        AttributedCharacterIterator attributedCharacterIterator = null;
        if (characterIterator instanceof AttributedCharacterIterator) {
            attributedCharacterIterator = (AttributedCharacterIterator)characterIterator;
        }
        final ArrayList list = new ArrayList<Glyph>();
        for (char ch = characterIterator.first(); ch != '\uffff'; ch = characterIterator.next()) {
            boolean b = false;
            for (int i = 0; i < this.glyphUnicodes.length; ++i) {
                if (this.glyphUnicodes[i].indexOf(ch) == 0 && this.languageMatches(this.glyphLangs[i]) && this.orientationMatches(this.glyphOrientations[i]) && this.formMatches(this.glyphUnicodes[i], this.glyphForms[i], attributedCharacterIterator, characterIterator.getIndex())) {
                    if (this.glyphUnicodes[i].length() == 1) {
                        final Element element = this.glyphElements[i];
                        final SVGGlyphElementBridge svgGlyphElementBridge = (SVGGlyphElementBridge)this.ctx.getBridge(element);
                        TextPaintInfo textPaintInfo = null;
                        if (attributedCharacterIterator != null) {
                            textPaintInfo = (TextPaintInfo)attributedCharacterIterator.getAttribute(SVGGVTFont.PAINT_INFO);
                        }
                        list.add(svgGlyphElementBridge.createGlyph(this.ctx, element, this.textElement, i, this.fontSize, this.fontFace, textPaintInfo));
                        b = true;
                        break;
                    }
                    final int index = characterIterator.getIndex();
                    boolean b2 = true;
                    for (int j = 1; j < this.glyphUnicodes[i].length(); ++j) {
                        if (this.glyphUnicodes[i].charAt(j) != characterIterator.next()) {
                            b2 = false;
                            break;
                        }
                    }
                    if (b2) {
                        final Element element2 = this.glyphElements[i];
                        final SVGGlyphElementBridge svgGlyphElementBridge2 = (SVGGlyphElementBridge)this.ctx.getBridge(element2);
                        TextPaintInfo textPaintInfo2 = null;
                        if (attributedCharacterIterator != null) {
                            attributedCharacterIterator.setIndex(characterIterator.getIndex());
                            textPaintInfo2 = (TextPaintInfo)attributedCharacterIterator.getAttribute(SVGGVTFont.PAINT_INFO);
                        }
                        list.add(svgGlyphElementBridge2.createGlyph(this.ctx, element2, this.textElement, i, this.fontSize, this.fontFace, textPaintInfo2));
                        b = true;
                        break;
                    }
                    ch = characterIterator.setIndex(index);
                }
            }
            if (!b) {
                final SVGGlyphElementBridge svgGlyphElementBridge3 = (SVGGlyphElementBridge)this.ctx.getBridge(this.missingGlyphElement);
                TextPaintInfo textPaintInfo3 = null;
                if (attributedCharacterIterator != null) {
                    attributedCharacterIterator.setIndex(characterIterator.getIndex());
                    textPaintInfo3 = (TextPaintInfo)attributedCharacterIterator.getAttribute(SVGGVTFont.PAINT_INFO);
                }
                list.add(svgGlyphElementBridge3.createGlyph(this.ctx, this.missingGlyphElement, this.textElement, -1, this.fontSize, this.fontFace, textPaintInfo3));
            }
        }
        return new SVGGVTGlyphVector(this, (Glyph[])list.toArray(new Glyph[list.size()]), fontRenderContext);
    }
    
    public GVTGlyphVector createGlyphVector(final FontRenderContext fontRenderContext, final int[] array, final CharacterIterator characterIterator) {
        final int length = array.length;
        final StringBuffer sb = new StringBuffer(length);
        for (int i = 0; i < length; ++i) {
            sb.append(this.glyphUnicodes[array[i]]);
        }
        return this.createGlyphVector(fontRenderContext, new StringCharacterIterator(sb.toString()));
    }
    
    public GVTGlyphVector createGlyphVector(final FontRenderContext fontRenderContext, final String text) {
        return this.createGlyphVector(fontRenderContext, new StringCharacterIterator(text));
    }
    
    public GVTFont deriveFont(final float n) {
        return new SVGGVTFont(n, this.fontFace, this.glyphUnicodes, this.glyphNames, this.glyphLangs, this.glyphOrientations, this.glyphForms, this.ctx, this.glyphElements, this.missingGlyphElement, this.hkernElements, this.vkernElements, this.textElement);
    }
    
    public String getFamilyName() {
        return this.fontFace.getFamilyName();
    }
    
    protected GVTLineMetrics getLineMetrics(final int n, final int n2) {
        if (this.lineMetrics != null) {
            return this.lineMetrics;
        }
        final float unitsPerEm = this.fontFace.getUnitsPerEm();
        final float n3 = this.fontSize / unitsPerEm;
        final float n4 = this.fontFace.getAscent() * n3;
        final float n5 = this.fontFace.getDescent() * n3;
        return this.lineMetrics = new GVTLineMetrics(n4, 0, new float[] { 0.0f, (n4 + n5) / 2.0f - n4, -n4 }, n5, unitsPerEm, unitsPerEm, n2 - n, this.fontFace.getStrikethroughPosition() * -n3, this.fontFace.getStrikethroughThickness() * n3, this.fontFace.getUnderlinePosition() * n3, this.fontFace.getUnderlineThickness() * n3, this.fontFace.getOverlinePosition() * -n3, this.fontFace.getOverlineThickness() * n3);
    }
    
    public GVTLineMetrics getLineMetrics(final char[] array, final int n, final int n2, final FontRenderContext fontRenderContext) {
        return this.getLineMetrics(n, n2);
    }
    
    public GVTLineMetrics getLineMetrics(final CharacterIterator characterIterator, final int n, final int n2, final FontRenderContext fontRenderContext) {
        return this.getLineMetrics(n, n2);
    }
    
    public GVTLineMetrics getLineMetrics(final String text, final FontRenderContext fontRenderContext) {
        return this.getLineMetrics(new StringCharacterIterator(text), 0, text.length(), fontRenderContext);
    }
    
    public GVTLineMetrics getLineMetrics(final String text, final int n, final int n2, final FontRenderContext fontRenderContext) {
        return this.getLineMetrics(new StringCharacterIterator(text), n, n2, fontRenderContext);
    }
    
    public float getSize() {
        return this.fontSize;
    }
    
    public String toString() {
        return this.fontFace.getFamilyName() + " " + this.fontFace.getFontWeight() + " " + this.fontFace.getFontStyle();
    }
    
    static {
        PAINT_INFO = GVTAttributedCharacterIterator.TextAttribute.PAINT_INFO;
    }
}
