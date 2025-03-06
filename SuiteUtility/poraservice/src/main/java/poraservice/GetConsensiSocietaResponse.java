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
@XmlType(name = "", propOrder = { "getConsensiSocietaResult" })
@XmlRootElement(name = "GetConsensiSocietaResponse")
public class GetConsensiSocietaResponse
{
    @XmlElement(name = "GetConsensiSocietaResult", nillable = true)
    protected RESULTMSG getConsensiSocietaResult;
    
    public RESULTMSG getGetConsensiSocietaResult() {
        return this.getConsensiSocietaResult;
    }
    
    public void setGetConsensiSocietaResult(final RESULTMSG value) {
        this.getConsensiSocietaResult = value;
    }
}
