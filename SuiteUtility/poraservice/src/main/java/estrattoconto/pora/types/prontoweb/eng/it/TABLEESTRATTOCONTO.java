// 
// Decompiled by Procyon v0.5.36
// 

package estrattoconto.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import pora.types.prontoweb.eng.it.PWEBTABLE;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TABLEESTRATTOCONTO", propOrder = { "elencodocumenti" })
public class TABLEESTRATTOCONTO extends PWEBTABLE
{
    @XmlElement(name = "ELENCODOCUMENTI", required = true, nillable = true)
    protected ArrayOfDOCUMENTO elencodocumenti;
    
    public ArrayOfDOCUMENTO getELENCODOCUMENTI() {
        return this.elencodocumenti;
    }
    
    public void setELENCODOCUMENTI(final ArrayOfDOCUMENTO value) {
        this.elencodocumenti = value;
    }
}
