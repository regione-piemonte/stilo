
package it.doqui.acta.acaris.object;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="repositoryId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="documentId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="principalId" type="{common.acaris.acta.doqui.it}PrincipalIdType"/&gt;
 *         &lt;element name="changeToken" type="{common.acaris.acta.doqui.it}ChangeTokenType" minOccurs="0"/&gt;
 *         &lt;element name="streamId" type="{common.acaris.acta.doqui.it}enumStreamId" minOccurs="0"/&gt;
 *         &lt;element name="contentStream" type="{common.acaris.acta.doqui.it}acarisContentStreamType" maxOccurs="2"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "repositoryId",
    "documentId",
    "principalId",
    "changeToken",
    "streamId",
    "contentStream"
})
@XmlRootElement(name = "addRenditionStream")
public class AddRenditionStream {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected ObjectIdType documentId;
    @XmlElement(required = true)
    protected PrincipalIdType principalId;
    protected ChangeTokenType changeToken;
    @XmlSchemaType(name = "string")
    protected EnumStreamId streamId;
    @XmlElement(required = true)
    protected List<AcarisContentStreamType> contentStream;

    /**
     * Recupera il valore della propriet� repositoryId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getRepositoryId() {
        return repositoryId;
    }

    /**
     * Imposta il valore della propriet� repositoryId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setRepositoryId(ObjectIdType value) {
        this.repositoryId = value;
    }

    /**
     * Recupera il valore della propriet� documentId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getDocumentId() {
        return documentId;
    }

    /**
     * Imposta il valore della propriet� documentId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setDocumentId(ObjectIdType value) {
        this.documentId = value;
    }

    /**
     * Recupera il valore della propriet� principalId.
     * 
     * @return
     *     possible object is
     *     {@link PrincipalIdType }
     *     
     */
    public PrincipalIdType getPrincipalId() {
        return principalId;
    }

    /**
     * Imposta il valore della propriet� principalId.
     * 
     * @param value
     *     allowed object is
     *     {@link PrincipalIdType }
     *     
     */
    public void setPrincipalId(PrincipalIdType value) {
        this.principalId = value;
    }

    /**
     * Recupera il valore della propriet� changeToken.
     * 
     * @return
     *     possible object is
     *     {@link ChangeTokenType }
     *     
     */
    public ChangeTokenType getChangeToken() {
        return changeToken;
    }

    /**
     * Imposta il valore della propriet� changeToken.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeTokenType }
     *     
     */
    public void setChangeToken(ChangeTokenType value) {
        this.changeToken = value;
    }

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
     * Gets the value of the contentStream property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contentStream property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContentStream().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AcarisContentStreamType }
     * 
     * 
     */
    public List<AcarisContentStreamType> getContentStream() {
        if (contentStream == null) {
            contentStream = new ArrayList<AcarisContentStreamType>();
        }
        return this.contentStream;
    }

}
