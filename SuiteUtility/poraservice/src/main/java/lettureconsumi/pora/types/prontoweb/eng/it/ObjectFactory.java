// 
// Decompiled by Procyon v0.5.36
// 

package lettureconsumi.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _TIPOFILE_QNAME;
    private static final QName _ArrayOfDETTAGLIOPRELIEVO_QNAME;
    private static final QName _DETTAGLIOPRELIEVO_QNAME;
    private static final QName _TABLECURVEPRELIEVO_QNAME;
    
    static {
        _TIPOFILE_QNAME = new QName("it.eng.prontoweb.types.pora.lettureconsumi", "TIPOFILE");
        _ArrayOfDETTAGLIOPRELIEVO_QNAME = new QName("it.eng.prontoweb.types.pora.lettureconsumi", "ArrayOfDETTAGLIOPRELIEVO");
        _DETTAGLIOPRELIEVO_QNAME = new QName("it.eng.prontoweb.types.pora.lettureconsumi", "DETTAGLIOPRELIEVO");
        _TABLECURVEPRELIEVO_QNAME = new QName("it.eng.prontoweb.types.pora.lettureconsumi", "TABLECURVEPRELIEVO");
    }
    
    public ArrayOfDETTAGLIOPRELIEVO createArrayOfDETTAGLIOPRELIEVO() {
        return new ArrayOfDETTAGLIOPRELIEVO();
    }
    
    public DETTAGLIOPRELIEVO createDETTAGLIOPRELIEVO() {
        return new DETTAGLIOPRELIEVO();
    }
    
    public TABLECURVEPRELIEVO createTABLECURVEPRELIEVO() {
        return new TABLECURVEPRELIEVO();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.lettureconsumi", name = "TIPOFILE")
    public JAXBElement<TIPOFILE> createTIPOFILE(final TIPOFILE value) {
        return (JAXBElement<TIPOFILE>)new JAXBElement(ObjectFactory._TIPOFILE_QNAME, (Class)TIPOFILE.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.lettureconsumi", name = "ArrayOfDETTAGLIOPRELIEVO")
    public JAXBElement<ArrayOfDETTAGLIOPRELIEVO> createArrayOfDETTAGLIOPRELIEVO(final ArrayOfDETTAGLIOPRELIEVO value) {
        return (JAXBElement<ArrayOfDETTAGLIOPRELIEVO>)new JAXBElement(ObjectFactory._ArrayOfDETTAGLIOPRELIEVO_QNAME, (Class)ArrayOfDETTAGLIOPRELIEVO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.lettureconsumi", name = "DETTAGLIOPRELIEVO")
    public JAXBElement<DETTAGLIOPRELIEVO> createDETTAGLIOPRELIEVO(final DETTAGLIOPRELIEVO value) {
        return (JAXBElement<DETTAGLIOPRELIEVO>)new JAXBElement(ObjectFactory._DETTAGLIOPRELIEVO_QNAME, (Class)DETTAGLIOPRELIEVO.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.lettureconsumi", name = "TABLECURVEPRELIEVO")
    public JAXBElement<TABLECURVEPRELIEVO> createTABLECURVEPRELIEVO(final TABLECURVEPRELIEVO value) {
        return (JAXBElement<TABLECURVEPRELIEVO>)new JAXBElement(ObjectFactory._TABLECURVEPRELIEVO_QNAME, (Class)TABLECURVEPRELIEVO.class, (Class)null, (Object)value);
    }
}
