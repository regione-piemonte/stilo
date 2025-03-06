// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import org.w3c.dom.views.AbstractView;
import org.w3c.dom.smil.TimeEvent;

public class DOMTimeEvent extends AbstractEvent implements TimeEvent
{
    protected AbstractView view;
    protected int detail;
    
    public AbstractView getView() {
        return this.view;
    }
    
    public int getDetail() {
        return this.detail;
    }
    
    public void initTimeEvent(final String s, final AbstractView view, final int detail) {
        this.initEvent(s, false, false);
        this.view = view;
        this.detail = detail;
    }
    
    public void initTimeEventNS(final String s, final String s2, final AbstractView view, final int detail) {
        this.initEventNS(s, s2, false, false);
        this.view = view;
        this.detail = detail;
    }
    
    public void setTimestamp(final long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
