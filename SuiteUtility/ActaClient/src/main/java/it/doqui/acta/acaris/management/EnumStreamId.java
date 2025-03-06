
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumStreamId.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumStreamId"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="primary"/&gt;
 *     &lt;enumeration value="signature"/&gt;
 *     &lt;enumeration value="timestamp"/&gt;
 *     &lt;enumeration value="renditionEngine"/&gt;
 *     &lt;enumeration value="renditionDocument"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumStreamId", namespace = "common.acaris.acta.doqui.it")
@XmlEnum
public enum EnumStreamId {

    @XmlEnumValue("primary")
    PRIMARY("primary"),
    @XmlEnumValue("signature")
    SIGNATURE("signature"),
    @XmlEnumValue("timestamp")
    TIMESTAMP("timestamp"),
    @XmlEnumValue("renditionEngine")
    RENDITION_ENGINE("renditionEngine"),
    @XmlEnumValue("renditionDocument")
    RENDITION_DOCUMENT("renditionDocument");
    private final String value;

    EnumStreamId(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumStreamId fromValue(String v) {
        for (EnumStreamId c: EnumStreamId.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
