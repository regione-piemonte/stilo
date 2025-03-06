// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.w3c.dom.DOMException;

public class StringValue extends AbstractValue
{
    protected String value;
    protected short unitType;
    
    public static String getCssText(final short n, final String s) {
        switch (n) {
            case 20: {
                return "url(" + s + ')';
            }
            case 19: {
                final char c = (s.indexOf(34) != -1) ? '\'' : '\"';
                return c + s + c;
            }
            default: {
                return s;
            }
        }
    }
    
    public StringValue(final short unitType, final String value) {
        this.unitType = unitType;
        this.value = value;
    }
    
    public short getPrimitiveType() {
        return this.unitType;
    }
    
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof StringValue)) {
            return false;
        }
        final StringValue stringValue = (StringValue)o;
        return this.unitType == stringValue.unitType && this.value.equals(stringValue.value);
    }
    
    public String getCssText() {
        return getCssText(this.unitType, this.value);
    }
    
    public String getStringValue() throws DOMException {
        return this.value;
    }
    
    public String toString() {
        return this.getCssText();
    }
}
