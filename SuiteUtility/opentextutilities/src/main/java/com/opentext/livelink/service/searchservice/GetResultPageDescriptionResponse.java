
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
 *         &lt;element name="GetResultPageDescriptionResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getResultPageDescriptionResult"
})
@XmlRootElement(name = "GetResultPageDescriptionResponse")
public class GetResultPageDescriptionResponse {

    @XmlElement(name = "GetResultPageDescriptionResult")
    protected String getResultPageDescriptionResult;

    /**
     * Recupera il valore della proprietà getResultPageDescriptionResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetResultPageDescriptionResult() {
        return getResultPageDescriptionResult;
    }

    /**
     * Imposta il valore della proprietà getResultPageDescriptionResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetResultPageDescriptionResult(String value) {
        this.getResultPageDescriptionResult = value;
    }

}
