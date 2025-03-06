// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.svggen;

import org.w3c.dom.NodeList;
import java.awt.font.LineMetrics;
import java.awt.font.GlyphMetrics;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.awt.geom.AffineTransform;
import java.awt.font.FontRenderContext;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

public class SVGFont extends AbstractSVGConverter
{
    public static final float EXTRA_LIGHT;
    public static final float LIGHT;
    public static final float DEMILIGHT;
    public static final float REGULAR;
    public static final float SEMIBOLD;
    public static final float MEDIUM;
    public static final float DEMIBOLD;
    public static final float BOLD;
    public static final float HEAVY;
    public static final float EXTRABOLD;
    public static final float ULTRABOLD;
    public static final float POSTURE_REGULAR;
    public static final float POSTURE_OBLIQUE;
    static final float[] fontStyles;
    static final String[] svgStyles;
    static final float[] fontWeights;
    static final String[] svgWeights;
    static Map logicalFontMap;
    static final int COMMON_FONT_SIZE = 100;
    final Map fontStringMap;
    
    public SVGFont(final SVGGeneratorContext svgGeneratorContext) {
        super(svgGeneratorContext);
        this.fontStringMap = new HashMap();
    }
    
    public void recordFontUsage(final String s, final Font font) {
        final Font commonSizeFont = createCommonSizeFont(font);
        final String string = commonSizeFont.getFamily() + commonSizeFont.getStyle();
        CharListHelper charListHelper = this.fontStringMap.get(string);
        if (charListHelper == null) {
            charListHelper = new CharListHelper();
        }
        for (int i = 0; i < s.length(); ++i) {
            charListHelper.add(s.charAt(i));
        }
        this.fontStringMap.put(string, charListHelper);
    }
    
    private static Font createCommonSizeFont(final Font font) {
        final HashMap<AttributedCharacterIterator.Attribute, Object> attributes = new HashMap<AttributedCharacterIterator.Attribute, Object>(font.getAttributes());
        attributes.put(TextAttribute.SIZE, new Float(100.0f));
        attributes.remove(TextAttribute.TRANSFORM);
        return new Font(attributes);
    }
    
    public SVGDescriptor toSVG(final GraphicContext graphicContext) {
        return this.toSVG(graphicContext.getFont(), graphicContext.getFontRenderContext());
    }
    
