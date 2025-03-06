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
@XmlType(name = "ArrayOfSOGGETTONEWSEXT", propOrder = { "soggettonewsext" })
public class ArrayOfSOGGETTONEWSEXT
{
    @XmlElement(name = "SOGGETTONEWSEXT", nillable = true)
    protected List<SOGGETTONEWSEXT> soggettonewsext;
    
    public List<SOGGETTONEWSEXT> getSOGGETTONEWSEXT() {
        if (this.soggettonewsext == null) {
            this.soggettonewsext = new ArrayList<SOGGETTONEWSEXT>();
        }
        return this.soggettonewsext;
    }
}
