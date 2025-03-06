// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.renderer;

import java.util.HashSet;
import org.apache.batik.gvt.text.TextHit;
import org.apache.batik.gvt.text.Mark;
import java.awt.geom.Path2D;
import java.awt.geom.GeneralPath;
import java.awt.Stroke;
import java.awt.Paint;
import java.awt.Shape;
import org.apache.batik.gvt.text.TextPaintInfo;
import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.font.GVTLineMetrics;
import org.apache.batik.gvt.font.GVTGlyphMetrics;
import org.apache.batik.gvt.text.TextSpanLayout;
import java.awt.font.FontRenderContext;
import java.awt.RenderingHints;
import org.apache.batik.gvt.font.GVTFontFamily;
import java.text.CharacterIterator;
import org.apache.batik.gvt.font.GVTFont;
import java.text.AttributedString;
import org.apache.batik.gvt.font.FontFamilyResolver;
import java.awt.font.TextAttribute;
import java.util.Iterator;
import org.apache.batik.gvt.text.AttributedCharacterSpanIterator;
import org.apache.batik.gvt.text.TextPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import org.apache.batik.gvt.text.BidiAttributedCharacterIterator;
import java.util.List;
import java.awt.Graphics2D;
import org.apache.batik.gvt.TextNode;
import org.apache.batik.gvt.TextPainter;
import java.util.Set;
import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import java.text.AttributedCharacterIterator;

public class StrokingTextPainter extends BasicTextPainter
{
    public static final AttributedCharacterIterator.Attribute PAINT_INFO;
    public static final AttributedCharacterIterator.Attribute FLOW_REGIONS;
    public static final AttributedCharacterIterator.Attribute FLOW_PARAGRAPH;
    public static final AttributedCharacterIterator.Attribute TEXT_COMPOUND_ID;
    public static final AttributedCharacterIterator.Attribute GVT_FONT;
    public static final AttributedCharacterIterator.Attribute GVT_FONTS;
    public static final AttributedCharacterIterator.Attribute BIDI_LEVEL;
    public static final AttributedCharacterIterator.Attribute XPOS;
    public static final AttributedCharacterIterator.Attribute YPOS;
    public static final AttributedCharacterIterator.Attribute TEXTPATH;
    public static final AttributedCharacterIterator.Attribute WRITING_MODE;
    public static final Integer WRITING_MODE_TTB;
    public static final Integer WRITING_MODE_RTL;
    public static final AttributedCharacterIterator.Attribute ANCHOR_TYPE;
    public static final Integer ADJUST_SPACING;
    public static final Integer ADJUST_ALL;
    public static final GVTAttributedCharacterIterator.TextAttribute ALT_GLYPH_HANDLER;
    static Set extendedAtts;
    protected static TextPainter singleton;
    
    public static TextPainter getInstance() {
        return StrokingTextPainter.singleton;
    }
    
