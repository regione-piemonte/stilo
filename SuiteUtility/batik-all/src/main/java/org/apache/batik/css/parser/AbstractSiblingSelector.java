// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.parser;

import org.w3c.css.sac.SimpleSelector;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SiblingSelector;

public abstract class AbstractSiblingSelector implements SiblingSelector
{
    protected short nodeType;
    protected Selector selector;
    protected SimpleSelector simpleSelector;
    
    protected AbstractSiblingSelector(final short nodeType, final Selector selector, final SimpleSelector simpleSelector) {
        this.nodeType = nodeType;
        this.selector = selector;
        this.simpleSelector = simpleSelector;
    }
    
    public short getNodeType() {
        return this.nodeType;
    }
    
    public Selector getSelector() {
        return this.selector;
    }
    
    public SimpleSelector getSiblingSelector() {
        return this.simpleSelector;
    }
}
