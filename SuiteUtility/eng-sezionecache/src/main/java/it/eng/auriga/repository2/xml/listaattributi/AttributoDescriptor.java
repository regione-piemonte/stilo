// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.listaattributi;

import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.mapping.ClassDescriptor;
import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.xml.TypeValidator;
import org.exolab.castor.xml.validators.StringValidator;
import org.exolab.castor.xml.FieldValidator;
import org.exolab.castor.xml.XMLFieldHandler;
import org.exolab.castor.xml.util.XMLFieldDescriptorImpl;
import org.exolab.castor.xml.NodeType;
import org.exolab.castor.xml.XMLFieldDescriptor;
import org.exolab.castor.xml.util.XMLClassDescriptorImpl;

public class AttributoDescriptor extends XMLClassDescriptorImpl
{
    private boolean elementDefinition;
    private String nsPrefix;
    private String nsURI;
    private String xmlName;
    private XMLFieldDescriptor identity;
    
    public AttributoDescriptor() {
        this.xmlName = "Attributo";
        this.elementDefinition = true;
        this.setCompositorAsSequence();
        XMLFieldDescriptorImpl desc = null;
        FieldHandler handler = null;
        FieldValidator fieldValidator = null;
        desc = new XMLFieldDescriptorImpl(String.class, "_nome", "Nome", NodeType.Attribute);
        desc.setImmutable(true);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final Attributo target = (Attributo)object;
                return target.getNome();
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final Attributo target = (Attributo)object;
                    target.setNome((String)value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public Object newInstance(final Object parent) {
                return null;
            }
        };
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        this.addFieldDescriptor((XMLFieldDescriptor)desc);
        fieldValidator = new FieldValidator();
        fieldValidator.setMinOccurs(1);
        StringValidator typeValidator = new StringValidator();
        typeValidator.setWhiteSpace("preserve");
        fieldValidator.setValidator((TypeValidator)typeValidator);
        desc.setValidator(fieldValidator);
        desc = new XMLFieldDescriptorImpl(String.class, "_valoreList", "Valore", NodeType.Element);
        desc.setImmutable(true);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final Attributo target = (Attributo)object;
                return target.getValore();
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final Attributo target = (Attributo)object;
                    target.addValore((String)value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public void resetValue(final Object object) throws IllegalStateException, IllegalArgumentException {
                try {
                    final Attributo target = (Attributo)object;
                    target.removeAllValore();
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public Object newInstance(final Object parent) {
                return null;
            }
        };
        desc.setHandler(handler);
        desc.setMultivalued(true);
        this.addFieldDescriptor((XMLFieldDescriptor)desc);
        fieldValidator = new FieldValidator();
        fieldValidator.setMinOccurs(0);
        typeValidator = new StringValidator();
        typeValidator.setWhiteSpace("preserve");
        fieldValidator.setValidator((TypeValidator)typeValidator);
        desc.setValidator(fieldValidator);
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
        return Attributo.class;
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
