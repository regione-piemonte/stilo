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
@XmlType(name = "", propOrder = { "getSituazioneDebitoriaExtResult" })
@XmlRootElement(name = "GetSituazioneDebitoriaExtResponse")
public class GetSituazioneDebitoriaExtResponse
{
    @XmlElement(name = "GetSituazioneDebitoriaExtResult", nillable = true)
    protected RESULTMSG getSituazioneDebitoriaExtResult;
    
    public RESULTMSG getGetSituazioneDebitoriaExtResult() {
        return this.getSituazioneDebitoriaExtResult;
    }
    
    public void setGetSituazioneDebitoriaExtResult(final RESULTMSG value) {
        this.getSituazioneDebitoriaExtResult = value;
    }
}
