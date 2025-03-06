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
@XmlType(name = "", propOrder = { "normalizzaIndirizzoUbiGeoCallResult" })
@XmlRootElement(name = "NormalizzaIndirizzoUbiGeoCallResponse")
public class NormalizzaIndirizzoUbiGeoCallResponse
{
    @XmlElement(name = "NormalizzaIndirizzoUbiGeoCallResult", nillable = true)
    protected RESULTMSG normalizzaIndirizzoUbiGeoCallResult;
    
    public RESULTMSG getNormalizzaIndirizzoUbiGeoCallResult() {
        return this.normalizzaIndirizzoUbiGeoCallResult;
    }
    
    public void setNormalizzaIndirizzoUbiGeoCallResult(final RESULTMSG value) {
        this.normalizzaIndirizzoUbiGeoCallResult = value;
    }
}
