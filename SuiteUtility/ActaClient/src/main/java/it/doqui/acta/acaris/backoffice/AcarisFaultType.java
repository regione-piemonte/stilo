
package it.doqui.acta.acaris.backoffice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per acarisFaultType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="acarisFaultType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="exceptionType" type="{common.acaris.acta.doqui.it}enumServiceException"/&gt;
 *         &lt;element name="errorCode" type="{common.acaris.acta.doqui.it}enumErrorCodeType"/&gt;
 *         &lt;element name="technicalInfo" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="objectId" type="{common.acaris.acta.doqui.it}ObjectIdType"/&gt;
 *         &lt;element name="className" type="{common.acaris.acta.doqui.it}enumObjectType"/&gt;
 *         &lt;element name="propertyName" type="{common.acaris.acta.doqui.it}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "acarisFaultType", propOrder = {
    "exceptionType",
    "errorCode",
    "technicalInfo",
    "objectId",
    "className",
    "propertyName"
})
public class AcarisFaultType {

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumServiceException exceptionType;
    @XmlElement(required = true)
    protected String errorCode;
    @XmlElement(required = true)
    protected String technicalInfo;
    @XmlElement(required = true)
    protected ObjectIdType objectId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected EnumObjectType className;
    @XmlElement(required = true)
    protected String propertyName;

    /**
     * Recupera il valore della proprietà exceptionType.
     * 
     * @return
     *     possible object is
     *     {@link EnumServiceException }
     *     
     */
    public EnumServiceException getExceptionType() {
        return exceptionType;
    }

    /**
     * Imposta il valore della proprietà exceptionType.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumServiceException }
     *     
     */
    public void setExceptionType(EnumServiceException value) {
        this.exceptionType = value;
    }

    /**
     * Recupera il valore della proprietà errorCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Imposta il valore della proprietà errorCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Recupera il valore della proprietà technicalInfo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTechnicalInfo() {
        return technicalInfo;
    }

    /**
     * Imposta il valore della proprietà technicalInfo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTechnicalInfo(String value) {
        this.technicalInfo = value;
    }

    /**
     * Recupera il valore della proprietà objectId.
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
     * Imposta il valore della proprietà objectId.
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
     * Recupera il valore della proprietà className.
     * 
     * @return
     *     possible object is
     *     {@link EnumObjectType }
     *     
     */
    public EnumObjectType getClassName() {
        return className;
    }

    /**
     * Imposta il valore della proprietà className.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumObjectType }
     *     
     */
    public void setClassName(EnumObjectType value) {
        this.className = value;
    }

    /**
     * Recupera il valore della proprietà propertyName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Imposta il valore della proprietà propertyName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyName(String value) {
        this.propertyName = value;
    }

}
