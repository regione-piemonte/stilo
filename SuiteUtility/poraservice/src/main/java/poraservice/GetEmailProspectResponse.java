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
@XmlType(name = "", propOrder = { "getEmailProspectResult" })
@XmlRootElement(name = "GetEmailProspectResponse")
public class GetEmailProspectResponse
{
    @XmlElement(name = "GetEmailProspectResult", nillable = true)
    protected RESULTMSG getEmailProspectResult;
    
    public RESULTMSG getGetEmailProspectResult() {
        return this.getEmailProspectResult;
    }
    
    public void setGetEmailProspectResult(final RESULTMSG value) {
        this.getEmailProspectResult = value;
    }
}
