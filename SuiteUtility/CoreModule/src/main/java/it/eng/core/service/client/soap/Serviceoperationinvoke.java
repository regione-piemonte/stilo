
package it.eng.core.service.client.soap;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for serviceoperationinvoke complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="serviceoperationinvoke">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="serializationtype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="uuidtransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tokenid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="servicename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operationame" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="objectsserialize" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceoperationinvoke", propOrder = {
    "serializationtype",
    "uuidtransaction",
    "tokenid",
    "servicename",
    "operationame",
    "objectsserialize"
})
public class Serviceoperationinvoke {

    protected String serializationtype;
    protected String uuidtransaction;
    protected String tokenid;
    protected String servicename;
    protected String operationame;
    protected List<String> objectsserialize;

    /**
     * Gets the value of the serializationtype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerializationtype() {
        return serializationtype;
    }

    /**
     * Sets the value of the serializationtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerializationtype(String value) {
        this.serializationtype = value;
    }

    /**
     * Gets the value of the uuidtransaction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUuidtransaction() {
        return uuidtransaction;
    }

    /**
     * Sets the value of the uuidtransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUuidtransaction(String value) {
        this.uuidtransaction = value;
    }

    /**
     * Gets the value of the tokenid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTokenid() {
        return tokenid;
    }

    /**
     * Sets the value of the tokenid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTokenid(String value) {
        this.tokenid = value;
    }

    /**
     * Gets the value of the servicename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServicename() {
        return servicename;
    }

    /**
     * Sets the value of the servicename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServicename(String value) {
        this.servicename = value;
    }

    /**
     * Gets the value of the operationame property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationame() {
        return operationame;
    }

    /**
     * Sets the value of the operationame property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationame(String value) {
        this.operationame = value;
    }

    /**
     * Gets the value of the objectsserialize property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objectsserialize property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjectsserialize().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getObjectsserialize() {
        if (objectsserialize == null) {
            objectsserialize = new ArrayList<String>();
        }
        return this.objectsserialize;
    }

}
