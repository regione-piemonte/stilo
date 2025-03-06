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
@XmlType(name = "ArrayOfFORNITURAEXT", propOrder = { "fornituraext" })
public class ArrayOfFORNITURAEXT
{
    @XmlElement(name = "FORNITURAEXT", nillable = true)
    protected List<FORNITURAEXT> fornituraext;
    
    public List<FORNITURAEXT> getFORNITURAEXT() {
        if (this.fornituraext == null) {
            this.fornituraext = new ArrayList<FORNITURAEXT>();
        }
        return this.fornituraext;
    }
}
