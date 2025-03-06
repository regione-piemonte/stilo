// 
// Decompiled by Procyon v0.5.36
// 

package it.eng.auriga.repository2.xml.outputud;

import org.exolab.castor.mapping.FieldDescriptor;
import org.exolab.castor.mapping.ClassDescriptor;
import org.exolab.castor.mapping.AccessMode;
import org.exolab.castor.mapping.FieldHandler;
import org.exolab.castor.xml.validators.IntegerValidator;
import org.exolab.castor.xml.TypeValidator;
import org.exolab.castor.xml.validators.StringValidator;
import org.exolab.castor.xml.FieldValidator;
import org.exolab.castor.xml.handlers.EnumFieldHandler;
import it.eng.auriga.repository2.xml.outputud.types.CategoriaRegType;
import org.exolab.castor.xml.XMLFieldHandler;
import org.exolab.castor.xml.util.XMLFieldDescriptorImpl;
import org.exolab.castor.xml.NodeType;
import org.exolab.castor.xml.XMLFieldDescriptor;
import org.exolab.castor.xml.util.XMLClassDescriptorImpl;

public class EstremiRegNumTypeDescriptor extends XMLClassDescriptorImpl
{
    private boolean elementDefinition;
    private String nsPrefix;
    private String nsURI;
    private String xmlName;
    private XMLFieldDescriptor identity;
    
    public EstremiRegNumTypeDescriptor() {
        this.xmlName = "EstremiRegNumType";
        this.elementDefinition = false;
        XMLFieldDescriptorImpl desc = null;
        FieldHandler handler = null;
        FieldValidator fieldValidator = null;
        desc = new XMLFieldDescriptorImpl(CategoriaRegType.class, "_categoriaReg", "CategoriaReg", NodeType.Element);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final EstremiRegNumType target = (EstremiRegNumType)object;
                return target.getCategoriaReg();
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final EstremiRegNumType target = (EstremiRegNumType)object;
                    target.setCategoriaReg((CategoriaRegType)value);
                }
                catch (Exception ex) {
                    throw new IllegalStateException(ex.toString());
                }
            }
            
            public Object newInstance(final Object parent) {
                return null;
            }
        };
        handler = (FieldHandler)new EnumFieldHandler(CategoriaRegType.class, handler);
        desc.setImmutable(true);
        desc.setHandler(handler);
        desc.setRequired(true);
        desc.setMultivalued(false);
        this.addFieldDescriptor((XMLFieldDescriptor)desc);
        fieldValidator = new FieldValidator();
        fieldValidator.setMinOccurs(1);
        desc.setValidator(fieldValidator);
        desc = new XMLFieldDescriptorImpl(String.class, "_siglaReg", "SiglaReg", NodeType.Element);
        desc.setImmutable(true);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final EstremiRegNumType target = (EstremiRegNumType)object;
                return target.getSiglaReg();
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final EstremiRegNumType target = (EstremiRegNumType)object;
                    target.setSiglaReg((String)value);
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
        final StringValidator typeValidator = new StringValidator();
        typeValidator.setMaxLength(10);
        typeValidator.setWhiteSpace("preserve");
        fieldValidator.setValidator((TypeValidator)typeValidator);
        desc.setValidator(fieldValidator);
        desc = new XMLFieldDescriptorImpl((Class)Integer.TYPE, "_annoReg", "AnnoReg", NodeType.Element);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final EstremiRegNumType target = (EstremiRegNumType)object;
                if (!target.hasAnnoReg()) {
                    return null;
                }
                return new Integer(target.getAnnoReg());
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final EstremiRegNumType target = (EstremiRegNumType)object;
                    if (value == null) {
                        return;
                    }
                    target.setAnnoReg((int)value);
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
        IntegerValidator typeValidator2 = new IntegerValidator();
        typeValidator2.setMinInclusive(1900);
        typeValidator2.setMaxInclusive(2100);
        fieldValidator.setValidator((TypeValidator)typeValidator2);
        desc.setValidator(fieldValidator);
        desc = new XMLFieldDescriptorImpl((Class)Integer.TYPE, "_numReg", "NumReg", NodeType.Element);
        handler = (FieldHandler)new XMLFieldHandler() {
            public Object getValue(final Object object) throws IllegalStateException {
                final EstremiRegNumType target = (EstremiRegNumType)object;
                if (!target.hasNumReg()) {
                    return null;
                }
                return new Integer(target.getNumReg());
            }
            
            public void setValue(final Object object, final Object value) throws IllegalStateException, IllegalArgumentException {
                try {
                    final EstremiRegNumType target = (EstremiRegNumType)object;
                    if (value == null) {
                        return;
                    }
                    target.setNumReg((int)value);
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
        typeValidator2 = new IntegerValidator();
        typeValidator2.setMinInclusive(1);
        typeValidator2.setMaxInclusive(9999999);
        fieldValidator.setValidator((TypeValidator)typeValidator2);
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
        return EstremiRegNumType.class;
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
