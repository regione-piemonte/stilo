
package it.doqui.acta.acaris.multifilling;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumStrutturaDocCompostoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumStrutturaDocCompostoType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="NA"/&gt;
 *     &lt;enumeration value="DocumentoEngine"/&gt;
 *     &lt;enumeration value="DocumentoRendition"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumStrutturaDocCompostoType")
@XmlEnum
public enum EnumStrutturaDocCompostoType {

    NA("NA"),
    @XmlEnumValue("DocumentoEngine")
    DOCUMENTO_ENGINE("DocumentoEngine"),
    @XmlEnumValue("DocumentoRendition")
    DOCUMENTO_RENDITION("DocumentoRendition");
    private final String value;

    EnumStrutturaDocCompostoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumStrutturaDocCompostoType fromValue(String v) {
        for (EnumStrutturaDocCompostoType c: EnumStrutturaDocCompostoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
