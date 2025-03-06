// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.ParseException;
import java.util.Iterator;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Attr;
import java.util.List;
import org.apache.batik.anim.values.AnimatableTransformListValue;
import org.w3c.dom.svg.SVGTransform;
import java.util.ArrayList;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGTransformList;
import org.w3c.dom.svg.SVGAnimatedTransformList;

public class SVGOMAnimatedTransformList extends AbstractSVGAnimatedValue implements SVGAnimatedTransformList
{
    protected BaseSVGTransformList baseVal;
    protected AnimSVGTransformList animVal;
    protected boolean changing;
    protected String defaultValue;
    
    public SVGOMAnimatedTransformList(final AbstractElement abstractElement, final String s, final String s2, final String defaultValue) {
        super(abstractElement, s, s2);
        this.defaultValue = defaultValue;
    }
    
    public SVGTransformList getBaseVal() {
        if (this.baseVal == null) {
            this.baseVal = new BaseSVGTransformList();
        }
        return (SVGTransformList)this.baseVal;
    }
    
    public SVGTransformList getAnimVal() {
        if (this.animVal == null) {
            this.animVal = new AnimSVGTransformList();
        }
        return (SVGTransformList)this.animVal;
    }
    
    public void check() {
        if (!this.hasAnimVal) {
            if (this.baseVal == null) {
                this.baseVal = new BaseSVGTransformList();
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
        final SVGTransformList baseVal = this.getBaseVal();
        final int numberOfItems = baseVal.getNumberOfItems();
        final ArrayList list = new ArrayList<SVGTransform>(numberOfItems);
        for (int i = 0; i < numberOfItems; ++i) {
            list.add(baseVal.getItem(i));
        }
        return new AnimatableTransformListValue(animationTarget, list);
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            final AnimatableTransformListValue animatableTransformListValue = (AnimatableTransformListValue)animatableValue;
            if (this.animVal == null) {
                this.animVal = new AnimSVGTransformList();
            }
            this.animVal.setAnimatedValue(animatableTransformListValue.getTransforms());
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
    
    protected class AnimSVGTransformList extends AbstractSVGTransformList
    {
        public AnimSVGTransformList() {
            this.itemList = new ArrayList(1);
        }
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedTransformList.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedTransformList.this.element).createSVGException(n, s, array);
        }
        
        public int getNumberOfItems() {
            if (SVGOMAnimatedTransformList.this.hasAnimVal) {
                return super.getNumberOfItems();
            }
            return SVGOMAnimatedTransformList.this.getBaseVal().getNumberOfItems();
        }
        
        public SVGTransform getItem(final int n) throws DOMException {
            if (SVGOMAnimatedTransformList.this.hasAnimVal) {
                return super.getItem(n);
            }
            return SVGOMAnimatedTransformList.this.getBaseVal().getItem(n);
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
            throw SVGOMAnimatedTransformList.this.element.createDOMException((short)7, "readonly.transform.list", null);
        }
        
        public SVGTransform initialize(final SVGTransform svgTransform) throws DOMException, SVGException {
            throw SVGOMAnimatedTransformList.this.element.createDOMException((short)7, "readonly.transform.list", null);
        }
        
        public SVGTransform insertItemBefore(final SVGTransform svgTransform, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedTransformList.this.element.createDOMException((short)7, "readonly.transform.list", null);
        }
        
        public SVGTransform replaceItem(final SVGTransform svgTransform, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedTransformList.this.element.createDOMException((short)7, "readonly.transform.list", null);
        }
        
        public SVGTransform removeItem(final int n) throws DOMException {
            throw SVGOMAnimatedTransformList.this.element.createDOMException((short)7, "readonly.transform.list", null);
        }
        
        public SVGTransform appendItem(final SVGTransform svgTransform) throws DOMException {
            throw SVGOMAnimatedTransformList.this.element.createDOMException((short)7, "readonly.transform.list", null);
        }
        
        public SVGTransform consolidate() {
            throw SVGOMAnimatedTransformList.this.element.createDOMException((short)7, "readonly.transform.list", null);
        }
        
        protected void setAnimatedValue(final Iterator iterator) {
            int i;
            int n;
            for (i = this.itemList.size(), n = 0; n < i && iterator.hasNext(); ++n) {
                ((SVGTransformItem)this.itemList.get(n)).assign(iterator.next());
            }
            while (iterator.hasNext()) {
                this.appendItemImpl(new SVGTransformItem(iterator.next()));
                ++n;
            }
            while (i > n) {
                this.removeItemImpl(--i);
            }
        }
        
        protected void setAnimatedValue(final SVGTransform svgTransform) {
            int i = this.itemList.size();
            while (i > 1) {
                this.removeItemImpl(--i);
            }
            if (i == 0) {
                this.appendItemImpl(new SVGTransformItem(svgTransform));
            }
            else {
                this.itemList.get(0).assign(svgTransform);
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
    
    public class BaseSVGTransformList extends AbstractSVGTransformList
    {
        protected boolean missing;
        protected boolean malformed;
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedTransformList.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedTransformList.this.element).createSVGException(n, s, array);
        }
        
        protected String getValueAsString() {
            final Attr attributeNodeNS = SVGOMAnimatedTransformList.this.element.getAttributeNodeNS(SVGOMAnimatedTransformList.this.namespaceURI, SVGOMAnimatedTransformList.this.localName);
            if (attributeNodeNS == null) {
                return SVGOMAnimatedTransformList.this.defaultValue;
            }
            return attributeNodeNS.getValue();
        }
        
        protected void setAttributeValue(final String s) {
            try {
                SVGOMAnimatedTransformList.this.changing = true;
                SVGOMAnimatedTransformList.this.element.setAttributeNS(SVGOMAnimatedTransformList.this.namespaceURI, SVGOMAnimatedTransformList.this.localName, s);
            }
            finally {
                SVGOMAnimatedTransformList.this.changing = false;
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
