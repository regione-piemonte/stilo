/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per EnvelopeType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="EnvelopeType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CAdES_BES"/&gt;
 *     &lt;enumeration value="CAdES_T"/&gt;
 *     &lt;enumeration value="CAdES_C"/&gt;
 *     &lt;enumeration value="TSD"/&gt;
 *     &lt;enumeration value="M7M"/&gt;
 *     &lt;enumeration value="P7M"/&gt;
 *     &lt;enumeration value="TSR"/&gt;
 *     &lt;enumeration value="PAdES"/&gt;
 *     &lt;enumeration value="XAdES_XL"/&gt;
 *     &lt;enumeration value="CAdES_X_Long"/&gt;
 *     &lt;enumeration value="XAdES"/&gt;
 *     &lt;enumeration value="XAdES_T"/&gt;
 *     &lt;enumeration value="XAdES_C"/&gt;
 *     &lt;enumeration value="XAdES_X"/&gt;
 *     &lt;enumeration value="XAdES_BES"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
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
