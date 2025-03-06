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
@XmlType(name = "ArrayOfFornitura", propOrder = { "fornitura" })
public class ArrayOfFornitura
{
    @XmlElement(name = "Fornitura", nillable = true)
    protected List<Fornitura> fornitura;
    
    public List<Fornitura> getFornitura() {
        if (this.fornitura == null) {
            this.fornitura = new ArrayList<Fornitura>();
        }
        return this.fornitura;
    }
}
