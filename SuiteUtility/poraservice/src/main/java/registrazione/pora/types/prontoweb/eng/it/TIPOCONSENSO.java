// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TIPOCONSENSO", propOrder = { "livellocod", "livellodescrizione", "registrazione", "richieste", "tipocod", "tipodescrizione" })
public class TIPOCONSENSO
{
    @XmlElement(name = "LIVELLOCOD", required = true, nillable = true)
    protected String livellocod;
    @XmlElement(name = "LIVELLODESCRIZIONE", required = true, nillable = true)
    protected String livellodescrizione;
    @XmlElement(name = "REGISTRAZIONE")
    protected boolean registrazione;
    @XmlElement(name = "RICHIESTE")
    protected boolean richieste;
    @XmlElement(name = "TIPOCOD", required = true, nillable = true)
    protected String tipocod;
    @XmlElement(name = "TIPODESCRIZIONE", required = true, nillable = true)
    protected String tipodescrizione;
    
    public String getLIVELLOCOD() {
        return this.livellocod;
    }
    
    public void setLIVELLOCOD(final String value) {
        this.livellocod = value;
    }
    
    public String getLIVELLODESCRIZIONE() {
        return this.livellodescrizione;
    }
    
    public void setLIVELLODESCRIZIONE(final String value) {
        this.livellodescrizione = value;
    }
    
    public boolean isREGISTRAZIONE() {
        return this.registrazione;
    }
    
    public void setREGISTRAZIONE(final boolean value) {
        this.registrazione = value;
    }
    
    public boolean isRICHIESTE() {
        return this.richieste;
    }
    
    public void setRICHIESTE(final boolean value) {
        this.richieste = value;
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
