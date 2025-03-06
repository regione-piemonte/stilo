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
@XmlType(name = "", propOrder = { "inserimentoSoggettoProspectResult" })
@XmlRootElement(name = "InserimentoSoggettoProspectResponse")
public class InserimentoSoggettoProspectResponse
{
    @XmlElement(name = "InserimentoSoggettoProspectResult", nillable = true)
    protected RESULTMSG inserimentoSoggettoProspectResult;
    
    public RESULTMSG getInserimentoSoggettoProspectResult() {
        return this.inserimentoSoggettoProspectResult;
    }
    
    public void setInserimentoSoggettoProspectResult(final RESULTMSG value) {
        this.inserimentoSoggettoProspectResult = value;
    }
}
