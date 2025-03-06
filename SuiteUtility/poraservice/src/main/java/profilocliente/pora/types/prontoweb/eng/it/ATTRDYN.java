// 
// Decompiled by Procyon v0.5.36
// 

package profilocliente.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ATTRDYN", propOrder = { "attrdyncod", "attrdyndesc", "dominio" })
public class ATTRDYN
{
    @XmlElement(name = "ATTR_DYN_COD", nillable = true)
    protected String attrdyncod;
    @XmlElement(name = "ATTR_DYN_DESC", nillable = true)
    protected String attrdyndesc;
    @XmlElement(name = "DOMINIO", nillable = true)
    protected ArrayOfATTRDYNVAL dominio;
    
    public String getATTRDYNCOD() {
        return this.attrdyncod;
    }
    
    public void setATTRDYNCOD(final String value) {
        this.attrdyncod = value;
    }
    
    public String getATTRDYNDESC() {
        return this.attrdyndesc;
    }
    
    public void setATTRDYNDESC(final String value) {
        this.attrdyndesc = value;
    }
    
    public ArrayOfATTRDYNVAL getDOMINIO() {
        return this.dominio;
    }
    
    public void setDOMINIO(final ArrayOfATTRDYNVAL value) {
        this.dominio = value;
    }
}
