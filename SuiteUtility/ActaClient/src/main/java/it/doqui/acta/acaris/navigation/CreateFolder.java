
package it.doqui.acta.acaris.navigation;

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
 *         &lt;element name="typeId" type="{archive.acaris.acta.doqui.it}enumFolderObjectType"/&gt;
 *         &lt;element name="principalId" type="{common.acaris.acta.doqui.it}PrincipalIdType"/&gt;
 *         &lt;element name="properties" type="{common.acaris.acta.doqui.it}PropertiesType"/&gt;
 *         &lt;element name="folderId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
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
    "principalId",
    "properties",
    "folderId"
})
@XmlRootElement(name = "createFolder")
public class CreateFolder {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumFolderObjectType typeId;
    @XmlElement(required = true)
    protected PrincipalIdType principalId;
    @XmlElement(required = true)
    protected PropertiesType properties;
    @XmlElement(required = true)
    protected ObjectIdType folderId;

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
     * Recupera il valore della proprietÓ typeId.
     * 
     * @return
     *     possible object is
     *     {@link EnumFolderObjectType }
     *     
     */
    public EnumFolderObjectType getTypeId() {
        return typeId;
    }

    /**
     * Imposta il valore della proprietÓ typeId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumFolderObjectType }
     *     
     */
    public void setTypeId(EnumFolderObjectType value) {
        this.typeId = value;
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
     * Recupera il valore della proprietÓ properties.
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
     * Imposta il valore della proprietÓ properties.
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
     * Recupera il valore della proprietÓ folderId.
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
     * Imposta il valore della proprietÓ folderId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setFolderId(ObjectIdType value) {
        this.folderId = value;
    }

}
