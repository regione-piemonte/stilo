// 
// Decompiled by Procyon v0.5.36
// 

package stradario.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _STRCOMUNELOCALITA_QNAME;
    private static final QName _ArrayOfSTRELEMCOMUNELOCALITA_QNAME;
    private static final QName _STRELEMCOMUNELOCALITA_QNAME;
    private static final QName _ArrayOfSTRRESULTEXT_QNAME;
    private static final QName _STRRESULTEXT_QNAME;
    
    static {
        _STRCOMUNELOCALITA_QNAME = new QName("it.eng.prontoweb.types.pora.stradario", "STRCOMUNELOCALITA");
        _ArrayOfSTRELEMCOMUNELOCALITA_QNAME = new QName("it.eng.prontoweb.types.pora.stradario", "ArrayOfSTRELEMCOMUNELOCALITA");
        _STRELEMCOMUNELOCALITA_QNAME = new QName("it.eng.prontoweb.types.pora.stradario", "STRELEMCOMUNELOCALITA");
        _ArrayOfSTRRESULTEXT_QNAME = new QName("it.eng.prontoweb.types.pora.stradario", "ArrayOfSTRRESULTEXT");
        _STRRESULTEXT_QNAME = new QName("it.eng.prontoweb.types.pora.stradario", "STRRESULTEXT");
    }
    
    public STRCOMUNELOCALITA createSTRCOMUNELOCALITA() {
        return new STRCOMUNELOCALITA();
    }
    
    public ArrayOfSTRELEMCOMUNELOCALITA createArrayOfSTRELEMCOMUNELOCALITA() {
        return new ArrayOfSTRELEMCOMUNELOCALITA();
    }
    
    public STRELEMCOMUNELOCALITA createSTRELEMCOMUNELOCALITA() {
        return new STRELEMCOMUNELOCALITA();
    }
    
    public ArrayOfSTRRESULTEXT createArrayOfSTRRESULTEXT() {
        return new ArrayOfSTRRESULTEXT();
    }
    
    public STRRESULTEXT createSTRRESULTEXT() {
        return new STRRESULTEXT();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.stradario", name = "STRCOMUNELOCALITA")
    public JAXBElement<STRCOMUNELOCALITA> createSTRCOMUNELOCALITA(final STRCOMUNELOCALITA value) {
        return (JAXBElement<STRCOMUNELOCALITA>)new JAXBElement(ObjectFactory._STRCOMUNELOCALITA_QNAME, (Class)STRCOMUNELOCALITA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.stradario", name = "ArrayOfSTRELEMCOMUNELOCALITA")
    public JAXBElement<ArrayOfSTRELEMCOMUNELOCALITA> createArrayOfSTRELEMCOMUNELOCALITA(final ArrayOfSTRELEMCOMUNELOCALITA value) {
        return (JAXBElement<ArrayOfSTRELEMCOMUNELOCALITA>)new JAXBElement(ObjectFactory._ArrayOfSTRELEMCOMUNELOCALITA_QNAME, (Class)ArrayOfSTRELEMCOMUNELOCALITA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.stradario", name = "STRELEMCOMUNELOCALITA")
    public JAXBElement<STRELEMCOMUNELOCALITA> createSTRELEMCOMUNELOCALITA(final STRELEMCOMUNELOCALITA value) {
        return (JAXBElement<STRELEMCOMUNELOCALITA>)new JAXBElement(ObjectFactory._STRELEMCOMUNELOCALITA_QNAME, (Class)STRELEMCOMUNELOCALITA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.stradario", name = "ArrayOfSTRRESULTEXT")
    public JAXBElement<ArrayOfSTRRESULTEXT> createArrayOfSTRRESULTEXT(final ArrayOfSTRRESULTEXT value) {
        return (JAXBElement<ArrayOfSTRRESULTEXT>)new JAXBElement(ObjectFactory._ArrayOfSTRRESULTEXT_QNAME, (Class)ArrayOfSTRRESULTEXT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.stradario", name = "STRRESULTEXT")
    public JAXBElement<STRRESULTEXT> createSTRRESULTEXT(final STRRESULTEXT value) {
        return (JAXBElement<STRRESULTEXT>)new JAXBElement(ObjectFactory._STRRESULTEXT_QNAME, (Class)STRRESULTEXT.class, (Class)null, (Object)value);
    }
}
