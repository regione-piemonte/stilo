// 
// Decompiled by Procyon v0.5.36
// 

package dettaglioservizio.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfELEMENTIDIFATTURAZIONE", propOrder = { "elementidifatturazione" })
public class ArrayOfELEMENTIDIFATTURAZIONE
{
    @XmlElement(name = "ELEMENTIDIFATTURAZIONE", nillable = true)
    protected List<ELEMENTIDIFATTURAZIONE> elementidifatturazione;
    
    public List<ELEMENTIDIFATTURAZIONE> getELEMENTIDIFATTURAZIONE() {
        if (this.elementidifatturazione == null) {
            this.elementidifatturazione = new ArrayList<ELEMENTIDIFATTURAZIONE>();
        }
        return this.elementidifatturazione;
    }
}
