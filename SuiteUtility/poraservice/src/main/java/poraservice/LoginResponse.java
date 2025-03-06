// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlElement;
import pora.types.prontoweb.eng.it.RESULTMSG;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "loginResult" })
@XmlRootElement(name = "LoginResponse")
public class LoginResponse
{
    @XmlElement(name = "LoginResult", nillable = true)
    protected RESULTMSG loginResult;
    
    public RESULTMSG getLoginResult() {
        return this.loginResult;
    }
    
    public void setLoginResult(final RESULTMSG value) {
        this.loginResult = value;
    }
}
