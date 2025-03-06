// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine;

import org.w3c.dom.Element;
import java.util.EventObject;

public class CSSEngineEvent extends EventObject
{
    protected Element element;
    protected int[] properties;
    
    public CSSEngineEvent(final CSSEngine source, final Element element, final int[] properties) {
        super(source);
        this.element = element;
        this.properties = properties;
    }
    
    public Element getElement() {
        return this.element;
    }
    
    public int[] getProperties() {
        return this.properties;
    }
}
