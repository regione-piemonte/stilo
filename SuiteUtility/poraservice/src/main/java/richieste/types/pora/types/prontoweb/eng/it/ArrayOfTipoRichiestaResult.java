// 
// Decompiled by Procyon v0.5.36
// 

package richieste.types.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTipoRichiestaResult", propOrder = { "tipoRichiestaResult" })
public class ArrayOfTipoRichiestaResult
{
    @XmlElement(name = "TipoRichiestaResult", nillable = true)
    protected List<TipoRichiestaResult> tipoRichiestaResult;
    
    public List<TipoRichiestaResult> getTipoRichiestaResult() {
        if (this.tipoRichiestaResult == null) {
            this.tipoRichiestaResult = new ArrayList<TipoRichiestaResult>();
        }
        return this.tipoRichiestaResult;
    }
}
