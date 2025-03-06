// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _INFOLINGUA_QNAME;
    private static final QName _ArrayOfSTATOFORNITURA_QNAME;
    private static final QName _STATOFORNITURA_QNAME;
    private static final QName _RESULTMSG_QNAME;
    private static final QName _TYPERESPONSE_QNAME;
    private static final QName _TABLEFORNITUREEXT_QNAME;
    private static final QName _PWEBTABLE_QNAME;
    private static final QName _SORTDIRECTION_QNAME;
    private static final QName _FORNITURAEXT_QNAME;
    private static final QName _FORNITURA_QNAME;
    private static final QName _FORNITURAMIN_QNAME;
    private static final QName _INDIRIZZO_QNAME;
    private static final QName _ArrayOfFORNITURAEXT_QNAME;
    private static final QName _ArrayOfSTRRESULT_QNAME;
    private static final QName _STRRESULT_QNAME;
    private static final QName _ESITOPAPERLESS_QNAME;
    private static final QName _ArrayOfRUOLO_QNAME;
    private static final QName _RUOLO_QNAME;
    private static final QName _TABLEFORNITURE_QNAME;
    private static final QName _ArrayOfFORNITURA_QNAME;
    private static final QName _ArrayOfRESULTMSG_QNAME;
    private static final QName _TOTALICONSUMO_QNAME;
    private static final QName _TABLEDATISOGGETTO_QNAME;
    private static final QName _PWEBTABLEFILTER_QNAME;
    private static final QName _ArrayOfFORNITURACP_QNAME;
    private static final QName _FORNITURACP_QNAME;
    
    static {
        _INFOLINGUA_QNAME = new QName("it.eng.prontoweb.types.pora", "INFOLINGUA");
        _ArrayOfSTATOFORNITURA_QNAME = new QName("it.eng.prontoweb.types.pora", "ArrayOfSTATOFORNITURA");
        _STATOFORNITURA_QNAME = new QName("it.eng.prontoweb.types.pora", "STATOFORNITURA");
        _RESULTMSG_QNAME = new QName("it.eng.prontoweb.types.pora", "RESULTMSG");
        _TYPERESPONSE_QNAME = new QName("it.eng.prontoweb.types.pora", "TYPERESPONSE");
        _TABLEFORNITUREEXT_QNAME = new QName("it.eng.prontoweb.types.pora", "TABLEFORNITUREEXT");
        _PWEBTABLE_QNAME = new QName("it.eng.prontoweb.types.pora", "PWEBTABLE");
        _SORTDIRECTION_QNAME = new QName("it.eng.prontoweb.types.pora", "SORTDIRECTION");
        _FORNITURAEXT_QNAME = new QName("it.eng.prontoweb.types.pora", "FORNITURAEXT");
        _FORNITURA_QNAME = new QName("it.eng.prontoweb.types.pora", "FORNITURA");
        _FORNITURAMIN_QNAME = new QName("it.eng.prontoweb.types.pora", "FORNITURAMIN");
        _INDIRIZZO_QNAME = new QName("it.eng.prontoweb.types.pora", "INDIRIZZO");
        _ArrayOfFORNITURAEXT_QNAME = new QName("it.eng.prontoweb.types.pora", "ArrayOfFORNITURAEXT");
        _ArrayOfSTRRESULT_QNAME = new QName("it.eng.prontoweb.types.pora", "ArrayOfSTRRESULT");
        _STRRESULT_QNAME = new QName("it.eng.prontoweb.types.pora", "STRRESULT");
        _ESITOPAPERLESS_QNAME = new QName("it.eng.prontoweb.types.pora", "ESITOPAPERLESS");
        _ArrayOfRUOLO_QNAME = new QName("it.eng.prontoweb.types.pora", "ArrayOfRUOLO");
        _RUOLO_QNAME = new QName("it.eng.prontoweb.types.pora", "RUOLO");
        _TABLEFORNITURE_QNAME = new QName("it.eng.prontoweb.types.pora", "TABLEFORNITURE");
        _ArrayOfFORNITURA_QNAME = new QName("it.eng.prontoweb.types.pora", "ArrayOfFORNITURA");
        _ArrayOfRESULTMSG_QNAME = new QName("it.eng.prontoweb.types.pora", "ArrayOfRESULTMSG");
        _TOTALICONSUMO_QNAME = new QName("it.eng.prontoweb.types.pora", "TOTALICONSUMO");
        _TABLEDATISOGGETTO_QNAME = new QName("it.eng.prontoweb.types.pora", "TABLEDATISOGGETTO");
        _PWEBTABLEFILTER_QNAME = new QName("it.eng.prontoweb.types.pora", "PWEBTABLEFILTER");
        _ArrayOfFORNITURACP_QNAME = new QName("it.eng.prontoweb.types.pora", "ArrayOfFORNITURACP");
        _FORNITURACP_QNAME = new QName("it.eng.prontoweb.types.pora", "FORNITURACP");
    }
    
    public ArrayOfSTATOFORNITURA createArrayOfSTATOFORNITURA() {
        return new ArrayOfSTATOFORNITURA();
    }
    
    public RESULTMSG createRESULTMSG() {
        return new RESULTMSG();
    }
    
    public PWEBTABLEFILTER createPWEBTABLEFILTER() {
        return new PWEBTABLEFILTER();
    }
    
    public ArrayOfFORNITURACP createArrayOfFORNITURACP() {
        return new ArrayOfFORNITURACP();
    }
    
    public ArrayOfRUOLO createArrayOfRUOLO() {
        return new ArrayOfRUOLO();
    }
    
    public INFOLINGUA createINFOLINGUA() {
        return new INFOLINGUA();
    }
    
    public STATOFORNITURA createSTATOFORNITURA() {
        return new STATOFORNITURA();
    }
    
    public TABLEFORNITUREEXT createTABLEFORNITUREEXT() {
        return new TABLEFORNITUREEXT();
    }
    
    public PWEBTABLE createPWEBTABLE() {
        return new PWEBTABLE();
    }
    
    public FORNITURAEXT createFORNITURAEXT() {
        return new FORNITURAEXT();
    }
    
    public FORNITURA createFORNITURA() {
        return new FORNITURA();
    }
    
    public FORNITURAMIN createFORNITURAMIN() {
        return new FORNITURAMIN();
    }
    
    public INDIRIZZO createINDIRIZZO() {
        return new INDIRIZZO();
    }
    
    public ArrayOfFORNITURAEXT createArrayOfFORNITURAEXT() {
        return new ArrayOfFORNITURAEXT();
    }
    
    public ArrayOfSTRRESULT createArrayOfSTRRESULT() {
        return new ArrayOfSTRRESULT();
    }
    
    public STRRESULT createSTRRESULT() {
        return new STRRESULT();
    }
    
    public ESITOPAPERLESS createESITOPAPERLESS() {
        return new ESITOPAPERLESS();
    }
    
    public RUOLO createRUOLO() {
        return new RUOLO();
    }
    
    public TABLEFORNITURE createTABLEFORNITURE() {
        return new TABLEFORNITURE();
    }
    
    public ArrayOfFORNITURA createArrayOfFORNITURA() {
        return new ArrayOfFORNITURA();
    }
    
    public ArrayOfRESULTMSG createArrayOfRESULTMSG() {
        return new ArrayOfRESULTMSG();
    }
    
    public TOTALICONSUMO createTOTALICONSUMO() {
        return new TOTALICONSUMO();
    }
    
    public TABLEDATISOGGETTO createTABLEDATISOGGETTO() {
        return new TABLEDATISOGGETTO();
    }
    
    public FORNITURACP createFORNITURACP() {
        return new FORNITURACP();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "INFOLINGUA")
    public JAXBElement<INFOLINGUA> createINFOLINGUA(final INFOLINGUA value) {
        return (JAXBElement<INFOLINGUA>)new JAXBElement(ObjectFactory._INFOLINGUA_QNAME, (Class)INFOLINGUA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "ArrayOfSTATOFORNITURA")
    public JAXBElement<ArrayOfSTATOFORNITURA> createArrayOfSTATOFORNITURA(final ArrayOfSTATOFORNITURA value) {
        return (JAXBElement<ArrayOfSTATOFORNITURA>)new JAXBElement(ObjectFactory._ArrayOfSTATOFORNITURA_QNAME, (Class)ArrayOfSTATOFORNITURA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "STATOFORNITURA")
    public JAXBElement<STATOFORNITURA> createSTATOFORNITURA(final STATOFORNITURA value) {
        return (JAXBElement<STATOFORNITURA>)new JAXBElement(ObjectFactory._STATOFORNITURA_QNAME, (Class)STATOFORNITURA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "RESULTMSG")
    public JAXBElement<RESULTMSG> createRESULTMSG(final RESULTMSG value) {
        return (JAXBElement<RESULTMSG>)new JAXBElement(ObjectFactory._RESULTMSG_QNAME, (Class)RESULTMSG.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "TYPERESPONSE")
    public JAXBElement<TYPERESPONSE> createTYPERESPONSE(final TYPERESPONSE value) {
        return (JAXBElement<TYPERESPONSE>)new JAXBElement(ObjectFactory._TYPERESPONSE_QNAME, (Class)TYPERESPONSE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "TABLEFORNITUREEXT")
    public JAXBElement<TABLEFORNITUREEXT> createTABLEFORNITUREEXT(final TABLEFORNITUREEXT value) {
        return (JAXBElement<TABLEFORNITUREEXT>)new JAXBElement(ObjectFactory._TABLEFORNITUREEXT_QNAME, (Class)TABLEFORNITUREEXT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "PWEBTABLE")
    public JAXBElement<PWEBTABLE> createPWEBTABLE(final PWEBTABLE value) {
        return (JAXBElement<PWEBTABLE>)new JAXBElement(ObjectFactory._PWEBTABLE_QNAME, (Class)PWEBTABLE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "SORTDIRECTION")
    public JAXBElement<SORTDIRECTION> createSORTDIRECTION(final SORTDIRECTION value) {
        return (JAXBElement<SORTDIRECTION>)new JAXBElement(ObjectFactory._SORTDIRECTION_QNAME, (Class)SORTDIRECTION.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "FORNITURAEXT")
    public JAXBElement<FORNITURAEXT> createFORNITURAEXT(final FORNITURAEXT value) {
        return (JAXBElement<FORNITURAEXT>)new JAXBElement(ObjectFactory._FORNITURAEXT_QNAME, (Class)FORNITURAEXT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "FORNITURA")
    public JAXBElement<FORNITURA> createFORNITURA(final FORNITURA value) {
        return (JAXBElement<FORNITURA>)new JAXBElement(ObjectFactory._FORNITURA_QNAME, (Class)FORNITURA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "FORNITURAMIN")
    public JAXBElement<FORNITURAMIN> createFORNITURAMIN(final FORNITURAMIN value) {
        return (JAXBElement<FORNITURAMIN>)new JAXBElement(ObjectFactory._FORNITURAMIN_QNAME, (Class)FORNITURAMIN.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "INDIRIZZO")
    public JAXBElement<INDIRIZZO> createINDIRIZZO(final INDIRIZZO value) {
        return (JAXBElement<INDIRIZZO>)new JAXBElement(ObjectFactory._INDIRIZZO_QNAME, (Class)INDIRIZZO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "ArrayOfFORNITURAEXT")
    public JAXBElement<ArrayOfFORNITURAEXT> createArrayOfFORNITURAEXT(final ArrayOfFORNITURAEXT value) {
        return (JAXBElement<ArrayOfFORNITURAEXT>)new JAXBElement(ObjectFactory._ArrayOfFORNITURAEXT_QNAME, (Class)ArrayOfFORNITURAEXT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "ArrayOfSTRRESULT")
    public JAXBElement<ArrayOfSTRRESULT> createArrayOfSTRRESULT(final ArrayOfSTRRESULT value) {
        return (JAXBElement<ArrayOfSTRRESULT>)new JAXBElement(ObjectFactory._ArrayOfSTRRESULT_QNAME, (Class)ArrayOfSTRRESULT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "STRRESULT")
    public JAXBElement<STRRESULT> createSTRRESULT(final STRRESULT value) {
        return (JAXBElement<STRRESULT>)new JAXBElement(ObjectFactory._STRRESULT_QNAME, (Class)STRRESULT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "ESITOPAPERLESS")
    public JAXBElement<ESITOPAPERLESS> createESITOPAPERLESS(final ESITOPAPERLESS value) {
        return (JAXBElement<ESITOPAPERLESS>)new JAXBElement(ObjectFactory._ESITOPAPERLESS_QNAME, (Class)ESITOPAPERLESS.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "ArrayOfRUOLO")
    public JAXBElement<ArrayOfRUOLO> createArrayOfRUOLO(final ArrayOfRUOLO value) {
        return (JAXBElement<ArrayOfRUOLO>)new JAXBElement(ObjectFactory._ArrayOfRUOLO_QNAME, (Class)ArrayOfRUOLO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "RUOLO")
    public JAXBElement<RUOLO> createRUOLO(final RUOLO value) {
        return (JAXBElement<RUOLO>)new JAXBElement(ObjectFactory._RUOLO_QNAME, (Class)RUOLO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "TABLEFORNITURE")
    public JAXBElement<TABLEFORNITURE> createTABLEFORNITURE(final TABLEFORNITURE value) {
        return (JAXBElement<TABLEFORNITURE>)new JAXBElement(ObjectFactory._TABLEFORNITURE_QNAME, (Class)TABLEFORNITURE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "ArrayOfFORNITURA")
    public JAXBElement<ArrayOfFORNITURA> createArrayOfFORNITURA(final ArrayOfFORNITURA value) {
        return (JAXBElement<ArrayOfFORNITURA>)new JAXBElement(ObjectFactory._ArrayOfFORNITURA_QNAME, (Class)ArrayOfFORNITURA.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "ArrayOfRESULTMSG")
    public JAXBElement<ArrayOfRESULTMSG> createArrayOfRESULTMSG(final ArrayOfRESULTMSG value) {
        return (JAXBElement<ArrayOfRESULTMSG>)new JAXBElement(ObjectFactory._ArrayOfRESULTMSG_QNAME, (Class)ArrayOfRESULTMSG.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "TOTALICONSUMO")
    public JAXBElement<TOTALICONSUMO> createTOTALICONSUMO(final TOTALICONSUMO value) {
        return (JAXBElement<TOTALICONSUMO>)new JAXBElement(ObjectFactory._TOTALICONSUMO_QNAME, (Class)TOTALICONSUMO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "TABLEDATISOGGETTO")
    public JAXBElement<TABLEDATISOGGETTO> createTABLEDATISOGGETTO(final TABLEDATISOGGETTO value) {
        return (JAXBElement<TABLEDATISOGGETTO>)new JAXBElement(ObjectFactory._TABLEDATISOGGETTO_QNAME, (Class)TABLEDATISOGGETTO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "PWEBTABLEFILTER")
    public JAXBElement<PWEBTABLEFILTER> createPWEBTABLEFILTER(final PWEBTABLEFILTER value) {
        return (JAXBElement<PWEBTABLEFILTER>)new JAXBElement(ObjectFactory._PWEBTABLEFILTER_QNAME, (Class)PWEBTABLEFILTER.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "ArrayOfFORNITURACP")
    public JAXBElement<ArrayOfFORNITURACP> createArrayOfFORNITURACP(final ArrayOfFORNITURACP value) {
        return (JAXBElement<ArrayOfFORNITURACP>)new JAXBElement(ObjectFactory._ArrayOfFORNITURACP_QNAME, (Class)ArrayOfFORNITURACP.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora", name = "FORNITURACP")
    public JAXBElement<FORNITURACP> createFORNITURACP(final FORNITURACP value) {
        return (JAXBElement<FORNITURACP>)new JAXBElement(ObjectFactory._FORNITURACP_QNAME, (Class)FORNITURACP.class, (Class)null, (Object)value);
    }
}
