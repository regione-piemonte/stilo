
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumClassificazioneStatoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumClassificazioneStatoType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="Attiva"/&gt;
 *     &lt;enumeration value="Ri-classificato"/&gt;
 *     &lt;enumeration value="Cancellato"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumClassificazioneStatoType")
@XmlEnum
public enum EnumClassificazioneStatoType {

    @XmlEnumValue("Attiva")
    ATTIVA("Attiva"),
    @XmlEnumValue("Ri-classificato")
    RI_CLASSIFICATO("Ri-classificato"),
    @XmlEnumValue("Cancellato")
    CANCELLATO("Cancellato");
    private final String value;

    EnumClassificazioneStatoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumClassificazioneStatoType fromValue(String v) {
        for (EnumClassificazioneStatoType c: EnumClassificazioneStatoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
