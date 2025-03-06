// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.flow;

import java.awt.font.TextAttribute;
import java.util.HashSet;
import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import java.text.CharacterIterator;
import org.apache.batik.gvt.font.GVTFont;
import java.util.Arrays;
import org.apache.batik.gvt.font.MultiGlyphVector;
import org.apache.batik.gvt.text.GlyphLayout;
import java.util.LinkedList;
import org.apache.batik.gvt.font.GVTGlyphVector;
import java.awt.font.FontRenderContext;
import java.util.Iterator;
import org.apache.batik.gvt.text.TextSpanLayout;
import java.util.ArrayList;
import java.util.List;
import org.apache.batik.gvt.TextNode;
import java.util.Set;
import java.text.AttributedCharacterIterator;
import org.apache.batik.gvt.TextPainter;
import org.apache.batik.gvt.renderer.StrokingTextPainter;

public class FlowTextPainter extends StrokingTextPainter
{
    protected static TextPainter singleton;
    public static final char SOFT_HYPHEN = '\u00ad';
    public static final char ZERO_WIDTH_SPACE = '\u200b';
    public static final char ZERO_WIDTH_JOINER = '\u200d';
    public static final char SPACE = ' ';
    public static final AttributedCharacterIterator.Attribute WORD_LIMIT;
    public static final AttributedCharacterIterator.Attribute FLOW_REGIONS;
    public static final AttributedCharacterIterator.Attribute FLOW_LINE_BREAK;
    public static final AttributedCharacterIterator.Attribute LINE_HEIGHT;
    public static final AttributedCharacterIterator.Attribute GVT_FONT;
    protected static Set szAtts;
    
    public static TextPainter getInstance() {
        return FlowTextPainter.singleton;
    }
    
    public List getTextRuns(final TextNode textNode, final AttributedCharacterIterator attributedCharacterIterator) {
        final List textRuns = textNode.getTextRuns();
        if (textRuns != null) {
            return textRuns;
        }
        final AttributedCharacterIterator[] textChunkACIs = this.getTextChunkACIs(attributedCharacterIterator);
        final List computeTextRuns = this.computeTextRuns(textNode, attributedCharacterIterator, textChunkACIs);
        attributedCharacterIterator.first();
        final List list = (List)attributedCharacterIterator.getAttribute(FlowTextPainter.FLOW_REGIONS);
        if (list != null) {
            final Iterator<TextRun> iterator = computeTextRuns.iterator();
            final ArrayList<ArrayList<TextSpanLayout>> list2 = new ArrayList<ArrayList<TextSpanLayout>>();
            final TextRun textRun = iterator.next();
            ArrayList<TextSpanLayout> list3 = new ArrayList<TextSpanLayout>();
            list2.add(list3);
            list3.add(textRun.getLayout());
            while (iterator.hasNext()) {
                final TextRun textRun2 = iterator.next();
                if (textRun2.isFirstRunInChunk()) {
                    list3 = new ArrayList<TextSpanLayout>();
                    list2.add(list3);
                }
                list3.add(textRun2.getLayout());
            }
            textWrap(textChunkACIs, list2, list, this.fontRenderContext);
        }
        textNode.setTextRuns(computeTextRuns);
        return textNode.getTextRuns();
    }
    
