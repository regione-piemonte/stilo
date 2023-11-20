/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.doqui.acta.acaris.archive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import it.doqui.acta.acaris.common.AcarisContentStreamType;
import it.doqui.acta.acaris.common.ObjectIdType;
import it.doqui.acta.acaris.common.PrincipalIdType;
import it.doqui.acta.acaris.common.PropertiesType;


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
 *         &lt;element name="typeId" type="{archive.acaris.acta.doqui.it}enumDocumentObjectType"/&gt;
 *         &lt;element name="associativeObjectTypeId" type="{archive.acaris.acta.doqui.it}enumFolderObjectType"/&gt;
 *         &lt;element name="principalId" type="{common.acaris.acta.doqui.it}PrincipalIdType"/&gt;
 *         &lt;element name="properties" type="{common.acaris.acta.doqui.it}PropertiesType"/&gt;
 *         &lt;element name="associativeObjectProperties" type="{common.acaris.acta.doqui.it}PropertiesType"/&gt;
 *         &lt;element name="folderId" type="{common.acaris.acta.doqui.it}ObjectIdType" minOccurs="0"/&gt;
 *         &lt;element name="contentStream" type="{common.acaris.acta.doqui.it}acarisContentStreamType" minOccurs="0"/&gt;
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
    "typeId",
    "associativeObjectTypeId",
    "principalId",
    "properties",
    "associativeObjectProperties",
    "folderId",
    "contentStream"
})
@XmlRootElement(name = "createAssociativeDocument")
public class CreateAssociativeDocument {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumDocumentObjectType typeId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumFolderObjectType associativeObjectTypeId;
    @XmlElement(required = true)
    protected PrincipalIdType principalId;
    @XmlElement(required = true)
    protected PropertiesType properties;
    @XmlElement(required = true)
    protected PropertiesType associativeObjectProperties;
    protected ObjectIdType folderId;
    protected AcarisContentStreamType contentStream;

    /**
     * Recupera il valore della proprietà repositoryId.
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
     * Imposta il valore della proprietà repositoryId.
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
     * Recupera il valore della proprietà typeId.
     * 
     * @return
     *     possible object is
     *     {@link EnumDocumentObjectType }
     *     
     */
    public EnumDocumentObjectType getTypeId() {
        return typeId;
    }

    /**
     * Imposta il valore della proprietà typeId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumDocumentObjectType }
     *     
     */
    public void setTypeId(EnumDocumentObjectType value) {
        this.typeId = value;
    }

    /**
     * Recupera il valore della proprietà associativeObjectTypeId.
     * 
     * @return
     *     possible object is
     *     {@link EnumFolderObjectType }
     *     
     */
    public EnumFolderObjectType getAssociativeObjectTypeId() {
        return associativeObjectTypeId;
    }

    /**
     * Imposta il valore della proprietà associativeObjectTypeId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumFolderObjectType }
     *     
     */
    public void setAssociativeObjectTypeId(EnumFolderObjectType value) {
        this.associativeObjectTypeId = value;
    }

    /**
     * Recupera il valore della proprietà principalId.
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
     * Imposta il valore della proprietà principalId.
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
     * Recupera il valore della proprietà properties.
     * 
     * @return
     *     possible object is
     *     {@link PropertiesType }
     *     
     */
    public PropertiesType getProperties() {
        return properties;
    }

    /**
     * Imposta il valore della proprietà properties.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertiesType }
     *     
     */
    public void setProperties(PropertiesType value) {
        this.properties = value;
    }

    /**
     * Recupera il valore della proprietà associativeObjectProperties.
     * 
     * @return
     *     possible object is
     *     {@link PropertiesType }
     *     
     */
    public PropertiesType getAssociativeObjectProperties() {
        return associativeObjectProperties;
    }

    /**
     * Imposta il valore della proprietà associativeObjectProperties.
     * 
     * @param value
     *     allowed object is
     *     {@link PropertiesType }
     *     
     */
    public void setAssociativeObjectProperties(PropertiesType value) {
        this.associativeObjectProperties = value;
    }

    /**
     * Recupera il valore della proprietà folderId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getFolderId() {
        return folderId;
    }

    /**
     * Imposta il valore della proprietà folderId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setFolderId(ObjectIdType value) {
        this.folderId = value;
    }

    /**
     * Recupera il valore della proprietà contentStream.
     * 
     * @return
     *     possible object is
     *     {@link AcarisContentStreamType }
     *     
     */
    public AcarisContentStreamType getContentStream() {
        return contentStream;
    }

    /**
     * Imposta il valore della proprietà contentStream.
     * 
     * @param value
     *     allowed object is
     *     {@link AcarisContentStreamType }
     *     
     */
    public void setContentStream(AcarisContentStreamType value) {
        this.contentStream = value;
    }

}
