// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.ParseException;
import java.util.Iterator;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.DOMException;
import java.util.ArrayList;
import org.w3c.dom.Attr;
import org.w3c.dom.svg.SVGPoint;
import org.apache.batik.anim.values.AnimatablePointListValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGPointList;
import org.w3c.dom.svg.SVGAnimatedPoints;

public class SVGOMAnimatedPoints extends AbstractSVGAnimatedValue implements SVGAnimatedPoints
{
    protected BaseSVGPointList baseVal;
    protected AnimSVGPointList animVal;
    protected boolean changing;
    protected String defaultValue;
    
    public SVGOMAnimatedPoints(final AbstractElement abstractElement, final String s, final String s2, final String defaultValue) {
        super(abstractElement, s, s2);
        this.defaultValue = defaultValue;
    }
    
    public SVGPointList getPoints() {
        if (this.baseVal == null) {
            this.baseVal = new BaseSVGPointList();
        }
        return (SVGPointList)this.baseVal;
    }
    
    public SVGPointList getAnimatedPoints() {
        if (this.animVal == null) {
            this.animVal = new AnimSVGPointList();
        }
        return (SVGPointList)this.animVal;
    }
    
    public void check() {
        if (!this.hasAnimVal) {
            if (this.baseVal == null) {
                this.baseVal = new BaseSVGPointList();
            }
            this.baseVal.revalidate();
            if (this.baseVal.missing) {
                throw new LiveAttributeException(this.element, this.localName, (short)0, null);
            }
            if (this.baseVal.malformed) {
                throw new LiveAttributeException(this.element, this.localName, (short)1, this.baseVal.getValueAsString());
            }
        }
    }
    
