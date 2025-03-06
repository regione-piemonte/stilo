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
@XmlType(name = "ArrayOfLETTURAEXT", propOrder = { "letturaext" })
public class ArrayOfLETTURAEXT
{
    @XmlElement(name = "LETTURAEXT", nillable = true)
    protected List<LETTURAEXT> letturaext;
    
    public List<LETTURAEXT> getLETTURAEXT() {
        if (this.letturaext == null) {
            this.letturaext = new ArrayList<LETTURAEXT>();
        }
        return this.letturaext;
    }
}
