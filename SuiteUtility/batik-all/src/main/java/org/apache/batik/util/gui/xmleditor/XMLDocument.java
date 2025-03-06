// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.xmleditor;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.PlainDocument;

public class XMLDocument extends PlainDocument
{
    protected XMLScanner lexer;
    protected XMLContext context;
    protected XMLToken cacheToken;
    
    public XMLDocument() {
        this(new XMLContext());
    }
    
    public XMLDocument(final XMLContext context) {
        this.cacheToken = null;
        this.context = context;
        this.lexer = new XMLScanner();
    }
    
    public XMLToken getScannerStart(final int n) throws BadLocationException {
        int n2 = 3;
        int i = 0;
        int startOffset = 0;
        if (this.cacheToken != null) {
            if (this.cacheToken.getStartOffset() > n) {
                this.cacheToken = null;
            }
            else {
                n2 = this.cacheToken.getContext();
                i = (startOffset = this.cacheToken.getStartOffset());
                final Element defaultRootElement = this.getDefaultRootElement();
                if (defaultRootElement.getElementIndex(n) - defaultRootElement.getElementIndex(i) < 50) {
                    return this.cacheToken;
                }
            }
        }
        this.lexer.setString(this.getText(i, n - i));
        this.lexer.reset();
        int n3 = n2;
        int n4 = i;
        while (i < n) {
            n4 = i;
            n3 = n2;
            i = this.lexer.scan(n2) + startOffset;
            n2 = this.lexer.getScanValue();
        }
        return this.cacheToken = new XMLToken(n3, n4, i);
    }
    
    public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
        super.insertString(offs, str, a);
        if (this.cacheToken != null && this.cacheToken.getStartOffset() >= offs) {
            this.cacheToken = null;
        }
    }
    
    public void remove(final int offs, final int len) throws BadLocationException {
        super.remove(offs, len);
        if (this.cacheToken != null && this.cacheToken.getStartOffset() >= offs) {
            this.cacheToken = null;
        }
    }
    
    public int find(String lowerCase, final int n, final boolean b) throws BadLocationException {
        int n2 = -1;
        final Element defaultRootElement = this.getDefaultRootElement();
        final int elementIndex = defaultRootElement.getElementIndex(n);
        if (elementIndex < 0) {
            return n2;
        }
        int fromIndex = n - defaultRootElement.getElement(elementIndex).getStartOffset();
        for (int i = elementIndex; i < defaultRootElement.getElementCount(); ++i) {
            final Element element = defaultRootElement.getElement(i);
            final int startOffset = element.getStartOffset();
            int length;
            if (element.getEndOffset() > this.getLength()) {
                length = this.getLength() - startOffset;
            }
            else {
                length = element.getEndOffset() - startOffset;
            }
            String s = this.getText(startOffset, length);
            if (!b) {
                s = s.toLowerCase();
                lowerCase = lowerCase.toLowerCase();
            }
            final int index = s.indexOf(lowerCase, fromIndex);
            if (index != -1) {
                n2 = startOffset + index;
                break;
            }
            fromIndex = 0;
        }
        return n2;
    }
}
