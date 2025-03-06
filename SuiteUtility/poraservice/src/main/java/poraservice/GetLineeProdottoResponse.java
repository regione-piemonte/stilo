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
@XmlType(name = "", propOrder = { "getLineeProdottoResult" })
@XmlRootElement(name = "GetLineeProdottoResponse")
public class GetLineeProdottoResponse
{
    @XmlElement(name = "GetLineeProdottoResult", nillable = true)
    protected RESULTMSG getLineeProdottoResult;
    
    public RESULTMSG getGetLineeProdottoResult() {
        return this.getLineeProdottoResult;
    }
    
    public void setGetLineeProdottoResult(final RESULTMSG value) {
        this.getLineeProdottoResult = value;
    }
}
