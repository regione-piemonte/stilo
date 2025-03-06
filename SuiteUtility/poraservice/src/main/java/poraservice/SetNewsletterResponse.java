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
@XmlType(name = "", propOrder = { "setNewsletterResult" })
@XmlRootElement(name = "SetNewsletterResponse")
public class SetNewsletterResponse
{
    @XmlElement(name = "SetNewsletterResult", nillable = true)
    protected RESULTMSG setNewsletterResult;
    
    public RESULTMSG getSetNewsletterResult() {
        return this.setNewsletterResult;
    }
    
    public void setSetNewsletterResult(final RESULTMSG value) {
        this.setNewsletterResult = value;
    }
}
