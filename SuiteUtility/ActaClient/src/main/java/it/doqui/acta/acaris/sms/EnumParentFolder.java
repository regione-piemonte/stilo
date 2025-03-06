
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumParentFolder.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumParentFolder"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="Aggregazione"/&gt;
 *     &lt;enumeration value="Classificazione.GruppoAllegati"/&gt;
 *     &lt;enumeration value="fascicoloTemporaneo"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumParentFolder", namespace = "documentservice.acaris.acta.doqui.it")
@XmlEnum
public enum EnumParentFolder {

    @XmlEnumValue("Aggregazione")
    AGGREGAZIONE("Aggregazione"),
    @XmlEnumValue("Classificazione.GruppoAllegati")
    CLASSIFICAZIONE_GRUPPO_ALLEGATI("Classificazione.GruppoAllegati"),
    @XmlEnumValue("fascicoloTemporaneo")
    FASCICOLO_TEMPORANEO("fascicoloTemporaneo");
    private final String value;

    EnumParentFolder(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumParentFolder fromValue(String v) {
        for (EnumParentFolder c: EnumParentFolder.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
