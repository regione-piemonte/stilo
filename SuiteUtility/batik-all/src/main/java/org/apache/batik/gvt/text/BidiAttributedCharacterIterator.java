// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import java.util.Set;
import java.util.Iterator;
import java.awt.font.TextLayout;
import java.util.Map;
import java.util.HashMap;
import java.text.AttributedString;
import java.awt.font.FontRenderContext;
import java.text.AttributedCharacterIterator;

public class BidiAttributedCharacterIterator implements AttributedCharacterIterator
{
    private AttributedCharacterIterator reorderedACI;
    private FontRenderContext frc;
    private int chunkStart;
    private int[] newCharOrder;
    private static final Float FLOAT_NAN;
    
    protected BidiAttributedCharacterIterator(final AttributedCharacterIterator reorderedACI, final FontRenderContext frc, final int chunkStart, final int[] newCharOrder) {
        this.reorderedACI = reorderedACI;
        this.frc = frc;
        this.chunkStart = chunkStart;
        this.newCharOrder = newCharOrder;
    }
    
    public BidiAttributedCharacterIterator(AttributedCharacterIterator iterator, final FontRenderContext fontRenderContext, final int chunkStart) {
        this.frc = fontRenderContext;
        this.chunkStart = chunkStart;
        iterator.first();
        final int n = iterator.getEndIndex() - iterator.getBeginIndex();
        final StringBuffer sb = new StringBuffer(n);
        char c = iterator.first();
        for (int i = 0; i < n; ++i) {
            sb.append(c);
            c = iterator.next();
        }
        final AttributedString attributedString = new AttributedString(sb.toString());
        final int beginIndex = iterator.getBeginIndex();
        int runLimit;
        for (int endIndex = iterator.getEndIndex(), j = beginIndex; j < endIndex; j = runLimit) {
            iterator.setIndex(j);
            final Map<Attribute, Object> attributes = iterator.getAttributes();
            runLimit = iterator.getRunLimit();
            final HashMap attributes2 = new HashMap<Attribute, Object>(attributes.size());
            for (final Map.Entry<Attribute, V> entry : attributes.entrySet()) {
                final Object key = entry.getKey();
                if (key == null) {
                    continue;
                }
                final Object value = entry.getValue();
                if (value == null) {
                    continue;
                }
                attributes2.put((Attribute)key, value);
            }
            attributedString.addAttributes((Map<? extends Attribute, ?>)attributes2, j - beginIndex, runLimit - beginIndex);
        }
        final TextLayout textLayout = new TextLayout(attributedString.getIterator(), fontRenderContext);
        final int[] array = new int[n];
        final int[] array2 = new int[n];
        int n2 = 0;
        byte characterLevel = textLayout.getCharacterLevel(0);
        array2[array[0] = 0] = characterLevel;
        byte b = characterLevel;
        for (int k = 1; k < n; ++k) {
            final byte characterLevel2 = textLayout.getCharacterLevel(k);
            array[k] = k;
            if ((array2[k] = characterLevel2) != characterLevel) {
                attributedString.addAttribute(GVTAttributedCharacterIterator.TextAttribute.BIDI_LEVEL, new Integer(characterLevel), n2, k);
                n2 = k;
                if ((characterLevel = characterLevel2) > b) {
                    b = characterLevel2;
                }
            }
        }
        attributedString.addAttribute(GVTAttributedCharacterIterator.TextAttribute.BIDI_LEVEL, new Integer(characterLevel), n2, n);
        iterator = attributedString.getIterator();
        if (n2 == 0 && characterLevel == 0) {
            this.reorderedACI = iterator;
            this.newCharOrder = new int[n];
            for (int l = 0; l < n; ++l) {
                this.newCharOrder[l] = chunkStart + l;
            }
            return;
        }
        this.newCharOrder = this.doBidiReorder(array, array2, n, b);
        final StringBuffer sb2 = new StringBuffer(n);
        int n3 = 0;
        for (int n4 = 0; n4 < n; ++n4) {
            final int n5 = this.newCharOrder[n4];
            char setIndex = iterator.setIndex(n5);
            if (n5 == 0) {
                n3 = n4;
            }
            if ((textLayout.getCharacterLevel(n5) & 0x1) != 0x0) {
                setIndex = (char)mirrorChar(setIndex);
            }
            sb2.append(setIndex);
        }
        final AttributedString attributedString2 = new AttributedString(sb2.toString());
        final Map[] array3 = new Map[n];
        final int beginIndex2 = iterator.getBeginIndex();
        int runLimit2;
        for (int endIndex2 = iterator.getEndIndex(), index = beginIndex2; index < endIndex2; index = runLimit2) {
            iterator.setIndex(index);
            final Map<Attribute, Object> attributes3 = iterator.getAttributes();
            runLimit2 = iterator.getRunLimit();
            for (int n6 = index; n6 < runLimit2; ++n6) {
                array3[n6 - beginIndex2] = attributes3;
            }
        }
        int n7 = 0;
        Map<? extends Attribute, ?> map = (Map<? extends Attribute, ?>)array3[this.newCharOrder[0]];
        for (int endIndex3 = 1; endIndex3 < n; ++endIndex3) {
            final Map map2 = array3[this.newCharOrder[endIndex3]];
            if (map2 != map) {
                attributedString2.addAttributes(map, n7, endIndex3);
                map = (Map<? extends Attribute, ?>)map2;
                n7 = endIndex3;
            }
        }
        attributedString2.addAttributes(map, n7, n);
        iterator.first();
        final Float value2 = (Float)iterator.getAttribute(GVTAttributedCharacterIterator.TextAttribute.X);
        if (value2 != null && !value2.isNaN()) {
            attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.X, BidiAttributedCharacterIterator.FLOAT_NAN, n3, n3 + 1);
            attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.X, value2, 0, 1);
        }
        final Float value3 = (Float)iterator.getAttribute(GVTAttributedCharacterIterator.TextAttribute.Y);
        if (value3 != null && !value3.isNaN()) {
            attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.Y, BidiAttributedCharacterIterator.FLOAT_NAN, n3, n3 + 1);
            attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.Y, value3, 0, 1);
        }
        final Float value4 = (Float)iterator.getAttribute(GVTAttributedCharacterIterator.TextAttribute.DX);
        if (value4 != null && !value4.isNaN()) {
            attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.DX, BidiAttributedCharacterIterator.FLOAT_NAN, n3, n3 + 1);
            attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.DX, value4, 0, 1);
        }
        final Float value5 = (Float)iterator.getAttribute(GVTAttributedCharacterIterator.TextAttribute.DY);
        if (value5 != null && !value5.isNaN()) {
            attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.DY, BidiAttributedCharacterIterator.FLOAT_NAN, n3, n3 + 1);
            attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.DY, value5, 0, 1);
        }
        final AttributedString assignArabicForms = ArabicTextHandler.assignArabicForms(attributedString2);
        for (int n8 = 0; n8 < this.newCharOrder.length; ++n8) {
            final int[] newCharOrder = this.newCharOrder;
            final int n9 = n8;
            newCharOrder[n9] += chunkStart;
        }
        this.reorderedACI = assignArabicForms.getIterator();
    }
    
    public int[] getCharMap() {
        return this.newCharOrder;
    }
    
    private int[] doBidiReorder(final int[] array, final int[] array2, final int n, final int n2) {
        if (n2 == 0) {
            return array;
        }
        int i = 0;
        while (i < n) {
            while (i < n && array2[i] < n2) {
                ++i;
            }
            if (i == n) {
                break;
            }
            final int n3 = i;
            ++i;
            while (i < n && array2[i] == n2) {
                ++i;
            }
            final int n4 = i - 1;
            for (int n5 = (n4 - n3 >> 1) + 1, j = 0; j < n5; ++j) {
                final int n6 = array[n3 + j];
                array[n3 + j] = array[n4 - j];
                array[n4 - j] = n6;
                array2[n4 - j] = (array2[n3 + j] = n2 - 1);
            }
        }
        return this.doBidiReorder(array, array2, n, n2 - 1);
    }
    
    public Set getAllAttributeKeys() {
        return this.reorderedACI.getAllAttributeKeys();
    }
    
    public Object getAttribute(final Attribute attribute) {
        return this.reorderedACI.getAttribute(attribute);
    }
    
    public Map getAttributes() {
        return this.reorderedACI.getAttributes();
    }
    
    public int getRunLimit() {
        return this.reorderedACI.getRunLimit();
    }
    
    public int getRunLimit(final Attribute attribute) {
        return this.reorderedACI.getRunLimit(attribute);
    }
    
    public int getRunLimit(final Set set) {
        return this.reorderedACI.getRunLimit(set);
    }
    
    public int getRunStart() {
        return this.reorderedACI.getRunStart();
    }
    
    public int getRunStart(final Attribute attribute) {
        return this.reorderedACI.getRunStart(attribute);
    }
    
    public int getRunStart(final Set set) {
        return this.reorderedACI.getRunStart(set);
    }
    
    public Object clone() {
        return new BidiAttributedCharacterIterator((AttributedCharacterIterator)this.reorderedACI.clone(), this.frc, this.chunkStart, this.newCharOrder.clone());
    }
    
    public char current() {
        return this.reorderedACI.current();
    }
    
    public char first() {
        return this.reorderedACI.first();
    }
    
    public int getBeginIndex() {
        return this.reorderedACI.getBeginIndex();
    }
    
    public int getEndIndex() {
        return this.reorderedACI.getEndIndex();
    }
    
    public int getIndex() {
        return this.reorderedACI.getIndex();
    }
    
    public char last() {
        return this.reorderedACI.last();
    }
    
    public char next() {
        return this.reorderedACI.next();
    }
    
    public char previous() {
        return this.reorderedACI.previous();
    }
    
    public char setIndex(final int index) {
        return this.reorderedACI.setIndex(index);
    }
    
    public static int mirrorChar(final int n) {
        switch (n) {
            case 40: {
                return 41;
            }
            case 41: {
                return 40;
            }
            case 60: {
                return 62;
            }
            case 62: {
                return 60;
            }
            case 91: {
                return 93;
            }
            case 93: {
                return 91;
            }
            case 123: {
                return 125;
            }
            case 125: {
                return 123;
            }
            case 171: {
                return 187;
            }
            case 187: {
                return 171;
            }
            case 8249: {
                return 8250;
            }
            case 8250: {
                return 8249;
            }
            case 8261: {
                return 8262;
            }
            case 8262: {
                return 8261;
            }
            case 8317: {
                return 8318;
            }
            case 8318: {
                return 8317;
            }
            case 8333: {
                return 8334;
            }
            case 8334: {
                return 8333;
            }
            case 8712: {
                return 8715;
            }
            case 8713: {
                return 8716;
            }
            case 8714: {
                return 8717;
            }
            case 8715: {
                return 8712;
            }
            case 8716: {
                return 8713;
            }
            case 8717: {
                return 8714;
            }
            case 8764: {
                return 8765;
            }
            case 8765: {
                return 8764;
            }
            case 8771: {
                return 8909;
            }
            case 8786: {
                return 8787;
            }
            case 8787: {
                return 8786;
            }
            case 8788: {
                return 8789;
            }
            case 8789: {
                return 8788;
            }
            case 8804: {
                return 8805;
            }
            case 8805: {
                return 8804;
            }
            case 8806: {
                return 8807;
            }
            case 8807: {
                return 8806;
            }
            case 8808: {
                return 8809;
            }
            case 8809: {
                return 8808;
            }
            case 8810: {
                return 8811;
            }
            case 8811: {
                return 8810;
            }
            case 8814: {
                return 8815;
            }
            case 8815: {
                return 8814;
            }
            case 8816: {
                return 8817;
            }
            case 8817: {
                return 8816;
            }
            case 8818: {
                return 8819;
            }
            case 8819: {
                return 8818;
            }
            case 8820: {
                return 8821;
            }
            case 8821: {
                return 8820;
            }
            case 8822: {
                return 8823;
            }
            case 8823: {
                return 8822;
            }
            case 8824: {
                return 8825;
            }
            case 8825: {
                return 8824;
            }
            case 8826: {
                return 8827;
            }
            case 8827: {
                return 8826;
            }
            case 8828: {
                return 8829;
            }
            case 8829: {
                return 8828;
            }
            case 8830: {
                return 8831;
            }
            case 8831: {
                return 8830;
            }
            case 8832: {
                return 8833;
            }
            case 8833: {
                return 8832;
            }
            case 8834: {
                return 8835;
            }
            case 8835: {
                return 8834;
            }
            case 8836: {
                return 8837;
            }
            case 8837: {
                return 8836;
            }
            case 8838: {
                return 8839;
            }
            case 8839: {
                return 8838;
            }
            case 8840: {
                return 8841;
            }
            case 8841: {
                return 8840;
            }
            case 8842: {
                return 8843;
            }
            case 8843: {
                return 8842;
            }
            case 8847: {
                return 8848;
            }
            case 8848: {
                return 8847;
            }
            case 8849: {
                return 8850;
            }
            case 8850: {
                return 8849;
            }
            case 8866: {
                return 8867;
            }
            case 8867: {
                return 8866;
            }
            case 8880: {
                return 8881;
            }
            case 8881: {
                return 8880;
            }
            case 8882: {
                return 8883;
            }
            case 8883: {
                return 8882;
            }
            case 8884: {
                return 8885;
            }
            case 8885: {
                return 8884;
            }
            case 8886: {
                return 8887;
            }
            case 8887: {
                return 8886;
            }
            case 8905: {
                return 8906;
            }
            case 8906: {
                return 8905;
            }
            case 8907: {
                return 8908;
            }
            case 8908: {
                return 8907;
            }
            case 8909: {
                return 8771;
            }
            case 8912: {
                return 8913;
            }
            case 8913: {
                return 8912;
            }
            case 8918: {
                return 8919;
            }
            case 8919: {
                return 8918;
            }
            case 8920: {
                return 8921;
            }
            case 8921: {
                return 8920;
            }
            case 8922: {
                return 8923;
            }
            case 8923: {
                return 8922;
            }
            case 8924: {
                return 8925;
            }
            case 8925: {
                return 8924;
            }
            case 8926: {
                return 8927;
            }
            case 8927: {
                return 8926;
            }
            case 8928: {
                return 8929;
            }
            case 8929: {
                return 8928;
            }
            case 8930: {
                return 8931;
            }
            case 8931: {
                return 8930;
            }
            case 8932: {
                return 8933;
            }
            case 8933: {
                return 8932;
            }
            case 8934: {
                return 8935;
            }
            case 8935: {
                return 8934;
            }
            case 8936: {
                return 8937;
            }
            case 8937: {
                return 8936;
            }
            case 8938: {
                return 8939;
            }
            case 8939: {
                return 8938;
            }
            case 8940: {
                return 8941;
            }
            case 8941: {
                return 8940;
            }
            case 8944: {
                return 8945;
            }
            case 8945: {
                return 8944;
            }
            case 8968: {
                return 8969;
            }
            case 8969: {
                return 8968;
            }
            case 8970: {
                return 8971;
            }
            case 8971: {
                return 8970;
            }
            case 9001: {
                return 9002;
            }
            case 9002: {
                return 9001;
            }
            case 12296: {
                return 12297;
            }
            case 12297: {
                return 12296;
            }
            case 12298: {
                return 12299;
            }
            case 12299: {
                return 12298;
            }
            case 12300: {
                return 12301;
            }
            case 12301: {
                return 12300;
            }
            case 12302: {
                return 12303;
            }
            case 12303: {
                return 12302;
            }
            case 12304: {
                return 12305;
            }
            case 12305: {
                return 12304;
            }
            case 12308: {
                return 12309;
            }
            case 12309: {
                return 12308;
            }
            case 12310: {
                return 12311;
            }
            case 12311: {
                return 12310;
            }
            case 12312: {
                return 12313;
            }
            case 12313: {
                return 12312;
            }
            case 12314: {
                return 12315;
            }
            case 12315: {
                return 12314;
            }
            default: {
                return n;
            }
        }
    }
    
    static {
        FLOAT_NAN = new Float(Float.NaN);
    }
}
