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
@XmlType(name = "", propOrder = { "getNazioniResult" })
@XmlRootElement(name = "GetNazioniResponse")
public class GetNazioniResponse
{
    @XmlElement(name = "GetNazioniResult", nillable = true)
    protected RESULTMSG getNazioniResult;
    
    public RESULTMSG getGetNazioniResult() {
        return this.getNazioniResult;
    }
    
    public void setGetNazioniResult(final RESULTMSG value) {
        this.getNazioniResult = value;
    }
}
