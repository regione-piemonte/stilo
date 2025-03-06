// 
// Decompiled by Procyon v0.5.36
// 

package filtri.types.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _FiltroRichiesta_QNAME;
    
    static {
        _FiltroRichiesta_QNAME = new QName("it.eng.prontoweb.types.pora.types.filtri", "FiltroRichiesta");
    }
    
    public FiltroRichiesta createFiltroRichiesta() {
        return new FiltroRichiesta();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.types.filtri", name = "FiltroRichiesta")
    public JAXBElement<FiltroRichiesta> createFiltroRichiesta(final FiltroRichiesta value) {
        return (JAXBElement<FiltroRichiesta>)new JAXBElement(ObjectFactory._FiltroRichiesta_QNAME, (Class)FiltroRichiesta.class, (Class)null, (Object)value);
    }
}
