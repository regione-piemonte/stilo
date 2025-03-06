
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
 *         &lt;element name="GetGroupByNameResult" type="{urn:MemberService.service.livelink.opentext.com}Group" minOccurs="0"/>
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
    "getGroupByNameResult"
})
@XmlRootElement(name = "GetGroupByNameResponse")
public class GetGroupByNameResponse {

    @XmlElement(name = "GetGroupByNameResult")
    protected Group getGroupByNameResult;

    /**
     * Recupera il valore della proprietà getGroupByNameResult.
     * 
     * @return
     *     possible object is
     *     {@link Group }
     *     
     */
    public Group getGetGroupByNameResult() {
        return getGroupByNameResult;
    }

    /**
     * Imposta il valore della proprietà getGroupByNameResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Group }
     *     
     */
    public void setGetGroupByNameResult(Group value) {
        this.getGroupByNameResult = value;
    }

}
