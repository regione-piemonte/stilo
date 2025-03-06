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
@XmlType(name = "", propOrder = { "getCurvePrelievoResult" })
@XmlRootElement(name = "GetCurvePrelievoResponse")
public class GetCurvePrelievoResponse
{
    @XmlElement(name = "GetCurvePrelievoResult", nillable = true)
    protected RESULTMSG getCurvePrelievoResult;
    
    public RESULTMSG getGetCurvePrelievoResult() {
        return this.getCurvePrelievoResult;
    }
    
    public void setGetCurvePrelievoResult(final RESULTMSG value) {
        this.getCurvePrelievoResult = value;
    }
}
