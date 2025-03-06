// 
// Decompiled by Procyon v0.5.36
// 

package log.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LOGJOB", propOrder = { "broken", "failures", "frequenza", "job", "lastdate", "nextdate", "profondita", "totaltime" })
public class LOGJOB
{
    @XmlElement(name = "BROKEN", nillable = true)
    protected String broken;
    @XmlElement(name = "FAILURES", nillable = true)
    protected String failures;
    @XmlElement(name = "FREQUENZA", nillable = true)
    protected String frequenza;
    @XmlElement(name = "JOB", nillable = true)
    protected String job;
    @XmlElement(name = "LASTDATE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastdate;
    @XmlElement(name = "NEXTDATE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar nextdate;
    @XmlElement(name = "PROFONDITA")
    protected Integer profondita;
    @XmlElement(name = "TOTALTIME", nillable = true)
    protected String totaltime;
    
    public String getBROKEN() {
        return this.broken;
    }
    
    public void setBROKEN(final String value) {
        this.broken = value;
    }
    
    public String getFAILURES() {
        return this.failures;
    }
    
    public void setFAILURES(final String value) {
        this.failures = value;
    }
    
    public String getFREQUENZA() {
        return this.frequenza;
    }
    
    public void setFREQUENZA(final String value) {
        this.frequenza = value;
    }
    
    public String getJOB() {
        return this.job;
    }
    
    public void setJOB(final String value) {
        this.job = value;
    }
    
    public XMLGregorianCalendar getLASTDATE() {
        return this.lastdate;
    }
    
    public void setLASTDATE(final XMLGregorianCalendar value) {
        this.lastdate = value;
    }
    
    public XMLGregorianCalendar getNEXTDATE() {
        return this.nextdate;
    }
    
    public void setNEXTDATE(final XMLGregorianCalendar value) {
        this.nextdate = value;
    }
    
    public Integer getPROFONDITA() {
        return this.profondita;
    }
    
    public void setPROFONDITA(final Integer value) {
        this.profondita = value;
    }
    
    public String getTOTALTIME() {
        return this.totaltime;
    }
    
    public void setTOTALTIME(final String value) {
        this.totaltime = value;
    }
}
