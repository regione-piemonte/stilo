// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.filter;

import java.awt.Graphics2D;
import java.awt.Composite;
import java.awt.AlphaComposite;
import org.apache.batik.ext.awt.image.GraphicsUtil;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.ColorModel;
import java.awt.Rectangle;
import java.util.Map;
import org.apache.batik.ext.awt.image.rendered.CachableRed;
import org.apache.batik.ext.awt.image.rendered.AbstractTiledRed;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.ext.awt.image.rendered.AbstractRed;

public class GraphicsNodeRed8Bit extends AbstractRed
{
    private GraphicsNode node;
    private AffineTransform node2dev;
    private RenderingHints hints;
    private boolean usePrimitivePaint;
    static final boolean onMacOSX;
    
    public GraphicsNodeRed8Bit(final GraphicsNode node, final AffineTransform node2dev, final boolean usePrimitivePaint, final RenderingHints hints) {
        this.node = node;
        this.node2dev = node2dev;
        this.hints = hints;
        this.usePrimitivePaint = usePrimitivePaint;
        AffineTransform affineTransform = node2dev;
        Rectangle2D primitiveBounds = node.getPrimitiveBounds();
        if (primitiveBounds == null) {
            primitiveBounds = new Rectangle2D.Float(0.0f, 0.0f, 1.0f, 1.0f);
        }
        if (!usePrimitivePaint) {
            final AffineTransform transform = node.getTransform();
            if (transform != null) {
                affineTransform = (AffineTransform)affineTransform.clone();
                affineTransform.concatenate(transform);
            }
        }
        final Rectangle bounds = affineTransform.createTransformedShape(primitiveBounds).getBounds();
        final ColorModel colorModel = this.createColorModel();
        final int defaultTileSize = AbstractTiledRed.getDefaultTileSize();
        final int n = defaultTileSize * (int)Math.floor(bounds.x / defaultTileSize);
        final int n2 = defaultTileSize * (int)Math.floor(bounds.y / defaultTileSize);
        int w = bounds.x + bounds.width - n;
        if (w > defaultTileSize) {
            w = defaultTileSize;
        }
        int h = bounds.y + bounds.height - n2;
        if (h > defaultTileSize) {
            h = defaultTileSize;
        }
        if (w <= 0 || h <= 0) {
            w = 1;
            h = 1;
        }
        this.init((CachableRed)null, bounds, colorModel, colorModel.createCompatibleSampleModel(w, h), n, n2, null);
    }
    
    public WritableRaster copyData(final WritableRaster writableRaster) {
        this.genRect(writableRaster);
        return writableRaster;
    }
    
    public void genRect(final WritableRaster writableRaster) {
        final Graphics2D graphics = GraphicsUtil.createGraphics(new BufferedImage(this.cm, writableRaster.createWritableTranslatedChild(0, 0), this.cm.isAlphaPremultiplied(), null), this.hints);
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, writableRaster.getWidth(), writableRaster.getHeight());
        graphics.setComposite(AlphaComposite.SrcOver);
        graphics.translate(-writableRaster.getMinX(), -writableRaster.getMinY());
        graphics.transform(this.node2dev);
        if (this.usePrimitivePaint) {
            this.node.primitivePaint(graphics);
        }
        else {
            this.node.paint(graphics);
        }
        graphics.dispose();
    }
    
    public ColorModel createColorModel() {
        if (GraphicsNodeRed8Bit.onMacOSX) {
            return GraphicsUtil.sRGB_Pre;
        }
        return GraphicsUtil.sRGB_Unpre;
    }
    
    static {
        onMacOSX = "Mac OS X".equals(System.getProperty("os.name"));
    }
}
