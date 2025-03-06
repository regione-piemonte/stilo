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
@XmlType(name = "ArrayOfSTATOFORNITURA", propOrder = { "statofornitura" })
public class ArrayOfSTATOFORNITURA
{
    @XmlElement(name = "STATOFORNITURA", nillable = true)
    protected List<STATOFORNITURA> statofornitura;
    
    public List<STATOFORNITURA> getSTATOFORNITURA() {
        if (this.statofornitura == null) {
            this.statofornitura = new ArrayList<STATOFORNITURA>();
        }
        return this.statofornitura;
    }
}
