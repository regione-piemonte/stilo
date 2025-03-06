// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.xmleditor;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.StyleContext;

public class XMLContext extends StyleContext
{
    public static final String XML_DECLARATION_STYLE = "xml_declaration";
    public static final String DOCTYPE_STYLE = "doctype";
    public static final String COMMENT_STYLE = "comment";
    public static final String ELEMENT_STYLE = "element";
    public static final String CHARACTER_DATA_STYLE = "character_data";
    public static final String ATTRIBUTE_NAME_STYLE = "attribute_name";
    public static final String ATTRIBUTE_VALUE_STYLE = "attribute_value";
    public static final String CDATA_STYLE = "cdata";
    protected Map syntaxForegroundMap;
    protected Map syntaxFontMap;
    
    public XMLContext() {
        this.syntaxForegroundMap = null;
        this.syntaxFontMap = null;
        this.syntaxFontMap = new HashMap();
        this.syntaxForegroundMap = new HashMap();
        final Font font = new Font("Monospaced", 0, 12);
        final String s = "default";
        final Font font2 = font;
        final Color black = Color.black;
        this.syntaxFontMap.put(s, font2);
        this.syntaxForegroundMap.put(s, black);
        final String s2 = "xml_declaration";
        final Font deriveFont = font.deriveFont(1);
        final Color color = new Color(0, 0, 124);
        this.syntaxFontMap.put(s2, deriveFont);
        this.syntaxForegroundMap.put(s2, color);
        final String s3 = "doctype";
        final Font deriveFont2 = font.deriveFont(1);
        final Color color2 = new Color(0, 0, 124);
        this.syntaxFontMap.put(s3, deriveFont2);
        this.syntaxForegroundMap.put(s3, color2);
        final String s4 = "comment";
        final Font font3 = font;
        final Color color3 = new Color(128, 128, 128);
        this.syntaxFontMap.put(s4, font3);
        this.syntaxForegroundMap.put(s4, color3);
        final String s5 = "element";
        final Font font4 = font;
        final Color color4 = new Color(0, 0, 255);
        this.syntaxFontMap.put(s5, font4);
        this.syntaxForegroundMap.put(s5, color4);
        final String s6 = "character_data";
        final Font font5 = font;
        final Color black2 = Color.black;
        this.syntaxFontMap.put(s6, font5);
        this.syntaxForegroundMap.put(s6, black2);
        final String s7 = "attribute_name";
        final Font font6 = font;
        final Color color5 = new Color(0, 124, 0);
        this.syntaxFontMap.put(s7, font6);
        this.syntaxForegroundMap.put(s7, color5);
        final String s8 = "attribute_value";
        final Font font7 = font;
        final Color color6 = new Color(153, 0, 107);
        this.syntaxFontMap.put(s8, font7);
        this.syntaxForegroundMap.put(s8, color6);
        final String s9 = "cdata";
        final Font font8 = font;
        final Color color7 = new Color(124, 98, 0);
        this.syntaxFontMap.put(s9, font8);
        this.syntaxForegroundMap.put(s9, color7);
    }
    
    public XMLContext(final Map syntaxFont, final Map syntaxForeground) {
        this.syntaxForegroundMap = null;
        this.syntaxFontMap = null;
        this.setSyntaxFont(syntaxFont);
        this.setSyntaxForeground(syntaxForeground);
    }
    
    public void setSyntaxForeground(final Map syntaxForegroundMap) {
        if (syntaxForegroundMap == null) {
            throw new IllegalArgumentException("syntaxForegroundMap can not be null");
        }
        this.syntaxForegroundMap = syntaxForegroundMap;
    }
    
    public void setSyntaxFont(final Map syntaxFontMap) {
        if (syntaxFontMap == null) {
            throw new IllegalArgumentException("syntaxFontMap can not be null");
        }
        this.syntaxFontMap = syntaxFontMap;
    }
    
    public Color getSyntaxForeground(final int n) {
        return this.getSyntaxForeground(this.getSyntaxName(n));
    }
    
    public Color getSyntaxForeground(final String s) {
        return this.syntaxForegroundMap.get(s);
    }
    
    public Font getSyntaxFont(final int n) {
        return this.getSyntaxFont(this.getSyntaxName(n));
    }
    
    public Font getSyntaxFont(final String s) {
        return this.syntaxFontMap.get(s);
    }
    
    public String getSyntaxName(final int n) {
        String s = null;
        switch (n) {
            case 6: {
                s = "xml_declaration";
                break;
            }
            case 7: {
                s = "doctype";
                break;
            }
            case 1: {
                s = "comment";
                break;
            }
            case 2: {
                s = "element";
                break;
            }
            case 4: {
                s = "attribute_name";
                break;
            }
            case 5: {
                s = "attribute_value";
                break;
            }
            case 10: {
                s = "cdata";
                break;
            }
            default: {
                s = "default";
                break;
            }
        }
        return s;
    }
}
