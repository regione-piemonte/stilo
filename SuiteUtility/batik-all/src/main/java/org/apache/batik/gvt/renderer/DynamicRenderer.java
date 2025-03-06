// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.renderer;

import java.util.Iterator;
import java.awt.Graphics2D;
import java.awt.image.WritableRaster;
import org.apache.batik.util.HaltingThread;
import java.awt.Shape;
import java.awt.Paint;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.Color;
import org.apache.batik.ext.awt.image.rendered.PadRed;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.image.SampleModel;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.Point;
import java.util.Collection;
import java.awt.Rectangle;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import java.awt.geom.AffineTransform;
import java.awt.RenderingHints;
import org.apache.batik.ext.awt.geom.RectListManager;

public class DynamicRenderer extends StaticRenderer
{
    static final int COPY_OVERHEAD = 1000;
    static final int COPY_LINE_OVERHEAD = 10;
    RectListManager damagedAreas;
    
    public DynamicRenderer() {
    }
    
    public DynamicRenderer(final RenderingHints renderingHints, final AffineTransform affineTransform) {
        super(renderingHints, affineTransform);
    }
    
    protected CachableRed setupCache(final CachableRed cachableRed) {
        return cachableRed;
    }
    
    public void flush(final Rectangle rectangle) {
    }
    
    public void flush(final Collection collection) {
    }
    
    protected void updateWorkingBuffers() {
        if (this.rootFilter == null) {
            this.rootFilter = this.rootGN.getGraphicsNodeRable(true);
            this.rootCR = null;
        }
        this.rootCR = this.renderGNR();
        if (this.rootCR == null) {
            this.workingRaster = null;
            this.workingOffScreen = null;
            this.workingBaseRaster = null;
            this.currentOffScreen = null;
            this.currentBaseRaster = null;
            this.currentRaster = null;
            return;
        }
        final SampleModel sampleModel = this.rootCR.getSampleModel();
        final int offScreenWidth = this.offScreenWidth;
        final int offScreenHeight = this.offScreenHeight;
        if (this.workingBaseRaster == null || this.workingBaseRaster.getWidth() < offScreenWidth || this.workingBaseRaster.getHeight() < offScreenHeight) {
            this.workingBaseRaster = Raster.createWritableRaster(sampleModel.createCompatibleSampleModel(offScreenWidth, offScreenHeight), new Point(0, 0));
            this.workingRaster = this.workingBaseRaster.createWritableChild(0, 0, offScreenWidth, offScreenHeight, 0, 0, null);
            this.workingOffScreen = new BufferedImage(this.rootCR.getColorModel(), this.workingRaster, this.rootCR.getColorModel().isAlphaPremultiplied(), null);
        }
        if (!this.isDoubleBuffered) {
            this.currentOffScreen = this.workingOffScreen;
            this.currentBaseRaster = this.workingBaseRaster;
            this.currentRaster = this.workingRaster;
        }
    }
    
    public void repaint(final RectListManager damagedAreas) {
        if (damagedAreas == null) {
            return;
        }
        this.updateWorkingBuffers();
        if (this.rootCR == null || this.workingBaseRaster == null) {
            return;
        }
        CachableRed rootCR = this.rootCR;
        final WritableRaster workingBaseRaster = this.workingBaseRaster;
        final WritableRaster workingRaster = this.workingRaster;
        final Rectangle bounds = this.rootCR.getBounds();
        final Rectangle bounds2 = this.workingRaster.getBounds();
        if (bounds2.x < bounds.x || bounds2.y < bounds.y || bounds2.x + bounds2.width > bounds.x + bounds.width || bounds2.y + bounds2.height > bounds.y + bounds.height) {
            rootCR = new PadRed(rootCR, bounds2, PadMode.ZERO_PAD, null);
        }
        final boolean b = false;
        final Rectangle bounds3 = workingRaster.getBounds();
        synchronized (workingBaseRaster) {
            if (b) {
                rootCR.copyData(workingRaster);
            }
            else {
                final Graphics2D graphics2D = null;
                if (this.isDoubleBuffered && this.currentRaster != null && this.damagedAreas != null) {
                    this.damagedAreas.subtract(damagedAreas, 1000, 10);
                    this.damagedAreas.mergeRects(1000, 10);
                    final Color paint = new Color(0, 0, 255, 50);
                    final Color paint2 = new Color(0, 0, 0, 50);
                    for (final Rectangle rectangle : this.damagedAreas) {
                        if (!bounds3.intersects(rectangle)) {
                            continue;
                        }
                        final Rectangle intersection = bounds3.intersection(rectangle);
                        GraphicsUtil.copyData(this.currentRaster.createWritableChild(intersection.x, intersection.y, intersection.width, intersection.height, intersection.x, intersection.y, null), workingRaster);
                        if (graphics2D == null) {
                            continue;
                        }
                        graphics2D.setPaint(paint);
                        graphics2D.fill(intersection);
                        graphics2D.setPaint(paint2);
                        graphics2D.draw(intersection);
                    }
                }
                final Color paint3 = new Color(255, 0, 0, 50);
                final Color paint4 = new Color(0, 0, 0, 50);
                for (final Rectangle rectangle2 : damagedAreas) {
                    if (!bounds3.intersects(rectangle2)) {
                        continue;
                    }
                    final Rectangle intersection2 = bounds3.intersection(rectangle2);
                    rootCR.copyData(workingRaster.createWritableChild(intersection2.x, intersection2.y, intersection2.width, intersection2.height, intersection2.x, intersection2.y, null));
                    if (graphics2D == null) {
                        continue;
                    }
                    graphics2D.setPaint(paint3);
                    graphics2D.fill(intersection2);
                    graphics2D.setPaint(paint4);
                    graphics2D.draw(intersection2);
                }
            }
        }
        if (HaltingThread.hasBeenHalted()) {
            return;
        }
        final BufferedImage workingOffScreen = this.workingOffScreen;
        this.workingBaseRaster = this.currentBaseRaster;
        this.workingRaster = this.currentRaster;
        this.workingOffScreen = this.currentOffScreen;
        this.currentRaster = workingRaster;
        this.currentBaseRaster = workingBaseRaster;
        this.currentOffScreen = workingOffScreen;
        this.damagedAreas = damagedAreas;
    }
}
