
package it.eng.hsm.client.aruba.generated;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pdfProfile.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="pdfProfile">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BASIC"/>
 *     &lt;enumeration value="PADESBES"/>
 *     &lt;enumeration value="PADESLTV"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "pdfProfile")
@XmlEnum
public enum PdfProfile {

    BASIC,
    PADESBES,
    PADESLTV;

    public String value() {
        return name();
    }

    public static PdfProfile fromValue(String v) {
        return valueOf(v);
    }

}
