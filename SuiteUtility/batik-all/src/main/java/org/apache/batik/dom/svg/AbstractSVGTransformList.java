// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.TransformListHandler;
import org.apache.batik.parser.TransformListParser;
import java.awt.geom.AffineTransform;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGTransform;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGTransformList;

public abstract class AbstractSVGTransformList extends AbstractSVGList implements SVGTransformList
{
    public static final String SVG_TRANSFORMATION_LIST_SEPARATOR = "";
    
    protected String getItemSeparator() {
        return "";
    }
    
    protected abstract SVGException createSVGException(final short p0, final String p1, final Object[] p2);
    
    public SVGTransform initialize(final SVGTransform svgTransform) throws DOMException, SVGException {
        return (SVGTransform)this.initializeImpl(svgTransform);
    }
    
    public SVGTransform getItem(final int n) throws DOMException {
        return (SVGTransform)this.getItemImpl(n);
    }
    
    public SVGTransform insertItemBefore(final SVGTransform svgTransform, final int n) throws DOMException, SVGException {
        return (SVGTransform)this.insertItemBeforeImpl(svgTransform, n);
    }
    
    public SVGTransform replaceItem(final SVGTransform svgTransform, final int n) throws DOMException, SVGException {
        return (SVGTransform)this.replaceItemImpl(svgTransform, n);
    }
    
    public SVGTransform removeItem(final int n) throws DOMException {
        return (SVGTransform)this.removeItemImpl(n);
    }
    
    public SVGTransform appendItem(final SVGTransform svgTransform) throws DOMException, SVGException {
        return (SVGTransform)this.appendItemImpl(svgTransform);
    }
    
    public SVGTransform createSVGTransformFromMatrix(final SVGMatrix matrix) {
        final SVGOMTransform svgomTransform = new SVGOMTransform();
        svgomTransform.setMatrix(matrix);
        return (SVGTransform)svgomTransform;
    }
    
    public SVGTransform consolidate() {
        this.revalidate();
        final int size = this.itemList.size();
        if (size == 0) {
            return null;
        }
        if (size == 1) {
            return this.getItem(0);
        }
        final AffineTransform affineTransform = (AffineTransform)((SVGTransformItem)this.getItemImpl(0)).affineTransform.clone();
        for (int i = 1; i < size; ++i) {
            affineTransform.concatenate(((SVGTransformItem)this.getItemImpl(i)).affineTransform);
        }
        return this.initialize(this.createSVGTransformFromMatrix((SVGMatrix)new SVGOMMatrix(affineTransform)));
    }
    
    public AffineTransform getAffineTransform() {
        final AffineTransform affineTransform = new AffineTransform();
        for (int i = 0; i < this.getNumberOfItems(); ++i) {
            affineTransform.concatenate(((SVGTransformItem)this.getItem(i)).affineTransform);
        }
        return affineTransform;
    }
    
    protected SVGItem createSVGItem(final Object o) {
        return new SVGTransformItem((SVGTransform)o);
    }
    
    protected void doParse(final String s, final ListHandler listHandler) throws ParseException {
        final TransformListParser transformListParser = new TransformListParser();
        transformListParser.setTransformListHandler(new TransformListBuilder(listHandler));
        transformListParser.parse(s);
    }
    
    protected void checkItemType(final Object o) {
        if (!(o instanceof SVGTransform)) {
            this.createSVGException((short)0, "expected.transform", null);
        }
    }
    
    protected class TransformListBuilder implements TransformListHandler
    {
        protected ListHandler listHandler;
        
        public TransformListBuilder(final ListHandler listHandler) {
            this.listHandler = listHandler;
        }
        
        public void startTransformList() throws ParseException {
            this.listHandler.startList();
        }
        
        public void matrix(final float n, final float n2, final float n3, final float n4, final float n5, final float n6) throws ParseException {
            final SVGTransformItem svgTransformItem = new SVGTransformItem();
            svgTransformItem.matrix(n, n2, n3, n4, n5, n6);
            this.listHandler.item(svgTransformItem);
        }
        
        public void rotate(final float n) throws ParseException {
            final SVGTransformItem svgTransformItem = new SVGTransformItem();
            svgTransformItem.rotate(n);
            this.listHandler.item(svgTransformItem);
        }
        
