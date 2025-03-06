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
@XmlType(name = "", propOrder = { "getComuniCatastoResult" })
@XmlRootElement(name = "GetComuniCatastoResponse")
public class GetComuniCatastoResponse
{
    @XmlElement(name = "GetComuniCatastoResult", nillable = true)
    protected RESULTMSG getComuniCatastoResult;
    
    public RESULTMSG getGetComuniCatastoResult() {
        return this.getComuniCatastoResult;
    }
    
    public void setGetComuniCatastoResult(final RESULTMSG value) {
        this.getComuniCatastoResult = value;
    }
}
