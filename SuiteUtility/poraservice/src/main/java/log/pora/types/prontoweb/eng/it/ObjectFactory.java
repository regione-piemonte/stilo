// 
// Decompiled by Procyon v0.5.36
// 

package log.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _TABLELOGBE_QNAME;
    private static final QName _ArrayOfLOGBE_QNAME;
    private static final QName _LOGBE_QNAME;
    private static final QName _ArrayOfLOGBEPARAMETER_QNAME;
    private static final QName _LOGBEPARAMETER_QNAME;
    private static final QName _ArrayOfLOGBESTOREDPROCEDURE_QNAME;
    private static final QName _LOGBESTOREDPROCEDURE_QNAME;
    private static final QName _PROVENIENZAPORTALE_QNAME;
    private static final QName _ArrayOfLOGJOB_QNAME;
    private static final QName _LOGJOB_QNAME;
    
    static {
        _TABLELOGBE_QNAME = new QName("it.eng.prontoweb.types.pora.log", "TABLELOGBE");
        _ArrayOfLOGBE_QNAME = new QName("it.eng.prontoweb.types.pora.log", "ArrayOfLOGBE");
        _LOGBE_QNAME = new QName("it.eng.prontoweb.types.pora.log", "LOGBE");
        _ArrayOfLOGBEPARAMETER_QNAME = new QName("it.eng.prontoweb.types.pora.log", "ArrayOfLOGBEPARAMETER");
        _LOGBEPARAMETER_QNAME = new QName("it.eng.prontoweb.types.pora.log", "LOGBEPARAMETER");
        _ArrayOfLOGBESTOREDPROCEDURE_QNAME = new QName("it.eng.prontoweb.types.pora.log", "ArrayOfLOGBESTOREDPROCEDURE");
        _LOGBESTOREDPROCEDURE_QNAME = new QName("it.eng.prontoweb.types.pora.log", "LOGBESTOREDPROCEDURE");
        _PROVENIENZAPORTALE_QNAME = new QName("it.eng.prontoweb.types.pora.log", "PROVENIENZA_PORTALE");
        _ArrayOfLOGJOB_QNAME = new QName("it.eng.prontoweb.types.pora.log", "ArrayOfLOGJOB");
        _LOGJOB_QNAME = new QName("it.eng.prontoweb.types.pora.log", "LOGJOB");
    }
    
    public TABLELOGBE createTABLELOGBE() {
        return new TABLELOGBE();
    }
    
    public ArrayOfLOGBE createArrayOfLOGBE() {
        return new ArrayOfLOGBE();
    }
    
    public LOGBE createLOGBE() {
        return new LOGBE();
    }
    
    public ArrayOfLOGBEPARAMETER createArrayOfLOGBEPARAMETER() {
        return new ArrayOfLOGBEPARAMETER();
    }
    
    public LOGBEPARAMETER createLOGBEPARAMETER() {
        return new LOGBEPARAMETER();
    }
    
    public ArrayOfLOGBESTOREDPROCEDURE createArrayOfLOGBESTOREDPROCEDURE() {
        return new ArrayOfLOGBESTOREDPROCEDURE();
    }
    
    public LOGBESTOREDPROCEDURE createLOGBESTOREDPROCEDURE() {
        return new LOGBESTOREDPROCEDURE();
    }
    
    public ArrayOfLOGJOB createArrayOfLOGJOB() {
        return new ArrayOfLOGJOB();
    }
    
    public LOGJOB createLOGJOB() {
        return new LOGJOB();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "TABLELOGBE")
    public JAXBElement<TABLELOGBE> createTABLELOGBE(final TABLELOGBE value) {
        return (JAXBElement<TABLELOGBE>)new JAXBElement(ObjectFactory._TABLELOGBE_QNAME, (Class)TABLELOGBE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "ArrayOfLOGBE")
    public JAXBElement<ArrayOfLOGBE> createArrayOfLOGBE(final ArrayOfLOGBE value) {
        return (JAXBElement<ArrayOfLOGBE>)new JAXBElement(ObjectFactory._ArrayOfLOGBE_QNAME, (Class)ArrayOfLOGBE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "LOGBE")
    public JAXBElement<LOGBE> createLOGBE(final LOGBE value) {
        return (JAXBElement<LOGBE>)new JAXBElement(ObjectFactory._LOGBE_QNAME, (Class)LOGBE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "ArrayOfLOGBEPARAMETER")
    public JAXBElement<ArrayOfLOGBEPARAMETER> createArrayOfLOGBEPARAMETER(final ArrayOfLOGBEPARAMETER value) {
        return (JAXBElement<ArrayOfLOGBEPARAMETER>)new JAXBElement(ObjectFactory._ArrayOfLOGBEPARAMETER_QNAME, (Class)ArrayOfLOGBEPARAMETER.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "LOGBEPARAMETER")
    public JAXBElement<LOGBEPARAMETER> createLOGBEPARAMETER(final LOGBEPARAMETER value) {
        return (JAXBElement<LOGBEPARAMETER>)new JAXBElement(ObjectFactory._LOGBEPARAMETER_QNAME, (Class)LOGBEPARAMETER.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "ArrayOfLOGBESTOREDPROCEDURE")
    public JAXBElement<ArrayOfLOGBESTOREDPROCEDURE> createArrayOfLOGBESTOREDPROCEDURE(final ArrayOfLOGBESTOREDPROCEDURE value) {
        return (JAXBElement<ArrayOfLOGBESTOREDPROCEDURE>)new JAXBElement(ObjectFactory._ArrayOfLOGBESTOREDPROCEDURE_QNAME, (Class)ArrayOfLOGBESTOREDPROCEDURE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "LOGBESTOREDPROCEDURE")
    public JAXBElement<LOGBESTOREDPROCEDURE> createLOGBESTOREDPROCEDURE(final LOGBESTOREDPROCEDURE value) {
        return (JAXBElement<LOGBESTOREDPROCEDURE>)new JAXBElement(ObjectFactory._LOGBESTOREDPROCEDURE_QNAME, (Class)LOGBESTOREDPROCEDURE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "PROVENIENZA_PORTALE")
    public JAXBElement<PROVENIENZAPORTALE> createPROVENIENZAPORTALE(final PROVENIENZAPORTALE value) {
        return (JAXBElement<PROVENIENZAPORTALE>)new JAXBElement(ObjectFactory._PROVENIENZAPORTALE_QNAME, (Class)PROVENIENZAPORTALE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "ArrayOfLOGJOB")
    public JAXBElement<ArrayOfLOGJOB> createArrayOfLOGJOB(final ArrayOfLOGJOB value) {
        return (JAXBElement<ArrayOfLOGJOB>)new JAXBElement(ObjectFactory._ArrayOfLOGJOB_QNAME, (Class)ArrayOfLOGJOB.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.log", name = "LOGJOB")
    public JAXBElement<LOGJOB> createLOGJOB(final LOGJOB value) {
        return (JAXBElement<LOGJOB>)new JAXBElement(ObjectFactory._LOGJOB_QNAME, (Class)LOGJOB.class, (Class)null, (Object)value);
    }
}
