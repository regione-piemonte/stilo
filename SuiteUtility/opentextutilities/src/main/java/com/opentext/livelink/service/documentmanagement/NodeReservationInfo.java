
package com.opentext.livelink.service.documentmanagement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per NodeReservationInfo complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="NodeReservationInfo">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:DocMan.service.livelink.opentext.com}ServiceDataObject">
 *       &lt;sequence>
 *         &lt;element name="Reserved" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ReservedBy" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="ReservedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NodeReservationInfo", propOrder = {
    "reserved",
    "reservedBy",
    "reservedDate"
})
public class NodeReservationInfo
    extends ServiceDataObject
{

    @XmlElement(name = "Reserved")
    protected boolean reserved;
    @XmlElement(name = "ReservedBy")
    protected long reservedBy;
    @XmlElement(name = "ReservedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar reservedDate;

    /**
     * Recupera il valore della proprietà reserved.
     * 
     */
    public boolean isReserved() {
        return reserved;
    }

    /**
     * Imposta il valore della proprietà reserved.
     * 
     */
    public void setReserved(boolean value) {
        this.reserved = value;
    }

    /**
     * Recupera il valore della proprietà reservedBy.
     * 
     */
    public long getReservedBy() {
        return reservedBy;
    }

    /**
     * Imposta il valore della proprietà reservedBy.
     * 
     */
    public void setReservedBy(long value) {
        this.reservedBy = value;
    }

    /**
     * Recupera il valore della proprietà reservedDate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReservedDate() {
        return reservedDate;
    }

    /**
     * Imposta il valore della proprietà reservedDate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReservedDate(XMLGregorianCalendar value) {
        this.reservedDate = value;
    }

}
