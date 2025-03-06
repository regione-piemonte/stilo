
package it.doqui.acta.acaris.multifilling;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumFirmaDigitaleType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumFirmaDigitaleType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="NA"/&gt;
 *     &lt;enumeration value="MarcaDetached"/&gt;
 *     &lt;enumeration value="Enveloped"/&gt;
 *     &lt;enumeration value="EnvelopedMultipart"/&gt;
 *     &lt;enumeration value="FirmaDetached"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumFirmaDigitaleType")
@XmlEnum
public enum EnumFirmaDigitaleType {

    NA("NA"),
    @XmlEnumValue("MarcaDetached")
    MARCA_DETACHED("MarcaDetached"),
    @XmlEnumValue("Enveloped")
    ENVELOPED("Enveloped"),
    @XmlEnumValue("EnvelopedMultipart")
    ENVELOPED_MULTIPART("EnvelopedMultipart"),
    @XmlEnumValue("FirmaDetached")
    FIRMA_DETACHED("FirmaDetached");
    private final String value;

    EnumFirmaDigitaleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumFirmaDigitaleType fromValue(String v) {
        for (EnumFirmaDigitaleType c: EnumFirmaDigitaleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
