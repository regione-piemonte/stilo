// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.events;

import org.w3c.dom.Node;
import org.w3c.dom.events.MutationEvent;

public class DOMMutationEvent extends AbstractEvent implements MutationEvent
{
    private Node relatedNode;
    private String prevValue;
    private String newValue;
    private String attrName;
    private short attrChange;
    
    public Node getRelatedNode() {
        return this.relatedNode;
    }
    
    public String getPrevValue() {
        return this.prevValue;
    }
    
    public String getNewValue() {
        return this.newValue;
    }
    
    public String getAttrName() {
        return this.attrName;
    }
    
    public short getAttrChange() {
        return this.attrChange;
    }
    
    public void initMutationEvent(final String s, final boolean b, final boolean b2, final Node relatedNode, final String prevValue, final String newValue, final String attrName, final short attrChange) {
        this.initEvent(s, b, b2);
        this.relatedNode = relatedNode;
        this.prevValue = prevValue;
        this.newValue = newValue;
        this.attrName = attrName;
        this.attrChange = attrChange;
    }
    
    public void initMutationEventNS(final String s, final String s2, final boolean b, final boolean b2, final Node relatedNode, final String prevValue, final String newValue, final String attrName, final short attrChange) {
        this.initEventNS(s, s2, b, b2);
        this.relatedNode = relatedNode;
        this.prevValue = prevValue;
        this.newValue = newValue;
        this.attrName = attrName;
        this.attrChange = attrChange;
    }
}
