// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import richieste.pora.types.prontoweb.eng.it.DATIINSERIMENTORICHIESTA;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "datiRichiesta" })
@XmlRootElement(name = "InserimentoRichiestaDD")
public class InserimentoRichiestaDD
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected DATIINSERIMENTORICHIESTA datiRichiesta;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public DATIINSERIMENTORICHIESTA getDatiRichiesta() {
        return this.datiRichiesta;
    }
    
    public void setDatiRichiesta(final DATIINSERIMENTORICHIESTA value) {
        this.datiRichiesta = value;
    }
}
