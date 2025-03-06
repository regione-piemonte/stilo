// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlElement;
import pora.types.prontoweb.eng.it.RESULTMSG;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "getTotaliConsumoFatturatoPagatoResult" })
@XmlRootElement(name = "GetTotaliConsumoFatturatoPagatoResponse")
public class GetTotaliConsumoFatturatoPagatoResponse
{
    @XmlElement(name = "GetTotaliConsumoFatturatoPagatoResult", nillable = true)
    protected RESULTMSG getTotaliConsumoFatturatoPagatoResult;
    
    public RESULTMSG getGetTotaliConsumoFatturatoPagatoResult() {
        return this.getTotaliConsumoFatturatoPagatoResult;
    }
    
    public void setGetTotaliConsumoFatturatoPagatoResult(final RESULTMSG value) {
        this.getTotaliConsumoFatturatoPagatoResult = value;
    }
}
