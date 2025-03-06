
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
 *         &lt;element name="typeId" type="{archive.acaris.acta.doqui.it}enumRelationshipObjectType"/&gt;
 *         &lt;element name="principalId" type="{common.acaris.acta.doqui.it}PrincipalIdType"/&gt;
 *         &lt;element name="properties" type="{common.acaris.acta.doqui.it}PropertiesType"/&gt;
 *         &lt;element name="sourceObjectId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="targetObjectId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
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
    "sourceObjectId",
    "targetObjectId"
})
@XmlRootElement(name = "createRelationship")
public class CreateRelationship {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumRelationshipObjectType typeId;
    @XmlElement(required = true)
    protected PrincipalIdType principalId;
    @XmlElement(required = true)
    protected PropertiesType properties;
    @XmlElement(required = true)
    protected ObjectIdType sourceObjectId;
    @XmlElement(required = true)
    protected ObjectIdType targetObjectId;

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
     * Recupera il valore della propriet� typeId.
     * 
     * @return
     *     possible object is
     *     {@link EnumRelationshipObjectType }
     *     
     */
    public EnumRelationshipObjectType getTypeId() {
        return typeId;
    }

    /**
     * Imposta il valore della propriet� typeId.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumRelationshipObjectType }
     *     
     */
    public void setTypeId(EnumRelationshipObjectType value) {
        this.typeId = value;
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
     * Recupera il valore della propriet� properties.
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
     * Imposta il valore della propriet� properties.
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
     * Recupera il valore della propriet� sourceObjectId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getSourceObjectId() {
        return sourceObjectId;
    }

    /**
     * Imposta il valore della propriet� sourceObjectId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setSourceObjectId(ObjectIdType value) {
        this.sourceObjectId = value;
    }

    /**
     * Recupera il valore della propriet� targetObjectId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getTargetObjectId() {
        return targetObjectId;
    }

    /**
     * Imposta il valore della propriet� targetObjectId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setTargetObjectId(ObjectIdType value) {
        this.targetObjectId = value;
    }

}
