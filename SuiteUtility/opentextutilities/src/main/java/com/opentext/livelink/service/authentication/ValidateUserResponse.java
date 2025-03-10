
package com.opentext.livelink.service.authentication;

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
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValidateUserResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "validateUserResult"
})
@XmlRootElement(name = "ValidateUserResponse")
public class ValidateUserResponse {

    @XmlElement(name = "ValidateUserResult")
    protected String validateUserResult;

    /**
     * Recupera il valore della proprietà validateUserResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidateUserResult() {
        return validateUserResult;
    }

    /**
     * Imposta il valore della proprietà validateUserResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidateUserResult(String value) {
        this.validateUserResult = value;
    }

}
