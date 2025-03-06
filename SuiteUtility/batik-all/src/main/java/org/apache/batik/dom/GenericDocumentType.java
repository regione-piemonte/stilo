// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.DocumentType;

public class GenericDocumentType extends AbstractChildNode implements DocumentType
{
    protected String qualifiedName;
    protected String publicId;
    protected String systemId;
    
    public GenericDocumentType(final String qualifiedName, final String publicId, final String systemId) {
        this.qualifiedName = qualifiedName;
        this.publicId = publicId;
        this.systemId = systemId;
    }
    
    public String getNodeName() {
        return this.qualifiedName;
    }
    
    public short getNodeType() {
        return 10;
    }
    
    public boolean isReadonly() {
        return true;
    }
    
    public void setReadonly(final boolean b) {
    }
    
    public String getName() {
        return null;
    }
    
    public NamedNodeMap getEntities() {
        return null;
    }
    
    public NamedNodeMap getNotations() {
        return null;
    }
    
    public String getPublicId() {
        return this.publicId;
    }
    
    public String getSystemId() {
        return this.systemId;
    }
    
    public String getInternalSubset() {
        return null;
    }
    
    protected Node newNode() {
        return new GenericDocumentType(this.qualifiedName, this.publicId, this.systemId);
    }
}
