// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.outputud.types;

import org.exolab.castor.xml.TypeValidator;
import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.mapping.ClassDescriptor;
import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.xml.XMLFieldDescriptor;
import org.exolab.castor.xml.util.XMLClassDescriptorImpl;

public class CategoriaRegTypeDescriptor extends XMLClassDescriptorImpl
{
    private boolean elementDefinition;
    private String nsPrefix;
    private String nsURI;
    private String xmlName;
    private XMLFieldDescriptor identity;
    
    public CategoriaRegTypeDescriptor() {
        this.xmlName = "CategoriaRegType";
        this.elementDefinition = false;
    }
    
    public AccessMode getAccessMode() {
        return null;
    }
    
    public ClassDescriptor getExtends() {
        return null;
    }
    
    public FieldDescriptor getIdentity() {
        return (FieldDescriptor)this.identity;
    }
    
    public Class getJavaClass() {
        return CategoriaRegType.class;
    }
    
    public String getNameSpacePrefix() {
        return this.nsPrefix;
    }
    
    public String getNameSpaceURI() {
        return this.nsURI;
    }
    
    public TypeValidator getValidator() {
        return (TypeValidator)this;
    }
    
    public String getXMLName() {
        return this.xmlName;
    }
    
    public boolean isElementDefinition() {
        return this.elementDefinition;
    }
}
