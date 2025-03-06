// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.flow;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import org.apache.batik.ext.awt.geom.Segment;
import java.util.List;
import org.apache.batik.ext.awt.geom.SegmentList;
import java.awt.Shape;

public class FlowRegions
{
    Shape flowShape;
    SegmentList sl;
    SegmentList.SplitResults sr;
    List validRanges;
    int currentRange;
    double currentY;
    double lineHeight;
    
    public FlowRegions(final Shape shape) {
        this(shape, shape.getBounds2D().getY());
    }
    
    public FlowRegions(final Shape flowShape, final double n) {
        this.flowShape = flowShape;
        this.sl = new SegmentList(flowShape);
        this.currentY = n - 1.0;
        this.gotoY(n);
    }
    
    public double getCurrentY() {
        return this.currentY;
    }
    
    public double getLineHeight() {
        return this.lineHeight;
    }
    
    public boolean gotoY(final double n) {
        if (n < this.currentY) {
            throw new IllegalArgumentException("New Y can not be lower than old Y\nOld Y: " + this.currentY + " New Y: " + n);
        }
        if (n == this.currentY) {
            return false;
        }
        this.sr = this.sl.split(n);
        this.sl = this.sr.getBelow();
        this.sr = null;
        this.currentY = n;
        if (this.sl == null) {
            return true;
        }
        this.newLineHeight(this.lineHeight);
        return false;
    }
    
    public void newLineHeight(final double lineHeight) {
        this.lineHeight = lineHeight;
        this.sr = this.sl.split(this.currentY + lineHeight);
        if (this.sr.getAbove() != null) {
            this.sortRow(this.sr.getAbove());
        }
        this.currentRange = 0;
    }
    
    public int getNumRangeOnLine() {
        if (this.validRanges == null) {
            return 0;
        }
        return this.validRanges.size();
    }
    
    public void resetRange() {
        this.currentRange = 0;
    }
    
    public double[] nextRange() {
        if (this.currentRange >= this.validRanges.size()) {
            return null;
        }
        return this.validRanges.get(this.currentRange++);
    }
    
    public void endLine() {
        this.sl = this.sr.getBelow();
        this.sr = null;
        this.currentY += this.lineHeight;
    }
    
    public boolean newLine() {
        return this.newLine(this.lineHeight);
    }
    
    public boolean newLine(final double n) {
        if (this.sr != null) {
            this.sl = this.sr.getBelow();
        }
        this.sr = null;
        if (this.sl == null) {
            return false;
        }
        this.currentY += this.lineHeight;
        this.newLineHeight(n);
        return true;
    }
    
    public boolean newLineAt(final double currentY, final double n) {
        if (this.sr != null) {
            this.sl = this.sr.getBelow();
        }
        this.sr = null;
        if (this.sl == null) {
            return false;
        }
        this.currentY = currentY;
        this.newLineHeight(n);
        return true;
    }
    
    public boolean done() {
        return this.sl == null;
    }
    
    public void sortRow(final SegmentList list) {
        final Transition[] a = new Transition[list.size() * 2];
        final Iterator iterator = list.iterator();
        int n = 0;
        while (iterator.hasNext()) {
            final Segment segment = iterator.next();
            a[n++] = new Transition(segment.minX(), true);
            a[n++] = new Transition(segment.maxX(), false);
        }
        Arrays.sort(a, TransitionComp.COMP);
        this.validRanges = new ArrayList();
        int n2 = 1;
        double loc = 0.0;
        for (int i = 1; i < a.length; ++i) {
            final Transition transition = a[i];
            if (transition.up) {
                if (n2 == 0 && this.flowShape.contains((loc + transition.loc) / 2.0, this.currentY + this.lineHeight / 2.0)) {
                    this.validRanges.add(new double[] { loc, transition.loc });
                }
                ++n2;
            }
            else if (--n2 == 0) {
                loc = transition.loc;
            }
        }
    }
    
    static class TransitionComp implements Comparator
    {
        public static Comparator COMP;
        
        public int compare(final Object o, final Object o2) {
            final Transition transition = (Transition)o;
            final Transition transition2 = (Transition)o2;
            if (transition.loc < transition2.loc) {
                return -1;
            }
            if (transition.loc > transition2.loc) {
                return 1;
            }
            if (transition.up) {
                if (transition2.up) {
                    return 0;
                }
                return -1;
            }
            else {
                if (transition2.up) {
                    return 1;
                }
                return 0;
            }
        }
        
        public boolean equals(final Object o) {
            return this == o;
        }
        
        static {
            TransitionComp.COMP = new TransitionComp();
        }
    }
    
    static class Transition
    {
        public double loc;
        public boolean up;
        
        public Transition(final double loc, final boolean up) {
            this.loc = loc;
            this.up = up;
        }
    }
}
