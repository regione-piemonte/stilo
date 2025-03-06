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
@XmlType(name = "", propOrder = { "multicompany", "nazioneCod", "regioneCod", "criterioRicerca" })
@XmlRootElement(name = "GetProvince")
public class GetProvince
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String nazioneCod;
    @XmlElement(nillable = true)
    protected String regioneCod;
    @XmlElement(nillable = true)
    protected String criterioRicerca;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getNazioneCod() {
        return this.nazioneCod;
    }
    
    public void setNazioneCod(final String value) {
        this.nazioneCod = value;
    }
    
    public String getRegioneCod() {
        return this.regioneCod;
    }
    
    public void setRegioneCod(final String value) {
        this.regioneCod = value;
    }
    
    public String getCriterioRicerca() {
        return this.criterioRicerca;
    }
    
    public void setCriterioRicerca(final String value) {
        this.criterioRicerca = value;
    }
}
