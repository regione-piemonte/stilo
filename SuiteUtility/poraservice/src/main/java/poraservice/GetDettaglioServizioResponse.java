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
@XmlType(name = "", propOrder = { "getDettaglioServizioResult" })
@XmlRootElement(name = "GetDettaglioServizioResponse")
public class GetDettaglioServizioResponse
{
    @XmlElement(name = "GetDettaglioServizioResult", nillable = true)
    protected RESULTMSG getDettaglioServizioResult;
    
    public RESULTMSG getGetDettaglioServizioResult() {
        return this.getDettaglioServizioResult;
    }
    
    public void setGetDettaglioServizioResult(final RESULTMSG value) {
        this.getDettaglioServizioResult = value;
    }
}
