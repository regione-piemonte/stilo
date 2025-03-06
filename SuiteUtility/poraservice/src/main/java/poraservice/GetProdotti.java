// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import richieste.pora.types.prontoweb.eng.it.FiltriProdotto;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "filtriProdotto" })
@XmlRootElement(name = "GetProdotti")
public class GetProdotti
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected FiltriProdotto filtriProdotto;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public FiltriProdotto getFiltriProdotto() {
        return this.filtriProdotto;
    }
    
    public void setFiltriProdotto(final FiltriProdotto value) {
        this.filtriProdotto = value;
    }
}
