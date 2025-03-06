// 
// Decompiled by Procyon v0.5.36
// 

package allegati.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ALLEGATOINFO", propOrder = { "codebase64", "contenttype", "descrizione", "descrizionebreve", "dimensione", "nomefile", "ticket" })
public class ALLEGATOINFO
{
    @XmlElement(name = "CODEBASE64", nillable = true)
    protected String codebase64;
    @XmlElement(name = "CONTENTTYPE", nillable = true)
    protected String contenttype;
    @XmlElement(name = "DESCRIZIONE", nillable = true)
    protected String descrizione;
    @XmlElement(name = "DESCRIZIONEBREVE", nillable = true)
    protected String descrizionebreve;
    @XmlElement(name = "DIMENSIONE")
    protected Integer dimensione;
    @XmlElement(name = "NOMEFILE", nillable = true)
    protected String nomefile;
    @XmlElement(name = "TICKET", nillable = true)
    protected String ticket;
    
    public String getCODEBASE64() {
        return this.codebase64;
    }
    
    public void setCODEBASE64(final String value) {
        this.codebase64 = value;
    }
    
    public String getCONTENTTYPE() {
        return this.contenttype;
    }
    
    public void setCONTENTTYPE(final String value) {
        this.contenttype = value;
    }
    
    public String getDESCRIZIONE() {
        return this.descrizione;
    }
    
    public void setDESCRIZIONE(final String value) {
        this.descrizione = value;
    }
    
    public String getDESCRIZIONEBREVE() {
        return this.descrizionebreve;
    }
    
    public void setDESCRIZIONEBREVE(final String value) {
        this.descrizionebreve = value;
    }
    
    public Integer getDIMENSIONE() {
        return this.dimensione;
    }
    
    public void setDIMENSIONE(final Integer value) {
        this.dimensione = value;
    }
    
    public String getNOMEFILE() {
        return this.nomefile;
    }
    
    public void setNOMEFILE(final String value) {
        this.nomefile = value;
    }
    
    public String getTICKET() {
        return this.ticket;
    }
    
    public void setTICKET(final String value) {
        this.ticket = value;
    }
}