        public void rotate(final float n, final float n2, final float n3) throws ParseException {
            final SVGTransformItem svgTransformItem = new SVGTransformItem();
            svgTransformItem.setRotate(n, n2, n3);
            this.listHandler.item(svgTransformItem);
        }
        
        public void translate(final float n) throws ParseException {
            final SVGTransformItem svgTransformItem = new SVGTransformItem();
            svgTransformItem.translate(n);
            this.listHandler.item(svgTransformItem);
        }
        
        public void translate(final float n, final float n2) throws ParseException {
            final SVGTransformItem svgTransformItem = new SVGTransformItem();
            svgTransformItem.setTranslate(n, n2);
            this.listHandler.item(svgTransformItem);
        }
        
        public void scale(final float n) throws ParseException {
            final SVGTransformItem svgTransformItem = new SVGTransformItem();
            svgTransformItem.scale(n);
            this.listHandler.item(svgTransformItem);
        }
        
        public void scale(final float n, final float n2) throws ParseException {
            final SVGTransformItem svgTransformItem = new SVGTransformItem();
            svgTransformItem.setScale(n, n2);
            this.listHandler.item(svgTransformItem);
        }
        
        public void skewX(final float skewX) throws ParseException {
            final SVGTransformItem svgTransformItem = new SVGTransformItem();
            svgTransformItem.setSkewX(skewX);
            this.listHandler.item(svgTransformItem);
        }
        
        public void skewY(final float skewY) throws ParseException {
            final SVGTransformItem svgTransformItem = new SVGTransformItem();
            svgTransformItem.setSkewY(skewY);
            this.listHandler.item(svgTransformItem);
        }
        
        public void endTransformList() throws ParseException {
            this.listHandler.endList();
        }
    }
    
    protected class SVGTransformItem extends AbstractSVGTransform implements SVGItem
    {
        protected boolean xOnly;
        protected boolean angleOnly;
        protected AbstractSVGList parent;
        protected String itemStringValue;
        
        protected SVGTransformItem() {
        }
        
        protected SVGTransformItem(final SVGTransform svgTransform) {
            this.assign(svgTransform);
        }
        
        protected void resetAttribute() {
            if (this.parent != null) {
                this.itemStringValue = null;
                this.parent.itemChanged();
            }
        }
        
        public void setParent(final AbstractSVGList parent) {
            this.parent = parent;
        }
        
        public AbstractSVGList getParent() {
            return this.parent;
        }
        
        public String getValueAsString() {
            if (this.itemStringValue == null) {
                this.itemStringValue = this.getStringValue();
            }
            return this.itemStringValue;
        }
        
        public void assign(final SVGTransform svgTransform) {
            this.type = svgTransform.getType();
            final SVGMatrix matrix = svgTransform.getMatrix();
            switch (this.type) {
                case 2: {
                    this.setTranslate(matrix.getE(), matrix.getF());
                    break;
                }
                case 3: {
                    this.setScale(matrix.getA(), matrix.getD());
                    break;
                }
                case 4: {
                    if (matrix.getE() == 0.0f) {
                        this.rotate(svgTransform.getAngle());
                        break;
                    }
                    this.angleOnly = false;
                    if (matrix.getA() == 1.0f) {
                        this.setRotate(svgTransform.getAngle(), matrix.getE(), matrix.getF());
                        break;
                    }
                    if (svgTransform instanceof AbstractSVGTransform) {
                        final AbstractSVGTransform abstractSVGTransform = (AbstractSVGTransform)svgTransform;
                        this.setRotate(abstractSVGTransform.getAngle(), abstractSVGTransform.getX(), abstractSVGTransform.getY());
                        break;
                    }
                    break;
                }
                case 5: {
                    this.setSkewX(svgTransform.getAngle());
                    break;
                }
                case 6: {
                    this.setSkewY(svgTransform.getAngle());
                    break;
                }
                case 1: {
                    this.setMatrix(matrix);
                    break;
                }
            }
        }
        
        protected void translate(final float n) {
            this.xOnly = true;
            this.setTranslate(n, 0.0f);
        }
        
