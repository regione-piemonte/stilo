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
@XmlType(name = "", propOrder = { "disattivaPaperlessResult" })
@XmlRootElement(name = "DisattivaPaperlessResponse")
public class DisattivaPaperlessResponse
{
    @XmlElement(name = "DisattivaPaperlessResult", nillable = true)
    protected RESULTMSG disattivaPaperlessResult;
    
    public RESULTMSG getDisattivaPaperlessResult() {
        return this.disattivaPaperlessResult;
    }
    
    public void setDisattivaPaperlessResult(final RESULTMSG value) {
        this.disattivaPaperlessResult = value;
    }
}
