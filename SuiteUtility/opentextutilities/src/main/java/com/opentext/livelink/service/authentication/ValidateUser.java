
package com.opentext.livelink.service.authentication;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="capToken" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "capToken"
})
@XmlRootElement(name = "ValidateUser")
public class ValidateUser {

    protected String capToken;

    /**
     * Recupera il valore della proprietà capToken.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapToken() {
        return capToken;
    }

    /**
     * Imposta il valore della proprietà capToken.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapToken(String value) {
        this.capToken = value;
    }

}
