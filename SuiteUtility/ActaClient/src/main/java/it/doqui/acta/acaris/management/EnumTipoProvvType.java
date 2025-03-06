
package it.doqui.acta.acaris.management;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumTipoProvvType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumTipoProvvType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="ProvvedimentoOriginatore"/&gt;
 *     &lt;enumeration value="ProvvedimentoDiModifica"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumTipoProvvType", namespace = "management.acaris.acta.doqui.it")
@XmlEnum
public enum EnumTipoProvvType {

    @XmlEnumValue("ProvvedimentoOriginatore")
    PROVVEDIMENTO_ORIGINATORE("ProvvedimentoOriginatore"),
    @XmlEnumValue("ProvvedimentoDiModifica")
    PROVVEDIMENTO_DI_MODIFICA("ProvvedimentoDiModifica");
    private final String value;

    EnumTipoProvvType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumTipoProvvType fromValue(String v) {
        for (EnumTipoProvvType c: EnumTipoProvvType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
