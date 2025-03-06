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
@XmlType(name = "", propOrder = { "getServiziResult" })
@XmlRootElement(name = "GetServiziResponse")
public class GetServiziResponse
{
    @XmlElement(name = "GetServiziResult", nillable = true)
    protected RESULTMSG getServiziResult;
    
    public RESULTMSG getGetServiziResult() {
        return this.getServiziResult;
    }
    
    public void setGetServiziResult(final RESULTMSG value) {
        this.getServiziResult = value;
    }
}
