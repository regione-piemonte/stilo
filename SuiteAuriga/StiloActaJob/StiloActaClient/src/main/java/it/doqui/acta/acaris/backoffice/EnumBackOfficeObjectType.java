/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumBackOfficeObjectType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumBackOfficeObjectType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="EntePropertiesType"/&gt;
 *     &lt;enumeration value="GruppoAOOPropertiesType"/&gt;
 *     &lt;enumeration value="AOOPropertiesType"/&gt;
 *     &lt;enumeration value="StrutturaPropertiesType"/&gt;
 *     &lt;enumeration value="NodoPropertiesType"/&gt;
 *     &lt;enumeration value="UtentePropertiesType"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumBackOfficeObjectType")
@XmlEnum
public enum EnumBackOfficeObjectType {

    @XmlEnumValue("EntePropertiesType")
    ENTE_PROPERTIES_TYPE("EntePropertiesType"),
    @XmlEnumValue("GruppoAOOPropertiesType")
    GRUPPO_AOO_PROPERTIES_TYPE("GruppoAOOPropertiesType"),
    @XmlEnumValue("AOOPropertiesType")
    AOO_PROPERTIES_TYPE("AOOPropertiesType"),
    @XmlEnumValue("StrutturaPropertiesType")
    STRUTTURA_PROPERTIES_TYPE("StrutturaPropertiesType"),
    @XmlEnumValue("NodoPropertiesType")
    NODO_PROPERTIES_TYPE("NodoPropertiesType"),
    @XmlEnumValue("UtentePropertiesType")
    UTENTE_PROPERTIES_TYPE("UtentePropertiesType");
    private final String value;

    EnumBackOfficeObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumBackOfficeObjectType fromValue(String v) {
        for (EnumBackOfficeObjectType c: EnumBackOfficeObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
