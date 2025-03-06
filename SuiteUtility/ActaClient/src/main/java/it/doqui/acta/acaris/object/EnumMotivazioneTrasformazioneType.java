
package it.doqui.acta.acaris.object;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumMotivazioneTrasformazioneType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumMotivazioneTrasformazioneType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="ScomposizioneDocumentoAggregato"/&gt;
 *     &lt;enumeration value="Sbustamento"/&gt;
 *     &lt;enumeration value="TrasformazionePerObsolescenza"/&gt;
 *     &lt;enumeration value="PassaggiodiStato"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumMotivazioneTrasformazioneType")
@XmlEnum
public enum EnumMotivazioneTrasformazioneType {

    @XmlEnumValue("ScomposizioneDocumentoAggregato")
    SCOMPOSIZIONE_DOCUMENTO_AGGREGATO("ScomposizioneDocumentoAggregato"),
    @XmlEnumValue("Sbustamento")
    SBUSTAMENTO("Sbustamento"),
    @XmlEnumValue("TrasformazionePerObsolescenza")
    TRASFORMAZIONE_PER_OBSOLESCENZA("TrasformazionePerObsolescenza"),
    @XmlEnumValue("PassaggiodiStato")
    PASSAGGIODI_STATO("PassaggiodiStato");
    private final String value;

    EnumMotivazioneTrasformazioneType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumMotivazioneTrasformazioneType fromValue(String v) {
        for (EnumMotivazioneTrasformazioneType c: EnumMotivazioneTrasformazioneType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
