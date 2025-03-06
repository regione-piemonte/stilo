// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.sezionecache;

import org.exolab.castor.xml.TypeValidator;
import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.mapping.ClassDescriptor;
import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.xml.FieldValidator;
import org.exolab.castor.xml.XMLFieldHandler;
import org.exolab.castor.xml.util.XMLFieldDescriptorImpl;
import org.exolab.castor.xml.NodeType;
import org.exolab.castor.xml.XMLFieldDescriptor;
import org.exolab.castor.xml.util.XMLClassDescriptorImpl;

public class SezioneCacheDescriptor extends XMLClassDescriptorImpl
{
    private boolean elementDefinition;
    private String nsPrefix;
    private String nsURI;
    private String xmlName;
    private XMLFieldDescriptor identity;
    
    public SezioneCacheDescriptor() {
        this.xmlName = "SezioneCache";
        this.elementDefinition = true;
        this.setCompositorAsSequence();
        XMLFieldDescriptorImpl desc = null;
        FieldHandler handler = null;
        FieldValidator fieldValidator = null;
        desc = new XMLFieldDescriptorImpl(Variabile.class, "_variabileList", "Variabile", NodeType.Element);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final SezioneCache target = (SezioneCache)object;
                return target.getVariabile();
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final SezioneCache target = (SezioneCache)object;
                    target.addVariabile((Variabile)value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public void resetValue(final Object object) throws IllegalStateException, IllegalArgumentException {
                try {
                    final SezioneCache target = (SezioneCache)object;
                    target.removeAllVariabile();
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public Object newInstance(final Object parent) {
                return new Variabile();
            }
        };
        desc.setHandler(handler);
        desc.setMultivalued(true);
        this.addFieldDescriptor((XMLFieldDescriptor)desc);
        fieldValidator = new FieldValidator();
        fieldValidator.setMinOccurs(0);
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
        return SezioneCache.class;
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
