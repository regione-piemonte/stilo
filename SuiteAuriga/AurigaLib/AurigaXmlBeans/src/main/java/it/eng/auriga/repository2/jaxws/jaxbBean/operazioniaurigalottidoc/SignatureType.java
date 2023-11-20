/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.06.06 alle 03:56:55 PM CEST 
//


package it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per SignatureType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="SignatureType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PAdES"/>
 *     &lt;enumeration value="CAdES"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SignatureType")
@XmlEnum
public enum SignatureType {

    @XmlEnumValue("PAdES")
    P_AD_ES("PAdES"),
    @XmlEnumValue("CAdES")
    C_AD_ES("CAdES");
    private final String value;

    SignatureType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SignatureType fromValue(String v) {
        for (SignatureType c: SignatureType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
