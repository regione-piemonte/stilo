
package it.doqui.acta.acaris.repository;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumEsitoPassoRapportoVerificaType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumEsitoPassoRapportoVerificaType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="NonOk"/&gt;
 *     &lt;enumeration value="Ok"/&gt;
 *     &lt;enumeration value="DaVerificare"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumEsitoPassoRapportoVerificaType")
@XmlEnum
public enum EnumEsitoPassoRapportoVerificaType {

    @XmlEnumValue("NonOk")
    NON_OK("NonOk"),
    @XmlEnumValue("Ok")
    OK("Ok"),
    @XmlEnumValue("DaVerificare")
    DA_VERIFICARE("DaVerificare");
    private final String value;

    EnumEsitoPassoRapportoVerificaType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumEsitoPassoRapportoVerificaType fromValue(String v) {
        for (EnumEsitoPassoRapportoVerificaType c: EnumEsitoPassoRapportoVerificaType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
