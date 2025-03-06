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
@XmlType(name = "", propOrder = { "getSituazioneDebitoriaResult" })
@XmlRootElement(name = "GetSituazioneDebitoriaResponse")
public class GetSituazioneDebitoriaResponse
{
    @XmlElement(name = "GetSituazioneDebitoriaResult", nillable = true)
    protected RESULTMSG getSituazioneDebitoriaResult;
    
    public RESULTMSG getGetSituazioneDebitoriaResult() {
        return this.getSituazioneDebitoriaResult;
    }
    
    public void setGetSituazioneDebitoriaResult(final RESULTMSG value) {
        this.getSituazioneDebitoriaResult = value;
    }
}
