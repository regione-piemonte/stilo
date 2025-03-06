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
@XmlType(name = "ArrayOfPRODOTTORESULT", propOrder = { "prodottoresult" })
public class ArrayOfPRODOTTORESULT
{
    @XmlElement(name = "PRODOTTORESULT", nillable = true)
    protected List<PRODOTTORESULT> prodottoresult;
    
    public List<PRODOTTORESULT> getPRODOTTORESULT() {
        if (this.prodottoresult == null) {
            this.prodottoresult = new ArrayList<PRODOTTORESULT>();
        }
        return this.prodottoresult;
    }
}
