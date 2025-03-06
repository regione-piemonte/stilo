// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

import java.util.Map;
import java.text.AttributedCharacterIterator;

public class UnresolvedFontFamily implements GVTFontFamily
{
    protected GVTFontFace fontFace;
    
    public UnresolvedFontFamily(final GVTFontFace fontFace) {
        this.fontFace = fontFace;
    }
    
    public UnresolvedFontFamily(final String s) {
        this(new GVTFontFace(s));
    }
    
    public GVTFontFace getFontFace() {
        return this.fontFace;
    }
    
    public String getFamilyName() {
        return this.fontFace.getFamilyName();
    }
    
    public GVTFont deriveFont(final float n, final AttributedCharacterIterator attributedCharacterIterator) {
        return null;
    }
    
    public GVTFont deriveFont(final float n, final Map map) {
        return null;
    }
}
