// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import java.util.Iterator;
import org.apache.batik.gvt.font.MultiGlyphVector;
import java.util.LinkedList;
import org.apache.batik.gvt.font.GVTGlyphVector;
import java.util.List;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.text.AttributedCharacterIterator;
import org.apache.batik.gvt.text.GlyphLayout;

public class FlowExtGlyphLayout extends GlyphLayout
{
    public FlowExtGlyphLayout(final AttributedCharacterIterator attributedCharacterIterator, final int[] array, final Point2D point2D, final FontRenderContext fontRenderContext) {
        super(attributedCharacterIterator, array, point2D, fontRenderContext);
    }
    
    public static void textWrapTextChunk(final AttributedCharacterIterator[] array, final List list, final List list2) {
        final GVTGlyphVector[] array2 = new GVTGlyphVector[array.length];
        final List[] array3 = new List[array.length];
        final GlyphIterator[] array4 = new GlyphIterator[array.length];
        final Iterator<List<GlyphLayout>> iterator = list.iterator();
        final Iterator<RegionInfo> iterator2 = list2.iterator();
        RegionInfo regionInfo = null;
        float n = 0.0f;
        if (iterator2.hasNext()) {
            regionInfo = iterator2.next();
            n = (float)regionInfo.getHeight();
        }
        final boolean b = true;
        final float n2 = 1.0f;
        float n3 = 0.0f;
        float n4 = 0.0f;
        Point2D.Float float1 = new Point2D.Float(0.0f, 0.0f);
        float n5 = 0.0f;
        int n6 = 0;
        while (iterator.hasNext()) {
            final AttributedCharacterIterator attributedCharacterIterator = array[n6];
            if (regionInfo != null) {
                final List list3 = (List)attributedCharacterIterator.getAttribute(FlowExtGlyphLayout.FLOW_EMPTY_PARAGRAPH);
                if (list3 != null) {
                    for (final MarginInfo marginInfo : list3) {
                        final float n7 = (n5 > marginInfo.getTopMargin()) ? n5 : marginInfo.getTopMargin();
                        if (n4 + n7 <= n && !marginInfo.isFlowRegionBreak()) {
                            n4 += n7;
                            n5 = marginInfo.getBottomMargin();
                        }
                        else {
                            if (!iterator2.hasNext()) {
                                regionInfo = null;
                                break;
                            }
                            regionInfo = iterator2.next();
                            n = (float)regionInfo.getHeight();
                            float1 = new Point2D.Float(0.0f, 0.0f);
                            n4 = 0.0f;
                            n5 = 0.0f;
                        }
                    }
                    if (regionInfo == null) {
                        break;
                    }
                }
            }
            final LinkedList<GVTGlyphVector> list4 = new LinkedList<GVTGlyphVector>();
            final List<GlyphLayout> list5 = iterator.next();
            final Iterator<GlyphLayout> iterator4 = list5.iterator();
            while (iterator4.hasNext()) {
                list4.add(iterator4.next().getGlyphVector());
            }
            final MultiGlyphVector multiGlyphVector = new MultiGlyphVector(list4);
            array2[n6] = multiGlyphVector;
            final int numGlyphs = multiGlyphVector.getNumGlyphs();
            attributedCharacterIterator.first();
            final MarginInfo marginInfo2 = (MarginInfo)attributedCharacterIterator.getAttribute(FlowExtGlyphLayout.FLOW_PARAGRAPH);
            if (marginInfo2 != null) {
                if (regionInfo == null) {
                    for (int i = 0; i < numGlyphs; ++i) {
                        multiGlyphVector.setGlyphVisible(i, false);
                    }
                }
                else {
                    final float n8 = (n5 > marginInfo2.getTopMargin()) ? n5 : marginInfo2.getTopMargin();
                    float topMargin;
                    if (n4 + n8 <= n) {
                        topMargin = n4 + n8;
                    }
                    else {
                        if (!iterator2.hasNext()) {
                            break;
                        }
                        regionInfo = iterator2.next();
                        final float n9 = (float)regionInfo.getHeight();
                        float1 = new Point2D.Float(0.0f, 0.0f);
                        topMargin = marginInfo2.getTopMargin();
                    }
                    n5 = marginInfo2.getBottomMargin();
                    float n10 = marginInfo2.getLeftMargin();
                    float n11 = marginInfo2.getRightMargin();
                    if (list5.get(0).isLeftToRight()) {
                        n10 += marginInfo2.getIndent();
                    }
                    else {
                        n11 += marginInfo2.getIndent();
                    }
                    float n12 = (float)regionInfo.getX() + n10;
                    float n13 = (float)regionInfo.getY();
                    float n14 = (float)(regionInfo.getWidth() - (n10 + n11));
                    n = (float)regionInfo.getHeight();
                    final LinkedList<LineInfo> list6 = new LinkedList<LineInfo>();
                    array3[n6] = list6;
                    float n15 = 0.0f;
                    GlyphIterator glyphIterator = new GlyphIterator(attributedCharacterIterator, multiGlyphVector);
                    array4[n6] = glyphIterator;
                    GlyphIterator glyphIterator2 = null;
                    GlyphIterator copy = null;
                    if (!glyphIterator.done() && !glyphIterator.isPrinting()) {
                        updateVerticalAlignOffset(float1, regionInfo, topMargin);
                        list6.add(glyphIterator.newLine(new Point2D.Float(n12, n13 + topMargin), n14, true, float1));
                    }
                    GlyphIterator glyphIterator3 = glyphIterator.copy();
                    int n16 = 1;
                    while (!glyphIterator.done()) {
                        int n17 = 0;
                        boolean b2 = false;
                        if (glyphIterator.isPrinting() && glyphIterator.getAdv() > n14) {
                            if (glyphIterator2 == null) {
                                if (!iterator2.hasNext()) {
                                    regionInfo = null;
                                    glyphIterator = glyphIterator3.copy(glyphIterator);
                                    break;
                                }
                                regionInfo = iterator2.next();
                                n12 = (float)regionInfo.getX() + n10;
                                n13 = (float)regionInfo.getY();
                                n14 = (float)(regionInfo.getWidth() - (n10 + n11));
                                n = (float)regionInfo.getHeight();
                                float1 = new Point2D.Float(0.0f, 0.0f);
                                topMargin = ((n16 != 0) ? marginInfo2.getTopMargin() : 0.0f);
                                n15 = 0.0f;
                                glyphIterator = glyphIterator3.copy(glyphIterator);
                                continue;
                            }
                            else {
                                glyphIterator = glyphIterator2.copy(glyphIterator);
                                n3 = 1.0f;
                                n17 = 1;
                                b2 = false;
                            }
                        }
                        else if (glyphIterator.isLastChar()) {
                            n3 = 1.0f;
                            n17 = 1;
                            b2 = true;
                        }
                        final int lineBreaks = glyphIterator.getLineBreaks();
                        if (lineBreaks != 0) {
                            if (n17 != 0) {
                                --n3;
                            }
                            n3 += lineBreaks;
                            n17 = 1;
                            b2 = true;
                        }
                        if (n17 == 0) {
                            if (glyphIterator.isBreakChar() || glyphIterator2 == null || !glyphIterator2.isBreakChar()) {
                                copy = glyphIterator.copy(copy);
                                glyphIterator.nextChar();
                                if (glyphIterator.getChar() == '\u200d') {
                                    continue;
                                }
                                final GlyphIterator glyphIterator4 = glyphIterator2;
                                glyphIterator2 = copy;
                                copy = glyphIterator4;
                            }
                            else {
                                glyphIterator.nextChar();
                            }
                        }
                        else {
                            final float n18 = glyphIterator.getMaxAscent() + glyphIterator.getMaxDescent();
                            float n19;
                            if (b) {
                                n19 = glyphIterator.getMaxFontSize() * n2;
                            }
                            else {
                                n19 = n2;
                            }
                            final float n20 = (n19 - n18) / 2.0f;
                            final float n21 = n15 + n20 + glyphIterator.getMaxAscent();
                            final float n22 = n20 + glyphIterator.getMaxDescent();
                            topMargin += n21;
                            float maxDescent = n22;
                            if (n22 < glyphIterator.getMaxDescent()) {
                                maxDescent = glyphIterator.getMaxDescent();
                            }
                            if (topMargin + maxDescent > n) {
                                if (!iterator2.hasNext()) {
                                    regionInfo = null;
                                    glyphIterator = glyphIterator3.copy(glyphIterator);
                                    break;
                                }
                                final float n23 = n14;
                                regionInfo = iterator2.next();
                                n12 = (float)regionInfo.getX() + n10;
                                n13 = (float)regionInfo.getY();
                                n14 = (float)(regionInfo.getWidth() - (n10 + n11));
                                n = (float)regionInfo.getHeight();
                                float1 = new Point2D.Float(0.0f, 0.0f);
                                topMargin = ((n16 != 0) ? marginInfo2.getTopMargin() : 0.0f);
                                n15 = 0.0f;
                                if (n23 <= n14 && lineBreaks == 0) {
                                    continue;
                                }
                                glyphIterator = glyphIterator3.copy(glyphIterator);
                            }
                            else {
                                n15 = n22 + (n3 - 1.0f) * n19;
                                n3 = 0.0f;
                                updateVerticalAlignOffset(float1, regionInfo, topMargin + maxDescent);
                                list6.add(glyphIterator.newLine(new Point2D.Float(n12, n13 + topMargin), n14, b2, float1));
                                final float n24 = n12 - n10;
                                final float n25 = n14 + (n10 + n11);
                                n10 = marginInfo2.getLeftMargin();
                                n11 = marginInfo2.getRightMargin();
                                n12 = n24 + n10;
                                n14 = n25 - (n10 + n11);
                                n16 = 0;
                                glyphIterator3 = glyphIterator.copy(glyphIterator3);
                                glyphIterator2 = null;
                            }
                        }
                    }
                    n4 = topMargin + n15;
                    int j = glyphIterator.getGlyphIndex();
                    while (j < numGlyphs) {
                        multiGlyphVector.setGlyphVisible(j++, false);
                    }
                    if (marginInfo2.isFlowRegionBreak()) {
                        regionInfo = null;
                        if (iterator2.hasNext()) {
                            regionInfo = iterator2.next();
                            n = (float)regionInfo.getHeight();
                            n4 = 0.0f;
                            n5 = 0.0f;
                            float1 = new Point2D.Float(0.0f, 0.0f);
                        }
                    }
                }
            }
            ++n6;
        }
        for (int k = 0; k < array.length; ++k) {
            final List list7 = array3[k];
            if (list7 != null) {
                final AttributedCharacterIterator attributedCharacterIterator2 = array[k];
                attributedCharacterIterator2.first();
                final MarginInfo marginInfo3 = (MarginInfo)attributedCharacterIterator2.getAttribute(FlowExtGlyphLayout.FLOW_PARAGRAPH);
                if (marginInfo3 != null) {
                    final int justification = marginInfo3.getJustification();
                    final GVTGlyphVector gvtGlyphVector = array2[k];
                    if (gvtGlyphVector == null) {
                        break;
                    }
                    layoutChunk(gvtGlyphVector, array4[k].getOrigin(), justification, list7);
                }
            }
        }
    }
    
