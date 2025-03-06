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
@XmlType(name = "ArrayOfATTRDYNVAL", propOrder = { "attrdynval" })
public class ArrayOfATTRDYNVAL
{
    @XmlElement(name = "ATTRDYNVAL", nillable = true)
    protected List<ATTRDYNVAL> attrdynval;
    
    public List<ATTRDYNVAL> getATTRDYNVAL() {
        if (this.attrdynval == null) {
            this.attrdynval = new ArrayList<ATTRDYNVAL>();
        }
        return this.attrdynval;
    }
}
