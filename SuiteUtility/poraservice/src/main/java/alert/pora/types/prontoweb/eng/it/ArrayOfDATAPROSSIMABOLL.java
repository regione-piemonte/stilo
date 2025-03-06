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
@XmlType(name = "ArrayOfDATAPROSSIMABOLL", propOrder = { "dataprossimaboll" })
public class ArrayOfDATAPROSSIMABOLL
{
    @XmlElement(name = "DATAPROSSIMABOLL", nillable = true)
    protected List<DATAPROSSIMABOLL> dataprossimaboll;
    
    public List<DATAPROSSIMABOLL> getDATAPROSSIMABOLL() {
        if (this.dataprossimaboll == null) {
            this.dataprossimaboll = new ArrayList<DATAPROSSIMABOLL>();
        }
        return this.dataprossimaboll;
    }
}
