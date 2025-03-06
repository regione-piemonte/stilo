// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.xmleditor;

import javax.swing.text.BadLocationException;
import javax.swing.text.Segment;
import javax.swing.text.TabExpander;
import javax.swing.text.Utilities;
import java.awt.Graphics;
import javax.swing.text.Element;
import javax.swing.text.PlainView;

public class XMLView extends PlainView
{
    protected XMLContext context;
    protected XMLScanner lexer;
    protected int tabSize;
    
    public XMLView(final XMLContext context, final Element elem) {
        super(elem);
        this.context = null;
        this.lexer = new XMLScanner();
        this.tabSize = 4;
        this.context = context;
    }
    
    public int getTabSize() {
        return this.tabSize;
    }
    
    protected int drawUnselectedText(final Graphics graphics, int n, final int n2, final int n3, final int n4) throws BadLocationException {
        final XMLDocument xmlDocument = (XMLDocument)this.getDocument();
        final XMLToken scannerStart = xmlDocument.getScannerStart(n3);
        this.lexer.setString(xmlDocument.getText(scannerStart.getStartOffset(), n4 - scannerStart.getStartOffset() + 1));
        this.lexer.reset();
        int i;
        int context;
        int n5;
        for (i = scannerStart.getStartOffset(), n5 = (context = scannerStart.getContext()); i < n3; i = this.lexer.scan(n5) + scannerStart.getStartOffset(), context = n5, n5 = this.lexer.getScanValue()) {}
        int n6 = n3;
        while (i < n4) {
            if (context != n5) {
                graphics.setColor(this.context.getSyntaxForeground(context));
                graphics.setFont(this.context.getSyntaxFont(context));
                final Segment lineBuffer = this.getLineBuffer();
                xmlDocument.getText(n6, i - n6, lineBuffer);
                n = Utilities.drawTabbedText(lineBuffer, n, n2, graphics, this, n6);
                n6 = i;
            }
            i = this.lexer.scan(n5) + scannerStart.getStartOffset();
            context = n5;
            n5 = this.lexer.getScanValue();
        }
        graphics.setColor(this.context.getSyntaxForeground(context));
        graphics.setFont(this.context.getSyntaxFont(context));
        final Segment lineBuffer2 = this.getLineBuffer();
        xmlDocument.getText(n6, n4 - n6, lineBuffer2);
        n = Utilities.drawTabbedText(lineBuffer2, n, n2, graphics, this, n6);
        return n;
    }
}
