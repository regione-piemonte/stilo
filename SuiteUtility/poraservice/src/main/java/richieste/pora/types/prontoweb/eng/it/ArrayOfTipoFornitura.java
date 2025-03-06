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
@XmlType(name = "ArrayOfTipoFornitura", propOrder = { "tipoFornitura" })
public class ArrayOfTipoFornitura
{
    @XmlElement(name = "TipoFornitura", nillable = true)
    protected List<TipoFornitura> tipoFornitura;
    
    public List<TipoFornitura> getTipoFornitura() {
        if (this.tipoFornitura == null) {
            this.tipoFornitura = new ArrayList<TipoFornitura>();
        }
        return this.tipoFornitura;
    }
}
