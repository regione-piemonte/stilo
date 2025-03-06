// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "comuneCOD", "localitaCOD" })
@XmlRootElement(name = "GetCAPDaComune")
public class GetCAPDaComune
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String comuneCOD;
    @XmlElement(nillable = true)
    protected String localitaCOD;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getComuneCOD() {
        return this.comuneCOD;
    }
    
    public void setComuneCOD(final String value) {
        this.comuneCOD = value;
    }
    
    public String getLocalitaCOD() {
        return this.localitaCOD;
    }
    
    public void setLocalitaCOD(final String value) {
        this.localitaCOD = value;
    }
}
