// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.w3c.dom.DOMException;

public class RectValue extends AbstractValue
{
    protected Value top;
    protected Value right;
    protected Value bottom;
    protected Value left;
    
    public RectValue(final Value top, final Value right, final Value bottom, final Value left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }
    
    public short getPrimitiveType() {
        return 24;
    }
    
    public String getCssText() {
        return "rect(" + this.top.getCssText() + ", " + this.right.getCssText() + ", " + this.bottom.getCssText() + ", " + this.left.getCssText() + ')';
    }
    
    public Value getTop() throws DOMException {
        return this.top;
    }
    
    public Value getRight() throws DOMException {
        return this.right;
    }
    
    public Value getBottom() throws DOMException {
        return this.bottom;
    }
    
    public Value getLeft() throws DOMException {
        return this.left;
    }
    
    public String toString() {
        return this.getCssText();
    }
}
