// 
// Decompiled by Procyon v0.5.36
// 

package log.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfLOGBEPARAMETER", propOrder = { "logbeparameter" })
public class ArrayOfLOGBEPARAMETER
{
    @XmlElement(name = "LOGBEPARAMETER", nillable = true)
    protected List<LOGBEPARAMETER> logbeparameter;
    
    public List<LOGBEPARAMETER> getLOGBEPARAMETER() {
        if (this.logbeparameter == null) {
            this.logbeparameter = new ArrayList<LOGBEPARAMETER>();
        }
        return this.logbeparameter;
    }
}
