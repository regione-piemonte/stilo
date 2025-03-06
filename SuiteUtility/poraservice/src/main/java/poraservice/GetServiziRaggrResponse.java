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
@XmlType(name = "", propOrder = { "getServiziRaggrResult" })
@XmlRootElement(name = "GetServiziRaggrResponse")
public class GetServiziRaggrResponse
{
    @XmlElement(name = "GetServiziRaggrResult", nillable = true)
    protected RESULTMSG getServiziRaggrResult;
    
    public RESULTMSG getGetServiziRaggrResult() {
        return this.getServiziRaggrResult;
    }
    
    public void setGetServiziRaggrResult(final RESULTMSG value) {
        this.getServiziRaggrResult = value;
    }
}
