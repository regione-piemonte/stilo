// 
// Decompiled by Procyon v0.5.36
// 

package stradario.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import pora.types.prontoweb.eng.it.STRRESULT;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "STRRESULTEXT", propOrder = { "capcod", "tipoviacod", "tipoviades" })
public class STRRESULTEXT extends STRRESULT
{
    @XmlElement(name = "CAPCOD", nillable = true)
    protected String capcod;
    @XmlElement(name = "TIPOVIACOD", nillable = true)
    protected String tipoviacod;
    @XmlElement(name = "TIPOVIADES", nillable = true)
    protected String tipoviades;
    
    public String getCAPCOD() {
        return this.capcod;
    }
    
    public void setCAPCOD(final String value) {
        this.capcod = value;
    }
    
    public String getTIPOVIACOD() {
        return this.tipoviacod;
    }
    
    public void setTIPOVIACOD(final String value) {
        this.tipoviacod = value;
    }
    
    public String getTIPOVIADES() {
        return this.tipoviades;
    }
    
    public void setTIPOVIADES(final String value) {
        this.tipoviades = value;
    }
}
