// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import registrazione.pora.types.prontoweb.eng.it.ArrayOfDATISOGGETTO;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TABLEDATISOGGETTO", propOrder = { "elencodatisoggetto" })
public class TABLEDATISOGGETTO extends PWEBTABLE
{
    @XmlElement(name = "ELENCODATISOGGETTO", required = true, nillable = true)
    protected ArrayOfDATISOGGETTO elencodatisoggetto;
    
    public ArrayOfDATISOGGETTO getELENCODATISOGGETTO() {
        return this.elencodatisoggetto;
    }
    
    public void setELENCODATISOGGETTO(final ArrayOfDATISOGGETTO value) {
        this.elencodatisoggetto = value;
    }
}
