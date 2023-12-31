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
 * <p>Java class for EnvelopeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="EnvelopeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CAdES_BES"/>
 *     &lt;enumeration value="CAdES_T"/>
 *     &lt;enumeration value="CAdES_C"/>
 *     &lt;enumeration value="TSD"/>
 *     &lt;enumeration value="M7M"/>
 *     &lt;enumeration value="P7M"/>
 *     &lt;enumeration value="TSR"/>
 *     &lt;enumeration value="PAdES"/>
 *     &lt;enumeration value="XAdES_XL"/>
 *     &lt;enumeration value="CAdES_X_Long"/>
 *     &lt;enumeration value="XAdES"/>
 *     &lt;enumeration value="XAdES_T"/>
 *     &lt;enumeration value="XAdES_C"/>
 *     &lt;enumeration value="XAdES_X"/>
 *     &lt;enumeration value="XAdES_BES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "EnvelopeType", namespace = "it.eng.fileoperation.ws.base")
@XmlEnum
public enum EnvelopeType {

    @XmlEnumValue("CAdES_BES")
    C_AD_ES_BES("CAdES_BES"),
    @XmlEnumValue("CAdES_T")
    C_AD_ES_T("CAdES_T"),
    @XmlEnumValue("CAdES_C")
    C_AD_ES_C("CAdES_C"),
    TSD("TSD"),
    @XmlEnumValue("M7M")
    M_7_M("M7M"),
    @XmlEnumValue("P7M")
    P_7_M("P7M"),
    TSR("TSR"),
    @XmlEnumValue("PAdES")
    P_AD_ES("PAdES"),
    @XmlEnumValue("XAdES_XL")
    X_AD_ES_XL("XAdES_XL"),
    @XmlEnumValue("CAdES_X_Long")
    C_AD_ES_X_LONG("CAdES_X_Long"),
    @XmlEnumValue("XAdES")
    X_AD_ES("XAdES"),
    @XmlEnumValue("XAdES_T")
    X_AD_ES_T("XAdES_T"),
    @XmlEnumValue("XAdES_C")
    X_AD_ES_C("XAdES_C"),
    @XmlEnumValue("XAdES_X")
    X_AD_ES_X("XAdES_X"),
    @XmlEnumValue("XAdES_BES")
    X_AD_ES_BES("XAdES_BES");
    private final String value;

    EnvelopeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnvelopeType fromValue(String v) {
        for (EnvelopeType c: EnvelopeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
