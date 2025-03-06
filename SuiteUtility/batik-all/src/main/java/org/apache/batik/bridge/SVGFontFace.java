// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.gvt.font.GVTFontFace;
import java.util.List;
import org.apache.batik.gvt.font.GVTFontFamily;
import org.w3c.dom.Element;

public class SVGFontFace extends FontFace
{
    Element fontFaceElement;
    GVTFontFamily fontFamily;
    
    public SVGFontFace(final Element fontFaceElement, final List list, final String s, final float n, final String s2, final String s3, final String s4, final String s5, final float n2, final String s6, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8, final float n9, final float n10) {
        super(list, s, n, s2, s3, s4, s5, n2, s6, n3, n4, n5, n6, n7, n8, n9, n10);
        this.fontFamily = null;
        this.fontFaceElement = fontFaceElement;
    }
    
    public GVTFontFamily getFontFamily(final BridgeContext bridgeContext) {
        if (this.fontFamily != null) {
            return this.fontFamily;
        }
        final Element parentElement = SVGUtilities.getParentElement(this.fontFaceElement);
        if (parentElement.getNamespaceURI().equals("http://www.w3.org/2000/svg") && parentElement.getLocalName().equals("font")) {
            return new SVGFontFamily(this, parentElement, bridgeContext);
        }
        return this.fontFamily = super.getFontFamily(bridgeContext);
    }
    
    public Element getFontFaceElement() {
        return this.fontFaceElement;
    }
    
    protected Element getBaseElement(final BridgeContext bridgeContext) {
        if (this.fontFaceElement != null) {
            return this.fontFaceElement;
        }
        return super.getBaseElement(bridgeContext);
    }
}
