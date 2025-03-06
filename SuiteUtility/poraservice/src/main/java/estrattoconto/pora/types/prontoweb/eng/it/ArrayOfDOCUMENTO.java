// 
// Decompiled by Procyon v0.5.36
// 

package estrattoconto.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDOCUMENTO", propOrder = { "documento" })
public class ArrayOfDOCUMENTO
{
    @XmlElement(name = "DOCUMENTO", nillable = true)
    protected List<DOCUMENTO> documento;
    
    public List<DOCUMENTO> getDOCUMENTO() {
        if (this.documento == null) {
            this.documento = new ArrayList<DOCUMENTO>();
        }
        return this.documento;
    }
}
