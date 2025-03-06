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
@XmlType(name = "", propOrder = { "getConsumoStimatoProssimaBollettaResult" })
@XmlRootElement(name = "GetConsumoStimatoProssimaBollettaResponse")
public class GetConsumoStimatoProssimaBollettaResponse
{
    @XmlElement(name = "GetConsumoStimatoProssimaBollettaResult", nillable = true)
    protected RESULTMSG getConsumoStimatoProssimaBollettaResult;
    
    public RESULTMSG getGetConsumoStimatoProssimaBollettaResult() {
        return this.getConsumoStimatoProssimaBollettaResult;
    }
    
    public void setGetConsumoStimatoProssimaBollettaResult(final RESULTMSG value) {
        this.getConsumoStimatoProssimaBollettaResult = value;
    }
}
