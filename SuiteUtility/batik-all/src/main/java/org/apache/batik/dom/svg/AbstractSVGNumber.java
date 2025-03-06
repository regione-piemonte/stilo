// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.svg.SVGNumber;

public abstract class AbstractSVGNumber implements SVGNumber
{
    protected float value;
    
    public float getValue() {
        return this.value;
    }
    
    public void setValue(final float value) {
        this.value = value;
    }
}
