
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SearchMatching.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="SearchMatching">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CONTAINS"/>
 *     &lt;enumeration value="ENDSWITH"/>
 *     &lt;enumeration value="SOUNDSLIKE"/>
 *     &lt;enumeration value="STARTSWITH"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SearchMatching")
@XmlEnum
public enum SearchMatching {

    CONTAINS,
    ENDSWITH,
    SOUNDSLIKE,
    STARTSWITH;

    public String value() {
        return name();
    }

    public static SearchMatching fromValue(String v) {
        return valueOf(v);
    }

}
