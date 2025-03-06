// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.w3c.dom.DOMException;

public class RGBColorValue extends AbstractValue
{
    protected Value red;
    protected Value green;
    protected Value blue;
    
    public RGBColorValue(final Value red, final Value green, final Value blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }
    
    public short getPrimitiveType() {
        return 25;
    }
    
    public String getCssText() {
        return "rgb(" + this.red.getCssText() + ", " + this.green.getCssText() + ", " + this.blue.getCssText() + ')';
    }
    
    public Value getRed() throws DOMException {
        return this.red;
    }
    
    public Value getGreen() throws DOMException {
        return this.green;
    }
    
    public Value getBlue() throws DOMException {
        return this.blue;
    }
    
    public String toString() {
        return this.getCssText();
    }
}
