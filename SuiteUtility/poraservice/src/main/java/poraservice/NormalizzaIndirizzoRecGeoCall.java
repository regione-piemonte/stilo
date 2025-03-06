// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import richieste.pora.types.prontoweb.eng.it.IndRecType;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "indirizzo" })
@XmlRootElement(name = "NormalizzaIndirizzoRecGeoCall")
public class NormalizzaIndirizzoRecGeoCall
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected IndRecType indirizzo;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public IndRecType getIndirizzo() {
        return this.indirizzo;
    }
    
    public void setIndirizzo(final IndRecType value) {
        this.indirizzo = value;
    }
}
