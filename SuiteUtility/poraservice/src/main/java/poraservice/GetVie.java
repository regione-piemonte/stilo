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
@XmlType(name = "", propOrder = { "multicompany", "provinciaCOD", "comuneCOD", "localitaCOD", "criterioRicerca" })
@XmlRootElement(name = "GetVie")
public class GetVie
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String provinciaCOD;
    @XmlElement(nillable = true)
    protected String comuneCOD;
    @XmlElement(nillable = true)
    protected String localitaCOD;
    @XmlElement(nillable = true)
    protected String criterioRicerca;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getProvinciaCOD() {
        return this.provinciaCOD;
    }
    
    public void setProvinciaCOD(final String value) {
        this.provinciaCOD = value;
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
    
    public String getCriterioRicerca() {
        return this.criterioRicerca;
    }
    
    public void setCriterioRicerca(final String value) {
        this.criterioRicerca = value;
    }
}
