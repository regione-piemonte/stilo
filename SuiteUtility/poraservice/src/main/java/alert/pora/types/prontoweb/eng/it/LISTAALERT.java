// 
// Decompiled by Procyon v0.5.36
// 

package alert.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LISTAALERT", propOrder = { "tblconsumiprossimeboll", "tbldateprossimeboll", "tbldocumentipagare" })
public class LISTAALERT
{
    @XmlElement(name = "TBLCONSUMIPROSSIMEBOLL", nillable = true)
    protected TABLECONSUMIPROSSIMEBOLL tblconsumiprossimeboll;
    @XmlElement(name = "TBLDATEPROSSIMEBOLL", nillable = true)
    protected TABLEDATEPROSSIMEBOLL tbldateprossimeboll;
    @XmlElement(name = "TBLDOCUMENTIPAGARE", nillable = true)
    protected TABLEDOCUMENTIPAGARE tbldocumentipagare;
    
    public TABLECONSUMIPROSSIMEBOLL getTBLCONSUMIPROSSIMEBOLL() {
        return this.tblconsumiprossimeboll;
    }
    
    public void setTBLCONSUMIPROSSIMEBOLL(final TABLECONSUMIPROSSIMEBOLL value) {
        this.tblconsumiprossimeboll = value;
    }
    
    public TABLEDATEPROSSIMEBOLL getTBLDATEPROSSIMEBOLL() {
        return this.tbldateprossimeboll;
    }
    
    public void setTBLDATEPROSSIMEBOLL(final TABLEDATEPROSSIMEBOLL value) {
        this.tbldateprossimeboll = value;
    }
    
    public TABLEDOCUMENTIPAGARE getTBLDOCUMENTIPAGARE() {
        return this.tbldocumentipagare;
    }
    
    public void setTBLDOCUMENTIPAGARE(final TABLEDOCUMENTIPAGARE value) {
        this.tbldocumentipagare = value;
    }
}
