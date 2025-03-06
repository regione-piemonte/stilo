// 
// Decompiled by Procyon v0.5.36
// 

package prospect.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSOGGETTOPROSPECTBASE", propOrder = { "soggettoprospectbase" })
public class ArrayOfSOGGETTOPROSPECTBASE
{
    @XmlElement(name = "SOGGETTOPROSPECTBASE", nillable = true)
    protected List<SOGGETTOPROSPECTBASE> soggettoprospectbase;
    
    public List<SOGGETTOPROSPECTBASE> getSOGGETTOPROSPECTBASE() {
        if (this.soggettoprospectbase == null) {
            this.soggettoprospectbase = new ArrayList<SOGGETTOPROSPECTBASE>();
        }
        return this.soggettoprospectbase;
    }
}
