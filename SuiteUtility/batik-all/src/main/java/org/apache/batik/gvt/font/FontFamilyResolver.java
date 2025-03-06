// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FontFamilyResolver
{
    public static final AWTFontFamily defaultFont;
    protected static final Map fonts;
    protected static final List awtFontFamilies;
    protected static final List awtFonts;
    protected static final Map resolvedFontFamilies;
    
    public static String lookup(final String s) {
        return FontFamilyResolver.fonts.get(s.toLowerCase());
    }
    
    public static GVTFontFamily resolve(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        GVTFontFamily gvtFontFamily = FontFamilyResolver.resolvedFontFamilies.get(lowerCase);
        if (gvtFontFamily == null) {
            final String s = FontFamilyResolver.fonts.get(lowerCase);
            if (s != null) {
                gvtFontFamily = new AWTFontFamily(s);
            }
            FontFamilyResolver.resolvedFontFamilies.put(lowerCase, gvtFontFamily);
        }
        return gvtFontFamily;
    }
    
    public static GVTFontFamily resolve(final UnresolvedFontFamily unresolvedFontFamily) {
        return resolve(unresolvedFontFamily.getFamilyName());
    }
    
    public static GVTFontFamily getFamilyThatCanDisplay(final char c) {
        for (int i = 0; i < FontFamilyResolver.awtFontFamilies.size(); ++i) {
            final AWTFontFamily awtFontFamily = FontFamilyResolver.awtFontFamilies.get(i);
            if (FontFamilyResolver.awtFonts.get(i).canDisplay(c) && awtFontFamily.getFamilyName().indexOf("Song") == -1) {
                return awtFontFamily;
            }
        }
        return null;
    }
    
    static {
        defaultFont = new AWTFontFamily("SansSerif");
        fonts = new HashMap();
        awtFontFamilies = new ArrayList();
        awtFonts = new ArrayList();
        FontFamilyResolver.fonts.put("sans-serif", "SansSerif");
        FontFamilyResolver.fonts.put("serif", "Serif");
        FontFamilyResolver.fonts.put("times", "Serif");
        FontFamilyResolver.fonts.put("times new roman", "Serif");
        FontFamilyResolver.fonts.put("cursive", "Dialog");
        FontFamilyResolver.fonts.put("fantasy", "Symbol");
        FontFamilyResolver.fonts.put("monospace", "Monospaced");
        FontFamilyResolver.fonts.put("monospaced", "Monospaced");
        FontFamilyResolver.fonts.put("courier", "Monospaced");
        final String[] availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (int n = (availableFontFamilyNames != null) ? availableFontFamilyNames.length : 0, i = 0; i < n; ++i) {
            FontFamilyResolver.fonts.put(availableFontFamilyNames[i].toLowerCase(), availableFontFamilyNames[i]);
            final StringTokenizer stringTokenizer = new StringTokenizer(availableFontFamilyNames[i]);
            String string = "";
            while (stringTokenizer.hasMoreTokens()) {
                string += stringTokenizer.nextToken();
            }
            FontFamilyResolver.fonts.put(string.toLowerCase(), availableFontFamilyNames[i]);
            final String replace = availableFontFamilyNames[i].replace(' ', '-');
            if (!replace.equals(availableFontFamilyNames[i])) {
                FontFamilyResolver.fonts.put(replace.toLowerCase(), availableFontFamilyNames[i]);
            }
        }
        FontFamilyResolver.awtFontFamilies.add(FontFamilyResolver.defaultFont);
        FontFamilyResolver.awtFonts.add(new AWTGVTFont(FontFamilyResolver.defaultFont.getFamilyName(), 0, 12));
        for (final String s : FontFamilyResolver.fonts.values()) {
            FontFamilyResolver.awtFontFamilies.add(new AWTFontFamily(s));
            FontFamilyResolver.awtFonts.add(new AWTGVTFont(s, 0, 12));
        }
        resolvedFontFamilies = new HashMap();
    }
}
