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
@XmlType(name = "", propOrder = { "recuperaCredenzialiAccountResult" })
@XmlRootElement(name = "RecuperaCredenzialiAccountResponse")
public class RecuperaCredenzialiAccountResponse
{
    @XmlElement(name = "RecuperaCredenzialiAccountResult", nillable = true)
    protected RESULTMSG recuperaCredenzialiAccountResult;
    
    public RESULTMSG getRecuperaCredenzialiAccountResult() {
        return this.recuperaCredenzialiAccountResult;
    }
    
    public void setRecuperaCredenzialiAccountResult(final RESULTMSG value) {
        this.recuperaCredenzialiAccountResult = value;
    }
}
