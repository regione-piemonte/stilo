
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
 *         &lt;element name="CreateDocumentResult" type="{urn:DocMan.service.livelink.opentext.com}Node" minOccurs="0"/>
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
    "createDocumentResult"
})
@XmlRootElement(name = "CreateDocumentResponse")
public class CreateDocumentResponse {

    @XmlElement(name = "CreateDocumentResult")
    protected Node createDocumentResult;

    /**
     * Recupera il valore della proprietà createDocumentResult.
     * 
     * @return
     *     possible object is
     *     {@link Node }
     *     
     */
    public Node getCreateDocumentResult() {
        return createDocumentResult;
    }

    /**
     * Imposta il valore della proprietà createDocumentResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Node }
     *     
     */
    public void setCreateDocumentResult(Node value) {
        this.createDocumentResult = value;
    }

}
