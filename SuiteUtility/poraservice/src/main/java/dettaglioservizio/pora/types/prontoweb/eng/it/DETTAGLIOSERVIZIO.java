// 
// Decompiled by Procyon v0.5.36
// 

package dettaglioservizio.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import pora.types.prontoweb.eng.it.FORNITURAEXT;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DETTAGLIOSERVIZIO", propOrder = { "elementifatt" })
public class DETTAGLIOSERVIZIO extends FORNITURAEXT
{
    @XmlElement(name = "ELEMENTIFATT", nillable = true)
    protected ArrayOfELEMENTIDIFATTURAZIONE elementifatt;
    
    public ArrayOfELEMENTIDIFATTURAZIONE getELEMENTIFATT() {
        return this.elementifatt;
    }
    
    public void setELEMENTIFATT(final ArrayOfELEMENTIDIFATTURAZIONE value) {
        this.elementifatt = value;
    }
}
