
package it.doqui.acta.acaris.backoffice;

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
 *         &lt;element name="queryableObject" type="{common.acaris.acta.doqui.it}QueryableObjectType"/&gt;
 *         &lt;element name="operation" type="{common.acaris.acta.doqui.it}enumPropertyFilterOperation"/&gt;
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
    "queryableObject",
    "operation"
})
@XmlRootElement(name = "getQueryableObjectMetadata", namespace = "backoffice.acaris.acta.doqui.it")
public class GetQueryableObjectMetadata {

    @XmlElement(required = true)
    protected ObjectIdType repositoryId;
    @XmlElement(required = true)
    protected QueryableObjectType queryableObject;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumPropertyFilterOperation operation;

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
     * Recupera il valore della proprietà queryableObject.
     * 
     * @return
     *     possible object is
     *     {@link QueryableObjectType }
     *     
     */
    public QueryableObjectType getQueryableObject() {
        return queryableObject;
    }

    /**
     * Imposta il valore della proprietà queryableObject.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryableObjectType }
     *     
     */
    public void setQueryableObject(QueryableObjectType value) {
        this.queryableObject = value;
    }

    /**
     * Recupera il valore della proprietà operation.
     * 
     * @return
     *     possible object is
     *     {@link EnumPropertyFilterOperation }
     *     
     */
    public EnumPropertyFilterOperation getOperation() {
        return operation;
    }

    /**
     * Imposta il valore della proprietà operation.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumPropertyFilterOperation }
     *     
     */
    public void setOperation(EnumPropertyFilterOperation value) {
        this.operation = value;
    }

}
