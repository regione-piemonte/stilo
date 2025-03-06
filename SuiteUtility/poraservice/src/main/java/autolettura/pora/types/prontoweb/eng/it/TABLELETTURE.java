// 
// Decompiled by Procyon v0.5.36
// 

package autolettura.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import pora.types.prontoweb.eng.it.PWEBTABLE;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TABLELETTURE", propOrder = { "elencoletture" })
public class TABLELETTURE extends PWEBTABLE
{
    @XmlElement(name = "ELENCOLETTURE", required = true, nillable = true)
    protected ArrayOfLETTURA elencoletture;
    
    public ArrayOfLETTURA getELENCOLETTURE() {
        return this.elencoletture;
    }
    
    public void setELENCOLETTURE(final ArrayOfLETTURA value) {
        this.elencoletture = value;
    }
}
