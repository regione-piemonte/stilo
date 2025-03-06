// 
// Decompiled by Procyon v0.5.36
// 

package multicompany.pora.types.prontoweb.eng.it;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfINFOMULTICOMPANY", propOrder = { "infomulticompany" })
public class ArrayOfINFOMULTICOMPANY
{
    @XmlElement(name = "INFOMULTICOMPANY", nillable = true)
    protected List<INFOMULTICOMPANY> infomulticompany;
    
    public List<INFOMULTICOMPANY> getINFOMULTICOMPANY() {
        if (this.infomulticompany == null) {
            this.infomulticompany = new ArrayList<INFOMULTICOMPANY>();
        }
        return this.infomulticompany;
    }
}
