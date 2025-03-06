// 
// Decompiled by Procyon v0.5.36
// 

package profilocliente.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfATTRDYN", propOrder = { "attrdyn" })
public class ArrayOfATTRDYN
{
    @XmlElement(name = "ATTRDYN", nillable = true)
    protected List<ATTRDYN> attrdyn;
    
    public List<ATTRDYN> getATTRDYN() {
        if (this.attrdyn == null) {
            this.attrdyn = new ArrayList<ATTRDYN>();
        }
        return this.attrdyn;
    }
}
