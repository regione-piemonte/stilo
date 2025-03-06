// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONSENSOSOG", propOrder = { "consensoregistrazione", "consensorichieste", "dataconsenso", "livellocod", "obbamm", "ordinamento", "soggettocod", "statocod", "statodesc", "tipocod", "tipodescrizione" })
public class CONSENSOSOG
{
    @XmlElement(name = "CONSENSOREGISTRAZIONE", required = true, nillable = true)
    protected String consensoregistrazione;
    @XmlElement(name = "CONSENSORICHIESTE", required = true, nillable = true)
    protected String consensorichieste;
    @XmlElement(name = "DATACONSENSO", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataconsenso;
    @XmlElement(name = "LIVELLOCOD", required = true, nillable = true)
    protected String livellocod;
    @XmlElement(name = "OBBAMM", required = true, nillable = true)
    protected String obbamm;
    @XmlElement(name = "ORDINAMENTO")
    protected int ordinamento;
    @XmlElement(name = "SOGGETTOCOD", required = true, nillable = true)
    protected String soggettocod;
    @XmlElement(name = "STATOCOD", required = true, nillable = true)
    protected String statocod;
    @XmlElement(name = "STATODESC", required = true, nillable = true)
    protected String statodesc;
    @XmlElement(name = "TIPOCOD", required = true, nillable = true)
    protected String tipocod;
    @XmlElement(name = "TIPODESCRIZIONE", required = true, nillable = true)
    protected String tipodescrizione;
    
    public String getCONSENSOREGISTRAZIONE() {
        return this.consensoregistrazione;
    }
    
    public void setCONSENSOREGISTRAZIONE(final String value) {
        this.consensoregistrazione = value;
    }
    
    public String getCONSENSORICHIESTE() {
        return this.consensorichieste;
    }
    
    public void setCONSENSORICHIESTE(final String value) {
        this.consensorichieste = value;
    }
    
    public XMLGregorianCalendar getDATACONSENSO() {
        return this.dataconsenso;
    }
    
    public void setDATACONSENSO(final XMLGregorianCalendar value) {
        this.dataconsenso = value;
    }
    
    public String getLIVELLOCOD() {
        return this.livellocod;
    }
    
    public void setLIVELLOCOD(final String value) {
        this.livellocod = value;
    }
    
    public String getOBBAMM() {
        return this.obbamm;
    }
    
    public void setOBBAMM(final String value) {
        this.obbamm = value;
    }
    
    public int getORDINAMENTO() {
        return this.ordinamento;
    }
    
    public void setORDINAMENTO(final int value) {
        this.ordinamento = value;
    }
    
    public String getSOGGETTOCOD() {
        return this.soggettocod;
    }
    
    public void setSOGGETTOCOD(final String value) {
        this.soggettocod = value;
    }
    
    public String getSTATOCOD() {
        return this.statocod;
    }
    
    public void setSTATOCOD(final String value) {
        this.statocod = value;
    }
    
    public String getSTATODESC() {
        return this.statodesc;
    }
    
    public void setSTATODESC(final String value) {
        this.statodesc = value;
    }
    
    public String getTIPOCOD() {
        return this.tipocod;
    }
    
    public void setTIPOCOD(final String value) {
        this.tipocod = value;
    }
    
    public String getTIPODESCRIZIONE() {
        return this.tipodescrizione;
    }
    
    public void setTIPODESCRIZIONE(final String value) {
        this.tipodescrizione = value;
    }
}
