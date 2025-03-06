
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumBaseObjectType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumBaseObjectType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="DocumentPropertiesType"/&gt;
 *     &lt;enumeration value="FolderPropertiesType"/&gt;
 *     &lt;enumeration value="RelationshipPropertiesType"/&gt;
 *     &lt;enumeration value="PolicyPropertiesType"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumBaseObjectType")
@XmlEnum
public enum EnumBaseObjectType {

    @XmlEnumValue("DocumentPropertiesType")
    DOCUMENT_PROPERTIES_TYPE("DocumentPropertiesType"),
    @XmlEnumValue("FolderPropertiesType")
    FOLDER_PROPERTIES_TYPE("FolderPropertiesType"),
    @XmlEnumValue("RelationshipPropertiesType")
    RELATIONSHIP_PROPERTIES_TYPE("RelationshipPropertiesType"),
    @XmlEnumValue("PolicyPropertiesType")
    POLICY_PROPERTIES_TYPE("PolicyPropertiesType");
    private final String value;

    EnumBaseObjectType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumBaseObjectType fromValue(String v) {
        for (EnumBaseObjectType c: EnumBaseObjectType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
