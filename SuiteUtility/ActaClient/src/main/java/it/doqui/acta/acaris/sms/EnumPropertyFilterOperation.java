
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumPropertyFilterOperation.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumPropertyFilterOperation"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="none"/&gt;
 *     &lt;enumeration value="all"/&gt;
 *     &lt;enumeration value="query"/&gt;
 *     &lt;enumeration value="getProperties"/&gt;
 *     &lt;enumeration value="getDescendants"/&gt;
 *     &lt;enumeration value="getChildren"/&gt;
 *     &lt;enumeration value="getFolderParent"/&gt;
 *     &lt;enumeration value="getObjectParents"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumPropertyFilterOperation", namespace = "common.acaris.acta.doqui.it")
@XmlEnum
public enum EnumPropertyFilterOperation {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("all")
    ALL("all"),
    @XmlEnumValue("query")
    QUERY("query"),
    @XmlEnumValue("getProperties")
    GET_PROPERTIES("getProperties"),
    @XmlEnumValue("getDescendants")
    GET_DESCENDANTS("getDescendants"),
    @XmlEnumValue("getChildren")
    GET_CHILDREN("getChildren"),
    @XmlEnumValue("getFolderParent")
    GET_FOLDER_PARENT("getFolderParent"),
    @XmlEnumValue("getObjectParents")
    GET_OBJECT_PARENTS("getObjectParents");
    private final String value;

    EnumPropertyFilterOperation(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumPropertyFilterOperation fromValue(String v) {
        for (EnumPropertyFilterOperation c: EnumPropertyFilterOperation.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
