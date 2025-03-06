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
@XmlType(name = "ArrayOfSOGGETTONEWS", propOrder = { "soggettonews" })
public class ArrayOfSOGGETTONEWS
{
    @XmlElement(name = "SOGGETTONEWS", nillable = true)
    protected List<SOGGETTONEWS> soggettonews;
    
    public List<SOGGETTONEWS> getSOGGETTONEWS() {
        if (this.soggettonews == null) {
            this.soggettonews = new ArrayList<SOGGETTONEWS>();
        }
        return this.soggettonews;
    }
}
