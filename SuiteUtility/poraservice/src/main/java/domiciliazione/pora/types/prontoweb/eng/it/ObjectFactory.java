// 
// Decompiled by Procyon v0.5.36
// 

package domiciliazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _DOMICILIAZIONE_QNAME;
    
    static {
        _DOMICILIAZIONE_QNAME = new QName("it.eng.prontoweb.types.pora.domiciliazione", "DOMICILIAZIONE");
    }
    
    public DOMICILIAZIONE createDOMICILIAZIONE() {
        return new DOMICILIAZIONE();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.domiciliazione", name = "DOMICILIAZIONE")
    public JAXBElement<DOMICILIAZIONE> createDOMICILIAZIONE(final DOMICILIAZIONE value) {
        return (JAXBElement<DOMICILIAZIONE>)new JAXBElement(ObjectFactory._DOMICILIAZIONE_QNAME, (Class)DOMICILIAZIONE.class, (Class)null, (Object)value);
    }
}
