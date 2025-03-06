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
@XmlType(name = "", propOrder = { "elencoRichiesteExtResult" })
@XmlRootElement(name = "ElencoRichiesteExtResponse")
public class ElencoRichiesteExtResponse
{
    @XmlElement(name = "ElencoRichiesteExtResult", nillable = true)
    protected RESULTMSG elencoRichiesteExtResult;
    
    public RESULTMSG getElencoRichiesteExtResult() {
        return this.elencoRichiesteExtResult;
    }
    
    public void setElencoRichiesteExtResult(final RESULTMSG value) {
        this.elencoRichiesteExtResult = value;
    }
}
