// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSOTTOTIPIRESULT", propOrder = { "sottotipiresult" })
public class ArrayOfSOTTOTIPIRESULT
{
    @XmlElement(name = "SOTTOTIPIRESULT", nillable = true)
    protected List<SOTTOTIPIRESULT> sottotipiresult;
    
    public List<SOTTOTIPIRESULT> getSOTTOTIPIRESULT() {
        if (this.sottotipiresult == null) {
            this.sottotipiresult = new ArrayList<SOTTOTIPIRESULT>();
        }
        return this.sottotipiresult;
    }
}
