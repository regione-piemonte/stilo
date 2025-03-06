// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.EntityReference;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Text;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentType;

public class GenericDocument extends AbstractDocument
{
    protected static final String ATTR_ID = "id";
    protected boolean readonly;
    
    protected GenericDocument() {
    }
    
    public GenericDocument(final DocumentType documentType, final DOMImplementation domImplementation) {
        super(documentType, domImplementation);
    }
    
    public boolean isReadonly() {
        return this.readonly;
    }
    
    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }
    
    public boolean isId(final Attr attr) {
        return attr.getNamespaceURI() == null && "id".equals(attr.getNodeName());
    }
    
    public Element createElement(final String s) throws DOMException {
        return new GenericElement(s.intern(), this);
    }
    
    public DocumentFragment createDocumentFragment() {
        return new GenericDocumentFragment(this);
    }
    
    public Text createTextNode(final String s) {
        return new GenericText(s, this);
    }
    
    public Comment createComment(final String s) {
        return new GenericComment(s, this);
    }
    
    public CDATASection createCDATASection(final String s) throws DOMException {
        return new GenericCDATASection(s, this);
    }
    
    public ProcessingInstruction createProcessingInstruction(final String s, final String s2) throws DOMException {
        return new GenericProcessingInstruction(s, s2, this);
    }
    
    public Attr createAttribute(final String s) throws DOMException {
        return new GenericAttr(s.intern(), this);
    }
    
    public EntityReference createEntityReference(final String s) throws DOMException {
        return new GenericEntityReference(s, this);
    }
    
    public Element createElementNS(String s, final String s2) throws DOMException {
        if (s != null && s.length() == 0) {
            s = null;
        }
        if (s == null) {
            return new GenericElement(s2.intern(), this);
        }
        return new GenericElementNS(s.intern(), s2.intern(), this);
    }
    
    public Attr createAttributeNS(String s, final String s2) throws DOMException {
        if (s != null && s.length() == 0) {
            s = null;
        }
        if (s == null) {
            return new GenericAttr(s2.intern(), this);
        }
        return new GenericAttrNS(s.intern(), s2.intern(), this);
    }
    
    protected Node newNode() {
        return new GenericDocument();
    }
}
