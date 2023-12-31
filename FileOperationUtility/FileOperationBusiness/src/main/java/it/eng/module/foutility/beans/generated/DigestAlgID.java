/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.01.20 at 10:39:55 AM CET 
//


package it.eng.module.foutility.beans.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DigestAlgID.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DigestAlgID">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="crc-32"/>
 *     &lt;enumeration value="sha-1"/>
 *     &lt;enumeration value="sha-256"/>
 *     &lt;enumeration value="md5"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DigestAlgID")
@XmlEnum
public enum DigestAlgID {

    @XmlEnumValue("crc-32")
    CRC_32("crc-32"),
    @XmlEnumValue("sha-1")
    SHA_1("sha-1"),
    @XmlEnumValue("sha-256")
    SHA_256("sha-256"),
    @XmlEnumValue("md5")
    MD_5("md5");
    private final String value;

    DigestAlgID(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DigestAlgID fromValue(String v) {
        for (DigestAlgID c: DigestAlgID.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
