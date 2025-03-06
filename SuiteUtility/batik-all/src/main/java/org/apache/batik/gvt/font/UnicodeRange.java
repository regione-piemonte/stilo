// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.font;

public class UnicodeRange
{
    private int firstUnicodeValue;
    private int lastUnicodeValue;
    
    public UnicodeRange(String substring) {
        if (substring.startsWith("U+") && substring.length() > 2) {
            substring = substring.substring(2);
            final int index = substring.indexOf(45);
            String s;
            String s2;
            if (index != -1) {
                s = substring.substring(0, index);
                s2 = substring.substring(index + 1);
            }
            else {
                s = substring;
                s2 = substring;
                if (substring.indexOf(63) != -1) {
                    s = s.replace('?', '0');
                    s2 = s2.replace('?', 'F');
                }
            }
            try {
                this.firstUnicodeValue = Integer.parseInt(s, 16);
                this.lastUnicodeValue = Integer.parseInt(s2, 16);
            }
            catch (NumberFormatException ex) {
                this.firstUnicodeValue = -1;
                this.lastUnicodeValue = -1;
            }
        }
        else {
            this.firstUnicodeValue = -1;
            this.lastUnicodeValue = -1;
        }
    }
    
    public boolean contains(final String s) {
        return s.length() == 1 && this.contains(s.charAt(0));
    }
    
    public boolean contains(final int n) {
        return n >= this.firstUnicodeValue && n <= this.lastUnicodeValue;
    }
}
