
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SearchScope.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="SearchScope">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GROUP"/>
 *     &lt;enumeration value="SYSTEM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SearchScope")
@XmlEnum
public enum SearchScope {

    GROUP,
    SYSTEM;

    public String value() {
        return name();
    }

    public static SearchScope fromValue(String v) {
        return valueOf(v);
    }

}
