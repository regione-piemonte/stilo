
package it.doqui.acta.acaris.multifiling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per acarisRenditionType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="acarisRenditionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="streamId" type="{common.acaris.acta.doqui.it}enumStreamId"/&gt;
 *         &lt;element name="mimeType" type="{common.acaris.acta.doqui.it}enumMimeTypeType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "acarisRenditionType", propOrder = {
    "streamId",
    "mimeType"
})
public class AcarisRenditionType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumStreamId streamId;
    @XmlSchemaType(name = "string")
    protected EnumMimeTypeType mimeType;

    /**
     * Recupera il valore della propriet� streamId.
     * 
     * @return
     *     possible object is
     *     {@link EnumStreamId }
     *     
     */
    public EnumStreamId getStreamId() {
        return streamId;
    }

    /**
     * Imposta il valore della propriet� streamId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumStreamId }
     *     
     */
    public void setStreamId(EnumStreamId value) {
        this.streamId = value;
    }

    /**
     * Recupera il valore della propriet� mimeType.
     * 
     * @return
     *     possible object is
     *     {@link EnumMimeTypeType }
     *     
     */
    public EnumMimeTypeType getMimeType() {
        return mimeType;
    }

    /**
     * Imposta il valore della propriet� mimeType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumMimeTypeType }
     *     
     */
    public void setMimeType(EnumMimeTypeType value) {
        this.mimeType = value;
    }

}
