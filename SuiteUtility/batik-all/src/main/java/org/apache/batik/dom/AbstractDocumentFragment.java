// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.DocumentFragment;

public abstract class AbstractDocumentFragment extends AbstractParentNode implements DocumentFragment
{
    public String getNodeName() {
        return "#document-fragment";
    }
    
    public short getNodeType() {
        return 11;
    }
    
    protected void checkChildType(final Node node, final boolean b) {
        switch (node.getNodeType()) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 11: {}
            default: {
                throw this.createDOMException((short)3, "child.type", new Object[] { new Integer(this.getNodeType()), this.getNodeName(), new Integer(node.getNodeType()), node.getNodeName() });
            }
        }
    }
}
