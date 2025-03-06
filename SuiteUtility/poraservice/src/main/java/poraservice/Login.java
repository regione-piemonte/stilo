// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import pora.types.prontoweb.eng.it.ArrayOfRUOLO;
import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "multicompany", "account", "password", "ruoli" })
@XmlRootElement(name = "Login")
public class Login
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String account;
    @XmlElement(nillable = true)
    protected String password;
    @XmlElement(nillable = true)
    protected ArrayOfRUOLO ruoli;
    
    public INFOMULTICOMPANY getMulticompany() {
        return this.multicompany;
    }
    
    public void setMulticompany(final INFOMULTICOMPANY value) {
        this.multicompany = value;
    }
    
    public String getAccount() {
        return this.account;
    }
    
    public void setAccount(final String value) {
        this.account = value;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String value) {
        this.password = value;
    }
    
    public ArrayOfRUOLO getRuoli() {
        return this.ruoli;
    }
    
    public void setRuoli(final ArrayOfRUOLO value) {
        this.ruoli = value;
    }
}
