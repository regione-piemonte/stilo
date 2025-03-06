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
@XmlType(name = "ArrayOfCONSENSOSOG", propOrder = { "consensosog" })
public class ArrayOfCONSENSOSOG
{
    @XmlElement(name = "CONSENSOSOG", nillable = true)
    protected List<CONSENSOSOG> consensosog;
    
    public List<CONSENSOSOG> getCONSENSOSOG() {
        if (this.consensosog == null) {
            this.consensosog = new ArrayList<CONSENSOSOG>();
        }
        return this.consensosog;
    }
}
