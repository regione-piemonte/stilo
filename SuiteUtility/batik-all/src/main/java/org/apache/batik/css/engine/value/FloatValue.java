// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.w3c.dom.DOMException;

public class FloatValue extends AbstractValue
{
    protected static final String[] UNITS;
    protected float floatValue;
    protected short unitType;
    
    public static String getCssText(final short n, final float f) {
        if (n < 0 || n >= FloatValue.UNITS.length) {
            throw new DOMException((short)12, "");
        }
        String str = String.valueOf(f);
        if (str.endsWith(".0")) {
            str = str.substring(0, str.length() - 2);
        }
        return str + FloatValue.UNITS[n - 1];
    }
    
    public FloatValue(final short unitType, final float floatValue) {
        this.unitType = unitType;
        this.floatValue = floatValue;
    }
    
    public short getPrimitiveType() {
        return this.unitType;
    }
    
    public float getFloatValue() {
        return this.floatValue;
    }
    
    public String getCssText() {
        return getCssText(this.unitType, this.floatValue);
    }
    
    public String toString() {
        return this.getCssText();
    }
    
    static {
        UNITS = new String[] { "", "%", "em", "ex", "px", "cm", "mm", "in", "pt", "pc", "deg", "rad", "grad", "ms", "s", "Hz", "kHz", "" };
    }
}
