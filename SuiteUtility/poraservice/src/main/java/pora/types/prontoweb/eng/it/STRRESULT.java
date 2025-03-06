// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import stradario.pora.types.prontoweb.eng.it.STRRESULTEXT;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "STRRESULT", propOrder = { "codice", "descrizione" })
@XmlSeeAlso({ STRRESULTEXT.class })
public class STRRESULT
{
    @XmlElement(name = "CODICE", nillable = true)
    protected String codice;
    @XmlElement(name = "DESCRIZIONE", nillable = true)
    protected String descrizione;
    
    public String getCODICE() {
        return this.codice;
    }
    
    public void setCODICE(final String value) {
        this.codice = value;
    }
    
    public String getDESCRIZIONE() {
        return this.descrizione;
    }
    
    public void setDESCRIZIONE(final String value) {
        this.descrizione = value;
    }
}
