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
@XmlType(name = "", propOrder = { "getServiziLightResult" })
@XmlRootElement(name = "GetServiziLightResponse")
public class GetServiziLightResponse
{
    @XmlElement(name = "GetServiziLightResult", nillable = true)
    protected RESULTMSG getServiziLightResult;
    
    public RESULTMSG getGetServiziLightResult() {
        return this.getServiziLightResult;
    }
    
    public void setGetServiziLightResult(final RESULTMSG value) {
        this.getServiziLightResult = value;
    }
}
