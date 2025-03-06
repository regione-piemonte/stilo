
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per NodeAuditRecord complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodeAuditRecord">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="AuditDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="AuditNumber" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="AuditString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NodeID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="PerformerID" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeAuditRecord", propOrder = {
    "auditDate",
    "auditNumber",
    "auditString",
    "nodeID",
    "performerID"
})
public class NodeAuditRecord
    extends ServiceDataObject
{

    @XmlElement(name = "AuditDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar auditDate;
    @XmlElement(name = "AuditNumber")
    protected int auditNumber;
    @XmlElement(name = "AuditString")
    protected String auditString;
    @XmlElement(name = "NodeID")
    protected long nodeID;
    @XmlElement(name = "PerformerID")
    protected long performerID;

    /**
     * Recupera il valore della proprietà auditDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAuditDate() {
        return auditDate;
    }

    /**
     * Imposta il valore della proprietà auditDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAuditDate(XMLGregorianCalendar value) {
        this.auditDate = value;
    }

    /**
     * Recupera il valore della proprietà auditNumber.
     * 
     */
    public int getAuditNumber() {
        return auditNumber;
    }

    /**
     * Imposta il valore della proprietà auditNumber.
     * 
     */
    public void setAuditNumber(int value) {
        this.auditNumber = value;
    }

    /**
     * Recupera il valore della proprietà auditString.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditString() {
        return auditString;
    }

    /**
     * Imposta il valore della proprietà auditString.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditString(String value) {
        this.auditString = value;
    }

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
     * Recupera il valore della proprietà performerID.
     * 
     */
    public long getPerformerID() {
        return performerID;
    }

    /**
     * Imposta il valore della proprietà performerID.
     * 
     */
    public void setPerformerID(long value) {
        this.performerID = value;
    }

}
