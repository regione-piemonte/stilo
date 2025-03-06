// 
// Decompiled by Procyon v0.5.36
// 

package org.w3c.dom.events;

import org.w3c.dom.DOMException;

public interface DocumentEvent
{
    Event createEvent(final String p0) throws DOMException;
    
    boolean canDispatch(final String p0, final String p1);
}
