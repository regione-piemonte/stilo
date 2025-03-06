
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumBackOfficeNavigationPathType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumBackOfficeNavigationPathType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="aoo"/&gt;
 *     &lt;enumeration value="strutture"/&gt;
 *     &lt;enumeration value="utenti"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumBackOfficeNavigationPathType", namespace = "backoffice.acaris.acta.doqui.it")
@XmlEnum
public enum EnumBackOfficeNavigationPathType {

    @XmlEnumValue("aoo")
    AOO("aoo"),
    @XmlEnumValue("strutture")
    STRUTTURE("strutture"),
    @XmlEnumValue("utenti")
    UTENTI("utenti");
    private final String value;

    EnumBackOfficeNavigationPathType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumBackOfficeNavigationPathType fromValue(String v) {
        for (EnumBackOfficeNavigationPathType c: EnumBackOfficeNavigationPathType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
