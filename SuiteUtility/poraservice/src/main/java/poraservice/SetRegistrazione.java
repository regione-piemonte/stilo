// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import pora.types.prontoweb.eng.it.ArrayOfRUOLO;
import registrazione.pora.types.prontoweb.eng.it.ArrayOfCONSENSO;
import registrazione.pora.types.prontoweb.eng.it.DATISOGGETTO;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "username", "password", "soggetto", "consensi", "ruoli" })
@XmlRootElement(name = "SetRegistrazione")
public class SetRegistrazione
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String username;
    @XmlElement(nillable = true)
    protected String password;
    @XmlElement(nillable = true)
    protected DATISOGGETTO soggetto;
    @XmlElement(nillable = true)
    protected ArrayOfCONSENSO consensi;
    @XmlElement(nillable = true)
    protected ArrayOfRUOLO ruoli;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String value) {
        this.username = value;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String value) {
        this.password = value;
    }
    
    public DATISOGGETTO getSoggetto() {
        return this.soggetto;
    }
    
    public void setSoggetto(final DATISOGGETTO value) {
        this.soggetto = value;
    }
    
    public ArrayOfCONSENSO getConsensi() {
        return this.consensi;
    }
    
    public void setConsensi(final ArrayOfCONSENSO value) {
        this.consensi = value;
    }
    
    public ArrayOfRUOLO getRuoli() {
        return this.ruoli;
    }
    
    public void setRuoli(final ArrayOfRUOLO value) {
        this.ruoli = value;
    }
}
