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
@XmlType(name = "", propOrder = { "eliminaAccountResult" })
@XmlRootElement(name = "EliminaAccountResponse")
public class EliminaAccountResponse
{
    @XmlElement(name = "EliminaAccountResult", nillable = true)
    protected RESULTMSG eliminaAccountResult;
    
    public RESULTMSG getEliminaAccountResult() {
        return this.eliminaAccountResult;
    }
    
    public void setEliminaAccountResult(final RESULTMSG value) {
        this.eliminaAccountResult = value;
    }
}