    public SVGFontDescriptor toSVG(final Font font, final FontRenderContext fontRenderContext) {
        final FontRenderContext frc = new FontRenderContext(new AffineTransform(), fontRenderContext.isAntiAliased(), fontRenderContext.usesFractionalMetrics());
        final String string = "" + this.doubleString(font.getSize2D());
        final String weightToSVG = weightToSVG(font);
        final String styleToSVG = styleToSVG(font);
        final String familyToSVG = familyToSVG(font);
        final Font commonSizeFont = createCommonSizeFont(font);
        final String string2 = commonSizeFont.getFamily() + commonSizeFont.getStyle();
        final CharListHelper charListHelper = this.fontStringMap.get(string2);
        if (charListHelper == null) {
            return new SVGFontDescriptor(string, weightToSVG, styleToSVG, familyToSVG, null);
        }
        final Document domFactory = this.generatorContext.domFactory;
        final SVGFontDescriptor svgFontDescriptor = this.descMap.get(string2);
        Element element;
        if (svgFontDescriptor != null) {
            element = svgFontDescriptor.getDef();
        }
        else {
            element = domFactory.createElementNS("http://www.w3.org/2000/svg", "font");
            final Element elementNS = domFactory.createElementNS("http://www.w3.org/2000/svg", "font-face");
            String substring = familyToSVG;
            if (familyToSVG.startsWith("'") && familyToSVG.endsWith("'")) {
                substring = familyToSVG.substring(1, familyToSVG.length() - 1);
            }
            elementNS.setAttributeNS(null, "font-family", substring);
            elementNS.setAttributeNS(null, "font-weight", weightToSVG);
            elementNS.setAttributeNS(null, "font-style", styleToSVG);
            elementNS.setAttributeNS(null, "units-per-em", "100");
            element.appendChild(elementNS);
            final Element elementNS2 = domFactory.createElementNS("http://www.w3.org/2000/svg", "missing-glyph");
            final GlyphVector glyphVector = commonSizeFont.createGlyphVector(frc, new int[] { commonSizeFont.getMissingGlyphCode() });
            final Shape glyphOutline = glyphVector.getGlyphOutline(0);
            final GlyphMetrics glyphMetrics = glyphVector.getGlyphMetrics(0);
            elementNS2.setAttributeNS(null, "d", SVGPath.toSVGPathData(AffineTransform.getScaleInstance(1.0, -1.0).createTransformedShape(glyphOutline), this.generatorContext));
            elementNS2.setAttributeNS(null, "horiz-adv-x", String.valueOf(glyphMetrics.getAdvance()));
            element.appendChild(elementNS2);
            element.setAttributeNS(null, "horiz-adv-x", String.valueOf(glyphMetrics.getAdvance()));
            final LineMetrics lineMetrics = commonSizeFont.getLineMetrics("By", frc);
            elementNS.setAttributeNS(null, "ascent", String.valueOf(lineMetrics.getAscent()));
            elementNS.setAttributeNS(null, "descent", String.valueOf(lineMetrics.getDescent()));
            element.setAttributeNS(null, "id", this.generatorContext.idGenerator.generateID("font"));
        }
        final String newChars = charListHelper.getNewChars();
        charListHelper.clearNewChars();
        for (int i = newChars.length() - 1; i >= 0; --i) {
            final char char1 = newChars.charAt(i);
            final String value = String.valueOf(char1);
            boolean b = false;
            final NodeList childNodes = element.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); ++j) {
                if (childNodes.item(j) instanceof Element && ((Element)childNodes.item(j)).getAttributeNS(null, "unicode").equals(value)) {
                    b = true;
                    break;
                }
            }
            if (b) {
                break;
            }
            final Element elementNS3 = domFactory.createElementNS("http://www.w3.org/2000/svg", "glyph");
            final GlyphVector glyphVector2 = commonSizeFont.createGlyphVector(frc, "" + char1);
            final Shape glyphOutline2 = glyphVector2.getGlyphOutline(0);
            final GlyphMetrics glyphMetrics2 = glyphVector2.getGlyphMetrics(0);
            elementNS3.setAttributeNS(null, "d", SVGPath.toSVGPathData(AffineTransform.getScaleInstance(1.0, -1.0).createTransformedShape(glyphOutline2), this.generatorContext));
            elementNS3.setAttributeNS(null, "horiz-adv-x", String.valueOf(glyphMetrics2.getAdvance()));
            elementNS3.setAttributeNS(null, "unicode", String.valueOf(char1));
            element.appendChild(elementNS3);
        }
        final SVGFontDescriptor svgFontDescriptor2 = new SVGFontDescriptor(string, weightToSVG, styleToSVG, familyToSVG, element);
        if (svgFontDescriptor == null) {
            this.descMap.put(string2, svgFontDescriptor2);
            this.defSet.add(element);
        }
        return svgFontDescriptor2;
    }
    
    public static String familyToSVG(final Font font) {
        final String family = font.getFamily();
        final String s = SVGFont.logicalFontMap.get(font.getName().toLowerCase());
        String string;
        if (s != null) {
            string = s;
        }
        else {
            string = '\'' + family + '\'';
        }
        return string;
    }
    
    public static String styleToSVG(final Font font) {
        Float n = (Float)font.getAttributes().get(TextAttribute.POSTURE);
        if (n == null) {
            if (font.isItalic()) {
                n = TextAttribute.POSTURE_OBLIQUE;
            }
            else {
                n = TextAttribute.POSTURE_REGULAR;
            }
        }
        float floatValue;
        int n2;
        for (floatValue = n, n2 = 0; n2 < SVGFont.fontStyles.length && floatValue > SVGFont.fontStyles[n2]; ++n2) {}
        return SVGFont.svgStyles[n2];
    }
    
    public static String weightToSVG(final Font font) {
        Float n = (Float)font.getAttributes().get(TextAttribute.WEIGHT);
        if (n == null) {
            if (font.isBold()) {
                n = TextAttribute.WEIGHT_BOLD;
            }
            else {
                n = TextAttribute.WEIGHT_REGULAR;
            }
        }
        float floatValue;
        int n2;
        for (floatValue = n, n2 = 0; n2 < SVGFont.fontWeights.length && floatValue > SVGFont.fontWeights[n2]; ++n2) {}
        return SVGFont.svgWeights[n2];
    }
    
    static {
        EXTRA_LIGHT = TextAttribute.WEIGHT_EXTRA_LIGHT;
        LIGHT = TextAttribute.WEIGHT_LIGHT;
        DEMILIGHT = TextAttribute.WEIGHT_DEMILIGHT;
        REGULAR = TextAttribute.WEIGHT_REGULAR;
        SEMIBOLD = TextAttribute.WEIGHT_SEMIBOLD;
        MEDIUM = TextAttribute.WEIGHT_MEDIUM;
        DEMIBOLD = TextAttribute.WEIGHT_DEMIBOLD;
        BOLD = TextAttribute.WEIGHT_BOLD;
        HEAVY = TextAttribute.WEIGHT_HEAVY;
        EXTRABOLD = TextAttribute.WEIGHT_EXTRABOLD;
        ULTRABOLD = TextAttribute.WEIGHT_ULTRABOLD;
        POSTURE_REGULAR = TextAttribute.POSTURE_REGULAR;
        POSTURE_OBLIQUE = TextAttribute.POSTURE_OBLIQUE;
        fontStyles = new float[] { SVGFont.POSTURE_REGULAR + (SVGFont.POSTURE_OBLIQUE - SVGFont.POSTURE_REGULAR) / 2.0f };
        svgStyles = new String[] { "normal", "italic" };
        fontWeights = new float[] { SVGFont.EXTRA_LIGHT + (SVGFont.LIGHT - SVGFont.EXTRA_LIGHT) / 2.0f, SVGFont.LIGHT + (SVGFont.DEMILIGHT - SVGFont.LIGHT) / 2.0f, SVGFont.DEMILIGHT + (SVGFont.REGULAR - SVGFont.DEMILIGHT) / 2.0f, SVGFont.REGULAR + (SVGFont.SEMIBOLD - SVGFont.REGULAR) / 2.0f, SVGFont.SEMIBOLD + (SVGFont.MEDIUM - SVGFont.SEMIBOLD) / 2.0f, SVGFont.MEDIUM + (SVGFont.DEMIBOLD - SVGFont.MEDIUM) / 2.0f, SVGFont.DEMIBOLD + (SVGFont.BOLD - SVGFont.DEMIBOLD) / 2.0f, SVGFont.BOLD + (SVGFont.HEAVY - SVGFont.BOLD) / 2.0f, SVGFont.HEAVY + (SVGFont.EXTRABOLD - SVGFont.HEAVY) / 2.0f, SVGFont.EXTRABOLD + (SVGFont.ULTRABOLD - SVGFont.EXTRABOLD) };
        svgWeights = new String[] { "100", "200", "300", "normal", "500", "500", "600", "bold", "800", "800", "900" };
        (SVGFont.logicalFontMap = new HashMap()).put("dialog", "sans-serif");
        SVGFont.logicalFontMap.put("dialoginput", "monospace");
        SVGFont.logicalFontMap.put("monospaced", "monospace");
        SVGFont.logicalFontMap.put("serif", "serif");
        SVGFont.logicalFontMap.put("sansserif", "sans-serif");
        SVGFont.logicalFontMap.put("symbol", "'WingDings'");
    }
    
    private static class CharListHelper
    {
        private int nUsed;
        private int[] charList;
        private StringBuffer freshChars;
        
        CharListHelper() {
            this.nUsed = 0;
            this.charList = new int[40];
            this.freshChars = new StringBuffer(40);
        }
        
        String getNewChars() {
            return this.freshChars.toString();
        }
        
        void clearNewChars() {
            this.freshChars = new StringBuffer(40);
        }
        
        boolean add(final int n) {
            final int binSearch = binSearch(this.charList, this.nUsed, n);
            if (binSearch >= 0) {
                return false;
            }
            if (this.nUsed == this.charList.length) {
                final int[] charList = new int[this.nUsed + 20];
                System.arraycopy(this.charList, 0, charList, 0, this.nUsed);
                this.charList = charList;
            }
            final int n2 = -binSearch - 1;
            System.arraycopy(this.charList, n2, this.charList, n2 + 1, this.nUsed - n2);
            this.charList[n2] = n;
            this.freshChars.append((char)n);
            ++this.nUsed;
            return true;
        }
        
        static int binSearch(final int[] array, final int n, final int n2) {
            int i = 0;
            int n3 = n - 1;
            while (i <= n3) {
                final int n4 = i + n3 >>> 1;
                final int n5 = array[n4];
                if (n5 < n2) {
                    i = n4 + 1;
                }
                else {
                    if (n5 <= n2) {
                        return n4;
                    }
                    n3 = n4 - 1;
                }
            }
            return -(i + 1);
        }
    }
}
