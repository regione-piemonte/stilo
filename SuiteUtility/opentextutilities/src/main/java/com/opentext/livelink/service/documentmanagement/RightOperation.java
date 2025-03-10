
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RightOperation.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="RightOperation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Add"/>
 *     &lt;enumeration value="AddReplace"/>
 *     &lt;enumeration value="Delete"/>
 *     &lt;enumeration value="Replace"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RightOperation")
@XmlEnum
public enum RightOperation {

    @XmlEnumValue("Add")
    ADD("Add"),
    @XmlEnumValue("AddReplace")
    ADD_REPLACE("AddReplace"),
    @XmlEnumValue("Delete")
    DELETE("Delete"),
    @XmlEnumValue("Replace")
    REPLACE("Replace");
    private final String value;

    RightOperation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RightOperation fromValue(String v) {
        for (RightOperation c: RightOperation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
