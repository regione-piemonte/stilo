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
@XmlType(name = "", propOrder = { "attivaDomiciliazioneResult" })
@XmlRootElement(name = "AttivaDomiciliazioneResponse")
public class AttivaDomiciliazioneResponse
{
    @XmlElement(name = "AttivaDomiciliazioneResult", nillable = true)
    protected RESULTMSG attivaDomiciliazioneResult;
    
    public RESULTMSG getAttivaDomiciliazioneResult() {
        return this.attivaDomiciliazioneResult;
    }
    
    public void setAttivaDomiciliazioneResult(final RESULTMSG value) {
        this.attivaDomiciliazioneResult = value;
    }
}
