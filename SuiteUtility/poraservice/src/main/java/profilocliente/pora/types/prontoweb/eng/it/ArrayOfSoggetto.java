// 
// Decompiled by Procyon v0.5.36
// 

package profilocliente.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSoggetto", propOrder = { "soggetto" })
public class ArrayOfSoggetto
{
    @XmlElement(name = "Soggetto", nillable = true)
    protected List<Soggetto> soggetto;
    
    public List<Soggetto> getSoggetto() {
        if (this.soggetto == null) {
            this.soggetto = new ArrayList<Soggetto>();
        }
        return this.soggetto;
    }
}
