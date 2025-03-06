// 
// Decompiled by Procyon v0.5.36
// 

package com.microsoft.schemas._2003._10.serialization.arrays;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _ArrayOfstring_QNAME;
    
    static {
        _ArrayOfstring_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfstring");
    }
    
    public ArrayOfstring createArrayOfstring() {
        return new ArrayOfstring();
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/Arrays", name = "ArrayOfstring")
    public JAXBElement<ArrayOfstring> createArrayOfstring(final ArrayOfstring value) {
        return (JAXBElement<ArrayOfstring>)new JAXBElement(ObjectFactory._ArrayOfstring_QNAME, (Class)ArrayOfstring.class, (Class)null, (Object)value);
    }
}
