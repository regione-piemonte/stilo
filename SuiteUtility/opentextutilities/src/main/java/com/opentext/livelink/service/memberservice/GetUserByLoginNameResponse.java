
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
 *         &lt;element name="GetUserByLoginNameResult" type="{urn:MemberService.service.livelink.opentext.com}User" minOccurs="0"/>
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
    "getUserByLoginNameResult"
})
@XmlRootElement(name = "GetUserByLoginNameResponse")
public class GetUserByLoginNameResponse {

    @XmlElement(name = "GetUserByLoginNameResult")
    protected User getUserByLoginNameResult;

    /**
     * Recupera il valore della proprietà getUserByLoginNameResult.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getGetUserByLoginNameResult() {
        return getUserByLoginNameResult;
    }

    /**
     * Imposta il valore della proprietà getUserByLoginNameResult.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setGetUserByLoginNameResult(User value) {
        this.getUserByLoginNameResult = value;
    }

}
