
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
 *         &lt;element name="UpdateNodeRightsResult" type="{urn:DocMan.service.livelink.opentext.com}NodeRightUpdateInfo" minOccurs="0"/>
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
    "updateNodeRightsResult"
})
@XmlRootElement(name = "UpdateNodeRightsResponse")
public class UpdateNodeRightsResponse {

    @XmlElement(name = "UpdateNodeRightsResult")
    protected NodeRightUpdateInfo updateNodeRightsResult;

    /**
     * Recupera il valore della proprietà updateNodeRightsResult.
     * 
     * @return
     *     possible object is
     *     {@link NodeRightUpdateInfo }
     *     
     */
    public NodeRightUpdateInfo getUpdateNodeRightsResult() {
        return updateNodeRightsResult;
    }

    /**
     * Imposta il valore della proprietà updateNodeRightsResult.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeRightUpdateInfo }
     *     
     */
    public void setUpdateNodeRightsResult(NodeRightUpdateInfo value) {
        this.updateNodeRightsResult = value;
    }

}
