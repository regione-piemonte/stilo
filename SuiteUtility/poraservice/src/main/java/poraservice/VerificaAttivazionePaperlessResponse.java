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
@XmlType(name = "", propOrder = { "verificaAttivazionePaperlessResult" })
@XmlRootElement(name = "VerificaAttivazionePaperlessResponse")
public class VerificaAttivazionePaperlessResponse
{
    @XmlElement(name = "VerificaAttivazionePaperlessResult", nillable = true)
    protected RESULTMSG verificaAttivazionePaperlessResult;
    
    public RESULTMSG getVerificaAttivazionePaperlessResult() {
        return this.verificaAttivazionePaperlessResult;
    }
    
    public void setVerificaAttivazionePaperlessResult(final RESULTMSG value) {
        this.verificaAttivazionePaperlessResult = value;
    }
}
