// 
// Decompiled by Procyon v0.5.36
// 

package multicompany.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "INFOMULTICOMPANYEXT", propOrder = { "anagrafebaseid", "anagrafeid", "baseid", "contrattuarioid", "crmbaseid", "stradarioid", "strbaseid" })
public class INFOMULTICOMPANYEXT extends INFOMULTICOMPANY
{
    @XmlElement(name = "ANAGRAFE_BASE_ID", nillable = true)
    protected String anagrafebaseid;
    @XmlElement(name = "ANAGRAFE_ID", nillable = true)
    protected String anagrafeid;
    @XmlElement(name = "BASE_ID", nillable = true)
    protected String baseid;
    @XmlElement(name = "CONTRATTUARIO_ID", nillable = true)
    protected String contrattuarioid;
    @XmlElement(name = "CRM_BASE_ID", nillable = true)
    protected String crmbaseid;
    @XmlElement(name = "STRADARIO_ID", nillable = true)
    protected String stradarioid;
    @XmlElement(name = "STR_BASE_ID", nillable = true)
    protected String strbaseid;
    
    public String getANAGRAFEBASEID() {
        return this.anagrafebaseid;
    }
    
    public void setANAGRAFEBASEID(final String value) {
        this.anagrafebaseid = value;
    }
    
    public String getANAGRAFEID() {
        return this.anagrafeid;
    }
    
    public void setANAGRAFEID(final String value) {
        this.anagrafeid = value;
    }
    
    public String getBASEID() {
        return this.baseid;
    }
    
    public void setBASEID(final String value) {
        this.baseid = value;
    }
    
    public String getCONTRATTUARIOID() {
        return this.contrattuarioid;
    }
    
    public void setCONTRATTUARIOID(final String value) {
        this.contrattuarioid = value;
    }
    
    public String getCRMBASEID() {
        return this.crmbaseid;
    }
    
    public void setCRMBASEID(final String value) {
        this.crmbaseid = value;
    }
    
    public String getSTRADARIOID() {
        return this.stradarioid;
    }
    
    public void setSTRADARIOID(final String value) {
        this.stradarioid = value;
    }
    
    public String getSTRBASEID() {
        return this.strbaseid;
    }
    
    public void setSTRBASEID(final String value) {
        this.strbaseid = value;
    }
}
