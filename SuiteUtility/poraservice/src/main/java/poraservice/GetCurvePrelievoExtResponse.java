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
@XmlType(name = "", propOrder = { "getCurvePrelievoExtResult" })
@XmlRootElement(name = "GetCurvePrelievoExtResponse")
public class GetCurvePrelievoExtResponse
{
    @XmlElement(name = "GetCurvePrelievoExtResult", nillable = true)
    protected RESULTMSG getCurvePrelievoExtResult;
    
    public RESULTMSG getGetCurvePrelievoExtResult() {
        return this.getCurvePrelievoExtResult;
    }
    
    public void setGetCurvePrelievoExtResult(final RESULTMSG value) {
        this.getCurvePrelievoExtResult = value;
    }
}
