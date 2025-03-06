// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ESITOCAMBIOPASSWORD", propOrder = { "newaccount" })
public class ESITOCAMBIOPASSWORD extends ACCESSOSITO
{
    @XmlElement(name = "NEW_ACCOUNT", nillable = true)
    protected String newaccount;
    
    public String getNEWACCOUNT() {
        return this.newaccount;
    }
    
    public void setNEWACCOUNT(final String value) {
        this.newaccount = value;
    }
}
