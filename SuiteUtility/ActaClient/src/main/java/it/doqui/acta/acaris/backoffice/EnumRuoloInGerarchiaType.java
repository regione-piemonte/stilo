
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumRuoloInGerarchiaType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumRuoloInGerarchiaType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="parent"/&gt;
 *     &lt;enumeration value="child"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumRuoloInGerarchiaType", namespace = "backoffice.acaris.acta.doqui.it")
@XmlEnum
public enum EnumRuoloInGerarchiaType {

    @XmlEnumValue("parent")
    PARENT("parent"),
    @XmlEnumValue("child")
    CHILD("child");
    private final String value;

    EnumRuoloInGerarchiaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumRuoloInGerarchiaType fromValue(String v) {
        for (EnumRuoloInGerarchiaType c: EnumRuoloInGerarchiaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
