// 
// Decompiled by Procyon v0.5.36
// 

package richieste.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ESITORICHIESTA", propOrder = { "id", "ticket" })
public class ESITORICHIESTA
{
    @XmlElement(name = "ID", nillable = true)
    protected String id;
    @XmlElement(name = "TICKET", nillable = true)
    protected String ticket;
    
    public String getID() {
        return this.id;
    }
    
    public void setID(final String value) {
        this.id = value;
    }
    
    public String getTICKET() {
        return this.ticket;
    }
    
    public void setTICKET(final String value) {
        this.ticket = value;
    }
}
