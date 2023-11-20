/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2018.09.19 alle 10:17:30 AM CEST 
//


package it.eng.auriga.repository2.jaxws.jaxbBean.prenotaappuntamento;

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
 *     &lt;enumeration value="AUR-001"/>
 *     &lt;enumeration value="AUR-002"/>
 *     &lt;enumeration value="AUR-003"/>
 *     &lt;enumeration value="AUR-004"/>
 *     &lt;enumeration value="AUR-005"/>
 *     &lt;enumeration value="AUR-006"/>
 *     &lt;enumeration value="AUR-999"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ErrorType")
@XmlEnum
public enum ErrorType {


    /**
     * Protocollo di richiesta inesistente
     * 
     */
    @XmlEnumValue("AUR-001")
    AUR_001("AUR-001"),

    /**
     * Richiesta non ancora evasa dall'ufficio: appuntamento non prenotabile
     * 
     */
    @XmlEnumValue("AUR-002")
    AUR_002("AUR-002"),

    /**
     * Richiesta con appuntamento scaduto: nuovo appuntamento non ancora prenotabile
     * 
     */
    @XmlEnumValue("AUR-003")
    AUR_003("AUR-003"),

    /**
     * L'appuntamento da annullare non risulta
     * 
     */
    @XmlEnumValue("AUR-004")
    AUR_004("AUR-004"),

    /**
     * Appuntamento già associato ad altra richiesta
     * 
     */
    @XmlEnumValue("AUR-005")
    AUR_005("AUR-005"),

    /**
     * Dati mancanti e/o erroneamente valorizzati
     * 
     */
    @XmlEnumValue("AUR-006")
    AUR_006("AUR-006"),

    /**
     * Errore generico
     * 
     */
    @XmlEnumValue("AUR-999")
    AUR_999("AUR-999");
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
