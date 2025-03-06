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
@XmlType(name = "", propOrder = { "inserimentoRichiestaDDResult" })
@XmlRootElement(name = "InserimentoRichiestaDDResponse")
public class InserimentoRichiestaDDResponse
{
    @XmlElement(name = "InserimentoRichiestaDDResult", nillable = true)
    protected RESULTMSG inserimentoRichiestaDDResult;
    
    public RESULTMSG getInserimentoRichiestaDDResult() {
        return this.inserimentoRichiestaDDResult;
    }
    
    public void setInserimentoRichiestaDDResult(final RESULTMSG value) {
        this.inserimentoRichiestaDDResult = value;
    }
}
