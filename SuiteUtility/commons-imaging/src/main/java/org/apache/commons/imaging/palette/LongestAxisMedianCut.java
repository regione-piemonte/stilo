// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import java.util.Collection;
import java.util.ArrayList;
import org.apache.commons.imaging.ImageWriteException;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class LongestAxisMedianCut implements MedianCut
{
    private static final Comparator<ColorGroup> COMPARATOR;
    
    @Override
    public boolean performNextMedianCut(final List<ColorGroup> colorGroups, final boolean ignoreAlpha) throws ImageWriteException {
        Collections.sort(colorGroups, LongestAxisMedianCut.COMPARATOR);
        final ColorGroup colorGroup = colorGroups.get(0);
        if (colorGroup.maxDiff == 0) {
            return false;
        }
        if (!ignoreAlpha && colorGroup.alphaDiff > colorGroup.redDiff && colorGroup.alphaDiff > colorGroup.greenDiff && colorGroup.alphaDiff > colorGroup.blueDiff) {
            this.doCut(colorGroup, ColorComponent.ALPHA, colorGroups, ignoreAlpha);
        }
        else if (colorGroup.redDiff > colorGroup.greenDiff && colorGroup.redDiff > colorGroup.blueDiff) {
            this.doCut(colorGroup, ColorComponent.RED, colorGroups, ignoreAlpha);
        }
        else if (colorGroup.greenDiff > colorGroup.blueDiff) {
            this.doCut(colorGroup, ColorComponent.GREEN, colorGroups, ignoreAlpha);
        }
        else {
            this.doCut(colorGroup, ColorComponent.BLUE, colorGroups, ignoreAlpha);
        }
        return true;
    }
    
    private void doCut(final ColorGroup colorGroup, final ColorComponent mode, final List<ColorGroup> colorGroups, final boolean ignoreAlpha) throws ImageWriteException {
        final Comparator<ColorCount> comp = new Comparator<ColorCount>() {
            @Override
            public int compare(final ColorCount c1, final ColorCount c2) {
                switch (mode) {
                    case ALPHA: {
                        return c1.alpha - c2.alpha;
                    }
                    case RED: {
                        return c1.red - c2.red;
                    }
                    case GREEN: {
                        return c1.green - c2.green;
                    }
                    case BLUE: {
                        return c1.blue - c2.blue;
                    }
                    default: {
                        return 0;
                    }
                }
            }
        };
        Collections.sort(colorGroup.colorCounts, comp);
        final int countHalf = (int)Math.round(colorGroup.totalPoints / 2.0);
        int oldCount = 0;
        int newCount = 0;
        int medianIndex;
        for (medianIndex = 0; medianIndex < colorGroup.colorCounts.size(); ++medianIndex) {
            final ColorCount colorCount = colorGroup.colorCounts.get(medianIndex);
            newCount += colorCount.count;
            if (newCount >= countHalf) {
                break;
            }
            oldCount = newCount;
        }
        if (medianIndex == colorGroup.colorCounts.size() - 1) {
            --medianIndex;
        }
        else if (medianIndex > 0) {
            final int newDiff = Math.abs(newCount - countHalf);
            final int oldDiff = Math.abs(countHalf - oldCount);
            if (oldDiff < newDiff) {
                --medianIndex;
            }
        }
        colorGroups.remove(colorGroup);
        final List<ColorCount> colorCounts1 = new ArrayList<ColorCount>(colorGroup.colorCounts.subList(0, medianIndex + 1));
        final List<ColorCount> colorCounts2 = new ArrayList<ColorCount>(colorGroup.colorCounts.subList(medianIndex + 1, colorGroup.colorCounts.size()));
        final ColorGroup less = new ColorGroup(new ArrayList<ColorCount>(colorCounts1), ignoreAlpha);
        colorGroups.add(less);
        final ColorGroup more = new ColorGroup(new ArrayList<ColorCount>(colorCounts2), ignoreAlpha);
        colorGroups.add(more);
        final ColorCount medianValue = colorGroup.colorCounts.get(medianIndex);
        int limit = 0;
        switch (mode) {
            case ALPHA: {
                limit = medianValue.alpha;
                break;
            }
            case RED: {
                limit = medianValue.red;
                break;
            }
            case GREEN: {
                limit = medianValue.green;
                break;
            }
            case BLUE: {
                limit = medianValue.blue;
                break;
            }
            default: {
                throw new Error("Bad mode.");
            }
        }
        colorGroup.cut = new ColorGroupCut(less, more, mode, limit);
    }
    
    static {
        COMPARATOR = new Comparator<ColorGroup>() {
            @Override
            public int compare(final ColorGroup cg1, final ColorGroup cg2) {
                if (cg1.maxDiff == cg2.maxDiff) {
                    return cg2.diffTotal - cg1.diffTotal;
                }
                return cg2.maxDiff - cg1.maxDiff;
            }
        };
    }
}
