// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.w3c.dom.NodeList;
import org.apache.batik.gvt.text.ArabicTextHandler;
import org.apache.batik.gvt.font.GVTFontFace;
import org.w3c.dom.Element;

public class SVGFontElementBridge extends AbstractSVGBridge
{
    public String getLocalName() {
        return "font";
    }
    
    public SVGGVTFont createFont(final BridgeContext bridgeContext, final Element element, final Element element2, final float n, final GVTFontFace gvtFontFace) {
        final NodeList elementsByTagNameNS = element.getElementsByTagNameNS("http://www.w3.org/2000/svg", "glyph");
        final int length = elementsByTagNameNS.getLength();
        final String[] array = new String[length];
        final String[] array2 = new String[length];
        final String[] array3 = new String[length];
        final String[] array4 = new String[length];
        final String[] array5 = new String[length];
        final Element[] array6 = new Element[length];
        for (int i = 0; i < length; ++i) {
            final Element element3 = (Element)elementsByTagNameNS.item(i);
            array[i] = element3.getAttributeNS(null, "unicode");
            if (array[i].length() > 1 && ArabicTextHandler.arabicChar(array[i].charAt(0))) {
                array[i] = new StringBuffer(array[i]).reverse().toString();
            }
            array2[i] = element3.getAttributeNS(null, "glyph-name");
            array3[i] = element3.getAttributeNS(null, "lang");
            array4[i] = element3.getAttributeNS(null, "orientation");
            array5[i] = element3.getAttributeNS(null, "arabic-form");
            array6[i] = element3;
        }
        final NodeList elementsByTagNameNS2 = element.getElementsByTagNameNS("http://www.w3.org/2000/svg", "missing-glyph");
        Element element4 = null;
        if (elementsByTagNameNS2.getLength() > 0) {
            element4 = (Element)elementsByTagNameNS2.item(0);
        }
        final NodeList elementsByTagNameNS3 = element.getElementsByTagNameNS("http://www.w3.org/2000/svg", "hkern");
        final Element[] array7 = new Element[elementsByTagNameNS3.getLength()];
        for (int j = 0; j < array7.length; ++j) {
            array7[j] = (Element)elementsByTagNameNS3.item(j);
        }
        final NodeList elementsByTagNameNS4 = element.getElementsByTagNameNS("http://www.w3.org/2000/svg", "vkern");
        final Element[] array8 = new Element[elementsByTagNameNS4.getLength()];
        for (int k = 0; k < array8.length; ++k) {
            array8[k] = (Element)elementsByTagNameNS4.item(k);
        }
        return new SVGGVTFont(n, gvtFontFace, array, array2, array3, array4, array5, bridgeContext, array6, element4, array7, array8, element2);
    }
}
