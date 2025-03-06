// 
// Decompiled by Procyon v0.5.36
// 

package org.datacontract.schemas._2004._07.pweb_be_pora_types;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCustomAttribute", propOrder = { "customAttribute" })
public class ArrayOfCustomAttribute
{
    @XmlElement(name = "CustomAttribute", nillable = true)
    protected List<CustomAttribute> customAttribute;
    
    public List<CustomAttribute> getCustomAttribute() {
        if (this.customAttribute == null) {
            this.customAttribute = new ArrayList<CustomAttribute>();
        }
        return this.customAttribute;
    }
}
