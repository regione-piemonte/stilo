
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
 *         &lt;element name="UpdateNodeRightsContextResult" type="{urn:Core.service.livelink.opentext.com}ChunkedOperationContext" minOccurs="0"/>
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
    "updateNodeRightsContextResult"
})
@XmlRootElement(name = "UpdateNodeRightsContextResponse")
public class UpdateNodeRightsContextResponse {

    @XmlElement(name = "UpdateNodeRightsContextResult")
    protected ChunkedOperationContext updateNodeRightsContextResult;

    /**
     * Recupera il valore della proprietà updateNodeRightsContextResult.
     * 
     * @return
     *     possible object is
     *     {@link ChunkedOperationContext }
     *     
     */
    public ChunkedOperationContext getUpdateNodeRightsContextResult() {
        return updateNodeRightsContextResult;
    }

    /**
     * Imposta il valore della proprietà updateNodeRightsContextResult.
     * 
     * @param value
     *     allowed object is
     *     {@link ChunkedOperationContext }
     *     
     */
    public void setUpdateNodeRightsContextResult(ChunkedOperationContext value) {
        this.updateNodeRightsContextResult = value;
    }

}
