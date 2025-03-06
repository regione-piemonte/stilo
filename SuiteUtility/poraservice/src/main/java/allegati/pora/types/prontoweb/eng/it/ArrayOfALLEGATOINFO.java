// 
// Decompiled by Procyon v0.5.36
// 

package allegati.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfALLEGATOINFO", propOrder = { "allegatoinfo" })
public class ArrayOfALLEGATOINFO
{
    @XmlElement(name = "ALLEGATOINFO", nillable = true)
    protected List<ALLEGATOINFO> allegatoinfo;
    
    public List<ALLEGATOINFO> getALLEGATOINFO() {
        if (this.allegatoinfo == null) {
            this.allegatoinfo = new ArrayList<ALLEGATOINFO>();
        }
        return this.allegatoinfo;
    }
}
