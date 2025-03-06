// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.awt.Font;
import java.text.AttributedCharacterIterator;

public class AWTFontFamily implements GVTFontFamily
{
    public static final AttributedCharacterIterator.Attribute TEXT_COMPOUND_DELIMITER;
    protected GVTFontFace fontFace;
    protected Font font;
    
    public AWTFontFamily(final GVTFontFace fontFace) {
        this.fontFace = fontFace;
    }
    
    public AWTFontFamily(final String s) {
        this(new GVTFontFace(s));
    }
    
    public AWTFontFamily(final GVTFontFace fontFace, final Font font) {
        this.fontFace = fontFace;
        this.font = font;
    }
    
    public String getFamilyName() {
        return this.fontFace.getFamilyName();
    }
    
    public GVTFontFace getFontFace() {
        return this.fontFace;
    }
    
    public GVTFont deriveFont(final float n, final AttributedCharacterIterator attributedCharacterIterator) {
        if (this.font != null) {
            return new AWTGVTFont(this.font, n);
        }
        return this.deriveFont(n, attributedCharacterIterator.getAttributes());
    }
    
    public GVTFont deriveFont(final float value, final Map m) {
        if (this.font != null) {
            return new AWTGVTFont(this.font, value);
        }
        final HashMap<TextAttribute, String> hashMap = new HashMap<TextAttribute, String>(m);
        hashMap.put(TextAttribute.SIZE, new Float(value));
        hashMap.put(TextAttribute.FAMILY, (Float)this.fontFace.getFamilyName());
        hashMap.remove(AWTFontFamily.TEXT_COMPOUND_DELIMITER);
        return new AWTGVTFont(hashMap);
    }
    
    static {
        TEXT_COMPOUND_DELIMITER = GVTAttributedCharacterIterator.TextAttribute.TEXT_COMPOUND_DELIMITER;
    }
}
