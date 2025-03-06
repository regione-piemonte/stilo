
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumTipoDocumentoArchivistico.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumTipoDocumentoArchivistico"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="DocumentoSemplice"/&gt;
 *     &lt;enumeration value="DocumentoDB"/&gt;
 *     &lt;enumeration value="DocumentoRegistro"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumTipoDocumentoArchivistico", namespace = "documentservice.acaris.acta.doqui.it")
@XmlEnum
public enum EnumTipoDocumentoArchivistico {

    @XmlEnumValue("DocumentoSemplice")
    DOCUMENTO_SEMPLICE("DocumentoSemplice"),
    @XmlEnumValue("DocumentoDB")
    DOCUMENTO_DB("DocumentoDB"),
    @XmlEnumValue("DocumentoRegistro")
    DOCUMENTO_REGISTRO("DocumentoRegistro");
    private final String value;

    EnumTipoDocumentoArchivistico(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumTipoDocumentoArchivistico fromValue(String v) {
        for (EnumTipoDocumentoArchivistico c: EnumTipoDocumentoArchivistico.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
