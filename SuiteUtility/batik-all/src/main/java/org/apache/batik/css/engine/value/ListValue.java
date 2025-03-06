// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine.value;

import org.w3c.dom.DOMException;

public class ListValue extends AbstractValue
{
    protected int length;
    protected Value[] items;
    protected char separator;
    
    public ListValue() {
        this.items = new Value[5];
        this.separator = ',';
    }
    
    public ListValue(final char separator) {
        this.items = new Value[5];
        this.separator = ',';
        this.separator = separator;
    }
    
    public char getSeparatorChar() {
        return this.separator;
    }
    
    public short getCssValueType() {
        return 2;
    }
    
    public String getCssText() {
        final StringBuffer sb = new StringBuffer(this.length * 8);
        if (this.length > 0) {
            sb.append(this.items[0].getCssText());
        }
        for (int i = 1; i < this.length; ++i) {
            sb.append(this.separator);
            sb.append(this.items[i].getCssText());
        }
        return sb.toString();
    }
    
    public int getLength() throws DOMException {
        return this.length;
    }
    
    public Value item(final int n) throws DOMException {
        return this.items[n];
    }
    
    public String toString() {
        return this.getCssText();
    }
    
    public void append(final Value value) {
        if (this.length == this.items.length) {
            final Value[] items = new Value[this.length * 2];
            System.arraycopy(this.items, 0, items, 0, this.length);
            this.items = items;
        }
        this.items[this.length++] = value;
    }
}
