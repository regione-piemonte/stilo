// 
// Decompiled by Procyon v0.5.36
// 

package lettureconsumi.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import pora.types.prontoweb.eng.it.PWEBTABLE;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TABLECURVEPRELIEVO", propOrder = { "elencocurveprelievo" })
public class TABLECURVEPRELIEVO extends PWEBTABLE
{
    @XmlElement(name = "ELENCOCURVEPRELIEVO", required = true, nillable = true)
    protected ArrayOfDETTAGLIOPRELIEVO elencocurveprelievo;
    
    public ArrayOfDETTAGLIOPRELIEVO getELENCOCURVEPRELIEVO() {
        return this.elencocurveprelievo;
    }
    
    public void setELENCOCURVEPRELIEVO(final ArrayOfDETTAGLIOPRELIEVO value) {
        this.elencocurveprelievo = value;
    }
}
