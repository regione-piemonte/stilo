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
@XmlType(name = "", propOrder = { "elencoAllegatiResult" })
@XmlRootElement(name = "ElencoAllegatiResponse")
public class ElencoAllegatiResponse
{
    @XmlElement(name = "ElencoAllegatiResult", nillable = true)
    protected RESULTMSG elencoAllegatiResult;
    
    public RESULTMSG getElencoAllegatiResult() {
        return this.elencoAllegatiResult;
    }
    
    public void setElencoAllegatiResult(final RESULTMSG value) {
        this.elencoAllegatiResult = value;
    }
}
