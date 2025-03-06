// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.outputud;

import org.exolab.castor.xml.TypeValidator;
import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.mapping.ClassDescriptor;
import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.xml.XMLClassDescriptor;
import org.exolab.castor.xml.XMLFieldDescriptor;

public class RegistrazioneDataUDDescriptor extends EstremiRegNumTypeDescriptor
{
    private boolean elementDefinition;
    private String nsPrefix;
    private String nsURI;
    private String xmlName;
    private XMLFieldDescriptor identity;
    
    public RegistrazioneDataUDDescriptor() {
        this.setExtendsWithoutFlatten((XMLClassDescriptor)new EstremiRegNumTypeDescriptor());
        this.xmlName = "RegistrazioneDataUD";
        this.elementDefinition = true;
    }
    
    public AccessMode getAccessMode() {
        return null;
    }
    
    public ClassDescriptor getExtends() {
        return super.getExtends();
    }
    
    public FieldDescriptor getIdentity() {
        if (this.identity == null) {
            return super.getIdentity();
        }
        return (FieldDescriptor)this.identity;
    }
    
    public Class getJavaClass() {
        return RegistrazioneDataUD.class;
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
