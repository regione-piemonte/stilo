// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.renderer;

import org.apache.batik.gvt.text.TextHit;
import org.apache.batik.gvt.text.ConcreteTextLayoutFactory;
import java.awt.geom.Rectangle2D;
import org.apache.batik.gvt.text.Mark;
import org.apache.batik.gvt.TextNode;
import java.awt.geom.AffineTransform;
import java.awt.font.FontRenderContext;
import org.apache.batik.gvt.text.TextLayoutFactory;
import org.apache.batik.gvt.TextPainter;

public abstract class BasicTextPainter implements TextPainter
{
    private static TextLayoutFactory textLayoutFactory;
    protected FontRenderContext fontRenderContext;
    protected FontRenderContext aaOffFontRenderContext;
    
    public BasicTextPainter() {
        this.fontRenderContext = new FontRenderContext(new AffineTransform(), true, true);
        this.aaOffFontRenderContext = new FontRenderContext(new AffineTransform(), false, true);
    }
    
    protected TextLayoutFactory getTextLayoutFactory() {
        return BasicTextPainter.textLayoutFactory;
    }
    
    public Mark selectAt(final double n, final double n2, final TextNode textNode) {
        return this.hitTest(n, n2, textNode);
    }
    
    public Mark selectTo(final double n, final double n2, final Mark mark) {
        if (mark == null) {
            return null;
        }
        return this.hitTest(n, n2, mark.getTextNode());
    }
    
    public Rectangle2D getGeometryBounds(final TextNode textNode) {
        return this.getOutline(textNode).getBounds2D();
    }
    
    protected abstract Mark hitTest(final double p0, final double p1, final TextNode p2);
    
    static {
        BasicTextPainter.textLayoutFactory = new ConcreteTextLayoutFactory();
    }
    
    protected static class BasicMark implements Mark
    {
        private TextNode node;
        private TextHit hit;
        
        protected BasicMark(final TextNode node, final TextHit hit) {
            this.hit = hit;
            this.node = node;
        }
        
        public TextHit getHit() {
            return this.hit;
        }
        
        public TextNode getTextNode() {
            return this.node;
        }
        
        public int getCharIndex() {
            return this.hit.getCharIndex();
        }
    }
}
