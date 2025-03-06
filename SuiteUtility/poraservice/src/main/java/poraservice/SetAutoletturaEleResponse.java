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
@XmlType(name = "", propOrder = { "setAutoletturaEleResult" })
@XmlRootElement(name = "SetAutoletturaEleResponse")
public class SetAutoletturaEleResponse
{
    @XmlElement(name = "SetAutoletturaEleResult", nillable = true)
    protected RESULTMSG setAutoletturaEleResult;
    
    public RESULTMSG getSetAutoletturaEleResult() {
        return this.setAutoletturaEleResult;
    }
    
    public void setSetAutoletturaEleResult(final RESULTMSG value) {
        this.setAutoletturaEleResult = value;
    }
}
