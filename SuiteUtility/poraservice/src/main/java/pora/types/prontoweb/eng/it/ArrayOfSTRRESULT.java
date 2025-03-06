// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSTRRESULT", propOrder = { "strresult" })
public class ArrayOfSTRRESULT
{
    @XmlElement(name = "STRRESULT", nillable = true)
    protected List<STRRESULT> strresult;
    
    public List<STRRESULT> getSTRRESULT() {
        if (this.strresult == null) {
            this.strresult = new ArrayList<STRRESULT>();
        }
        return this.strresult;
    }
}
