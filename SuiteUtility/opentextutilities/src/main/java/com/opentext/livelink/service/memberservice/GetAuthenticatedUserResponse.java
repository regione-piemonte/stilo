
package com.opentext.livelink.service.memberservice;

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
 *         &lt;element name="GetAuthenticatedUserResult" type="{urn:MemberService.service.livelink.opentext.com}User" minOccurs="0"/>
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
    "getAuthenticatedUserResult"
})
@XmlRootElement(name = "GetAuthenticatedUserResponse")
public class GetAuthenticatedUserResponse {

    @XmlElement(name = "GetAuthenticatedUserResult")
    protected User getAuthenticatedUserResult;

    /**
     * Recupera il valore della proprietà getAuthenticatedUserResult.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getGetAuthenticatedUserResult() {
        return getAuthenticatedUserResult;
    }

    /**
     * Imposta il valore della proprietà getAuthenticatedUserResult.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setGetAuthenticatedUserResult(User value) {
        this.getAuthenticatedUserResult = value;
    }

}
