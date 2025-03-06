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
@XmlType(name = "ArrayOfDATISOGGETTO", propOrder = { "datisoggetto" })
public class ArrayOfDATISOGGETTO
{
    @XmlElement(name = "DATISOGGETTO", nillable = true)
    protected List<DATISOGGETTO> datisoggetto;
    
    public List<DATISOGGETTO> getDATISOGGETTO() {
        if (this.datisoggetto == null) {
            this.datisoggetto = new ArrayList<DATISOGGETTO>();
        }
        return this.datisoggetto;
    }
}
