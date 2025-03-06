// 
// Decompiled by Procyon v0.5.36
// 

package prospect.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _SOGGETTOPROSPECTBASE_QNAME;
    private static final QName _ArrayOfSOGGETTOPROSPECTBASE_QNAME;
    
    static {
        _SOGGETTOPROSPECTBASE_QNAME = new QName("it.eng.prontoweb.types.pora.prospect", "SOGGETTOPROSPECTBASE");
        _ArrayOfSOGGETTOPROSPECTBASE_QNAME = new QName("it.eng.prontoweb.types.pora.prospect", "ArrayOfSOGGETTOPROSPECTBASE");
    }
    
    public SOGGETTOPROSPECTBASE createSOGGETTOPROSPECTBASE() {
        return new SOGGETTOPROSPECTBASE();
    }
    
    public ArrayOfSOGGETTOPROSPECTBASE createArrayOfSOGGETTOPROSPECTBASE() {
        return new ArrayOfSOGGETTOPROSPECTBASE();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.prospect", name = "SOGGETTOPROSPECTBASE")
    public JAXBElement<SOGGETTOPROSPECTBASE> createSOGGETTOPROSPECTBASE(final SOGGETTOPROSPECTBASE value) {
        return (JAXBElement<SOGGETTOPROSPECTBASE>)new JAXBElement(ObjectFactory._SOGGETTOPROSPECTBASE_QNAME, (Class)SOGGETTOPROSPECTBASE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.prospect", name = "ArrayOfSOGGETTOPROSPECTBASE")
    public JAXBElement<ArrayOfSOGGETTOPROSPECTBASE> createArrayOfSOGGETTOPROSPECTBASE(final ArrayOfSOGGETTOPROSPECTBASE value) {
        return (JAXBElement<ArrayOfSOGGETTOPROSPECTBASE>)new JAXBElement(ObjectFactory._ArrayOfSOGGETTOPROSPECTBASE_QNAME, (Class)ArrayOfSOGGETTOPROSPECTBASE.class, (Class)null, (Object)value);
    }
}