    public void paint(final TextNode textNode, final Graphics2D graphics2D) {
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return;
        }
        final List textRuns = this.getTextRuns(textNode, attributedCharacterIterator);
        this.paintDecorations(textRuns, graphics2D, 1);
        this.paintDecorations(textRuns, graphics2D, 4);
        this.paintTextRuns(textRuns, graphics2D);
        this.paintDecorations(textRuns, graphics2D, 2);
    }
    
    protected void printAttrs(final AttributedCharacterIterator attributedCharacterIterator) {
        attributedCharacterIterator.first();
        int beginIndex = attributedCharacterIterator.getBeginIndex();
        System.out.print("AttrRuns: ");
        while (attributedCharacterIterator.current() != '\uffff') {
            final int runLimit = attributedCharacterIterator.getRunLimit();
            System.out.print("" + (runLimit - beginIndex) + ", ");
            attributedCharacterIterator.setIndex(runLimit);
            beginIndex = runLimit;
        }
        System.out.println("");
    }
    
    public List getTextRuns(final TextNode textNode, final AttributedCharacterIterator attributedCharacterIterator) {
        final List textRuns = textNode.getTextRuns();
        if (textRuns != null) {
            return textRuns;
        }
        textNode.setTextRuns(this.computeTextRuns(textNode, attributedCharacterIterator, this.getTextChunkACIs(attributedCharacterIterator)));
        return textNode.getTextRuns();
    }
    
    public List computeTextRuns(final TextNode textNode, final AttributedCharacterIterator attributedCharacterIterator, final AttributedCharacterIterator[] array) {
        final int[][] array2 = new int[array.length][];
        int beginIndex = attributedCharacterIterator.getBeginIndex();
        for (int i = 0; i < array.length; ++i) {
            final BidiAttributedCharacterIterator bidiAttributedCharacterIterator = new BidiAttributedCharacterIterator(array[i], this.fontRenderContext, beginIndex);
            array[i] = bidiAttributedCharacterIterator;
            array2[i] = bidiAttributedCharacterIterator.getCharMap();
            array[i] = createModifiedACIForFontMatching(array[i]);
            beginIndex += array[i].getEndIndex() - array[i].getBeginIndex();
        }
        final ArrayList list = new ArrayList();
        TextChunk textChunk = null;
        int n = 0;
        Point2D point2D = textNode.getLocation();
        TextChunk textChunk2;
        do {
            array[n].first();
            textChunk2 = this.getTextChunk(textNode, array[n], array2[n], list, textChunk);
            array[n].first();
            if (textChunk2 != null) {
                point2D = this.adjustChunkOffsets(point2D, list, textChunk2);
            }
            textChunk = textChunk2;
            ++n;
        } while (textChunk2 != null && n < array.length);
        return list;
    }
    
    protected AttributedCharacterIterator[] getTextChunkACIs(final AttributedCharacterIterator attributedCharacterIterator) {
        final ArrayList<AttributedCharacterIterator> list = new ArrayList<AttributedCharacterIterator>();
        int beginIndex = attributedCharacterIterator.getBeginIndex();
        attributedCharacterIterator.first();
        final boolean b = attributedCharacterIterator.getAttribute(StrokingTextPainter.WRITING_MODE) == StrokingTextPainter.WRITING_MODE_TTB;
        while (attributedCharacterIterator.setIndex(beginIndex) != '\uffff') {
            TextPath textPath = null;
            int runLimit;
            for (int index = beginIndex; attributedCharacterIterator.setIndex(index) != '\uffff'; index = runLimit) {
                final TextPath textPath2 = (TextPath)attributedCharacterIterator.getAttribute(StrokingTextPainter.TEXTPATH);
                if (index != beginIndex) {
                    if (b) {
                        final Float n = (Float)attributedCharacterIterator.getAttribute(StrokingTextPainter.YPOS);
                        if (n != null && !n.isNaN()) {
                            break;
                        }
                    }
                    else {
                        final Float n2 = (Float)attributedCharacterIterator.getAttribute(StrokingTextPainter.XPOS);
                        if (n2 != null && !n2.isNaN()) {
                            break;
                        }
                    }
                    if (textPath == null && textPath2 != null) {
                        break;
                    }
                    if (textPath != null && textPath2 == null) {
                        break;
                    }
                }
                textPath = textPath2;
                if (attributedCharacterIterator.getAttribute(StrokingTextPainter.FLOW_PARAGRAPH) != null) {
                    attributedCharacterIterator.setIndex(attributedCharacterIterator.getRunLimit(StrokingTextPainter.FLOW_PARAGRAPH));
                    break;
                }
                runLimit = attributedCharacterIterator.getRunLimit(StrokingTextPainter.TEXT_COMPOUND_ID);
                if (index == beginIndex) {
                    if (attributedCharacterIterator.getAttribute(StrokingTextPainter.ANCHOR_TYPE) != TextNode.Anchor.START) {
                        if (b) {
                            final Float n3 = (Float)attributedCharacterIterator.getAttribute(StrokingTextPainter.YPOS);
                            if (n3 == null) {
                                continue;
                            }
                            if (n3.isNaN()) {
                                continue;
                            }
                        }
                        else {
                            final Float n4 = (Float)attributedCharacterIterator.getAttribute(StrokingTextPainter.XPOS);
                            if (n4 == null) {
                                continue;
                            }
                            if (n4.isNaN()) {
                                continue;
                            }
                        }
                        for (int i = index + 1; i < runLimit; ++i) {
                            attributedCharacterIterator.setIndex(i);
                            if (b) {
                                final Float n5 = (Float)attributedCharacterIterator.getAttribute(StrokingTextPainter.YPOS);
                                if (n5 == null) {
                                    break;
                                }
                                if (n5.isNaN()) {
                                    break;
                                }
                            }
                            else {
                                final Float n6 = (Float)attributedCharacterIterator.getAttribute(StrokingTextPainter.XPOS);
                                if (n6 == null) {
                                    break;
                                }
                                if (n6.isNaN()) {
                                    break;
                                }
                            }
                            list.add(new AttributedCharacterSpanIterator(attributedCharacterIterator, i - 1, i));
                            beginIndex = i;
                        }
                    }
                }
            }
            final int index2 = attributedCharacterIterator.getIndex();
            list.add(new AttributedCharacterSpanIterator(attributedCharacterIterator, beginIndex, index2));
            beginIndex = index2;
        }
        final AttributedCharacterIterator[] array = new AttributedCharacterIterator[list.size()];
        final Iterator<Object> iterator = list.iterator();
        int n7 = 0;
        while (iterator.hasNext()) {
            array[n7] = iterator.next();
            ++n7;
        }
        return array;
    }
    
    protected static AttributedCharacterIterator createModifiedACIForFontMatching(final AttributedCharacterIterator text) {
        text.first();
        AttributedString attributedString = null;
        int n = 0;
        final int beginIndex = text.getBeginIndex();
        int i = 1;
        int index = text.getRunStart(StrokingTextPainter.TEXT_COMPOUND_ID);
        while (i != 0) {
            final int n2 = index;
            index = text.getRunLimit(StrokingTextPainter.TEXT_COMPOUND_ID);
            final int n3 = index - n2;
            final List list = (List)text.getAttribute(StrokingTextPainter.GVT_FONTS);
            float floatValue = 12.0f;
            final Float n4 = (Float)text.getAttribute(TextAttribute.SIZE);
            if (n4 != null) {
                floatValue = n4;
            }
            if (list.size() == 0) {
                list.add(FontFamilyResolver.defaultFont.deriveFont(floatValue, text));
            }
            final boolean[] array = new boolean[n3];
            if (attributedString == null) {
                attributedString = new AttributedString(text);
            }
            GVTFont gvtFont = null;
            int n5 = 0;
            int n6 = n2;
            for (int j = 0; j < list.size(); ++j) {
                int k = n6;
                int n7 = 0;
                text.setIndex(k);
                final GVTFont gvtFont2 = list.get(j);
                if (gvtFont == null) {
                    gvtFont = gvtFont2;
                }
                while (k < index) {
                    int canDisplayUpTo = gvtFont2.canDisplayUpTo(text, k, index);
                    if (text.getAttribute(StrokingTextPainter.ALT_GLYPH_HANDLER) != null) {
                        canDisplayUpTo = -1;
                    }
                    if (canDisplayUpTo == -1) {
                        canDisplayUpTo = index;
                    }
                    if (canDisplayUpTo <= k) {
                        if (n7 == 0) {
                            n6 = k;
                            n7 = 1;
                        }
                        ++k;
                    }
                    else {
                        int n8 = -1;
                        for (int l = k; l < canDisplayUpTo; ++l) {
                            if (array[l - n2]) {
                                if (n8 != -1) {
                                    attributedString.addAttribute(StrokingTextPainter.GVT_FONT, gvtFont2, n8 - beginIndex, l - beginIndex);
                                    n8 = -1;
                                }
                            }
                            else if (n8 == -1) {
                                n8 = l;
                            }
                            array[l - n2] = true;
                            ++n5;
                        }
                        if (n8 != -1) {
                            attributedString.addAttribute(StrokingTextPainter.GVT_FONT, gvtFont2, n8 - beginIndex, canDisplayUpTo - beginIndex);
                        }
                        k = canDisplayUpTo + 1;
                    }
                }
                if (n5 == n3) {
                    break;
                }
            }
            int n9 = -1;
            GVTFontFamily gvtFontFamily = null;
            GVTFont value = gvtFont;
            for (int n10 = 0; n10 < n3; ++n10) {
                if (array[n10]) {
                    if (n9 != -1) {
                        attributedString.addAttribute(StrokingTextPainter.GVT_FONT, value, n9 + n, n10 + n);
                        n9 = -1;
                        value = null;
                        gvtFontFamily = null;
                    }
                }
                else {
                    final GVTFontFamily familyThatCanDisplay = FontFamilyResolver.getFamilyThatCanDisplay(text.setIndex(n2 + n10));
                    if (n9 == -1) {
                        n9 = n10;
                        gvtFontFamily = familyThatCanDisplay;
                        if (gvtFontFamily == null) {
                            value = gvtFont;
                        }
                        else {
                            value = familyThatCanDisplay.deriveFont(floatValue, text);
                        }
                    }
                    else if (gvtFontFamily != familyThatCanDisplay) {
                        attributedString.addAttribute(StrokingTextPainter.GVT_FONT, value, n9 + n, n10 + n);
                        n9 = n10;
                        gvtFontFamily = familyThatCanDisplay;
                        if (gvtFontFamily == null) {
                            value = gvtFont;
                        }
                        else {
                            value = familyThatCanDisplay.deriveFont(floatValue, text);
                        }
                    }
                }
            }
            if (n9 != -1) {
                attributedString.addAttribute(StrokingTextPainter.GVT_FONT, value, n9 + n, n3 + n);
            }
            n += n3;
            if (text.setIndex(index) == '\uffff') {
                i = 0;
            }
        }
        if (attributedString != null) {
            return attributedString.getIterator();
        }
        return text;
    }
    
    protected TextChunk getTextChunk(final TextNode textNode, final AttributedCharacterIterator attributedCharacterIterator, final int[] array, final List list, final TextChunk textChunk) {
        int end = 0;
        if (textChunk != null) {
            end = textChunk.end;
        }
        int n = end;
        final int index = attributedCharacterIterator.getIndex();
        if (attributedCharacterIterator.current() == '\uffff') {
            return null;
        }
        final Point2D.Float float1 = new Point2D.Float(0.0f, 0.0f);
        final Point2D.Float float2 = new Point2D.Float(0.0f, 0.0f);
        boolean b = true;
        while (true) {
            final int runStart = attributedCharacterIterator.getRunStart(StrokingTextPainter.extendedAtts);
            final int runLimit = attributedCharacterIterator.getRunLimit(StrokingTextPainter.extendedAtts);
            final AttributedCharacterSpanIterator attributedCharacterSpanIterator = new AttributedCharacterSpanIterator(attributedCharacterIterator, runStart, runLimit);
            final int[] array2 = new int[runLimit - runStart];
            System.arraycopy(array, runStart - index, array2, 0, array2.length);
            FontRenderContext fontRenderContext = this.fontRenderContext;
            final RenderingHints renderingHints = textNode.getRenderingHints();
            if (renderingHints != null && renderingHints.get(RenderingHints.KEY_TEXT_ANTIALIASING) == RenderingHints.VALUE_TEXT_ANTIALIAS_OFF) {
                fontRenderContext = this.aaOffFontRenderContext;
            }
            final TextSpanLayout textLayout = this.getTextLayoutFactory().createTextLayout(attributedCharacterSpanIterator, array2, float1, fontRenderContext);
            list.add(new TextRun(textLayout, attributedCharacterSpanIterator, b));
            final Point2D advance2D = textLayout.getAdvance2D();
            final Point2D.Float float3 = float2;
            float3.x += (float)advance2D.getX();
            final Point2D.Float float4 = float2;
            float4.y += (float)advance2D.getY();
            ++n;
            if (attributedCharacterIterator.setIndex(runLimit) == '\uffff') {
                break;
            }
            b = false;
        }
        return new TextChunk(end, n, float2);
    }
    
    protected Point2D adjustChunkOffsets(final Point2D point2D, final List list, final TextChunk textChunk) {
        final TextRun textRun = list.get(textChunk.begin);
        final int anchorType = textRun.getAnchorType();
        final Float length = textRun.getLength();
        final Integer lengthAdjust = textRun.getLengthAdjust();
        boolean b = true;
        if (length == null || length.isNaN()) {
            b = false;
        }
        int n = 0;
        for (int i = textChunk.begin; i < textChunk.end; ++i) {
            final AttributedCharacterIterator aci = list.get(i).getACI();
            n += aci.getEndIndex() - aci.getBeginIndex();
        }
        if (lengthAdjust == GVTAttributedCharacterIterator.TextAttribute.ADJUST_SPACING && n == 1) {
            b = false;
        }
        float n2 = 1.0f;
        float n3 = 1.0f;
        final TextSpanLayout layout = list.get(textChunk.end - 1).getLayout();
        final GVTGlyphMetrics glyphMetrics = layout.getGlyphMetrics(layout.getGlyphCount() - 1);
        final GVTLineMetrics lineMetrics = layout.getLineMetrics();
        final Rectangle2D bounds2D = glyphMetrics.getBounds2D();
        final float n4 = (glyphMetrics.getVerticalAdvance() - (lineMetrics.getAscent() + lineMetrics.getDescent())) / 2.0f;
        final float n5 = (float)(bounds2D.getWidth() + bounds2D.getX());
        final float n6 = (float)(n4 + lineMetrics.getAscent() + (bounds2D.getHeight() + bounds2D.getY()));
        Point2D.Float float1;
        if (!b) {
            float1 = new Point2D.Float((float)(textChunk.advance.getX() + n5 - glyphMetrics.getHorizontalAdvance()), (float)(textChunk.advance.getY() - glyphMetrics.getVerticalAdvance() + n6));
        }
        else {
            final Point2D advance = textChunk.advance;
            if (layout.isVertical()) {
                if (lengthAdjust == StrokingTextPainter.ADJUST_SPACING) {
                    n3 = (float)((length - n6) / (advance.getY() - glyphMetrics.getVerticalAdvance()));
                }
                else {
                    n3 = (float)(length / (advance.getY() - glyphMetrics.getVerticalAdvance() + n6));
                }
                float1 = new Point2D.Float(0.0f, length);
            }
            else {
                if (lengthAdjust == StrokingTextPainter.ADJUST_SPACING) {
                    n2 = (float)((length - n5) / (advance.getX() - glyphMetrics.getHorizontalAdvance()));
                }
                else {
                    n2 = (float)(length / (advance.getX() + n5 - glyphMetrics.getHorizontalAdvance()));
                }
                float1 = new Point2D.Float(length, 0.0f);
            }
            final Point2D.Float advance2 = new Point2D.Float(0.0f, 0.0f);
            for (int j = textChunk.begin; j < textChunk.end; ++j) {
                final TextSpanLayout layout2 = list.get(j).getLayout();
                layout2.setScale(n2, n3, lengthAdjust == StrokingTextPainter.ADJUST_SPACING);
                final Point2D advance2D = layout2.getAdvance2D();
                final Point2D.Float float2 = advance2;
                float2.x += (float)advance2D.getX();
                final Point2D.Float float3 = advance2;
                float3.y += (float)advance2D.getY();
            }
            textChunk.advance = advance2;
        }
        float n7 = 0.0f;
        float n8 = 0.0f;
        switch (anchorType) {
            case 1: {
                n7 = (float)(-float1.getX() / 2.0);
                n8 = (float)(-float1.getY() / 2.0);
                break;
            }
            case 2: {
                n7 = (float)(-float1.getX());
                n8 = (float)(-float1.getY());
                break;
            }
        }
        final TextRun textRun2 = list.get(textChunk.begin);
        final TextSpanLayout layout3 = textRun2.getLayout();
        final AttributedCharacterIterator aci2 = textRun2.getACI();
        aci2.first();
        final boolean vertical = layout3.isVertical();
        final Float n9 = (Float)aci2.getAttribute(StrokingTextPainter.XPOS);
        final Float n10 = (Float)aci2.getAttribute(StrokingTextPainter.YPOS);
        final TextPath textPath = (TextPath)aci2.getAttribute(StrokingTextPainter.TEXTPATH);
        float floatValue = (float)point2D.getX();
        float floatValue2 = (float)point2D.getY();
        float floatValue3 = 0.0f;
        float floatValue4 = 0.0f;
        if (n9 != null && !n9.isNaN()) {
            floatValue = (floatValue3 = n9);
        }
        if (n10 != null && !n10.isNaN()) {
            floatValue2 = (floatValue4 = n10);
        }
        float y;
        float x;
        if (vertical) {
            floatValue2 += n8;
            y = floatValue4 + n8;
            x = 0.0f;
        }
        else {
            floatValue += n7;
            x = floatValue3 + n7;
            y = 0.0f;
        }
        for (int k = textChunk.begin; k < textChunk.end; ++k) {
            final TextRun textRun3 = list.get(k);
            final TextSpanLayout layout4 = textRun3.getLayout();
            final AttributedCharacterIterator aci3 = textRun3.getACI();
            aci3.first();
            final TextPath textPath2 = (TextPath)aci3.getAttribute(StrokingTextPainter.TEXTPATH);
            if (vertical) {
                final Float n11 = (Float)aci3.getAttribute(StrokingTextPainter.XPOS);
                if (n11 != null && !n11.isNaN()) {
                    floatValue = n11;
                }
            }
            else {
                final Float n12 = (Float)aci3.getAttribute(StrokingTextPainter.YPOS);
                if (n12 != null && !n12.isNaN()) {
                    floatValue2 = n12;
                }
            }
            if (textPath2 == null) {
                layout4.setOffset(new Point2D.Float(floatValue, floatValue2));
                final Point2D advance2D2 = layout4.getAdvance2D();
                floatValue += (float)advance2D2.getX();
                floatValue2 += (float)advance2D2.getY();
            }
            else {
                layout4.setOffset(new Point2D.Float(x, y));
                final Point2D advance2D3 = layout4.getAdvance2D();
                x += (float)advance2D3.getX();
                y += (float)advance2D3.getY();
                final Point2D textPathAdvance = layout4.getTextPathAdvance();
                floatValue = (float)textPathAdvance.getX();
                floatValue2 = (float)textPathAdvance.getY();
            }
        }
        return new Point2D.Float(floatValue, floatValue2);
    }
    
    protected void paintDecorations(final List list, final Graphics2D graphics2D, final int n) {
        Paint paint = null;
        Paint paint2 = null;
        Stroke stroke = null;
        Rectangle2D rectangle2D = null;
        double y = 0.0;
        double height = 0.0;
        for (int i = 0; i < list.size(); ++i) {
            final TextRun textRun = list.get(i);
            final AttributedCharacterIterator aci = textRun.getACI();
            aci.first();
            final TextPaintInfo textPaintInfo = (TextPaintInfo)aci.getAttribute(StrokingTextPainter.PAINT_INFO);
            if (textPaintInfo != null && textPaintInfo.composite != null) {
                graphics2D.setComposite(textPaintInfo.composite);
            }
            Paint paint3 = null;
            Stroke stroke2 = null;
            Paint paint4 = null;
            if (textPaintInfo != null) {
                switch (n) {
                    case 1: {
                        paint3 = textPaintInfo.underlinePaint;
                        stroke2 = textPaintInfo.underlineStroke;
                        paint4 = textPaintInfo.underlineStrokePaint;
                        break;
                    }
                    case 4: {
                        paint3 = textPaintInfo.overlinePaint;
                        stroke2 = textPaintInfo.overlineStroke;
                        paint4 = textPaintInfo.overlineStrokePaint;
                        break;
                    }
                    case 2: {
                        paint3 = textPaintInfo.strikethroughPaint;
                        stroke2 = textPaintInfo.strikethroughStroke;
                        paint4 = textPaintInfo.strikethroughStrokePaint;
                        break;
                    }
                    default: {
                        return;
                    }
                }
            }
            if (textRun.isFirstRunInChunk()) {
                final Rectangle2D bounds2D = textRun.getLayout().getDecorationOutline(n).getBounds2D();
                y = bounds2D.getY();
                height = bounds2D.getHeight();
            }
            if ((textRun.isFirstRunInChunk() || paint3 != paint || stroke2 != stroke || paint4 != paint2) && rectangle2D != null) {
                if (paint != null) {
                    graphics2D.setPaint(paint);
                    graphics2D.fill(rectangle2D);
                }
                if (stroke != null && paint2 != null) {
                    graphics2D.setPaint(paint2);
                    graphics2D.setStroke(stroke);
                    graphics2D.draw(rectangle2D);
                }
                rectangle2D = null;
            }
            if ((paint3 != null || paint4 != null) && !textRun.getLayout().isVertical() && !textRun.getLayout().isOnATextPath()) {
                final Shape decorationOutline = textRun.getLayout().getDecorationOutline(n);
                if (rectangle2D == null) {
                    final Rectangle2D bounds2D2 = decorationOutline.getBounds2D();
                    rectangle2D = new Rectangle2D.Double(bounds2D2.getX(), y, bounds2D2.getWidth(), height);
                }
                else {
                    final Rectangle2D bounds2D3 = decorationOutline.getBounds2D();
                    final double min = Math.min(rectangle2D.getX(), bounds2D3.getX());
                    rectangle2D.setRect(min, y, Math.max(rectangle2D.getMaxX(), bounds2D3.getMaxX()) - min, height);
                }
            }
            paint = paint3;
            stroke = stroke2;
            paint2 = paint4;
        }
        if (rectangle2D != null) {
            if (paint != null) {
                graphics2D.setPaint(paint);
                graphics2D.fill(rectangle2D);
            }
            if (stroke != null && paint2 != null) {
                graphics2D.setPaint(paint2);
                graphics2D.setStroke(stroke);
                graphics2D.draw(rectangle2D);
            }
        }
    }
    
    protected void paintTextRuns(final List list, final Graphics2D graphics2D) {
        for (int i = 0; i < list.size(); ++i) {
            final TextRun textRun = list.get(i);
            final AttributedCharacterIterator aci = textRun.getACI();
            aci.first();
            final TextPaintInfo textPaintInfo = (TextPaintInfo)aci.getAttribute(StrokingTextPainter.PAINT_INFO);
            if (textPaintInfo != null && textPaintInfo.composite != null) {
                graphics2D.setComposite(textPaintInfo.composite);
            }
            textRun.getLayout().draw(graphics2D);
        }
    }
    
    public Shape getOutline(final TextNode textNode) {
        Path2D path2D = null;
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        final List textRuns = this.getTextRuns(textNode, attributedCharacterIterator);
        for (int i = 0; i < textRuns.size(); ++i) {
            final GeneralPath s = new GeneralPath(textRuns.get(i).getLayout().getOutline());
            if (path2D == null) {
                path2D = s;
            }
            else {
                path2D.setWindingRule(1);
                path2D.append(s, false);
            }
        }
        final Shape decorationOutline = this.getDecorationOutline(textRuns, 1);
        final Shape decorationOutline2 = this.getDecorationOutline(textRuns, 2);
        final Shape decorationOutline3 = this.getDecorationOutline(textRuns, 4);
        if (decorationOutline != null) {
            if (path2D == null) {
                path2D = new GeneralPath(decorationOutline);
            }
            else {
                path2D.setWindingRule(1);
                path2D.append(decorationOutline, false);
            }
        }
        if (decorationOutline2 != null) {
            if (path2D == null) {
                path2D = new GeneralPath(decorationOutline2);
            }
            else {
                path2D.setWindingRule(1);
                path2D.append(decorationOutline2, false);
            }
        }
        if (decorationOutline3 != null) {
            if (path2D == null) {
                path2D = new GeneralPath(decorationOutline3);
            }
            else {
                path2D.setWindingRule(1);
                path2D.append(decorationOutline3, false);
            }
        }
        return path2D;
    }
    
    public Rectangle2D getBounds2D(final TextNode textNode) {
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        final List textRuns = this.getTextRuns(textNode, attributedCharacterIterator);
        Rectangle2D rectangle2D = null;
        for (int i = 0; i < textRuns.size(); ++i) {
            final Rectangle2D bounds2D = textRuns.get(i).getLayout().getBounds2D();
            if (bounds2D != null) {
                if (rectangle2D == null) {
                    rectangle2D = bounds2D;
                }
                else {
                    rectangle2D.add(bounds2D);
                }
            }
        }
        final Shape decorationStrokeOutline = this.getDecorationStrokeOutline(textRuns, 1);
        if (decorationStrokeOutline != null) {
            if (rectangle2D == null) {
                rectangle2D = decorationStrokeOutline.getBounds2D();
            }
            else {
                rectangle2D.add(decorationStrokeOutline.getBounds2D());
            }
        }
        final Shape decorationStrokeOutline2 = this.getDecorationStrokeOutline(textRuns, 2);
        if (decorationStrokeOutline2 != null) {
            if (rectangle2D == null) {
                rectangle2D = decorationStrokeOutline2.getBounds2D();
            }
            else {
                rectangle2D.add(decorationStrokeOutline2.getBounds2D());
            }
        }
        final Shape decorationStrokeOutline3 = this.getDecorationStrokeOutline(textRuns, 4);
        if (decorationStrokeOutline3 != null) {
            if (rectangle2D == null) {
                rectangle2D = decorationStrokeOutline3.getBounds2D();
            }
            else {
                rectangle2D.add(decorationStrokeOutline3.getBounds2D());
            }
        }
        return rectangle2D;
    }
    
    protected Shape getDecorationOutline(final List list, final int n) {
        Path2D path2D = null;
        Paint paint = null;
        Paint paint2 = null;
        Stroke stroke = null;
        Rectangle2D rectangle2D = null;
        double y = 0.0;
        double height = 0.0;
        for (int i = 0; i < list.size(); ++i) {
            final TextRun textRun = list.get(i);
            final AttributedCharacterIterator aci = textRun.getACI();
            aci.first();
            Paint paint3 = null;
            Stroke stroke2 = null;
            Paint paint4 = null;
            final TextPaintInfo textPaintInfo = (TextPaintInfo)aci.getAttribute(StrokingTextPainter.PAINT_INFO);
            if (textPaintInfo != null) {
                switch (n) {
                    case 1: {
                        paint3 = textPaintInfo.underlinePaint;
                        stroke2 = textPaintInfo.underlineStroke;
                        paint4 = textPaintInfo.underlineStrokePaint;
                        break;
                    }
                    case 4: {
                        paint3 = textPaintInfo.overlinePaint;
                        stroke2 = textPaintInfo.overlineStroke;
                        paint4 = textPaintInfo.overlineStrokePaint;
                        break;
                    }
                    case 2: {
                        paint3 = textPaintInfo.strikethroughPaint;
                        stroke2 = textPaintInfo.strikethroughStroke;
                        paint4 = textPaintInfo.strikethroughStrokePaint;
                        break;
                    }
                    default: {
                        return null;
                    }
                }
            }
            if (textRun.isFirstRunInChunk()) {
                final Rectangle2D bounds2D = textRun.getLayout().getDecorationOutline(n).getBounds2D();
                y = bounds2D.getY();
                height = bounds2D.getHeight();
            }
            if ((textRun.isFirstRunInChunk() || paint3 != paint || stroke2 != stroke || paint4 != paint2) && rectangle2D != null) {
                if (path2D == null) {
                    path2D = new GeneralPath(rectangle2D);
                }
                else {
                    path2D.append(rectangle2D, false);
                }
                rectangle2D = null;
            }
            if ((paint3 != null || paint4 != null) && !textRun.getLayout().isVertical() && !textRun.getLayout().isOnATextPath()) {
                final Shape decorationOutline = textRun.getLayout().getDecorationOutline(n);
                if (rectangle2D == null) {
                    final Rectangle2D bounds2D2 = decorationOutline.getBounds2D();
                    rectangle2D = new Rectangle2D.Double(bounds2D2.getX(), y, bounds2D2.getWidth(), height);
                }
                else {
                    final Rectangle2D bounds2D3 = decorationOutline.getBounds2D();
                    final double min = Math.min(rectangle2D.getX(), bounds2D3.getX());
                    rectangle2D.setRect(min, y, Math.max(rectangle2D.getMaxX(), bounds2D3.getMaxX()) - min, height);
                }
            }
            paint = paint3;
            stroke = stroke2;
            paint2 = paint4;
        }
        if (rectangle2D != null) {
            if (path2D == null) {
                path2D = new GeneralPath(rectangle2D);
            }
            else {
                path2D.append(rectangle2D, false);
            }
        }
        return path2D;
    }
    
    protected Shape getDecorationStrokeOutline(final List list, final int n) {
        Path2D path2D = null;
        Paint paint = null;
        Paint paint2 = null;
        Stroke stroke = null;
        Rectangle2D rectangle2D = null;
        double y = 0.0;
        double height = 0.0;
        for (int i = 0; i < list.size(); ++i) {
            final TextRun textRun = list.get(i);
            final AttributedCharacterIterator aci = textRun.getACI();
            aci.first();
            Paint paint3 = null;
            Stroke stroke2 = null;
            Paint paint4 = null;
            final TextPaintInfo textPaintInfo = (TextPaintInfo)aci.getAttribute(StrokingTextPainter.PAINT_INFO);
            if (textPaintInfo != null) {
                switch (n) {
                    case 1: {
                        paint3 = textPaintInfo.underlinePaint;
                        stroke2 = textPaintInfo.underlineStroke;
                        paint4 = textPaintInfo.underlineStrokePaint;
                        break;
                    }
                    case 4: {
                        paint3 = textPaintInfo.overlinePaint;
                        stroke2 = textPaintInfo.overlineStroke;
                        paint4 = textPaintInfo.overlineStrokePaint;
                        break;
                    }
                    case 2: {
                        paint3 = textPaintInfo.strikethroughPaint;
                        stroke2 = textPaintInfo.strikethroughStroke;
                        paint4 = textPaintInfo.strikethroughStrokePaint;
                        break;
                    }
                    default: {
                        return null;
                    }
                }
            }
            if (textRun.isFirstRunInChunk()) {
                final Rectangle2D bounds2D = textRun.getLayout().getDecorationOutline(n).getBounds2D();
                y = bounds2D.getY();
                height = bounds2D.getHeight();
            }
            if ((textRun.isFirstRunInChunk() || paint3 != paint || stroke2 != stroke || paint4 != paint2) && rectangle2D != null) {
                Shape strokedShape = null;
                if (stroke != null && paint2 != null) {
                    strokedShape = stroke.createStrokedShape(rectangle2D);
                }
                else if (paint != null) {
                    strokedShape = rectangle2D;
                }
                if (strokedShape != null) {
                    if (path2D == null) {
                        path2D = new GeneralPath(strokedShape);
                    }
                    else {
                        path2D.append(strokedShape, false);
                    }
                }
                rectangle2D = null;
            }
            if ((paint3 != null || paint4 != null) && !textRun.getLayout().isVertical() && !textRun.getLayout().isOnATextPath()) {
                final Shape decorationOutline = textRun.getLayout().getDecorationOutline(n);
                if (rectangle2D == null) {
                    final Rectangle2D bounds2D2 = decorationOutline.getBounds2D();
                    rectangle2D = new Rectangle2D.Double(bounds2D2.getX(), y, bounds2D2.getWidth(), height);
                }
                else {
                    final Rectangle2D bounds2D3 = decorationOutline.getBounds2D();
                    final double min = Math.min(rectangle2D.getX(), bounds2D3.getX());
                    rectangle2D.setRect(min, y, Math.max(rectangle2D.getMaxX(), bounds2D3.getMaxX()) - min, height);
                }
            }
            paint = paint3;
            stroke = stroke2;
            paint2 = paint4;
        }
        if (rectangle2D != null) {
            Shape strokedShape2 = null;
            if (stroke != null && paint2 != null) {
                strokedShape2 = stroke.createStrokedShape(rectangle2D);
            }
            else if (paint != null) {
                strokedShape2 = rectangle2D;
            }
            if (strokedShape2 != null) {
                if (path2D == null) {
                    path2D = new GeneralPath(strokedShape2);
                }
                else {
                    path2D.append(strokedShape2, false);
                }
            }
        }
        return path2D;
    }
    
    public Mark getMark(final TextNode textNode, final int n, final boolean b) {
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        if (n < attributedCharacterIterator.getBeginIndex() || n > attributedCharacterIterator.getEndIndex()) {
            return null;
        }
        return new BasicMark(textNode, new TextHit(n, b));
    }
    
    protected Mark hitTest(final double x, final double y, final TextNode textNode) {
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        final List textRuns = this.getTextRuns(textNode, attributedCharacterIterator);
        for (int i = 0; i < textRuns.size(); ++i) {
            final TextSpanLayout layout = textRuns.get(i).getLayout();
            final TextHit hitTestChar = layout.hitTestChar((float)x, (float)y);
            if (hitTestChar != null && layout.getBounds2D().contains(x, y)) {
                return new BasicMark(textNode, hitTestChar);
            }
        }
        return null;
    }
    
    public Mark selectFirst(final TextNode textNode) {
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        return new BasicMark(textNode, new TextHit(attributedCharacterIterator.getBeginIndex(), false));
    }
    
    public Mark selectLast(final TextNode textNode) {
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        return new BasicMark(textNode, new TextHit(attributedCharacterIterator.getEndIndex() - 1, false));
    }
    
    public int[] getSelected(final Mark mark, final Mark mark2) {
        if (mark == null || mark2 == null) {
            return null;
        }
        BasicMark basicMark;
        BasicMark basicMark2;
        try {
            basicMark = (BasicMark)mark;
            basicMark2 = (BasicMark)mark2;
        }
        catch (ClassCastException ex) {
            throw new Error("This Mark was not instantiated by this TextPainter class!");
        }
        final TextNode textNode = basicMark.getTextNode();
        if (textNode == null) {
            return null;
        }
        if (textNode != basicMark2.getTextNode()) {
            throw new Error("Markers are from different TextNodes!");
        }
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        final int[] array = { basicMark.getHit().getCharIndex(), basicMark2.getHit().getCharIndex() };
        final Iterator iterator = this.getTextRuns(textNode, attributedCharacterIterator).iterator();
        int glyphIndex = -1;
        int glyphIndex2 = -1;
        TextSpanLayout textSpanLayout = null;
        TextSpanLayout textSpanLayout2 = null;
        while (iterator.hasNext()) {
            final TextSpanLayout layout = iterator.next().getLayout();
            if (glyphIndex == -1) {
                glyphIndex = layout.getGlyphIndex(array[0]);
                if (glyphIndex != -1) {
                    textSpanLayout = layout;
                }
            }
            if (glyphIndex2 == -1) {
                glyphIndex2 = layout.getGlyphIndex(array[1]);
                if (glyphIndex2 != -1) {
                    textSpanLayout2 = layout;
                }
            }
            if (glyphIndex != -1 && glyphIndex2 != -1) {
                break;
            }
        }
        if (textSpanLayout == null || textSpanLayout2 == null) {
            return null;
        }
        final int characterCount = textSpanLayout.getCharacterCount(glyphIndex, glyphIndex);
        final int characterCount2 = textSpanLayout2.getCharacterCount(glyphIndex2, glyphIndex2);
        if (characterCount > 1) {
            if (array[0] > array[1] && textSpanLayout.isLeftToRight()) {
                final int[] array2 = array;
                final int n = 0;
                array2[n] += characterCount - 1;
            }
            else if (array[1] > array[0] && !textSpanLayout.isLeftToRight()) {
                final int[] array3 = array;
                final int n2 = 0;
                array3[n2] -= characterCount - 1;
            }
        }
        if (characterCount2 > 1) {
            if (array[1] > array[0] && textSpanLayout2.isLeftToRight()) {
                final int[] array4 = array;
                final int n3 = 1;
                array4[n3] += characterCount2 - 1;
            }
            else if (array[0] > array[1] && !textSpanLayout2.isLeftToRight()) {
                final int[] array5 = array;
                final int n4 = 1;
                array5[n4] -= characterCount2 - 1;
            }
        }
        return array;
    }
    
    public Shape getHighlightShape(final Mark mark, final Mark mark2) {
        if (mark == null || mark2 == null) {
            return null;
        }
        BasicMark basicMark;
        BasicMark basicMark2;
        try {
            basicMark = (BasicMark)mark;
            basicMark2 = (BasicMark)mark2;
        }
        catch (ClassCastException ex) {
            throw new Error("This Mark was not instantiated by this TextPainter class!");
        }
        final TextNode textNode = basicMark.getTextNode();
        if (textNode == null) {
            return null;
        }
        if (textNode != basicMark2.getTextNode()) {
            throw new Error("Markers are from different TextNodes!");
        }
        final AttributedCharacterIterator attributedCharacterIterator = textNode.getAttributedCharacterIterator();
        if (attributedCharacterIterator == null) {
            return null;
        }
        int charIndex = basicMark.getHit().getCharIndex();
        int charIndex2 = basicMark2.getHit().getCharIndex();
        if (charIndex > charIndex2) {
            final int n = charIndex;
            charIndex = charIndex2;
            charIndex2 = n;
        }
        final List textRuns = this.getTextRuns(textNode, attributedCharacterIterator);
        final GeneralPath generalPath = new GeneralPath();
        for (int i = 0; i < textRuns.size(); ++i) {
            final Shape highlightShape = textRuns.get(i).getLayout().getHighlightShape(charIndex, charIndex2);
            if (highlightShape != null && !highlightShape.getBounds().isEmpty()) {
                generalPath.append(highlightShape, false);
            }
        }
        return generalPath;
    }
    
    static {
        PAINT_INFO = GVTAttributedCharacterIterator.TextAttribute.PAINT_INFO;
        FLOW_REGIONS = GVTAttributedCharacterIterator.TextAttribute.FLOW_REGIONS;
        FLOW_PARAGRAPH = GVTAttributedCharacterIterator.TextAttribute.FLOW_PARAGRAPH;
        TEXT_COMPOUND_ID = GVTAttributedCharacterIterator.TextAttribute.TEXT_COMPOUND_ID;
        GVT_FONT = GVTAttributedCharacterIterator.TextAttribute.GVT_FONT;
        GVT_FONTS = GVTAttributedCharacterIterator.TextAttribute.GVT_FONTS;
        BIDI_LEVEL = GVTAttributedCharacterIterator.TextAttribute.BIDI_LEVEL;
        XPOS = GVTAttributedCharacterIterator.TextAttribute.X;
        YPOS = GVTAttributedCharacterIterator.TextAttribute.Y;
        TEXTPATH = GVTAttributedCharacterIterator.TextAttribute.TEXTPATH;
        WRITING_MODE = GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE;
        WRITING_MODE_TTB = GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE_TTB;
        WRITING_MODE_RTL = GVTAttributedCharacterIterator.TextAttribute.WRITING_MODE_RTL;
        ANCHOR_TYPE = GVTAttributedCharacterIterator.TextAttribute.ANCHOR_TYPE;
        ADJUST_SPACING = GVTAttributedCharacterIterator.TextAttribute.ADJUST_SPACING;
        ADJUST_ALL = GVTAttributedCharacterIterator.TextAttribute.ADJUST_ALL;
        ALT_GLYPH_HANDLER = GVTAttributedCharacterIterator.TextAttribute.ALT_GLYPH_HANDLER;
        (StrokingTextPainter.extendedAtts = new HashSet()).add(StrokingTextPainter.FLOW_PARAGRAPH);
        StrokingTextPainter.extendedAtts.add(StrokingTextPainter.TEXT_COMPOUND_ID);
        StrokingTextPainter.extendedAtts.add(StrokingTextPainter.GVT_FONT);
        StrokingTextPainter.singleton = new StrokingTextPainter();
    }
    
    public class TextRun
    {
        protected AttributedCharacterIterator aci;
        protected TextSpanLayout layout;
        protected int anchorType;
        protected boolean firstRunInChunk;
        protected Float length;
        protected Integer lengthAdjust;
        
        public TextRun(final TextSpanLayout layout, final AttributedCharacterIterator aci, final boolean firstRunInChunk) {
            this.layout = layout;
            (this.aci = aci).first();
            this.firstRunInChunk = firstRunInChunk;
            this.anchorType = 0;
            final TextNode.Anchor anchor = (TextNode.Anchor)aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.ANCHOR_TYPE);
            if (anchor != null) {
                this.anchorType = anchor.getType();
            }
            if (aci.getAttribute(StrokingTextPainter.WRITING_MODE) == StrokingTextPainter.WRITING_MODE_RTL) {
                if (this.anchorType == 0) {
                    this.anchorType = 2;
                }
                else if (this.anchorType == 2) {
                    this.anchorType = 0;
                }
            }
            this.length = (Float)aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.BBOX_WIDTH);
            this.lengthAdjust = (Integer)aci.getAttribute(GVTAttributedCharacterIterator.TextAttribute.LENGTH_ADJUST);
        }
        
        public AttributedCharacterIterator getACI() {
            return this.aci;
        }
        
        public TextSpanLayout getLayout() {
            return this.layout;
        }
        
        public int getAnchorType() {
            return this.anchorType;
        }
        
        public Float getLength() {
            return this.length;
        }
        
        public Integer getLengthAdjust() {
            return this.lengthAdjust;
        }
        
        public boolean isFirstRunInChunk() {
            return this.firstRunInChunk;
        }
    }
    
    class TextChunk
    {
        public int begin;
        public int end;
        public Point2D advance;
        
        public TextChunk(final int begin, final int end, final Point2D point2D) {
            this.begin = begin;
            this.end = end;
            this.advance = new Point2D.Float((float)point2D.getX(), (float)point2D.getY());
        }
    }
}
