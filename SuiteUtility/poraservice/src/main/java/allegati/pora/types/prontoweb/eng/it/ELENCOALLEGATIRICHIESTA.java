// 
// Decompiled by Procyon v0.5.36
// 

package allegati.pora.types.prontoweb.eng.it;

import richieste.pora.types.prontoweb.eng.it.RICHIESTAINFO;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ELENCOALLEGATIRICHIESTA", propOrder = { "allegati", "richiesta" })
public class ELENCOALLEGATIRICHIESTA
{
    @XmlElement(name = "ALLEGATI", nillable = true)
    protected ArrayOfALLEGATOINFO allegati;
    @XmlElement(name = "RICHIESTA", nillable = true)
    protected RICHIESTAINFO richiesta;
    
    public ArrayOfALLEGATOINFO getALLEGATI() {
        return this.allegati;
    }
    
    public void setALLEGATI(final ArrayOfALLEGATOINFO value) {
        this.allegati = value;
    }
    
    public RICHIESTAINFO getRICHIESTA() {
        return this.richiesta;
    }
    
    public void setRICHIESTA(final RICHIESTAINFO value) {
        this.richiesta = value;
    }
}
