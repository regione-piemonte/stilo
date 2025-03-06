// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.awt.image.renderable;

import org.apache.batik.ext.awt.image.rendered.ProfileRed;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderContext;
import java.util.Map;
import org.apache.batik.ext.awt.color.ICCColorSpaceExt;

public class ProfileRable extends AbstractRable
{
    private ICCColorSpaceExt colorSpace;
    
    public ProfileRable(final Filter filter, final ICCColorSpaceExt colorSpace) {
        super(filter);
        this.colorSpace = colorSpace;
    }
    
    public void setSource(final Filter filter) {
        this.init(filter, null);
    }
    
    public Filter getSource() {
        return this.getSources().get(0);
    }
    
    public void setColorSpace(final ICCColorSpaceExt colorSpace) {
        this.touch();
        this.colorSpace = colorSpace;
    }
    
    public ICCColorSpaceExt getColorSpace() {
        return this.colorSpace;
    }
    
    public RenderedImage createRendering(final RenderContext renderContext) {
        final RenderedImage rendering = this.getSource().createRendering(renderContext);
        if (rendering == null) {
            return null;
        }
        return new ProfileRed(GraphicsUtil.wrap(rendering), this.colorSpace);
    }
}
