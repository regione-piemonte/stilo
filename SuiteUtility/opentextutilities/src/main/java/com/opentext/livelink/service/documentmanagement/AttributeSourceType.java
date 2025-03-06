
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AttributeSourceType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="AttributeSourceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DESTINATION"/>
 *     &lt;enumeration value="MERGE"/>
 *     &lt;enumeration value="ORIGINAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AttributeSourceType")
@XmlEnum
public enum AttributeSourceType {

    DESTINATION,
    MERGE,
    ORIGINAL;

    public String value() {
        return name();
    }

    public static AttributeSourceType fromValue(String v) {
        return valueOf(v);
    }

}
