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
@XmlType(name = "", propOrder = { "recuperaPasswordResult" })
@XmlRootElement(name = "RecuperaPasswordResponse")
public class RecuperaPasswordResponse
{
    @XmlElement(name = "RecuperaPasswordResult", nillable = true)
    protected RESULTMSG recuperaPasswordResult;
    
    public RESULTMSG getRecuperaPasswordResult() {
        return this.recuperaPasswordResult;
    }
    
    public void setRecuperaPasswordResult(final RESULTMSG value) {
        this.recuperaPasswordResult = value;
    }
}
