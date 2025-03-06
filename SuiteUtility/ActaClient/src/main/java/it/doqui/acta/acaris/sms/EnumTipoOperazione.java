
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumTipoOperazione.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumTipoOperazione"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="soloMetadati"/&gt;
 *     &lt;enumeration value="elettronico"/&gt;
 *     &lt;enumeration value="aggiuntaDocumentoFisico"/&gt;
 *     &lt;enumeration value="aggiuntaContenutoFisico"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumTipoOperazione", namespace = "documentservice.acaris.acta.doqui.it")
@XmlEnum
public enum EnumTipoOperazione {

    @XmlEnumValue("soloMetadati")
    SOLO_METADATI("soloMetadati"),
    @XmlEnumValue("elettronico")
    ELETTRONICO("elettronico"),
    @XmlEnumValue("aggiuntaDocumentoFisico")
    AGGIUNTA_DOCUMENTO_FISICO("aggiuntaDocumentoFisico"),
    @XmlEnumValue("aggiuntaContenutoFisico")
    AGGIUNTA_CONTENUTO_FISICO("aggiuntaContenutoFisico");
    private final String value;

    EnumTipoOperazione(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumTipoOperazione fromValue(String v) {
        for (EnumTipoOperazione c: EnumTipoOperazione.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
