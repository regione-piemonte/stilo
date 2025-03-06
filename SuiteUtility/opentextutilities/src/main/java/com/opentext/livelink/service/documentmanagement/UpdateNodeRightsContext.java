
package com.opentext.livelink.service.documentmanagement;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="nodeID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="operation" type="{urn:DocMan.service.livelink.opentext.com}RightOperation"/>
 *         &lt;element name="rights" type="{urn:DocMan.service.livelink.opentext.com}NodeRight" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="propagation" type="{urn:DocMan.service.livelink.opentext.com}RightPropagation"/>
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
    "nodeID",
    "operation",
    "rights",
    "propagation"
})
@XmlRootElement(name = "UpdateNodeRightsContext")
public class UpdateNodeRightsContext {

    protected long nodeID;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RightOperation operation;
    @XmlElement(nillable = true)
    protected List<NodeRight> rights;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected RightPropagation propagation;

    /**
     * Recupera il valore della proprietà nodeID.
     * 
     */
    public long getNodeID() {
        return nodeID;
    }

    /**
     * Imposta il valore della proprietà nodeID.
     * 
     */
    public void setNodeID(long value) {
        this.nodeID = value;
    }

    /**
     * Recupera il valore della proprietà operation.
     * 
     * @return
     *     possible object is
     *     {@link RightOperation }
     *     
     */
    public RightOperation getOperation() {
        return operation;
    }

    /**
     * Imposta il valore della proprietà operation.
     * 
     * @param value
     *     allowed object is
     *     {@link RightOperation }
     *     
     */
    public void setOperation(RightOperation value) {
        this.operation = value;
    }

    /**
     * Gets the value of the rights property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rights property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRights().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NodeRight }
     * 
     * 
     */
    public List<NodeRight> getRights() {
        if (rights == null) {
            rights = new ArrayList<NodeRight>();
        }
        return this.rights;
    }

    /**
     * Recupera il valore della proprietà propagation.
     * 
     * @return
     *     possible object is
     *     {@link RightPropagation }
     *     
     */
    public RightPropagation getPropagation() {
        return propagation;
    }

    /**
     * Imposta il valore della proprietà propagation.
     * 
     * @param value
     *     allowed object is
     *     {@link RightPropagation }
     *     
     */
    public void setPropagation(RightPropagation value) {
        this.propagation = value;
    }

}
