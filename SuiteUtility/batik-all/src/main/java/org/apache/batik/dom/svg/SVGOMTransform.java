// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGMatrix;
import java.awt.geom.AffineTransform;

public class SVGOMTransform extends AbstractSVGTransform
{
    public SVGOMTransform() {
        this.affineTransform = new AffineTransform();
    }
    
    protected SVGMatrix createMatrix() {
        return (SVGMatrix)new AbstractSVGMatrix() {
            protected AffineTransform getAffineTransform() {
                return SVGOMTransform.this.affineTransform;
            }
            
            public void setA(final float a) throws DOMException {
                SVGOMTransform.this.setType((short)1);
                super.setA(a);
            }
            
            public void setB(final float b) throws DOMException {
                SVGOMTransform.this.setType((short)1);
                super.setB(b);
            }
            
            public void setC(final float c) throws DOMException {
                SVGOMTransform.this.setType((short)1);
                super.setC(c);
            }
            
            public void setD(final float d) throws DOMException {
                SVGOMTransform.this.setType((short)1);
                super.setD(d);
            }
            
            public void setE(final float e) throws DOMException {
                SVGOMTransform.this.setType((short)1);
                super.setE(e);
            }
            
            public void setF(final float f) throws DOMException {
                SVGOMTransform.this.setType((short)1);
                super.setF(f);
            }
        };
    }
}
