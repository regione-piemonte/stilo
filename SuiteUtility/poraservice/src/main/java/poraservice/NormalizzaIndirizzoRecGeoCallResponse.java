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
@XmlType(name = "", propOrder = { "normalizzaIndirizzoRecGeoCallResult" })
@XmlRootElement(name = "NormalizzaIndirizzoRecGeoCallResponse")
public class NormalizzaIndirizzoRecGeoCallResponse
{
    @XmlElement(name = "NormalizzaIndirizzoRecGeoCallResult", nillable = true)
    protected RESULTMSG normalizzaIndirizzoRecGeoCallResult;
    
    public RESULTMSG getNormalizzaIndirizzoRecGeoCallResult() {
        return this.normalizzaIndirizzoRecGeoCallResult;
    }
    
    public void setNormalizzaIndirizzoRecGeoCallResult(final RESULTMSG value) {
        this.normalizzaIndirizzoRecGeoCallResult = value;
    }
}
