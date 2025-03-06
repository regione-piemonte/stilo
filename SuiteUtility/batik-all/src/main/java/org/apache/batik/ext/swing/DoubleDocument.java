// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.ext.swing;

import javax.swing.text.BadLocationException;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

public class DoubleDocument extends PlainDocument
{
    public void insertString(final int offs, final String s, final AttributeSet set) throws BadLocationException {
        if (s == null) {
            return;
        }
        final String text = this.getText(0, this.getLength());
        int n = (text.indexOf(46) != -1) ? 1 : 0;
        final char[] charArray = s.toCharArray();
        final char[] value = new char[charArray.length];
        int count = 0;
        if (offs == 0 && charArray != null && charArray.length > 0 && charArray[0] == '-') {
            value[count++] = charArray[0];
        }
        for (int i = 0; i < charArray.length; ++i) {
            if (Character.isDigit(charArray[i])) {
                value[count++] = charArray[i];
            }
            if (n == 0 && charArray[i] == '.') {
                value[count++] = '.';
                n = 1;
            }
        }
        final String str = new String(value, 0, count);
        try {
            final StringBuffer sb = new StringBuffer(text);
            sb.insert(offs, str);
            final String string = sb.toString();
            if (string.equals(".") || string.equals("-") || string.equals("-.")) {
                super.insertString(offs, str, set);
            }
            else {
                Double.valueOf(string);
                super.insertString(offs, str, set);
            }
        }
        catch (NumberFormatException ex) {}
    }
    
    public void setValue(final double d) {
        try {
            this.remove(0, this.getLength());
            this.insertString(0, String.valueOf(d), null);
        }
        catch (BadLocationException ex) {}
    }
    
    public double getValue() {
        try {
            final String text = this.getText(0, this.getLength());
            if (text != null && text.length() > 0) {
                return Double.parseDouble(text);
            }
            return 0.0;
        }
        catch (BadLocationException ex) {
            throw new Error(ex.getMessage());
        }
    }
}
