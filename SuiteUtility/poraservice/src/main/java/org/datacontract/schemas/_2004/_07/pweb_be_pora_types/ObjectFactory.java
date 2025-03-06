// 
// Decompiled by Procyon v0.5.36
// 

package org.datacontract.schemas._2004._07.pweb_be_pora_types;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _ArrayOfCustomAttribute_QNAME;
    private static final QName _CustomAttribute_QNAME;
    
    static {
        _ArrayOfCustomAttribute_QNAME = new QName("http://schemas.datacontract.org/2004/07/PWEB_BE_PORA.Types.Richieste", "ArrayOfCustomAttribute");
        _CustomAttribute_QNAME = new QName("http://schemas.datacontract.org/2004/07/PWEB_BE_PORA.Types.Richieste", "CustomAttribute");
    }
    
    public ArrayOfCustomAttribute createArrayOfCustomAttribute() {
        return new ArrayOfCustomAttribute();
    }
    
    public CustomAttribute createCustomAttribute() {
        return new CustomAttribute();
    }
    
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PWEB_BE_PORA.Types.Richieste", name = "ArrayOfCustomAttribute")
    public JAXBElement<ArrayOfCustomAttribute> createArrayOfCustomAttribute(final ArrayOfCustomAttribute value) {
        return (JAXBElement<ArrayOfCustomAttribute>)new JAXBElement(ObjectFactory._ArrayOfCustomAttribute_QNAME, (Class)ArrayOfCustomAttribute.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PWEB_BE_PORA.Types.Richieste", name = "CustomAttribute")
    public JAXBElement<CustomAttribute> createCustomAttribute(final CustomAttribute value) {
        return (JAXBElement<CustomAttribute>)new JAXBElement(ObjectFactory._CustomAttribute_QNAME, (Class)CustomAttribute.class, (Class)null, (Object)value);
    }
}
