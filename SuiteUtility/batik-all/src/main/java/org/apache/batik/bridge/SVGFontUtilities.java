// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.Map;
import java.util.StringTokenizer;
import java.util.Collection;
import java.util.ArrayList;
import org.apache.batik.gvt.font.UnresolvedFontFamily;
import org.apache.batik.gvt.font.GVTFontFamily;
import java.util.Iterator;
import org.apache.batik.css.engine.CSSEngine;
import org.w3c.dom.NodeList;
import org.apache.batik.css.engine.FontFaceRule;
import org.apache.batik.dom.svg.SVGOMDocument;
import org.w3c.dom.Element;
import java.util.LinkedList;
import java.util.List;
import org.w3c.dom.Document;
import org.apache.batik.util.SVGConstants;

public abstract class SVGFontUtilities implements SVGConstants
{
    public static List getFontFaces(final Document document, final BridgeContext bridgeContext) {
        final List list = bridgeContext.getFontFamilyMap().get(document);
        if (list != null) {
            return list;
        }
        final LinkedList<SVGFontFace> list2 = new LinkedList<SVGFontFace>();
        final NodeList elementsByTagNameNS = document.getElementsByTagNameNS("http://www.w3.org/2000/svg", "font-face");
        final SVGFontFaceElementBridge svgFontFaceElementBridge = (SVGFontFaceElementBridge)bridgeContext.getBridge("http://www.w3.org/2000/svg", "font-face");
        for (int i = 0; i < elementsByTagNameNS.getLength(); ++i) {
            list2.add(svgFontFaceElementBridge.createFontFace(bridgeContext, (Element)elementsByTagNameNS.item(i)));
        }
        final CSSEngine cssEngine = ((SVGOMDocument)document).getCSSEngine();
        final Iterator iterator = cssEngine.getFontFaces().iterator();
        while (iterator.hasNext()) {
            list2.add((SVGFontFace)CSSFontFace.createCSSFontFace(cssEngine, iterator.next()));
        }
        return list2;
    }
    
    public static GVTFontFamily getFontFamily(final Element element, final BridgeContext bridgeContext, final String s, final String str, final String s2) {
        final String string = s.toLowerCase() + " " + str + " " + s2;
        final Map fontFamilyMap = bridgeContext.getFontFamilyMap();
        final UnresolvedFontFamily unresolvedFontFamily = fontFamilyMap.get(string);
        if (unresolvedFontFamily != null) {
            return unresolvedFontFamily;
        }
        final Document ownerDocument = element.getOwnerDocument();
        List fontFaces = (List)fontFamilyMap.get(ownerDocument);
        if (fontFaces == null) {
            fontFaces = getFontFaces(ownerDocument, bridgeContext);
            fontFamilyMap.put(ownerDocument, fontFaces);
        }
        final Iterator<FontFace> iterator = fontFaces.iterator();
        final LinkedList<GVTFontFamily> list = new LinkedList<GVTFontFamily>();
        while (iterator.hasNext()) {
            final FontFace fontFace = iterator.next();
            if (!fontFace.hasFamilyName(s)) {
                continue;
            }
            final String fontStyle = fontFace.getFontStyle();
            if (!fontStyle.equals("all") && fontStyle.indexOf(s2) == -1) {
                continue;
            }
            final GVTFontFamily fontFamily = fontFace.getFontFamily(bridgeContext);
            if (fontFamily == null) {
                continue;
            }
            list.add(fontFamily);
        }
        if (list.size() == 1) {
            fontFamilyMap.put(string, list.get(0));
            return (UnresolvedFontFamily)list.get(0);
        }
        if (list.size() > 1) {
            final String fontWeightNumberString = getFontWeightNumberString(str);
            final ArrayList c = new ArrayList<String>(list.size());
            final Iterator<Object> iterator2 = list.iterator();
            while (iterator2.hasNext()) {
                c.add(getFontWeightNumberString(iterator2.next().getFontFace().getFontWeight()));
            }
            final ArrayList list2 = new ArrayList<String>(c);
            for (int i = 100; i <= 900; i += 100) {
                final String value = String.valueOf(i);
                boolean b = false;
                int n = 1000;
                int n2 = 0;
                for (int j = 0; j < c.size(); ++j) {
                    final String str2 = c.get(j);
                    if (str2.indexOf(value) > -1) {
                        b = true;
                        break;
                    }
                    final StringTokenizer stringTokenizer = new StringTokenizer(str2, " ,");
                    while (stringTokenizer.hasMoreTokens()) {
                        final int abs = Math.abs(Integer.parseInt(stringTokenizer.nextToken()) - i);
                        if (abs < n) {
                            n = abs;
                            n2 = j;
                        }
                    }
                }
                if (!b) {
                    list2.set(n2, list2.get(n2) + ", " + value);
                }
            }
            for (int k = 0; k < list.size(); ++k) {
                if (((String)list2.get(k)).indexOf(fontWeightNumberString) > -1) {
                    fontFamilyMap.put(string, list.get(k));
                    return list.get(k);
                }
            }
            fontFamilyMap.put(string, list.get(0));
            return (UnresolvedFontFamily)list.get(0);
        }
        final UnresolvedFontFamily unresolvedFontFamily2 = new UnresolvedFontFamily(s);
        fontFamilyMap.put(string, unresolvedFontFamily2);
        return unresolvedFontFamily2;
    }
    
    protected static String getFontWeightNumberString(final String s) {
        if (s.equals("normal")) {
            return "400";
        }
        if (s.equals("bold")) {
            return "700";
        }
        if (s.equals("all")) {
            return "100, 200, 300, 400, 500, 600, 700, 800, 900";
        }
        return s;
    }
}
