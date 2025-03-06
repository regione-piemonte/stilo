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
@XmlType(name = "", propOrder = { "gestioneSoggettoProspectResult" })
@XmlRootElement(name = "GestioneSoggettoProspectResponse")
public class GestioneSoggettoProspectResponse
{
    @XmlElement(name = "GestioneSoggettoProspectResult", nillable = true)
    protected RESULTMSG gestioneSoggettoProspectResult;
    
    public RESULTMSG getGestioneSoggettoProspectResult() {
        return this.gestioneSoggettoProspectResult;
    }
    
    public void setGestioneSoggettoProspectResult(final RESULTMSG value) {
        this.gestioneSoggettoProspectResult = value;
    }
}
