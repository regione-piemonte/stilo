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
@XmlType(name = "TABLELETTUREEXT", propOrder = { "elencolettureext" })
public class TABLELETTUREEXT extends PWEBTABLE
{
    @XmlElement(name = "ELENCOLETTUREEXT", required = true, nillable = true)
    protected ArrayOfLETTURAEXT elencolettureext;
    
    public ArrayOfLETTURAEXT getELENCOLETTUREEXT() {
        return this.elencolettureext;
    }
    
    public void setELENCOLETTUREEXT(final ArrayOfLETTURAEXT value) {
        this.elencolettureext = value;
    }
}
