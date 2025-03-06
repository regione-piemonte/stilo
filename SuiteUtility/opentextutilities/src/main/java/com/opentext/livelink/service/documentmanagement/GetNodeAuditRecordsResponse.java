
package com.opentext.livelink.service.documentmanagement;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="GetNodeAuditRecordsResult" type="{urn:DocMan.service.livelink.opentext.com}NodeAuditRecord" maxOccurs="unbounded" minOccurs="0"/>
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
    "getNodeAuditRecordsResult"
})
@XmlRootElement(name = "GetNodeAuditRecordsResponse")
public class GetNodeAuditRecordsResponse {

    @XmlElement(name = "GetNodeAuditRecordsResult", nillable = true)
    protected List<NodeAuditRecord> getNodeAuditRecordsResult;

    /**
     * Gets the value of the getNodeAuditRecordsResult property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the getNodeAuditRecordsResult property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGetNodeAuditRecordsResult().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NodeAuditRecord }
     * 
     * 
     */
    public List<NodeAuditRecord> getGetNodeAuditRecordsResult() {
        if (getNodeAuditRecordsResult == null) {
            getNodeAuditRecordsResult = new ArrayList<NodeAuditRecord>();
        }
        return this.getNodeAuditRecordsResult;
    }

}
