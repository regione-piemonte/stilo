// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import org.apache.batik.gvt.text.GVTAttributedCharacterIterator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.lang.ref.SoftReference;
import java.util.Map;
import org.apache.batik.gvt.font.GVTFont;
import org.w3c.dom.Element;
import org.apache.batik.gvt.font.GVTFontFace;
import java.text.AttributedCharacterIterator;
import org.apache.batik.gvt.font.GVTFontFamily;

public class SVGFontFamily implements GVTFontFamily
{
    public static final AttributedCharacterIterator.Attribute TEXT_COMPOUND_ID;
    protected GVTFontFace fontFace;
    protected Element fontElement;
    protected BridgeContext ctx;
    protected Boolean complex;
    
    public SVGFontFamily(final GVTFontFace fontFace, final Element fontElement, final BridgeContext ctx) {
        this.complex = null;
        this.fontFace = fontFace;
        this.fontElement = fontElement;
        this.ctx = ctx;
    }
    
    public String getFamilyName() {
        return this.fontFace.getFamilyName();
    }
    
    public GVTFontFace getFontFace() {
        return this.fontFace;
    }
    
    public GVTFont deriveFont(final float n, final AttributedCharacterIterator attributedCharacterIterator) {
        return this.deriveFont(n, attributedCharacterIterator.getAttributes());
    }
    
    public GVTFont deriveFont(final float n, final Map map) {
        return ((SVGFontElementBridge)this.ctx.getBridge(this.fontElement)).createFont(this.ctx, this.fontElement, map.get(SVGFontFamily.TEXT_COMPOUND_ID).get(), n, this.fontFace);
    }
    
    public boolean isComplex() {
        if (this.complex != null) {
            return this.complex;
        }
        final boolean complex = isComplex(this.fontElement, this.ctx);
        this.complex = (complex ? Boolean.TRUE : Boolean.FALSE);
        return complex;
    }
    
    public static boolean isComplex(final Element element, final BridgeContext bridgeContext) {
        final NodeList elementsByTagNameNS = element.getElementsByTagNameNS("http://www.w3.org/2000/svg", "glyph");
        for (int length = elementsByTagNameNS.getLength(), i = 0; i < length; ++i) {
            for (Node node = ((Element)elementsByTagNameNS.item(i)).getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == 1) {
                    final Bridge bridge = bridgeContext.getBridge((Element)node);
                    if (bridge != null && bridge instanceof GraphicsNodeBridge) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    static {
        TEXT_COMPOUND_ID = GVTAttributedCharacterIterator.TextAttribute.TEXT_COMPOUND_ID;
    }
}
