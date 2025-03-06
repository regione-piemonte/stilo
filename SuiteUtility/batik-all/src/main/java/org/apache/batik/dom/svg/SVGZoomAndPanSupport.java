// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import org.w3c.dom.DOMException;
import org.apache.batik.dom.AbstractNode;
import org.w3c.dom.Element;
import org.apache.batik.util.SVGConstants;

public class SVGZoomAndPanSupport implements SVGConstants
{
    protected SVGZoomAndPanSupport() {
    }
    
    public static void setZoomAndPan(final Element element, final short value) throws DOMException {
        switch (value) {
            case 1: {
                element.setAttributeNS(null, "zoomAndPan", "disable");
                break;
            }
            case 2: {
                element.setAttributeNS(null, "zoomAndPan", "magnify");
                break;
            }
            default: {
                throw ((AbstractNode)element).createDOMException((short)13, "zoom.and.pan", new Object[] { new Integer(value) });
            }
        }
    }
    
    public static short getZoomAndPan(final Element element) {
        if (element.getAttributeNS(null, "zoomAndPan").equals("magnify")) {
            return 2;
        }
        return 1;
    }
}
