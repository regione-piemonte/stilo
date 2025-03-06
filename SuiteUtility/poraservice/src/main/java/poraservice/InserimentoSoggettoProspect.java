// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import registrazione.pora.types.prontoweb.eng.it.ArrayOfCONSENSO;
import registrazione.pora.types.prontoweb.eng.it.DATISOGGETTO;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "soggettoProspect", "consensi" })
@XmlRootElement(name = "InserimentoSoggettoProspect")
public class InserimentoSoggettoProspect
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected DATISOGGETTO soggettoProspect;
    @XmlElement(nillable = true)
    protected ArrayOfCONSENSO consensi;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public DATISOGGETTO getSoggettoProspect() {
        return this.soggettoProspect;
    }
    
    public void setSoggettoProspect(final DATISOGGETTO value) {
        this.soggettoProspect = value;
    }
    
    public ArrayOfCONSENSO getConsensi() {
        return this.consensi;
    }
    
    public void setConsensi(final ArrayOfCONSENSO value) {
        this.consensi = value;
    }
}
