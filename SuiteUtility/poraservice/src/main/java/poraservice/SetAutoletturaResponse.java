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
@XmlType(name = "", propOrder = { "setAutoletturaResult" })
@XmlRootElement(name = "SetAutoletturaResponse")
public class SetAutoletturaResponse
{
    @XmlElement(name = "SetAutoletturaResult", nillable = true)
    protected RESULTMSG setAutoletturaResult;
    
    public RESULTMSG getSetAutoletturaResult() {
        return this.setAutoletturaResult;
    }
    
    public void setSetAutoletturaResult(final RESULTMSG value) {
        this.setAutoletturaResult = value;
    }
}
