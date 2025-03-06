
package it.doqui.acta.acaris.repository;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumVolumeStatoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumVolumeStatoType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="Aperto"/&gt;
 *     &lt;enumeration value="ChiusoInCorrente"/&gt;
 *     &lt;enumeration value="ChiusoInDeposito"/&gt;
 *     &lt;enumeration value="Scartato"/&gt;
 *     &lt;enumeration value="Cancellato"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumVolumeStatoType")
@XmlEnum
public enum EnumVolumeStatoType {

    @XmlEnumValue("Aperto")
    APERTO("Aperto"),
    @XmlEnumValue("ChiusoInCorrente")
    CHIUSO_IN_CORRENTE("ChiusoInCorrente"),
    @XmlEnumValue("ChiusoInDeposito")
    CHIUSO_IN_DEPOSITO("ChiusoInDeposito"),
    @XmlEnumValue("Scartato")
    SCARTATO("Scartato"),
    @XmlEnumValue("Cancellato")
    CANCELLATO("Cancellato");
    private final String value;

    EnumVolumeStatoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumVolumeStatoType fromValue(String v) {
        for (EnumVolumeStatoType c: EnumVolumeStatoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
