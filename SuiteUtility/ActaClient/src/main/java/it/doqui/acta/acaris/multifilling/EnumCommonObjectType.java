
package it.doqui.acta.acaris.multifilling;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumCommonObjectType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumCommonObjectType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="AnnotazioniPropertiesType"/&gt;
 *     &lt;enumeration value="ProtocolloPropertiesType"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumCommonObjectType", namespace = "common.acaris.acta.doqui.it")
@XmlEnum
public enum EnumCommonObjectType {

    @XmlEnumValue("AnnotazioniPropertiesType")
    ANNOTAZIONI_PROPERTIES_TYPE("AnnotazioniPropertiesType"),
    @XmlEnumValue("ProtocolloPropertiesType")
    PROTOCOLLO_PROPERTIES_TYPE("ProtocolloPropertiesType");
    private final String value;

    EnumCommonObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumCommonObjectType fromValue(String v) {
        for (EnumCommonObjectType c: EnumCommonObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
