// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSTATOCONSENSO", propOrder = { "statoconsenso" })
public class ArrayOfSTATOCONSENSO
{
    @XmlElement(name = "STATOCONSENSO", nillable = true)
    protected List<STATOCONSENSO> statoconsenso;
    
    public List<STATOCONSENSO> getSTATOCONSENSO() {
        if (this.statoconsenso == null) {
            this.statoconsenso = new ArrayList<STATOCONSENSO>();
        }
        return this.statoconsenso;
    }
}
