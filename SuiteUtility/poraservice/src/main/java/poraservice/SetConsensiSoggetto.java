// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import registrazione.pora.types.prontoweb.eng.it.ArrayOfCONSENSO;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "soggettoCod", "consensi" })
@XmlRootElement(name = "SetConsensiSoggetto")
public class SetConsensiSoggetto
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String soggettoCod;
    @XmlElement(nillable = true)
    protected ArrayOfCONSENSO consensi;
    
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
    
    public ArrayOfCONSENSO getConsensi() {
        return this.consensi;
    }
    
    public void setConsensi(final ArrayOfCONSENSO value) {
        this.consensi = value;
    }
}
