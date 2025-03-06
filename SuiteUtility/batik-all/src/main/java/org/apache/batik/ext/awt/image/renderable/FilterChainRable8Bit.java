// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.awt.image.renderable.RenderableImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.ext.awt.image.SVGComposite;
import java.awt.Graphics2D;
import org.apache.batik.ext.awt.image.PadMode;
import java.awt.geom.Rectangle2D;

public class FilterChainRable8Bit extends AbstractRable implements FilterChainRable, PaintRable
{
    private int filterResolutionX;
    private int filterResolutionY;
    private Filter chainSource;
    private FilterResRable filterRes;
    private PadRable crop;
    private Rectangle2D filterRegion;
    
    public FilterChainRable8Bit(final Filter chainSource, final Rectangle2D filterRegion) {
        if (chainSource == null) {
            throw new IllegalArgumentException();
        }
        if (filterRegion == null) {
            throw new IllegalArgumentException();
        }
        this.crop = new PadRable8Bit(chainSource, (Rectangle2D)filterRegion.clone(), PadMode.ZERO_PAD);
        this.chainSource = chainSource;
        this.filterRegion = filterRegion;
        this.init(this.crop);
    }
    
    public int getFilterResolutionX() {
        return this.filterResolutionX;
    }
    
    public void setFilterResolutionX(final int filterResolutionX) {
        this.touch();
        this.filterResolutionX = filterResolutionX;
        this.setupFilterRes();
    }
    
    public int getFilterResolutionY() {
        return this.filterResolutionY;
    }
    
    public void setFilterResolutionY(final int filterResolutionY) {
        this.touch();
        this.filterResolutionY = filterResolutionY;
        this.setupFilterRes();
    }
    
    private void setupFilterRes() {
        if (this.filterResolutionX >= 0) {
            if (this.filterRes == null) {
                (this.filterRes = new FilterResRable8Bit()).setSource(this.chainSource);
            }
            this.filterRes.setFilterResolutionX(this.filterResolutionX);
            this.filterRes.setFilterResolutionY(this.filterResolutionY);
        }
        else {
            this.filterRes = null;
        }
        if (this.filterRes != null) {
            this.crop.setSource(this.filterRes);
        }
        else {
            this.crop.setSource(this.chainSource);
        }
    }
    
    public void setFilterRegion(final Rectangle2D filterRegion) {
        if (filterRegion == null) {
            throw new IllegalArgumentException();
        }
        this.touch();
        this.filterRegion = filterRegion;
    }
    
    public Rectangle2D getFilterRegion() {
        return this.filterRegion;
    }
    
    public Filter getSource() {
        return this.crop;
    }
    
    public void setSource(final Filter source) {
        if (source == null) {
            throw new IllegalArgumentException("Null Source for Filter Chain");
        }
        this.touch();
        this.chainSource = source;
        if (this.filterRes == null) {
            this.crop.setSource(source);
        }
        else {
            this.filterRes.setSource(source);
        }
    }
    
    public Rectangle2D getBounds2D() {
        return (Rectangle2D)this.filterRegion.clone();
    }
    
    public boolean paintRable(final Graphics2D graphics2D) {
        if (!SVGComposite.OVER.equals(graphics2D.getComposite())) {
            return false;
        }
        GraphicsUtil.drawImage(graphics2D, this.getSource());
        return true;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        return this.crop.createRendering(renderContext);
    }
}
