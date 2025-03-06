
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumTipoDocumentoCreazione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumTipoDocumentoCreazione"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="documentoArchivistico.semplice"/&gt;
 *     &lt;enumeration value="documentoArchivistico.db"/&gt;
 *     &lt;enumeration value="documentoArchivistico.registro"/&gt;
 *     &lt;enumeration value="documentoFisico"/&gt;
 *     &lt;enumeration value="contenutoFisico"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumTipoDocumentoCreazione", namespace = "documentservice.acaris.acta.doqui.it")
@XmlEnum
public enum EnumTipoDocumentoCreazione {

    @XmlEnumValue("documentoArchivistico.semplice")
    DOCUMENTO_ARCHIVISTICO_SEMPLICE("documentoArchivistico.semplice"),
    @XmlEnumValue("documentoArchivistico.db")
    DOCUMENTO_ARCHIVISTICO_DB("documentoArchivistico.db"),
    @XmlEnumValue("documentoArchivistico.registro")
    DOCUMENTO_ARCHIVISTICO_REGISTRO("documentoArchivistico.registro"),
    @XmlEnumValue("documentoFisico")
    DOCUMENTO_FISICO("documentoFisico"),
    @XmlEnumValue("contenutoFisico")
    CONTENUTO_FISICO("contenutoFisico");
    private final String value;

    EnumTipoDocumentoCreazione(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumTipoDocumentoCreazione fromValue(String v) {
        for (EnumTipoDocumentoCreazione c: EnumTipoDocumentoCreazione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
