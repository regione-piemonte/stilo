// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlElement;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PWEBTABLEFILTER", propOrder = { "elencocodicifornitura", "filterexpression", "pagenumber", "pagesize", "sortcolumnname", "sortdirection" })
public class PWEBTABLEFILTER
{
    @XmlElement(name = "ELENCOCODICIFORNITURA", nillable = true)
    protected ArrayOfstring elencocodicifornitura;
    @XmlElement(name = "FILTEREXPRESSION", nillable = true)
    protected String filterexpression;
    @XmlElement(name = "PAGENUMBER")
    protected Integer pagenumber;
    @XmlElement(name = "PAGESIZE")
    protected Integer pagesize;
    @XmlElement(name = "SORTCOLUMNNAME", nillable = true)
    protected String sortcolumnname;
    @XmlElement(name = "SORTDIRECTION")
    @XmlSchemaType(name = "string")
    protected SORTDIRECTION sortdirection;
    
    public ArrayOfstring getELENCOCODICIFORNITURA() {
        return this.elencocodicifornitura;
    }
    
    public void setELENCOCODICIFORNITURA(final ArrayOfstring value) {
        this.elencocodicifornitura = value;
    }
    
    public String getFILTEREXPRESSION() {
        return this.filterexpression;
    }
    
    public void setFILTEREXPRESSION(final String value) {
        this.filterexpression = value;
    }
    
    public Integer getPAGENUMBER() {
        return this.pagenumber;
    }
    
    public void setPAGENUMBER(final Integer value) {
        this.pagenumber = value;
    }
    
    public Integer getPAGESIZE() {
        return this.pagesize;
    }
    
    public void setPAGESIZE(final Integer value) {
        this.pagesize = value;
    }
    
    public String getSORTCOLUMNNAME() {
        return this.sortcolumnname;
    }
    
    public void setSORTCOLUMNNAME(final String value) {
        this.sortcolumnname = value;
    }
    
    public SORTDIRECTION getSORTDIRECTION() {
        return this.sortdirection;
    }
    
    public void setSORTDIRECTION(final SORTDIRECTION value) {
        this.sortdirection = value;
    }
}
