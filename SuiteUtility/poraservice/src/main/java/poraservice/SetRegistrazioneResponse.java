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
@XmlType(name = "", propOrder = { "setRegistrazioneResult" })
@XmlRootElement(name = "SetRegistrazioneResponse")
public class SetRegistrazioneResponse
{
    @XmlElement(name = "SetRegistrazioneResult", nillable = true)
    protected RESULTMSG setRegistrazioneResult;
    
    public RESULTMSG getSetRegistrazioneResult() {
        return this.setRegistrazioneResult;
    }
    
    public void setSetRegistrazioneResult(final RESULTMSG value) {
        this.setRegistrazioneResult = value;
    }
}
