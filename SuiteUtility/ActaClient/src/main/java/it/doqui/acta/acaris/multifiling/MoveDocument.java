
package it.doqui.acta.acaris.multifiling;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element name="principalId" type="{common.acaris.acta.doqui.it}PrincipalIdType"/&gt;
 *         &lt;element name="associativeObjectId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="sourceFolderId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="targetFolderId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="associativeProperties" type="{common.acaris.acta.doqui.it}PropertiesType"/&gt;
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
    "principalId",
    "associativeObjectId",
    "sourceFolderId",
    "targetFolderId",
    "associativeProperties"
})
@XmlRootElement(name = "moveDocument")
public class MoveDocument {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected PrincipalIdType principalId;
    @XmlElement(required = true)
    protected ObjectIdType associativeObjectId;
    @XmlElement(required = true)
    protected ObjectIdType sourceFolderId;
    @XmlElement(required = true)
    protected ObjectIdType targetFolderId;
    @XmlElement(required = true)
    protected PropertiesType associativeProperties;

    /**
     * Recupera il valore della proprietÓ repositoryId.
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
     * Imposta il valore della proprietÓ repositoryId.
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
     * Recupera il valore della proprietÓ principalId.
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
     * Imposta il valore della proprietÓ principalId.
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
     * Recupera il valore della proprietÓ associativeObjectId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getAssociativeObjectId() {
        return associativeObjectId;
    }

    /**
     * Imposta il valore della proprietÓ associativeObjectId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setAssociativeObjectId(ObjectIdType value) {
        this.associativeObjectId = value;
    }

    /**
     * Recupera il valore della proprietÓ sourceFolderId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getSourceFolderId() {
        return sourceFolderId;
    }

    /**
     * Imposta il valore della proprietÓ sourceFolderId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setSourceFolderId(ObjectIdType value) {
        this.sourceFolderId = value;
    }

    /**
     * Recupera il valore della proprietÓ targetFolderId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getTargetFolderId() {
        return targetFolderId;
    }

    /**
     * Imposta il valore della proprietÓ targetFolderId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setTargetFolderId(ObjectIdType value) {
        this.targetFolderId = value;
    }

    /**
     * Recupera il valore della proprietÓ associativeProperties.
     * 
     * @return
     *     possible object is
     *     {@link PropertiesType }
     *     
     */
    public PropertiesType getAssociativeProperties() {
        return associativeProperties;
    }

    /**
     * Imposta il valore della proprietÓ associativeProperties.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertiesType }
     *     
     */
    public void setAssociativeProperties(PropertiesType value) {
        this.associativeProperties = value;
    }

}
