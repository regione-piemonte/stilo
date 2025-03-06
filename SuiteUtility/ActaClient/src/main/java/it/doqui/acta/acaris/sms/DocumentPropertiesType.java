
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per DocumentPropertiesType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="DocumentPropertiesType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{archive.acaris.acta.doqui.it}ArchivePropertiesType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="objectTypeId" type="{archive.acaris.acta.doqui.it}enumArchiveObjectType"/&gt;
 *         &lt;element name="contentStreamLength" type="{common.acaris.acta.doqui.it}contentStreamLengthType"/&gt;
 *         &lt;element name="contentStreamMimeType" type="{common.acaris.acta.doqui.it}enumMimeTypeType"/&gt;
 *         &lt;element name="contentStreamFilename" type="{common.acaris.acta.doqui.it}contentStreamFilenameType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentPropertiesType", propOrder = {
    "objectTypeId",
    "contentStreamLength",
    "contentStreamMimeType",
    "contentStreamFilename"
})
@XmlSeeAlso({
    ClipsMetallicaPropertiesType.class,
    ContenutoFisicoPropertiesType.class,
    DocumentoPropertiesType.class
})
public abstract class DocumentPropertiesType
    extends ArchivePropertiesType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumArchiveObjectType objectTypeId;
    protected int contentStreamLength;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumMimeTypeType contentStreamMimeType;
    @XmlElement(required = true)
    protected String contentStreamFilename;

    /**
     * Recupera il valore della propriet� objectTypeId.
     * 
     * @return
     *     possible object is
     *     {@link EnumArchiveObjectType }
     *     
     */
    public EnumArchiveObjectType getObjectTypeId() {
        return objectTypeId;
    }

    /**
     * Imposta il valore della propriet� objectTypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumArchiveObjectType }
     *     
     */
    public void setObjectTypeId(EnumArchiveObjectType value) {
        this.objectTypeId = value;
    }

    /**
     * Recupera il valore della propriet� contentStreamLength.
     * 
     */
    public int getContentStreamLength() {
        return contentStreamLength;
    }

    /**
     * Imposta il valore della propriet� contentStreamLength.
     * 
     */
    public void setContentStreamLength(int value) {
        this.contentStreamLength = value;
    }

    /**
     * Recupera il valore della propriet� contentStreamMimeType.
     * 
     * @return
     *     possible object is
     *     {@link EnumMimeTypeType }
     *     
     */
    public EnumMimeTypeType getContentStreamMimeType() {
        return contentStreamMimeType;
    }

    /**
     * Imposta il valore della propriet� contentStreamMimeType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumMimeTypeType }
     *     
     */
    public void setContentStreamMimeType(EnumMimeTypeType value) {
        this.contentStreamMimeType = value;
    }

    /**
     * Recupera il valore della propriet� contentStreamFilename.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentStreamFilename() {
        return contentStreamFilename;
    }

    /**
     * Imposta il valore della propriet� contentStreamFilename.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentStreamFilename(String value) {
        this.contentStreamFilename = value;
    }

}
