
package it.doqui.acta.acaris.object;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumPropertyFilter.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumPropertyFilter"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="none"/&gt;
 *     &lt;enumeration value="all"/&gt;
 *     &lt;enumeration value="list"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumPropertyFilter", namespace = "common.acaris.acta.doqui.it")
@XmlEnum
public enum EnumPropertyFilter {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("all")
    ALL("all"),
    @XmlEnumValue("list")
    LIST("list");
    private final String value;

    EnumPropertyFilter(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumPropertyFilter fromValue(String v) {
        for (EnumPropertyFilter c: EnumPropertyFilter.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
