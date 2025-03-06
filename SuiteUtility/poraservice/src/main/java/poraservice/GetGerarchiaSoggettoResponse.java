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
@XmlType(name = "", propOrder = { "getGerarchiaSoggettoResult" })
@XmlRootElement(name = "GetGerarchiaSoggettoResponse")
public class GetGerarchiaSoggettoResponse
{
    @XmlElement(name = "GetGerarchiaSoggettoResult", nillable = true)
    protected RESULTMSG getGerarchiaSoggettoResult;
    
    public RESULTMSG getGetGerarchiaSoggettoResult() {
        return this.getGerarchiaSoggettoResult;
    }
    
    public void setGetGerarchiaSoggettoResult(final RESULTMSG value) {
        this.getGerarchiaSoggettoResult = value;
    }
}
