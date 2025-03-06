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
@XmlType(name = "", propOrder = { "setAutoletturaFlussoResult" })
@XmlRootElement(name = "SetAutoletturaFlussoResponse")
public class SetAutoletturaFlussoResponse
{
    @XmlElement(name = "SetAutoletturaFlussoResult", nillable = true)
    protected RESULTMSG setAutoletturaFlussoResult;
    
    public RESULTMSG getSetAutoletturaFlussoResult() {
        return this.setAutoletturaFlussoResult;
    }
    
    public void setSetAutoletturaFlussoResult(final RESULTMSG value) {
        this.setAutoletturaFlussoResult = value;
    }
}
