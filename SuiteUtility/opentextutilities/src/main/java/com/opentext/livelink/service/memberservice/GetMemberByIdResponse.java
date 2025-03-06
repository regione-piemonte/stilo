
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
 *         &lt;element name="GetMemberByIdResult" type="{urn:MemberService.service.livelink.opentext.com}Member" minOccurs="0"/>
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
    "getMemberByIdResult"
})
@XmlRootElement(name = "GetMemberByIdResponse")
public class GetMemberByIdResponse {

    @XmlElement(name = "GetMemberByIdResult")
    protected Member getMemberByIdResult;

    /**
     * Recupera il valore della proprietà getMemberByIdResult.
     * 
     * @return
     *     possible object is
     *     {@link Member }
     *     
     */
    public Member getGetMemberByIdResult() {
        return getMemberByIdResult;
    }

    /**
     * Imposta il valore della proprietà getMemberByIdResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Member }
     *     
     */
    public void setGetMemberByIdResult(Member value) {
        this.getMemberByIdResult = value;
    }

}
