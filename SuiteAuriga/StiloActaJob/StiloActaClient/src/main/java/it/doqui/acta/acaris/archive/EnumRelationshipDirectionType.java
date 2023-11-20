/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumRelationshipDirectionType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumRelationshipDirectionType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="source"/&gt;
 *     &lt;enumeration value="target"/&gt;
 *     &lt;enumeration value="either"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumRelationshipDirectionType")
@XmlEnum
public enum EnumRelationshipDirectionType {

    @XmlEnumValue("source")
    SOURCE("source"),
    @XmlEnumValue("target")
    TARGET("target"),
    @XmlEnumValue("either")
    EITHER("either");
    private final String value;

    EnumRelationshipDirectionType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumRelationshipDirectionType fromValue(String v) {
        for (EnumRelationshipDirectionType c: EnumRelationshipDirectionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
