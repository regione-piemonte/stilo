
package it.doqui.acta.acaris.multifilling;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumFascicoloTemporaneoStatoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumFascicoloTemporaneoStatoType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="Attivo"/&gt;
 *     &lt;enumeration value="Disattivo"/&gt;
 *     &lt;enumeration value="Congelato"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumFascicoloTemporaneoStatoType")
@XmlEnum
public enum EnumFascicoloTemporaneoStatoType {

    @XmlEnumValue("Attivo")
    ATTIVO("Attivo"),
    @XmlEnumValue("Disattivo")
    DISATTIVO("Disattivo"),
    @XmlEnumValue("Congelato")
    CONGELATO("Congelato");
    private final String value;

    EnumFascicoloTemporaneoStatoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumFascicoloTemporaneoStatoType fromValue(String v) {
        for (EnumFascicoloTemporaneoStatoType c: EnumFascicoloTemporaneoStatoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
