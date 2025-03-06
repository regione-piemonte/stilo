// 
// Decompiled by Procyon v0.5.36
// 

package allegati.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _ALLEGATOINFO_QNAME;
    private static final QName _ArrayOfALLEGATOINFO_QNAME;
    private static final QName _ALLEGATORICHIESTA_QNAME;
    private static final QName _ELENCOALLEGATIRICHIESTA_QNAME;
    
    static {
        _ALLEGATOINFO_QNAME = new QName("it.eng.prontoweb.types.pora.allegati", "ALLEGATOINFO");
        _ArrayOfALLEGATOINFO_QNAME = new QName("it.eng.prontoweb.types.pora.allegati", "ArrayOfALLEGATOINFO");
        _ALLEGATORICHIESTA_QNAME = new QName("it.eng.prontoweb.types.pora.allegati", "ALLEGATORICHIESTA");
        _ELENCOALLEGATIRICHIESTA_QNAME = new QName("it.eng.prontoweb.types.pora.allegati", "ELENCOALLEGATIRICHIESTA");
    }
    
    public ALLEGATOINFO createALLEGATOINFO() {
        return new ALLEGATOINFO();
    }
    
    public ArrayOfALLEGATOINFO createArrayOfALLEGATOINFO() {
        return new ArrayOfALLEGATOINFO();
    }
    
    public ALLEGATORICHIESTA createALLEGATORICHIESTA() {
        return new ALLEGATORICHIESTA();
    }
    
    public ELENCOALLEGATIRICHIESTA createELENCOALLEGATIRICHIESTA() {
        return new ELENCOALLEGATIRICHIESTA();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.allegati", name = "ALLEGATOINFO")
    public JAXBElement<ALLEGATOINFO> createALLEGATOINFO(final ALLEGATOINFO value) {
        return (JAXBElement<ALLEGATOINFO>)new JAXBElement(ObjectFactory._ALLEGATOINFO_QNAME, (Class)ALLEGATOINFO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.allegati", name = "ArrayOfALLEGATOINFO")
    public JAXBElement<ArrayOfALLEGATOINFO> createArrayOfALLEGATOINFO(final ArrayOfALLEGATOINFO value) {
        return (JAXBElement<ArrayOfALLEGATOINFO>)new JAXBElement(ObjectFactory._ArrayOfALLEGATOINFO_QNAME, (Class)ArrayOfALLEGATOINFO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.allegati", name = "ALLEGATORICHIESTA")
    public JAXBElement<ALLEGATORICHIESTA> createALLEGATORICHIESTA(final ALLEGATORICHIESTA value) {
        return (JAXBElement<ALLEGATORICHIESTA>)new JAXBElement(ObjectFactory._ALLEGATORICHIESTA_QNAME, (Class)ALLEGATORICHIESTA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.allegati", name = "ELENCOALLEGATIRICHIESTA")
    public JAXBElement<ELENCOALLEGATIRICHIESTA> createELENCOALLEGATIRICHIESTA(final ELENCOALLEGATIRICHIESTA value) {
        return (JAXBElement<ELENCOALLEGATIRICHIESTA>)new JAXBElement(ObjectFactory._ELENCOALLEGATIRICHIESTA_QNAME, (Class)ELENCOALLEGATIRICHIESTA.class, (Class)null, (Object)value);
    }
}
