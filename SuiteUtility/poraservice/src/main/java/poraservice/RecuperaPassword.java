// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import registrazione.pora.types.prontoweb.eng.it.DATISOGGETTO;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "soggetto" })
@XmlRootElement(name = "RecuperaPassword")
public class RecuperaPassword
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected DATISOGGETTO soggetto;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public DATISOGGETTO getSoggetto() {
        return this.soggetto;
    }
    
    public void setSoggetto(final DATISOGGETTO value) {
        this.soggetto = value;
    }
}
