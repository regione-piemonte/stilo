
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for transformType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="transformType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CANONICAL_WITH_COMMENT"/>
 *     &lt;enumeration value="CANONICAL_OMIT_COMMENT"/>
 *     &lt;enumeration value="BASE64"/>
 *     &lt;enumeration value="XPATH2_INTERSECT"/>
 *     &lt;enumeration value="XPATH2_SUBTRACT"/>
 *     &lt;enumeration value="XPATH2_UNION"/>
 *     &lt;enumeration value="XSLT"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "transformType")
@XmlEnum
public enum TransformType {

    CANONICAL_WITH_COMMENT("CANONICAL_WITH_COMMENT"),
    CANONICAL_OMIT_COMMENT("CANONICAL_OMIT_COMMENT"),
    @XmlEnumValue("BASE64")
    BASE_64("BASE64"),
    @XmlEnumValue("XPATH2_INTERSECT")
    XPATH_2_INTERSECT("XPATH2_INTERSECT"),
    @XmlEnumValue("XPATH2_SUBTRACT")
    XPATH_2_SUBTRACT("XPATH2_SUBTRACT"),
    @XmlEnumValue("XPATH2_UNION")
    XPATH_2_UNION("XPATH2_UNION"),
    XSLT("XSLT");
    private final String value;

    TransformType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TransformType fromValue(String v) {
        for (TransformType c: TransformType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