        protected void rotate(final float n) {
            this.angleOnly = true;
            this.setRotate(n, 0.0f, 0.0f);
        }
        
        protected void scale(final float n) {
            this.xOnly = true;
            this.setScale(n, n);
        }
        
        protected void matrix(final float m00, final float m2, final float m3, final float m4, final float m5, final float m6) {
            this.setMatrix((SVGMatrix)new SVGOMMatrix(new AffineTransform(m00, m2, m3, m4, m5, m6)));
        }
        
        public void setMatrix(final SVGMatrix matrix) {
            super.setMatrix(matrix);
            this.resetAttribute();
        }
        
        public void setTranslate(final float n, final float n2) {
            super.setTranslate(n, n2);
            this.resetAttribute();
        }
        
        public void setScale(final float n, final float n2) {
            super.setScale(n, n2);
            this.resetAttribute();
        }
        
        public void setRotate(final float n, final float n2, final float n3) {
            super.setRotate(n, n2, n3);
            this.resetAttribute();
        }
        
        public void setSkewX(final float skewX) {
            super.setSkewX(skewX);
            this.resetAttribute();
        }
        
        public void setSkewY(final float skewY) {
            super.setSkewY(skewY);
            this.resetAttribute();
        }
        
        protected SVGMatrix createMatrix() {
            return (SVGMatrix)new AbstractSVGMatrix() {
                private final /* synthetic */ SVGTransformItem this$1 = this$1;
                
                protected AffineTransform getAffineTransform() {
                    return this.this$1.affineTransform;
                }
                
                public void setA(final float a) throws DOMException {
                    this.this$1.type = 1;
                    super.setA(a);
                    this.this$1.resetAttribute();
                }
                
                public void setB(final float b) throws DOMException {
                    this.this$1.type = 1;
                    super.setB(b);
                    this.this$1.resetAttribute();
                }
                
                public void setC(final float c) throws DOMException {
                    this.this$1.type = 1;
                    super.setC(c);
                    this.this$1.resetAttribute();
                }
                
                public void setD(final float d) throws DOMException {
                    this.this$1.type = 1;
                    super.setD(d);
                    this.this$1.resetAttribute();
                }
                
                public void setE(final float e) throws DOMException {
                    this.this$1.type = 1;
                    super.setE(e);
                    this.this$1.resetAttribute();
                }
                
                public void setF(final float f) throws DOMException {
                    this.this$1.type = 1;
                    super.setF(f);
                    this.this$1.resetAttribute();
                }
            };
        }
        
        protected String getStringValue() {
            final StringBuffer sb = new StringBuffer();
            switch (this.type) {
                case 2: {
                    sb.append("translate(");
                    sb.append((float)this.affineTransform.getTranslateX());
                    if (!this.xOnly) {
                        sb.append(' ');
                        sb.append((float)this.affineTransform.getTranslateY());
                    }
                    sb.append(')');
                    break;
                }
                case 4: {
                    sb.append("rotate(");
                    sb.append(this.angle);
                    if (!this.angleOnly) {
                        sb.append(' ');
                        sb.append(this.x);
                        sb.append(' ');
                        sb.append(this.y);
                    }
                    sb.append(')');
                    break;
                }
                case 3: {
                    sb.append("scale(");
                    sb.append((float)this.affineTransform.getScaleX());
                    if (!this.xOnly) {
                        sb.append(' ');
                        sb.append((float)this.affineTransform.getScaleY());
                    }
                    sb.append(')');
                    break;
                }
                case 5: {
                    sb.append("skewX(");
                    sb.append(this.angle);
                    sb.append(')');
                    break;
                }
                case 6: {
                    sb.append("skewY(");
                    sb.append(this.angle);
                    sb.append(')');
                    break;
                }
                case 1: {
                    sb.append("matrix(");
                    final double[] flatmatrix = new double[6];
                    this.affineTransform.getMatrix(flatmatrix);
                    for (int i = 0; i < 6; ++i) {
                        if (i != 0) {
                            sb.append(' ');
                        }
                        sb.append((float)flatmatrix[i]);
                    }
                    sb.append(')');
                    break;
                }
            }
            return sb.toString();
        }
    }
}