    public static boolean textWrap(final AttributedCharacterIterator[] array, final List list, final List list2, final FontRenderContext fontRenderContext) {
        final GVTGlyphVector[] array2 = new GVTGlyphVector[array.length];
        final WordInfo[][] array3 = new WordInfo[array.length][];
        final Iterator<List<GlyphLayout>> iterator = list.iterator();
        float bottomMargin = 0.0f;
        int n = 0;
        final BlockInfo[] array4 = new BlockInfo[array.length];
        final float[] array5 = new float[array.length];
        int n2 = 0;
        while (iterator.hasNext()) {
            final AttributedCharacterIterator attributedCharacterIterator = array[n2];
            final LinkedList<GVTGlyphVector> list3 = new LinkedList<GVTGlyphVector>();
            final Iterator<GlyphLayout> iterator2 = iterator.next().iterator();
            while (iterator2.hasNext()) {
                list3.add(iterator2.next().getGlyphVector());
            }
            final MultiGlyphVector multiGlyphVector = new MultiGlyphVector(list3);
            array2[n2] = multiGlyphVector;
            array3[n2] = doWordAnalysis(multiGlyphVector, attributedCharacterIterator, n, fontRenderContext);
            attributedCharacterIterator.first();
            final BlockInfo blockInfo = (BlockInfo)attributedCharacterIterator.getAttribute(FlowTextPainter.FLOW_PARAGRAPH);
            blockInfo.initLineInfo(fontRenderContext);
            array4[n2] = blockInfo;
            if (bottomMargin > blockInfo.getTopMargin()) {
                array5[n2] = bottomMargin;
            }
            else {
                array5[n2] = blockInfo.getTopMargin();
            }
            bottomMargin = blockInfo.getBottomMargin();
            n += array3[n2].length;
            ++n2;
        }
        final Iterator<RegionInfo> iterator3 = list2.iterator();
        int i = 0;
        int j = 0;
        final LinkedList<LineInfo> list4 = new LinkedList<LineInfo>();
        while (iterator3.hasNext()) {
            final FlowRegions flowRegions = new FlowRegions(iterator3.next().getShape());
            while (j < array3.length) {
                final WordInfo[] array6 = array3[j];
                final BlockInfo blockInfo2 = array4[j];
                final WordInfo wordInfo = array6[i];
                Object o = wordInfo.getFlowLine();
                double n3 = Math.max(wordInfo.getLineHeight(), blockInfo2.getLineHeight());
                LineInfo lineInfo = new LineInfo(flowRegions, blockInfo2, true);
                final double n4 = lineInfo.getCurrentY() + array5[j];
                array5[j] = 0.0f;
                if (lineInfo.gotoY(n4)) {
                    break;
                }
                while (!lineInfo.addWord(wordInfo) && !lineInfo.gotoY(lineInfo.getCurrentY() + n3 * 0.1)) {}
                if (flowRegions.done()) {
                    break;
                }
                ++i;
                while (i < array6.length) {
                    final WordInfo wordInfo2 = array6[i];
                    if (wordInfo2.getFlowLine() != o || !lineInfo.addWord(wordInfo2)) {
                        lineInfo.layout();
                        list4.add(lineInfo);
                        lineInfo = null;
                        o = wordInfo2.getFlowLine();
                        n3 = Math.max(wordInfo2.getLineHeight(), blockInfo2.getLineHeight());
                        if (!flowRegions.newLine(n3)) {
                            break;
                        }
                        lineInfo = new LineInfo(flowRegions, blockInfo2, false);
                        while (!lineInfo.addWord(wordInfo2) && !lineInfo.gotoY(lineInfo.getCurrentY() + n3 * 0.1)) {}
                        if (flowRegions.done()) {
                            break;
                        }
                    }
                    ++i;
                }
                if (lineInfo != null) {
                    lineInfo.setParaEnd(true);
                    lineInfo.layout();
                }
                if (flowRegions.done()) {
                    break;
                }
                ++j;
                i = 0;
                if (blockInfo2.isFlowRegionBreak()) {
                    break;
                }
                if (!flowRegions.newLine(n3)) {
                    break;
                }
            }
            if (j == array3.length) {
                break;
            }
        }
        final boolean b = j < array3.length;
        while (j < array3.length) {
            for (WordInfo[] array7 = array3[j]; i < array7.length; ++i) {
                final WordInfo wordInfo3 = array7[i];
                for (int numGlyphGroups = wordInfo3.getNumGlyphGroups(), k = 0; k < numGlyphGroups; ++k) {
                    final GlyphGroupInfo glyphGroup = wordInfo3.getGlyphGroup(k);
                    final GVTGlyphVector glyphVector = glyphGroup.getGlyphVector();
                    for (int end = glyphGroup.getEnd(), l = glyphGroup.getStart(); l <= end; ++l) {
                        glyphVector.setGlyphVisible(l, false);
                    }
                }
            }
            ++j;
            i = 0;
        }
        return b;
    }
    
    static int[] allocWordMap(final int[] array, int toIndex) {
        if (array != null) {
            if (toIndex <= array.length) {
                return array;
            }
            if (toIndex < array.length * 2) {
                toIndex = array.length * 2;
            }
        }
        final int[] a = new int[toIndex];
        int fromIndex = (array != null) ? array.length : 0;
        if (toIndex < fromIndex) {
            fromIndex = toIndex;
        }
        if (fromIndex != 0) {
            System.arraycopy(array, 0, a, 0, fromIndex);
        }
        Arrays.fill(a, fromIndex, toIndex, -1);
        return a;
    }
    
