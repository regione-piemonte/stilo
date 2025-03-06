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
@XmlType(name = "", propOrder = { "getProdottiResult" })
@XmlRootElement(name = "GetProdottiResponse")
public class GetProdottiResponse
{
    @XmlElement(name = "GetProdottiResult", nillable = true)
    protected RESULTMSG getProdottiResult;
    
    public RESULTMSG getGetProdottiResult() {
        return this.getProdottiResult;
    }
    
    public void setGetProdottiResult(final RESULTMSG value) {
        this.getProdottiResult = value;
    }
}
