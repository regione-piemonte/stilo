
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumServiceException.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumServiceException"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="invalidArgument"/&gt;
 *     &lt;enumeration value="objectNotFound"/&gt;
 *     &lt;enumeration value="notSupported"/&gt;
 *     &lt;enumeration value="permissionDenied"/&gt;
 *     &lt;enumeration value="runtime"/&gt;
 *     &lt;enumeration value="constraint"/&gt;
 *     &lt;enumeration value="contentAlreadyExists"/&gt;
 *     &lt;enumeration value="filterNotValid"/&gt;
 *     &lt;enumeration value="storage"/&gt;
 *     &lt;enumeration value="streamNotSupported"/&gt;
 *     &lt;enumeration value="updateConflict"/&gt;
 *     &lt;enumeration value="versioning"/&gt;
 *     &lt;enumeration value="systemError"/&gt;
 *     &lt;enumeration value="unrecoverableError"/&gt;
 *     &lt;enumeration value="configurationError"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumServiceException")
@XmlEnum
public enum EnumServiceException {

    @XmlEnumValue("invalidArgument")
    INVALID_ARGUMENT("invalidArgument"),
    @XmlEnumValue("objectNotFound")
    OBJECT_NOT_FOUND("objectNotFound"),
    @XmlEnumValue("notSupported")
    NOT_SUPPORTED("notSupported"),
    @XmlEnumValue("permissionDenied")
    PERMISSION_DENIED("permissionDenied"),
    @XmlEnumValue("runtime")
    RUNTIME("runtime"),
    @XmlEnumValue("constraint")
    CONSTRAINT("constraint"),
    @XmlEnumValue("contentAlreadyExists")
    CONTENT_ALREADY_EXISTS("contentAlreadyExists"),
    @XmlEnumValue("filterNotValid")
    FILTER_NOT_VALID("filterNotValid"),
    @XmlEnumValue("storage")
    STORAGE("storage"),
    @XmlEnumValue("streamNotSupported")
    STREAM_NOT_SUPPORTED("streamNotSupported"),
    @XmlEnumValue("updateConflict")
    UPDATE_CONFLICT("updateConflict"),
    @XmlEnumValue("versioning")
    VERSIONING("versioning"),
    @XmlEnumValue("systemError")
    SYSTEM_ERROR("systemError"),
    @XmlEnumValue("unrecoverableError")
    UNRECOVERABLE_ERROR("unrecoverableError"),
    @XmlEnumValue("configurationError")
    CONFIGURATION_ERROR("configurationError");
    private final String value;

    EnumServiceException(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumServiceException fromValue(String v) {
        for (EnumServiceException c: EnumServiceException.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
