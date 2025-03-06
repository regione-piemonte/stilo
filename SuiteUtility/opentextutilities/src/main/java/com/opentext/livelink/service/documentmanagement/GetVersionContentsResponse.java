
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
 *         &lt;element name="GetVersionContentsResult" type="{urn:Core.service.livelink.opentext.com}Attachment" minOccurs="0"/>
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
    "getVersionContentsResult"
})
@XmlRootElement(name = "GetVersionContentsResponse")
public class GetVersionContentsResponse {

    @XmlElement(name = "GetVersionContentsResult")
    protected Attachment getVersionContentsResult;

    /**
     * Recupera il valore della proprietà getVersionContentsResult.
     * 
     * @return
     *     possible object is
     *     {@link Attachment }
     *     
     */
    public Attachment getGetVersionContentsResult() {
        return getVersionContentsResult;
    }

    /**
     * Imposta il valore della proprietà getVersionContentsResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Attachment }
     *     
     */
    public void setGetVersionContentsResult(Attachment value) {
        this.getVersionContentsResult = value;
    }

}
