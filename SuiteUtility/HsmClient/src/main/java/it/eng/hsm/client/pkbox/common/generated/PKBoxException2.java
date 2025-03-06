
package it.eng.hsm.client.pkbox.common.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per PKBoxException complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="PKBoxException">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hexErrorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PKBoxException", namespace = "http://soap.remote.pkserver.it/xsd", propOrder = {
    "errorCode",
    "hexErrorCode",
    "message"
})
public class PKBoxException2 {

    protected int errorCode;
    @XmlElement(required = true, nillable = true)
    protected String hexErrorCode;
    @XmlElement(required = true, nillable = true)
    protected String message;

    /**
     * Recupera il valore della proprietà errorCode.
     * 
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Imposta il valore della proprietà errorCode.
     * 
     */
    public void setErrorCode(int value) {
        this.errorCode = value;
    }

    /**
     * Recupera il valore della proprietà hexErrorCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHexErrorCode() {
        return hexErrorCode;
    }

    /**
     * Imposta il valore della proprietà hexErrorCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHexErrorCode(String value) {
        this.hexErrorCode = value;
    }

    /**
     * Recupera il valore della proprietà message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Imposta il valore della proprietà message.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

}
