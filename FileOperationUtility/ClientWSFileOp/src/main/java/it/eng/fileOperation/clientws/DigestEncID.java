/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DigestEncID.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="DigestEncID"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="hex"/&gt;
 *     &lt;enumeration value="base64"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "DigestEncID")
@XmlEnum
public enum DigestEncID {

    @XmlEnumValue("hex")
    HEX("hex"),
    @XmlEnumValue("base64")
    BASE_64("base64");
    private final String value;

    DigestEncID(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DigestEncID fromValue(String v) {
        for (DigestEncID c: DigestEncID.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
