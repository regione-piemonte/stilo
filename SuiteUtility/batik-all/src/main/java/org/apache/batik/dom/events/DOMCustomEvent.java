// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import org.w3c.dom.events.CustomEvent;

public class DOMCustomEvent extends DOMEvent implements CustomEvent
{
    protected Object detail;
    
    public Object getDetail() {
        return this.detail;
    }
    
    public void initCustomEventNS(final String s, final String s2, final boolean b, final boolean b2, final Object detail) {
        this.initEventNS(s, s2, b, b2);
        this.detail = detail;
    }
}
