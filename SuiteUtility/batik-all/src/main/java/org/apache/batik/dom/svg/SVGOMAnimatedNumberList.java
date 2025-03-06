// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.ParseException;
import java.util.Iterator;
import org.w3c.dom.svg.SVGNumber;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.DOMException;
import java.util.ArrayList;
import org.w3c.dom.Attr;
import org.apache.batik.anim.values.AnimatableNumberListValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGNumberList;
import org.w3c.dom.svg.SVGAnimatedNumberList;

public class SVGOMAnimatedNumberList extends AbstractSVGAnimatedValue implements SVGAnimatedNumberList
{
    protected BaseSVGNumberList baseVal;
    protected AnimSVGNumberList animVal;
    protected boolean changing;
    protected String defaultValue;
    protected boolean emptyAllowed;
    
    public SVGOMAnimatedNumberList(final AbstractElement abstractElement, final String s, final String s2, final String defaultValue, final boolean emptyAllowed) {
        super(abstractElement, s, s2);
        this.defaultValue = defaultValue;
        this.emptyAllowed = emptyAllowed;
    }
    
    public SVGNumberList getBaseVal() {
        if (this.baseVal == null) {
            this.baseVal = new BaseSVGNumberList();
        }
        return (SVGNumberList)this.baseVal;
    }
    
    public SVGNumberList getAnimVal() {
        if (this.animVal == null) {
            this.animVal = new AnimSVGNumberList();
        }
        return (SVGNumberList)this.animVal;
    }
    
    public void check() {
        if (!this.hasAnimVal) {
            if (this.baseVal == null) {
                this.baseVal = new BaseSVGNumberList();
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
        final SVGNumberList baseVal = this.getBaseVal();
        final int numberOfItems = baseVal.getNumberOfItems();
        final float[] array = new float[numberOfItems];
        for (int i = 0; i < numberOfItems; ++i) {
            array[i] = baseVal.getItem(numberOfItems).getValue();
        }
        return new AnimatableNumberListValue(animationTarget, array);
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            final AnimatableNumberListValue animatableNumberListValue = (AnimatableNumberListValue)animatableValue;
            if (this.animVal == null) {
                this.animVal = new AnimSVGNumberList();
            }
            this.animVal.setAnimatedValue(animatableNumberListValue.getNumbers());
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
    
    protected class AnimSVGNumberList extends AbstractSVGNumberList
    {
        public AnimSVGNumberList() {
            this.itemList = new ArrayList(1);
        }
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedNumberList.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedNumberList.this.element).createSVGException(n, s, array);
        }
        
        protected Element getElement() {
            return SVGOMAnimatedNumberList.this.element;
        }
        
        public int getNumberOfItems() {
            if (SVGOMAnimatedNumberList.this.hasAnimVal) {
                return super.getNumberOfItems();
            }
            return SVGOMAnimatedNumberList.this.getBaseVal().getNumberOfItems();
        }
        
        public SVGNumber getItem(final int n) throws DOMException {
            if (SVGOMAnimatedNumberList.this.hasAnimVal) {
                return super.getItem(n);
            }
            return SVGOMAnimatedNumberList.this.getBaseVal().getItem(n);
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
            throw SVGOMAnimatedNumberList.this.element.createDOMException((short)7, "readonly.number.list", null);
        }
        
        public SVGNumber initialize(final SVGNumber svgNumber) throws DOMException, SVGException {
            throw SVGOMAnimatedNumberList.this.element.createDOMException((short)7, "readonly.number.list", null);
        }
        
        public SVGNumber insertItemBefore(final SVGNumber svgNumber, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedNumberList.this.element.createDOMException((short)7, "readonly.number.list", null);
        }
        
        public SVGNumber replaceItem(final SVGNumber svgNumber, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedNumberList.this.element.createDOMException((short)7, "readonly.number.list", null);
        }
        
        public SVGNumber removeItem(final int n) throws DOMException {
            throw SVGOMAnimatedNumberList.this.element.createDOMException((short)7, "readonly.number.list", null);
        }
        
        public SVGNumber appendItem(final SVGNumber svgNumber) throws DOMException {
            throw SVGOMAnimatedNumberList.this.element.createDOMException((short)7, "readonly.number.list", null);
        }
        
        protected void setAnimatedValue(final float[] array) {
            int i;
            int j;
            for (i = this.itemList.size(), j = 0; j < i && j < array.length; ++j) {
                ((SVGNumberItem)this.itemList.get(j)).value = array[j];
            }
            while (j < array.length) {
                this.appendItemImpl(new SVGNumberItem(array[j]));
                ++j;
            }
            while (i > array.length) {
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
    
    public class BaseSVGNumberList extends AbstractSVGNumberList
    {
        protected boolean missing;
        protected boolean malformed;
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedNumberList.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedNumberList.this.element).createSVGException(n, s, array);
        }
        
        protected Element getElement() {
            return SVGOMAnimatedNumberList.this.element;
        }
        
        protected String getValueAsString() {
            final Attr attributeNodeNS = SVGOMAnimatedNumberList.this.element.getAttributeNodeNS(SVGOMAnimatedNumberList.this.namespaceURI, SVGOMAnimatedNumberList.this.localName);
            if (attributeNodeNS == null) {
                return SVGOMAnimatedNumberList.this.defaultValue;
            }
            return attributeNodeNS.getValue();
        }
        
        protected void setAttributeValue(final String s) {
            try {
                SVGOMAnimatedNumberList.this.changing = true;
                SVGOMAnimatedNumberList.this.element.setAttributeNS(SVGOMAnimatedNumberList.this.namespaceURI, SVGOMAnimatedNumberList.this.localName, s);
            }
            finally {
                SVGOMAnimatedNumberList.this.changing = false;
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
            final boolean b = valueAsString != null && valueAsString.length() == 0;
            if (valueAsString == null || (b && !SVGOMAnimatedNumberList.this.emptyAllowed)) {
                this.missing = true;
                return;
            }
            if (b) {
                this.itemList = new ArrayList(1);
            }
            else {
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
                    this.valid = true;
                    this.malformed = true;
                }
            }
        }
    }
}
