// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGMatrix;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.PointsHandler;
import org.apache.batik.parser.PointsParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGPointList;

public abstract class AbstractSVGPointList extends AbstractSVGList implements SVGPointList
{
    public static final String SVG_POINT_LIST_SEPARATOR = " ";
    
    protected String getItemSeparator() {
        return " ";
    }
    
    protected abstract SVGException createSVGException(final short p0, final String p1, final Object[] p2);
    
    public SVGPoint initialize(final SVGPoint svgPoint) throws DOMException, SVGException {
        return (SVGPoint)this.initializeImpl(svgPoint);
    }
    
    public SVGPoint getItem(final int n) throws DOMException {
        return (SVGPoint)this.getItemImpl(n);
    }
    
    public SVGPoint insertItemBefore(final SVGPoint svgPoint, final int n) throws DOMException, SVGException {
        return (SVGPoint)this.insertItemBeforeImpl(svgPoint, n);
    }
    
    public SVGPoint replaceItem(final SVGPoint svgPoint, final int n) throws DOMException, SVGException {
        return (SVGPoint)this.replaceItemImpl(svgPoint, n);
    }
    
    public SVGPoint removeItem(final int n) throws DOMException {
        return (SVGPoint)this.removeItemImpl(n);
    }
    
    public SVGPoint appendItem(final SVGPoint svgPoint) throws DOMException, SVGException {
        return (SVGPoint)this.appendItemImpl(svgPoint);
    }
    
    protected SVGItem createSVGItem(final Object o) {
        final SVGPoint svgPoint = (SVGPoint)o;
        return new SVGPointItem(svgPoint.getX(), svgPoint.getY());
    }
    
    protected void doParse(final String s, final ListHandler listHandler) throws ParseException {
        final PointsParser pointsParser = new PointsParser();
        pointsParser.setPointsHandler(new PointsListBuilder(listHandler));
        pointsParser.parse(s);
    }
    
    protected void checkItemType(final Object o) throws SVGException {
        if (!(o instanceof SVGPoint)) {
            this.createSVGException((short)0, "expected.point", null);
        }
    }
    
    protected class PointsListBuilder implements PointsHandler
    {
        protected ListHandler listHandler;
        
        public PointsListBuilder(final ListHandler listHandler) {
            this.listHandler = listHandler;
        }
        
        public void startPoints() throws ParseException {
            this.listHandler.startList();
        }
        
        public void point(final float n, final float n2) throws ParseException {
            this.listHandler.item(new SVGPointItem(n, n2));
        }
        
        public void endPoints() throws ParseException {
            this.listHandler.endList();
        }
    }
    
    protected class SVGPointItem extends AbstractSVGItem implements SVGPoint
    {
        protected float x;
        protected float y;
        
        public SVGPointItem(final float x, final float y) {
            this.x = x;
            this.y = y;
        }
        
        protected String getStringValue() {
            return Float.toString(this.x) + ',' + Float.toString(this.y);
        }
        
        public float getX() {
            return this.x;
        }
        
        public float getY() {
            return this.y;
        }
        
        public void setX(final float x) {
            this.x = x;
            this.resetAttribute();
        }
        
        public void setY(final float y) {
            this.y = y;
            this.resetAttribute();
        }
        
        public SVGPoint matrixTransform(final SVGMatrix svgMatrix) {
            return SVGOMPoint.matrixTransform((SVGPoint)this, svgMatrix);
        }
    }
}
