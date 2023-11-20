/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumSerieFascicoliStatoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumSerieFascicoliStatoType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="Attiva"/&gt;
 *     &lt;enumeration value="Disattiva"/&gt;
 *     &lt;enumeration value="Congelata"/&gt;
 *     &lt;enumeration value="ChiusaInCorrente"/&gt;
 *     &lt;enumeration value="ChiusaInDeposito"/&gt;
 *     &lt;enumeration value="Cancellata"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumSerieFascicoliStatoType")
@XmlEnum
public enum EnumSerieFascicoliStatoType {

    @XmlEnumValue("Attiva")
    ATTIVA("Attiva"),
    @XmlEnumValue("Disattiva")
    DISATTIVA("Disattiva"),
    @XmlEnumValue("Congelata")
    CONGELATA("Congelata"),
    @XmlEnumValue("ChiusaInCorrente")
    CHIUSA_IN_CORRENTE("ChiusaInCorrente"),
    @XmlEnumValue("ChiusaInDeposito")
    CHIUSA_IN_DEPOSITO("ChiusaInDeposito"),
    @XmlEnumValue("Cancellata")
    CANCELLATA("Cancellata");
    private final String value;

    EnumSerieFascicoliStatoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumSerieFascicoliStatoType fromValue(String v) {
        for (EnumSerieFascicoliStatoType c: EnumSerieFascicoliStatoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
