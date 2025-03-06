// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.xmleditor;

import javax.swing.text.View;
import javax.swing.text.Element;
import javax.swing.text.Document;
import java.awt.Font;
import javax.swing.JEditorPane;
import javax.swing.text.ViewFactory;
import javax.swing.text.DefaultEditorKit;

public class XMLEditorKit extends DefaultEditorKit
{
    public static final String XML_MIME_TYPE = "text/xml";
    protected XMLContext context;
    protected ViewFactory factory;
    
    public XMLEditorKit() {
        this(null);
    }
    
    public XMLEditorKit(final XMLContext context) {
        this.factory = null;
        this.factory = new XMLViewFactory();
        if (context == null) {
            this.context = new XMLContext();
        }
        else {
            this.context = context;
        }
    }
    
    public XMLContext getStylePreferences() {
        return this.context;
    }
    
    public void install(final JEditorPane c) {
        super.install(c);
        final Font syntaxFont = this.context.getSyntaxFont("default");
        if (syntaxFont != null) {
            c.setFont(syntaxFont);
        }
    }
    
    public String getContentType() {
        return "text/xml";
    }
    
    public Object clone() {
        final XMLEditorKit xmlEditorKit = new XMLEditorKit();
        xmlEditorKit.context = this.context;
        return xmlEditorKit;
    }
    
    public Document createDefaultDocument() {
        return new XMLDocument(this.context);
    }
    
    public ViewFactory getViewFactory() {
        return this.factory;
    }
    
    protected class XMLViewFactory implements ViewFactory
    {
        public View create(final Element element) {
            return new XMLView(XMLEditorKit.this.context, element);
        }
    }
}
