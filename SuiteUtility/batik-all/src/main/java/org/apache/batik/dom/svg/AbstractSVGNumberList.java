// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.NumberListHandler;
import org.apache.batik.parser.NumberListParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGNumber;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGNumberList;

public abstract class AbstractSVGNumberList extends AbstractSVGList implements SVGNumberList
{
    public static final String SVG_NUMBER_LIST_SEPARATOR = " ";
    
    protected String getItemSeparator() {
        return " ";
    }
    
    protected abstract SVGException createSVGException(final short p0, final String p1, final Object[] p2);
    
    protected abstract Element getElement();
    
    protected AbstractSVGNumberList() {
    }
    
    public SVGNumber initialize(final SVGNumber svgNumber) throws DOMException, SVGException {
        return (SVGNumber)this.initializeImpl(svgNumber);
    }
    
    public SVGNumber getItem(final int n) throws DOMException {
        return (SVGNumber)this.getItemImpl(n);
    }
    
    public SVGNumber insertItemBefore(final SVGNumber svgNumber, final int n) throws DOMException, SVGException {
        return (SVGNumber)this.insertItemBeforeImpl(svgNumber, n);
    }
    
    public SVGNumber replaceItem(final SVGNumber svgNumber, final int n) throws DOMException, SVGException {
        return (SVGNumber)this.replaceItemImpl(svgNumber, n);
    }
    
    public SVGNumber removeItem(final int n) throws DOMException {
        return (SVGNumber)this.removeItemImpl(n);
    }
    
    public SVGNumber appendItem(final SVGNumber svgNumber) throws DOMException, SVGException {
        return (SVGNumber)this.appendItemImpl(svgNumber);
    }
    
    protected SVGItem createSVGItem(final Object o) {
        return new SVGNumberItem(((SVGNumber)o).getValue());
    }
    
    protected void doParse(final String s, final ListHandler listHandler) throws ParseException {
        final NumberListParser numberListParser = new NumberListParser();
        numberListParser.setNumberListHandler(new NumberListBuilder(listHandler));
        numberListParser.parse(s);
    }
    
    protected void checkItemType(final Object o) throws SVGException {
        if (!(o instanceof SVGNumber)) {
            this.createSVGException((short)0, "expected SVGNumber", null);
        }
    }
    
    protected class NumberListBuilder implements NumberListHandler
    {
        protected ListHandler listHandler;
        protected float currentValue;
        
        public NumberListBuilder(final ListHandler listHandler) {
            this.listHandler = listHandler;
        }
        
        public void startNumberList() throws ParseException {
            this.listHandler.startList();
        }
        
        public void startNumber() throws ParseException {
            this.currentValue = 0.0f;
        }
        
        public void numberValue(final float currentValue) throws ParseException {
            this.currentValue = currentValue;
        }
        
        public void endNumber() throws ParseException {
            this.listHandler.item(new SVGNumberItem(this.currentValue));
        }
        
        public void endNumberList() throws ParseException {
            this.listHandler.endList();
        }
    }
    
    protected class SVGNumberItem extends AbstractSVGNumber implements SVGItem
    {
        protected AbstractSVGList parentList;
        
        public SVGNumberItem(final float value) {
            this.value = value;
        }
        
        public String getValueAsString() {
            return Float.toString(this.value);
        }
        
        public void setParent(final AbstractSVGList parentList) {
            this.parentList = parentList;
        }
        
        public AbstractSVGList getParent() {
            return this.parentList;
        }
        
        protected void reset() {
            if (this.parentList != null) {
                this.parentList.itemChanged();
            }
        }
    }
}
