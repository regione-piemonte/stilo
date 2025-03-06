// 
// Decompiled by Procyon v0.5.36
// 

package multicompany.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _INFOMULTICOMPANY_QNAME;
    private static final QName _INFOMULTICOMPANYEXT_QNAME;
    private static final QName _ArrayOfINFOMULTICOMPANY_QNAME;
    
    static {
        _INFOMULTICOMPANY_QNAME = new QName("it.eng.prontoweb.types.pora.multicompany", "INFOMULTICOMPANY");
        _INFOMULTICOMPANYEXT_QNAME = new QName("it.eng.prontoweb.types.pora.multicompany", "INFOMULTICOMPANYEXT");
        _ArrayOfINFOMULTICOMPANY_QNAME = new QName("it.eng.prontoweb.types.pora.multicompany", "ArrayOfINFOMULTICOMPANY");
    }
    
    public INFOMULTICOMPANY createINFOMULTICOMPANY() {
        return new INFOMULTICOMPANY();
    }
    
    public INFOMULTICOMPANYEXT createINFOMULTICOMPANYEXT() {
        return new INFOMULTICOMPANYEXT();
    }
    
    public ArrayOfINFOMULTICOMPANY createArrayOfINFOMULTICOMPANY() {
        return new ArrayOfINFOMULTICOMPANY();
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.multicompany", name = "INFOMULTICOMPANY")
    public JAXBElement<INFOMULTICOMPANY> createINFOMULTICOMPANY(final INFOMULTICOMPANY value) {
        return (JAXBElement<INFOMULTICOMPANY>)new JAXBElement(ObjectFactory._INFOMULTICOMPANY_QNAME, (Class)INFOMULTICOMPANY.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.multicompany", name = "INFOMULTICOMPANYEXT")
    public JAXBElement<INFOMULTICOMPANYEXT> createINFOMULTICOMPANYEXT(final INFOMULTICOMPANYEXT value) {
        return (JAXBElement<INFOMULTICOMPANYEXT>)new JAXBElement(ObjectFactory._INFOMULTICOMPANYEXT_QNAME, (Class)INFOMULTICOMPANYEXT.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "it.eng.prontoweb.types.pora.multicompany", name = "ArrayOfINFOMULTICOMPANY")
    public JAXBElement<ArrayOfINFOMULTICOMPANY> createArrayOfINFOMULTICOMPANY(final ArrayOfINFOMULTICOMPANY value) {
        return (JAXBElement<ArrayOfINFOMULTICOMPANY>)new JAXBElement(ObjectFactory._ArrayOfINFOMULTICOMPANY_QNAME, (Class)ArrayOfINFOMULTICOMPANY.class, (Class)null, (Object)value);
    }
}
