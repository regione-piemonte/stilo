
package it.doqui.acta.acaris.sms;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per CollocazioneDocumento complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CollocazioneDocumento"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="classificazioneId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="parentId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="parentFolderType" type="{documentservice.acaris.acta.doqui.it}enumParentFolder"/&gt;
 *         &lt;element name="classificazionePrincipaleId" type="{common.acaris.acta.doqui.it}ObjectIdType" minOccurs="0"/&gt;
 *         &lt;element name="gruppoAllegatiId" type="{common.acaris.acta.doqui.it}ObjectIdType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CollocazioneDocumento", namespace = "documentservice.acaris.acta.doqui.it", propOrder = {
    "classificazioneId",
    "parentId",
    "parentFolderType",
    "classificazionePrincipaleId",
    "gruppoAllegatiId"
})
public class CollocazioneDocumento {

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType classificazioneId;
    @XmlElement(namespace = "", required = true)
    protected ObjectIdType parentId;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "string")
    protected EnumParentFolder parentFolderType;
    @XmlElement(namespace = "")
    protected ObjectIdType classificazionePrincipaleId;
    @XmlElement(namespace = "")
    protected ObjectIdType gruppoAllegatiId;

    /**
     * Recupera il valore della proprietà classificazioneId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getClassificazioneId() {
        return classificazioneId;
    }

    /**
     * Imposta il valore della proprietà classificazioneId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setClassificazioneId(ObjectIdType value) {
        this.classificazioneId = value;
    }

    /**
     * Recupera il valore della proprietà parentId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getParentId() {
        return parentId;
    }

    /**
     * Imposta il valore della proprietà parentId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setParentId(ObjectIdType value) {
        this.parentId = value;
    }

    /**
     * Recupera il valore della proprietà parentFolderType.
     * 
     * @return
     *     possible object is
     *     {@link EnumParentFolder }
     *     
     */
    public EnumParentFolder getParentFolderType() {
        return parentFolderType;
    }

    /**
     * Imposta il valore della proprietà parentFolderType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumParentFolder }
     *     
     */
    public void setParentFolderType(EnumParentFolder value) {
        this.parentFolderType = value;
    }

    /**
     * Recupera il valore della proprietà classificazionePrincipaleId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getClassificazionePrincipaleId() {
        return classificazionePrincipaleId;
    }

    /**
     * Imposta il valore della proprietà classificazionePrincipaleId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setClassificazionePrincipaleId(ObjectIdType value) {
        this.classificazionePrincipaleId = value;
    }

    /**
     * Recupera il valore della proprietà gruppoAllegatiId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getGruppoAllegatiId() {
        return gruppoAllegatiId;
    }

    /**
     * Imposta il valore della proprietà gruppoAllegatiId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setGruppoAllegatiId(ObjectIdType value) {
        this.gruppoAllegatiId = value;
    }

}
