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
@XmlType(name = "ArrayOfFORNITURA", propOrder = { "fornitura" })
public class ArrayOfFORNITURA
{
    @XmlElement(name = "FORNITURA", nillable = true)
    protected List<FORNITURA> fornitura;
    
    public List<FORNITURA> getFORNITURA() {
        if (this.fornitura == null) {
            this.fornitura = new ArrayList<FORNITURA>();
        }
        return this.fornitura;
    }
}
