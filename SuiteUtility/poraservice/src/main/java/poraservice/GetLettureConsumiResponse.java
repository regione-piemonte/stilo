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
@XmlType(name = "", propOrder = { "getLettureConsumiResult" })
@XmlRootElement(name = "GetLettureConsumiResponse")
public class GetLettureConsumiResponse
{
    @XmlElement(name = "GetLettureConsumiResult", nillable = true)
    protected RESULTMSG getLettureConsumiResult;
    
    public RESULTMSG getGetLettureConsumiResult() {
        return this.getLettureConsumiResult;
    }
    
    public void setGetLettureConsumiResult(final RESULTMSG value) {
        this.getLettureConsumiResult = value;
    }
}
