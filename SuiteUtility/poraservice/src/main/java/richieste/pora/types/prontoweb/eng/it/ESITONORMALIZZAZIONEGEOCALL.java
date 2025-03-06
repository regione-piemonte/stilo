// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ESITONORMALIZZAZIONEGEOCALL", propOrder = { "indirizzirecapito", "indirizziubicazione" })
public class ESITONORMALIZZAZIONEGEOCALL
{
    @XmlElement(name = "INDIRIZZIRECAPITO", nillable = true)
    protected ArrayOfIndRecType indirizzirecapito;
    @XmlElement(name = "INDIRIZZIUBICAZIONE", nillable = true)
    protected ArrayOfIndUbiType indirizziubicazione;
    
    public ArrayOfIndRecType getINDIRIZZIRECAPITO() {
        return this.indirizzirecapito;
    }
    
    public void setINDIRIZZIRECAPITO(final ArrayOfIndRecType value) {
        this.indirizzirecapito = value;
    }
    
    public ArrayOfIndUbiType getINDIRIZZIUBICAZIONE() {
        return this.indirizziubicazione;
    }
    
    public void setINDIRIZZIUBICAZIONE(final ArrayOfIndUbiType value) {
        this.indirizziubicazione = value;
    }
}
