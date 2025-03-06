// 
// Decompiled by Procyon v0.5.36
// 

package stradario.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "STRCOMUNELOCALITA", propOrder = { "comuni", "localita" })
public class STRCOMUNELOCALITA
{
    @XmlElement(name = "COMUNI", nillable = true)
    protected ArrayOfSTRELEMCOMUNELOCALITA comuni;
    @XmlElement(name = "LOCALITA", nillable = true)
    protected ArrayOfSTRELEMCOMUNELOCALITA localita;
    
    public ArrayOfSTRELEMCOMUNELOCALITA getCOMUNI() {
        return this.comuni;
    }
    
    public void setCOMUNI(final ArrayOfSTRELEMCOMUNELOCALITA value) {
        this.comuni = value;
    }
    
    public ArrayOfSTRELEMCOMUNELOCALITA getLOCALITA() {
        return this.localita;
    }
    
    public void setLOCALITA(final ArrayOfSTRELEMCOMUNELOCALITA value) {
        this.localita = value;
    }
}
