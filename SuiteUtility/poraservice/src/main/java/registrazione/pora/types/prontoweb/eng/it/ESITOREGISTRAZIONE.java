// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import pora.types.prontoweb.eng.it.ArrayOfRUOLO;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ESITOREGISTRAZIONE", propOrder = { "isprospect", "linguasoggbe", "linguasoggfe", "listaruoli", "soggettocodreg", "token" })
public class ESITOREGISTRAZIONE extends INFOMULTICOMPANY
{
    @XmlElement(name = "ISPROSPECT")
    protected boolean isprospect;
    @XmlElement(name = "LINGUASOGGBE", required = true, nillable = true)
    protected String linguasoggbe;
    @XmlElement(name = "LINGUASOGGFE", required = true, nillable = true)
    protected String linguasoggfe;
    @XmlElement(name = "LISTARUOLI", nillable = true)
    protected ArrayOfRUOLO listaruoli;
    @XmlElement(name = "SOGGETTOCODREG", required = true, nillable = true)
    protected String soggettocodreg;
    @XmlElement(name = "TOKEN", required = true, nillable = true)
    protected String token;
    
    public boolean isISPROSPECT() {
        return this.isprospect;
    }
    
    public void setISPROSPECT(final boolean value) {
        this.isprospect = value;
    }
    
    public String getLINGUASOGGBE() {
        return this.linguasoggbe;
    }
    
    public void setLINGUASOGGBE(final String value) {
        this.linguasoggbe = value;
    }
    
    public String getLINGUASOGGFE() {
        return this.linguasoggfe;
    }
    
    public void setLINGUASOGGFE(final String value) {
        this.linguasoggfe = value;
    }
    
    public ArrayOfRUOLO getLISTARUOLI() {
        return this.listaruoli;
    }
    
    public void setLISTARUOLI(final ArrayOfRUOLO value) {
        this.listaruoli = value;
    }
    
    public String getSOGGETTOCODREG() {
        return this.soggettocodreg;
    }
    
    public void setSOGGETTOCODREG(final String value) {
        this.soggettocodreg = value;
    }
    
    public String getTOKEN() {
        return this.token;
    }
    
    public void setTOKEN(final String value) {
        this.token = value;
    }
}
