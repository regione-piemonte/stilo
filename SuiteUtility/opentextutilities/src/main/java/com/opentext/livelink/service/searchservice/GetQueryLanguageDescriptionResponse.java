
package com.opentext.livelink.service.searchservice;

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
 *         &lt;element name="GetQueryLanguageDescriptionResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getQueryLanguageDescriptionResult"
})
@XmlRootElement(name = "GetQueryLanguageDescriptionResponse")
public class GetQueryLanguageDescriptionResponse {

    @XmlElement(name = "GetQueryLanguageDescriptionResult")
    protected String getQueryLanguageDescriptionResult;

    /**
     * Recupera il valore della proprietà getQueryLanguageDescriptionResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetQueryLanguageDescriptionResult() {
        return getQueryLanguageDescriptionResult;
    }

    /**
     * Imposta il valore della proprietà getQueryLanguageDescriptionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetQueryLanguageDescriptionResult(String value) {
        this.getQueryLanguageDescriptionResult = value;
    }

}
