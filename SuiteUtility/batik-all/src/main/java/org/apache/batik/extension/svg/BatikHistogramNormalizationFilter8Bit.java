// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension.svg;

import java.awt.RenderingHints;
import org.apache.batik.ext.awt.image.rendered.ComponentTransferRed;
import org.apache.batik.ext.awt.image.LinearTransfer;
import org.apache.batik.ext.awt.image.TransferFunction;
import java.awt.image.RenderedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.renderable.RenderContext;
import java.util.Map;
import org.apache.batik.ext.awt.image.renderable.Filter;
import org.apache.batik.ext.awt.image.renderable.AbstractColorInterpolationRable;

public class BatikHistogramNormalizationFilter8Bit extends AbstractColorInterpolationRable implements BatikHistogramNormalizationFilter
{
    private float trim;
    protected int[] histo;
    protected float slope;
    protected float intercept;
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public float getTrim() {
        return this.trim;
    }
    
    public void setTrim(final float trim) {
        this.trim = trim;
        this.touch();
    }
    
    public BatikHistogramNormalizationFilter8Bit(final Filter source, final float trim) {
        this.trim = 0.01f;
        this.histo = null;
        this.setSource(source);
        this.setTrim(trim);
    }
    
    public void computeHistogram(final RenderContext renderContext) {
        if (this.histo != null) {
            return;
        }
        final Filter source = this.getSource();
        float n = 100.0f / source.getWidth();
        final float n2 = 100.0f / source.getHeight();
        if (n > n2) {
            n = n2;
        }
        final RenderedImage rendering = this.getSource().createRendering(new RenderContext(AffineTransform.getScaleInstance(n, n), renderContext.getRenderingHints()));
        this.histo = new HistogramRed(this.convertSourceCS(rendering)).getHistogram();
        final int n3 = (int)(rendering.getWidth() * rendering.getHeight() * this.trim + 0.5);
        int n4 = 0;
        int i;
        for (i = 0; i < 255; ++i) {
            n4 += this.histo[i];
            if (n4 >= n3) {
                break;
            }
        }
        final int n5 = i;
        int n6 = 0;
        int j;
        for (j = 255; j > 0; --j) {
            n6 += this.histo[j];
            if (n6 >= n3) {
                break;
            }
        }
        this.slope = 255.0f / (j - n5);
        this.intercept = this.slope * -n5 / 255.0f;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        final RenderedImage rendering = this.getSource().createRendering(renderContext);
        if (rendering == null) {
            return null;
        }
        this.computeHistogram(renderContext);
        final TransferFunction[] array = new TransferFunction[rendering.getSampleModel().getNumBands()];
        final LinearTransfer linearTransfer = new LinearTransfer(this.slope, this.intercept);
        for (int i = 0; i < array.length; ++i) {
            array[i] = linearTransfer;
        }
        return new ComponentTransferRed(this.convertSourceCS(rendering), array, null);
    }
}
