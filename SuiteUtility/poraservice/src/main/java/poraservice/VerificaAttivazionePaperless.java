// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "codFornitura" })
@XmlRootElement(name = "VerificaAttivazionePaperless")
public class VerificaAttivazionePaperless
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String codFornitura;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getCodFornitura() {
        return this.codFornitura;
    }
    
    public void setCodFornitura(final String value) {
        this.codFornitura = value;
    }
}
