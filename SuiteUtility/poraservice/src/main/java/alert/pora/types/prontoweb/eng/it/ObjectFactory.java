// 
// Decompiled by Procyon v0.5.36
// 

package alert.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _TABLEDOCUMENTIPAGARE_QNAME;
    private static final QName _INFOPROSSIMABOLL_QNAME;
    private static final QName _DATAPROSSIMABOLL_QNAME;
    private static final QName _CONSUMOROSSIMABOLL_QNAME;
    private static final QName _LISTAALERT_QNAME;
    private static final QName _TABLECONSUMIPROSSIMEBOLL_QNAME;
    private static final QName _ArrayOfCONSUMOROSSIMABOLL_QNAME;
    private static final QName _TABLEDATEPROSSIMEBOLL_QNAME;
    private static final QName _ArrayOfDATAPROSSIMABOLL_QNAME;
    
    static {
        _TABLEDOCUMENTIPAGARE_QNAME = new QName("it.eng.prontoweb.types.pora.alert", "TABLEDOCUMENTIPAGARE");
        _INFOPROSSIMABOLL_QNAME = new QName("it.eng.prontoweb.types.pora.alert", "INFOPROSSIMABOLL");
        _DATAPROSSIMABOLL_QNAME = new QName("it.eng.prontoweb.types.pora.alert", "DATAPROSSIMABOLL");
        _CONSUMOROSSIMABOLL_QNAME = new QName("it.eng.prontoweb.types.pora.alert", "CONSUMOROSSIMABOLL");
        _LISTAALERT_QNAME = new QName("it.eng.prontoweb.types.pora.alert", "LISTAALERT");
        _TABLECONSUMIPROSSIMEBOLL_QNAME = new QName("it.eng.prontoweb.types.pora.alert", "TABLECONSUMIPROSSIMEBOLL");
        _ArrayOfCONSUMOROSSIMABOLL_QNAME = new QName("it.eng.prontoweb.types.pora.alert", "ArrayOfCONSUMOROSSIMABOLL");
        _TABLEDATEPROSSIMEBOLL_QNAME = new QName("it.eng.prontoweb.types.pora.alert", "TABLEDATEPROSSIMEBOLL");
        _ArrayOfDATAPROSSIMABOLL_QNAME = new QName("it.eng.prontoweb.types.pora.alert", "ArrayOfDATAPROSSIMABOLL");
    }
    
    public TABLEDOCUMENTIPAGARE createTABLEDOCUMENTIPAGARE() {
        return new TABLEDOCUMENTIPAGARE();
    }
    
    public INFOPROSSIMABOLL createINFOPROSSIMABOLL() {
        return new INFOPROSSIMABOLL();
    }
    
    public DATAPROSSIMABOLL createDATAPROSSIMABOLL() {
        return new DATAPROSSIMABOLL();
    }
    
    public CONSUMOROSSIMABOLL createCONSUMOROSSIMABOLL() {
        return new CONSUMOROSSIMABOLL();
    }
    
    public LISTAALERT createLISTAALERT() {
        return new LISTAALERT();
    }
    
    public TABLECONSUMIPROSSIMEBOLL createTABLECONSUMIPROSSIMEBOLL() {
        return new TABLECONSUMIPROSSIMEBOLL();
    }
    
    public ArrayOfCONSUMOROSSIMABOLL createArrayOfCONSUMOROSSIMABOLL() {
        return new ArrayOfCONSUMOROSSIMABOLL();
    }
    
    public TABLEDATEPROSSIMEBOLL createTABLEDATEPROSSIMEBOLL() {
        return new TABLEDATEPROSSIMEBOLL();
    }
    
    public ArrayOfDATAPROSSIMABOLL createArrayOfDATAPROSSIMABOLL() {
        return new ArrayOfDATAPROSSIMABOLL();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.alert", name = "TABLEDOCUMENTIPAGARE")
    public JAXBElement<TABLEDOCUMENTIPAGARE> createTABLEDOCUMENTIPAGARE(final TABLEDOCUMENTIPAGARE value) {
        return (JAXBElement<TABLEDOCUMENTIPAGARE>)new JAXBElement(ObjectFactory._TABLEDOCUMENTIPAGARE_QNAME, (Class)TABLEDOCUMENTIPAGARE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.alert", name = "INFOPROSSIMABOLL")
    public JAXBElement<INFOPROSSIMABOLL> createINFOPROSSIMABOLL(final INFOPROSSIMABOLL value) {
        return (JAXBElement<INFOPROSSIMABOLL>)new JAXBElement(ObjectFactory._INFOPROSSIMABOLL_QNAME, (Class)INFOPROSSIMABOLL.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.alert", name = "DATAPROSSIMABOLL")
    public JAXBElement<DATAPROSSIMABOLL> createDATAPROSSIMABOLL(final DATAPROSSIMABOLL value) {
        return (JAXBElement<DATAPROSSIMABOLL>)new JAXBElement(ObjectFactory._DATAPROSSIMABOLL_QNAME, (Class)DATAPROSSIMABOLL.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.alert", name = "CONSUMOROSSIMABOLL")
    public JAXBElement<CONSUMOROSSIMABOLL> createCONSUMOROSSIMABOLL(final CONSUMOROSSIMABOLL value) {
        return (JAXBElement<CONSUMOROSSIMABOLL>)new JAXBElement(ObjectFactory._CONSUMOROSSIMABOLL_QNAME, (Class)CONSUMOROSSIMABOLL.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.alert", name = "LISTAALERT")
    public JAXBElement<LISTAALERT> createLISTAALERT(final LISTAALERT value) {
        return (JAXBElement<LISTAALERT>)new JAXBElement(ObjectFactory._LISTAALERT_QNAME, (Class)LISTAALERT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.alert", name = "TABLECONSUMIPROSSIMEBOLL")
    public JAXBElement<TABLECONSUMIPROSSIMEBOLL> createTABLECONSUMIPROSSIMEBOLL(final TABLECONSUMIPROSSIMEBOLL value) {
        return (JAXBElement<TABLECONSUMIPROSSIMEBOLL>)new JAXBElement(ObjectFactory._TABLECONSUMIPROSSIMEBOLL_QNAME, (Class)TABLECONSUMIPROSSIMEBOLL.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.alert", name = "ArrayOfCONSUMOROSSIMABOLL")
    public JAXBElement<ArrayOfCONSUMOROSSIMABOLL> createArrayOfCONSUMOROSSIMABOLL(final ArrayOfCONSUMOROSSIMABOLL value) {
        return (JAXBElement<ArrayOfCONSUMOROSSIMABOLL>)new JAXBElement(ObjectFactory._ArrayOfCONSUMOROSSIMABOLL_QNAME, (Class)ArrayOfCONSUMOROSSIMABOLL.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.alert", name = "TABLEDATEPROSSIMEBOLL")
    public JAXBElement<TABLEDATEPROSSIMEBOLL> createTABLEDATEPROSSIMEBOLL(final TABLEDATEPROSSIMEBOLL value) {
        return (JAXBElement<TABLEDATEPROSSIMEBOLL>)new JAXBElement(ObjectFactory._TABLEDATEPROSSIMEBOLL_QNAME, (Class)TABLEDATEPROSSIMEBOLL.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.alert", name = "ArrayOfDATAPROSSIMABOLL")
    public JAXBElement<ArrayOfDATAPROSSIMABOLL> createArrayOfDATAPROSSIMABOLL(final ArrayOfDATAPROSSIMABOLL value) {
        return (JAXBElement<ArrayOfDATAPROSSIMABOLL>)new JAXBElement(ObjectFactory._ArrayOfDATAPROSSIMABOLL_QNAME, (Class)ArrayOfDATAPROSSIMABOLL.class, (Class)null, (Object)value);
    }
}
