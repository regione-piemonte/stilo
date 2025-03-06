
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
 *         &lt;element name="GetNodeRightsResult" type="{urn:DocMan.service.livelink.opentext.com}NodeRights" minOccurs="0"/>
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
    "getNodeRightsResult"
})
@XmlRootElement(name = "GetNodeRightsResponse")
public class GetNodeRightsResponse {

    @XmlElement(name = "GetNodeRightsResult")
    protected NodeRights getNodeRightsResult;

    /**
     * Recupera il valore della proprietà getNodeRightsResult.
     * 
     * @return
     *     possible object is
     *     {@link NodeRights }
     *     
     */
    public NodeRights getGetNodeRightsResult() {
        return getNodeRightsResult;
    }

    /**
     * Imposta il valore della proprietà getNodeRightsResult.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeRights }
     *     
     */
    public void setGetNodeRightsResult(NodeRights value) {
        this.getNodeRightsResult = value;
    }

}
