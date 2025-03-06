
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
 *         &lt;element name="CreateMemberResult" type="{urn:MemberService.service.livelink.opentext.com}Member" minOccurs="0"/>
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
    "createMemberResult"
})
@XmlRootElement(name = "CreateMemberResponse")
public class CreateMemberResponse {

    @XmlElement(name = "CreateMemberResult")
    protected Member createMemberResult;

    /**
     * Recupera il valore della proprietà createMemberResult.
     * 
     * @return
     *     possible object is
     *     {@link Member }
     *     
     */
    public Member getCreateMemberResult() {
        return createMemberResult;
    }

    /**
     * Imposta il valore della proprietà createMemberResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Member }
     *     
     */
    public void setCreateMemberResult(Member value) {
        this.createMemberResult = value;
    }

}
