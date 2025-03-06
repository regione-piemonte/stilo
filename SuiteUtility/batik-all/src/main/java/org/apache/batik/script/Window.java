// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.script;

import org.apache.batik.bridge.BridgeContext;
import org.w3c.dom.Node;
import org.w3c.dom.Document;

public interface Window
{
    Object setInterval(final String p0, final long p1);
    
    Object setInterval(final Runnable p0, final long p1);
    
    void clearInterval(final Object p0);
    
    Object setTimeout(final String p0, final long p1);
    
    Object setTimeout(final Runnable p0, final long p1);
    
    void clearTimeout(final Object p0);
    
    Node parseXML(final String p0, final Document p1);
    
    void getURL(final String p0, final URLResponseHandler p1);
    
    void getURL(final String p0, final URLResponseHandler p1, final String p2);
    
    void postURL(final String p0, final String p1, final URLResponseHandler p2);
    
    void postURL(final String p0, final String p1, final URLResponseHandler p2, final String p3);
    
    void postURL(final String p0, final String p1, final URLResponseHandler p2, final String p3, final String p4);
    
    void alert(final String p0);
    
    boolean confirm(final String p0);
    
    String prompt(final String p0);
    
    String prompt(final String p0, final String p1);
    
    BridgeContext getBridgeContext();
    
    Interpreter getInterpreter();
    
    public interface URLResponseHandler
    {
        void getURLDone(final boolean p0, final String p1, final String p2);
    }
}
