// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.Element;
import org.apache.batik.parser.ParseException;
import org.apache.batik.parser.LengthHandler;
import org.apache.batik.parser.LengthParser;
import org.w3c.dom.DOMException;
import org.apache.batik.parser.UnitProcessor;
import org.w3c.dom.svg.SVGLength;

public abstract class AbstractSVGLength implements SVGLength
{
    public static final short HORIZONTAL_LENGTH = 2;
    public static final short VERTICAL_LENGTH = 1;
    public static final short OTHER_LENGTH = 0;
    protected short unitType;
    protected float value;
    protected short direction;
    protected UnitProcessor.Context context;
    protected static final String[] UNITS;
    
    protected abstract SVGOMElement getAssociatedElement();
    
    public AbstractSVGLength(final short direction) {
        this.context = new DefaultContext();
        this.direction = direction;
        this.value = 0.0f;
        this.unitType = 1;
    }
    
    public short getUnitType() {
        this.revalidate();
        return this.unitType;
    }
    
    public float getValue() {
        this.revalidate();
        try {
            return UnitProcessor.svgToUserSpace(this.value, this.unitType, this.direction, this.context);
        }
        catch (IllegalArgumentException ex) {
            return 0.0f;
        }
    }
    
    public void setValue(final float n) throws DOMException {
        this.value = UnitProcessor.userSpaceToSVG(n, this.unitType, this.direction, this.context);
        this.reset();
    }
    
    public float getValueInSpecifiedUnits() {
        this.revalidate();
        return this.value;
    }
    
    public void setValueInSpecifiedUnits(final float value) throws DOMException {
        this.revalidate();
        this.value = value;
        this.reset();
    }
    
    public String getValueAsString() {
        this.revalidate();
        if (this.unitType == 0) {
            return "";
        }
        return Float.toString(this.value) + AbstractSVGLength.UNITS[this.unitType];
    }
    
    public void setValueAsString(final String s) throws DOMException {
        this.parse(s);
        this.reset();
    }
    
    public void newValueSpecifiedUnits(final short unitType, final float value) {
        this.unitType = unitType;
        this.value = value;
        this.reset();
    }
    
    public void convertToSpecifiedUnits(final short unitType) {
        final float value = this.getValue();
        this.unitType = unitType;
        this.setValue(value);
    }
    
    protected void reset() {
    }
    
    protected void revalidate() {
    }
    
    protected void parse(final String s) {
        try {
            final LengthParser lengthParser = new LengthParser();
            final UnitProcessor.UnitResolver lengthHandler = new UnitProcessor.UnitResolver();
            lengthParser.setLengthHandler(lengthHandler);
            lengthParser.parse(s);
            this.unitType = lengthHandler.unit;
            this.value = lengthHandler.value;
        }
        catch (ParseException ex) {
            this.unitType = 0;
            this.value = 0.0f;
        }
    }
    
    static {
        UNITS = new String[] { "", "", "%", "em", "ex", "px", "cm", "mm", "in", "pt", "pc" };
    }
    
    protected class DefaultContext implements UnitProcessor.Context
    {
        public Element getElement() {
            return AbstractSVGLength.this.getAssociatedElement();
        }
        
        public float getPixelUnitToMillimeter() {
            return AbstractSVGLength.this.getAssociatedElement().getSVGContext().getPixelUnitToMillimeter();
        }
        
        public float getPixelToMM() {
            return this.getPixelUnitToMillimeter();
        }
        
        public float getFontSize() {
            return AbstractSVGLength.this.getAssociatedElement().getSVGContext().getFontSize();
        }
        
        public float getXHeight() {
            return 0.5f;
        }
        
        public float getViewportWidth() {
            return AbstractSVGLength.this.getAssociatedElement().getSVGContext().getViewportWidth();
        }
        
        public float getViewportHeight() {
            return AbstractSVGLength.this.getAssociatedElement().getSVGContext().getViewportHeight();
        }
    }
}
