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
@XmlType(name = "ArrayOfSTRRESULTEXT", propOrder = { "strresultext" })
public class ArrayOfSTRRESULTEXT
{
    @XmlElement(name = "STRRESULTEXT", nillable = true)
    protected List<STRRESULTEXT> strresultext;
    
    public List<STRRESULTEXT> getSTRRESULTEXT() {
        if (this.strresultext == null) {
            this.strresultext = new ArrayList<STRRESULTEXT>();
        }
        return this.strresultext;
    }
}
