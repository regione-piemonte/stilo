// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfIndUbiType", propOrder = { "indUbiType" })
public class ArrayOfIndUbiType
{
    @XmlElement(name = "IndUbiType", nillable = true)
    protected List<IndUbiType> indUbiType;
    
    public List<IndUbiType> getIndUbiType() {
        if (this.indUbiType == null) {
            this.indUbiType = new ArrayList<IndUbiType>();
        }
        return this.indUbiType;
    }
}
