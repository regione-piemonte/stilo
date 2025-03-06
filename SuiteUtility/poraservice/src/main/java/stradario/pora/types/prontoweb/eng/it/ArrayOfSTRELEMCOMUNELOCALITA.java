// 
// Decompiled by Procyon v0.5.36
// 

package stradario.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSTRELEMCOMUNELOCALITA", propOrder = { "strelemcomunelocalita" })
public class ArrayOfSTRELEMCOMUNELOCALITA
{
    @XmlElement(name = "STRELEMCOMUNELOCALITA", nillable = true)
    protected List<STRELEMCOMUNELOCALITA> strelemcomunelocalita;
    
    public List<STRELEMCOMUNELOCALITA> getSTRELEMCOMUNELOCALITA() {
        if (this.strelemcomunelocalita == null) {
            this.strelemcomunelocalita = new ArrayList<STRELEMCOMUNELOCALITA>();
        }
        return this.strelemcomunelocalita;
    }
}
