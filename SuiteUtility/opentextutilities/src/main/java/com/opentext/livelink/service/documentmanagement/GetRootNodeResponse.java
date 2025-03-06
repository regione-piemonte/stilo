
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
 *         &lt;element name="GetRootNodeResult" type="{urn:DocMan.service.livelink.opentext.com}Node" minOccurs="0"/>
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
    "getRootNodeResult"
})
@XmlRootElement(name = "GetRootNodeResponse")
public class GetRootNodeResponse {

    @XmlElement(name = "GetRootNodeResult")
    protected Node getRootNodeResult;

    /**
     * Recupera il valore della proprietà getRootNodeResult.
     * 
     * @return
     *     possible object is
     *     {@link Node }
     *     
     */
    public Node getGetRootNodeResult() {
        return getRootNodeResult;
    }

    /**
     * Imposta il valore della proprietà getRootNodeResult.
     * 
     * @param value
     *     allowed object is
     *     {@link Node }
     *     
     */
    public void setGetRootNodeResult(Node value) {
        this.getRootNodeResult = value;
    }

}
