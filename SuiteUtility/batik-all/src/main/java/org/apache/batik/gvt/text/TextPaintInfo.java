// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import java.awt.Composite;
import java.awt.Stroke;
import java.awt.Paint;

public class TextPaintInfo
{
    public boolean visible;
    public Paint fillPaint;
    public Paint strokePaint;
    public Stroke strokeStroke;
    public Composite composite;
    public Paint underlinePaint;
    public Paint underlineStrokePaint;
    public Stroke underlineStroke;
    public Paint overlinePaint;
    public Paint overlineStrokePaint;
    public Stroke overlineStroke;
    public Paint strikethroughPaint;
    public Paint strikethroughStrokePaint;
    public Stroke strikethroughStroke;
    public int startChar;
    public int endChar;
    
    public TextPaintInfo() {
    }
    
    public TextPaintInfo(final TextPaintInfo textPaintInfo) {
        this.set(textPaintInfo);
    }
    
    public void set(final TextPaintInfo textPaintInfo) {
        if (textPaintInfo == null) {
            this.fillPaint = null;
            this.strokePaint = null;
            this.strokeStroke = null;
            this.composite = null;
            this.underlinePaint = null;
            this.underlineStrokePaint = null;
            this.underlineStroke = null;
            this.overlinePaint = null;
            this.overlineStrokePaint = null;
            this.overlineStroke = null;
            this.strikethroughPaint = null;
            this.strikethroughStrokePaint = null;
            this.strikethroughStroke = null;
            this.visible = false;
        }
        else {
            this.fillPaint = textPaintInfo.fillPaint;
            this.strokePaint = textPaintInfo.strokePaint;
            this.strokeStroke = textPaintInfo.strokeStroke;
            this.composite = textPaintInfo.composite;
            this.underlinePaint = textPaintInfo.underlinePaint;
            this.underlineStrokePaint = textPaintInfo.underlineStrokePaint;
            this.underlineStroke = textPaintInfo.underlineStroke;
            this.overlinePaint = textPaintInfo.overlinePaint;
            this.overlineStrokePaint = textPaintInfo.overlineStrokePaint;
            this.overlineStroke = textPaintInfo.overlineStroke;
            this.strikethroughPaint = textPaintInfo.strikethroughPaint;
            this.strikethroughStrokePaint = textPaintInfo.strikethroughStrokePaint;
            this.strikethroughStroke = textPaintInfo.strikethroughStroke;
            this.visible = textPaintInfo.visible;
        }
    }
    
    public static boolean equivilent(final TextPaintInfo textPaintInfo, final TextPaintInfo textPaintInfo2) {
        if (textPaintInfo == null) {
            return textPaintInfo2 == null;
        }
        return textPaintInfo2 != null && textPaintInfo.fillPaint == null == (textPaintInfo2.fillPaint == null) && textPaintInfo.visible == textPaintInfo2.visible && (textPaintInfo.strokePaint != null && textPaintInfo.strokeStroke != null) == (textPaintInfo2.strokePaint != null && textPaintInfo2.strokeStroke != null);
    }
}