    static WordInfo[] doWordAnalysis(final GVTGlyphVector gvtGlyphVector, final AttributedCharacterIterator attributedCharacterIterator, final int n, final FontRenderContext fontRenderContext) {
        final int numGlyphs = gvtGlyphVector.getNumGlyphs();
        final int[] array = new int[numGlyphs];
        int[] array2 = allocWordMap(null, 10);
        int n2 = 0;
        int beginIndex = attributedCharacterIterator.getBeginIndex();
        for (int i = 0; i < numGlyphs; ++i) {
            final int characterCount = gvtGlyphVector.getCharacterCount(i, i);
            attributedCharacterIterator.setIndex(beginIndex);
            int n3 = (int)attributedCharacterIterator.getAttribute(FlowTextPainter.WORD_LIMIT) - n;
            if (n3 > n2) {
                n2 = n3;
                array2 = allocWordMap(array2, n2 + 1);
            }
            ++beginIndex;
            for (int j = 1; j < characterCount; ++j) {
                attributedCharacterIterator.setIndex(beginIndex);
                final int n4 = (int)attributedCharacterIterator.getAttribute(FlowTextPainter.WORD_LIMIT) - n;
                if (n4 > n2) {
                    n2 = n4;
                    array2 = allocWordMap(array2, n2 + 1);
                }
                if (n4 < n3) {
                    array2[n3] = n4;
                    n3 = n4;
                }
                else if (n4 > n3) {
                    array2[n4] = n3;
                }
                ++beginIndex;
            }
            array[i] = n3;
        }
        int n5 = 0;
        final WordInfo[] array3 = new WordInfo[n2 + 1];
        for (int k = 0; k <= n2; ++k) {
            final int n6 = array2[k];
            if (n6 == -1) {
                array3[k] = new WordInfo(n5++);
            }
            else {
                int n7 = n6;
                for (int l = array2[k]; l != -1; l = array2[n7]) {
                    n7 = l;
                }
                array2[k] = n7;
                array3[k] = array3[n7];
            }
        }
        final WordInfo[] array4 = new WordInfo[n5];
        for (int n8 = 0; n8 <= n2; ++n8) {
            array4[array3[n8].getIndex()] = array3[n8];
        }
        int beginIndex2 = attributedCharacterIterator.getBeginIndex();
        final int endIndex = attributedCharacterIterator.getEndIndex();
        char c = attributedCharacterIterator.setIndex(beginIndex2);
        int n9 = beginIndex2;
        GVTFont gvtFont = (GVTFont)attributedCharacterIterator.getAttribute(FlowTextPainter.GVT_FONT);
        float n10 = 1.0f;
        final Float n11 = (Float)attributedCharacterIterator.getAttribute(FlowTextPainter.LINE_HEIGHT);
        if (n11 != null) {
            n10 = n11;
        }
        int n12 = attributedCharacterIterator.getRunLimit(FlowTextPainter.szAtts);
        WordInfo wordInfo = null;
        final float[] array5 = new float[numGlyphs];
        final float[] array6 = new float[numGlyphs];
        final boolean[] array7 = new boolean[numGlyphs];
        final boolean[] array8 = new boolean[numGlyphs];
        final boolean[] array9 = new boolean[numGlyphs];
        final float[] glyphPositions = gvtGlyphVector.getGlyphPositions(0, numGlyphs + 1, null);
        for (int n13 = 0; n13 < numGlyphs; ++n13) {
            final char c2 = c;
            c = attributedCharacterIterator.setIndex(beginIndex2);
            final WordInfo wordInfo2 = array3[(int)attributedCharacterIterator.getAttribute(FlowTextPainter.WORD_LIMIT) - n];
            if (wordInfo2.getFlowLine() == null) {
                wordInfo2.setFlowLine(attributedCharacterIterator.getAttribute(FlowTextPainter.FLOW_LINE_BREAK));
            }
            if (wordInfo == null) {
                wordInfo = wordInfo2;
            }
            else if (wordInfo != wordInfo2) {
                wordInfo.addLineMetrics(gvtFont, gvtFont.getLineMetrics(attributedCharacterIterator, n9, beginIndex2, fontRenderContext));
                wordInfo.addLineHeight(n10);
                n9 = beginIndex2;
                wordInfo = wordInfo2;
            }
            final int characterCount2 = gvtGlyphVector.getCharacterCount(n13, n13);
            if (characterCount2 == 1) {
                switch (c) {
                    case 173: {
                        array7[n13] = true;
                        final char next = attributedCharacterIterator.next();
                        attributedCharacterIterator.previous();
                        array6[n13] = -(glyphPositions[2 * n13 + 2] - glyphPositions[2 * n13] + gvtFont.getHKern(c2, next));
                        break;
                    }
                    case 8205: {
                        array8[n13] = true;
                        break;
                    }
                    case 8203: {
                        array8[n13] = true;
                        break;
                    }
                    case 32: {
                        array9[n13] = true;
                        final char next2 = attributedCharacterIterator.next();
                        attributedCharacterIterator.previous();
                        array5[n13] = -(glyphPositions[2 * n13 + 2] - glyphPositions[2 * n13] + gvtFont.getHKern(c2, next2));
                        break;
                    }
                }
            }
            beginIndex2 += characterCount2;
            if (beginIndex2 > n12 && beginIndex2 < endIndex) {
                wordInfo.addLineMetrics(gvtFont, gvtFont.getLineMetrics(attributedCharacterIterator, n9, n12, fontRenderContext));
                wordInfo.addLineHeight(n10);
                wordInfo = null;
                n9 = beginIndex2;
                attributedCharacterIterator.setIndex(beginIndex2);
                gvtFont = (GVTFont)attributedCharacterIterator.getAttribute(FlowTextPainter.GVT_FONT);
                n10 = (float)attributedCharacterIterator.getAttribute(FlowTextPainter.LINE_HEIGHT);
                n12 = attributedCharacterIterator.getRunLimit(FlowTextPainter.szAtts);
            }
        }
        wordInfo.addLineMetrics(gvtFont, gvtFont.getLineMetrics(attributedCharacterIterator, n9, n12, fontRenderContext));
        wordInfo.addLineHeight(n10);
        final int[] array10 = new int[n5];
        for (int n14 = 0; n14 < numGlyphs; ++n14) {
            final int index = array3[array[n14]].getIndex();
            array[n14] = index;
            final int[] array11 = array10;
            final int n15 = index;
            ++array11[n15];
        }
        final int[][] array12 = new int[n5][];
        final int[] array13 = new int[n5];
        for (int n16 = 0; n16 < numGlyphs; ++n16) {
            final int n17 = array[n16];
            int[] array14 = array12[n17];
            if (array14 == null) {
                final int[][] array15 = array12;
                final int n18 = n17;
                final int[] array16 = new int[array10[n17]];
                array15[n18] = array16;
                array14 = array16;
                array10[n17] = 0;
            }
            final int n19 = array10[n17];
            array14[n19] = n16;
            if (n19 == 0) {
                final int[] array17 = array13;
                final int n20 = n17;
                ++array17[n20];
            }
            else if (array14[n19 - 1] != n16 - 1) {
                final int[] array18 = array13;
                final int n21 = n17;
                ++array18[n21];
            }
            final int[] array19 = array10;
            final int n22 = n17;
            ++array19[n22];
        }
        for (int n23 = 0; n23 < n5; ++n23) {
            final int n24 = array13[n23];
            final GlyphGroupInfo[] glyphGroups = new GlyphGroupInfo[n24];
            if (n24 == 1) {
                final int[] array20 = array12[n23];
                final int n25 = array20[0];
                final int n26 = array20[array20.length - 1];
                glyphGroups[0] = new GlyphGroupInfo(gvtGlyphVector, n25, n26, array8, array7[n26], glyphPositions, array6, array5, array9);
            }
            else {
                int n27 = 0;
                final int[] array21 = array12[n23];
                int n29;
                int n28 = n29 = array21[0];
                for (int n30 = 1; n30 < array21.length; ++n30) {
                    if (n28 + 1 != array21[n30]) {
                        final int n31 = array21[n30 - 1];
                        glyphGroups[n27] = new GlyphGroupInfo(gvtGlyphVector, n29, n31, array8, array7[n31], glyphPositions, array6, array5, array9);
                        n29 = array21[n30];
                        ++n27;
                    }
                    n28 = array21[n30];
                }
                final int n32 = array21[array21.length - 1];
                glyphGroups[n27] = new GlyphGroupInfo(gvtGlyphVector, n29, n32, array8, array7[n32], glyphPositions, array6, array5, array9);
            }
            array4[n23].setGlyphGroups(glyphGroups);
        }
        return array4;
    }
    
    static {
        FlowTextPainter.singleton = new FlowTextPainter();
        WORD_LIMIT = TextLineBreaks.WORD_LIMIT;
        FLOW_REGIONS = GVTAttributedCharacterIterator.TextAttribute.FLOW_REGIONS;
        FLOW_LINE_BREAK = GVTAttributedCharacterIterator.TextAttribute.FLOW_LINE_BREAK;
        LINE_HEIGHT = GVTAttributedCharacterIterator.TextAttribute.LINE_HEIGHT;
        GVT_FONT = GVTAttributedCharacterIterator.TextAttribute.GVT_FONT;
        (FlowTextPainter.szAtts = new HashSet()).add(TextAttribute.SIZE);
        FlowTextPainter.szAtts.add(FlowTextPainter.GVT_FONT);
        FlowTextPainter.szAtts.add(FlowTextPainter.LINE_HEIGHT);
    }
}
