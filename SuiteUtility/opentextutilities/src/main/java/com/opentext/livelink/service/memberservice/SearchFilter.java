
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SearchFilter.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="SearchFilter">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ANY"/>
 *     &lt;enumeration value="DOMAIN"/>
 *     &lt;enumeration value="GROUP"/>
 *     &lt;enumeration value="USER"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SearchFilter")
@XmlEnum
public enum SearchFilter {

    ANY,
    DOMAIN,
    GROUP,
    USER;

    public String value() {
        return name();
    }

    public static SearchFilter fromValue(String v) {
        return valueOf(v);
    }

}
