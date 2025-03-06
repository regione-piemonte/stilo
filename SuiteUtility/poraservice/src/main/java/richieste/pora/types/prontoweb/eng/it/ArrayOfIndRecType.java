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
@XmlType(name = "ArrayOfIndRecType", propOrder = { "indRecType" })
public class ArrayOfIndRecType
{
    @XmlElement(name = "IndRecType", nillable = true)
    protected List<IndRecType> indRecType;
    
    public List<IndRecType> getIndRecType() {
        if (this.indRecType == null) {
            this.indRecType = new ArrayList<IndRecType>();
        }
        return this.indRecType;
    }
}
