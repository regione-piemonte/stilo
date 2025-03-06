// 
// Decompiled by Procyon v0.5.36
// 

package com.microsoft.schemas._2003._10.serialization.arrays;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfstring", propOrder = { "string" })
public class ArrayOfstring
{
    @XmlElement(nillable = true)
    protected List<String> string;
    
    public List<String> getString() {
        if (this.string == null) {
            this.string = new ArrayList<String>();
        }
        return this.string;
    }
}
