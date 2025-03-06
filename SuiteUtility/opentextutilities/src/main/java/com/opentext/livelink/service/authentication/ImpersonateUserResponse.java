
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
 *         &lt;element name="ImpersonateUserResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "impersonateUserResult"
})
@XmlRootElement(name = "ImpersonateUserResponse")
public class ImpersonateUserResponse {

    @XmlElement(name = "ImpersonateUserResult")
    protected String impersonateUserResult;

    /**
     * Recupera il valore della proprietà impersonateUserResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImpersonateUserResult() {
        return impersonateUserResult;
    }

    /**
     * Imposta il valore della proprietà impersonateUserResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImpersonateUserResult(String value) {
        this.impersonateUserResult = value;
    }

}
