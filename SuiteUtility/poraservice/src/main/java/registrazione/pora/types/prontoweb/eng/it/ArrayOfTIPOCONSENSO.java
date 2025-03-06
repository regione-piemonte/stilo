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
@XmlType(name = "ArrayOfTIPOCONSENSO", propOrder = { "tipoconsenso" })
public class ArrayOfTIPOCONSENSO
{
    @XmlElement(name = "TIPOCONSENSO", nillable = true)
    protected List<TIPOCONSENSO> tipoconsenso;
    
    public List<TIPOCONSENSO> getTIPOCONSENSO() {
        if (this.tipoconsenso == null) {
            this.tipoconsenso = new ArrayList<TIPOCONSENSO>();
        }
        return this.tipoconsenso;
    }
}
