// 
// Decompiled by Procyon v0.5.36
// 

package org.datacontract.schemas._2004._07.pweb_be_pora_types;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomAttribute", propOrder = { "key", "value" })
public class CustomAttribute
{
    @XmlElement(name = "Key", nillable = true)
    protected String key;
    @XmlElement(name = "Value", nillable = true)
    protected String value;
    
    public String getKey() {
        return this.key;
    }
    
    public void setKey(final String value) {
        this.key = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        this.value = value;
    }
}
