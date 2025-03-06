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
@XmlType(name = "", propOrder = { "autenticaTOKENResult" })
@XmlRootElement(name = "AutenticaTOKENResponse")
public class AutenticaTOKENResponse
{
    @XmlElement(name = "AutenticaTOKENResult", nillable = true)
    protected RESULTMSG autenticaTOKENResult;
    
    public RESULTMSG getAutenticaTOKENResult() {
        return this.autenticaTOKENResult;
    }
    
    public void setAutenticaTOKENResult(final RESULTMSG value) {
        this.autenticaTOKENResult = value;
    }
}
