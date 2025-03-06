// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.NodeList;
import org.apache.batik.dom.xbl.XBLShadowTreeElement;
import org.apache.batik.dom.xbl.NodeXBL;
import org.w3c.dom.Node;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.Element;
import org.apache.batik.bridge.DocumentLoader;
import org.w3c.dom.svg.SVGDocument;
import org.apache.batik.bridge.URIResolver;

public class SVG12URIResolver extends URIResolver
{
    public SVG12URIResolver(final SVGDocument svgDocument, final DocumentLoader documentLoader) {
        super(svgDocument, documentLoader);
    }
    
    protected String getRefererBaseURI(final Element element) {
        final AbstractNode abstractNode = (AbstractNode)element;
        if (abstractNode.getXblBoundElement() != null) {
            return null;
        }
        return abstractNode.getBaseURI();
    }
    
    protected Node getNodeByFragment(final String s, final Element element) {
        final NodeXBL nodeXBL = (NodeXBL)element;
        final NodeXBL nodeXBL2 = (NodeXBL)nodeXBL.getXblBoundElement();
        if (nodeXBL2 != null) {
            final Element elementById = ((XBLShadowTreeElement)nodeXBL2.getXblShadowTree()).getElementById(s);
            if (elementById != null) {
                return elementById;
            }
            final NodeList xblDefinitions = nodeXBL.getXblDefinitions();
            for (int i = 0; i < xblDefinitions.getLength(); ++i) {
                final Element elementById2 = xblDefinitions.item(i).getOwnerDocument().getElementById(s);
                if (elementById2 != null) {
                    return elementById2;
                }
            }
        }
        return super.getNodeByFragment(s, element);
    }
}
