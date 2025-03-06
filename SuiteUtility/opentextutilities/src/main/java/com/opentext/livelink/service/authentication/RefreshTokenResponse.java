
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
 *         &lt;element name="RefreshTokenResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "refreshTokenResult"
})
@XmlRootElement(name = "RefreshTokenResponse")
public class RefreshTokenResponse {

    @XmlElement(name = "RefreshTokenResult")
    protected String refreshTokenResult;

    /**
     * Recupera il valore della proprietà refreshTokenResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefreshTokenResult() {
        return refreshTokenResult;
    }

    /**
     * Imposta il valore della proprietà refreshTokenResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefreshTokenResult(String value) {
        this.refreshTokenResult = value;
    }

}
