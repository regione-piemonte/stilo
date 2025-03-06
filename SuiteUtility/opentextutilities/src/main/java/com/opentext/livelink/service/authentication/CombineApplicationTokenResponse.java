
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
 *         &lt;element name="CombineApplicationTokenResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "combineApplicationTokenResult"
})
@XmlRootElement(name = "CombineApplicationTokenResponse")
public class CombineApplicationTokenResponse {

    @XmlElement(name = "CombineApplicationTokenResult")
    protected String combineApplicationTokenResult;

    /**
     * Recupera il valore della proprietà combineApplicationTokenResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombineApplicationTokenResult() {
        return combineApplicationTokenResult;
    }

    /**
     * Imposta il valore della proprietà combineApplicationTokenResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombineApplicationTokenResult(String value) {
        this.combineApplicationTokenResult = value;
    }

}
