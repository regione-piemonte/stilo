// 
// Decompiled by Procyon v0.5.36
// 

package org.w3c.dom.events;

import org.w3c.dom.DOMException;

public interface EventTarget
{
    void addEventListener(final String p0, final EventListener p1, final boolean p2);
    
    void removeEventListener(final String p0, final EventListener p1, final boolean p2);
    
    boolean dispatchEvent(final Event p0) throws EventException, DOMException;
    
    void addEventListenerNS(final String p0, final String p1, final EventListener p2, final boolean p3, final Object p4);
    
    void removeEventListenerNS(final String p0, final String p1, final EventListener p2, final boolean p3);
}
