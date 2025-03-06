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
@XmlType(name = "", propOrder = { "sbloccaAccountResult" })
@XmlRootElement(name = "SbloccaAccountResponse")
public class SbloccaAccountResponse
{
    @XmlElement(name = "SbloccaAccountResult", nillable = true)
    protected RESULTMSG sbloccaAccountResult;
    
    public RESULTMSG getSbloccaAccountResult() {
        return this.sbloccaAccountResult;
    }
    
    public void setSbloccaAccountResult(final RESULTMSG value) {
        this.sbloccaAccountResult = value;
    }
}
