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
@XmlType(name = "", propOrder = { "getTipiServizioResult" })
@XmlRootElement(name = "GetTipiServizioResponse")
public class GetTipiServizioResponse
{
    @XmlElement(name = "GetTipiServizioResult", nillable = true)
    protected RESULTMSG getTipiServizioResult;
    
    public RESULTMSG getGetTipiServizioResult() {
        return this.getTipiServizioResult;
    }
    
    public void setGetTipiServizioResult(final RESULTMSG value) {
        this.getTipiServizioResult = value;
    }
}
