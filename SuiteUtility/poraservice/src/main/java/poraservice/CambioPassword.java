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
@XmlType(name = "", propOrder = { "multicompany", "account", "password", "nuovoAccount", "nuovaPassword", "token", "ruoli" })
@XmlRootElement(name = "CambioPassword")
public class CambioPassword
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY multicompany;
    @XmlElement(nillable = true)
    protected String account;
    @XmlElement(nillable = true)
    protected String password;
    @XmlElement(nillable = true)
    protected String nuovoAccount;
    @XmlElement(nillable = true)
    protected String nuovaPassword;
    @XmlElement(nillable = true)
    protected String token;
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
    
    public String getNuovoAccount() {
        return this.nuovoAccount;
    }
    
    public void setNuovoAccount(final String value) {
        this.nuovoAccount = value;
    }
    
    public String getNuovaPassword() {
        return this.nuovaPassword;
    }
    
    public void setNuovaPassword(final String value) {
        this.nuovaPassword = value;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public void setToken(final String value) {
        this.token = value;
    }
    
    public ArrayOfRUOLO getRuoli() {
        return this.ruoli;
    }
    
    public void setRuoli(final ArrayOfRUOLO value) {
        this.ruoli = value;
    }
}
