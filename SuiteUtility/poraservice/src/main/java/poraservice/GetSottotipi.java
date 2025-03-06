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
@XmlType(name = "", propOrder = { "multicompany", "tipoRichiesta", "codLineaProdotto" })
@XmlRootElement(name = "GetSottotipi")
public class GetSottotipi
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String tipoRichiesta;
    @XmlElement(nillable = true)
    protected String codLineaProdotto;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getTipoRichiesta() {
        return this.tipoRichiesta;
    }
    
    public void setTipoRichiesta(final String value) {
        this.tipoRichiesta = value;
    }
    
    public String getCodLineaProdotto() {
        return this.codLineaProdotto;
    }
    
    public void setCodLineaProdotto(final String value) {
        this.codLineaProdotto = value;
    }
}
