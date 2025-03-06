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
@XmlType(name = "", propOrder = { "getComuniLocalitaResult" })
@XmlRootElement(name = "GetComuniLocalitaResponse")
public class GetComuniLocalitaResponse
{
    @XmlElement(name = "GetComuniLocalitaResult", nillable = true)
    protected RESULTMSG getComuniLocalitaResult;
    
    public RESULTMSG getGetComuniLocalitaResult() {
        return this.getComuniLocalitaResult;
    }
    
    public void setGetComuniLocalitaResult(final RESULTMSG value) {
        this.getComuniLocalitaResult = value;
    }
}
