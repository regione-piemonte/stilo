// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.outputud;

import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.mapping.ClassDescriptor;
import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.xml.TypeValidator;
import org.exolab.castor.xml.validators.IntegerValidator;
import org.exolab.castor.xml.FieldValidator;
import org.exolab.castor.xml.XMLFieldHandler;
import org.exolab.castor.xml.util.XMLFieldDescriptorImpl;
import org.exolab.castor.xml.NodeType;
import org.exolab.castor.xml.XMLFieldDescriptor;
import org.exolab.castor.xml.util.XMLClassDescriptorImpl;

public class Output_UDDescriptor extends XMLClassDescriptorImpl
{
    private boolean elementDefinition;
    private String nsPrefix;
    private String nsURI;
    private String xmlName;
    private XMLFieldDescriptor identity;
    
    public Output_UDDescriptor() {
        this.xmlName = "Output_UD";
        this.elementDefinition = true;
        this.setCompositorAsSequence();
        XMLFieldDescriptorImpl desc = null;
        FieldHandler handler = null;
        FieldValidator fieldValidator = null;
        desc = new XMLFieldDescriptorImpl((Class)Integer.TYPE, "_idUD", "IdUD", NodeType.Element);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final Output_UD target = (Output_UD)object;
                if (!target.hasIdUD()) {
                    return null;
                }
                return new Integer(target.getIdUD());
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final Output_UD target = (Output_UD)object;
                    if (value == null) {
                        return;
                    }
                    target.setIdUD((int)value);
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
        final IntegerValidator typeValidator = new IntegerValidator();
        fieldValidator.setValidator((TypeValidator)typeValidator);
        desc.setValidator(fieldValidator);
        desc = new XMLFieldDescriptorImpl(RegistrazioneDataUD.class, "_registrazioneDataUDList", "RegistrazioneDataUD", NodeType.Element);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final Output_UD target = (Output_UD)object;
                return target.getRegistrazioneDataUD();
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final Output_UD target = (Output_UD)object;
                    target.addRegistrazioneDataUD((RegistrazioneDataUD)value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public void resetValue(final Object object) throws IllegalStateException, IllegalArgumentException {
                try {
                    final Output_UD target = (Output_UD)object;
                    target.removeAllRegistrazioneDataUD();
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public Object newInstance(final Object parent) {
                return new RegistrazioneDataUD();
            }
        };
        desc.setHandler(handler);
        desc.setMultivalued(true);
        this.addFieldDescriptor((XMLFieldDescriptor)desc);
        fieldValidator = new FieldValidator();
        fieldValidator.setMinOccurs(0);
        desc.setValidator(fieldValidator);
        desc = new XMLFieldDescriptorImpl(VersioneElettronicaNonCaricata.class, "_versioneElettronicaNonCaricataList", "VersioneElettronicaNonCaricata", NodeType.Element);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final Output_UD target = (Output_UD)object;
                return target.getVersioneElettronicaNonCaricata();
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final Output_UD target = (Output_UD)object;
                    target.addVersioneElettronicaNonCaricata((VersioneElettronicaNonCaricata)value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public void resetValue(final Object object) throws IllegalStateException, IllegalArgumentException {
                try {
                    final Output_UD target = (Output_UD)object;
                    target.removeAllVersioneElettronicaNonCaricata();
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public Object newInstance(final Object parent) {
                return new VersioneElettronicaNonCaricata();
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
        return Output_UD.class;
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
