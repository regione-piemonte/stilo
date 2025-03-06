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
@XmlType(name = "", propOrder = { "elencoRichiesteResult" })
@XmlRootElement(name = "ElencoRichiesteResponse")
public class ElencoRichiesteResponse
{
    @XmlElement(name = "ElencoRichiesteResult", nillable = true)
    protected RESULTMSG elencoRichiesteResult;
    
    public RESULTMSG getElencoRichiesteResult() {
        return this.elencoRichiesteResult;
    }
    
    public void setElencoRichiesteResult(final RESULTMSG value) {
        this.elencoRichiesteResult = value;
    }
}
