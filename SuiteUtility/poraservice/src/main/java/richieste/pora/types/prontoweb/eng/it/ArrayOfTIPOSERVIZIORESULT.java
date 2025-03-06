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
@XmlType(name = "ArrayOfTIPOSERVIZIORESULT", propOrder = { "tiposervizioresult" })
public class ArrayOfTIPOSERVIZIORESULT
{
    @XmlElement(name = "TIPOSERVIZIORESULT", nillable = true)
    protected List<TIPOSERVIZIORESULT> tiposervizioresult;
    
    public List<TIPOSERVIZIORESULT> getTIPOSERVIZIORESULT() {
        if (this.tiposervizioresult == null) {
            this.tiposervizioresult = new ArrayList<TIPOSERVIZIORESULT>();
        }
        return this.tiposervizioresult;
    }
}
