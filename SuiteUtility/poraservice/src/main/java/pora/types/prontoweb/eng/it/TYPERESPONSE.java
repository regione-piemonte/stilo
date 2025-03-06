// 
// Decompiled by Procyon v0.5.36
// 

package pora.types.prontoweb.eng.it;

import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TYPERESPONSE")
@XmlEnum
public enum TYPERESPONSE
{
    @XmlEnumValue("Info")
    INFO("INFO", 0, "Info"), 
    @XmlEnumValue("Warning")
    WARNING("WARNING", 1, "Warning"), 
    @XmlEnumValue("Error")
    ERROR("ERROR", 2, "Error");
    
    private final String value;
    
    private TYPERESPONSE(final String name, final int ordinal, final String v) {
        this.value = v;
    }
    
    public String value() {
        return this.value;
    }
    
    public static TYPERESPONSE fromValue(final String v) {
        TYPERESPONSE[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final TYPERESPONSE c = values[i];
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
