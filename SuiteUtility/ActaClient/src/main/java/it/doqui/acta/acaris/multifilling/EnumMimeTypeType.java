
package it.doqui.acta.acaris.multifilling;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per enumMimeTypeType.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * <pre>
 * &lt;simpleType name="enumMimeTypeType"&gt;
 *   &lt;restriction base="{common.acaris.acta.doqui.it}string"&gt;
 *     &lt;enumeration value="image/tiff"/&gt;
 *     &lt;enumeration value="image/x-tiff"/&gt;
 *     &lt;enumeration value="image/jpeg"/&gt;
 *     &lt;enumeration value="image/pjpeg"/&gt;
 *     &lt;enumeration value="application/pdf"/&gt;
 *     &lt;enumeration value="text/plain"/&gt;
 *     &lt;enumeration value="application/x-compressed"/&gt;
 *     &lt;enumeration value="application/x-zip-compressed"/&gt;
 *     &lt;enumeration value="application/zip"/&gt;
 *     &lt;enumeration value="multipart/x-zip"/&gt;
 *     &lt;enumeration value="application/x-tar"/&gt;
 *     &lt;enumeration value="application/gnutar"/&gt;
 *     &lt;enumeration value="application/pkcs7-mime"/&gt;
 *     &lt;enumeration value="application/timestamp-reply"/&gt;
 *     &lt;enumeration value="multipart/mixed"/&gt;
 *     &lt;enumeration value="application/msword"/&gt;
 *     &lt;enumeration value="application/rtf"/&gt;
 *     &lt;enumeration value="application/x-rtf"/&gt;
 *     &lt;enumeration value="text/richtext"/&gt;
 *     &lt;enumeration value="application/excel"/&gt;
 *     &lt;enumeration value="application/vnd.ms-excel"/&gt;
 *     &lt;enumeration value="application/x-excel"/&gt;
 *     &lt;enumeration value="application/x-msexcel"/&gt;
 *     &lt;enumeration value="application/mspowerpoint"/&gt;
 *     &lt;enumeration value="application/powerpoint"/&gt;
 *     &lt;enumeration value="application/vnd.ms-powerpoint"/&gt;
 *     &lt;enumeration value="application/x-mspowerpoint"/&gt;
 *     &lt;enumeration value="application/vnd.oasis.opendocument.chart"/&gt;
 *     &lt;enumeration value="application/vnd.oasis.opendocument.graphics"/&gt;
 *     &lt;enumeration value="application/vnd.oasis.opendocument.image"/&gt;
 *     &lt;enumeration value="application/vnd.oasis.opendocument.presentation"/&gt;
 *     &lt;enumeration value="application/vnd.oasis.opendocument.spreadsheet"/&gt;
 *     &lt;enumeration value="application/vnd.oasis.opendocument.text"/&gt;
 *     &lt;enumeration value="application/xml"/&gt;
 *     &lt;enumeration value="application/xsl"/&gt;
 *     &lt;enumeration value="application/timestamped-data"/&gt;
 *     &lt;enumeration value="text/html"/&gt;
 *     &lt;enumeration value="message/rfc822"/&gt;
 *     &lt;enumeration value="image/png"/&gt;
 *     &lt;enumeration value="application/dwf"/&gt;
 *     &lt;enumeration value="application/pkcs7-signature"/&gt;
 *     &lt;enumeration value="application/vnd.excel"/&gt;
 *     &lt;enumeration value="application/vnd.openxmlformats-officedocument.wordprocessingml.document"/&gt;
 *     &lt;enumeration value="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"/&gt;
 *     &lt;enumeration value="application/x-7z-compressed"/&gt;
 *     &lt;enumeration value="application/x-zip-compressed"/&gt;
 *     &lt;enumeration value="application/x-rar-compressed"/&gt;
 *     &lt;enumeration value="audio/mpeg3"/&gt;
 *     &lt;enumeration value="audio/wav"/&gt;
 *     &lt;enumeration value="audio/x-mpeg-3"/&gt;
 *     &lt;enumeration value="audio/x-wav"/&gt;
 *     &lt;enumeration value="drawing/x-dwf"/&gt;
 *     &lt;enumeration value="image/bmp"/&gt;
 *     &lt;enumeration value="image/pipeg"/&gt;
 *     &lt;enumeration value="image/vnd.dwf"/&gt;
 *     &lt;enumeration value="image/x-dwf"/&gt;
 *     &lt;enumeration value="text/xml"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "enumMimeTypeType", namespace = "common.acaris.acta.doqui.it")
@XmlEnum
public enum EnumMimeTypeType {

