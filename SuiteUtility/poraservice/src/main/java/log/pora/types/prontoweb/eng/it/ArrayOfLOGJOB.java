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
@XmlType(name = "ArrayOfLOGJOB", propOrder = { "logjob" })
public class ArrayOfLOGJOB
{
    @XmlElement(name = "LOGJOB", nillable = true)
    protected List<LOGJOB> logjob;
    
    public List<LOGJOB> getLOGJOB() {
        if (this.logjob == null) {
            this.logjob = new ArrayList<LOGJOB>();
        }
        return this.logjob;
    }
}
