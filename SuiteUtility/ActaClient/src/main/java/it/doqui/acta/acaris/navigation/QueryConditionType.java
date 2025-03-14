
package it.doqui.acta.acaris.navigation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per QueryConditionType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="QueryConditionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="propertyName" type="{common.acaris.acta.doqui.it}string"/&gt;
 *         &lt;element name="operator" type="{common.acaris.acta.doqui.it}enumQueryOperator"/&gt;
 *         &lt;element name="value" type="{common.acaris.acta.doqui.it}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueryConditionType", namespace = "common.acaris.acta.doqui.it", propOrder = {
    "propertyName",
    "operator",
    "value"
})
public class QueryConditionType {

    @XmlElement(namespace = "", required = true)
    protected String propertyName;
    @XmlElement(namespace = "", required = true)
    @XmlSchemaType(name = "string")
    protected EnumQueryOperator operator;
    @XmlElement(namespace = "", required = true)
    protected String value;

    /**
     * Recupera il valore della proprietÓ propertyName.
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
     * Imposta il valore della proprietÓ propertyName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyName(String value) {
        this.propertyName = value;
    }

    /**
     * Recupera il valore della proprietÓ operator.
     * 
     * @return
     *     possible object is
     *     {@link EnumQueryOperator }
     *     
     */
    public EnumQueryOperator getOperator() {
        return operator;
    }

    /**
     * Imposta il valore della proprietÓ operator.
     * 
     * @param value
     *     allowed object is
     *     {@link EnumQueryOperator }
     *     
     */
    public void setOperator(EnumQueryOperator value) {
        this.operator = value;
    }

    /**
     * Recupera il valore della proprietÓ value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietÓ value.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

}
