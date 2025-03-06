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
@XmlType(name = "ArrayOfCONSENSO", propOrder = { "consenso" })
public class ArrayOfCONSENSO
{
    @XmlElement(name = "CONSENSO", nillable = true)
    protected List<CONSENSO> consenso;
    
    public List<CONSENSO> getCONSENSO() {
        if (this.consenso == null) {
            this.consenso = new ArrayList<CONSENSO>();
        }
        return this.consenso;
    }
}
