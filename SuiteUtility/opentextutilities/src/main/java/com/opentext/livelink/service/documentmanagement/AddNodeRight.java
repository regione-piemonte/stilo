
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
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="nodeRight" type="{urn:DocMan.service.livelink.opentext.com}NodeRight" minOccurs="0"/>
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
    "id",
    "nodeRight"
})
@XmlRootElement(name = "AddNodeRight")
public class AddNodeRight {

    @XmlElement(name = "ID")
    protected long id;
    protected NodeRight nodeRight;

    /**
     * Recupera il valore della proprietà id.
     * 
     */
    public long getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     */
    public void setID(long value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà nodeRight.
     * 
     * @return
     *     possible object is
     *     {@link NodeRight }
     *     
     */
    public NodeRight getNodeRight() {
        return nodeRight;
    }

    /**
     * Imposta il valore della proprietà nodeRight.
     * 
     * @param value
     *     allowed object is
     *     {@link NodeRight }
     *     
     */
    public void setNodeRight(NodeRight value) {
        this.nodeRight = value;
    }

}
