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
@XmlType(name = "TABLEDATEPROSSIMEBOLL", propOrder = { "dateprossimeboll" })
public class TABLEDATEPROSSIMEBOLL extends PWEBTABLE
{
    @XmlElement(name = "DATEPROSSIMEBOLL", required = true, nillable = true)
    protected ArrayOfDATAPROSSIMABOLL dateprossimeboll;
    
    public ArrayOfDATAPROSSIMABOLL getDATEPROSSIMEBOLL() {
        return this.dateprossimeboll;
    }
    
    public void setDATEPROSSIMEBOLL(final ArrayOfDATAPROSSIMABOLL value) {
        this.dateprossimeboll = value;
    }
}
