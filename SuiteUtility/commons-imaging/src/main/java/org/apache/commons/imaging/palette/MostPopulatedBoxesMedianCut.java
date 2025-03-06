// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.palette;

import java.io.Serializable;
import org.apache.commons.imaging.ImageWriteException;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class MostPopulatedBoxesMedianCut implements MedianCut
{
    @Override
    public boolean performNextMedianCut(final List<ColorGroup> colorGroups, final boolean ignoreAlpha) throws ImageWriteException {
        int maxPoints = 0;
        ColorGroup colorGroup = null;
        for (final ColorGroup group : colorGroups) {
            if (group.maxDiff > 0 && group.totalPoints > maxPoints) {
                colorGroup = group;
                maxPoints = group.totalPoints;
            }
        }
        if (colorGroup == null) {
            return false;
        }
        double bestScore = Double.MAX_VALUE;
        ColorComponent bestColorComponent = null;
        int bestMedianIndex = -1;
        for (final ColorComponent colorComponent : ColorComponent.values()) {
            if (!ignoreAlpha || colorComponent != ColorComponent.ALPHA) {
                Collections.sort(colorGroup.colorCounts, new ColorComparer(colorComponent));
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
                final List<ColorCount> lowerColors = new ArrayList<ColorCount>(colorGroup.colorCounts.subList(0, medianIndex + 1));
                final List<ColorCount> upperColors = new ArrayList<ColorCount>(colorGroup.colorCounts.subList(medianIndex + 1, colorGroup.colorCounts.size()));
                if (!lowerColors.isEmpty()) {
                    if (!upperColors.isEmpty()) {
                        final ColorGroup lowerGroup = new ColorGroup(lowerColors, ignoreAlpha);
                        final ColorGroup upperGroup = new ColorGroup(upperColors, ignoreAlpha);
                        final int diff = Math.abs(lowerGroup.totalPoints - upperGroup.totalPoints);
                        final double score = diff / (double)Math.max(lowerGroup.totalPoints, upperGroup.totalPoints);
                        if (score < bestScore) {
                            bestScore = score;
                            bestColorComponent = colorComponent;
                            bestMedianIndex = medianIndex;
                        }
                    }
                }
            }
        }
        if (bestColorComponent == null) {
            return false;
        }
        Collections.sort(colorGroup.colorCounts, new ColorComparer(bestColorComponent));
        final List<ColorCount> lowerColors2 = new ArrayList<ColorCount>(colorGroup.colorCounts.subList(0, bestMedianIndex + 1));
        final List<ColorCount> upperColors2 = new ArrayList<ColorCount>(colorGroup.colorCounts.subList(bestMedianIndex + 1, colorGroup.colorCounts.size()));
        final ColorGroup lowerGroup2 = new ColorGroup(lowerColors2, ignoreAlpha);
        final ColorGroup upperGroup2 = new ColorGroup(upperColors2, ignoreAlpha);
        colorGroups.remove(colorGroup);
        colorGroups.add(lowerGroup2);
        colorGroups.add(upperGroup2);
        final ColorCount medianValue = colorGroup.colorCounts.get(bestMedianIndex);
        int limit = 0;
        switch (bestColorComponent) {
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
        colorGroup.cut = new ColorGroupCut(lowerGroup2, upperGroup2, bestColorComponent, limit);
        return true;
    }
    
    private static class ColorComparer implements Comparator<ColorCount>, Serializable
    {
        private static final long serialVersionUID = 1L;
        private final ColorComponent colorComponent;
        
        ColorComparer(final ColorComponent colorComponent) {
            this.colorComponent = colorComponent;
        }
        
        @Override
        public int compare(final ColorCount c1, final ColorCount c2) {
            switch (this.colorComponent) {
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
    }
}
