
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for xmlSignatureType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="xmlSignatureType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="XMLENVELOPED"/>
 *     &lt;enumeration value="XMLENVELOPING"/>
 *     &lt;enumeration value="XMLDETACHED_INTERNAL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "xmlSignatureType")
@XmlEnum
public enum XmlSignatureType {

    XMLENVELOPED,
    XMLENVELOPING,
    XMLDETACHED_INTERNAL;

    public String value() {
        return name();
    }

    public static XmlSignatureType fromValue(String v) {
        return valueOf(v);
    }

}