    public static void updateVerticalAlignOffset(final Point2D.Float float1, final RegionInfo regionInfo, final float n) {
        float1.setLocation(0.0f, regionInfo.getVerticalAlignment() * ((float)regionInfo.getHeight() - n));
    }
    
    public static void layoutChunk(final GVTGlyphVector gvtGlyphVector, final Point2D point2D, final int n, final List list) {
        final Iterator<LineInfo> iterator = list.iterator();
        final int numGlyphs = gvtGlyphVector.getNumGlyphs();
        final float[] glyphPositions = gvtGlyphVector.getGlyphPositions(0, numGlyphs + 1, null);
        Point2D.Float location = null;
        float advance = 0.0f;
        float x = (float)point2D.getX();
        final float y = (float)point2D.getY();
        float n2 = 1.0f;
        float n3 = 0.0f;
        float y2 = 0.0f;
        int endIdx = 0;
        final Point2D.Float float1 = new Point2D.Float();
        int i;
        for (i = 0; i < numGlyphs; ++i) {
            if (i == endIdx) {
                x += advance;
                if (!iterator.hasNext()) {
                    break;
                }
                final LineInfo lineInfo = iterator.next();
                endIdx = lineInfo.getEndIdx();
                location = lineInfo.getLocation();
                advance = lineInfo.getAdvance();
                final float visualAdvance = lineInfo.getVisualAdvance();
                final float lastCharWidth = lineInfo.getLastCharWidth();
                final float lineWidth = lineInfo.getLineWidth();
                final boolean partialLine = lineInfo.isPartialLine();
                y2 = lineInfo.getVerticalAlignOffset().y;
                n3 = 0.0f;
                n2 = 1.0f;
                switch (n) {
                    case 1: {
                        n3 = (lineWidth - visualAdvance) / 2.0f;
                        break;
                    }
                    case 2: {
                        n3 = lineWidth - visualAdvance;
                        break;
                    }
                    case 3: {
                        if (!partialLine && endIdx != i + 1) {
                            n2 = (lineWidth - lastCharWidth) / (visualAdvance - lastCharWidth);
                            break;
                        }
                        break;
                    }
                }
            }
            float1.x = location.x + (glyphPositions[2 * i] - x) * n2 + n3;
            float1.y = location.y + (glyphPositions[2 * i + 1] - y + y2);
            gvtGlyphVector.setGlyphPosition(i, float1);
        }
        float1.x = x;
        float1.y = y;
        if (location != null) {
            float1.x = location.x + (glyphPositions[2 * i] - x) * n2 + n3;
            float1.y = location.y + (glyphPositions[2 * i + 1] - y) + y2;
        }
        gvtGlyphVector.setGlyphPosition(i, float1);
    }
}
