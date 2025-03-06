// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FORNITURA", propOrder = { "soggettocod", "statofornituracod", "statofornituradesc", "matricolacontatore", "statocontatorecod", "statocontatoredesc", "tiposerviziodesc", "contrattoid", "contrattoanno", "contrattonumero", "datastipulacontratto", "datafinecontratto", "indirizzofornitura", "indirizzorecapito", "raggruppamentomultifornitura", "docid", "docanno", "docnumero", "docdataemissione", "docesistenzapdf", "docidnotencrypted" })
@XmlSeeAlso({ FORNITURAEXT.class })
public class FORNITURA extends FORNITURAMIN
{
    @XmlElement(name = "SOGGETTOCOD", nillable = true)
    protected String soggettocod;
    @XmlElement(name = "STATOFORNITURACOD", nillable = true)
    protected String statofornituracod;
    @XmlElement(name = "STATOFORNITURADESC", nillable = true)
    protected String statofornituradesc;
    @XmlElement(name = "MATRICOLACONTATORE", nillable = true)
    protected String matricolacontatore;
    @XmlElement(name = "STATOCONTATORECOD", nillable = true)
    protected String statocontatorecod;
    @XmlElement(name = "STATOCONTATOREDESC", nillable = true)
    protected String statocontatoredesc;
    @XmlElement(name = "TIPOSERVIZIODESC", nillable = true)
    protected String tiposerviziodesc;
    @XmlElement(name = "CONTRATTOID", nillable = true)
    protected String contrattoid;
    @XmlElement(name = "CONTRATTOANNO")
    protected Integer contrattoanno;
    @XmlElement(name = "CONTRATTONUMERO", nillable = true)
    protected String contrattonumero;
    @XmlElement(name = "DATASTIPULACONTRATTO")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datastipulacontratto;
    @XmlElement(name = "DATAFINECONTRATTO")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datafinecontratto;
    @XmlElement(name = "INDIRIZZOFORNITURA", nillable = true)
    protected INDIRIZZO indirizzofornitura;
    @XmlElement(name = "INDIRIZZORECAPITO", nillable = true)
    protected INDIRIZZO indirizzorecapito;
    @XmlElement(name = "RAGGRUPPAMENTOMULTIFORNITURA")
    protected Boolean raggruppamentomultifornitura;
    @XmlElement(name = "DOCID", nillable = true)
    protected String docid;
    @XmlElement(name = "DOCANNO")
    protected Integer docanno;
    @XmlElement(name = "DOCNUMERO")
    protected Long docnumero;
    @XmlElement(name = "DOCDATAEMISSIONE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar docdataemissione;
    @XmlElement(name = "DOCESISTENZAPDF")
    protected Boolean docesistenzapdf;
    @XmlElement(name = "DOCID_NOTENCRYPTED", nillable = true)
    protected String docidnotencrypted;
    
    public String getSOGGETTOCOD() {
        return this.soggettocod;
    }
    
    public void setSOGGETTOCOD(final String value) {
        this.soggettocod = value;
    }
    
    public String getSTATOFORNITURACOD() {
        return this.statofornituracod;
    }
    
    public void setSTATOFORNITURACOD(final String value) {
        this.statofornituracod = value;
    }
    
    public String getSTATOFORNITURADESC() {
        return this.statofornituradesc;
    }
    
    public void setSTATOFORNITURADESC(final String value) {
        this.statofornituradesc = value;
    }
    
    public String getMATRICOLACONTATORE() {
        return this.matricolacontatore;
    }
    
    public void setMATRICOLACONTATORE(final String value) {
        this.matricolacontatore = value;
    }
    
    public String getSTATOCONTATORECOD() {
        return this.statocontatorecod;
    }
    
    public void setSTATOCONTATORECOD(final String value) {
        this.statocontatorecod = value;
    }
    
    public String getSTATOCONTATOREDESC() {
        return this.statocontatoredesc;
    }
    
    public void setSTATOCONTATOREDESC(final String value) {
        this.statocontatoredesc = value;
    }
    
    public String getTIPOSERVIZIODESC() {
        return this.tiposerviziodesc;
    }
    
    public void setTIPOSERVIZIODESC(final String value) {
        this.tiposerviziodesc = value;
    }
    
    public String getCONTRATTOID() {
        return this.contrattoid;
    }
    
    public void setCONTRATTOID(final String value) {
        this.contrattoid = value;
    }
    
    public Integer getCONTRATTOANNO() {
        return this.contrattoanno;
    }
    
    public void setCONTRATTOANNO(final Integer value) {
        this.contrattoanno = value;
    }
    
    public String getCONTRATTONUMERO() {
        return this.contrattonumero;
    }
    
    public void setCONTRATTONUMERO(final String value) {
        this.contrattonumero = value;
    }
    
    public XMLGregorianCalendar getDATASTIPULACONTRATTO() {
        return this.datastipulacontratto;
    }
    
    public void setDATASTIPULACONTRATTO(final XMLGregorianCalendar value) {
        this.datastipulacontratto = value;
    }
    
    public XMLGregorianCalendar getDATAFINECONTRATTO() {
        return this.datafinecontratto;
    }
    
    public void setDATAFINECONTRATTO(final XMLGregorianCalendar value) {
        this.datafinecontratto = value;
    }
    
    public INDIRIZZO getINDIRIZZOFORNITURA() {
        return this.indirizzofornitura;
    }
    
    public void setINDIRIZZOFORNITURA(final INDIRIZZO value) {
        this.indirizzofornitura = value;
    }
    
    public INDIRIZZO getINDIRIZZORECAPITO() {
        return this.indirizzorecapito;
    }
    
    public void setINDIRIZZORECAPITO(final INDIRIZZO value) {
        this.indirizzorecapito = value;
    }
    
    public Boolean isRAGGRUPPAMENTOMULTIFORNITURA() {
        return this.raggruppamentomultifornitura;
    }
    
    public void setRAGGRUPPAMENTOMULTIFORNITURA(final Boolean value) {
        this.raggruppamentomultifornitura = value;
    }
    
    public String getDOCID() {
        return this.docid;
    }
    
    public void setDOCID(final String value) {
        this.docid = value;
    }
    
    public Integer getDOCANNO() {
        return this.docanno;
    }
    
    public void setDOCANNO(final Integer value) {
        this.docanno = value;
    }
    
    public Long getDOCNUMERO() {
        return this.docnumero;
    }
    
    public void setDOCNUMERO(final Long value) {
        this.docnumero = value;
    }
    
    public XMLGregorianCalendar getDOCDATAEMISSIONE() {
        return this.docdataemissione;
    }
    
    public void setDOCDATAEMISSIONE(final XMLGregorianCalendar value) {
        this.docdataemissione = value;
    }
    
    public Boolean isDOCESISTENZAPDF() {
        return this.docesistenzapdf;
    }
    
    public void setDOCESISTENZAPDF(final Boolean value) {
        this.docesistenzapdf = value;
    }
    
    public String getDOCIDNOTENCRYPTED() {
        return this.docidnotencrypted;
    }
    
    public void setDOCIDNOTENCRYPTED(final String value) {
        this.docidnotencrypted = value;
    }
}
