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
@XmlType(name = "ALLEGATORICHIESTA", propOrder = { "allegato", "richiesta" })
public class ALLEGATORICHIESTA
{
    @XmlElement(name = "ALLEGATO", nillable = true)
    protected ALLEGATOINFO allegato;
    @XmlElement(name = "RICHIESTA", nillable = true)
    protected RICHIESTAINFO richiesta;
    
    public ALLEGATOINFO getALLEGATO() {
        return this.allegato;
    }
    
    public void setALLEGATO(final ALLEGATOINFO value) {
        this.allegato = value;
    }
    
    public RICHIESTAINFO getRICHIESTA() {
        return this.richiesta;
    }
    
    public void setRICHIESTA(final RICHIESTAINFO value) {
        this.richiesta = value;
    }
}
