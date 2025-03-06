// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg12;

import org.w3c.dom.views.AbstractView;
import org.apache.batik.dom.events.DOMUIEvent;

public class SVGOMWheelEvent extends DOMUIEvent
{
    protected int wheelDelta;
    
    public int getWheelDelta() {
        return this.wheelDelta;
    }
    
    public void initWheelEvent(final String s, final boolean b, final boolean b2, final AbstractView abstractView, final int wheelDelta) {
        this.initUIEvent(s, b, b2, abstractView, 0);
        this.wheelDelta = wheelDelta;
    }
    
    public void initWheelEventNS(final String s, final String s2, final boolean b, final boolean b2, final AbstractView abstractView, final int wheelDelta) {
        this.initUIEventNS(s, s2, b, b2, abstractView, 0);
        this.wheelDelta = wheelDelta;
    }
}
