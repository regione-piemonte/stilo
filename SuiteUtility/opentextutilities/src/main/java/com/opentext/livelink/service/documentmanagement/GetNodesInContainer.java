
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="containerID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="options" type="{urn:DocMan.service.livelink.opentext.com}GetNodesInContainerOptions" minOccurs="0"/>
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
    "containerID",
    "options"
})
@XmlRootElement(name = "GetNodesInContainer")
public class GetNodesInContainer {

    protected long containerID;
    protected GetNodesInContainerOptions options;

    /**
     * Recupera il valore della proprietà containerID.
     * 
     */
    public long getContainerID() {
        return containerID;
    }

    /**
     * Imposta il valore della proprietà containerID.
     * 
     */
    public void setContainerID(long value) {
        this.containerID = value;
    }

    /**
     * Recupera il valore della proprietà options.
     * 
     * @return
     *     possible object is
     *     {@link GetNodesInContainerOptions }
     *     
     */
    public GetNodesInContainerOptions getOptions() {
        return options;
    }

    /**
     * Imposta il valore della proprietà options.
     * 
     * @param value
     *     allowed object is
     *     {@link GetNodesInContainerOptions }
     *     
     */
    public void setOptions(GetNodesInContainerOptions value) {
        this.options = value;
    }

}
