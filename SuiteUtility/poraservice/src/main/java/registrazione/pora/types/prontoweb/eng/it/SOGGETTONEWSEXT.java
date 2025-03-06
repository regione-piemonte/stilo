// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOGGETTONEWSEXT", propOrder = { "cellulare", "cognome", "email", "nome", "ragionesociale" })
public class SOGGETTONEWSEXT extends SOGGETTONEWS
{
    @XmlElement(name = "CELLULARE", nillable = true)
    protected String cellulare;
    @XmlElement(name = "COGNOME", nillable = true)
    protected String cognome;
    @XmlElement(name = "EMAIL", nillable = true)
    protected String email;
    @XmlElement(name = "NOME", nillable = true)
    protected String nome;
    @XmlElement(name = "RAGIONESOCIALE", nillable = true)
    protected String ragionesociale;
    
    public String getCELLULARE() {
        return this.cellulare;
    }
    
    public void setCELLULARE(final String value) {
        this.cellulare = value;
    }
    
    public String getCOGNOME() {
        return this.cognome;
    }
    
    public void setCOGNOME(final String value) {
        this.cognome = value;
    }
    
    public String getEMAIL() {
        return this.email;
    }
    
    public void setEMAIL(final String value) {
        this.email = value;
    }
    
    public String getNOME() {
        return this.nome;
    }
    
    public void setNOME(final String value) {
        this.nome = value;
    }
    
    public String getRAGIONESOCIALE() {
        return this.ragionesociale;
    }
    
    public void setRAGIONESOCIALE(final String value) {
        this.ragionesociale = value;
    }
}
