// 
// Decompiled by Procyon v0.5.36
// 

package stradario.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "STRELEMCOMUNELOCALITA", propOrder = { "codice", "comunecod", "comunedesc", "descrizione", "provinciacod", "provinciadesc", "regionecod", "regionedesc" })
public class STRELEMCOMUNELOCALITA
{
    @XmlElement(name = "CODICE", nillable = true)
    protected String codice;
    @XmlElement(name = "COMUNECOD", nillable = true)
    protected String comunecod;
    @XmlElement(name = "COMUNEDESC", nillable = true)
    protected String comunedesc;
    @XmlElement(name = "DESCRIZIONE", nillable = true)
    protected String descrizione;
    @XmlElement(name = "PROVINCIACOD", nillable = true)
    protected String provinciacod;
    @XmlElement(name = "PROVINCIADESC", nillable = true)
    protected String provinciadesc;
    @XmlElement(name = "REGIONECOD", nillable = true)
    protected String regionecod;
    @XmlElement(name = "REGIONEDESC", nillable = true)
    protected String regionedesc;
    
    public String getCODICE() {
        return this.codice;
    }
    
    public void setCODICE(final String value) {
        this.codice = value;
    }
    
    public String getCOMUNECOD() {
        return this.comunecod;
    }
    
    public void setCOMUNECOD(final String value) {
        this.comunecod = value;
    }
    
    public String getCOMUNEDESC() {
        return this.comunedesc;
    }
    
    public void setCOMUNEDESC(final String value) {
        this.comunedesc = value;
    }
    
    public String getDESCRIZIONE() {
        return this.descrizione;
    }
    
    public void setDESCRIZIONE(final String value) {
        this.descrizione = value;
    }
    
    public String getPROVINCIACOD() {
        return this.provinciacod;
    }
    
    public void setPROVINCIACOD(final String value) {
        this.provinciacod = value;
    }
    
    public String getPROVINCIADESC() {
        return this.provinciadesc;
    }
    
    public void setPROVINCIADESC(final String value) {
        this.provinciadesc = value;
    }
    
    public String getREGIONECOD() {
        return this.regionecod;
    }
    
    public void setREGIONECOD(final String value) {
        this.regionecod = value;
    }
    
    public String getREGIONEDESC() {
        return this.regionedesc;
    }
    
    public void setREGIONEDESC(final String value) {
        this.regionedesc = value;
    }
}
