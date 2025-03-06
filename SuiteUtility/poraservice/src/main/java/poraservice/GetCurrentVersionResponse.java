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
@XmlType(name = "", propOrder = { "getCurrentVersionResult" })
@XmlRootElement(name = "GetCurrentVersionResponse")
public class GetCurrentVersionResponse
{
    @XmlElement(name = "GetCurrentVersionResult", nillable = true)
    protected RESULTMSG getCurrentVersionResult;
    
    public RESULTMSG getGetCurrentVersionResult() {
        return this.getCurrentVersionResult;
    }
    
    public void setGetCurrentVersionResult(final RESULTMSG value) {
        this.getCurrentVersionResult = value;
    }
}
