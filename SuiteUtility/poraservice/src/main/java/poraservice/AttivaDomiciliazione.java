// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import domiciliazione.pora.types.prontoweb.eng.it.DOMICILIAZIONE;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "soggettoCod", "forntituraCod", "domiciliazione" })
@XmlRootElement(name = "AttivaDomiciliazione")
public class AttivaDomiciliazione
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String soggettoCod;
    @XmlElement(nillable = true)
    protected String forntituraCod;
    @XmlElement(nillable = true)
    protected DOMICILIAZIONE domiciliazione;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getSoggettoCod() {
        return this.soggettoCod;
    }
    
    public void setSoggettoCod(final String value) {
        this.soggettoCod = value;
    }
    
    public String getForntituraCod() {
        return this.forntituraCod;
    }
    
    public void setForntituraCod(final String value) {
        this.forntituraCod = value;
    }
    
    public DOMICILIAZIONE getDomiciliazione() {
        return this.domiciliazione;
    }
    
    public void setDomiciliazione(final DOMICILIAZIONE value) {
        this.domiciliazione = value;
    }
}
