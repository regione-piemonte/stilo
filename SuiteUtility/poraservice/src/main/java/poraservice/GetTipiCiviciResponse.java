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
@XmlType(name = "", propOrder = { "getTipiCiviciResult" })
@XmlRootElement(name = "GetTipiCiviciResponse")
public class GetTipiCiviciResponse
{
    @XmlElement(name = "GetTipiCiviciResult", nillable = true)
    protected RESULTMSG getTipiCiviciResult;
    
    public RESULTMSG getGetTipiCiviciResult() {
        return this.getTipiCiviciResult;
    }
    
    public void setGetTipiCiviciResult(final RESULTMSG value) {
        this.getTipiCiviciResult = value;
    }
}
