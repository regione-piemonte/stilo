// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.apache.batik.dom.util.XMLSupport;
import org.w3c.dom.Element;
import org.apache.batik.xml.XMLUtilities;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public abstract class AbstractText extends AbstractCharacterData implements Text
{
    public Text splitText(final int endIndex) throws DOMException {
        if (this.isReadonly()) {
            throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(this.getNodeType()), this.getNodeName() });
        }
        final String nodeValue = this.getNodeValue();
        if (endIndex < 0 || endIndex >= nodeValue.length()) {
            throw this.createDOMException((short)1, "offset", new Object[] { new Integer(endIndex) });
        }
        final Node parentNode = this.getParentNode();
        if (parentNode == null) {
            throw this.createDOMException((short)1, "need.parent", new Object[0]);
        }
        final Text textNode = this.createTextNode(nodeValue.substring(endIndex));
        final Node nextSibling = this.getNextSibling();
        if (nextSibling != null) {
            parentNode.insertBefore(textNode, nextSibling);
        }
        else {
            parentNode.appendChild(textNode);
        }
        this.setNodeValue(nodeValue.substring(0, endIndex));
        return textNode;
    }
    
    protected Node getPreviousLogicallyAdjacentTextNode(final Node node) {
        Node node2 = node.getPreviousSibling();
        Node node4;
        for (Node node3 = node.getParentNode(); node2 == null && node3 != null && node3.getNodeType() == 5; node3 = node4.getParentNode(), node2 = node4.getPreviousSibling()) {
            node4 = node3;
        }
        while (node2 != null && node2.getNodeType() == 5) {
            node2 = node2.getLastChild();
        }
        if (node2 == null) {
            return null;
        }
        final short nodeType = node2.getNodeType();
        if (nodeType == 3 || nodeType == 4) {
            return node2;
        }
        return null;
    }
    
    protected Node getNextLogicallyAdjacentTextNode(final Node node) {
        Node node2 = node.getNextSibling();
        Node node4;
        for (Node node3 = node.getParentNode(); node2 == null && node3 != null && node3.getNodeType() == 5; node3 = node4.getParentNode(), node2 = node4.getNextSibling()) {
            node4 = node3;
        }
        while (node2 != null && node2.getNodeType() == 5) {
            node2 = node2.getFirstChild();
        }
        if (node2 == null) {
            return null;
        }
        final short nodeType = node2.getNodeType();
        if (nodeType == 3 || nodeType == 4) {
            return node2;
        }
        return null;
    }
    
    public String getWholeText() {
        final StringBuffer sb = new StringBuffer();
        for (Node previousLogicallyAdjacentTextNode = this; previousLogicallyAdjacentTextNode != null; previousLogicallyAdjacentTextNode = this.getPreviousLogicallyAdjacentTextNode(previousLogicallyAdjacentTextNode)) {
            sb.insert(0, previousLogicallyAdjacentTextNode.getNodeValue());
        }
        for (Node node = this.getNextLogicallyAdjacentTextNode(this); node != null; node = this.getNextLogicallyAdjacentTextNode(node)) {
            sb.append(node.getNodeValue());
        }
        return sb.toString();
    }
    
    public boolean isElementContentWhitespace() {
        for (int length = this.nodeValue.length(), i = 0; i < length; ++i) {
            if (!XMLUtilities.isXMLSpace(this.nodeValue.charAt(i))) {
                return false;
            }
        }
        final Node parentNode = this.getParentNode();
        return parentNode.getNodeType() != 1 || !XMLSupport.getXMLSpace((Element)parentNode).equals("preserve");
    }
    
    public Text replaceWholeText(final String nodeValue) throws DOMException {
        for (Node node = this.getPreviousLogicallyAdjacentTextNode(this); node != null; node = this.getPreviousLogicallyAdjacentTextNode(node)) {
            if (((AbstractNode)node).isReadonly()) {
                throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(node.getNodeType()), node.getNodeName() });
            }
        }
        for (Node node2 = this.getNextLogicallyAdjacentTextNode(this); node2 != null; node2 = this.getNextLogicallyAdjacentTextNode(node2)) {
            if (((AbstractNode)node2).isReadonly()) {
                throw this.createDOMException((short)7, "readonly.node", new Object[] { new Integer(node2.getNodeType()), node2.getNodeName() });
            }
        }
        final Node parentNode = this.getParentNode();
        for (Node node3 = this.getPreviousLogicallyAdjacentTextNode(this); node3 != null; node3 = this.getPreviousLogicallyAdjacentTextNode(node3)) {
            parentNode.removeChild(node3);
        }
        for (Node node4 = this.getNextLogicallyAdjacentTextNode(this); node4 != null; node4 = this.getNextLogicallyAdjacentTextNode(node4)) {
            parentNode.removeChild(node4);
        }
        if (this.isReadonly()) {
            final Text textNode = this.createTextNode(nodeValue);
            parentNode.replaceChild(textNode, this);
            return textNode;
        }
        this.setNodeValue(nodeValue);
        return this;
    }
    
    public String getTextContent() {
        if (this.isElementContentWhitespace()) {
            return "";
        }
        return this.getNodeValue();
    }
    
    protected abstract Text createTextNode(final String p0);
}
