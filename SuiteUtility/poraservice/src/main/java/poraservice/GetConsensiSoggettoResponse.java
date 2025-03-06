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
@XmlType(name = "", propOrder = { "getConsensiSoggettoResult" })
@XmlRootElement(name = "GetConsensiSoggettoResponse")
public class GetConsensiSoggettoResponse
{
    @XmlElement(name = "GetConsensiSoggettoResult", nillable = true)
    protected RESULTMSG getConsensiSoggettoResult;
    
    public RESULTMSG getGetConsensiSoggettoResult() {
        return this.getConsensiSoggettoResult;
    }
    
    public void setGetConsensiSoggettoResult(final RESULTMSG value) {
        this.getConsensiSoggettoResult = value;
    }
}
