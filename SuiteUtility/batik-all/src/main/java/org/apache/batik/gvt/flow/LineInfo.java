// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.flow;

import java.awt.geom.Point2D;
import org.apache.batik.gvt.font.GVTGlyphVector;

public class LineInfo
{
    FlowRegions fr;
    double lineHeight;
    double ascent;
    double descent;
    double hLeading;
    double baseline;
    int numGlyphs;
    int words;
    int size;
    GlyphGroupInfo[] ggis;
    int newSize;
    GlyphGroupInfo[] newGGIS;
    int numRanges;
    double[] ranges;
    double[] rangeAdv;
    BlockInfo bi;
    boolean paraStart;
    boolean paraEnd;
    protected static final int FULL_WORD = 0;
    protected static final int FULL_ADV = 1;
    static final float MAX_COMPRESS = 0.1f;
    static final float COMRESS_SCALE = 3.0f;
    
    public LineInfo(final FlowRegions fr, final BlockInfo bi, final boolean paraStart) {
        this.lineHeight = -1.0;
        this.ascent = -1.0;
        this.descent = -1.0;
        this.hLeading = -1.0;
        this.words = 0;
        this.size = 0;
        this.ggis = null;
        this.newSize = 0;
        this.newGGIS = null;
        this.bi = null;
        this.fr = fr;
        this.bi = bi;
        this.lineHeight = bi.getLineHeight();
        this.ascent = bi.getAscent();
        this.descent = bi.getDescent();
        this.hLeading = (this.lineHeight - (this.ascent + this.descent)) / 2.0;
        this.baseline = (float)(fr.getCurrentY() + this.hLeading + this.ascent);
        this.paraStart = paraStart;
        this.paraEnd = false;
        if (this.lineHeight > 0.0) {
            fr.newLineHeight(this.lineHeight);
            this.updateRangeInfo();
        }
    }
    
    public void setParaEnd(final boolean paraEnd) {
        this.paraEnd = paraEnd;
    }
    
    public boolean addWord(final WordInfo wordInfo) {
        final double lineHeight = wordInfo.getLineHeight();
        if (lineHeight <= this.lineHeight) {
            return this.insertWord(wordInfo);
        }
        this.fr.newLineHeight(lineHeight);
        if (!this.updateRangeInfo()) {
            if (this.lineHeight > 0.0) {
                this.fr.newLineHeight(this.lineHeight);
            }
            return false;
        }
        if (!this.insertWord(wordInfo)) {
            if (this.lineHeight > 0.0) {
                this.setLineHeight(this.lineHeight);
            }
            return false;
        }
        this.lineHeight = lineHeight;
        if (wordInfo.getAscent() > this.ascent) {
            this.ascent = wordInfo.getAscent();
        }
        if (wordInfo.getDescent() > this.descent) {
            this.descent = wordInfo.getDescent();
        }
        this.hLeading = (lineHeight - (this.ascent + this.descent)) / 2.0;
        this.baseline = (float)(this.fr.getCurrentY() + this.hLeading + this.ascent);
        return true;
    }
    
    public boolean insertWord(final WordInfo wordInfo) {
        this.mergeGlyphGroups(wordInfo);
        if (!this.assignGlyphGroupRanges(this.newSize, this.newGGIS)) {
            return false;
        }
        this.swapGlyphGroupInfo();
        return true;
    }
    
    public boolean assignGlyphGroupRanges(final int n, final GlyphGroupInfo[] array) {
        int i = 0;
        int j = 0;
        while (j < this.numRanges) {
            final double n2 = this.ranges[2 * j + 1] - this.ranges[2 * j];
            float advance;
            float n3;
            for (advance = 0.0f, n3 = 0.0f; i < n; ++i, n3 += advance) {
                final GlyphGroupInfo glyphGroupInfo = array[i];
                glyphGroupInfo.setRange(j);
                advance = glyphGroupInfo.getAdvance();
                if (n2 - (n3 + advance) < 0.0) {
                    break;
                }
            }
            if (i == n) {
                --i;
                n3 -= advance;
            }
            float n4;
            GlyphGroupInfo glyphGroupInfo2;
            for (n4 = array[i].getLastAdvance(); n3 + n4 > n2; n3 -= glyphGroupInfo2.getAdvance(), n4 = glyphGroupInfo2.getLastAdvance()) {
                --i;
                n4 = 0.0f;
                if (i < 0) {
                    break;
                }
                glyphGroupInfo2 = array[i];
                if (j != glyphGroupInfo2.getRange()) {
                    break;
                }
            }
            ++i;
            this.rangeAdv[j] = n3 + n4;
            ++j;
            if (i == n) {
                return true;
            }
        }
        return false;
    }
    
