// 
// Decompiled by Procyon v0.5.36
// 

package autolettura.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LETTURA", propOrder = { "annofatturazione", "componentestrumento", "consumo", "datafatturazione", "datalettura", "docfatturazione", "fasciaorariacod", "pianoorariocod", "pianoorariodesc", "progressivofascia", "progressivoregistrazione", "provenienzaletturacod", "provenienzaletturadesc", "siglacomponente", "statoletturacod", "statoletturadesc", "testataletturaid", "tipoconsumocod", "tipoconsumodesc", "tipoletturacod", "tipoletturadesc", "tipooriginelettura", "valorelettura" })
@XmlSeeAlso({ LETTURAEXT.class })
public class LETTURA
{
    @XmlElement(name = "ANNOFATTURAZIONE")
    protected Integer annofatturazione;
    @XmlElement(name = "COMPONENTESTRUMENTO", nillable = true)
    protected String componentestrumento;
    @XmlElement(name = "CONSUMO")
    protected Double consumo;
    @XmlElement(name = "DATAFATTURAZIONE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datafatturazione;
    @XmlElement(name = "DATALETTURA")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datalettura;
    @XmlElement(name = "DOCFATTURAZIONE")
    protected Long docfatturazione;
    @XmlElement(name = "FASCIAORARIACOD", nillable = true)
    protected String fasciaorariacod;
    @XmlElement(name = "PIANOORARIOCOD", nillable = true)
    protected String pianoorariocod;
    @XmlElement(name = "PIANOORARIODESC", nillable = true)
    protected String pianoorariodesc;
    @XmlElement(name = "PROGRESSIVOFASCIA")
    protected Integer progressivofascia;
    @XmlElement(name = "PROGRESSIVOREGISTRAZIONE")
    protected Integer progressivoregistrazione;
    @XmlElement(name = "PROVENIENZALETTURACOD", nillable = true)
    protected String provenienzaletturacod;
    @XmlElement(name = "PROVENIENZALETTURADESC", nillable = true)
    protected String provenienzaletturadesc;
    @XmlElement(name = "SIGLACOMPONENTE", nillable = true)
    protected String siglacomponente;
    @XmlElement(name = "STATOLETTURACOD", nillable = true)
    protected String statoletturacod;
    @XmlElement(name = "STATOLETTURADESC", nillable = true)
    protected String statoletturadesc;
    @XmlElement(name = "TESTATALETTURAID", required = true, nillable = true)
    protected String testataletturaid;
    @XmlElement(name = "TIPOCONSUMOCOD", nillable = true)
    protected String tipoconsumocod;
    @XmlElement(name = "TIPOCONSUMODESC", nillable = true)
    protected String tipoconsumodesc;
    @XmlElement(name = "TIPOLETTURACOD", nillable = true)
    protected String tipoletturacod;
    @XmlElement(name = "TIPOLETTURADESC", nillable = true)
    protected String tipoletturadesc;
    @XmlElement(name = "TIPOORIGINELETTURA", nillable = true)
    protected String tipooriginelettura;
    @XmlElement(name = "VALORELETTURA")
    protected Double valorelettura;
    
    public Integer getANNOFATTURAZIONE() {
        return this.annofatturazione;
    }
    
    public void setANNOFATTURAZIONE(final Integer value) {
        this.annofatturazione = value;
    }
    
    public String getCOMPONENTESTRUMENTO() {
        return this.componentestrumento;
    }
    
    public void setCOMPONENTESTRUMENTO(final String value) {
        this.componentestrumento = value;
    }
    
    public Double getCONSUMO() {
        return this.consumo;
    }
    
    public void setCONSUMO(final Double value) {
        this.consumo = value;
    }
    
    public XMLGregorianCalendar getDATAFATTURAZIONE() {
        return this.datafatturazione;
    }
    
    public void setDATAFATTURAZIONE(final XMLGregorianCalendar value) {
        this.datafatturazione = value;
    }
    
    public XMLGregorianCalendar getDATALETTURA() {
        return this.datalettura;
    }
    
    public void setDATALETTURA(final XMLGregorianCalendar value) {
        this.datalettura = value;
    }
    
    public Long getDOCFATTURAZIONE() {
        return this.docfatturazione;
    }
    
    public void setDOCFATTURAZIONE(final Long value) {
        this.docfatturazione = value;
    }
    
    public String getFASCIAORARIACOD() {
        return this.fasciaorariacod;
    }
    
    public void setFASCIAORARIACOD(final String value) {
        this.fasciaorariacod = value;
    }
    
    public String getPIANOORARIOCOD() {
        return this.pianoorariocod;
    }
    
    public void setPIANOORARIOCOD(final String value) {
        this.pianoorariocod = value;
    }
    
    public String getPIANOORARIODESC() {
        return this.pianoorariodesc;
    }
    
    public void setPIANOORARIODESC(final String value) {
        this.pianoorariodesc = value;
    }
    
    public Integer getPROGRESSIVOFASCIA() {
        return this.progressivofascia;
    }
    
    public void setPROGRESSIVOFASCIA(final Integer value) {
        this.progressivofascia = value;
    }
    
    public Integer getPROGRESSIVOREGISTRAZIONE() {
        return this.progressivoregistrazione;
    }
    
    public void setPROGRESSIVOREGISTRAZIONE(final Integer value) {
        this.progressivoregistrazione = value;
    }
    
    public String getPROVENIENZALETTURACOD() {
        return this.provenienzaletturacod;
    }
    
    public void setPROVENIENZALETTURACOD(final String value) {
        this.provenienzaletturacod = value;
    }
    
    public String getPROVENIENZALETTURADESC() {
        return this.provenienzaletturadesc;
    }
    
    public void setPROVENIENZALETTURADESC(final String value) {
        this.provenienzaletturadesc = value;
    }
    
    public String getSIGLACOMPONENTE() {
        return this.siglacomponente;
    }
    
    public void setSIGLACOMPONENTE(final String value) {
        this.siglacomponente = value;
    }
    
    public String getSTATOLETTURACOD() {
        return this.statoletturacod;
    }
    
    public void setSTATOLETTURACOD(final String value) {
        this.statoletturacod = value;
    }
    
    public String getSTATOLETTURADESC() {
        return this.statoletturadesc;
    }
    
    public void setSTATOLETTURADESC(final String value) {
        this.statoletturadesc = value;
    }
    
    public String getTESTATALETTURAID() {
        return this.testataletturaid;
    }
    
    public void setTESTATALETTURAID(final String value) {
        this.testataletturaid = value;
    }
    
    public String getTIPOCONSUMOCOD() {
        return this.tipoconsumocod;
    }
    
    public void setTIPOCONSUMOCOD(final String value) {
        this.tipoconsumocod = value;
    }
    
    public String getTIPOCONSUMODESC() {
        return this.tipoconsumodesc;
    }
    
    public void setTIPOCONSUMODESC(final String value) {
        this.tipoconsumodesc = value;
    }
    
    public String getTIPOLETTURACOD() {
        return this.tipoletturacod;
    }
    
    public void setTIPOLETTURACOD(final String value) {
        this.tipoletturacod = value;
    }
    
    public String getTIPOLETTURADESC() {
        return this.tipoletturadesc;
    }
    
    public void setTIPOLETTURADESC(final String value) {
        this.tipoletturadesc = value;
    }
    
    public String getTIPOORIGINELETTURA() {
        return this.tipooriginelettura;
    }
    
    public void setTIPOORIGINELETTURA(final String value) {
        this.tipooriginelettura = value;
    }
    
    public Double getVALORELETTURA() {
        return this.valorelettura;
    }
    
    public void setVALORELETTURA(final Double value) {
        this.valorelettura = value;
    }
}
