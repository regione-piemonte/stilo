
package it.doqui.acta.acaris.multifilling;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumModalitaCalcoloProgDocType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumModalitaCalcoloProgDocType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="IncrementoAutomatico"/&gt;
 *     &lt;enumeration value="Ereditato"/&gt;
 *     &lt;enumeration value="Libero"/&gt;
 *     &lt;enumeration value="IncrementoAutomaticoConAzzeramentoInizioAnno"/&gt;
 *     &lt;enumeration value="IncrementoAutomaticoConAzzeramentoInizioLegislatura"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumModalitaCalcoloProgDocType")
@XmlEnum
public enum EnumModalitaCalcoloProgDocType {

    @XmlEnumValue("IncrementoAutomatico")
    INCREMENTO_AUTOMATICO("IncrementoAutomatico"),
    @XmlEnumValue("Ereditato")
    EREDITATO("Ereditato"),
    @XmlEnumValue("Libero")
    LIBERO("Libero"),
    @XmlEnumValue("IncrementoAutomaticoConAzzeramentoInizioAnno")
    INCREMENTO_AUTOMATICO_CON_AZZERAMENTO_INIZIO_ANNO("IncrementoAutomaticoConAzzeramentoInizioAnno"),
    @XmlEnumValue("IncrementoAutomaticoConAzzeramentoInizioLegislatura")
    INCREMENTO_AUTOMATICO_CON_AZZERAMENTO_INIZIO_LEGISLATURA("IncrementoAutomaticoConAzzeramentoInizioLegislatura");
    private final String value;

    EnumModalitaCalcoloProgDocType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumModalitaCalcoloProgDocType fromValue(String v) {
        for (EnumModalitaCalcoloProgDocType c: EnumModalitaCalcoloProgDocType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
