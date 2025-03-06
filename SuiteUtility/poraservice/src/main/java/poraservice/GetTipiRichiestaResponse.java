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
@XmlType(name = "", propOrder = { "getTipiRichiestaResult" })
@XmlRootElement(name = "GetTipiRichiestaResponse")
public class GetTipiRichiestaResponse
{
    @XmlElement(name = "GetTipiRichiestaResult", nillable = true)
    protected RESULTMSG getTipiRichiestaResult;
    
    public RESULTMSG getGetTipiRichiestaResult() {
        return this.getTipiRichiestaResult;
    }
    
    public void setGetTipiRichiestaResult(final RESULTMSG value) {
        this.getTipiRichiestaResult = value;
    }
}
