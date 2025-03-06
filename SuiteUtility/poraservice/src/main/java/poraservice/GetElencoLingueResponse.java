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
@XmlType(name = "", propOrder = { "getElencoLingueResult" })
@XmlRootElement(name = "GetElencoLingueResponse")
public class GetElencoLingueResponse
{
    @XmlElement(name = "GetElencoLingueResult", nillable = true)
    protected RESULTMSG getElencoLingueResult;
    
    public RESULTMSG getGetElencoLingueResult() {
        return this.getElencoLingueResult;
    }
    
    public void setGetElencoLingueResult(final RESULTMSG value) {
        this.getElencoLingueResult = value;
    }
}
