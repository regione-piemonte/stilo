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
@XmlType(name = "ArrayOfLOGBESTOREDPROCEDURE", propOrder = { "logbestoredprocedure" })
public class ArrayOfLOGBESTOREDPROCEDURE
{
    @XmlElement(name = "LOGBESTOREDPROCEDURE", nillable = true)
    protected List<LOGBESTOREDPROCEDURE> logbestoredprocedure;
    
    public List<LOGBESTOREDPROCEDURE> getLOGBESTOREDPROCEDURE() {
        if (this.logbestoredprocedure == null) {
            this.logbestoredprocedure = new ArrayList<LOGBESTOREDPROCEDURE>();
        }
        return this.logbestoredprocedure;
    }
}
