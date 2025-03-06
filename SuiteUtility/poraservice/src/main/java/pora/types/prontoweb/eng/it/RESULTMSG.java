// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RESULTMSG", propOrder = { "typemsg", "codmsg", "progmsg", "descmsg", "objectmsg", "errmsg", "exceptionstacktrace", "errseverita", "errcodice" })
public class RESULTMSG
{
    @XmlElement(name = "TYPEMSG", required = true)
    @XmlSchemaType(name = "string")
    protected TYPERESPONSE typemsg;
    @XmlElement(name = "COD_MSG", nillable = true)
    protected String codmsg;
    @XmlElement(name = "PROG_MSG")
    protected Integer progmsg;
    @XmlElement(name = "DESC_MSG", nillable = true)
    protected String descmsg;
    @XmlElement(name = "OBJECTMSG", nillable = true)
    protected Object objectmsg;
    @XmlElement(name = "ERR_MSG", nillable = true)
    protected String errmsg;
    @XmlElement(name = "EXCEPTIONSTACKTRACE", nillable = true)
    protected String exceptionstacktrace;
    @XmlElement(name = "ERR_SEVERITA", nillable = true)
    protected String errseverita;
    @XmlElement(name = "ERR_CODICE", nillable = true)
    protected String errcodice;
    
    public TYPERESPONSE getTYPEMSG() {
        return this.typemsg;
    }
    
    public void setTYPEMSG(final TYPERESPONSE value) {
        this.typemsg = value;
    }
    
    public String getCODMSG() {
        return this.codmsg;
    }
    
    public void setCODMSG(final String value) {
        this.codmsg = value;
    }
    
    public Integer getPROGMSG() {
        return this.progmsg;
    }
    
    public void setPROGMSG(final Integer value) {
        this.progmsg = value;
    }
    
    public String getDESCMSG() {
        return this.descmsg;
    }
    
    public void setDESCMSG(final String value) {
        this.descmsg = value;
    }
    
    public Object getOBJECTMSG() {
        return this.objectmsg;
    }
    
    public void setOBJECTMSG(final Object value) {
        this.objectmsg = value;
    }
    
    public String getERRMSG() {
        return this.errmsg;
    }
    
    public void setERRMSG(final String value) {
        this.errmsg = value;
    }
    
    public String getEXCEPTIONSTACKTRACE() {
        return this.exceptionstacktrace;
    }
    
    public void setEXCEPTIONSTACKTRACE(final String value) {
        this.exceptionstacktrace = value;
    }
    
    public String getERRSEVERITA() {
        return this.errseverita;
    }
    
    public void setERRSEVERITA(final String value) {
        this.errseverita = value;
    }
    
    public String getERRCODICE() {
        return this.errcodice;
    }
    
    public void setERRCODICE(final String value) {
        this.errcodice = value;
    }
}
