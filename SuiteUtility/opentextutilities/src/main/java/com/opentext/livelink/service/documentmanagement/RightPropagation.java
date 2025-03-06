
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per RightPropagation.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="RightPropagation">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ChildrenOnly"/>
 *     &lt;enumeration value="TargetAndChildren"/>
 *     &lt;enumeration value="TargetOnly"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RightPropagation")
@XmlEnum
public enum RightPropagation {

    @XmlEnumValue("ChildrenOnly")
    CHILDREN_ONLY("ChildrenOnly"),
    @XmlEnumValue("TargetAndChildren")
    TARGET_AND_CHILDREN("TargetAndChildren"),
    @XmlEnumValue("TargetOnly")
    TARGET_ONLY("TargetOnly");
    private final String value;

    RightPropagation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RightPropagation fromValue(String v) {
        for (RightPropagation c: RightPropagation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
