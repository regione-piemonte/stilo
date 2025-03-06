// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import pora.types.prontoweb.eng.it.ArrayOfRUOLO;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ACCESSOSITO", propOrder = { "listaconsensisog", "listaruoli", "profilosoggetto" })
@XmlSeeAlso({ ESITOCAMBIOPASSWORD.class, ESITOLOGIN.class })
public class ACCESSOSITO extends INFOMULTICOMPANY
{
    @XmlElement(name = "LISTACONSENSISOG", nillable = true)
    protected ArrayOfCONSENSOSOG listaconsensisog;
    @XmlElement(name = "LISTARUOLI", nillable = true)
    protected ArrayOfRUOLO listaruoli;
    @XmlElement(name = "PROFILOSOGGETTO", nillable = true)
    protected DATISOGGETTO profilosoggetto;
    
    public ArrayOfCONSENSOSOG getLISTACONSENSISOG() {
        return this.listaconsensisog;
    }
    
    public void setLISTACONSENSISOG(final ArrayOfCONSENSOSOG value) {
        this.listaconsensisog = value;
    }
    
    public ArrayOfRUOLO getLISTARUOLI() {
        return this.listaruoli;
    }
    
    public void setLISTARUOLI(final ArrayOfRUOLO value) {
        this.listaruoli = value;
    }
    
    public DATISOGGETTO getPROFILOSOGGETTO() {
        return this.profilosoggetto;
    }
    
    public void setPROFILOSOGGETTO(final DATISOGGETTO value) {
        this.profilosoggetto = value;
    }
}
