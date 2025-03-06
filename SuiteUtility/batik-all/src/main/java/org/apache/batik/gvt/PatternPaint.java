// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt;

import java.awt.image.Raster;
import java.awt.PaintContext;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.image.ColorModel;
import org.apache.batik.ext.awt.image.renderable.PadRable8Bit;
import org.apache.batik.ext.awt.image.PadMode;
import org.apache.batik.ext.awt.image.renderable.Filter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Paint;

public class PatternPaint implements Paint
{
    private GraphicsNode node;
    private Rectangle2D patternRegion;
    private AffineTransform patternTransform;
    private Filter tile;
    private boolean overflow;
    private PatternPaintContext lastContext;
    
    public PatternPaint(final GraphicsNode node, final Rectangle2D patternRegion, final boolean overflow, final AffineTransform patternTransform) {
        if (node == null) {
            throw new IllegalArgumentException();
        }
        if (patternRegion == null) {
            throw new IllegalArgumentException();
        }
        this.node = node;
        this.patternRegion = patternRegion;
        this.overflow = overflow;
        this.patternTransform = patternTransform;
        final CompositeGraphicsNode compositeGraphicsNode = new CompositeGraphicsNode();
        compositeGraphicsNode.getChildren().add(node);
        final Filter graphicsNodeRable = compositeGraphicsNode.getGraphicsNodeRable(true);
        final Rectangle2D rectangle2D = (Rectangle2D)patternRegion.clone();
        if (overflow) {
            rectangle2D.add(compositeGraphicsNode.getBounds());
        }
        this.tile = new PadRable8Bit(graphicsNodeRable, rectangle2D, PadMode.ZERO_PAD);
    }
    
    public GraphicsNode getGraphicsNode() {
        return this.node;
    }
    
    public Rectangle2D getPatternRect() {
        return (Rectangle2D)this.patternRegion.clone();
    }
    
    public AffineTransform getPatternTransform() {
        return this.patternTransform;
    }
    
    public boolean getOverflow() {
        return this.overflow;
    }
    
    public PaintContext createContext(final ColorModel obj, final Rectangle rectangle, final Rectangle2D rectangle2D, AffineTransform tx, final RenderingHints renderingHints) {
        if (this.patternTransform != null) {
            tx = new AffineTransform(tx);
            tx.concatenate(this.patternTransform);
        }
        if (this.lastContext != null && this.lastContext.getColorModel().equals(obj)) {
            final double[] flatmatrix = new double[6];
            final double[] flatmatrix2 = new double[6];
            tx.getMatrix(flatmatrix);
            this.lastContext.getUsr2Dev().getMatrix(flatmatrix2);
            if (flatmatrix[0] == flatmatrix2[0] && flatmatrix[1] == flatmatrix2[1] && flatmatrix[2] == flatmatrix2[2] && flatmatrix[3] == flatmatrix2[3]) {
                if (flatmatrix[4] == flatmatrix2[4] && flatmatrix[5] == flatmatrix2[5]) {
                    return this.lastContext;
                }
                return new PatternPaintContextWrapper(this.lastContext, (int)(flatmatrix2[4] - flatmatrix[4] + 0.5), (int)(flatmatrix2[5] - flatmatrix[5] + 0.5));
            }
        }
        return this.lastContext = new PatternPaintContext(obj, tx, renderingHints, this.tile, this.patternRegion, this.overflow);
    }
    
    public int getTransparency() {
        return 3;
    }
    
    static class PatternPaintContextWrapper implements PaintContext
    {
        PatternPaintContext ppc;
        int xShift;
        int yShift;
        
        PatternPaintContextWrapper(final PatternPaintContext ppc, final int xShift, final int yShift) {
            this.ppc = ppc;
            this.xShift = xShift;
            this.yShift = yShift;
        }
        
        public void dispose() {
        }
        
        public ColorModel getColorModel() {
            return this.ppc.getColorModel();
        }
        
        public Raster getRaster(final int n, final int n2, final int n3, final int n4) {
            return this.ppc.getRaster(n + this.xShift, n2 + this.yShift, n3, n4);
        }
    }
}
