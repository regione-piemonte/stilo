
package it.doqui.acta.acaris.sms;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per ObjectResponseType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="ObjectResponseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="objectId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="properties" type="{common.acaris.acta.doqui.it}PropertyType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObjectResponseType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "objectId",
    "properties"
})
public class ObjectResponseType {

    @XmlElement(namespace = "", required = true)
    protected ObjectIdType objectId;
    @XmlElement(namespace = "")
    protected List<PropertyType> properties;

    /**
     * Recupera il valore della propriet� objectId.
     * 
     * @return
     *     possible object is
     *     {@link ObjectIdType }
     *     
     */
    public ObjectIdType getObjectId() {
        return objectId;
    }

    /**
     * Imposta il valore della propriet� objectId.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectIdType }
     *     
     */
    public void setObjectId(ObjectIdType value) {
        this.objectId = value;
    }

    /**
     * Gets the value of the properties property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the properties property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperties().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyType }
     * 
     * 
     */
    public List<PropertyType> getProperties() {
        if (properties == null) {
            properties = new ArrayList<PropertyType>();
        }
        return this.properties;
    }

}
