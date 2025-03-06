// 
// Decompiled by Procyon v0.5.36
// 

package lettureconsumi.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDETTAGLIOPRELIEVO", propOrder = { "dettaglioprelievo" })
public class ArrayOfDETTAGLIOPRELIEVO
{
    @XmlElement(name = "DETTAGLIOPRELIEVO", nillable = true)
    protected List<DETTAGLIOPRELIEVO> dettaglioprelievo;
    
    public List<DETTAGLIOPRELIEVO> getDETTAGLIOPRELIEVO() {
        if (this.dettaglioprelievo == null) {
            this.dettaglioprelievo = new ArrayList<DETTAGLIOPRELIEVO>();
        }
        return this.dettaglioprelievo;
    }
}
