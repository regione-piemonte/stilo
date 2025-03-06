
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
 *         &lt;element name="GetRenditionContentsResult" type="{urn:Core.service.livelink.opentext.com}Attachment" minOccurs="0"/>
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
    "getRenditionContentsResult"
})
@XmlRootElement(name = "GetRenditionContentsResponse")
public class GetRenditionContentsResponse {

    @XmlElement(name = "GetRenditionContentsResult")
    protected Attachment getRenditionContentsResult;

    /**
     * Recupera il valore della proprietà getRenditionContentsResult.
     * 
     * @return
     *     possible object is
     *     {@link Attachment }
     *     
     */
    public Attachment getGetRenditionContentsResult() {
        return getRenditionContentsResult;
    }

    /**
     * Imposta il valore della proprietà getRenditionContentsResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Attachment }
     *     
     */
    public void setGetRenditionContentsResult(Attachment value) {
        this.getRenditionContentsResult = value;
    }

}
