// 
// Decompiled by Procyon v0.5.36
// 

package autolettura.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PERIODO", propOrder = { "datafine", "datainizio" })
public class PERIODO
{
    @XmlElement(name = "DATAFINE")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datafine;
    @XmlElement(name = "DATAINIZIO", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datainizio;
    
    public XMLGregorianCalendar getDATAFINE() {
        return this.datafine;
    }
    
    public void setDATAFINE(final XMLGregorianCalendar value) {
        this.datafine = value;
    }
    
    public XMLGregorianCalendar getDATAINIZIO() {
        return this.datainizio;
    }
    
    public void setDATAINIZIO(final XMLGregorianCalendar value) {
        this.datainizio = value;
    }
}
