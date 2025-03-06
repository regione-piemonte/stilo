// 
// Decompiled by Procyon v0.5.36
// 

package estrattoconto.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _DOCUMENTO_QNAME;
    private static final QName _TABLEESTRATTOCONTO_QNAME;
    private static final QName _ArrayOfDOCUMENTO_QNAME;
    
    static {
        _DOCUMENTO_QNAME = new QName("it.eng.prontoweb.types.pora.estrattoconto", "DOCUMENTO");
        _TABLEESTRATTOCONTO_QNAME = new QName("it.eng.prontoweb.types.pora.estrattoconto", "TABLEESTRATTOCONTO");
        _ArrayOfDOCUMENTO_QNAME = new QName("it.eng.prontoweb.types.pora.estrattoconto", "ArrayOfDOCUMENTO");
    }
    
    public DOCUMENTO createDOCUMENTO() {
        return new DOCUMENTO();
    }
    
    public TABLEESTRATTOCONTO createTABLEESTRATTOCONTO() {
        return new TABLEESTRATTOCONTO();
    }
    
    public ArrayOfDOCUMENTO createArrayOfDOCUMENTO() {
        return new ArrayOfDOCUMENTO();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.estrattoconto", name = "DOCUMENTO")
    public JAXBElement<DOCUMENTO> createDOCUMENTO(final DOCUMENTO value) {
        return (JAXBElement<DOCUMENTO>)new JAXBElement(ObjectFactory._DOCUMENTO_QNAME, (Class)DOCUMENTO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.estrattoconto", name = "TABLEESTRATTOCONTO")
    public JAXBElement<TABLEESTRATTOCONTO> createTABLEESTRATTOCONTO(final TABLEESTRATTOCONTO value) {
        return (JAXBElement<TABLEESTRATTOCONTO>)new JAXBElement(ObjectFactory._TABLEESTRATTOCONTO_QNAME, (Class)TABLEESTRATTOCONTO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.estrattoconto", name = "ArrayOfDOCUMENTO")
    public JAXBElement<ArrayOfDOCUMENTO> createArrayOfDOCUMENTO(final ArrayOfDOCUMENTO value) {
        return (JAXBElement<ArrayOfDOCUMENTO>)new JAXBElement(ObjectFactory._ArrayOfDOCUMENTO_QNAME, (Class)ArrayOfDOCUMENTO.class, (Class)null, (Object)value);
    }
}
