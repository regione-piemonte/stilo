// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PRODOTTORESULT", propOrder = { "prodottocod", "prodottodesc", "lineaprodottocod", "lineaprodottodesc", "raggrproddualcod", "raggrproddualdesc", "tiposerviziocod", "tiposerviziodesc", "offertacod", "offertadesc", "residenzacod", "residenzadesc", "potenzacod", "potenzadesc", "attivazione", "subentro", "switching", "cambio" })
public class PRODOTTORESULT
{
    @XmlElement(name = "PRODOTTOCOD", nillable = true)
    protected String prodottocod;
    @XmlElement(name = "PRODOTTODESC", nillable = true)
    protected String prodottodesc;
    @XmlElement(name = "LINEAPRODOTTOCOD", nillable = true)
    protected String lineaprodottocod;
    @XmlElement(name = "LINEAPRODOTTODESC", nillable = true)
    protected String lineaprodottodesc;
    @XmlElement(name = "RAGGRPRODDUALCOD", nillable = true)
    protected String raggrproddualcod;
    @XmlElement(name = "RAGGRPRODDUALDESC", nillable = true)
    protected String raggrproddualdesc;
    @XmlElement(name = "TIPOSERVIZIOCOD", nillable = true)
    protected String tiposerviziocod;
    @XmlElement(name = "TIPOSERVIZIODESC", nillable = true)
    protected String tiposerviziodesc;
    @XmlElement(name = "OFFERTACOD", nillable = true)
    protected String offertacod;
    @XmlElement(name = "OFFERTADESC", nillable = true)
    protected String offertadesc;
    @XmlElement(name = "RESIDENZACOD", nillable = true)
    protected String residenzacod;
    @XmlElement(name = "RESIDENZADESC", nillable = true)
    protected String residenzadesc;
    @XmlElement(name = "POTENZACOD", nillable = true)
    protected String potenzacod;
    @XmlElement(name = "POTENZADESC", nillable = true)
    protected String potenzadesc;
    @XmlElement(name = "ATTIVAZIONE")
    protected Boolean attivazione;
    @XmlElement(name = "SUBENTRO")
    protected Boolean subentro;
    @XmlElement(name = "SWITCHING")
    protected Boolean switching;
    @XmlElement(name = "CAMBIO")
    protected Boolean cambio;
    
    public String getPRODOTTOCOD() {
        return this.prodottocod;
    }
    
    public void setPRODOTTOCOD(final String value) {
        this.prodottocod = value;
    }
    
    public String getPRODOTTODESC() {
        return this.prodottodesc;
    }
    
    public void setPRODOTTODESC(final String value) {
        this.prodottodesc = value;
    }
    
    public String getLINEAPRODOTTOCOD() {
        return this.lineaprodottocod;
    }
    
    public void setLINEAPRODOTTOCOD(final String value) {
        this.lineaprodottocod = value;
    }
    
    public String getLINEAPRODOTTODESC() {
        return this.lineaprodottodesc;
    }
    
    public void setLINEAPRODOTTODESC(final String value) {
        this.lineaprodottodesc = value;
    }
    
    public String getRAGGRPRODDUALCOD() {
        return this.raggrproddualcod;
    }
    
    public void setRAGGRPRODDUALCOD(final String value) {
        this.raggrproddualcod = value;
    }
    
    public String getRAGGRPRODDUALDESC() {
        return this.raggrproddualdesc;
    }
    
    public void setRAGGRPRODDUALDESC(final String value) {
        this.raggrproddualdesc = value;
    }
    
    public String getTIPOSERVIZIOCOD() {
        return this.tiposerviziocod;
    }
    
    public void setTIPOSERVIZIOCOD(final String value) {
        this.tiposerviziocod = value;
    }
    
    public String getTIPOSERVIZIODESC() {
        return this.tiposerviziodesc;
    }
    
    public void setTIPOSERVIZIODESC(final String value) {
        this.tiposerviziodesc = value;
    }
    
    public String getOFFERTACOD() {
        return this.offertacod;
    }
    
    public void setOFFERTACOD(final String value) {
        this.offertacod = value;
    }
    
    public String getOFFERTADESC() {
        return this.offertadesc;
    }
    
    public void setOFFERTADESC(final String value) {
        this.offertadesc = value;
    }
    
    public String getRESIDENZACOD() {
        return this.residenzacod;
    }
    
    public void setRESIDENZACOD(final String value) {
        this.residenzacod = value;
    }
    
    public String getRESIDENZADESC() {
        return this.residenzadesc;
    }
    
    public void setRESIDENZADESC(final String value) {
        this.residenzadesc = value;
    }
    
    public String getPOTENZACOD() {
        return this.potenzacod;
    }
    
    public void setPOTENZACOD(final String value) {
        this.potenzacod = value;
    }
    
    public String getPOTENZADESC() {
        return this.potenzadesc;
    }
    
    public void setPOTENZADESC(final String value) {
        this.potenzadesc = value;
    }
    
    public Boolean isATTIVAZIONE() {
        return this.attivazione;
    }
    
    public void setATTIVAZIONE(final Boolean value) {
        this.attivazione = value;
    }
    
    public Boolean isSUBENTRO() {
        return this.subentro;
    }
    
    public void setSUBENTRO(final Boolean value) {
        this.subentro = value;
    }
    
    public Boolean isSWITCHING() {
        return this.switching;
    }
    
    public void setSWITCHING(final Boolean value) {
        this.switching = value;
    }
    
    public Boolean isCAMBIO() {
        return this.cambio;
    }
    
    public void setCAMBIO(final Boolean value) {
        this.cambio = value;
    }
}