    public boolean setLineHeight(final double lineHeight) {
        this.fr.newLineHeight(lineHeight);
        if (this.updateRangeInfo()) {
            this.lineHeight = lineHeight;
            return true;
        }
        if (this.lineHeight > 0.0) {
            this.fr.newLineHeight(this.lineHeight);
        }
        return false;
    }
    
    public double getCurrentY() {
        return this.fr.getCurrentY();
    }
    
    public boolean gotoY(final double n) {
        if (this.fr.gotoY(n)) {
            return true;
        }
        if (this.lineHeight > 0.0) {
            this.updateRangeInfo();
        }
        this.baseline = (float)(this.fr.getCurrentY() + this.hLeading + this.ascent);
        return false;
    }
    
    protected boolean updateRangeInfo() {
        this.fr.resetRange();
        final int numRangeOnLine = this.fr.getNumRangeOnLine();
        if (numRangeOnLine == 0) {
            return false;
        }
        this.numRanges = numRangeOnLine;
        if (this.ranges == null) {
            this.rangeAdv = new double[this.numRanges];
            this.ranges = new double[2 * this.numRanges];
        }
        else if (this.numRanges > this.rangeAdv.length) {
            int numRanges = 2 * this.rangeAdv.length;
            if (numRanges < this.numRanges) {
                numRanges = this.numRanges;
            }
            this.rangeAdv = new double[numRanges];
            this.ranges = new double[2 * numRanges];
        }
        for (int i = 0; i < this.numRanges; ++i) {
            final double[] nextRange = this.fr.nextRange();
            double n = nextRange[0];
            if (i == 0) {
                double n2 = this.bi.getLeftMargin();
                if (this.paraStart) {
                    final double n3 = this.bi.getIndent();
                    if (n2 < -n3) {
                        n2 = 0.0;
                    }
                    else {
                        n2 += n3;
                    }
                }
                n += n2;
            }
            double n4 = nextRange[1];
            if (i == this.numRanges - 1) {
                n4 -= this.bi.getRightMargin();
            }
            this.ranges[2 * i] = n;
            this.ranges[2 * i + 1] = n4;
        }
        return true;
    }
    
    protected void swapGlyphGroupInfo() {
        final GlyphGroupInfo[] ggis = this.ggis;
        this.ggis = this.newGGIS;
        this.newGGIS = ggis;
        this.size = this.newSize;
        this.newSize = 0;
    }
    
    protected void mergeGlyphGroups(final WordInfo wordInfo) {
        final int numGlyphGroups = wordInfo.getNumGlyphGroups();
        this.newSize = 0;
        if (this.ggis == null) {
            this.newSize = numGlyphGroups;
            this.newGGIS = new GlyphGroupInfo[numGlyphGroups];
            for (int i = 0; i < numGlyphGroups; ++i) {
                this.newGGIS[i] = wordInfo.getGlyphGroup(i);
            }
        }
        else {
            int j = 0;
            int k = 0;
            GlyphGroupInfo glyphGroupInfo = wordInfo.getGlyphGroup(k);
            int n = glyphGroupInfo.getStart();
            final int start = this.ggis[this.size - 1].getStart();
            this.newGGIS = assureSize(this.newGGIS, this.size + numGlyphGroups);
            if (n < start) {
                GlyphGroupInfo glyphGroupInfo2 = this.ggis[j];
                int n2 = glyphGroupInfo2.getStart();
                while (j < this.size && k < numGlyphGroups) {
                    if (n < n2) {
                        this.newGGIS[this.newSize++] = glyphGroupInfo;
                        if (++k >= numGlyphGroups) {
                            continue;
                        }
                        glyphGroupInfo = wordInfo.getGlyphGroup(k);
                        n = glyphGroupInfo.getStart();
                    }
                    else {
                        this.newGGIS[this.newSize++] = glyphGroupInfo2;
                        if (++j >= this.size) {
                            continue;
                        }
                        glyphGroupInfo2 = this.ggis[j];
                        n2 = glyphGroupInfo2.getStart();
                    }
                }
            }
            while (j < this.size) {
                this.newGGIS[this.newSize++] = this.ggis[j++];
            }
            while (k < numGlyphGroups) {
                this.newGGIS[this.newSize++] = wordInfo.getGlyphGroup(k++);
            }
        }
    }
    
