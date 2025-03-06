// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ESITORECUPEROCREDENZIALI", propOrder = { "token", "username" })
public class ESITORECUPEROCREDENZIALI extends INFOMULTICOMPANY
{
    @XmlElement(name = "TOKEN", nillable = true)
    protected String token;
    @XmlElement(name = "USERNAME", nillable = true)
    protected String username;
    
    public String getTOKEN() {
        return this.token;
    }
    
    public void setTOKEN(final String value) {
        this.token = value;
    }
    
    public String getUSERNAME() {
        return this.username;
    }
    
    public void setUSERNAME(final String value) {
        this.username = value;
    }
}
