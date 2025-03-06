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
@XmlType(name = "", propOrder = { "getPlayerResult" })
@XmlRootElement(name = "GetPlayerResponse")
public class GetPlayerResponse
{
    @XmlElement(name = "GetPlayerResult", nillable = true)
    protected RESULTMSG getPlayerResult;
    
    public RESULTMSG getGetPlayerResult() {
        return this.getPlayerResult;
    }
    
    public void setGetPlayerResult(final RESULTMSG value) {
        this.getPlayerResult = value;
    }
}
