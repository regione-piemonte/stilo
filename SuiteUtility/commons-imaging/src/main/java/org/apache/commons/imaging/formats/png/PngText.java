// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.formats.png;

public abstract class PngText
{
    public final String keyword;
    public final String text;
    
    public PngText(final String keyword, final String text) {
        this.keyword = keyword;
        this.text = text;
    }
    
    public static class Text extends PngText
    {
        public Text(final String keyword, final String text) {
            super(keyword, text);
        }
    }
    
    public static class Ztxt extends PngText
    {
        public Ztxt(final String keyword, final String text) {
            super(keyword, text);
        }
    }
    
    public static class Itxt extends PngText
    {
        public final String languageTag;
        public final String translatedKeyword;
        
        public Itxt(final String keyword, final String text, final String languageTag, final String translatedKeyword) {
            super(keyword, text);
            this.languageTag = languageTag;
            this.translatedKeyword = translatedKeyword;
        }
    }
}
