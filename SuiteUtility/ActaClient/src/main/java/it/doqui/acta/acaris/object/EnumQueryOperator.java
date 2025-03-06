
package it.doqui.acta.acaris.object;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumQueryOperator.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumQueryOperator"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="equals"/&gt;
 *     &lt;enumeration value="greaterThan"/&gt;
 *     &lt;enumeration value="lessThan"/&gt;
 *     &lt;enumeration value="greaterThanOrEqualTo"/&gt;
 *     &lt;enumeration value="lessThanOrEqualTo"/&gt;
 *     &lt;enumeration value="like"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumQueryOperator", namespace = "common.acaris.acta.doqui.it")
@XmlEnum
public enum EnumQueryOperator {

    @XmlEnumValue("equals")
    EQUALS("equals"),
    @XmlEnumValue("greaterThan")
    GREATER_THAN("greaterThan"),
    @XmlEnumValue("lessThan")
    LESS_THAN("lessThan"),
    @XmlEnumValue("greaterThanOrEqualTo")
    GREATER_THAN_OR_EQUAL_TO("greaterThanOrEqualTo"),
    @XmlEnumValue("lessThanOrEqualTo")
    LESS_THAN_OR_EQUAL_TO("lessThanOrEqualTo"),
    @XmlEnumValue("like")
    LIKE("like");
    private final String value;

    EnumQueryOperator(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumQueryOperator fromValue(String v) {
        for (EnumQueryOperator c: EnumQueryOperator.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
