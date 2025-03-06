// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

import org.w3c.dom.Document;
import org.w3c.dom.events.EventListener;

public interface XMLHttpRequest
{
    public static final short UNSENT = 0;
    public static final short OPENED = 1;
    public static final short HEADERS_RECEIVED = 2;
    public static final short LOADING = 3;
    public static final short DONE = 4;
    
    EventListener getOnreadystatechange();
    
    void setOnreadystatechange(final EventListener p0);
    
    short getReadyState();
    
    void open(final String p0, final String p1);
    
    void open(final String p0, final String p1, final boolean p2);
    
    void open(final String p0, final String p1, final boolean p2, final String p3);
    
    void open(final String p0, final String p1, final boolean p2, final String p3, final String p4);
    
    void setRequestHeader(final String p0, final String p1);
    
    void send();
    
    void send(final String p0);
    
    void send(final Document p0);
    
    void abort();
    
    String getAllResponseHeaders();
    
    String getResponseHeader(final String p0);
    
    String getResponseText();
    
    String getResponseXML();
    
    short getStatus();
    
    String getStatusText();
}