    @XmlEnumValue("image/tiff")
    IMAGE_TIFF("image/tiff"),
    @XmlEnumValue("image/x-tiff")
    IMAGE_X_TIFF("image/x-tiff"),
    @XmlEnumValue("image/jpeg")
    IMAGE_JPEG("image/jpeg"),
    @XmlEnumValue("image/pjpeg")
    IMAGE_PJPEG("image/pjpeg"),
    @XmlEnumValue("application/pdf")
    APPLICATION_PDF("application/pdf"),
    @XmlEnumValue("text/plain")
    TEXT_PLAIN("text/plain"),
    @XmlEnumValue("application/x-compressed")
    APPLICATION_X_COMPRESSED("application/x-compressed"),
    @XmlEnumValue("application/x-zip-compressed")
    APPLICATION_X_ZIP_COMPRESSED("application/x-zip-compressed"),
    @XmlEnumValue("application/zip")
    APPLICATION_ZIP("application/zip"),
    @XmlEnumValue("multipart/x-zip")
    MULTIPART_X_ZIP("multipart/x-zip"),
    @XmlEnumValue("application/x-tar")
    APPLICATION_X_TAR("application/x-tar"),
    @XmlEnumValue("application/gnutar")
    APPLICATION_GNUTAR("application/gnutar"),
    @XmlEnumValue("application/pkcs7-mime")
    APPLICATION_PKCS_7_MIME("application/pkcs7-mime"),
    @XmlEnumValue("application/timestamp-reply")
    APPLICATION_TIMESTAMP_REPLY("application/timestamp-reply"),
    @XmlEnumValue("multipart/mixed")
    MULTIPART_MIXED("multipart/mixed"),
    @XmlEnumValue("application/msword")
    APPLICATION_MSWORD("application/msword"),
    @XmlEnumValue("application/rtf")
    APPLICATION_RTF("application/rtf"),
    @XmlEnumValue("application/x-rtf")
    APPLICATION_X_RTF("application/x-rtf"),
    @XmlEnumValue("text/richtext")
    TEXT_RICHTEXT("text/richtext"),
    @XmlEnumValue("application/excel")
    APPLICATION_EXCEL("application/excel"),
    @XmlEnumValue("application/vnd.ms-excel")
    APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel"),
    @XmlEnumValue("application/x-excel")
    APPLICATION_X_EXCEL("application/x-excel"),
    @XmlEnumValue("application/x-msexcel")
    APPLICATION_X_MSEXCEL("application/x-msexcel"),
    @XmlEnumValue("application/mspowerpoint")
    APPLICATION_MSPOWERPOINT("application/mspowerpoint"),
    @XmlEnumValue("application/powerpoint")
    APPLICATION_POWERPOINT("application/powerpoint"),
    @XmlEnumValue("application/vnd.ms-powerpoint")
    APPLICATION_VND_MS_POWERPOINT("application/vnd.ms-powerpoint"),
    @XmlEnumValue("application/x-mspowerpoint")
    APPLICATION_X_MSPOWERPOINT("application/x-mspowerpoint"),
    @XmlEnumValue("application/vnd.oasis.opendocument.chart")
    APPLICATION_VND_OASIS_OPENDOCUMENT_CHART("application/vnd.oasis.opendocument.chart"),
    @XmlEnumValue("application/vnd.oasis.opendocument.graphics")
    APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS("application/vnd.oasis.opendocument.graphics"),
    @XmlEnumValue("application/vnd.oasis.opendocument.image")
    APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE("application/vnd.oasis.opendocument.image"),
    @XmlEnumValue("application/vnd.oasis.opendocument.presentation")
    APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION("application/vnd.oasis.opendocument.presentation"),
    @XmlEnumValue("application/vnd.oasis.opendocument.spreadsheet")
    APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET("application/vnd.oasis.opendocument.spreadsheet"),
    @XmlEnumValue("application/vnd.oasis.opendocument.text")
    APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT("application/vnd.oasis.opendocument.text"),
    @XmlEnumValue("application/xml")
    APPLICATION_XML("application/xml"),
    @XmlEnumValue("application/xsl")
    APPLICATION_XSL("application/xsl"),
    @XmlEnumValue("application/timestamped-data")
    APPLICATION_TIMESTAMPED_DATA("application/timestamped-data"),
    @XmlEnumValue("text/html")
    TEXT_HTML("text/html"),
    @XmlEnumValue("message/rfc822")
    MESSAGE_RFC_822("message/rfc822"),
    @XmlEnumValue("image/png")
    IMAGE_PNG("image/png"),
    @XmlEnumValue("application/dwf")
    APPLICATION_DWF("application/dwf"),
    @XmlEnumValue("application/pkcs7-signature")
    APPLICATION_PKCS_7_SIGNATURE("application/pkcs7-signature"),
    @XmlEnumValue("application/vnd.excel")
    APPLICATION_VND_EXCEL("application/vnd.excel"),
    @XmlEnumValue("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    @XmlEnumValue("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    @XmlEnumValue("application/x-7z-compressed")
    APPLICATION_X_7_Z_COMPRESSED("application/x-7z-compressed"),
    @XmlEnumValue("application/x-rar-compressed")
    APPLICATION_X_RAR_COMPRESSED("application/x-rar-compressed"),
    @XmlEnumValue("audio/mpeg3")
    AUDIO_MPEG_3("audio/mpeg3"),
    @XmlEnumValue("audio/wav")
    AUDIO_WAV("audio/wav"),
    @XmlEnumValue("audio/x-mpeg-3")
    AUDIO_X_MPEG_3("audio/x-mpeg-3"),
    @XmlEnumValue("audio/x-wav")
    AUDIO_X_WAV("audio/x-wav"),
    @XmlEnumValue("drawing/x-dwf")
    DRAWING_X_DWF("drawing/x-dwf"),
    @XmlEnumValue("image/bmp")
    IMAGE_BMP("image/bmp"),
    @XmlEnumValue("image/pipeg")
    IMAGE_PIPEG("image/pipeg"),
    @XmlEnumValue("image/vnd.dwf")
    IMAGE_VND_DWF("image/vnd.dwf"),
    @XmlEnumValue("image/x-dwf")
    IMAGE_X_DWF("image/x-dwf"),
    @XmlEnumValue("text/xml")
    TEXT_XML("text/xml");
    private final String value;

    EnumMimeTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EnumMimeTypeType fromValue(String v) {
        for (EnumMimeTypeType c: EnumMimeTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
