// 
// Decompiled by Procyon v0.5.36
// 

package autolettura.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfLETTURA", propOrder = { "lettura" })
public class ArrayOfLETTURA
{
    @XmlElement(name = "LETTURA", nillable = true)
    protected List<LETTURA> lettura;
    
    public List<LETTURA> getLETTURA() {
        if (this.lettura == null) {
            this.lettura = new ArrayList<LETTURA>();
        }
        return this.lettura;
    }
}
