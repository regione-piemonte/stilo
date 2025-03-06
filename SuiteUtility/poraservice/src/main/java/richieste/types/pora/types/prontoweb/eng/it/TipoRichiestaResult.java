// 
// Decompiled by Procyon v0.5.36
// 

package richieste.types.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TipoRichiestaResult", propOrder = { "codtiporichiesta", "desctiporichiesta" })
public class TipoRichiestaResult
{
    @XmlElement(name = "CODTIPORICHIESTA", nillable = true)
    protected String codtiporichiesta;
    @XmlElement(name = "DESCTIPORICHIESTA", nillable = true)
    protected String desctiporichiesta;
    
    public String getCODTIPORICHIESTA() {
        return this.codtiporichiesta;
    }
    
    public void setCODTIPORICHIESTA(final String value) {
        this.codtiporichiesta = value;
    }
    
    public String getDESCTIPORICHIESTA() {
        return this.desctiporichiesta;
    }
    
    public void setDESCTIPORICHIESTA(final String value) {
        this.desctiporichiesta = value;
    }
}
