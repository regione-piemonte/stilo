// 
// Decompiled by Procyon v0.5.36
// 

package dettaglioservizio.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _DETTAGLIOSERVIZIO_QNAME;
    private static final QName _ArrayOfELEMENTIDIFATTURAZIONE_QNAME;
    private static final QName _ELEMENTIDIFATTURAZIONE_QNAME;
    
    static {
        _DETTAGLIOSERVIZIO_QNAME = new QName("it.eng.prontoweb.types.pora.dettaglioservizio", "DETTAGLIOSERVIZIO");
        _ArrayOfELEMENTIDIFATTURAZIONE_QNAME = new QName("it.eng.prontoweb.types.pora.dettaglioservizio", "ArrayOfELEMENTIDIFATTURAZIONE");
        _ELEMENTIDIFATTURAZIONE_QNAME = new QName("it.eng.prontoweb.types.pora.dettaglioservizio", "ELEMENTIDIFATTURAZIONE");
    }
    
    public DETTAGLIOSERVIZIO createDETTAGLIOSERVIZIO() {
        return new DETTAGLIOSERVIZIO();
    }
    
    public ArrayOfELEMENTIDIFATTURAZIONE createArrayOfELEMENTIDIFATTURAZIONE() {
        return new ArrayOfELEMENTIDIFATTURAZIONE();
    }
    
    public ELEMENTIDIFATTURAZIONE createELEMENTIDIFATTURAZIONE() {
        return new ELEMENTIDIFATTURAZIONE();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.dettaglioservizio", name = "DETTAGLIOSERVIZIO")
    public JAXBElement<DETTAGLIOSERVIZIO> createDETTAGLIOSERVIZIO(final DETTAGLIOSERVIZIO value) {
        return (JAXBElement<DETTAGLIOSERVIZIO>)new JAXBElement(ObjectFactory._DETTAGLIOSERVIZIO_QNAME, (Class)DETTAGLIOSERVIZIO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.dettaglioservizio", name = "ArrayOfELEMENTIDIFATTURAZIONE")
    public JAXBElement<ArrayOfELEMENTIDIFATTURAZIONE> createArrayOfELEMENTIDIFATTURAZIONE(final ArrayOfELEMENTIDIFATTURAZIONE value) {
        return (JAXBElement<ArrayOfELEMENTIDIFATTURAZIONE>)new JAXBElement(ObjectFactory._ArrayOfELEMENTIDIFATTURAZIONE_QNAME, (Class)ArrayOfELEMENTIDIFATTURAZIONE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.dettaglioservizio", name = "ELEMENTIDIFATTURAZIONE")
    public JAXBElement<ELEMENTIDIFATTURAZIONE> createELEMENTIDIFATTURAZIONE(final ELEMENTIDIFATTURAZIONE value) {
        return (JAXBElement<ELEMENTIDIFATTURAZIONE>)new JAXBElement(ObjectFactory._ELEMENTIDIFATTURAZIONE_QNAME, (Class)ELEMENTIDIFATTURAZIONE.class, (Class)null, (Object)value);
    }
}
