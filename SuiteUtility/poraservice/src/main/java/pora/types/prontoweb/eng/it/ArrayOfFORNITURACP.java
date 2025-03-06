// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfFORNITURACP", propOrder = { "fornituracp" })
public class ArrayOfFORNITURACP
{
    @XmlElement(name = "FORNITURACP", nillable = true)
    protected List<FORNITURACP> fornituracp;
    
    public List<FORNITURACP> getFORNITURACP() {
        if (this.fornituracp == null) {
            this.fornituracp = new ArrayList<FORNITURACP>();
        }
        return this.fornituracp;
    }
}
