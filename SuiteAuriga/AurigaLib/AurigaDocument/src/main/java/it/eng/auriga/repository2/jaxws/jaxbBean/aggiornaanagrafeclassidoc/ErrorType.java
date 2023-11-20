/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.06.09 alle 02:55:53 PM CEST 
//


package it.eng.auriga.repository2.jaxws.jaxbBean.aggiornaanagrafeclassidoc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ErrorType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="ErrorType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="GAE-999"/>
 *     &lt;enumeration value="GAE-001"/>
 *     &lt;enumeration value="GAE-002"/>
 *     &lt;enumeration value="GAE-003"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ErrorType")
@XmlEnum
public enum ErrorType {


    /**
     * Errore generico
     * 
     */
    @XmlEnumValue("GAE-999")
    GAE_999("GAE-999"),

    /**
     * Dati obbligatori mancanti
     * 
     */
    @XmlEnumValue("GAE-001")
    GAE_001("GAE-001"),

    /**
     * Dati non coerenti
     * 
     */
    @XmlEnumValue("GAE-002")
    GAE_002("GAE-002"),

    /**
     * Modifica non consentita
     * 
     */
    @XmlEnumValue("GAE-003")
    GAE_003("GAE-003");
    private final String value;

    ErrorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ErrorType fromValue(String v) {
        for (ErrorType c: ErrorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
