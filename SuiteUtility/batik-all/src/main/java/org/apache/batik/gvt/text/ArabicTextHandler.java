// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import java.util.Map;
import java.text.AttributedString;
import java.text.AttributedCharacterIterator;

public class ArabicTextHandler
{
    private static final int arabicStart = 1536;
    private static final int arabicEnd = 1791;
    private static final AttributedCharacterIterator.Attribute ARABIC_FORM;
    private static final Integer ARABIC_NONE;
    private static final Integer ARABIC_ISOLATED;
    private static final Integer ARABIC_TERMINAL;
    private static final Integer ARABIC_INITIAL;
    private static final Integer ARABIC_MEDIAL;
    static int singleCharFirst;
    static int singleCharLast;
    static int[][] singleCharRemappings;
    static int doubleCharFirst;
    static int doubleCharLast;
    static int[][][] doubleCharRemappings;
    
    private ArabicTextHandler() {
    }
    
    public static AttributedString assignArabicForms(AttributedString attributedString) {
        if (!containsArabic(attributedString)) {
            return attributedString;
        }
        final AttributedCharacterIterator iterator = attributedString.getIterator();
        final int capacity = iterator.getEndIndex() - iterator.getBeginIndex();
        int[] array = null;
        if (capacity >= 3) {
            char first = iterator.first();
            char next = iterator.next();
            int n = 1;
            for (char c = iterator.next(); c != '\uffff'; c = iterator.next(), ++n) {
                if (arabicCharTransparent(next) && hasSubstitute(first, c)) {
                    if (array == null) {
                        array = new int[capacity];
                        for (int i = 0; i < capacity; ++i) {
                            array[i] = i + iterator.getBeginIndex();
                        }
                    }
                    final int n2 = array[n];
                    array[n] = array[n - 1];
                    array[n - 1] = n2;
                }
                first = next;
                next = c;
            }
        }
        if (array != null) {
            final StringBuffer sb = new StringBuffer(capacity);
            for (int j = 0; j < capacity; ++j) {
                sb.append(iterator.setIndex(array[j]));
            }
            final AttributedString attributedString2 = new AttributedString(sb.toString());
            for (int k = 0; k < capacity; ++k) {
                iterator.setIndex(array[k]);
                attributedString2.addAttributes(iterator.getAttributes(), k, k + 1);
            }
            if (array[0] != iterator.getBeginIndex()) {
                iterator.setIndex(array[0]);
                final Float value = (Float)iterator.getAttribute(GVTAttributedCharacterIterator.TextAttribute.X);
                final Float value2 = (Float)iterator.getAttribute(GVTAttributedCharacterIterator.TextAttribute.Y);
                if (value != null && !value.isNaN()) {
                    attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.X, new Float(Float.NaN), array[0], array[0] + 1);
                    attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.X, value, 0, 1);
                }
                if (value2 != null && !value2.isNaN()) {
                    attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.Y, new Float(Float.NaN), array[0], array[0] + 1);
                    attributedString2.addAttribute(GVTAttributedCharacterIterator.TextAttribute.Y, value2, 0, 1);
                }
            }
            attributedString = attributedString2;
        }
        final AttributedCharacterIterator iterator2 = attributedString.getIterator();
        int n3 = -1;
        int beginIndex = iterator2.getBeginIndex();
        for (char c2 = iterator2.first(); c2 != '\uffff'; c2 = iterator2.next(), ++beginIndex) {
            if (c2 >= '\u0600' && c2 <= '\u06ff') {
                if (n3 == -1) {
                    n3 = beginIndex;
                }
            }
            else if (n3 != -1) {
                attributedString.addAttribute(ArabicTextHandler.ARABIC_FORM, ArabicTextHandler.ARABIC_NONE, n3, beginIndex);
                n3 = -1;
            }
        }
        if (n3 != -1) {
            attributedString.addAttribute(ArabicTextHandler.ARABIC_FORM, ArabicTextHandler.ARABIC_NONE, n3, beginIndex);
        }
        final AttributedCharacterIterator iterator3 = attributedString.getIterator();
        int index = iterator3.getBeginIndex();
        final Integer arabic_NONE = ArabicTextHandler.ARABIC_NONE;
        while (iterator3.setIndex(index) != '\uffff') {
            final int runStart = iterator3.getRunStart(ArabicTextHandler.ARABIC_FORM);
            index = iterator3.getRunLimit(ArabicTextHandler.ARABIC_FORM);
            char c3 = iterator3.setIndex(runStart);
            Integer value3 = (Integer)iterator3.getAttribute(ArabicTextHandler.ARABIC_FORM);
            if (value3 == null) {
                continue;
            }
            int l = runStart;
            int beginIndex2 = runStart - 1;
            while (l < index) {
                final char c4 = c3;
                for (c3 = iterator3.setIndex(l); arabicCharTransparent(c3) && l < index; ++l, c3 = iterator3.setIndex(l)) {}
                if (l >= index) {
                    break;
                }
                final Integer n4 = value3;
                value3 = ArabicTextHandler.ARABIC_NONE;
                if (beginIndex2 >= runStart) {
                    if (arabicCharShapesRight(c4) && arabicCharShapesLeft(c3)) {
                        attributedString.addAttribute(ArabicTextHandler.ARABIC_FORM, new Integer(n4 + 1), beginIndex2, beginIndex2 + 1);
                        value3 = ArabicTextHandler.ARABIC_INITIAL;
                    }
                    else if (arabicCharShaped(c3)) {
                        value3 = ArabicTextHandler.ARABIC_ISOLATED;
                    }
                }
                else if (arabicCharShaped(c3)) {
                    value3 = ArabicTextHandler.ARABIC_ISOLATED;
                }
                if (value3 != ArabicTextHandler.ARABIC_NONE) {
                    attributedString.addAttribute(ArabicTextHandler.ARABIC_FORM, value3, l, l + 1);
                }
                beginIndex2 = l;
                ++l;
            }
        }
        return attributedString;
    }
    
    public static boolean arabicChar(final char c) {
        return c >= '\u0600' && c <= '\u06ff';
    }
    
    public static boolean containsArabic(final AttributedString attributedString) {
        return containsArabic(attributedString.getIterator());
    }
    
    public static boolean containsArabic(final AttributedCharacterIterator attributedCharacterIterator) {
        for (char c = attributedCharacterIterator.first(); c != '\uffff'; c = attributedCharacterIterator.next()) {
            if (arabicChar(c)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean arabicCharTransparent(final char c) {
        return c >= '\u064b' && c <= '\u06ed' && (c <= '\u0655' || c == '\u0670' || (c >= '\u06d6' && c <= '\u06e4') || (c >= '\u06e7' && c <= '\u06e8') || c >= '\u06ea');
    }
    
    private static boolean arabicCharShapesRight(final char c) {
        return (c >= '\u0622' && c <= '\u0625') || c == '\u0627' || c == '\u0629' || (c >= '\u062f' && c <= '\u0632') || c == '\u0648' || (c >= '\u0671' && c <= '\u0673') || (c >= '\u0675' && c <= '\u0677') || (c >= '\u0688' && c <= '\u0699') || c == '\u06c0' || (c >= '\u06c2' && c <= '\u06cb') || c == '\u06cd' || c == '\u06cf' || (c >= '\u06d2' && c <= '\u06d3') || arabicCharShapesDuel(c);
    }
    
    private static boolean arabicCharShapesDuel(final char c) {
        return c == '\u0626' || c == '\u0628' || (c >= '\u062a' && c <= '\u062e') || (c >= '\u0633' && c <= '\u063a') || (c >= '\u0641' && c <= '\u0647') || (c >= '\u0649' && c <= '\u064a') || (c >= '\u0678' && c <= '\u0687') || (c >= '\u069a' && c <= '\u06bf') || c == '\u06c1' || c == '\u06cc' || c == '\u06ce' || (c >= '\u06d0' && c <= '\u06d1') || (c >= '\u06fa' && c <= '\u06fc');
    }
    
    private static boolean arabicCharShapesLeft(final char c) {
        return arabicCharShapesDuel(c);
    }
    
    private static boolean arabicCharShaped(final char c) {
        return arabicCharShapesRight(c);
    }
    
    public static boolean hasSubstitute(final char c, final char c2) {
        if (c < ArabicTextHandler.doubleCharFirst || c > ArabicTextHandler.doubleCharLast) {
            return false;
        }
        final int[][] array = ArabicTextHandler.doubleCharRemappings[c - ArabicTextHandler.doubleCharFirst];
        if (array == null) {
            return false;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i][0] == c2) {
                return true;
            }
        }
        return false;
    }
    
    public static int getSubstituteChar(final char c, final char c2, final int n) {
        if (n == 0) {
            return -1;
        }
        if (c < ArabicTextHandler.doubleCharFirst || c > ArabicTextHandler.doubleCharLast) {
            return -1;
        }
        final int[][] array = ArabicTextHandler.doubleCharRemappings[c - ArabicTextHandler.doubleCharFirst];
        if (array == null) {
            return -1;
        }
        for (int i = 0; i < array.length; ++i) {
            if (array[i][0] == c2) {
                return array[i][n];
            }
        }
        return -1;
    }
    
    public static int getSubstituteChar(final char c, final int n) {
        if (n == 0) {
            return -1;
        }
        if (c < ArabicTextHandler.singleCharFirst || c > ArabicTextHandler.singleCharLast) {
            return -1;
        }
        final int[] array = ArabicTextHandler.singleCharRemappings[c - ArabicTextHandler.singleCharFirst];
        if (array == null) {
            return -1;
        }
        return array[n - 1];
    }
    
    public static String createSubstituteString(final AttributedCharacterIterator attributedCharacterIterator) {
        final int beginIndex = attributedCharacterIterator.getBeginIndex();
        final int endIndex = attributedCharacterIterator.getEndIndex();
        final StringBuffer sb = new StringBuffer(endIndex - beginIndex);
        for (int i = beginIndex; i < endIndex; ++i) {
            char setIndex = attributedCharacterIterator.setIndex(i);
            if (!arabicChar(setIndex)) {
                sb.append(setIndex);
            }
            else {
                final Integer n = (Integer)attributedCharacterIterator.getAttribute(ArabicTextHandler.ARABIC_FORM);
                if (charStartsLigature(setIndex) && i + 1 < endIndex) {
                    final char setIndex2 = attributedCharacterIterator.setIndex(i + 1);
                    final Integer n2 = (Integer)attributedCharacterIterator.getAttribute(ArabicTextHandler.ARABIC_FORM);
                    if (n != null && n2 != null) {
                        if (n.equals(ArabicTextHandler.ARABIC_TERMINAL) && n2.equals(ArabicTextHandler.ARABIC_INITIAL)) {
                            final int substituteChar = getSubstituteChar(setIndex, setIndex2, ArabicTextHandler.ARABIC_ISOLATED);
                            if (substituteChar > -1) {
                                sb.append((char)substituteChar);
                                ++i;
                                continue;
                            }
                        }
                        else if (n.equals(ArabicTextHandler.ARABIC_TERMINAL)) {
                            final int substituteChar2 = getSubstituteChar(setIndex, setIndex2, ArabicTextHandler.ARABIC_TERMINAL);
                            if (substituteChar2 > -1) {
                                sb.append((char)substituteChar2);
                                ++i;
                                continue;
                            }
                        }
                        else if (n.equals(ArabicTextHandler.ARABIC_MEDIAL) && n2.equals(ArabicTextHandler.ARABIC_MEDIAL)) {
                            final int substituteChar3 = getSubstituteChar(setIndex, setIndex2, ArabicTextHandler.ARABIC_MEDIAL);
                            if (substituteChar3 > -1) {
                                sb.append((char)substituteChar3);
                                ++i;
                                continue;
                            }
                        }
                    }
                }
                if (n != null && n > 0) {
                    final int substituteChar4 = getSubstituteChar(setIndex, n);
                    if (substituteChar4 > -1) {
                        setIndex = (char)substituteChar4;
                    }
                }
                sb.append(setIndex);
            }
        }
        return sb.toString();
    }
    
    public static boolean charStartsLigature(final char c) {
        return c == '\u064b' || c == '\u064c' || c == '\u064d' || c == '\u064e' || c == '\u064f' || c == '\u0650' || c == '\u0651' || c == '\u0652' || c == '\u0622' || c == '\u0623' || c == '\u0625' || c == '\u0627';
    }
    
    public static int getNumChars(final char c) {
        if (isLigature(c)) {
            return 2;
        }
        return 1;
    }
    
    public static boolean isLigature(final char c) {
        return c >= '\ufe70' && c <= '\ufefc' && (c <= '\ufe72' || c == '\ufe74' || (c >= '\ufe76' && c <= '\ufe7f') || c >= '\ufef5');
    }
    
    static {
        ARABIC_FORM = GVTAttributedCharacterIterator.TextAttribute.ARABIC_FORM;
        ARABIC_NONE = GVTAttributedCharacterIterator.TextAttribute.ARABIC_NONE;
        ARABIC_ISOLATED = GVTAttributedCharacterIterator.TextAttribute.ARABIC_ISOLATED;
        ARABIC_TERMINAL = GVTAttributedCharacterIterator.TextAttribute.ARABIC_TERMINAL;
        ARABIC_INITIAL = GVTAttributedCharacterIterator.TextAttribute.ARABIC_INITIAL;
        ARABIC_MEDIAL = GVTAttributedCharacterIterator.TextAttribute.ARABIC_MEDIAL;
        ArabicTextHandler.singleCharFirst = 1569;
        ArabicTextHandler.singleCharLast = 1610;
        ArabicTextHandler.singleCharRemappings = new int[][] { { 65152, -1, -1, -1 }, { 65153, 65154, -1, -1 }, { 65155, 65156, -1, -1 }, { 65157, 65158, -1, -1 }, { 65159, 65160, -1, -1 }, { 65161, 65162, 65163, 65164 }, { 65165, 65166, -1, -1 }, { 65167, 65168, 65169, 65170 }, { 65171, 65172, -1, -1 }, { 65173, 65174, 65175, 65176 }, { 65177, 65178, 65179, 65180 }, { 65181, 65182, 65183, 65184 }, { 65185, 65186, 65187, 65188 }, { 65189, 65190, 65191, 65192 }, { 65193, 65194, -1, -1 }, { 65195, 65196, -1, -1 }, { 65197, 65198, -1, -1 }, { 65199, 65200, -1, -1 }, { 65201, 65202, 65203, 65204 }, { 65205, 65206, 65207, 65208 }, { 65209, 65210, 65211, 65212 }, { 65213, 65214, 65215, 65216 }, { 65217, 65218, 65219, 65220 }, { 65221, 65222, 65223, 65224 }, { 65225, 65226, 65227, 65228 }, { 65229, 65230, 65231, 65232 }, null, null, null, null, null, null, { 65233, 65234, 65235, 65236 }, { 65237, 65238, 65239, 65240 }, { 65241, 65242, 65243, 65244 }, { 65245, 65246, 65247, 65248 }, { 65249, 65250, 65251, 65252 }, { 65253, 65254, 65255, 65256 }, { 65257, 65258, 65259, 65260 }, { 65261, 65262, -1, -1 }, { 65263, 65264, -1, -1 }, { 65265, 65266, 65267, 65268 } };
        ArabicTextHandler.doubleCharFirst = 1570;
        ArabicTextHandler.doubleCharLast = 1618;
        ArabicTextHandler.doubleCharRemappings = new int[][][] { { { 1604, 65269, 65270, -1, -1 } }, { { 1604, 65271, 65272, -1, -1 } }, null, { { 1604, 65273, 65274, -1, -1 } }, null, { { 1604, 65275, 65276, -1, -1 } }, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, { { 32, 65136, -1, -1, -1 }, { 1600, -1, -1, -1, 65137 } }, { { 32, 65138, -1, -1, -1 } }, { { 32, 65140, -1, -1, -1 } }, { { 32, 65142, -1, -1, -1 }, { 1600, -1, -1, -1, 65143 } }, { { 32, 65144, -1, -1, -1 }, { 1600, -1, -1, -1, 65145 } }, { { 32, 65146, -1, -1, -1 }, { 1600, -1, -1, -1, 65147 } }, { { 32, 65148, -1, -1, -1 }, { 1600, -1, -1, -1, 65149 } }, { { 32, 65150, -1, -1, -1 }, { 1600, -1, -1, -1, 65151 } } };
    }
}
