
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumTipoFirmaDigitaleType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumTipoFirmaDigitaleType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="FirmaSemplice"/&gt;
 *     &lt;enumeration value="FirmaMultiplaParallela"/&gt;
 *     &lt;enumeration value="FirmaMultiplaControfirma"/&gt;
 *     &lt;enumeration value="FirmaMultiplaCatena"/&gt;
 *     &lt;enumeration value="MarcaTemporale"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumTipoFirmaDigitaleType")
@XmlEnum
public enum EnumTipoFirmaDigitaleType {

    @XmlEnumValue("FirmaSemplice")
    FIRMA_SEMPLICE("FirmaSemplice"),
    @XmlEnumValue("FirmaMultiplaParallela")
    FIRMA_MULTIPLA_PARALLELA("FirmaMultiplaParallela"),
    @XmlEnumValue("FirmaMultiplaControfirma")
    FIRMA_MULTIPLA_CONTROFIRMA("FirmaMultiplaControfirma"),
    @XmlEnumValue("FirmaMultiplaCatena")
    FIRMA_MULTIPLA_CATENA("FirmaMultiplaCatena"),
    @XmlEnumValue("MarcaTemporale")
    MARCA_TEMPORALE("MarcaTemporale");
    private final String value;

    EnumTipoFirmaDigitaleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumTipoFirmaDigitaleType fromValue(String v) {
        for (EnumTipoFirmaDigitaleType c: EnumTipoFirmaDigitaleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
