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
@XmlType(name = "ArrayOfLOGBE", propOrder = { "logbe" })
public class ArrayOfLOGBE
{
    @XmlElement(name = "LOGBE", nillable = true)
    protected List<LOGBE> logbe;
    
    public List<LOGBE> getLOGBE() {
        if (this.logbe == null) {
            this.logbe = new ArrayList<LOGBE>();
        }
        return this.logbe;
    }
}
