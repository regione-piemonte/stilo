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
@XmlType(name = "ArrayOfRUOLO", propOrder = { "ruolo" })
public class ArrayOfRUOLO
{
    @XmlElement(name = "RUOLO", nillable = true)
    protected List<RUOLO> ruolo;
    
    public List<RUOLO> getRUOLO() {
        if (this.ruolo == null) {
            this.ruolo = new ArrayList<RUOLO>();
        }
        return this.ruolo;
    }
}
