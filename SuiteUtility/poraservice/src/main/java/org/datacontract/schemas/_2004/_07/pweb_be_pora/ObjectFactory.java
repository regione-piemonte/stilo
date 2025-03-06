// 
// Decompiled by Procyon v0.5.36
// 

package org.datacontract.schemas._2004._07.pweb_be_pora;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _InfoGUID_QNAME;
    
    static {
        _InfoGUID_QNAME = new QName("http://schemas.datacontract.org/2004/07/PWEB_BE_PORA.Types", "InfoGUID");
    }
    
    public InfoGUID createInfoGUID() {
        return new InfoGUID();
    }
    
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/PWEB_BE_PORA.Types", name = "InfoGUID")
    public JAXBElement<InfoGUID> createInfoGUID(final InfoGUID value) {
        return (JAXBElement<InfoGUID>)new JAXBElement(ObjectFactory._InfoGUID_QNAME, (Class)InfoGUID.class, (Class)null, (Object)value);
    }
}
