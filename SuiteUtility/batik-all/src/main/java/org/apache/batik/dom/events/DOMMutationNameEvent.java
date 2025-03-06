// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import org.w3c.dom.Node;
import org.w3c.dom.events.MutationNameEvent;

public class DOMMutationNameEvent extends DOMMutationEvent implements MutationNameEvent
{
    protected String prevNamespaceURI;
    protected String prevNodeName;
    
    public void initMutationNameEvent(final String s, final boolean b, final boolean b2, final Node node, final String prevNamespaceURI, final String prevNodeName) {
        this.initMutationEvent(s, b, b2, node, null, null, null, (short)0);
        this.prevNamespaceURI = prevNamespaceURI;
        this.prevNodeName = prevNodeName;
    }
    
    public void initMutationNameEventNS(final String s, final String s2, final boolean b, final boolean b2, final Node node, final String prevNamespaceURI, final String prevNodeName) {
        this.initMutationEventNS(s, s2, b, b2, node, null, null, null, (short)0);
        this.prevNamespaceURI = prevNamespaceURI;
        this.prevNodeName = prevNodeName;
    }
    
    public String getPrevNamespaceURI() {
        return this.prevNamespaceURI;
    }
    
    public String getPrevNodeName() {
        return this.prevNodeName;
    }
}
