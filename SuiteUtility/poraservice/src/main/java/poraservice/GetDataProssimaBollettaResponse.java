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
@XmlType(name = "", propOrder = { "getDataProssimaBollettaResult" })
@XmlRootElement(name = "GetDataProssimaBollettaResponse")
public class GetDataProssimaBollettaResponse
{
    @XmlElement(name = "GetDataProssimaBollettaResult", nillable = true)
    protected RESULTMSG getDataProssimaBollettaResult;
    
    public RESULTMSG getGetDataProssimaBollettaResult() {
        return this.getDataProssimaBollettaResult;
    }
    
    public void setGetDataProssimaBollettaResult(final RESULTMSG value) {
        this.getDataProssimaBollettaResult = value;
    }
}
