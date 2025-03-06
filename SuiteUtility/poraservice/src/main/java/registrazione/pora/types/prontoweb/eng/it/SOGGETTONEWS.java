// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import multicompany.pora.types.prontoweb.eng.it.INFOMULTICOMPANY;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SOGGETTONEWS", propOrder = { "codlingua", "dataadesionenwesletter" })
@XmlSeeAlso({ SOGGETTONEWSEXT.class })
public class SOGGETTONEWS extends INFOMULTICOMPANY
{
    @XmlElement(name = "CODLINGUA", nillable = true)
    protected String codlingua;
    @XmlElement(name = "DATAADESIONENWESLETTER")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataadesionenwesletter;
    
    public String getCODLINGUA() {
        return this.codlingua;
    }
    
    public void setCODLINGUA(final String value) {
        this.codlingua = value;
    }
    
    public XMLGregorianCalendar getDATAADESIONENWESLETTER() {
        return this.dataadesionenwesletter;
    }
    
    public void setDATAADESIONENWESLETTER(final XMLGregorianCalendar value) {
        this.dataadesionenwesletter = value;
    }
}
