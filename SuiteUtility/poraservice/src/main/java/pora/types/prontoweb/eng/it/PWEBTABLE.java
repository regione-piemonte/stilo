// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlElement;
import autolettura.pora.types.prontoweb.eng.it.TABLELETTUREEXT;
import autolettura.pora.types.prontoweb.eng.it.TABLELETTURE;
import alert.pora.types.prontoweb.eng.it.TABLEDATEPROSSIMEBOLL;
import alert.pora.types.prontoweb.eng.it.TABLECONSUMIPROSSIMEBOLL;
import alert.pora.types.prontoweb.eng.it.TABLEDOCUMENTIPAGARE;
import estrattoconto.pora.types.prontoweb.eng.it.TABLEESTRATTOCONTO;
import lettureconsumi.pora.types.prontoweb.eng.it.TABLECURVEPRELIEVO;
import log.pora.types.prontoweb.eng.it.TABLELOGBE;
import richieste.pora.types.prontoweb.eng.it.ELENCORICHIESTE;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PWEBTABLE", propOrder = { "currentpagenumber", "filterexpression", "pagesize", "sortcolumnname", "sortdirection", "totalrecords" })
@XmlSeeAlso({ ELENCORICHIESTE.class, TABLELOGBE.class, TABLECURVEPRELIEVO.class, TABLEESTRATTOCONTO.class, TABLEDOCUMENTIPAGARE.class, TABLECONSUMIPROSSIMEBOLL.class, TABLEDATEPROSSIMEBOLL.class, TABLELETTURE.class, TABLELETTUREEXT.class, TABLEFORNITUREEXT.class, TABLEFORNITURE.class, TABLEDATISOGGETTO.class })
public class PWEBTABLE
{
    @XmlElement(name = "CURRENTPAGENUMBER")
    protected Integer currentpagenumber;
    @XmlElement(name = "FILTEREXPRESSION", nillable = true)
    protected String filterexpression;
    @XmlElement(name = "PAGESIZE")
    protected Integer pagesize;
    @XmlElement(name = "SORTCOLUMNNAME", nillable = true)
    protected String sortcolumnname;
    @XmlElement(name = "SORTDIRECTION")
    @XmlSchemaType(name = "string")
    protected SORTDIRECTION sortdirection;
    @XmlElement(name = "TOTALRECORDS")
    protected Integer totalrecords;
    
    public Integer getCURRENTPAGENUMBER() {
        return this.currentpagenumber;
    }
    
    public void setCURRENTPAGENUMBER(final Integer value) {
        this.currentpagenumber = value;
    }
    
    public String getFILTEREXPRESSION() {
        return this.filterexpression;
    }
    
    public void setFILTEREXPRESSION(final String value) {
        this.filterexpression = value;
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
    
    public Integer getTOTALRECORDS() {
        return this.totalrecords;
    }
    
    public void setTOTALRECORDS(final Integer value) {
        this.totalrecords = value;
    }
}
