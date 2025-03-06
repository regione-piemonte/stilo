// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.IOException;
import java.awt.color.ICC_Profile;
import org.apache.batik.util.ParsedURL;
import org.apache.batik.dom.AbstractNode;
import org.apache.batik.dom.util.XLinkSupport;
import org.apache.batik.ext.awt.color.ICCColorSpaceExt;
import org.w3c.dom.Element;
import org.apache.batik.ext.awt.color.NamedProfileCache;

public class SVGColorProfileElementBridge extends AbstractSVGBridge implements ErrorConstants
{
    public NamedProfileCache cache;
    
    public SVGColorProfileElementBridge() {
        this.cache = new NamedProfileCache();
    }
    
    public String getLocalName() {
        return "color-profile";
    }
    
    public ICCColorSpaceExt createICCColorSpaceExt(final BridgeContext bridgeContext, final Element element, final String s) {
        final ICCColorSpaceExt request = this.cache.request(s.toLowerCase());
        if (request != null) {
            return request;
        }
        final NodeList elementsByTagNameNS = element.getOwnerDocument().getElementsByTagNameNS("http://www.w3.org/2000/svg", "color-profile");
        final int length = elementsByTagNameNS.getLength();
        Object o = null;
        for (int i = 0; i < length; ++i) {
            final Node item = elementsByTagNameNS.item(i);
            if (item.getNodeType() == 1) {
                final Element element2 = (Element)item;
                if (s.equalsIgnoreCase(element2.getAttributeNS(null, "name"))) {
                    o = element2;
                }
            }
        }
        if (o == null) {
            return null;
        }
        final String xLinkHref = XLinkSupport.getXLinkHref((Element)o);
        ICC_Profile instance = null;
        if (xLinkHref != null) {
            final String baseURI = ((AbstractNode)o).getBaseURI();
            ParsedURL parsedURL = null;
            if (baseURI != null) {
                parsedURL = new ParsedURL(baseURI);
            }
            final ParsedURL parsedURL2 = new ParsedURL(parsedURL, xLinkHref);
            if (!parsedURL2.complete()) {
                throw new BridgeException(bridgeContext, element, "uri.malformed", new Object[] { xLinkHref });
            }
            try {
                bridgeContext.getUserAgent().checkLoadExternalResource(parsedURL2, parsedURL);
                instance = ICC_Profile.getInstance(parsedURL2.openStream());
            }
            catch (IOException ex) {
                throw new BridgeException(bridgeContext, element, ex, "uri.io", new Object[] { xLinkHref });
            }
            catch (SecurityException ex2) {
                throw new BridgeException(bridgeContext, element, ex2, "uri.unsecure", new Object[] { xLinkHref });
            }
        }
        if (instance == null) {
            return null;
        }
        final ICCColorSpaceExt iccColorSpaceExt = new ICCColorSpaceExt(instance, convertIntent((Element)o, bridgeContext));
        this.cache.put(s.toLowerCase(), iccColorSpaceExt);
        return iccColorSpaceExt;
    }
    
    private static int convertIntent(final Element element, final BridgeContext bridgeContext) {
        final String attributeNS = element.getAttributeNS(null, "rendering-intent");
        if (attributeNS.length() == 0) {
            return 4;
        }
        if ("perceptual".equals(attributeNS)) {
            return 0;
        }
        if ("auto".equals(attributeNS)) {
            return 4;
        }
        if ("relative-colorimetric".equals(attributeNS)) {
            return 1;
        }
        if ("absolute-colorimetric".equals(attributeNS)) {
            return 2;
        }
        if ("saturation".equals(attributeNS)) {
            return 3;
        }
        throw new BridgeException(bridgeContext, element, "attribute.malformed", new Object[] { "rendering-intent", attributeNS });
    }
}
