// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.LengthListHandler;
import org.apache.batik.parser.LengthListParser;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGLength;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGException;
import org.w3c.dom.svg.SVGLengthList;

public abstract class AbstractSVGLengthList extends AbstractSVGList implements SVGLengthList
{
    protected short direction;
    public static final String SVG_LENGTH_LIST_SEPARATOR = " ";
    
    protected String getItemSeparator() {
        return " ";
    }
    
    protected abstract SVGException createSVGException(final short p0, final String p1, final Object[] p2);
    
    protected abstract Element getElement();
    
    protected AbstractSVGLengthList(final short direction) {
        this.direction = direction;
    }
    
    public SVGLength initialize(final SVGLength svgLength) throws DOMException, SVGException {
        return (SVGLength)this.initializeImpl(svgLength);
    }
    
    public SVGLength getItem(final int n) throws DOMException {
        return (SVGLength)this.getItemImpl(n);
    }
    
    public SVGLength insertItemBefore(final SVGLength svgLength, final int n) throws DOMException, SVGException {
        return (SVGLength)this.insertItemBeforeImpl(svgLength, n);
    }
    
    public SVGLength replaceItem(final SVGLength svgLength, final int n) throws DOMException, SVGException {
        return (SVGLength)this.replaceItemImpl(svgLength, n);
    }
    
    public SVGLength removeItem(final int n) throws DOMException {
        return (SVGLength)this.removeItemImpl(n);
    }
    
    public SVGLength appendItem(final SVGLength svgLength) throws DOMException, SVGException {
        return (SVGLength)this.appendItemImpl(svgLength);
    }
    
    protected SVGItem createSVGItem(final Object o) {
        final SVGLength svgLength = (SVGLength)o;
        return new SVGLengthItem(svgLength.getUnitType(), svgLength.getValueInSpecifiedUnits(), this.direction);
    }
    
    protected void doParse(final String s, final ListHandler listHandler) throws ParseException {
        final LengthListParser lengthListParser = new LengthListParser();
        lengthListParser.setLengthListHandler(new LengthListBuilder(listHandler));
        lengthListParser.parse(s);
    }
    
    protected void checkItemType(final Object o) throws SVGException {
        if (!(o instanceof SVGLength)) {
            this.createSVGException((short)0, "expected.length", null);
        }
    }
    
    protected class LengthListBuilder implements LengthListHandler
    {
        protected ListHandler listHandler;
        protected float currentValue;
        protected short currentType;
        
        public LengthListBuilder(final ListHandler listHandler) {
            this.listHandler = listHandler;
        }
        
        public void startLengthList() throws ParseException {
            this.listHandler.startList();
        }
        
        public void startLength() throws ParseException {
            this.currentType = 1;
            this.currentValue = 0.0f;
        }
        
        public void lengthValue(final float currentValue) throws ParseException {
            this.currentValue = currentValue;
        }
        
        public void em() throws ParseException {
            this.currentType = 3;
        }
        
        public void ex() throws ParseException {
            this.currentType = 4;
        }
        
        public void in() throws ParseException {
            this.currentType = 8;
        }
        
        public void cm() throws ParseException {
            this.currentType = 6;
        }
        
        public void mm() throws ParseException {
            this.currentType = 7;
        }
        
        public void pc() throws ParseException {
            this.currentType = 10;
        }
        
        public void pt() throws ParseException {
            this.currentType = 3;
        }
        
        public void px() throws ParseException {
            this.currentType = 5;
        }
        
        public void percentage() throws ParseException {
            this.currentType = 2;
        }
        
        public void endLength() throws ParseException {
            this.listHandler.item(new SVGLengthItem(this.currentType, this.currentValue, AbstractSVGLengthList.this.direction));
        }
        
        public void endLengthList() throws ParseException {
            this.listHandler.endList();
        }
    }
    
    protected class SVGLengthItem extends AbstractSVGLength implements SVGItem
    {
        protected AbstractSVGList parentList;
        
        public SVGLengthItem(final short unitType, final float value, final short n) {
            super(n);
            this.unitType = unitType;
            this.value = value;
        }
        
        protected SVGOMElement getAssociatedElement() {
            return (SVGOMElement)AbstractSVGLengthList.this.getElement();
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
