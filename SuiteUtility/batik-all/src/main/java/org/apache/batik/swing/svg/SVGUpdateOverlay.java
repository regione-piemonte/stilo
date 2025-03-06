// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import java.util.Iterator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;
import org.apache.batik.swing.gvt.Overlay;

public class SVGUpdateOverlay implements Overlay
{
    List rects;
    int size;
    int updateCount;
    int[] counts;
    
    public SVGUpdateOverlay(final int size, final int n) {
        this.rects = new LinkedList();
        this.size = size;
        this.counts = new int[n];
    }
    
    public void addRect(final Rectangle rectangle) {
        this.rects.add(rectangle);
        if (this.rects.size() > this.size) {
            this.rects.remove(0);
        }
        ++this.updateCount;
    }
    
    public void endUpdate() {
        int i;
        for (i = 0; i < this.counts.length - 1; ++i) {
            this.counts[i] = this.counts[i + 1];
        }
        this.counts[i] = this.updateCount;
        this.updateCount = 0;
        int size = this.rects.size();
        for (int j = this.counts.length - 1; j >= 0; --j) {
            if (this.counts[j] > size) {
                this.counts[j] = size;
            }
            size -= this.counts[j];
        }
        final int[] counts = this.counts;
        final int n = 0;
        counts[n] += size;
    }
    
    public void paint(final Graphics graphics) {
        final Iterator<Rectangle> iterator = this.rects.iterator();
        int n = 0;
        int n2;
        int n3;
        for (n2 = 0, n3 = 0; n3 < this.counts.length - 1 && n2 == this.counts[n3]; ++n3) {}
        final int n4 = this.counts.length - 1;
        while (iterator.hasNext()) {
            final Rectangle rectangle = iterator.next();
            graphics.setColor(new Color(1.0f, (n4 - n3) / (float)n4, 0.0f, (n + 1.0f) / this.rects.size()));
            graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            ++n;
            ++n2;
            while (n3 < this.counts.length - 1 && n2 == this.counts[n3]) {
                ++n3;
                n2 = 0;
            }
        }
    }
}
