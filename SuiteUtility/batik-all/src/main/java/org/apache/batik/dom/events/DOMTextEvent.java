// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import org.w3c.dom.views.AbstractView;
import org.w3c.dom.events.TextEvent;

public class DOMTextEvent extends DOMUIEvent implements TextEvent
{
    protected String data;
    
    public String getData() {
        return this.data;
    }
    
    public void initTextEvent(final String s, final boolean b, final boolean b2, final AbstractView abstractView, final String data) {
        this.initUIEvent(s, b, b2, abstractView, 0);
        this.data = data;
    }
    
    public void initTextEventNS(final String s, final String s2, final boolean b, final boolean b2, final AbstractView abstractView, final String data) {
        this.initUIEventNS(s, s2, b, b2, abstractView, 0);
        this.data = data;
    }
}
