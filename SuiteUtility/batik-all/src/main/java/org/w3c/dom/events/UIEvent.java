// 
// Decompiled by Procyon v0.5.36
// 

package org.w3c.dom.events;

import org.w3c.dom.views.AbstractView;

public interface UIEvent extends Event
{
    AbstractView getView();
    
    int getDetail();
    
    void initUIEvent(final String p0, final boolean p1, final boolean p2, final AbstractView p3, final int p4);
    
    void initUIEventNS(final String p0, final String p1, final boolean p2, final boolean p3, final AbstractView p4, final int p5);
}
