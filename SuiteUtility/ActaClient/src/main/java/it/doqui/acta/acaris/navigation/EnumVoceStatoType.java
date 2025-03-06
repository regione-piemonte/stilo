
package it.doqui.acta.acaris.navigation;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumVoceStatoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumVoceStatoType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="Bozza"/&gt;
 *     &lt;enumeration value="Preattiva"/&gt;
 *     &lt;enumeration value="Attiva"/&gt;
 *     &lt;enumeration value="Disattiva"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumVoceStatoType")
@XmlEnum
public enum EnumVoceStatoType {

    @XmlEnumValue("Bozza")
    BOZZA("Bozza"),
    @XmlEnumValue("Preattiva")
    PREATTIVA("Preattiva"),
    @XmlEnumValue("Attiva")
    ATTIVA("Attiva"),
    @XmlEnumValue("Disattiva")
    DISATTIVA("Disattiva");
    private final String value;

    EnumVoceStatoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumVoceStatoType fromValue(String v) {
        for (EnumVoceStatoType c: EnumVoceStatoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