    public AnimatableValue getUnderlyingValue(final AnimationTarget animationTarget) {
        final SVGPointList points = this.getPoints();
        final int numberOfItems = points.getNumberOfItems();
        final float[] array = new float[numberOfItems * 2];
        for (int i = 0; i < numberOfItems; ++i) {
            final SVGPoint item = points.getItem(i);
            array[i * 2] = item.getX();
            array[i * 2 + 1] = item.getY();
        }
        return new AnimatablePointListValue(animationTarget, array);
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            final AnimatablePointListValue animatablePointListValue = (AnimatablePointListValue)animatableValue;
            if (this.animVal == null) {
                this.animVal = new AnimSVGPointList();
            }
            this.animVal.setAnimatedValue(animatablePointListValue.getNumbers());
        }
        this.fireAnimatedAttributeListeners();
    }
    
    public void attrAdded(final Attr attr, final String s) {
        if (!this.changing && this.baseVal != null) {
            this.baseVal.invalidate();
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrModified(final Attr attr, final String s, final String s2) {
        if (!this.changing && this.baseVal != null) {
            this.baseVal.invalidate();
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    public void attrRemoved(final Attr attr, final String s) {
        if (!this.changing && this.baseVal != null) {
            this.baseVal.invalidate();
        }
        this.fireBaseAttributeListeners();
        if (!this.hasAnimVal) {
            this.fireAnimatedAttributeListeners();
        }
    }
    
    protected class AnimSVGPointList extends AbstractSVGPointList
    {
        public AnimSVGPointList() {
            this.itemList = new ArrayList(1);
        }
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedPoints.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedPoints.this.element).createSVGException(n, s, array);
        }
        
        public int getNumberOfItems() {
            if (SVGOMAnimatedPoints.this.hasAnimVal) {
                return super.getNumberOfItems();
            }
            return SVGOMAnimatedPoints.this.getPoints().getNumberOfItems();
        }
        
        public SVGPoint getItem(final int n) throws DOMException {
            if (SVGOMAnimatedPoints.this.hasAnimVal) {
                return super.getItem(n);
            }
            return SVGOMAnimatedPoints.this.getPoints().getItem(n);
        }
        
        protected String getValueAsString() {
            if (this.itemList.size() == 0) {
                return "";
            }
            final StringBuffer sb = new StringBuffer(this.itemList.size() * 8);
            final Iterator<SVGItem> iterator = (Iterator<SVGItem>)this.itemList.iterator();
            if (iterator.hasNext()) {
                sb.append(iterator.next().getValueAsString());
            }
            while (iterator.hasNext()) {
                sb.append(this.getItemSeparator());
                sb.append(iterator.next().getValueAsString());
            }
            return sb.toString();
        }
        
        protected void setAttributeValue(final String s) {
        }
        
        public void clear() throws DOMException {
            throw SVGOMAnimatedPoints.this.element.createDOMException((short)7, "readonly.point.list", null);
        }
        
        public SVGPoint initialize(final SVGPoint svgPoint) throws DOMException, SVGException {
            throw SVGOMAnimatedPoints.this.element.createDOMException((short)7, "readonly.point.list", null);
        }
        
        public SVGPoint insertItemBefore(final SVGPoint svgPoint, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedPoints.this.element.createDOMException((short)7, "readonly.point.list", null);
        }
        
        public SVGPoint replaceItem(final SVGPoint svgPoint, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedPoints.this.element.createDOMException((short)7, "readonly.point.list", null);
        }
        
        public SVGPoint removeItem(final int n) throws DOMException {
            throw SVGOMAnimatedPoints.this.element.createDOMException((short)7, "readonly.point.list", null);
        }
        
        public SVGPoint appendItem(final SVGPoint svgPoint) throws DOMException {
            throw SVGOMAnimatedPoints.this.element.createDOMException((short)7, "readonly.point.list", null);
        }
        
        protected void setAnimatedValue(final float[] array) {
            int i;
            int j;
            for (i = this.itemList.size(), j = 0; j < i && j < array.length / 2; ++j) {
                final SVGPointItem svgPointItem = this.itemList.get(j);
                svgPointItem.x = array[j * 2];
                svgPointItem.y = array[j * 2 + 1];
            }
            while (j < array.length / 2) {
                this.appendItemImpl(new SVGPointItem(array[j * 2], array[j * 2 + 1]));
                ++j;
            }
            while (i > array.length / 2) {
                this.removeItemImpl(--i);
            }
        }
        
        protected void resetAttribute() {
        }
        
        protected void resetAttribute(final SVGItem svgItem) {
        }
        
        protected void revalidate() {
            this.valid = true;
        }
    }
    
    protected class BaseSVGPointList extends AbstractSVGPointList
    {
        protected boolean missing;
        protected boolean malformed;
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedPoints.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedPoints.this.element).createSVGException(n, s, array);
        }
        
        protected String getValueAsString() {
            final Attr attributeNodeNS = SVGOMAnimatedPoints.this.element.getAttributeNodeNS(SVGOMAnimatedPoints.this.namespaceURI, SVGOMAnimatedPoints.this.localName);
            if (attributeNodeNS == null) {
                return SVGOMAnimatedPoints.this.defaultValue;
            }
            return attributeNodeNS.getValue();
        }
        
        protected void setAttributeValue(final String s) {
            try {
                SVGOMAnimatedPoints.this.changing = true;
                SVGOMAnimatedPoints.this.element.setAttributeNS(SVGOMAnimatedPoints.this.namespaceURI, SVGOMAnimatedPoints.this.localName, s);
            }
            finally {
                SVGOMAnimatedPoints.this.changing = false;
            }
        }
        
        protected void resetAttribute() {
            super.resetAttribute();
            this.missing = false;
            this.malformed = false;
        }
        
        protected void resetAttribute(final SVGItem svgItem) {
            super.resetAttribute(svgItem);
            this.missing = false;
            this.malformed = false;
        }
        
        protected void revalidate() {
            if (this.valid) {
                return;
            }
            this.valid = true;
            this.missing = false;
            this.malformed = false;
            final String valueAsString = this.getValueAsString();
            if (valueAsString == null) {
                this.missing = true;
                return;
            }
            try {
                final ListBuilder listBuilder = new ListBuilder();
                this.doParse(valueAsString, listBuilder);
                if (listBuilder.getList() != null) {
                    this.clear(this.itemList);
                }
                this.itemList = listBuilder.getList();
            }
            catch (ParseException ex) {
                this.itemList = new ArrayList(1);
                this.malformed = true;
            }
        }
    }
}
