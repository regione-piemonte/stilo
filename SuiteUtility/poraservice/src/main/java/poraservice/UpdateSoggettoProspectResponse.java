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
@XmlType(name = "", propOrder = { "updateSoggettoProspectResult" })
@XmlRootElement(name = "UpdateSoggettoProspectResponse")
public class UpdateSoggettoProspectResponse
{
    @XmlElement(name = "UpdateSoggettoProspectResult", nillable = true)
    protected RESULTMSG updateSoggettoProspectResult;
    
    public RESULTMSG getUpdateSoggettoProspectResult() {
        return this.updateSoggettoProspectResult;
    }
    
    public void setUpdateSoggettoProspectResult(final RESULTMSG value) {
        this.updateSoggettoProspectResult = value;
    }
}
