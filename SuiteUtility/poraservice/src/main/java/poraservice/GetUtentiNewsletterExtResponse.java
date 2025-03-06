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
@XmlType(name = "", propOrder = { "getUtentiNewsletterExtResult" })
@XmlRootElement(name = "GetUtentiNewsletterExtResponse")
public class GetUtentiNewsletterExtResponse
{
    @XmlElement(name = "GetUtentiNewsletterExtResult", nillable = true)
    protected RESULTMSG getUtentiNewsletterExtResult;
    
    public RESULTMSG getGetUtentiNewsletterExtResult() {
        return this.getUtentiNewsletterExtResult;
    }
    
    public void setGetUtentiNewsletterExtResult(final RESULTMSG value) {
        this.getUtentiNewsletterExtResult = value;
    }
}
