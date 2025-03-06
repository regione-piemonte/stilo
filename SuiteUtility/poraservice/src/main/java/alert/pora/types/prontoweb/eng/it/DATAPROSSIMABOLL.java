// 
// Decompiled by Procyon v0.5.36
// 

package alert.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATAPROSSIMABOLL", propOrder = { "dataprevista" })
public class DATAPROSSIMABOLL extends INFOPROSSIMABOLL
{
    @XmlElement(name = "DATAPREVISTA")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataprevista;
    
    public XMLGregorianCalendar getDATAPREVISTA() {
        return this.dataprevista;
    }
    
    public void setDATAPREVISTA(final XMLGregorianCalendar value) {
        this.dataprevista = value;
    }
}
