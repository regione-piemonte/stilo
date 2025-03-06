
package it.doqui.acta.acaris.multifiling;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumRelationshipObjectType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumRelationshipObjectType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="DocumentAssociationPropertiesType"/&gt;
 *     &lt;enumeration value="HistoryModificheTecnichePropertiesType"/&gt;
 *     &lt;enumeration value="DocumentCompositionPropertiesType"/&gt;
 *     &lt;enumeration value="HistoryVecchieVersioniPropertiesType"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumRelationshipObjectType")
@XmlEnum
public enum EnumRelationshipObjectType {

    @XmlEnumValue("DocumentAssociationPropertiesType")
    DOCUMENT_ASSOCIATION_PROPERTIES_TYPE("DocumentAssociationPropertiesType"),
    @XmlEnumValue("HistoryModificheTecnichePropertiesType")
    HISTORY_MODIFICHE_TECNICHE_PROPERTIES_TYPE("HistoryModificheTecnichePropertiesType"),
    @XmlEnumValue("DocumentCompositionPropertiesType")
    DOCUMENT_COMPOSITION_PROPERTIES_TYPE("DocumentCompositionPropertiesType"),
    @XmlEnumValue("HistoryVecchieVersioniPropertiesType")
    HISTORY_VECCHIE_VERSIONI_PROPERTIES_TYPE("HistoryVecchieVersioniPropertiesType");
    private final String value;

    EnumRelationshipObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumRelationshipObjectType fromValue(String v) {
        for (EnumRelationshipObjectType c: EnumRelationshipObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
