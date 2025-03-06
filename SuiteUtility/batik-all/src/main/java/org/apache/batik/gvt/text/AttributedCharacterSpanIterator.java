// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import java.util.Map;
import java.util.Set;
import java.text.AttributedCharacterIterator;

public class AttributedCharacterSpanIterator implements AttributedCharacterIterator
{
    private AttributedCharacterIterator aci;
    private int begin;
    private int end;
    
    public AttributedCharacterSpanIterator(final AttributedCharacterIterator aci, final int b, final int b2) {
        this.aci = aci;
        this.end = Math.min(aci.getEndIndex(), b2);
        this.begin = Math.max(aci.getBeginIndex(), b);
        this.aci.setIndex(this.begin);
    }
    
    public Set getAllAttributeKeys() {
        return this.aci.getAllAttributeKeys();
    }
    
    public Object getAttribute(final Attribute attribute) {
        return this.aci.getAttribute(attribute);
    }
    
    public Map getAttributes() {
        return this.aci.getAttributes();
    }
    
    public int getRunLimit() {
        return Math.min(this.aci.getRunLimit(), this.end);
    }
    
    public int getRunLimit(final Attribute attribute) {
        return Math.min(this.aci.getRunLimit(attribute), this.end);
    }
    
    public int getRunLimit(final Set set) {
        return Math.min(this.aci.getRunLimit(set), this.end);
    }
    
    public int getRunStart() {
        return Math.max(this.aci.getRunStart(), this.begin);
    }
    
    public int getRunStart(final Attribute attribute) {
        return Math.max(this.aci.getRunStart(attribute), this.begin);
    }
    
    public int getRunStart(final Set set) {
        return Math.max(this.aci.getRunStart(set), this.begin);
    }
    
    public Object clone() {
        return new AttributedCharacterSpanIterator((AttributedCharacterIterator)this.aci.clone(), this.begin, this.end);
    }
    
    public char current() {
        return this.aci.current();
    }
    
    public char first() {
        return this.aci.setIndex(this.begin);
    }
    
    public int getBeginIndex() {
        return this.begin;
    }
    
    public int getEndIndex() {
        return this.end;
    }
    
    public int getIndex() {
        return this.aci.getIndex();
    }
    
    public char last() {
        return this.setIndex(this.end - 1);
    }
    
    public char next() {
        if (this.getIndex() < this.end - 1) {
            return this.aci.next();
        }
        return this.setIndex(this.end);
    }
    
    public char previous() {
        if (this.getIndex() > this.begin) {
            return this.aci.previous();
        }
        return '\uffff';
    }
    
    public char setIndex(final int a) {
        final int min = Math.min(Math.max(a, this.begin), this.end);
        char setIndex = this.aci.setIndex(min);
        if (min == this.end) {
            setIndex = '\uffff';
        }
        return setIndex;
    }
}
