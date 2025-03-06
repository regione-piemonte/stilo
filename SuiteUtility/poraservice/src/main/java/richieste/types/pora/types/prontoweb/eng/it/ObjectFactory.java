// 
// Decompiled by Procyon v0.5.36
// 

package richieste.types.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _ArrayOfTipoRichiestaResult_QNAME;
    private static final QName _TipoRichiestaResult_QNAME;
    
    static {
        _ArrayOfTipoRichiestaResult_QNAME = new QName("it.eng.prontoweb.types.pora.types.richieste", "ArrayOfTipoRichiestaResult");
        _TipoRichiestaResult_QNAME = new QName("it.eng.prontoweb.types.pora.types.richieste", "TipoRichiestaResult");
    }
    
    public ArrayOfTipoRichiestaResult createArrayOfTipoRichiestaResult() {
        return new ArrayOfTipoRichiestaResult();
    }
    
    public TipoRichiestaResult createTipoRichiestaResult() {
        return new TipoRichiestaResult();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.types.richieste", name = "ArrayOfTipoRichiestaResult")
    public JAXBElement<ArrayOfTipoRichiestaResult> createArrayOfTipoRichiestaResult(final ArrayOfTipoRichiestaResult value) {
        return (JAXBElement<ArrayOfTipoRichiestaResult>)new JAXBElement(ObjectFactory._ArrayOfTipoRichiestaResult_QNAME, (Class)ArrayOfTipoRichiestaResult.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.types.richieste", name = "TipoRichiestaResult")
    public JAXBElement<TipoRichiestaResult> createTipoRichiestaResult(final TipoRichiestaResult value) {
        return (JAXBElement<TipoRichiestaResult>)new JAXBElement(ObjectFactory._TipoRichiestaResult_QNAME, (Class)TipoRichiestaResult.class, (Class)null, (Object)value);
    }
}
