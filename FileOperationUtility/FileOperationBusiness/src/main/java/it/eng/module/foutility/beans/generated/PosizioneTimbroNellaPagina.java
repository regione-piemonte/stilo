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
 * <p>Java class for posizioneTimbroNellaPagina.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="posizioneTimbroNellaPagina">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="altoDx"/>
 *     &lt;enumeration value="altoSn"/>
 *     &lt;enumeration value="bassoDx"/>
 *     &lt;enumeration value="bassoSn"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "posizioneTimbroNellaPagina", namespace = "it.eng.fileoperation.ws.timbro")
@XmlEnum
public enum PosizioneTimbroNellaPagina {

    @XmlEnumValue("altoDx")
    ALTO_DX("altoDx"),
    @XmlEnumValue("altoSn")
    ALTO_SN("altoSn"),
    @XmlEnumValue("bassoDx")
    BASSO_DX("bassoDx"),
    @XmlEnumValue("bassoSn")
    BASSO_SN("bassoSn");
    private final String value;

    PosizioneTimbroNellaPagina(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PosizioneTimbroNellaPagina fromValue(String v) {
        for (PosizioneTimbroNellaPagina c: PosizioneTimbroNellaPagina.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}