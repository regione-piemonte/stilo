// 
// Decompiled by Procyon v0.5.36
// 

package alert.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import pora.types.prontoweb.eng.it.PWEBTABLE;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TABLECONSUMIPROSSIMEBOLL", propOrder = { "elencodocumenti" })
public class TABLECONSUMIPROSSIMEBOLL extends PWEBTABLE
{
    @XmlElement(name = "ELENCODOCUMENTI", required = true, nillable = true)
    protected ArrayOfCONSUMOROSSIMABOLL elencodocumenti;
    
    public ArrayOfCONSUMOROSSIMABOLL getELENCODOCUMENTI() {
        return this.elencodocumenti;
    }
    
    public void setELENCODOCUMENTI(final ArrayOfCONSUMOROSSIMABOLL value) {
        this.elencodocumenti = value;
    }
}
