
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumStepErrorAction.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumStepErrorAction"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="insert"/&gt;
 *     &lt;enumeration value="exception"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumStepErrorAction", namespace = "documentservice.acaris.acta.doqui.it")
@XmlEnum
public enum EnumStepErrorAction {

    @XmlEnumValue("insert")
    INSERT("insert"),
    @XmlEnumValue("exception")
    EXCEPTION("exception");
    private final String value;

    EnumStepErrorAction(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumStepErrorAction fromValue(String v) {
        for (EnumStepErrorAction c: EnumStepErrorAction.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
