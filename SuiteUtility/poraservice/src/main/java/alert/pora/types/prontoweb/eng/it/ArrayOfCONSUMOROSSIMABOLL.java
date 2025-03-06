// 
// Decompiled by Procyon v0.5.36
// 

package alert.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCONSUMOROSSIMABOLL", propOrder = { "consumorossimaboll" })
public class ArrayOfCONSUMOROSSIMABOLL
{
    @XmlElement(name = "CONSUMOROSSIMABOLL", nillable = true)
    protected List<CONSUMOROSSIMABOLL> consumorossimaboll;
    
    public List<CONSUMOROSSIMABOLL> getCONSUMOROSSIMABOLL() {
        if (this.consumorossimaboll == null) {
            this.consumorossimaboll = new ArrayList<CONSUMOROSSIMABOLL>();
        }
        return this.consumorossimaboll;
    }
}