    public void layout() {
        if (this.size == 0) {
            return;
        }
        this.assignGlyphGroupRanges(this.size, this.ggis);
        final GVTGlyphVector glyphVector = this.ggis[0].getGlyphVector();
        final boolean b = false;
        double n = 0.0;
        double n2 = 0.0;
        final int[] array = new int[this.numRanges];
        final int[] array2 = new int[this.numRanges];
        final GlyphGroupInfo[] array3 = new GlyphGroupInfo[this.numRanges];
        GlyphGroupInfo glyphGroupInfo = this.ggis[0];
        int n3 = glyphGroupInfo.getRange();
        final int[] array4 = array;
        final int n4 = n3;
        ++array4[n4];
        final int[] array5 = array2;
        final int n5 = n3;
        array5[n5] += glyphGroupInfo.getGlyphCount();
        for (int i = 1; i < this.size; ++i) {
            glyphGroupInfo = this.ggis[i];
            n3 = glyphGroupInfo.getRange();
            if (array3[n3] == null || !array3[n3].getHideLast()) {
                final int[] array6 = array;
                final int n6 = n3;
                ++array6[n6];
            }
            array3[n3] = glyphGroupInfo;
            final int[] array7 = array2;
            final int n7 = n3;
            array7[n7] += glyphGroupInfo.getGlyphCount();
            final GlyphGroupInfo glyphGroupInfo2 = this.ggis[i - 1];
            final int range = glyphGroupInfo2.getRange();
            if (n3 != range) {
                final int[] array8 = array2;
                final int n8 = range;
                array8[n8] += glyphGroupInfo2.getLastGlyphCount() - glyphGroupInfo2.getGlyphCount();
            }
        }
        final int[] array9 = array2;
        final int n9 = n3;
        array9[n9] += glyphGroupInfo.getLastGlyphCount() - glyphGroupInfo.getGlyphCount();
        int range2 = -1;
        double n10 = 0.0;
        GlyphGroupInfo glyphGroupInfo3 = null;
        for (int j = 0; j < this.size; ++j) {
            final GlyphGroupInfo glyphGroupInfo4 = glyphGroupInfo3;
            final int n11 = range2;
            glyphGroupInfo3 = this.ggis[j];
            range2 = glyphGroupInfo3.getRange();
            if (range2 != n11) {
                n10 = this.ranges[2 * range2];
                final double n12 = this.ranges[2 * range2 + 1] - n10;
                final double n13 = this.rangeAdv[range2];
                int textAlignment = this.bi.getTextAlignment();
                if (this.paraEnd && textAlignment == 3) {
                    textAlignment = 0;
                }
                switch (textAlignment) {
                    default: {
                        final double n14 = n12 - n13;
                        if (!b) {
                            final int n15 = array[range2] - 1;
                            if (n15 >= 1) {
                                n = n14 / n15;
                                break;
                            }
                            break;
                        }
                        else {
                            final int n16 = array2[range2] - 1;
                            if (n16 >= 1) {
                                n2 = n14 / n16;
                                break;
                            }
                            break;
                        }
                        break;
                    }
                    case 0: {
                        break;
                    }
                    case 1: {
                        n10 += (n12 - n13) / 2.0;
                        break;
                    }
                    case 2: {
                        n10 += n12 - n13;
                        break;
                    }
                }
            }
            else if (glyphGroupInfo4 != null && glyphGroupInfo4.getHideLast()) {
                glyphVector.setGlyphVisible(glyphGroupInfo4.getEnd(), false);
            }
            final int start = glyphGroupInfo3.getStart();
            final int end = glyphGroupInfo3.getEnd();
            final boolean[] hide = glyphGroupInfo3.getHide();
            Point2D glyphPosition = glyphVector.getGlyphPosition(start);
            final double x = glyphPosition.getX();
            double n17 = 0.0;
            for (int k = start; k <= end; ++k) {
                final Point2D glyphPosition2 = glyphVector.getGlyphPosition(k + 1);
                if (hide[k - start]) {
                    glyphVector.setGlyphVisible(k, false);
                    n17 += glyphPosition2.getX() - glyphPosition.getX();
                }
                else {
                    glyphVector.setGlyphVisible(k, true);
                }
                glyphPosition.setLocation(glyphPosition.getX() - x - n17 + n10, glyphPosition.getY() + this.baseline);
                glyphVector.setGlyphPosition(k, glyphPosition);
                glyphPosition = glyphPosition2;
                n17 -= n2;
            }
            if (glyphGroupInfo3.getHideLast()) {
                n10 += glyphGroupInfo3.getAdvance() - n17;
            }
            else {
                n10 += glyphGroupInfo3.getAdvance() - n17 + n;
            }
        }
    }
    
    public static GlyphGroupInfo[] assureSize(final GlyphGroupInfo[] array, int n) {
        if (array == null) {
            if (n < 10) {
                n = 10;
            }
            return new GlyphGroupInfo[n];
        }
        if (n <= array.length) {
            return array;
        }
        int n2 = array.length * 2;
        if (n2 < n) {
            n2 = n;
        }
        return new GlyphGroupInfo[n2];
    }
}
