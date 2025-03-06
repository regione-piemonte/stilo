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
@XmlType(name = "", propOrder = { "getUtentiNewsletterResult" })
@XmlRootElement(name = "GetUtentiNewsletterResponse")
public class GetUtentiNewsletterResponse
{
    @XmlElement(name = "GetUtentiNewsletterResult", nillable = true)
    protected RESULTMSG getUtentiNewsletterResult;
    
    public RESULTMSG getGetUtentiNewsletterResult() {
        return this.getUtentiNewsletterResult;
    }
    
    public void setGetUtentiNewsletterResult(final RESULTMSG value) {
        this.getUtentiNewsletterResult = value;
    }
}
