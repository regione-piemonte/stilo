// 
// Decompiled by Procyon v0.5.36
// 

package autolettura.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _LETTURA_QNAME;
    private static final QName _TABLELETTURE_QNAME;
    private static final QName _ArrayOfLETTURA_QNAME;
    private static final QName _PERIODO_QNAME;
    private static final QName _LETTURAEXT_QNAME;
    private static final QName _TABLELETTUREEXT_QNAME;
    private static final QName _ArrayOfLETTURAEXT_QNAME;
    private static final QName _TIPOAUTOLETTURA_QNAME;
    
    static {
        _LETTURA_QNAME = new QName("it.eng.prontoweb.types.pora.autolettura", "LETTURA");
        _TABLELETTURE_QNAME = new QName("it.eng.prontoweb.types.pora.autolettura", "TABLELETTURE");
        _ArrayOfLETTURA_QNAME = new QName("it.eng.prontoweb.types.pora.autolettura", "ArrayOfLETTURA");
        _PERIODO_QNAME = new QName("it.eng.prontoweb.types.pora.autolettura", "PERIODO");
        _LETTURAEXT_QNAME = new QName("it.eng.prontoweb.types.pora.autolettura", "LETTURAEXT");
        _TABLELETTUREEXT_QNAME = new QName("it.eng.prontoweb.types.pora.autolettura", "TABLELETTUREEXT");
        _ArrayOfLETTURAEXT_QNAME = new QName("it.eng.prontoweb.types.pora.autolettura", "ArrayOfLETTURAEXT");
        _TIPOAUTOLETTURA_QNAME = new QName("it.eng.prontoweb.types.pora.autolettura", "TIPOAUTOLETTURA");
    }
    
    public PERIODO createPERIODO() {
        return new PERIODO();
    }
    
    public LETTURA createLETTURA() {
        return new LETTURA();
    }
    
    public TABLELETTURE createTABLELETTURE() {
        return new TABLELETTURE();
    }
    
    public ArrayOfLETTURA createArrayOfLETTURA() {
        return new ArrayOfLETTURA();
    }
    
    public LETTURAEXT createLETTURAEXT() {
        return new LETTURAEXT();
    }
    
    public TABLELETTUREEXT createTABLELETTUREEXT() {
        return new TABLELETTUREEXT();
    }
    
    public ArrayOfLETTURAEXT createArrayOfLETTURAEXT() {
        return new ArrayOfLETTURAEXT();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.autolettura", name = "LETTURA")
    public JAXBElement<LETTURA> createLETTURA(final LETTURA value) {
        return (JAXBElement<LETTURA>)new JAXBElement(ObjectFactory._LETTURA_QNAME, (Class)LETTURA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.autolettura", name = "TABLELETTURE")
    public JAXBElement<TABLELETTURE> createTABLELETTURE(final TABLELETTURE value) {
        return (JAXBElement<TABLELETTURE>)new JAXBElement(ObjectFactory._TABLELETTURE_QNAME, (Class)TABLELETTURE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.autolettura", name = "ArrayOfLETTURA")
    public JAXBElement<ArrayOfLETTURA> createArrayOfLETTURA(final ArrayOfLETTURA value) {
        return (JAXBElement<ArrayOfLETTURA>)new JAXBElement(ObjectFactory._ArrayOfLETTURA_QNAME, (Class)ArrayOfLETTURA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.autolettura", name = "PERIODO")
    public JAXBElement<PERIODO> createPERIODO(final PERIODO value) {
        return (JAXBElement<PERIODO>)new JAXBElement(ObjectFactory._PERIODO_QNAME, (Class)PERIODO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.autolettura", name = "LETTURAEXT")
    public JAXBElement<LETTURAEXT> createLETTURAEXT(final LETTURAEXT value) {
        return (JAXBElement<LETTURAEXT>)new JAXBElement(ObjectFactory._LETTURAEXT_QNAME, (Class)LETTURAEXT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.autolettura", name = "TABLELETTUREEXT")
    public JAXBElement<TABLELETTUREEXT> createTABLELETTUREEXT(final TABLELETTUREEXT value) {
        return (JAXBElement<TABLELETTUREEXT>)new JAXBElement(ObjectFactory._TABLELETTUREEXT_QNAME, (Class)TABLELETTUREEXT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.autolettura", name = "ArrayOfLETTURAEXT")
    public JAXBElement<ArrayOfLETTURAEXT> createArrayOfLETTURAEXT(final ArrayOfLETTURAEXT value) {
        return (JAXBElement<ArrayOfLETTURAEXT>)new JAXBElement(ObjectFactory._ArrayOfLETTURAEXT_QNAME, (Class)ArrayOfLETTURAEXT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.autolettura", name = "TIPOAUTOLETTURA")
    public JAXBElement<TIPOAUTOLETTURA> createTIPOAUTOLETTURA(final TIPOAUTOLETTURA value) {
        return (JAXBElement<TIPOAUTOLETTURA>)new JAXBElement(ObjectFactory._TIPOAUTOLETTURA_QNAME, (Class)TIPOAUTOLETTURA.class, (Class)null, (Object)value);
    }
}
