
package it.doqui.acta.acaris.multifiling;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumIncludeRelationshipsType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumIncludeRelationshipsType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="none"/&gt;
 *     &lt;enumeration value="source"/&gt;
 *     &lt;enumeration value="target"/&gt;
 *     &lt;enumeration value="both"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumIncludeRelationshipsType")
@XmlEnum
public enum EnumIncludeRelationshipsType {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("source")
    SOURCE("source"),
    @XmlEnumValue("target")
    TARGET("target"),
    @XmlEnumValue("both")
    BOTH("both");
    private final String value;

    EnumIncludeRelationshipsType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumIncludeRelationshipsType fromValue(String v) {
        for (EnumIncludeRelationshipsType c: EnumIncludeRelationshipsType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
