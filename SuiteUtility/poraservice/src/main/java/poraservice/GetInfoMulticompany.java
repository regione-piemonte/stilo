// 
// Decompiled by Procyon v0.5.36
// 

package poraservice;

import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "mcomp" })
@XmlRootElement(name = "GetInfoMulticompany")
public class GetInfoMulticompany
{
    @XmlElement(nillable = true)
    protected INFOMULTICOMPANY mcomp;
    
    public INFOMULTICOMPANY getMcomp() {
        return this.mcomp;
    }
    
    public void setMcomp(final INFOMULTICOMPANY value) {
        this.mcomp = value;
    }
}
