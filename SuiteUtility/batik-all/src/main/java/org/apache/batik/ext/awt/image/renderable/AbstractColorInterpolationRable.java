// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import java.awt.image.RenderedImage;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import java.awt.color.ColorSpace;
import java.util.List;
import java.util.Map;

public abstract class AbstractColorInterpolationRable extends AbstractRable
{
    protected boolean csLinear;
    
    protected AbstractColorInterpolationRable() {
        this.csLinear = true;
    }
    
    protected AbstractColorInterpolationRable(final Filter filter) {
        super(filter);
        this.csLinear = true;
    }
    
    protected AbstractColorInterpolationRable(final Filter filter, final Map map) {
        super(filter, map);
        this.csLinear = true;
    }
    
    protected AbstractColorInterpolationRable(final List list) {
        super(list);
        this.csLinear = true;
    }
    
    protected AbstractColorInterpolationRable(final List list, final Map map) {
        super(list, map);
        this.csLinear = true;
    }
    
    public boolean isColorSpaceLinear() {
        return this.csLinear;
    }
    
    public void setColorSpaceLinear(final boolean csLinear) {
        this.touch();
        this.csLinear = csLinear;
    }
    
    public ColorSpace getOperationColorSpace() {
        if (this.csLinear) {
            return ColorSpace.getInstance(1004);
        }
        return ColorSpace.getInstance(1000);
    }
    
    protected CachableRed convertSourceCS(final CachableRed cachableRed) {
        if (this.csLinear) {
            return GraphicsUtil.convertToLsRGB(cachableRed);
        }
        return GraphicsUtil.convertTosRGB(cachableRed);
    }
    
    protected CachableRed convertSourceCS(final RenderedImage renderedImage) {
        return this.convertSourceCS(GraphicsUtil.wrap(renderedImage));
    }
}
