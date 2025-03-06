
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumServiceType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumServiceType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="Archive"/&gt;
 *     &lt;enumeration value="BackOffice"/&gt;
 *     &lt;enumeration value="Management"/&gt;
 *     &lt;enumeration value="OfficialBook"/&gt;
 *     &lt;enumeration value="Sms"/&gt;
 *     &lt;enumeration value="SubjectRegistry"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumServiceType", namespace = "backoffice.acaris.acta.doqui.it")
@XmlEnum
public enum EnumServiceType {

    @XmlEnumValue("Archive")
    ARCHIVE("Archive"),
    @XmlEnumValue("BackOffice")
    BACK_OFFICE("BackOffice"),
    @XmlEnumValue("Management")
    MANAGEMENT("Management"),
    @XmlEnumValue("OfficialBook")
    OFFICIAL_BOOK("OfficialBook"),
    @XmlEnumValue("Sms")
    SMS("Sms"),
    @XmlEnumValue("SubjectRegistry")
    SUBJECT_REGISTRY("SubjectRegistry");
    private final String value;

    EnumServiceType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumServiceType fromValue(String v) {
        for (EnumServiceType c: EnumServiceType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
