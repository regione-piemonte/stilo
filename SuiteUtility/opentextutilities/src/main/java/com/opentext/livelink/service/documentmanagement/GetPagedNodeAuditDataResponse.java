
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
 *         &lt;element name="GetPagedNodeAuditDataResult" type="{urn:DocMan.service.livelink.opentext.com}PagedNodeAuditData" minOccurs="0"/>
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
    "getPagedNodeAuditDataResult"
})
@XmlRootElement(name = "GetPagedNodeAuditDataResponse")
public class GetPagedNodeAuditDataResponse {

    @XmlElement(name = "GetPagedNodeAuditDataResult")
    protected PagedNodeAuditData getPagedNodeAuditDataResult;

    /**
     * Recupera il valore della proprietà getPagedNodeAuditDataResult.
     * 
     * @return
     *     possible object is
     *     {@link PagedNodeAuditData }
     *     
     */
    public PagedNodeAuditData getGetPagedNodeAuditDataResult() {
        return getPagedNodeAuditDataResult;
    }

    /**
     * Imposta il valore della proprietà getPagedNodeAuditDataResult.
     * 
     * @param value
     *     allowed object is
     *     {@link PagedNodeAuditData }
     *     
     */
    public void setGetPagedNodeAuditDataResult(PagedNodeAuditData value) {
        this.getPagedNodeAuditDataResult = value;
    }

}
