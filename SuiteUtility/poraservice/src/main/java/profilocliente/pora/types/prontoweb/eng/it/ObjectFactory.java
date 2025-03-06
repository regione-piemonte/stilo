// 
// Decompiled by Procyon v0.5.36
// 

package profilocliente.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _ArrayOfATTRDYN_QNAME;
    private static final QName _ATTRDYN_QNAME;
    private static final QName _ArrayOfATTRDYNVAL_QNAME;
    private static final QName _ATTRDYNVAL_QNAME;
    private static final QName _ArrayOfSoggetto_QNAME;
    private static final QName _Soggetto_QNAME;
    
    static {
        _ArrayOfATTRDYN_QNAME = new QName("it.eng.prontoweb.types.pora.profilocliente", "ArrayOfATTRDYN");
        _ATTRDYN_QNAME = new QName("it.eng.prontoweb.types.pora.profilocliente", "ATTRDYN");
        _ArrayOfATTRDYNVAL_QNAME = new QName("it.eng.prontoweb.types.pora.profilocliente", "ArrayOfATTRDYNVAL");
        _ATTRDYNVAL_QNAME = new QName("it.eng.prontoweb.types.pora.profilocliente", "ATTRDYNVAL");
        _ArrayOfSoggetto_QNAME = new QName("it.eng.prontoweb.types.pora.profilocliente", "ArrayOfSoggetto");
        _Soggetto_QNAME = new QName("it.eng.prontoweb.types.pora.profilocliente", "Soggetto");
    }
    
    public ArrayOfATTRDYN createArrayOfATTRDYN() {
        return new ArrayOfATTRDYN();
    }
    
    public ATTRDYN createATTRDYN() {
        return new ATTRDYN();
    }
    
    public ArrayOfATTRDYNVAL createArrayOfATTRDYNVAL() {
        return new ArrayOfATTRDYNVAL();
    }
    
    public ATTRDYNVAL createATTRDYNVAL() {
        return new ATTRDYNVAL();
    }
    
    public ArrayOfSoggetto createArrayOfSoggetto() {
        return new ArrayOfSoggetto();
    }
    
    public Soggetto createSoggetto() {
        return new Soggetto();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.profilocliente", name = "ArrayOfATTRDYN")
    public JAXBElement<ArrayOfATTRDYN> createArrayOfATTRDYN(final ArrayOfATTRDYN value) {
        return (JAXBElement<ArrayOfATTRDYN>)new JAXBElement(ObjectFactory._ArrayOfATTRDYN_QNAME, (Class)ArrayOfATTRDYN.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.profilocliente", name = "ATTRDYN")
    public JAXBElement<ATTRDYN> createATTRDYN(final ATTRDYN value) {
        return (JAXBElement<ATTRDYN>)new JAXBElement(ObjectFactory._ATTRDYN_QNAME, (Class)ATTRDYN.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.profilocliente", name = "ArrayOfATTRDYNVAL")
    public JAXBElement<ArrayOfATTRDYNVAL> createArrayOfATTRDYNVAL(final ArrayOfATTRDYNVAL value) {
        return (JAXBElement<ArrayOfATTRDYNVAL>)new JAXBElement(ObjectFactory._ArrayOfATTRDYNVAL_QNAME, (Class)ArrayOfATTRDYNVAL.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.profilocliente", name = "ATTRDYNVAL")
    public JAXBElement<ATTRDYNVAL> createATTRDYNVAL(final ATTRDYNVAL value) {
        return (JAXBElement<ATTRDYNVAL>)new JAXBElement(ObjectFactory._ATTRDYNVAL_QNAME, (Class)ATTRDYNVAL.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.profilocliente", name = "ArrayOfSoggetto")
    public JAXBElement<ArrayOfSoggetto> createArrayOfSoggetto(final ArrayOfSoggetto value) {
        return (JAXBElement<ArrayOfSoggetto>)new JAXBElement(ObjectFactory._ArrayOfSoggetto_QNAME, (Class)ArrayOfSoggetto.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.profilocliente", name = "Soggetto")
    public JAXBElement<Soggetto> createSoggetto(final Soggetto value) {
        return (JAXBElement<Soggetto>)new JAXBElement(ObjectFactory._Soggetto_QNAME, (Class)Soggetto.class, (Class)null, (Object)value);
    }
}
