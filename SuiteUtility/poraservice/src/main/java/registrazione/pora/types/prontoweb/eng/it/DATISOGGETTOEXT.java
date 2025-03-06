// 
// Decompiled by Procyon v0.5.36
// 

package registrazione.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import org.datacontract.schemas._2004._07.pweb_be_pora_types.ArrayOfCustomAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DATISOGGETTOEXT", propOrder = { "customAttributes" })
public class DATISOGGETTOEXT extends DATISOGGETTO
{
    @XmlElement(name = "CustomAttributes", nillable = true)
    protected ArrayOfCustomAttribute customAttributes;
    
    public ArrayOfCustomAttribute getCustomAttributes() {
        return this.customAttributes;
    }
    
    public void setCustomAttributes(final ArrayOfCustomAttribute value) {
        this.customAttributes = value;
    }
}
