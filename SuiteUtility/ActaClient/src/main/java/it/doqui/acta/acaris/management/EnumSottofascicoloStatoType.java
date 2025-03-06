
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumSottofascicoloStatoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumSottofascicoloStatoType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="Aperto"/&gt;
 *     &lt;enumeration value="AttesaDiChiusura"/&gt;
 *     &lt;enumeration value="ChiusoInCorrente"/&gt;
 *     &lt;enumeration value="ChiusoInDeposito"/&gt;
 *     &lt;enumeration value="Congelato"/&gt;
 *     &lt;enumeration value="Spostato"/&gt;
 *     &lt;enumeration value="Scartato"/&gt;
 *     &lt;enumeration value="Cancellato"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumSottofascicoloStatoType")
@XmlEnum
public enum EnumSottofascicoloStatoType {

    @XmlEnumValue("Aperto")
    APERTO("Aperto"),
    @XmlEnumValue("AttesaDiChiusura")
    ATTESA_DI_CHIUSURA("AttesaDiChiusura"),
    @XmlEnumValue("ChiusoInCorrente")
    CHIUSO_IN_CORRENTE("ChiusoInCorrente"),
    @XmlEnumValue("ChiusoInDeposito")
    CHIUSO_IN_DEPOSITO("ChiusoInDeposito"),
    @XmlEnumValue("Congelato")
    CONGELATO("Congelato"),
    @XmlEnumValue("Spostato")
    SPOSTATO("Spostato"),
    @XmlEnumValue("Scartato")
    SCARTATO("Scartato"),
    @XmlEnumValue("Cancellato")
    CANCELLATO("Cancellato");
    private final String value;

    EnumSottofascicoloStatoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumSottofascicoloStatoType fromValue(String v) {
        for (EnumSottofascicoloStatoType c: EnumSottofascicoloStatoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
