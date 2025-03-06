
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for credentialsType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="credentialsType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SMS"/>
 *     &lt;enumeration value="ARUBACALL"/>
 *     &lt;enumeration value="CNS2"/>
 *     &lt;enumeration value="PAPERTOKEN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "credentialsType")
@XmlEnum
public enum CredentialsType {

    SMS("SMS"),
    ARUBACALL("ARUBACALL"),
    @XmlEnumValue("CNS2")
    CNS_2("CNS2"),
    PAPERTOKEN("PAPERTOKEN");
    private final String value;

    CredentialsType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CredentialsType fromValue(String v) {
        for (CredentialsType c: CredentialsType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
