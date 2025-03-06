// 
// Decompiled by Procyon v0.5.36
// 

package alert.pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CONSUMOROSSIMABOLL", propOrder = { "consumoaccontoprevisto" })
public class CONSUMOROSSIMABOLL extends INFOPROSSIMABOLL
{
    @XmlElement(name = "CONSUMOACCONTOPREVISTO")
    protected Double consumoaccontoprevisto;
    
    public Double getCONSUMOACCONTOPREVISTO() {
        return this.consumoaccontoprevisto;
    }
    
    public void setCONSUMOACCONTOPREVISTO(final Double value) {
        this.consumoaccontoprevisto = value;
    }
}
