// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import pora.types.prontoweb.eng.it.ArrayOfRESULTMSG;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import pora.types.prontoweb.eng.it.PWEBTABLE;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ELENCORICHIESTE", propOrder = { "richieste" })
public class ELENCORICHIESTE extends PWEBTABLE
{
    @XmlElement(name = "RICHIESTE", nillable = true)
    protected ArrayOfRESULTMSG richieste;
    
    public ArrayOfRESULTMSG getRICHIESTE() {
        return this.richieste;
    }
    
    public void setRICHIESTE(final ArrayOfRESULTMSG value) {
        this.richieste = value;
    }
}
