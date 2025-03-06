
package com.opentext.livelink.service.documentmanagement;

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
 *         &lt;element name="GetVersionContentsContextResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getVersionContentsContextResult"
})
@XmlRootElement(name = "GetVersionContentsContextResponse")
public class GetVersionContentsContextResponse {

    @XmlElement(name = "GetVersionContentsContextResult")
    protected String getVersionContentsContextResult;

    /**
     * Recupera il valore della proprietà getVersionContentsContextResult.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetVersionContentsContextResult() {
        return getVersionContentsContextResult;
    }

    /**
     * Imposta il valore della proprietà getVersionContentsContextResult.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetVersionContentsContextResult(String value) {
        this.getVersionContentsContextResult = value;
    }

}
