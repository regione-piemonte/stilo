// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.w3c.dom.DOMException;

public abstract class AbstractValue implements Value
{
    public short getCssValueType() {
        return 1;
    }
    
    public short getPrimitiveType() {
        throw this.createDOMException();
    }
    
    public float getFloatValue() throws DOMException {
        throw this.createDOMException();
    }
    
    public String getStringValue() throws DOMException {
        throw this.createDOMException();
    }
    
    public Value getRed() throws DOMException {
        throw this.createDOMException();
    }
    
    public Value getGreen() throws DOMException {
        throw this.createDOMException();
    }
    
    public Value getBlue() throws DOMException {
        throw this.createDOMException();
    }
    
    public int getLength() throws DOMException {
        throw this.createDOMException();
    }
    
    public Value item(final int n) throws DOMException {
        throw this.createDOMException();
    }
    
    public Value getTop() throws DOMException {
        throw this.createDOMException();
    }
    
    public Value getRight() throws DOMException {
        throw this.createDOMException();
    }
    
    public Value getBottom() throws DOMException {
        throw this.createDOMException();
    }
    
    public Value getLeft() throws DOMException {
        throw this.createDOMException();
    }
    
    public String getIdentifier() throws DOMException {
        throw this.createDOMException();
    }
    
    public String getListStyle() throws DOMException {
        throw this.createDOMException();
    }
    
    public String getSeparator() throws DOMException {
        throw this.createDOMException();
    }
    
    protected DOMException createDOMException() {
        return new DOMException((short)15, Messages.formatMessage("invalid.value.access", new Object[] { new Integer(this.getCssValueType()) }));
    }
}
