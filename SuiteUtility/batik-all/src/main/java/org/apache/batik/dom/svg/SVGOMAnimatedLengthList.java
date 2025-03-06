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
import org.w3c.dom.svg.SVGLength;
import org.apache.batik.anim.values.AnimatableLengthListValue;
import org.apache.batik.anim.values.AnimatableValue;
import org.apache.batik.dom.anim.AnimationTarget;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGLengthList;
import org.w3c.dom.svg.SVGAnimatedLengthList;

public class SVGOMAnimatedLengthList extends AbstractSVGAnimatedValue implements SVGAnimatedLengthList
{
    protected BaseSVGLengthList baseVal;
    protected AnimSVGLengthList animVal;
    protected boolean changing;
    protected String defaultValue;
    protected boolean emptyAllowed;
    protected short direction;
    
    public SVGOMAnimatedLengthList(final AbstractElement abstractElement, final String s, final String s2, final String defaultValue, final boolean emptyAllowed, final short direction) {
        super(abstractElement, s, s2);
        this.defaultValue = defaultValue;
        this.emptyAllowed = emptyAllowed;
        this.direction = direction;
    }
    
    public SVGLengthList getBaseVal() {
        if (this.baseVal == null) {
            this.baseVal = new BaseSVGLengthList();
        }
        return (SVGLengthList)this.baseVal;
    }
    
    public SVGLengthList getAnimVal() {
        if (this.animVal == null) {
            this.animVal = new AnimSVGLengthList();
        }
        return (SVGLengthList)this.animVal;
    }
    
    public void check() {
        if (!this.hasAnimVal) {
            if (this.baseVal == null) {
                this.baseVal = new BaseSVGLengthList();
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
        final SVGLengthList baseVal = this.getBaseVal();
        final int numberOfItems = baseVal.getNumberOfItems();
        final short[] array = new short[numberOfItems];
        final float[] array2 = new float[numberOfItems];
        for (int i = 0; i < numberOfItems; ++i) {
            final SVGLength item = baseVal.getItem(i);
            array[i] = item.getUnitType();
            array2[i] = item.getValueInSpecifiedUnits();
        }
        return new AnimatableLengthListValue(animationTarget, array, array2, animationTarget.getPercentageInterpretation(this.getNamespaceURI(), this.getLocalName(), false));
    }
    
    protected void updateAnimatedValue(final AnimatableValue animatableValue) {
        if (animatableValue == null) {
            this.hasAnimVal = false;
        }
        else {
            this.hasAnimVal = true;
            final AnimatableLengthListValue animatableLengthListValue = (AnimatableLengthListValue)animatableValue;
            if (this.animVal == null) {
                this.animVal = new AnimSVGLengthList();
            }
            this.animVal.setAnimatedValue(animatableLengthListValue.getLengthTypes(), animatableLengthListValue.getLengthValues());
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
    
    protected class AnimSVGLengthList extends AbstractSVGLengthList
    {
        public AnimSVGLengthList() {
            super(SVGOMAnimatedLengthList.this.direction);
            this.itemList = new ArrayList(1);
        }
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedLengthList.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedLengthList.this.element).createSVGException(n, s, array);
        }
        
        protected Element getElement() {
            return SVGOMAnimatedLengthList.this.element;
        }
        
        public int getNumberOfItems() {
            if (SVGOMAnimatedLengthList.this.hasAnimVal) {
                return super.getNumberOfItems();
            }
            return SVGOMAnimatedLengthList.this.getBaseVal().getNumberOfItems();
        }
        
        public SVGLength getItem(final int n) throws DOMException {
            if (SVGOMAnimatedLengthList.this.hasAnimVal) {
                return super.getItem(n);
            }
            return SVGOMAnimatedLengthList.this.getBaseVal().getItem(n);
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
            throw SVGOMAnimatedLengthList.this.element.createDOMException((short)7, "readonly.length.list", null);
        }
        
        public SVGLength initialize(final SVGLength svgLength) throws DOMException, SVGException {
            throw SVGOMAnimatedLengthList.this.element.createDOMException((short)7, "readonly.length.list", null);
        }
        
        public SVGLength insertItemBefore(final SVGLength svgLength, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedLengthList.this.element.createDOMException((short)7, "readonly.length.list", null);
        }
        
        public SVGLength replaceItem(final SVGLength svgLength, final int n) throws DOMException, SVGException {
            throw SVGOMAnimatedLengthList.this.element.createDOMException((short)7, "readonly.length.list", null);
        }
        
        public SVGLength removeItem(final int n) throws DOMException {
            throw SVGOMAnimatedLengthList.this.element.createDOMException((short)7, "readonly.length.list", null);
        }
        
        public SVGLength appendItem(final SVGLength svgLength) throws DOMException {
            throw SVGOMAnimatedLengthList.this.element.createDOMException((short)7, "readonly.length.list", null);
        }
        
        protected void setAnimatedValue(final short[] array, final float[] array2) {
            int i;
            int j;
            for (i = this.itemList.size(), j = 0; j < i && j < array.length; ++j) {
                final SVGLengthItem svgLengthItem = this.itemList.get(j);
                svgLengthItem.unitType = array[j];
                svgLengthItem.value = array2[j];
                svgLengthItem.direction = this.direction;
            }
            while (j < array.length) {
                this.appendItemImpl(new SVGLengthItem(array[j], array2[j], this.direction));
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
    
    public class BaseSVGLengthList extends AbstractSVGLengthList
    {
        protected boolean missing;
        protected boolean malformed;
        
        public BaseSVGLengthList() {
            super(SVGOMAnimatedLengthList.this.direction);
        }
        
        protected DOMException createDOMException(final short n, final String s, final Object[] array) {
            return SVGOMAnimatedLengthList.this.element.createDOMException(n, s, array);
        }
        
        protected SVGException createSVGException(final short n, final String s, final Object[] array) {
            return ((SVGOMElement)SVGOMAnimatedLengthList.this.element).createSVGException(n, s, array);
        }
        
        protected Element getElement() {
            return SVGOMAnimatedLengthList.this.element;
        }
        
        protected String getValueAsString() {
            final Attr attributeNodeNS = SVGOMAnimatedLengthList.this.element.getAttributeNodeNS(SVGOMAnimatedLengthList.this.namespaceURI, SVGOMAnimatedLengthList.this.localName);
            if (attributeNodeNS == null) {
                return SVGOMAnimatedLengthList.this.defaultValue;
            }
            return attributeNodeNS.getValue();
        }
        
        protected void setAttributeValue(final String s) {
            try {
                SVGOMAnimatedLengthList.this.changing = true;
                SVGOMAnimatedLengthList.this.element.setAttributeNS(SVGOMAnimatedLengthList.this.namespaceURI, SVGOMAnimatedLengthList.this.localName, s);
            }
            finally {
                SVGOMAnimatedLengthList.this.changing = false;
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
            if (valueAsString == null || (b && !SVGOMAnimatedLengthList.this.emptyAllowed)) {
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
