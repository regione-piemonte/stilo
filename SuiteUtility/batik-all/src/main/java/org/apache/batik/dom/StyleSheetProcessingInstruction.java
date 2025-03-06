// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.DOMException;
import org.apache.batik.dom.util.DOMUtilities;
import org.w3c.dom.Node;
import org.apache.batik.dom.util.HashTable;
import org.w3c.dom.stylesheets.StyleSheet;
import org.w3c.dom.stylesheets.LinkStyle;

public class StyleSheetProcessingInstruction extends AbstractProcessingInstruction implements LinkStyle
{
    protected boolean readonly;
    protected transient StyleSheet sheet;
    protected StyleSheetFactory factory;
    protected transient HashTable pseudoAttributes;
    
    protected StyleSheetProcessingInstruction() {
    }
    
    public StyleSheetProcessingInstruction(final String data, final AbstractDocument ownerDocument, final StyleSheetFactory factory) {
        this.ownerDocument = ownerDocument;
        this.setData(data);
        this.factory = factory;
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    public void setNodeName(final String s) {
    }
    
    public String getTarget() {
        return "xml-stylesheet";
    }
    
    public StyleSheet getSheet() {
        if (this.sheet == null) {
            this.sheet = this.factory.createStyleSheet(this, this.getPseudoAttributes());
        }
        return this.sheet;
    }
    
    public HashTable getPseudoAttributes() {
        if (this.pseudoAttributes == null) {
            (this.pseudoAttributes = new HashTable()).put("alternate", "no");
            this.pseudoAttributes.put("media", "all");
            DOMUtilities.parseStyleSheetPIData(this.data, this.pseudoAttributes);
        }
        return this.pseudoAttributes;
    }
    
    public void setData(final String data) throws DOMException {
        super.setData(data);
        this.sheet = null;
        this.pseudoAttributes = null;
    }
    
    protected Node newNode() {
        return new StyleSheetProcessingInstruction();
    }
}
