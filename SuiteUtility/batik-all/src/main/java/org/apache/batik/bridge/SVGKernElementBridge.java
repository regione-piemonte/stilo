// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge;

import java.util.StringTokenizer;
import org.apache.batik.gvt.font.UnicodeRange;
import java.util.ArrayList;
import org.apache.batik.gvt.font.Kern;
import org.w3c.dom.Element;

public abstract class SVGKernElementBridge extends AbstractSVGBridge
{
    public Kern createKern(final BridgeContext bridgeContext, final Element element, final SVGGVTFont svggvtFont) {
        final String attributeNS = element.getAttributeNS(null, "u1");
        final String attributeNS2 = element.getAttributeNS(null, "u2");
        final String attributeNS3 = element.getAttributeNS(null, "g1");
        final String attributeNS4 = element.getAttributeNS(null, "g2");
        String attributeNS5 = element.getAttributeNS(null, "k");
        if (attributeNS5.length() == 0) {
            attributeNS5 = "0";
        }
        final float float1 = Float.parseFloat(attributeNS5);
        int n = 0;
        int n2 = 0;
        int[] array = null;
        int[] array2 = null;
        final ArrayList list = new ArrayList<UnicodeRange>();
        final ArrayList list2 = new ArrayList<UnicodeRange>();
        final StringTokenizer stringTokenizer = new StringTokenizer(attributeNS, ",");
        while (stringTokenizer.hasMoreTokens()) {
            final String nextToken = stringTokenizer.nextToken();
            if (nextToken.startsWith("U+")) {
                list.add(new UnicodeRange(nextToken));
            }
            else {
                final int[] glyphCodesForUnicode = svggvtFont.getGlyphCodesForUnicode(nextToken);
                if (array == null) {
                    array = glyphCodesForUnicode;
                    n = glyphCodesForUnicode.length;
                }
                else {
                    if (n + glyphCodesForUnicode.length > array.length) {
                        int n3 = array.length * 2;
                        if (n3 < n + glyphCodesForUnicode.length) {
                            n3 = n + glyphCodesForUnicode.length;
                        }
                        final int[] array3 = new int[n3];
                        System.arraycopy(array, 0, array3, 0, n);
                        array = array3;
                    }
                    for (int i = 0; i < glyphCodesForUnicode.length; ++i) {
                        array[n++] = glyphCodesForUnicode[i];
                    }
                }
            }
        }
        final StringTokenizer stringTokenizer2 = new StringTokenizer(attributeNS2, ",");
        while (stringTokenizer2.hasMoreTokens()) {
            final String nextToken2 = stringTokenizer2.nextToken();
            if (nextToken2.startsWith("U+")) {
                list2.add(new UnicodeRange(nextToken2));
            }
            else {
                final int[] glyphCodesForUnicode2 = svggvtFont.getGlyphCodesForUnicode(nextToken2);
                if (array2 == null) {
                    array2 = glyphCodesForUnicode2;
                    n2 = glyphCodesForUnicode2.length;
                }
                else {
                    if (n2 + glyphCodesForUnicode2.length > array2.length) {
                        int n4 = array2.length * 2;
                        if (n4 < n2 + glyphCodesForUnicode2.length) {
                            n4 = n2 + glyphCodesForUnicode2.length;
                        }
                        final int[] array4 = new int[n4];
                        System.arraycopy(array2, 0, array4, 0, n2);
                        array2 = array4;
                    }
                    for (int j = 0; j < glyphCodesForUnicode2.length; ++j) {
                        array2[n2++] = glyphCodesForUnicode2[j];
                    }
                }
            }
        }
        final StringTokenizer stringTokenizer3 = new StringTokenizer(attributeNS3, ",");
        while (stringTokenizer3.hasMoreTokens()) {
            final int[] glyphCodesForName = svggvtFont.getGlyphCodesForName(stringTokenizer3.nextToken());
            if (array == null) {
                array = glyphCodesForName;
                n = glyphCodesForName.length;
            }
            else {
                if (n + glyphCodesForName.length > array.length) {
                    int n5 = array.length * 2;
                    if (n5 < n + glyphCodesForName.length) {
                        n5 = n + glyphCodesForName.length;
                    }
                    final int[] array5 = new int[n5];
                    System.arraycopy(array, 0, array5, 0, n);
                    array = array5;
                }
                for (int k = 0; k < glyphCodesForName.length; ++k) {
                    array[n++] = glyphCodesForName[k];
                }
            }
        }
        final StringTokenizer stringTokenizer4 = new StringTokenizer(attributeNS4, ",");
        while (stringTokenizer4.hasMoreTokens()) {
            final int[] glyphCodesForName2 = svggvtFont.getGlyphCodesForName(stringTokenizer4.nextToken());
            if (array2 == null) {
                array2 = glyphCodesForName2;
                n2 = glyphCodesForName2.length;
            }
            else {
                if (n2 + glyphCodesForName2.length > array2.length) {
                    int n6 = array2.length * 2;
                    if (n6 < n2 + glyphCodesForName2.length) {
                        n6 = n2 + glyphCodesForName2.length;
                    }
                    final int[] array6 = new int[n6];
                    System.arraycopy(array2, 0, array6, 0, n2);
                    array2 = array6;
                }
                for (int l = 0; l < glyphCodesForName2.length; ++l) {
                    array2[n2++] = glyphCodesForName2[l];
                }
            }
        }
        int[] array7;
        if (n == 0 || n == array.length) {
            array7 = array;
        }
        else {
            array7 = new int[n];
            System.arraycopy(array, 0, array7, 0, n);
        }
        int[] array8;
        if (n2 == 0 || n2 == array2.length) {
            array8 = array2;
        }
        else {
            array8 = new int[n2];
            System.arraycopy(array2, 0, array8, 0, n2);
        }
        final UnicodeRange[] array9 = new UnicodeRange[list.size()];
        list.toArray(array9);
        final UnicodeRange[] array10 = new UnicodeRange[list2.size()];
        list2.toArray(array10);
        return new Kern(array7, array8, array9, array10, float1);
    }
}
