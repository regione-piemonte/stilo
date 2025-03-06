
package com.opentext.livelink.service.memberservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SearchColumn.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="SearchColumn">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FIRSTNAME"/>
 *     &lt;enumeration value="LASTNAME"/>
 *     &lt;enumeration value="MAILADDRESS"/>
 *     &lt;enumeration value="NAME"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SearchColumn")
@XmlEnum
public enum SearchColumn {

    FIRSTNAME,
    LASTNAME,
    MAILADDRESS,
    NAME;

    public String value() {
        return name();
    }

    public static SearchColumn fromValue(String v) {
        return valueOf(v);
    }

}
