
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for typeTransport.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="typeTransport">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BYNARYNET"/>
 *     &lt;enumeration value="FILENAME"/>
 *     &lt;enumeration value="DIRECTORYNAME"/>
 *     &lt;enumeration value="STREAM"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "typeTransport")
@XmlEnum
public enum TypeTransport {

    BYNARYNET,
    FILENAME,
    DIRECTORYNAME,
    STREAM;

    public String value() {
        return name();
    }

    public static TypeTransport fromValue(String v) {
        return valueOf(v);
    }

}
