// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INDIRIZZO", propOrder = { "capcod", "capdes", "civicodes", "civiconumero", "comunecod", "comunedes", "indirizzoid", "interno", "lettera", "localitacod", "localitades", "logicanumerazione", "nazionecod", "nazionedes", "piano", "provinciacod", "provinciades", "regionecod", "regionedes", "scala", "suffisso", "tipocivcod", "tipocivdes", "tipoindirizzocod", "tipoviacod", "tipoviades", "viacod", "viades" })
public class INDIRIZZO
{
    @XmlElement(name = "CAPCOD", required = true, nillable = true)
    protected String capcod;
    @XmlElement(name = "CAPDES", required = true, nillable = true)
    protected String capdes;
    @XmlElement(name = "CIVICODES", required = true, nillable = true)
    protected String civicodes;
    @XmlElement(name = "CIVICONUMERO")
    protected double civiconumero;
    @XmlElement(name = "COMUNECOD", required = true, nillable = true)
    protected String comunecod;
    @XmlElement(name = "COMUNEDES", required = true, nillable = true)
    protected String comunedes;
    @XmlElement(name = "INDIRIZZOID", nillable = true)
    protected String indirizzoid;
    @XmlElement(name = "INTERNO", nillable = true)
    protected String interno;
    @XmlElement(name = "LETTERA", nillable = true)
    protected String lettera;
    @XmlElement(name = "LOCALITACOD", nillable = true)
    protected String localitacod;
    @XmlElement(name = "LOCALITADES", required = true, nillable = true)
    protected String localitades;
    @XmlElement(name = "LOGICANUMERAZIONE", nillable = true)
    protected String logicanumerazione;
    @XmlElement(name = "NAZIONECOD", required = true, nillable = true)
    protected String nazionecod;
    @XmlElement(name = "NAZIONEDES", required = true, nillable = true)
    protected String nazionedes;
    @XmlElement(name = "PIANO", nillable = true)
    protected String piano;
    @XmlElement(name = "PROVINCIACOD", required = true, nillable = true)
    protected String provinciacod;
    @XmlElement(name = "PROVINCIADES", required = true, nillable = true)
    protected String provinciades;
    @XmlElement(name = "REGIONECOD", required = true, nillable = true)
    protected String regionecod;
    @XmlElement(name = "REGIONEDES", required = true, nillable = true)
    protected String regionedes;
    @XmlElement(name = "SCALA", nillable = true)
    protected String scala;
    @XmlElement(name = "SUFFISSO", nillable = true)
    protected String suffisso;
    @XmlElement(name = "TIPOCIVCOD", required = true, nillable = true)
    protected String tipocivcod;
    @XmlElement(name = "TIPOCIVDES", required = true, nillable = true)
    protected String tipocivdes;
    @XmlElement(name = "TIPOINDIRIZZOCOD", nillable = true)
    protected String tipoindirizzocod;
    @XmlElement(name = "TIPOVIACOD", required = true, nillable = true)
    protected String tipoviacod;
    @XmlElement(name = "TIPOVIADES", required = true, nillable = true)
    protected String tipoviades;
    @XmlElement(name = "VIACOD", nillable = true)
    protected String viacod;
    @XmlElement(name = "VIADES", required = true, nillable = true)
    protected String viades;
    
    public String getCAPCOD() {
        return this.capcod;
    }
    
    public void setCAPCOD(final String value) {
        this.capcod = value;
    }
    
    public String getCAPDES() {
        return this.capdes;
    }
    
    public void setCAPDES(final String value) {
        this.capdes = value;
    }
    
    public String getCIVICODES() {
        return this.civicodes;
    }
    
    public void setCIVICODES(final String value) {
        this.civicodes = value;
    }
    
    public double getCIVICONUMERO() {
        return this.civiconumero;
    }
    
    public void setCIVICONUMERO(final double value) {
        this.civiconumero = value;
    }
    
    public String getCOMUNECOD() {
        return this.comunecod;
    }
    
    public void setCOMUNECOD(final String value) {
        this.comunecod = value;
    }
    
    public String getCOMUNEDES() {
        return this.comunedes;
    }
    
    public void setCOMUNEDES(final String value) {
        this.comunedes = value;
    }
    
    public String getINDIRIZZOID() {
        return this.indirizzoid;
    }
    
    public void setINDIRIZZOID(final String value) {
        this.indirizzoid = value;
    }
    
    public String getINTERNO() {
        return this.interno;
    }
    
    public void setINTERNO(final String value) {
        this.interno = value;
    }
    
    public String getLETTERA() {
        return this.lettera;
    }
    
    public void setLETTERA(final String value) {
        this.lettera = value;
    }
    
    public String getLOCALITACOD() {
        return this.localitacod;
    }
    
    public void setLOCALITACOD(final String value) {
        this.localitacod = value;
    }
    
    public String getLOCALITADES() {
        return this.localitades;
    }
    
    public void setLOCALITADES(final String value) {
        this.localitades = value;
    }
    
    public String getLOGICANUMERAZIONE() {
        return this.logicanumerazione;
    }
    
    public void setLOGICANUMERAZIONE(final String value) {
        this.logicanumerazione = value;
    }
    
    public String getNAZIONECOD() {
        return this.nazionecod;
    }
    
    public void setNAZIONECOD(final String value) {
        this.nazionecod = value;
    }
    
    public String getNAZIONEDES() {
        return this.nazionedes;
    }
    
    public void setNAZIONEDES(final String value) {
        this.nazionedes = value;
    }
    
    public String getPIANO() {
        return this.piano;
    }
    
    public void setPIANO(final String value) {
        this.piano = value;
    }
    
    public String getPROVINCIACOD() {
        return this.provinciacod;
    }
    
    public void setPROVINCIACOD(final String value) {
        this.provinciacod = value;
    }
    
    public String getPROVINCIADES() {
        return this.provinciades;
    }
    
    public void setPROVINCIADES(final String value) {
        this.provinciades = value;
    }
    
    public String getREGIONECOD() {
        return this.regionecod;
    }
    
    public void setREGIONECOD(final String value) {
        this.regionecod = value;
    }
    
    public String getREGIONEDES() {
        return this.regionedes;
    }
    
    public void setREGIONEDES(final String value) {
        this.regionedes = value;
    }
    
    public String getSCALA() {
        return this.scala;
    }
    
    public void setSCALA(final String value) {
        this.scala = value;
    }
    
    public String getSUFFISSO() {
        return this.suffisso;
    }
    
    public void setSUFFISSO(final String value) {
        this.suffisso = value;
    }
    
    public String getTIPOCIVCOD() {
        return this.tipocivcod;
    }
    
    public void setTIPOCIVCOD(final String value) {
        this.tipocivcod = value;
    }
    
    public String getTIPOCIVDES() {
        return this.tipocivdes;
    }
    
    public void setTIPOCIVDES(final String value) {
        this.tipocivdes = value;
    }
    
    public String getTIPOINDIRIZZOCOD() {
        return this.tipoindirizzocod;
    }
    
    public void setTIPOINDIRIZZOCOD(final String value) {
        this.tipoindirizzocod = value;
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
    
    public String getVIACOD() {
        return this.viacod;
    }
    
    public void setVIACOD(final String value) {
        this.viacod = value;
    }
    
    public String getVIADES() {
        return this.viades;
    }
    
    public void setVIADES(final String value) {
        this.viades = value;
    }
}
