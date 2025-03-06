
package it.doqui.acta.acaris.repository;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumTipoDocumentoType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumTipoDocumentoType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="Semplice"/&gt;
 *     &lt;enumeration value="Firmato"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumTipoDocumentoType")
@XmlEnum
public enum EnumTipoDocumentoType {

    @XmlEnumValue("Semplice")
    SEMPLICE("Semplice"),
    @XmlEnumValue("Firmato")
    FIRMATO("Firmato");
    private final String value;

    EnumTipoDocumentoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumTipoDocumentoType fromValue(String v) {
        for (EnumTipoDocumentoType c: EnumTipoDocumentoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
