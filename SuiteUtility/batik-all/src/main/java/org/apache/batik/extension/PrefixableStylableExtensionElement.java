// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.extension;

import org.w3c.dom.DOMException;
import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.AbstractDocument;

public abstract class PrefixableStylableExtensionElement extends StylableExtensionElement
{
    protected String prefix;
    
    protected PrefixableStylableExtensionElement() {
        this.prefix = null;
    }
    
    public PrefixableStylableExtensionElement(final String prefix, final AbstractDocument abstractDocument) {
        super(prefix, abstractDocument);
        this.prefix = null;
        this.setPrefix(prefix);
    }
    
    public String getNodeName() {
        return (this.prefix == null || this.prefix.equals("")) ? this.getLocalName() : (this.prefix + ':' + this.getLocalName());
    }
    
    public void setPrefix(final String prefix) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        if (prefix != null && !prefix.equals("") && !DOMUtilities.isValidName(prefix)) {
            throw this.createDOMException((short)5, "prefix", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), prefix });
        }
        this.prefix = prefix;
    }
}
