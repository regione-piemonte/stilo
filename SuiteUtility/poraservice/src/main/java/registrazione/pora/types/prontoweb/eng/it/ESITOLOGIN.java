// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ESITOLOGIN", propOrder = { "autenticazione", "primoccesso" })
@XmlSeeAlso({ ESITOAUTENTICAZIONETOKEN.class, ESITOSBLOCCACCOUNT.class })
public class ESITOLOGIN extends ACCESSOSITO
{
    @XmlElement(name = "AUTENTICAZIONE")
    protected Boolean autenticazione;
    @XmlElement(name = "PRIMOCCESSO")
    protected Boolean primoccesso;
    
    public Boolean isAUTENTICAZIONE() {
        return this.autenticazione;
    }
    
    public void setAUTENTICAZIONE(final Boolean value) {
        this.autenticazione = value;
    }
    
    public Boolean isPRIMOCCESSO() {
        return this.primoccesso;
    }
    
    public void setPRIMOCCESSO(final Boolean value) {
        this.primoccesso = value;
    }
}
