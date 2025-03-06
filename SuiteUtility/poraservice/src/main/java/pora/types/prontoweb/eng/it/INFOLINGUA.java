// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import org.datacontract.schemas._2004._07.pweb_be_pora.InfoGUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INFOLINGUA", propOrder = { "linguaid" })
@XmlSeeAlso({ INFOMULTICOMPANY.class })
public class INFOLINGUA extends InfoGUID
{
    @XmlElement(name = "LINGUA_ID", nillable = true)
    protected String linguaid;
    
    public String getLINGUAID() {
        return this.linguaid;
    }
    
    public void setLINGUAID(final String value) {
        this.linguaid = value;
    }
}
